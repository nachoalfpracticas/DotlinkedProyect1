package com.example.dotlinked_proyecto.appServices;

import com.example.dotlinked_proyecto.API.Connection.Connection;
import com.example.dotlinked_proyecto.API.RetrofitSources.IListEmployeesByCompanyCall;
import com.example.dotlinked_proyecto.bean.Person;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;

public class ListEmployeesByCompanyIdService implements IListEmployeesByCompanyCall {

  @Override
  public Call<List<Person>> listEmployeesByCompany(String companyId, String token) {
    Retrofit retrofit = Connection.getRetrofitClient();
    IListEmployeesByCompanyCall listEmployeesByCompany = retrofit.create(IListEmployeesByCompanyCall.class);
    return listEmployeesByCompany.listEmployeesByCompany(companyId, token);
  }
}
