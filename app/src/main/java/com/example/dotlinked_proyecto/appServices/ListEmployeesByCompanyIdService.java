package com.example.dotlinked_proyecto.appServices;

import com.example.dotlinked_proyecto.API.Connection.Connection;
import com.example.dotlinked_proyecto.API.RetrofitSources.ListEmployeesByCompanyCall;
import com.example.dotlinked_proyecto.bean.Person;
import com.example.dotlinked_proyecto.interfaces.IListEmployeesByCompanyId;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;

public class ListEmployeesByCompanyIdService implements IListEmployeesByCompanyId {
  @Override
  public Call<List<Person>> getPersonByCompany(String IdCompany, String token) {
    Retrofit retrofit = Connection.getRetrofitClient();
    ListEmployeesByCompanyCall listEmployeesByCompany = retrofit.create(ListEmployeesByCompanyCall.class);
    return listEmployeesByCompany.listEmployeesByCompany(IdCompany, token);
  }
}
