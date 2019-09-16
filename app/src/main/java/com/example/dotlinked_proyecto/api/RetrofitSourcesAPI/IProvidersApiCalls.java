package com.example.dotlinked_proyecto.api.RetrofitSourcesAPI;

import com.example.dotlinked_proyecto.bean.Order;
import com.example.dotlinked_proyecto.bean.Person;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface IProvidersApiCalls {

  @GET("GetOrdenesProveedor")
  Call<List<Order>> listOrderByCompanyAndProvider(@Query("empresa") String companyId,
                                                  @Header("Authorization") String token);

}
