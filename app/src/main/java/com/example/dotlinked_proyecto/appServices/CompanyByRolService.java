package com.example.dotlinked_proyecto.appServices;

import com.example.dotlinked_proyecto.API.Connection.Connection;
import com.example.dotlinked_proyecto.API.RetrofitSources.ICompanyUserByRolCall;
import com.example.dotlinked_proyecto.bean.Company;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;

public class CompanyByRolService implements ICompanyUserByRolCall {

  @Override
  public Call<List<Company>> getCompanies(String rol, String token) {
    Retrofit retrofit = Connection.getRetrofitClient();
    ICompanyUserByRolCall companyUserByRolCall = retrofit.create(ICompanyUserByRolCall.class);

    return companyUserByRolCall.getCompanies(rol, token);
  }
}
