package com.junxu.calendarapp;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by linxu on 2017/5/31.
 */

public class CalendarLayout extends LinearLayout {

    private ImageView btnPrev;
    private ImageView btnNext;
    private TextView txtDate;
    private GridView grid;

    private Calendar calDate = Calendar.getInstance();
    private String displyFormat;
    public CalendarListener calendarListener;

    public CalendarLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initControl(context,attrs);
    }

    public CalendarLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initControl(context,attrs);
    }

    public CalendarLayout(Context context) {
        super(context);
    }
    private void initControl(Context context,AttributeSet arrts){
        bindControl(context);

        TypedArray ta = getContext().obtainStyledAttributes(arrts,R.styleable.CalendarLayout);
        try {
            String format = ta.getString(R.styleable.CalendarLayout_dateFormat);
            if (displyFormat == null){
                displyFormat = "MMMM yyyy";
            }
        }finally {
            ta.recycle();
        }
        bindControlEvent();
        renderCalendar();

    }

    private void bindControl(Context context){
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.calendar_layout,this);

        btnPrev = (ImageView) findViewById(R.id.btnPrev);
        btnNext = (ImageView) findViewById(R.id.btnNext);
        txtDate = (TextView) findViewById(R.id.txtDate);
        grid = (GridView) findViewById(R.id.calendar_grid);
        grid.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if(calendarListener == null){
                    return false;
                }else{
                    calendarListener.onItemLongPress((Date) parent.getItemAtPosition(position));
                    return true;
                }
            }
        });
    }

    private void bindControlEvent(){
        btnPrev.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                calDate.add(Calendar.MONTH,-1);
                renderCalendar();
            }
        });
        btnNext.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                calDate.add(Calendar.MONTH,1);
                renderCalendar();
            }
        });
    }
    private void  renderCalendar(){
        SimpleDateFormat sdf = new SimpleDateFormat(displyFormat);
        txtDate.setText(sdf.format(calDate.getTime()));
        ArrayList<Date> cells = new ArrayList<>();
        Calendar calendar = (Calendar) calDate.clone();

        calendar.set(Calendar.DAY_OF_MONTH,1);
        int prevDays = calendar.get(Calendar.DAY_OF_WEEK)-1;

        calendar.add(Calendar.DAY_OF_MONTH,-prevDays);

        int maxCellCount = 6*7;

        while (cells.size()<maxCellCount){
            cells.add(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH,1);
        }

        grid.setAdapter(new CalendarAdapter(getContext(),cells));
    }
    private class CalendarAdapter extends ArrayAdapter<Date>{

        LayoutInflater inflater;
        public CalendarAdapter(Context context,  ArrayList<Date> days) {
            super(context, R.layout.calendar_text_day,days);
            inflater = LayoutInflater.from(context);
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            Date date = getItem(position);

            if(convertView==null){
                convertView = inflater.inflate(R.layout.calendar_text_day,parent,false);
            }
            int day = date.getDate();
            ((TextView)convertView).setText(String.valueOf(day));
            Date now = new Date();
            boolean isTheSameMonth = false;
            if(date.getMonth() == now.getMonth()){
                isTheSameMonth = true;
            }
            if(isTheSameMonth){
                ((TextView)convertView).setTextColor(Color.parseColor("#000000"));
            }else{
                ((TextView)convertView).setTextColor(Color.parseColor("#666666"));
            }
            if(date.getDate() == now.getDate()&&date.getMonth() == now.getMonth()&&date.getYear() == now.getYear()){
                ((CalendarDayTextViw)convertView).setTextColor(Color.parseColor("#ff0000"));
                ((CalendarDayTextViw)convertView).isToday=true;

            }

            return convertView;
        }
    }
    public interface CalendarListener{
        void onItemLongPress(Date date);
    }
}
