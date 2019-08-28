package com.example.dotlinked_proyecto.API.RetrofitSources;

import com.example.dotlinked_proyecto.bean.Person;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface ListEmployeesByCompanyCall {
  @GET("/ListarPersonasEmpleadoAdmsEmpresa")
  Call<List<Person>> listEmployeesByCompany(@Query("empresaId") String companyId, @Header("Authorization") String token);

}