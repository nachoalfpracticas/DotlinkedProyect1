package com.example.dotlinked_proyecto.services;

import com.example.dotlinked_proyecto.API.Connection.Connection;
import com.example.dotlinked_proyecto.API.RetrofitSources.ListClaimsPersonCall;
import com.example.dotlinked_proyecto.bean.Claim;
import com.example.dotlinked_proyecto.interfaces.IlistClaimsPersonService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;

public class ListClaimsPersonService implements IlistClaimsPersonService {
  @Override
  public Call<List<Claim>> getListComplaintsPerson(String token) {
    Retrofit retrofit = Connection.getRetrofitClient();
    ListClaimsPersonCall listClaimsPersonCall = retrofit.create(ListClaimsPersonCall.class);
    return listClaimsPersonCall.getListComplaintsPerson(token);
  }
}
