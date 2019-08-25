package com.example.dotlinked_proyecto.services;

import com.example.dotlinked_proyecto.API.Connection.Connection;
import com.example.dotlinked_proyecto.API.RetrofitSources.ListEventsByCompanyCall;
import com.example.dotlinked_proyecto.bean.Event;
import com.example.dotlinked_proyecto.interfaces.IListEventsByCompanyService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;

public class ListEventsByCompanyService implements IListEventsByCompanyService {
  @Override
  public Call<List<Event>> getEventsByCompany(String IdCompany, String dateInit, String token) {
    Retrofit retrofit = Connection.getRetrofitClient();

    ListEventsByCompanyCall listEventsByCompanyCall = retrofit.create(ListEventsByCompanyCall.class);

    return listEventsByCompanyCall.getEventsByCompany(IdCompany, dateInit, token);
  }
}
