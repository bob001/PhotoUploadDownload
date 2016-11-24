package com.qiu.photo_videotest.photo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.qiu.photo_videotest.R;

/**
 * Created by lenovo on 2016/11/21.
 */

public class View_TakePhoto extends View {
    private int shapeColor;
    private boolean isDisplay;
    private Paint paint;

    public View_TakePhoto(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupAttributes(attrs);
        setupPaint();
    }

    private void setupAttributes(AttributeSet attrs) {
        // Obtain a typed array of attributes
        TypedArray a = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.TakePhotoView, 0, 0);
        // Extract custom attributes into member variables
        try {
            shapeColor = a.getColor(R.styleable.TakePhotoView_shapeColor, Color.BLACK);
            isDisplay = a.getBoolean(R.styleable.TakePhotoView_isDisplay,false);
        } finally {
            // TypedArray objects are shared and must be recycled.
            a.recycle();
        }
    }

    private void setupPaint() {
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(shapeColor);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(0, 0, 0, 10, paint);
        if (isDisplay) {

        }
    }

    public int getShapeColor() {
        return shapeColor;
    }

    public void setShapeColor(int shapeColor) {
        this.shapeColor = shapeColor;
    }

    public boolean isDisplay() {
        return isDisplay;
    }

    public void setDisplay(boolean display) {
        isDisplay = display;
    }
}
