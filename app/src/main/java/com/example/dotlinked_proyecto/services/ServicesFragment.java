package com.example.dotlinked_proyecto.services;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.example.dotlinked_proyecto.API.Class.Token;
import com.example.dotlinked_proyecto.Persistence.Session;
import com.example.dotlinked_proyecto.R;
import com.example.dotlinked_proyecto.Utils.Util;
import com.example.dotlinked_proyecto.appServices.ServicesCompanyService;
import com.example.dotlinked_proyecto.bean.Appointment;
import com.example.dotlinked_proyecto.bean.Person;
import com.example.dotlinked_proyecto.services.Adapter.RecyclerViewServicesAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

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
  private RecyclerView rvServices;
  private Context context;

  private SimpleDateFormat df;
  private FloatingActionButton floatingActionButton;
  private ServicesCompanyService companyService;

  private Session session;
  private List<Person> personList;
  private List<Appointment> appointmentList;
  private SimpleDateFormat dateFormat;
  private Calendar cal;
  private RecyclerViewServicesAdapter adapter;


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
    dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    session = new Session(context);
    session.deleteAppointmentsOfUser();
    personList = new ArrayList<>();
    appointmentList = new ArrayList<>();
    companyService = new ServicesCompanyService();
    cal = Calendar.getInstance();

  }

  @SuppressWarnings("NullableProblems")
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {


    View view = inflater.inflate(R.layout.fragment_services, container, false);
    mCalendarView = view.findViewById(R.id.servicesCalendarView);
    mCalendarView.isInEditMode();
    mCalendarView.setFocusableInTouchMode(true);
    tvDateDay = view.findViewById(R.id.btn_select_day);
    tvDateDay.setText(df.format(new Date()));
    rvServices = view.findViewById(R.id.rv_services_calendar);
    LinearLayoutManager layoutManager = new LinearLayoutManager(context);
    rvServices.setLayoutManager(layoutManager);
    DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvServices.getContext(),
            layoutManager.getOrientation());
    dividerItemDecoration.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.divider_recycler)));
    rvServices.addItemDecoration(dividerItemDecoration);
    setRecyclerViewAdapter(new ArrayList<>());
    floatingActionButton = view.findViewById(R.id.floatingActionButton);

    cal = mCalendarView.getCurrentPageDate();
    Date currentDay = cal.getTime();
    String strDate = dateFormat.format(currentDay);
    getReservedServicesOfUser(strDate);

    floatingActionButton.setOnClickListener(view1 -> {
      Intent intent = new Intent(getActivity(), ServicePreviewOrderActivity.class);
      if (rol.equals(Objects.requireNonNull(getActivity()).getString(R.string.rol_contact))) {
        String rolUnTranslate = Util.unTranslateRoles(getActivity(), rol);
        Call<List<Person>> call = companyService.listTenantByContact(companyId, rolUnTranslate, "bearer " + access_token);
        call.enqueue(new Callback<List<Person>>() {
          @Override
          public void onResponse(Call<List<Person>> call, Response<List<Person>> response) {
            if (response.body() != null && response.body().size() > 0) {
              personList = response.body();
              session.setListTenantsForContact(personList);
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
        startActivity(intent);
      }
    });

    mCalendarView.setOnPreviousPageChangeListener(() -> {
      cal = mCalendarView.getCurrentPageDate();
      Date d = cal.getTime();
      String strd = dateFormat.format(d);
      getReservedServicesOfUser(strd);
      setRecyclerViewAdapter(new ArrayList<>());
    });

    mCalendarView.setOnForwardPageChangeListener(() -> {
      cal = mCalendarView.getCurrentPageDate();
      Date d = cal.getTime();
      String strd = dateFormat.format(d);
      getReservedServicesOfUser(strd);
      setRecyclerViewAdapter(new ArrayList<>());
    });
    mCalendarView.setOnDayClickListener(this::previewService);
    return view;
  }

  private void setRecyclerViewAdapter(List<Appointment> appointments) {
    adapter = new RecyclerViewServicesAdapter(context, appointments);
    rvServices.setAdapter(adapter);
  }

  private List<Appointment> setRecyclerViewAdapterToDay(String date) {
    List<Appointment> tmpServiceList;
    tmpServiceList = appointmentList.stream()
        .filter(s -> s.getDateFrom().contains(date))
        .collect(Collectors.toList());
    setRecyclerViewAdapter(tmpServiceList);
    adapterSetOnClickListener(tmpServiceList);
    return tmpServiceList;
  }

  @SuppressWarnings("NullableProblems")
  private void getReservedServicesOfUser(String dateInit) {
    try {
      cal.setTime(Objects.requireNonNull(dateFormat.parse(dateInit)));
    } catch (ParseException e) {
      e.printStackTrace();
    }
    cal.add(Calendar.MONTH, 1);
    Date d = cal.getTime();
    String dateEnd = dateFormat.format(d);
    Call<List<Appointment>> call = companyService.getReservedServiceOfUser(rol, companyId, dateInit, dateEnd, "bearer " + access_token);
    call.enqueue(new Callback<List<Appointment>>() {
      @Override
      public void onResponse(Call<List<Appointment>> call, Response<List<Appointment>> response) {
        if (response.body() != null && response.body().size() > 0) {
          appointmentList = response.body();
          session.setAppointmentsOfUser(appointmentList);
          for(Appointment app: appointmentList)
            d("RESPONSE", "Appointments user: " + app.toString());
          Calendar cal = Calendar.getInstance();
          String date = df.format(cal.getTime());
          setRecyclerViewAdapterToDay(date);
        } else {
          Toast.makeText(context, getString(R.string.no_services_data), Toast.LENGTH_LONG).show();
        }
      }

      @Override
      public void onFailure(Call<List<Appointment>> call, Throwable t) {
        d("RESPONSE", "Error getReservedServicesOfUser: " + t.getCause());
      }
    });
  }

  private void adapterSetOnClickListener(List<Appointment> tmpServiceList) {
    adapter.setClickListener((view, position) -> {
      Appointment ser = tmpServiceList.get(position);
      Intent intent = new Intent(getActivity(), ServiceDetailActivity.class);
      intent.putExtra("service", new Gson().toJson(ser));
      Objects.requireNonNull(getActivity()).startActivity(intent);
    });
  }

  @RequiresApi(api = Build.VERSION_CODES.N)
  private void previewService(EventDay eventDay) {
    appointmentList = session.getAppointmentsOfUser();
    String date = df.format(eventDay.getCalendar().getTime());
    tvDateDay.setText(date);
    String datePet = dateFormat.format(eventDay.getCalendar().getTime());
    List<Appointment> tmpServiceList = setRecyclerViewAdapterToDay(datePet);
    adapterSetOnClickListener(tmpServiceList);

  }
}
