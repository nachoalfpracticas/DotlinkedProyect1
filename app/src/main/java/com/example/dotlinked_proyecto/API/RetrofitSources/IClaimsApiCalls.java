package com.example.dotlinked_proyecto.api.RetrofitSources;

import com.example.dotlinked_proyecto.bean.Claim;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface IClaimsApiCalls {
  @GET("/ListarQuejasPersona")
  Call<List<Claim>> getListComplaintsPerson(@Header("Authorization") String token);

  @POST("CrearQueja")
  @FormUrlEncoded
  Call<String> createClaim(@Field(value = "asunto", encoded = true) String subject,
                          @Field(value = "descripcion", encoded = true) String description,
                          @Field(value = "personaId", encoded = true) int personId,
                          @Field(value = "empresaId", encoded = true) int companyId,
                          @Header("Authorization") String token);
}
