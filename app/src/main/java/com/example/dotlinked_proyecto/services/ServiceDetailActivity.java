package com.example.dotlinked_proyecto.services;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.dotlinked_proyecto.Persistence.Session;
import com.example.dotlinked_proyecto.R;
import com.example.dotlinked_proyecto.bean.Service;
import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class ServiceDetailActivity extends AppCompatActivity {

  private Service service;
  private Session session;
  private TextView tvServiceName;
  private TextView tvServiceDate;
  private TextView tvServiceLocation;
  private TextView tvServiceTimeStart;
  private TextView tvServiceTimeEnd;
  private TextView tvServiceCost;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_service_detail);

    session = new Session(this);
    Toolbar toolbar = findViewById(R.id.toolbar_service_detail);
    setSupportActionBar(toolbar);
    Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setDisplayShowHomeEnabled(true);

    Bundle bundle = getIntent().getExtras();
    if (bundle != null) {
      String strService = bundle.getString("service", "");
      service = new Gson().fromJson(strService, Service.class);
    }

    tvServiceName = findViewById(R.id.service_title);
    tvServiceDate = findViewById(R.id.service_date);
    tvServiceLocation = findViewById(R.id.service_location);
    tvServiceTimeStart = findViewById(R.id.service_initTime);
    tvServiceTimeEnd = findViewById(R.id.service_endTime);
    tvServiceCost = findViewById(R.id.service_cost);


    if (service != null) {
      String timeInit = service.getDateInit().split("T")[1];
      String timeEnd = service.getDateEnd().split("T")[1];
      tvServiceName.setText(service.getServiceName());

      DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
      Date date = null;
      try {
        date = formatter.parse(service.getDateInit());
      } catch (ParseException e) {
        e.printStackTrace();
      }
      String patternFormat = getString(R.string.date_format);
      DateFormat df = new SimpleDateFormat(patternFormat, Locale.getDefault());
      assert date != null;
      String startDate = df.format(date);
      tvServiceDate.setText(startDate);
      tvServiceLocation.setText(service.getLocation());
      tvServiceTimeStart.setText(timeInit);
      tvServiceTimeEnd.setText(timeEnd);
      tvServiceCost.setText(String.format(getString(R.string.priceServiceEuro), String.valueOf(service.getCost())));

      setTitle(String.format(getString(R.string.service_Id), " : " + service.getServiceId()));

    }


  }

  // https://es.stackoverflow.com/questions/8387/bóton-de-atrás-en-el-título-de-la-activity
  @Override
  public boolean onSupportNavigateUp() {
    onBackPressed();
    return false;
  }

  @Override
  public void onBackPressed() {
    finish();
  }

}

