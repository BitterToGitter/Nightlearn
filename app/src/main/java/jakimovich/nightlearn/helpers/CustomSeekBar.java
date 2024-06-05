package jakimovich.nightlearn.helpers;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;

import androidx.core.content.ContextCompat;

import jakimovich.nightlearn.R;

public class CustomSeekBar extends androidx.appcompat.widget.AppCompatSeekBar {

    protected Rect rect;
    protected Paint paint;

    public CustomSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        rect = new Rect();
        paint = new Paint();
        paint.setColor(ContextCompat.getColor(context, R.color.brightGray));
        paint.setTextSize(70);
        paint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int progress = getProgress();
        String progressText = String.valueOf(progress);

        // get thumb's x position
        float thumb_x = (float) (getPaddingLeft() + ((double) progress / getMax()  * (getWidth() - getPaddingLeft() - getPaddingRight())));
        // get thumb's y position
        float thumb_y = getHeight() / 2 + getPaddingBottom() / 2;

        paint.getTextBounds(progressText, 0, progressText.length(), rect);
        canvas.drawText(progressText, thumb_x, thumb_y + rect.height() / 2, paint);
    } //Todo: to solve issue with x-axis


    protected void superOnDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
