package com.example.dotlinked_proyecto.api.RetrofitSourcesAPI;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface IListRolesByTokenCall {
  @GET("/ListarRolesPorToken")
  Call<String[]> getRoles(@Header("Authorization") String token);
}
