package com.example.dotlinked_proyecto.services;

import com.example.dotlinked_proyecto.API.Connection.Connection;
import com.example.dotlinked_proyecto.API.RetrofitSources.CompanyUserByRolCall;
import com.example.dotlinked_proyecto.bean.Company;
import com.example.dotlinked_proyecto.interfaces.ICompanyByRolService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;

public class CompanyByRolService implements ICompanyByRolService {
  @Override
  public Call<List<Company>> getCompanyByRol(String rol, String token) {
    Retrofit retrofit = Connection.getRetrofitClient();
    CompanyUserByRolCall companyUserByRolCall = retrofit.create(CompanyUserByRolCall.class);

    return companyUserByRolCall.getCompanies(rol, token);
  }
}
