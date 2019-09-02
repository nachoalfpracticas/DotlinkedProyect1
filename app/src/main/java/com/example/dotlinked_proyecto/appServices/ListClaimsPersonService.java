package com.example.dotlinked_proyecto.appServices;

import com.example.dotlinked_proyecto.API.Connection.Connection;
import com.example.dotlinked_proyecto.API.RetrofitSources.IListClaimsPersonCall;
import com.example.dotlinked_proyecto.bean.Claim;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;

public class ListClaimsPersonService implements IListClaimsPersonCall {
  @Override
  public Call<List<Claim>> getListComplaintsPerson(String token) {
    Retrofit retrofit = Connection.getRetrofitClient();
    IListClaimsPersonCall IListClaimsPersonCall = retrofit.create(IListClaimsPersonCall.class);
    return IListClaimsPersonCall.getListComplaintsPerson(token);
  }
}
