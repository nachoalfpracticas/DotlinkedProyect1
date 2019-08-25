package com.example.dotlinked_proyecto.API.RetrofitSources;

import com.example.dotlinked_proyecto.bean.Claim;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface ListClaimsPersonCall {
  @GET("/ListarQuejasPersona")
  Call<List<Claim>> getListComplaintsPerson(@Header("Authorization") String token);
}
