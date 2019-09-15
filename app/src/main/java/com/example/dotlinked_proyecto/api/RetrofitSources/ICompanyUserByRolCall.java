package com.example.dotlinked_proyecto.API.RetrofitSources;

import com.example.dotlinked_proyecto.bean.Company;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface ICompanyUserByRolCall {
  @GET("/ListarEmpresasDeUsuarioRol")
  Call<List<Company>> getCompanies(@Query("name") String rol, @Header("Authorization") String token);
}
