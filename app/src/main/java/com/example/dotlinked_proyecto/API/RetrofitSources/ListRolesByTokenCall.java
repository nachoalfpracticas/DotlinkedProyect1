package com.example.dotlinked_proyecto.API.RetrofitSources;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface ListRolesByTokenCall {
  @GET("/ListarRolesPorToken")
  Call<String[]> getRoles(@Header("Authorization") String token);
}
