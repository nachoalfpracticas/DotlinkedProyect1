package com.example.dotlinked_proyecto.services;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dotlinked_proyecto.Persistence.Session;
import com.example.dotlinked_proyecto.R;
import com.example.dotlinked_proyecto.Utils.Util;
import com.example.dotlinked_proyecto.Utils.UtilMessages;
import com.example.dotlinked_proyecto.appServices.ServicesCompanyService;
import com.example.dotlinked_proyecto.bean.Appointment;
import com.example.dotlinked_proyecto.bean.AppointmentNewOrUpdate;
import com.example.dotlinked_proyecto.bean.Person;
import com.example.dotlinked_proyecto.bean.Service;
import com.example.dotlinked_proyecto.bean.ServiceInfo;
import com.example.dotlinked_proyecto.services.Adapter.RecyclerViewShceduleServiceAdapter;
import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.util.Log.d;

public class ServiceOrderActivity extends AppCompatActivity {


  private TextView tvServiceLocation;
  private TextView tvUserName;
  private AppCompatButton btnSelectDate;
  private RecyclerView rcSchedules;
  private Session session;
  private Service service;
  private ServiceInfo serviceInfo;
  private Appointment appointment;
  private List<ServiceInfo> serviceInfoList;
  private ServicesCompanyService companyService;
  private SimpleDateFormat dateFormat;
  private RecyclerViewShceduleServiceAdapter adapter;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_service_order);
    session = new Session(this);
    companyService = new ServicesCompanyService();
    dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    Toolbar toolbar = findViewById(R.id.toolbar_service_order);
    setSupportActionBar(toolbar);
    Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setDisplayShowHomeEnabled(true);


    Bundle bundle = getIntent().getExtras();
    if (bundle != null) {
      service = new Gson().fromJson(bundle.getString("serviceSelected"), Service.class);
      appointment = new Gson().fromJson(bundle.getString("appointment"), Appointment.class);
    }
    if (appointment == null)
      toolbar.setTitle(String.format(getString(R.string.service_name), ": " + service.getServiceName()));
    else {
      toolbar.setTitleTextAppearance(this, R.style.TextAppearance_AppCompat_Small);
      toolbar.setTitle(String.format(getString(R.string.change_appointment), service.getServiceName(), Util.formatDateToLocale(this, appointment.getDateFrom())));
    }

    btnSelectDate = findViewById(R.id.btn_select_service_other_day);
    tvUserName = findViewById(R.id.tv_user_name);
    tvServiceLocation = findViewById(R.id.tv_service_location);

    tvServiceLocation.setText(service.getLocation());
    tvUserName.setText(session.getTenantSelect().getFullName());

    rcSchedules = findViewById(R.id.rv_services_schedules);
    LinearLayoutManager layoutManager = new LinearLayoutManager(this);
    rcSchedules.setLayoutManager(layoutManager);
    DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rcSchedules.getContext(),
            layoutManager.getOrientation());
    dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.divider_recycler));
    rcSchedules.addItemDecoration(dividerItemDecoration);
    setRecyclerViewAdapter(new ArrayList<>());

    Calendar cal = Calendar.getInstance();
    String date = dateFormat.format(cal.getTime());
    getAvailableSchedulesToServices(date);

    btnSelectDate.setOnClickListener(v -> showCalendarSelectAppointmentDay());
  }

  @SuppressWarnings("NullableProblems")
  private void getAvailableSchedulesToServices(String date) {
    String companyId = session.getCompanyIdUser();
    String serviceId = service.getServiceId();
    Person person = session.getTenantSelect();
    String token = session.getToken().getAccess_token();
    boolean isNew = appointment == null;

    Call<List<ServiceInfo>> call = companyService.listScheduleService(companyId, serviceId, date, String.valueOf(person.getPersonId()), "bearer " + token);
    call.enqueue(new Callback<List<ServiceInfo>>() {
      @RequiresApi(api = Build.VERSION_CODES.N)
      @Override
      public void onResponse(Call<List<ServiceInfo>> call, Response<List<ServiceInfo>> response) {
        if (response.body() != null && response.body().size() > 0) {
          serviceInfoList = response.body();
          serviceInfoList.forEach(ser -> ser.setDateInit(date));
          setRecyclerViewAdapter(serviceInfoList);
          adapter.setClickListener((view, position) -> {
            serviceInfo = serviceInfoList.get(position);
            String dateFrom = Util.formatToDate(serviceInfo.getDateInit());
            String dateTimeFrom = dateFrom + "T" + serviceInfo.getHour() + ":00";
            AppointmentNewOrUpdate newOrUpdate =
                    new AppointmentNewOrUpdate(null,
                            Integer.valueOf(serviceId), person.getPersonId(), dateTimeFrom);
            if (!isNew) newOrUpdate.setAppointmentId(appointment.getAppointmentId());

            Call<String> callApp = companyService.createUpdateAppointment(
                    newOrUpdate.getAppointmentId(),
                    newOrUpdate.getServiceId(),
                    newOrUpdate.getPersonId(),
                    newOrUpdate.getDateFrom(),
                    "bearer " + session.getToken().getAccess_token());

            callApp.enqueue(new Callback<String>() {
              @Override
              public void onResponse(Call<String> call, Response<String> response) {
                if (response.body() != null) {
                  String res = response.body();
                  Log.d("RESPONSE", response.body());
                  UtilMessages.showResponseToCreateUpdateAppointment(ServiceOrderActivity.this, isNew, res);
                  if (res.toLowerCase().equals(getString(R.string.OK).toLowerCase())) {
                    Calendar c = Calendar.getInstance();
                    Date dateInit = Util.convertDate(date);
                    c.setTime(dateInit);
                    c.set(Calendar.DAY_OF_MONTH, 1);
                    Date d = c.getTime();
                    String strDateInit = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(d);
                    String dateInit2 = Util.formatDateToLocale(ServiceOrderActivity.this, strDateInit);
                    Util.updateReservedServicesOfUser(ServiceOrderActivity.this, dateInit2);
                  }
                } else {
                  UtilMessages.showLoadDataError(ServiceOrderActivity.this);
                  Log.d("RESPONSE", getString(R.string.save_data_err));
                }
              }

              @Override
              public void onFailure(Call<String> call, Throwable t) {
                UtilMessages.showLoadDataError(ServiceOrderActivity.this);
                d("RESPONSE", "Error createUpdateAppointment: " + t.getCause());
              }
            });
          });
        } else {
          String str = String.format(getString(R.string.without_dates_for_day_selected), Util.formatDateToLocale(ServiceOrderActivity.this, date));
          SpannableString spa = new SpannableString(str);
          int i = str.indexOf("@");
          Drawable d = getResources().getDrawable(R.drawable.claim_icon);
          d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
          spa.setSpan(new ImageSpan(d), i, i + 1, ImageSpan.ALIGN_BOTTOM);

          NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(ServiceOrderActivity.this);
          dialogBuilder
              .withTitle("   " + getString(R.string.appointment_info))
              .withIcon(R.drawable.ic_dates_icon_white)
              .withDividerColor(R.color.daysLabelColor)
              .withMessage(spa)
              .withMessageColor("#FAD201")
              .withDialogColor(R.color.blueDotlinked)
              .withDuration(700)
              .withButton1Text(getString(R.string.OK))
              .withButton2Text(getString(R.string.cancel))
              .withEffect(Effectstype.RotateBottom)
              .isCancelableOnTouchOutside(false)
                  .setButton1Click(v -> dialogBuilder.dismiss())
              .setButton2Click(view -> {
                finish();
                dialogBuilder.dismiss();
              })
              .show();
        }
      }

      @Override
      public void onFailure(Call<List<ServiceInfo>> call, Throwable t) {
        d("RESPONSE", "Error getAvailableSchedulesToServices: " + t.getCause());
      }
    });
  }

  @SuppressLint("NewApi")
  private void showCalendarSelectAppointmentDay() {
    NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(ServiceOrderActivity.this);
    View view = getLayoutInflater().inflate(getResources().getLayout(R.layout.calendar_select_appointment), null);
    DatePicker dpSelectDay = view.findViewById(R.id.calendar_select_day);

    dpSelectDay.setOnDateChangedListener((datePicker, i, i1, i2) -> {
      int day = datePicker.getDayOfMonth();
      int month = datePicker.getMonth();
      int year = datePicker.getYear();
      String date = year + "-" + (month + 1) + "-" + day;
      String dateFormat = Util.formatDateToLocale(ServiceOrderActivity.this, date);
      Toast.makeText(ServiceOrderActivity.this, String.format(getString(R.string.select_day), dateFormat), Toast.LENGTH_LONG).show();
      getAvailableSchedulesToServices(date);
      dialogBuilder.dismiss();
    });

    dialogBuilder.withEffect(Effectstype.Fadein)
            .withDuration(700)
            .withMessageColor("#FAD201")
            .withDialogColor(R.color.blueDotlinked)
            .withDividerColor(R.color.daysLabelColor)
            .isCancelable(false)
            .isCancelableOnTouchOutside(false)
            .setCustomView(view, ServiceOrderActivity.this)
            .show();
  }

  private void setRecyclerViewAdapter(List<ServiceInfo> serviceInfoList) {
    adapter = new RecyclerViewShceduleServiceAdapter(this, serviceInfoList);
    rcSchedules.setAdapter(adapter);
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
