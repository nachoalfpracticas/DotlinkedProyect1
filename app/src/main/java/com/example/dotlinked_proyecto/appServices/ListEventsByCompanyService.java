package com.example.dotlinked_proyecto.appServices;

import com.example.dotlinked_proyecto.api.RetrofitSourcesAPI.IListEventsByCompanyCall;
import com.example.dotlinked_proyecto.bean.Event;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;

public class ListEventsByCompanyService implements IListEventsByCompanyCall {
  private IListEventsByCompanyCall IListEventsByCompanyCall;

  public ListEventsByCompanyService() {
    Retrofit retrofit = com.example.dotlinked_proyecto.api.connection.Connection.getRetrofitClient();
    if (retrofit != null)
      this.IListEventsByCompanyCall = retrofit.create(IListEventsByCompanyCall.class);
  }


  @Override
  public Call<List<Event>> getEventsByCompanyStartDay(String IdCompany, String dateInit, String token) {
    return IListEventsByCompanyCall.getEventsByCompanyStartDay(IdCompany, dateInit, token);
  }

  @Override
  public Call<List<Event>> getEventsByCompany(String IdCompany, String token) {
    return IListEventsByCompanyCall.getEventsByCompany(IdCompany, token);
  }
}
