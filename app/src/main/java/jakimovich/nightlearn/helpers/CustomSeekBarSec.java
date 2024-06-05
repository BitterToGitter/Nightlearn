package jakimovich.nightlearn.helpers;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;

import androidx.core.content.ContextCompat;

import jakimovich.nightlearn.R;

public class CustomSeekBarSec extends CustomSeekBar {

    public CustomSeekBarSec(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.superOnDraw(canvas);

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
