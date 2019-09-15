package com.example.dotlinked_proyecto.appServices;

import com.example.dotlinked_proyecto.api.RetrofitSources.IListEmployeesByCompanyCall;
import com.example.dotlinked_proyecto.api.connection.Connection;
import com.example.dotlinked_proyecto.bean.Person;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;

public class ListEmployeesByCompanyIdService implements IListEmployeesByCompanyCall {

  private IListEmployeesByCompanyCall listEmployeesByCompany;

  @Override
  public Call<List<Person>> listEmployeesByCompany(String companyId, String token) {
    Retrofit retrofit = Connection.getRetrofitClient();
    if (retrofit != null)
      listEmployeesByCompany = retrofit.create(IListEmployeesByCompanyCall.class);
    return listEmployeesByCompany.listEmployeesByCompany(companyId, token);
  }
}
