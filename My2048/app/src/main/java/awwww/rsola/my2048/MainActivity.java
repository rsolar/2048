package awwww.rsola.my2048;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewScore = (TextView) findViewById(R.id.textViewScore);
        textViewBest = (TextView) findViewById(R.id.textViewBest);

        SharedPreferences sp = getSharedPreferences("my2048", Context.MODE_PRIVATE);

        best = sp.getInt("best", 0);
        textViewBest.setText(best + "");

        mainActivity = this;
    }

    public static MainActivity getMainActivity() {
        return mainActivity;
    }

    public void showScore() {
        textViewScore.setText(score + "");
        textViewBest.setText(best + "");
    }

    public int getScore() {
        return score;
    }

    public void clearScore() {
        score = 0;
        showScore();
    }

    public void addScore(int s) {
        score += s;
        showScore();
    }

    public void setBest(int s) {
        best = s;
        showScore();
    }

    private int score = 0;
    private int best = 0;
    private TextView textViewScore;
    private TextView textViewBest;
    private static MainActivity mainActivity = null;

}
