package ru.vladus177.customview.util;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;

import ru.vladus177.customview.R;

public class TrackingTextView extends android.support.v7.widget.AppCompatTextView {

    public TrackingTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        try {

            TypedArray a = context.obtainStyledAttributes(attrs,
                    R.styleable.TrackingTextView, defStyle, 0);

            String str = a.getString(R.styleable.TrackingTextView_fonttype);
            a.recycle();
            switch (Integer.parseInt(str)) {
                case 0:
                    //str = "assets/fonts/digital7.ttf";
                    str = "fonts/digital7m.ttf";
                    break;
                default:
                    break;
            }

            setTypeface(FontManager.getInstance(getContext()).loadFont(str));
            init();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public TrackingTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    private void init() {

        maxTextSize = this.getTextSize();
        if (maxTextSize < 35) {
            maxTextSize = 30;
        }
        minTextSize = 20;
    }

    private void refitText(String text, int textWidth) {
        if (textWidth > 0) {
            int availableWidth = textWidth - this.getPaddingLeft()
                    - this.getPaddingRight();
            float trySize = maxTextSize;

            this.setTextSize(TypedValue.COMPLEX_UNIT_PX, trySize);
            while ((trySize > minTextSize)
                    && (this.getPaint().measureText(text) > availableWidth)) {
                trySize -= 1;
                if (trySize <= minTextSize) {
                    trySize = minTextSize;
                    break;
                }
                this.setTextSize(TypedValue.COMPLEX_UNIT_PX, trySize);
            }
            this.setTextSize(TypedValue.COMPLEX_UNIT_PX, trySize);
        }
    }

    @Override
    protected void onTextChanged(final CharSequence text, final int start,
                                 final int before, final int after) {
        refitText(text.toString(), this.getWidth());
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        if (w != oldw) {
            refitText(this.getText().toString(), w);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int parentWidth = MeasureSpec.getSize(widthMeasureSpec);
        refitText(this.getText().toString(), parentWidth);
    }

    public float getMinTextSize() {
        return minTextSize;
    }

    public void setMinTextSize(int minTextSize) {
        this.minTextSize = minTextSize;
    }

    public float getMaxTextSize() {
        return maxTextSize;
    }

    public void setMaxTextSize(int minTextSize) {
        this.maxTextSize = minTextSize;
    }

    private float minTextSize;
    private float maxTextSize;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
