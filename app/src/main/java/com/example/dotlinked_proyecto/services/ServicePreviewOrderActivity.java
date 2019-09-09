package com.example.dotlinked_proyecto.services;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;

import com.example.dotlinked_proyecto.Persistence.Session;
import com.example.dotlinked_proyecto.R;
import com.example.dotlinked_proyecto.Utils.Util;
import com.example.dotlinked_proyecto.Utils.UtilMessages;
import com.example.dotlinked_proyecto.appServices.ServicesCompanyService;
import com.example.dotlinked_proyecto.bean.Appointment;
import com.example.dotlinked_proyecto.bean.Company;
import com.example.dotlinked_proyecto.bean.Person;
import com.example.dotlinked_proyecto.bean.Service;
import com.example.dotlinked_proyecto.services.Adapter.SpinnerTenantsAdapter;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.util.Log.d;

public class ServicePreviewOrderActivity extends AppCompatActivity {
  SpinnerTenantsAdapter adapter;
  private Session session;
  private List<Service> serviceList;
  private ServicesCompanyService companyService;
  private Spinner spnServices;
  private Spinner spnTenants;
  private TextView tvTenants;

  private Service serviceSelected;
  private AppCompatButton btnSelectService;
  private Person person;
  private List<Appointment> appointmentList;

  @RequiresApi(api = Build.VERSION_CODES.N)
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_service_preview_order);
    session = new Session(this);
    companyService = new ServicesCompanyService();
    List<Person> personList = session.getTenantsForContact();

    Toolbar toolbar = findViewById(R.id.toolbar_service_preview_order);
    setSupportActionBar(toolbar);
    Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setDisplayShowHomeEnabled(true);
    setTitle(getString(R.string.select_service));

    btnSelectService = findViewById(R.id.btn_select_service_other_day);
    btnSelectService.setEnabled(false);
    AppCompatTextView tvCompanyName = findViewById(R.id.tv_company_name);
    spnServices = findViewById(R.id.spn_services);
    spnTenants = findViewById(R.id.spn_tenants);
    tvTenants = findViewById(R.id.lbl_spinner_tenants);

    List<Company> companies = session.getCompaniesUserByRol();
    for (Company company : companies) {
      if (company.getCompanyId() == Integer.parseInt(session.getCompanyIdUser()))
        tvCompanyName.setText(company.getCompanyName());
    }

    if (personList.size() > 0) {
      adapter = new SpinnerTenantsAdapter(this, android.R.layout.simple_spinner_item, personList);
      adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
      spnTenants.setAdapter(adapter);
      // You can create an anonymous listener to handle the event when is selected an spinner item
      spnTenants.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view,
                                   int position, long id) {
          // Here you get the current item (a User object) that is selected by its position
          person = adapter.getItem(position);
          session.setTenantSelect(person);
          // Here you can do the action you want to...
          Toast.makeText(ServicePreviewOrderActivity.this,
              "ID: " + Objects.requireNonNull(person).getPersonId() + "\nName: " + person.getName(),
              Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapter) {
        }
      });


    } else {
      spnTenants.setVisibility(View.GONE);
      tvTenants.setVisibility(View.GONE);
      person = session.getTenantSelect();
    }

    getServicesByCompany();

    btnSelectService.setOnClickListener(view -> {
      appointmentList = session.getAppointmentsOfUser();
      if (appointmentList != null) {
        List<Appointment> appointmentListTemp = appointmentList.stream()
            .filter(a -> a.getServiceId().equals(Integer.valueOf(serviceSelected.getServiceId()))
                && Util.converDate(a.getDateFrom()).after(new Date()))
            .collect(Collectors.toList());
        if (appointmentListTemp.size() > 0) {
          UtilMessages.showAppointmentInfo(ServicePreviewOrderActivity.this,
              serviceSelected,
              appointmentListTemp.get(0),
              appointmentListTemp.get(0).getServiceName(),
              appointmentListTemp.get(0).getDateFrom().split("T")[0],
              appointmentListTemp.get(0).getDateFrom().split("T")[1]);
          return;
        }
      }
      Intent intent = new Intent(this, ServiceOrderActivity.class);
      intent.putExtra("serviceSelected", new Gson().toJson(serviceSelected));
      startActivity(intent);
    });
  }

  @SuppressWarnings("NullableProblems")
  private void getServicesByCompany() {
    List<String> servicesName = new ArrayList<>();
    Call<List<Service>> call = companyService.getServicesByCompanyId(session.getCompanyIdUser(),
            "bearer " + session.getToken().getAccess_token());
    call.enqueue(new Callback<List<Service>>() {
      @Override
      public void onResponse(Call<List<Service>> call, Response<List<Service>> response) {
        if (response.body() != null && response.body().size() > 0) {
          serviceList = response.body();
          for (Service s : serviceList) {
            servicesName.add(s.getServiceName());
          }
          ArrayAdapter adapter = new ArrayAdapter<>(ServicePreviewOrderActivity.this, android.R.layout.simple_spinner_item, servicesName);
          adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
          spnServices.setAdapter(adapter);
          spnServices.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
              serviceSelected = serviceList.get(pos);
              Toast.makeText(getApplicationContext(), String.format(getString(R.string.select_item), serviceSelected.getServiceName()),
                      Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
          });
          btnSelectService.setEnabled(true);

        } else {
          UtilMessages.showLoadDataError(ServicePreviewOrderActivity.this, response.message());
          Log.d("RESPONSE", "Error response getServicesByCompany " + response.message());
        }
      }

      @Override
      public void onFailure(Call<List<Service>> call, Throwable t) {
        d("RESPONSE", "Error getServicesByCompany: " + t.getCause());
      }
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
