package com.example.dotlinked_proyecto.api.RetrofitSources;

import com.example.dotlinked_proyecto.bean.Person;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface IListEmployeesByCompanyCall {
  @GET("/ListarPersonasEmpleadoAdmsEmpresa")
  Call<List<Person>> listEmployeesByCompany(@Query("empresaId") String companyId, @Header("Authorization") String token);

}