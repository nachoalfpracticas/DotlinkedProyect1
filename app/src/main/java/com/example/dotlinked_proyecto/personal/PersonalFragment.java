package com.example.dotlinked_proyecto.personal;


import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dotlinked_proyecto.API.Class.Token;
import com.example.dotlinked_proyecto.R;
import com.example.dotlinked_proyecto.appServices.ListEmployeesByCompanyIdService;
import com.example.dotlinked_proyecto.bean.Person;
import com.example.dotlinked_proyecto.personal.Adapter.ClickListener;
import com.example.dotlinked_proyecto.personal.Adapter.RecyclerViewPersonalAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.util.Log.d;

public class PersonalFragment extends Fragment {

  private static final String ARG_TOKEN = "token";
  private static final String ARG_COMPANY_ID = "companyId";

  private String access_token;
  private String companyId;
  private List<Person> personList;

  private Context context;
  private ListEmployeesByCompanyIdService employeesByCompanyIdService;

  private RecyclerViewPersonalAdapter adapter;
  private RecyclerView rvPersons;

  public PersonalFragment() {
    // Required empty public constructor
  }

  public static PersonalFragment newInstance(String companyId, Token token) {
    PersonalFragment fragment = new PersonalFragment();
    Bundle args = new Bundle();
    args.putString(ARG_TOKEN, token.getAccess_token());
    args.putString(ARG_COMPANY_ID, companyId);
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    context = getContext();
    employeesByCompanyIdService = new ListEmployeesByCompanyIdService();
    if (getArguments() != null) {
      access_token = getArguments().getString(ARG_TOKEN);
      companyId = getArguments().getString(ARG_COMPANY_ID);
    }

    getPersonsList();

  }

  @SuppressWarnings("NullableProblems")
  private void getPersonsList() {
    Call<List<Person>> call = employeesByCompanyIdService.listEmployeesByCompany(companyId, "bearer " + access_token);
    call.enqueue(new Callback<List<Person>>() {
      @RequiresApi(api = Build.VERSION_CODES.N)
      @Override
      public void onResponse(Call<List<Person>> call, Response<List<Person>> response) {
        if (response.body() != null && response.body().size() > 0 && !response.body().get(0).getFullName().isEmpty()) {
          personList = response.body();
          adapter = new RecyclerViewPersonalAdapter(context, personList, new ClickListener() {
            @Override
            public void onPositionClicked(View view, int position) {
/*
              if (view.getId() == ) {
                Toast.makeText(view.getContext(), "ITEM PRESSED = " + String.valueOf(getAdapterPosition()), Toast.LENGTH_SHORT).show();
              } else {
                Toast.makeText(view.getContext(), "ROW PRESSED = " + String.valueOf(getAdapterPosition()), Toast.LENGTH_SHORT).show();
              }*/
            }

            @Override
            public void onLongClicked(View view, int position) {

            }

          });
          // adapter.setClickListener((view, position) -> {
          //  Toast.makeText(context, "Hola tocaste el rc", Toast.LENGTH_SHORT).show();
          //});
          rvPersons.setAdapter(adapter);


          personList.forEach(person -> d("RESPONSE", "Response getPersonsList: " + person.toString()));
        } else {
          d("RESPONSE", "Response getPersonsList []" + response.message());
        }
      }

      @Override
      public void onFailure(Call<List<Person>> call, Throwable t) {
        d("RESPONSE", "Error getPersonsList: " + t.getCause());
      }
    });

  }

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {

    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_personal, container, false);
    rvPersons = view.findViewById(R.id.rv_personal);
    LinearLayoutManager layoutManager = new LinearLayoutManager(context);
    rvPersons.setLayoutManager(layoutManager);

    return view;
  }

}
