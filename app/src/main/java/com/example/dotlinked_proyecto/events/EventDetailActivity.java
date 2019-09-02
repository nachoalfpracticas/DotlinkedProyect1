package com.example.dotlinked_proyecto.events;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.applandeo.materialcalendarview.EventDay;
import com.example.dotlinked_proyecto.R;
import com.example.dotlinked_proyecto.bean.Event;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class EventDetailActivity extends AppCompatActivity {

  public String getFormattedDate(Date date) {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(this.getString(R.string.date_format), Locale.getDefault());
    return simpleDateFormat.format(date);
  }

  @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_event_detail);

    Intent intent = getIntent();

    Toolbar toolbar = findViewById(R.id.toolbar_event_detail);
    setSupportActionBar(toolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setDisplayShowHomeEnabled(true);
    TextView evTitle = findViewById(R.id.event_title);
    TextView evDes = findViewById(R.id.event_description);
    TextView evLocation = findViewById(R.id.event_location);
    TextView evInitTime = findViewById(R.id.event_initTime);
    TextView evEndTime = findViewById(R.id.event_endTime);
    TextView evCost = findViewById(R.id.event_cost);

    //AppCompatImageView imgView = findViewById(R.id.img_event);
    setSupportActionBar(toolbar);

    if (intent != null) {
      Object event = intent.getParcelableExtra(EventsCalendarFragment.EVENT);

      if (event instanceof Event) {
        Event myEventDay = (Event) event;
        String eventDay = myEventDay.getDateInit();
        setTitle(eventDay);
        evTitle.setText(myEventDay.getTitle());
        evDes.setText(myEventDay.getDescription());
        evLocation.setText(myEventDay.getLocation());
        evInitTime.setText(String.valueOf(myEventDay.getTimeFrom()));
        evEndTime.setText(String.valueOf(myEventDay.getTimeTo()));
        evCost.setText(String.valueOf(myEventDay.getCost()));

        return;
      }

      if (event instanceof EventDay) {
        EventDay eventDay = (EventDay) event;
        toolbar.setTitle(getFormattedDate(eventDay.getCalendar().getTime()));
      }
    }
  }

  @Override
  public void onBackPressed() {
    finish();
  }
}

