package com.example.dotlinked_proyecto.API.RetrofitSources;

import com.example.dotlinked_proyecto.bean.Appointment;
import com.example.dotlinked_proyecto.bean.Person;
import com.example.dotlinked_proyecto.bean.Service;
import com.example.dotlinked_proyecto.bean.ServiceInfo;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface IServicesApiCalls {
  @GET("ListarServicios")
  Call<List<Service>> getServicesByCompanyId(@Query("empresaId") String companyId,
                                             @Header("Authorization") String token);

  @GET("GetCitas")
  Call<List<Appointment>> getReservedServiceOfUser(@Query("rol") String rol,
                                                   @Query("empresa") String companyId,
                                                   @Query("fechaInicio") String dateInit,
                                                   @Query("fechaFin") String dateEnd,
                                                   @Header("Authorization") String token);

  @GET("ListarInquilinosPorContacto")
  Call<List<Person>> listTenantByContact(@Query("empresaId") String companyId,
                                         @Query("rol") String rol,
                                         @Header("Authorization") String token);

  @GET("Horarios")
  Call<List<ServiceInfo>> listScheduleService(@Query("empresaId") String companyId,
                                              @Query("servicio") String serviceId,
                                              @Query("fecha") String date,
                                              @Query("personaid") String personId,
                                              @Header("Authorization") String token);

  @POST("CrearModificarCita")
  Call<JSONObject> createUpdateAppointment(@Body Appointment appointment, @Header("Authorization") String token);
}
