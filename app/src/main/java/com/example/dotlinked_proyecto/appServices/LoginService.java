package com.example.dotlinked_proyecto.appServices;

import com.example.dotlinked_proyecto.api.Class.Token;
import com.example.dotlinked_proyecto.api.RetrofitSources.ITokenCall;
import com.example.dotlinked_proyecto.api.RetrofitSources.TokenFieldsCall;
import com.example.dotlinked_proyecto.api.connection.Connection;
import com.example.dotlinked_proyecto.bean.Person;
import com.example.dotlinked_proyecto.interfaces.ILoginService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;

public class LoginService implements ILoginService {

  private ITokenCall tokenCall;

  public LoginService() {
    Retrofit retrofit = Connection.getRetrofitClient();
    if (retrofit != null)
      tokenCall = retrofit.create(ITokenCall.class);
  }

  @Override
    public Call<Token> login(String userName, String password) {
      TokenFieldsCall body = new TokenFieldsCall(userName, password);
      return tokenCall.getTokenCall(body.getGrant_type(), body.getUserName(), body.getPassword());
    }

  @Override
  public Call<List<Person>> getPersonInfo(String token) {
    return tokenCall.getPersonInfo(token);
  }

}
