package com.example.dotlinked_proyecto.appServices;

import com.example.dotlinked_proyecto.api.RetrofitSourcesAPI.IProvidersApiCalls;
import com.example.dotlinked_proyecto.api.connection.Connection;
import com.example.dotlinked_proyecto.bean.Order;
import com.example.dotlinked_proyecto.bean.ServiceOrderDetail;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;

public class ProviderService implements IProvidersApiCalls {

  private IProvidersApiCalls providersApiCalls;

  public ProviderService() {
    Retrofit retrofit = Connection.getRetrofitClient();
    if (retrofit != null)
      providersApiCalls = retrofit.create(IProvidersApiCalls.class);
  }

  @Override
  public Call<List<Order>> listOrderByCompanyAndProvider(String companyId, String token) {
    return providersApiCalls.listOrderByCompanyAndProvider(companyId, token);
  }

  @Override
  public Call<List<ServiceOrderDetail>> listServiceOrderDetail(int orderServiceId, String token) {
    return providersApiCalls.listServiceOrderDetail(orderServiceId, token);
  }
}
