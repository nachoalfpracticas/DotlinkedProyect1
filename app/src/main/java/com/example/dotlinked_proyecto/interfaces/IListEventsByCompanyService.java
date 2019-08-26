package com.example.dotlinked_proyecto.interfaces;

import com.example.dotlinked_proyecto.bean.Event;

import java.util.List;

import retrofit2.Call;

public interface IListEventsByCompanyService {
  Call<List<Event>> getEventsByCompany(String IdCompany, String dateInit, String token);
}
