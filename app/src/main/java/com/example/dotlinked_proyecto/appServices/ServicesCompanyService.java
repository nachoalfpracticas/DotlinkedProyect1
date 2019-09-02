package com.example.dotlinked_proyecto.appServices;

import com.example.dotlinked_proyecto.API.Connection.Connection;
import com.example.dotlinked_proyecto.API.RetrofitSources.IServicesApiCalls;
import com.example.dotlinked_proyecto.bean.Service;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;

public class ServicesCompanyService implements IServicesApiCalls {
  @Override
  public Call<List<Service>> getServicesByCompanyId(String companyId, String token) {
    Retrofit retrofit = Connection.getRetrofitClient();
    IServicesApiCalls servicesApiCalls = retrofit.create(IServicesApiCalls.class);

    return servicesApiCalls.getServicesByCompanyId(companyId, token);
  }

  @Override
  public Call<List<Service>> getReservedServiceOfUser(String rol, String companyId, String dateInit, String dateEnd, String token) {
    return null;
  }
}
