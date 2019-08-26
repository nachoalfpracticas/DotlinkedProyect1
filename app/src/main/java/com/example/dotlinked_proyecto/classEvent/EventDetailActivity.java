package com.example.dotlinked_proyecto.classEvent;

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

public class EventDetailActivity extends AppCompatActivity {

  public static String getFormattedDate(Date date) {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
    return simpleDateFormat.format(date);
  }

  @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_event_detail);

    Intent intent = getIntent();

    Toolbar toolbar = findViewById(R.id.toolbar_event_detail);
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
        String eventDay = getFormattedDate(myEventDay.getFechaDesde());
        setTitle(eventDay);
        evTitle.setText(myEventDay.getTitulo());
        evDes.setText(myEventDay.getDescripcion());
        evLocation.setText(myEventDay.getUbicacion());
        evInitTime.setText(String.valueOf(myEventDay.getHoraDesde()));
        evEndTime.setText(String.valueOf(myEventDay.getHoraHasta()));
        evCost.setText(String.valueOf(myEventDay.getCosto()));
       /* int imgInt = myEventDay.getImageResource();
        imgView.setImageResource(imgInt);*/

        return;
      }

      if (event instanceof EventDay) {
        EventDay eventDay = (EventDay) event;
        toolbar.setTitle(getFormattedDate(eventDay.getCalendar().getTime()));
      }
    }
  }
}

