package com.example.dotlinked_proyecto.services;

import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dotlinked_proyecto.Persistence.Session;
import com.example.dotlinked_proyecto.R;
import com.example.dotlinked_proyecto.appServices.ServicesCompanyService;
import com.example.dotlinked_proyecto.bean.Person;
import com.example.dotlinked_proyecto.bean.Service;
import com.example.dotlinked_proyecto.bean.ServiceInfo;
import com.example.dotlinked_proyecto.services.Adapter.RecyclerViewShceduleServiceAdapter;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServiceOrderActivity extends AppCompatActivity {


  private TextView tvService;
  private TextView tvUserName;
  private RecyclerView rcSchedules;
  private Session session;
  private Service service;
  private List<ServiceInfo> serviceInfoList;
  private ServicesCompanyService companyService;
  private SimpleDateFormat dateFormat;



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
    }
    setTitle(String.format(getString(R.string.service_Id), " : " + service.getServiceId()));

    tvUserName = findViewById(R.id.tv_user_name);
    tvService = findViewById(R.id.tv_service);
    rcSchedules = findViewById(R.id.rv_services_schedules);
    LinearLayoutManager layoutManager = new LinearLayoutManager(this);
    rcSchedules.setLayoutManager(layoutManager);
    DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rcSchedules.getContext(),
            layoutManager.getOrientation());
    dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.divider_recycler));
    rcSchedules.addItemDecoration(dividerItemDecoration);
    setRecyclerViewAdapter(new ArrayList<>());

    tvService.setText(service.getServiceName());
    tvUserName.setText(session.getTenantSelect().getFullName());

    Calendar cal = Calendar.getInstance();
    String date = dateFormat.format(cal.getTime());
    getAvailableSchedulesToServices(date);

  }

  @SuppressWarnings("NullableProblems")
  private void getAvailableSchedulesToServices(String date) {
    // String companyId, String serviceId, String date, String personId, String token) {
    String companyId = session.getCompanyIdUser();
    String serviceId = service.getServiceId();
    Person person = session.getTenantSelect();
    String token = session.getToken().getAccess_token();

    Call<List<ServiceInfo>> call = companyService.listScheduleService(companyId, serviceId, date, String.valueOf(person.getPersonId()), "bearer " + token);
    call.enqueue(new Callback<List<ServiceInfo>>() {
      @RequiresApi(api = Build.VERSION_CODES.N)
      @Override
      public void onResponse(Call<List<ServiceInfo>> call, Response<List<ServiceInfo>> response) {
        if (response.body() != null && response.body().size() > 0) {
          serviceInfoList = response.body();
          serviceInfoList.forEach(ser -> ser.setDateInit(date));
          setRecyclerViewAdapter(serviceInfoList);
        }
      }

      @Override
      public void onFailure(Call<List<ServiceInfo>> call, Throwable t) {

      }
    });
  }

  private void setRecyclerViewAdapter(List<ServiceInfo> serviceInfoList) {
    RecyclerViewShceduleServiceAdapter adapter = new RecyclerViewShceduleServiceAdapter(this, serviceInfoList);
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
