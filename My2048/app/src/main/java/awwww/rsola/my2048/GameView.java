package awwww.rsola.my2048;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;

import java.util.ArrayList;
import java.util.List;

public class GameView extends GridLayout {

    public GameView(Context context) {
        super(context);
        initGameView();
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initGameView();
    }

    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initGameView();
    }

    private void initGameView() {
        setColumnCount(4);
        setBackgroundColor(0xFFBBADA0);

        setOnTouchListener(new OnTouchListener() {
            private float startX, startY;
            private float endX, endY;
            private float offsetX, offsetY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startX = event.getX();
                        startY = event.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        endX = event.getX();
                        endY = event.getY();

                        offsetX = endX - startX;
                        offsetY = endY - startY;

                        if (Math.abs(offsetX) > Math.abs(offsetY)) {
                            if (offsetX < -100) {
                                moveLeft();
                            } else if (offsetX > 100) {
                                moveRight();
                            }
                        } else {
                            if (offsetY < -100) {
                                moveUp();
                            } else if (offsetY > 100) {
                                moveDown();
                            }
                        }
                }
                return true;
            }
        });
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        int cardSize = (Math.min(w, h) - 10) / 4;
        addCards(cardSize);

        startGame();
    }

    private void addCards(int cardSize) {
        Card card;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                card = new Card(getContext());
                card.setNumber(0);
                addView(card, cardSize, cardSize);
                cardMap[i][j] = card;
            }
        }
    }

    private void addRandomNumber() {
        emptyPoints.clear();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (cardMap[i][j].getNumber() == 0) {
                    emptyPoints.add(new Point(i, j));
                }
            }
        }

        Point point = emptyPoints.remove((int) (Math.random() * emptyPoints.size()));
        cardMap[point.x][point.y].setNumber(Math.random() > 0.1 ? 2 : 4);
    }

    private void startGame() {
        if (MainActivity.getMainActivity() != null) {
            MainActivity.getMainActivity().clearScore();
        }

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                cardMap[i][j].setNumber(0);
            }
        }

        addRandomNumber();
        addRandomNumber();
    }

    private void checkGame() {
        boolean completed = true;

        ALL:
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (cardMap[i][j].getNumber() == 0 ||
                        (i > 0 && cardMap[i][j].equals(cardMap[i - 1][j])) ||
                        (i < 3 && cardMap[i][j].equals(cardMap[i + 1][j])) ||
                        (j > 0 && cardMap[i][j].equals(cardMap[i][j - 1])) ||
                        (j < 3 && cardMap[i][j].equals(cardMap[i][j + 1]))) {
                    completed = false;
                    break ALL;
                }
            }
        }

        if (completed) {
            new AlertDialog.Builder(getContext()).setTitle("2048").setMessage("游戏结束。").setPositiveButton("重新开始", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startGame();
                }
            }).show();
        }
    }

    private void updateBest() {
        int best, score;
        SharedPreferences sp = getContext().getSharedPreferences("my2048", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        score = MainActivity.getMainActivity().getScore();
        best = sp.getInt("best", 0);
        if (best < score) {
            editor.putInt("best", score);
            MainActivity.getMainActivity().setBest(score);
            editor.apply();
        }
    }

    private void moveLeft() {
        boolean moved = false;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                for (int y = j + 1; y < 4; y++) {
                    if (cardMap[i][y].getNumber() > 0) {
                        if (cardMap[i][j].getNumber() == 0) {
                            cardMap[i][j].setNumber(cardMap[i][y].getNumber());
                            cardMap[i][y].setNumber(0);
                            y = j + 1;
                            moved = true;
                        } else if (cardMap[i][j].equals(cardMap[i][y])) {
                            cardMap[i][j].setNumber(cardMap[i][j].getNumber() * 2);
                            cardMap[i][y].setNumber(0);
                            MainActivity.getMainActivity().addScore(cardMap[i][j].getNumber());
                            updateBest();
                            moved = true;
                        }
                    }
                }
            }
        }
        if (moved) {
            addRandomNumber();
            checkGame();
        }
    }

    private void moveRight() {
        boolean moved = false;
        for (int i = 0; i < 4; i++) {
            for (int j = 3; j >= 0; j--) {
                for (int y = j - 1; y >= 0; y--) {
                    if (cardMap[i][y].getNumber() > 0) {
                        if (cardMap[i][j].getNumber() == 0) {
                            cardMap[i][j].setNumber(cardMap[i][y].getNumber());
                            cardMap[i][y].setNumber(0);
                            y = j - 1;
                            moved = true;
                        } else if (cardMap[i][j].equals(cardMap[i][y])) {
                            cardMap[i][j].setNumber(cardMap[i][j].getNumber() * 2);
                            cardMap[i][y].setNumber(0);
                            MainActivity.getMainActivity().addScore(cardMap[i][j].getNumber());
                            updateBest();
                            moved = true;
                        }
                    }
                }
            }
        }
        if (moved) {
            addRandomNumber();
            checkGame();
        }
    }

    private void moveUp() {
        boolean moved = false;
        for (int j = 0; j < 4; j++) {
            for (int i = 0; i < 4; i++) {
                for (int x = i + 1; x < 4; x++) {
                    if (cardMap[x][j].getNumber() > 0) {
                        if (cardMap[i][j].getNumber() == 0) {
                            cardMap[i][j].setNumber(cardMap[x][j].getNumber());
                            cardMap[x][j].setNumber(0);
                            x = i + 1;
                            moved = true;
                        } else if (cardMap[i][j].equals(cardMap[x][j])) {
                            cardMap[i][j].setNumber(cardMap[i][j].getNumber() * 2);
                            cardMap[x][j].setNumber(0);
                            MainActivity.getMainActivity().addScore(cardMap[i][j].getNumber());
                            updateBest();
                            moved = true;
                        }
                    }
                }
            }
        }
        if (moved) {
            addRandomNumber();
            checkGame();
        }
    }

    private void moveDown() {
        boolean moved = false;
        for (int j = 0; j < 4; j++) {
            for (int i = 3; i >= 0; i--) {
                for (int x = i - 1; x >= 0; x--) {
                    if (cardMap[x][j].getNumber() > 0) {
                        if (cardMap[i][j].getNumber() == 0) {
                            cardMap[i][j].setNumber(cardMap[x][j].getNumber());
                            cardMap[x][j].setNumber(0);
                            x = i - 1;
                            moved = true;
                        } else if (cardMap[i][j].equals(cardMap[x][j])) {
                            cardMap[i][j].setNumber(cardMap[i][j].getNumber() * 2);
                            cardMap[x][j].setNumber(0);
                            MainActivity.getMainActivity().addScore(cardMap[i][j].getNumber());
                            updateBest();
                            moved = true;
                        }
                    }
                }
            }
        }
        if (moved) {
            addRandomNumber();
            checkGame();
        }
    }

    private Card[][] cardMap = new Card[4][4];
    private List<Point> emptyPoints = new ArrayList<>();

}
