package com.example.dotlinked_proyecto.appServices;

import com.example.dotlinked_proyecto.API.Connection.Connection;
import com.example.dotlinked_proyecto.API.RetrofitSources.IListRolesByTokenCall;

import retrofit2.Call;
import retrofit2.Retrofit;

public class RolService implements IListRolesByTokenCall {
  @Override
  public Call<String[]> getRoles(String token) {
    Retrofit retrofit = Connection.getRetrofitClient();
    IListRolesByTokenCall listRolesByTokenCall = retrofit.create(IListRolesByTokenCall.class);

    return listRolesByTokenCall.getRoles(token);
  }
}
