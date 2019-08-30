package com.example.dotlinked_proyecto.events;

import android.app.ProgressDialog;
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
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.exceptions.OutOfDateRangeException;
import com.example.dotlinked_proyecto.API.Class.Token;
import com.example.dotlinked_proyecto.R;
import com.example.dotlinked_proyecto.bean.Day;
import com.example.dotlinked_proyecto.bean.Event;
import com.example.dotlinked_proyecto.bean.MyEventDay;
import com.example.dotlinked_proyecto.events.Adapter.RecyclerViewEventsAdapter;
import com.example.dotlinked_proyecto.services.ListEventsByCompanyService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;
import static android.util.Log.d;


public class EventsCalendarFragment extends Fragment {
  static final String RESULT = "result";
  static final String EVENT = "event";

  private static final String ARG_ROL = "rol";
  private static final String ARG_COMPANY_ID = "companyId";
  private static final String ARG_TOKEN = "token";


  private static final int ADD_NOTE = 44;

  private String rol;
  private String companyId;
  private String access_token;
  private ListEventsByCompanyService companyService;
  private List<EventDay> mEventDays = new ArrayList<>();
  private List<Event> eventList;
  private List<Event> allEventsByCompanyList;
  private ProgressDialog progressDialog;
  private Context context;

  private CalendarView mCalendarView;
  private AppCompatTextView tvEventDay;
  private RecyclerView rcEvents;

  private RecyclerViewEventsAdapter adapter;
  private SimpleDateFormat df;
  public EventsCalendarFragment() {
    // Required empty public constructor
  }


  public static EventsCalendarFragment newInstance(String rol, String companyId, Token token) {
    EventsCalendarFragment fragment = new EventsCalendarFragment();
    Bundle args = new Bundle();
    args.putString(ARG_ROL, rol);
    args.putString(ARG_COMPANY_ID, companyId);
    args.putString(ARG_TOKEN, token.getAccess_token());

    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    context = getContext();
    df = new SimpleDateFormat(Objects.requireNonNull(context).getString(R.string.date_format), Locale.getDefault());
    if (getArguments() != null) {
      rol = getArguments().getString(ARG_ROL);
      companyId = getArguments().getString(ARG_COMPANY_ID);
      access_token = getArguments().getString(ARG_TOKEN);

    }
    context = getContext();
    companyService = new ListEventsByCompanyService();


  }

  @RequiresApi(api = Build.VERSION_CODES.N)
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_events_calendar, container, false);
    Calendar cal = Calendar.getInstance();
    allEventsByCompanyList = new ArrayList<>();
    Day day = new Day();

    mCalendarView = view.findViewById(R.id.calendarView);
    mCalendarView.isInEditMode();
    tvEventDay = view.findViewById(R.id.tv_select_day);
    tvEventDay.setText(df.format(cal.getTime()));

    /*FloatingActionButton floatingActionButton = view.findViewById(R.id.floatingActionButton);
    floatingActionButton.setOnClickListener(v -> addNote());*/
    rcEvents = view.findViewById(R.id.rv_events_calendar);
    LinearLayoutManager layoutManager = new LinearLayoutManager(context);
    rcEvents.setLayoutManager(layoutManager);
    DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rcEvents.getContext(),
            layoutManager.getOrientation());
    dividerItemDecoration.setDrawable(context.getResources().getDrawable(R.drawable.divider_recycler));
    rcEvents.addItemDecoration(dividerItemDecoration);
    setRecyclerViewAdapter(new ArrayList<>());
    mCalendarView.setOnDayClickListener(this::previewEvent);
    try {
      progressDialog = new ProgressDialog(context);
      progressDialog.setTitle(getString(R.string.load_data));
      progressDialog.setMessage(getString(R.string.load_events_data));
      progressDialog.setCancelable(true);
      progressDialog.setIndeterminate(true);
      progressDialog.show();
      Thread.sleep(2000);
      getAllEventsByCompany();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    return view;
  }

  @RequiresApi(api = Build.VERSION_CODES.N)
  @SuppressWarnings("NullableProblems")
  private void getAllEventsByCompany() {
    if (allEventsByCompanyList.size() == 0) {
      new Thread(() -> {
        Call<List<Event>> call = companyService.getEventsByCompany(companyId, access_token);
        call.enqueue(new Callback<List<Event>>() {
          @Override
          public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
            if (response.body() != null && response.body().size() > 0) {
              allEventsByCompanyList = response.body();
              allEventsByCompanyList.forEach(e -> d("RESPONSE", "Response getAllEventsByCompany: " + e.toString()));
            }
          }

          @Override
          public void onFailure(Call<List<Event>> call, Throwable t) {

          }
        });
        Objects.requireNonNull(getActivity()).runOnUiThread(() -> {
          progressDialog.dismiss();
          if (allEventsByCompanyList.size() == 0) {
            Toast.makeText(context, getString(R.string.no_events_data), Toast.LENGTH_LONG).show();
          }
        });
      }).start();
    } else {
      progressDialog.dismiss();
    }


  }


  // TODO Añadir "Cita"
  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == ADD_NOTE && resultCode == RESULT_OK) {
      MyEventDay myEventDay = data.getParcelableExtra(RESULT);
      try {
        mCalendarView.setDate(Objects.requireNonNull(myEventDay).getCalendar());
      } catch (OutOfDateRangeException e) {
        e.printStackTrace();
      }
      mEventDays.add(myEventDay);
      mCalendarView.setEvents(mEventDays);
    }
  }

  private void addNote() {
    Intent intent = new Intent(context, AddNoteActivity.class);
    startActivityForResult(intent, ADD_NOTE);
  }

  @RequiresApi(api = Build.VERSION_CODES.O)
  @SuppressWarnings("NullableProblems")
  private void previewEvent(EventDay eventDay) {

    String date = df.format(eventDay.getCalendar().getTime());
    tvEventDay.setText(date);
    String dateInit = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(eventDay.getCalendar().getTime());

    Call<List<Event>> call = companyService.getEventsByCompanyStarDay(companyId, dateInit, access_token);
    call.enqueue(new Callback<List<Event>>() {
      @RequiresApi(api = Build.VERSION_CODES.N)
      @Override
      public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
        if (response.body() != null && response.body().size() > 0 && !response.body().get(0).getTitle().isEmpty()) {
          eventList = response.body();
          eventList.forEach(e -> d("RESPONSE", "Response ListEventsCompanyByDate: " + e.toString()));
          setRecyclerViewAdapter(eventList);
          adapter.setClickListener((view, position) -> {
            Event event = eventList.get(position);
            Toast.makeText(context, "You click in: " + adapter.getItem(position), Toast.LENGTH_LONG).show();
            d("RESPONSE", "Event: " + event.toString());
            d("RESPONSE", "Event date: " + eventDay.getCalendar().toString());

            event.setDateInit(df.format(eventDay.getCalendar().getTime()));
            Intent intent = new Intent(context, EventDetailActivity.class);
            intent.putExtra(EVENT, event);
            startActivity(intent);
          });
        } else {
          d("RESPONSE", "Response getEvents [] " + response.message());
          setRecyclerViewAdapter(new ArrayList<>());
        }
      }

      @Override
      public void onFailure(Call<List<Event>> call, Throwable t) {
        d("RESPONSE", "Error getEvents: " + t.getCause());
        setRecyclerViewAdapter(new ArrayList<>());
      }
    });

  }

  private void setRecyclerViewAdapter(List<Event> eventList) {
    adapter = new RecyclerViewEventsAdapter(context, eventList);
    rcEvents.setAdapter(adapter);
  }
}
