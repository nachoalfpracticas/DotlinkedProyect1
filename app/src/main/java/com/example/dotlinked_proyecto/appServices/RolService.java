package com.example.dotlinked_proyecto.appServices;

import com.example.dotlinked_proyecto.API.Connection.Connection;
import com.example.dotlinked_proyecto.API.RetrofitSources.ListRolesByTokenCall;
import com.example.dotlinked_proyecto.interfaces.IRolService;

import retrofit2.Call;
import retrofit2.Retrofit;

public class RolService implements IRolService {
  @Override
  public Call<String[]> getRol(String token) {
    Retrofit retrofit = Connection.getRetrofitClient();
    ListRolesByTokenCall listRolesByTokenCall = retrofit.create(ListRolesByTokenCall.class);

    return listRolesByTokenCall.getRoles(token);
  }
}
