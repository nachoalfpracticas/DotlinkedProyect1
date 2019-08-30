package com.example.dotlinked_proyecto.services;

import com.example.dotlinked_proyecto.API.Connection.Connection;
import com.example.dotlinked_proyecto.API.RetrofitSources.ListEventsByCompanyCall;
import com.example.dotlinked_proyecto.bean.Event;
import com.example.dotlinked_proyecto.interfaces.IListEventsByCompanyService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;

public class ListEventsByCompanyService implements IListEventsByCompanyService {
  private ListEventsByCompanyCall listEventsByCompanyCall;

  public ListEventsByCompanyService() {
    Retrofit retrofit = Connection.getRetrofitClient();
    this.listEventsByCompanyCall = retrofit.create(ListEventsByCompanyCall.class);
  }

  @Override
  public Call<List<Event>> getEventsByCompanyStarDay(String IdCompany, String dateInit, String token) {
    return listEventsByCompanyCall.getEventsByCompanyStartDay(IdCompany, dateInit, token);
  }

  @Override
  public Call<List<Event>> getEventsByCompany(String IdCompany, String token) {
    return listEventsByCompanyCall.getEventsByCompany(IdCompany, token);
  }
}
