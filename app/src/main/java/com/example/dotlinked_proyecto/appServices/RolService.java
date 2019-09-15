package com.example.dotlinked_proyecto.appServices;

import com.example.dotlinked_proyecto.api.RetrofitSources.IListRolesByTokenCall;
import com.example.dotlinked_proyecto.api.connection.Connection;

import retrofit2.Call;
import retrofit2.Retrofit;

public class RolService implements IListRolesByTokenCall {

  private IListRolesByTokenCall listRolesByTokenCall;

  @Override
  public Call<String[]> getRoles(String token) {
    Retrofit retrofit = Connection.getRetrofitClient();
    if (retrofit != null)
      listRolesByTokenCall = retrofit.create(IListRolesByTokenCall.class);

    return listRolesByTokenCall.getRoles(token);
  }
}
