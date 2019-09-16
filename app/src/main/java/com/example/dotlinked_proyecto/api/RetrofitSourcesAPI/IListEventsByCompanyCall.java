package com.example.dotlinked_proyecto.api.RetrofitSourcesAPI;

import com.example.dotlinked_proyecto.bean.Event;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface IListEventsByCompanyCall {
  @GET("/GetEventos")
  Call<List<Event>> getEventsByCompanyStartDay(@Query("empresa") String IdCompany,
                                               @Query("fechaInicio") String dateInit,
                                               @Header("Authorization") String token);

  @GET("/GetEventosEmpresa")
  Call<List<Event>> getEventsByCompany(@Query("empresa") String IdCompany,
                                       @Header("Authorization") String token);
}

