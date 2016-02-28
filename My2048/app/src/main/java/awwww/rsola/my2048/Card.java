package awwww.rsola.my2048;

import android.content.Context;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;

public class Card extends FrameLayout {

    public Card(Context context) {
        super(context);

        textViewNumber = new TextView(getContext());
        textViewNumber.setTextSize(32);
        textViewNumber.setGravity(Gravity.CENTER);
        textViewNumber.setBackgroundColor(0x33FFFFFF);

        LayoutParams lp = new LayoutParams(-1, -1);
        lp.setMargins(10, 10, 10, 10);
        addView(textViewNumber, lp);

        setNumber(0);
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
        textViewNumber.setText(number > 0 ? number + "" : "");

        switch (number) {
            case 0:
                textViewNumber.setBackgroundColor(0x33FFFFFF);
                break;
            case 2:
                textViewNumber.setTextColor(getResources().getColor(R.color.text2));
                textViewNumber.setBackgroundColor(getResources().getColor(R.color.bg2));
                break;
            case 4:
                textViewNumber.setTextColor(getResources().getColor(R.color.text4));
                textViewNumber.setBackgroundColor(getResources().getColor(R.color.bg4));
                break;
            case 8:
                textViewNumber.setTextColor(getResources().getColor(R.color.text8));
                textViewNumber.setBackgroundColor(getResources().getColor(R.color.bg8));
                break;
            case 16:
                textViewNumber.setTextColor(getResources().getColor(R.color.text16));
                textViewNumber.setBackgroundColor(getResources().getColor(R.color.bg16));
                break;
            case 32:
                textViewNumber.setTextColor(getResources().getColor(R.color.text32));
                textViewNumber.setBackgroundColor(getResources().getColor(R.color.bg32));
                break;
            case 64:
                textViewNumber.setTextColor(getResources().getColor(R.color.text64));
                textViewNumber.setBackgroundColor(getResources().getColor(R.color.bg64));
                break;
            case 128:
                textViewNumber.setTextColor(getResources().getColor(R.color.text128));
                textViewNumber.setBackgroundColor(getResources().getColor(R.color.bg128));
                break;
            case 256:
                textViewNumber.setTextColor(getResources().getColor(R.color.text256));
                textViewNumber.setBackgroundColor(getResources().getColor(R.color.bg256));
                break;
            case 512:
                textViewNumber.setTextColor(getResources().getColor(R.color.text512));
                textViewNumber.setBackgroundColor(getResources().getColor(R.color.bg512));
                break;
            case 1024:
                textViewNumber.setTextColor(getResources().getColor(R.color.text1024));
                textViewNumber.setBackgroundColor(getResources().getColor(R.color.bg1024));
                break;
            case 2048:
                textViewNumber.setTextColor(getResources().getColor(R.color.text2048));
                textViewNumber.setBackgroundColor(getResources().getColor(R.color.bg2048));
                break;
            default:
                textViewNumber.setTextColor(getResources().getColor(R.color.textsuper));
                textViewNumber.setBackgroundColor(getResources().getColor(R.color.bgsuper));
                break;
        }
    }

    public boolean equals(Card o) {
        return getNumber() == o.getNumber();
    }

    private int number = 0;
    private TextView textViewNumber;
}
