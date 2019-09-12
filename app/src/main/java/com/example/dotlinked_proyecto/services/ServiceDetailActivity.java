package com.example.dotlinked_proyecto.services;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import com.example.dotlinked_proyecto.Persistence.Session;
import com.example.dotlinked_proyecto.R;
import com.example.dotlinked_proyecto.Utils.UtilMessages;
import com.example.dotlinked_proyecto.appServices.ServicesCompanyService;
import com.example.dotlinked_proyecto.bean.Appointment;
import com.google.gson.Gson;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.util.Log.d;

public class ServiceDetailActivity extends AppCompatActivity {

  private Appointment appointment;
  private Session session;
  private TextView tvServiceName;
  private TextView tvServiceDate;
  private TextView tvServiceLocation;
  private TextView tvServiceTimeStart;
  private TextView tvServiceTimeEnd;
  private TextView tvServiceCost;
  private AppCompatButton btnCancelAppointment;
  private AppCompatButton btnUpdateAppointment;

  private ServicesCompanyService companyService;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_service_detail);
    companyService = new ServicesCompanyService();

    session = new Session(this);
    Toolbar toolbar = findViewById(R.id.toolbar_service_detail);
    setSupportActionBar(toolbar);
    Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setDisplayShowHomeEnabled(true);

    Bundle bundle = getIntent().getExtras();
    if (bundle != null) {
      String strService = bundle.getString("service", "");
      appointment = new Gson().fromJson(strService, Appointment.class);
    }

    btnCancelAppointment = findViewById(R.id.btn_cancel_appointment);
    btnUpdateAppointment = findViewById(R.id.btn_update_appointment);
    btnCancelAppointment.setVisibility(View.GONE);
    btnUpdateAppointment.setVisibility(View.GONE);
    if (appointment.isPending()) {
      btnCancelAppointment.setVisibility(View.VISIBLE);
      btnUpdateAppointment.setVisibility(View.VISIBLE);
    }

    tvServiceName = findViewById(R.id.service_title);
    tvServiceDate = findViewById(R.id.service_date);
    tvServiceLocation = findViewById(R.id.service_location);
    tvServiceTimeStart = findViewById(R.id.service_initTime);
    tvServiceTimeEnd = findViewById(R.id.service_endTime);
    tvServiceCost = findViewById(R.id.service_cost);


    if (appointment != null) {
      String timeInit = appointment.getDateFrom().split("T")[1];
      String timeEnd = appointment.getDateTo().split("T")[1];
      tvServiceName.setText(appointment.getServiceName());

      DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
      Date date = null;
      try {
        date = formatter.parse(appointment.getDateFrom());
      } catch (ParseException e) {
        e.printStackTrace();
      }
      String patternFormat = getString(R.string.date_format);
      DateFormat df = new SimpleDateFormat(patternFormat, Locale.getDefault());
      assert date != null;
      String startDate = df.format(date);
      tvServiceDate.setText(startDate);
      tvServiceLocation.setText(appointment.getLocation());
      tvServiceTimeStart.setText(timeInit);
      tvServiceTimeEnd.setText(timeEnd);
      tvServiceCost.setText(String.format(getString(R.string.priceServiceEuro), String.valueOf(appointment.getCost())));

      setTitle(String.format(getString(R.string.service_Id), " : " + appointment.getServiceId()));

    } else {
      UtilMessages.showLoadDataError(this);
    }

    btnCancelAppointment.setOnClickListener(view -> {
      Call<String> call = companyService.deleteAppointment(
              appointment.getAppointmentId(),
              appointment.getServiceId(),
              appointment.getPersonId(),
              appointment.getDateFrom(),
              "bearer " + session.getToken().getAccess_token());
      call.enqueue(new Callback<String>() {
        @SuppressWarnings(value = "NullableProblems")
        @Override
        public void onResponse(Call<String> call, Response<String> response) {
          if (response.body() != null) {
            String res = response.body();
            Log.d("RESPONSE", response.body());
            UtilMessages.showResponseToCreateUpdateAppointment(ServiceDetailActivity.this, false, res + getString(R.string.delete));
          } else {
            UtilMessages.showLoadDataError(ServiceDetailActivity.this);
            try {
              Log.d("RESPONSE", getString(R.string.load_data_err) + " " + Objects.requireNonNull(response.errorBody()).string());
            } catch (IOException e) {
              Log.d("RESPONSE", "Error in deleteAppointment: " + Objects.requireNonNull(e.getMessage()));
            }
          }
        }

        @SuppressWarnings(value = "NullableProblems")
        @Override
        public void onFailure(Call<String> call, Throwable t) {
          UtilMessages.showLoadDataError(ServiceDetailActivity.this, t.getLocalizedMessage());
          d("RESPONSE", "Error deleteAppointment: " + t.getCause());
        }
      });
    });


    btnUpdateAppointment.setOnClickListener(view -> {

    });
  }

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

