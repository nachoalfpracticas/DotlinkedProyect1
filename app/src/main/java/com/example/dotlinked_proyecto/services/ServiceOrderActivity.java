package com.example.dotlinked_proyecto.services;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;

import com.example.dotlinked_proyecto.Persistence.Session;
import com.example.dotlinked_proyecto.R;
import com.example.dotlinked_proyecto.Utils.UtilMessages;
import com.example.dotlinked_proyecto.appServices.ServicesCompanyService;
import com.example.dotlinked_proyecto.bean.Company;
import com.example.dotlinked_proyecto.bean.Person;
import com.example.dotlinked_proyecto.bean.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.util.Log.d;

public class ServiceOrderActivity extends AppCompatActivity {
  private Session session;
  private List<Service> serviceList;
  private ServicesCompanyService companyService;
  private Spinner spnServices;
  SpinnerTenantsAdapter adapter;
  private Spinner spnTenants;
  private TextView tvTenants;

  private String serviceSelectedName;
  private String serviceSelectedId;
  private AppCompatButton btnSelectService;
  private List<Person> personList;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_service_order);
    session = new Session(this);
    companyService = new ServicesCompanyService();
    personList = session.getTenantsForContact();

    btnSelectService = findViewById(R.id.btn_select_service);
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
          Person user = adapter.getItem(position);
          // Here you can do the action you want to...
          Toast.makeText(ServiceOrderActivity.this,
                  "ID: " + Objects.requireNonNull(user).getPersonId() + "\nName: " + user.getName(),
                  Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapter) {
        }
      });


    } else {
      spnTenants.setVisibility(View.GONE);
      tvTenants.setVisibility(View.GONE);
    }

    getServicesByCompany();

    btnSelectService.setOnClickListener(view -> {

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
          ArrayAdapter adapter = new ArrayAdapter<>(ServiceOrderActivity.this, android.R.layout.simple_spinner_item, servicesName);
          adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
          spnServices.setAdapter(adapter);
          spnServices.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
              serviceSelectedName = serviceList.get(pos).getServiceName();
              serviceSelectedId = String.valueOf(serviceList.get(pos).getServiceId());
              Toast.makeText(getApplicationContext(), String.format(getString(R.string.select_item), serviceSelectedName),
                  Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
          });
          btnSelectService.setEnabled(true);

        } else {
          UtilMessages.showLoadDataError(ServiceOrderActivity.this, response.message());
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
  public void onBackPressed() {
    finish();
  }
}
