package com.example.dotlinked_proyecto.services;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.example.dotlinked_proyecto.API.Class.Token;
import com.example.dotlinked_proyecto.Persistence.Session;
import com.example.dotlinked_proyecto.R;
import com.example.dotlinked_proyecto.appServices.ServicesCompanyService;
import com.example.dotlinked_proyecto.bean.Person;
import com.example.dotlinked_proyecto.events.Adapter.RecyclerViewEventsAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.util.Log.d;


public class ServicesFragment extends Fragment {
  private static final String ARG_ROL = "rol";
  private static final String ARG_COMPANY_ID = "companyId";
  private static final String ARG_TOKEN = "token";

  private String rol;
  private String companyId;
  private String access_token;

  private CalendarView mCalendarView;
  private AppCompatTextView tvDateDay;
  private RecyclerView rcDates;
  private Context context;

  private RecyclerViewEventsAdapter adapter;
  private SimpleDateFormat df;
  private FloatingActionButton floatingActionButton;
  private ServicesCompanyService companyService;

  private Session session;
  private List<Person> personList;


  public ServicesFragment() {
    // Required empty public constructor
  }

  public static ServicesFragment newInstance(String rol, String companyId, Token token) {
    ServicesFragment fragment = new ServicesFragment();
    Bundle args = new Bundle();
    args.putString(ARG_TOKEN, token.getAccess_token());
    args.putString(ARG_ROL, rol);
    args.putString(ARG_COMPANY_ID, companyId);
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      access_token = getArguments().getString(ARG_TOKEN);
      rol = getArguments().getString(ARG_ROL);
      companyId = getArguments().getString(ARG_COMPANY_ID);
    }
    context = getContext();
    df = new SimpleDateFormat(Objects.requireNonNull(getActivity()).getString(R.string.date_format), Locale.getDefault());
    session = new Session(context);
    personList = new ArrayList<>();
    companyService = new ServicesCompanyService();

  }

  @SuppressWarnings("NullableProblems")
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {

    View view = inflater.inflate(R.layout.fragment_services, container, false);
    mCalendarView = view.findViewById(R.id.servicesCalendarView);
    mCalendarView.isInEditMode();
    mCalendarView.setFocusableInTouchMode(true);
    tvDateDay = view.findViewById(R.id.tv_select_day);
    tvDateDay.setText(df.format(new Date()));
    rcDates = view.findViewById(R.id.rv_dates_calendar);
    floatingActionButton = view.findViewById(R.id.floatingActionButton);

    getReservedServicesOfUser();

    floatingActionButton.setOnClickListener(view1 -> {
      if (rol.equals(Objects.requireNonNull(getActivity()).getString(R.string.rol_contact))) {
        Call<List<Person>> call = companyService.listTenantByContact(companyId, "bearer " + access_token);
        call.enqueue(new Callback<List<Person>>() {
          @Override
          public void onResponse(Call<List<Person>> call, Response<List<Person>> response) {
            if (response.body() != null && response.body().size() > 0) {
              personList = response.body();
              session.setListTenantsForContact(personList);
              Intent intent = new Intent(getActivity(), ServiceOrderActivity.class);
              startActivity(intent);
            } else
              Toast.makeText(context, getString(R.string.no_tenants_for_contact), Toast.LENGTH_LONG).show();
          }

          @Override
          public void onFailure(Call<List<Person>> call, Throwable t) {
            d("RESPONSE", "Error getPersonClaims: " + t.getCause());
          }
        });

      } else {
        session.setListTenantsForContact(new ArrayList<>());
      }
    });
    mCalendarView.setOnDayClickListener(this::previewEvent);

    return view;
  }

  private void getReservedServicesOfUser() {

  }

  private void previewEvent(EventDay eventDay) {

  }

}
