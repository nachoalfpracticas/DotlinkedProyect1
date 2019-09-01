package com.example.dotlinked_proyecto.appServices;

import com.example.dotlinked_proyecto.API.Connection.Connection;
import com.example.dotlinked_proyecto.API.RetrofitSources.ServicesApiCalls;
import com.example.dotlinked_proyecto.bean.Service;
import com.example.dotlinked_proyecto.interfaces.IServicesCompanyService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;

public class ServicesCompanyService implements IServicesCompanyService {
  @Override
  public Call<List<Service>> getServicesByCompanyId(String companyId, String token) {
    Retrofit retrofit = Connection.getRetrofitClient();
    ServicesApiCalls servicesApiCalls = retrofit.create(ServicesApiCalls.class);

    return servicesApiCalls.getServicesByCompanyId(companyId, token);
  }
}
