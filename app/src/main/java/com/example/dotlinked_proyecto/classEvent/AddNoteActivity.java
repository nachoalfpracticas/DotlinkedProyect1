package com.example.dotlinked_proyecto.classEvent;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.applandeo.materialcalendarview.CalendarView;
import com.example.dotlinked_proyecto.R;
import com.example.dotlinked_proyecto.bean.MyEventDay;

import java.util.Calendar;

public class AddNoteActivity extends AppCompatActivity {

  @RequiresApi(api = Build.VERSION_CODES.N)
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_add_note);

    final CalendarView datePicker = findViewById(R.id.datePicker);
    Button button = findViewById(R.id.addNoteButton);
    final EditText noteEditText = findViewById(R.id.noteEditText);


    datePicker.setOnDayClickListener(eventDay -> {
      try {
        Thread.sleep(500);
        datePicker.setVisibility(View.GONE);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    });

    button.setOnClickListener(v -> {
      Intent returnIntent = new Intent();
      Calendar cal = datePicker.getFirstSelectedDate();
      Log.d("APP", "Day: " + cal.getTime().toString() + " Note text: " + noteEditText.getText().toString());
      Toast.makeText(this, String.format(getString(R.string.select_item), cal.getTime().toString()), Toast.LENGTH_LONG).show();
      int r = R.drawable.sample_four_icons;
      MyEventDay myEventDay = new MyEventDay(cal, R.drawable.ic_message_black_48dp, noteEditText.getText().toString());

      returnIntent.putExtra(EventsCalendarFragment.RESULT, myEventDay);
      setResult(Activity.RESULT_OK, returnIntent);
      finish();
    });
  }
}
