package com.example.dotlinked_proyecto.appServices;

import com.example.dotlinked_proyecto.api.RetrofitSources.ICompanyUserByRolCall;
import com.example.dotlinked_proyecto.api.connection.Connection;
import com.example.dotlinked_proyecto.bean.Company;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;

public class CompanyByRolService implements ICompanyUserByRolCall {
 private ICompanyUserByRolCall companyUserByRolCall;;
  @Override
  public Call<List<Company>> getCompanies(String rol, String token) {
    Retrofit retrofit = Connection.getRetrofitClient();
    if (retrofit != null)
      companyUserByRolCall = retrofit.create(ICompanyUserByRolCall.class);

    return companyUserByRolCall.getCompanies(rol, token);
  }
}
