package jakimovich.nightlearn.helpers;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;

import androidx.core.content.ContextCompat;

import jakimovich.nightlearn.R;

public class CustomSeekBarSec extends androidx.appcompat.widget.AppCompatSeekBar {

    private Rect rect;
    private Paint paint;

    public CustomSeekBarSec(Context context, AttributeSet attrs) {
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
        String progressText = String.valueOf(progress) + " sec";

        // get thumb's x position
        float thumb_x = (float) (getPaddingLeft() + ((double) progress / getMax()) * (getWidth() - getPaddingLeft() - getPaddingRight()));
        // get thumb's y position
        float thumb_y = getHeight() / 2 + getPaddingBottom() / 2;

        // draw text centered on thumb
        paint.getTextBounds(progressText, 0, progressText.length(), rect);
        canvas.drawText(progressText, thumb_x, thumb_y + rect.height() / 2, paint);
    }
}
