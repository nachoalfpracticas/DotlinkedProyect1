package com.example.dotlinked_proyecto.API.RetrofitSources;

import com.example.dotlinked_proyecto.API.Class.Token;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ITokenCall {
  @FormUrlEncoded
  @POST("/token")
  Call<Token> getTokenCall(@Field(value="grant_type", encoded=true) String grant_type,
                           @Field(value="userName", encoded=true) String userName,
                           @Field(value="password", encoded=true) String password);
}
