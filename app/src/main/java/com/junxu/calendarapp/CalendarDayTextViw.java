package com.junxu.calendarapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by linxu on 2017/5/31.
 */

public class CalendarDayTextViw extends TextView {
    public boolean isToday=false;
    private Paint paint = new Paint();
    public CalendarDayTextViw(Context context, AttributeSet attrs) {
        super(context, attrs);
        initControl();
    }

    public CalendarDayTextViw(Context context) {
        super(context);
    }

    public CalendarDayTextViw(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initControl();
    }

    private void initControl(){
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.parseColor("#ff0000"));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(isToday) {
            canvas.translate(getWidth() / 2, getHeight() / 2);
            canvas.drawCircle(0, 0, getWidth() / 2, paint);
        }
    }
}
