package com.example.dotlinked_proyecto.API.RetrofitSources;

import com.example.dotlinked_proyecto.bean.Event;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface ListEventsByCompanyCall {
  @GET("/GetEventos")
  Call<List<Event>> getEventsByCompany(@Query("empresa") String Idcompany,
                                       @Query("fechaInicio") String dateInit,
                                       @Header("Authorization") String token);
}
