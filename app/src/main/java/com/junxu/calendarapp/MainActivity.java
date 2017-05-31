package com.junxu.calendarapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements CalendarLayout.CalendarListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CalendarLayout calendarLayout = (CalendarLayout) findViewById(R.id.calendar_layout);
        calendarLayout.calendarListener = this;
    }

    @Override
    public void onItemLongPress(Date date) {
        DateFormat df = SimpleDateFormat.getDateInstance();
        Toast.makeText(this,df.format(date),Toast.LENGTH_LONG).show();
    }
}
