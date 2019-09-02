package com.example.dotlinked_proyecto.API.RetrofitSources;

import com.example.dotlinked_proyecto.bean.Service;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface IServicesApiCalls {
  @GET("ListarServicios")
  Call<List<Service>> getServicesByCompanyId(@Query("empresaId") String companyId,
                                             @Header("Authorization") String token);

  @GET("GetCitas")
  Call<List<Service>> getReservedServiceOfUser(@Query("rol") String rol,
                                               @Query("empresa") String companyId,
                                               @Query("fechaInicio") String dateInit,
                                               @Query("fechaFin") String dateEnd,
                                               @Header("Authorization") String token);
}
