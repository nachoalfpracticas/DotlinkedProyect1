package com.example.dotlinked_proyecto.api.RetrofitSourcesAPI;

import com.example.dotlinked_proyecto.api.Class.Token;
import com.example.dotlinked_proyecto.bean.Person;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ITokenCall {
  @FormUrlEncoded
  @POST("/token")
  Call<Token> getTokenCall(@Field(value="grant_type", encoded=true) String grant_type,
                           @Field(value="userName", encoded=true) String userName,
                           @Field(value="password", encoded=true) String password);

  @GET("UserInfo")
  Call<List<Person>> getPersonInfo(@Header("Authorization") String token);
}
