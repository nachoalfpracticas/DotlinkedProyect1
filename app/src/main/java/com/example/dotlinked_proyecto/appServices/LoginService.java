package com.example.dotlinked_proyecto.appServices;

import com.example.dotlinked_proyecto.API.Class.Token;
import com.example.dotlinked_proyecto.API.Connection.Connection;
import com.example.dotlinked_proyecto.API.RetrofitSources.ITokenCall;
import com.example.dotlinked_proyecto.API.RetrofitSources.TokenFieldsCall;
import com.example.dotlinked_proyecto.interfaces.ILoginService;

import retrofit2.Call;
import retrofit2.Retrofit;

public class LoginService implements ILoginService {

    @Override
    public Call<Token> login(String userName, String password) {
      Retrofit retrofit = Connection.getRetrofitClient();
      ITokenCall tokenCall = retrofit.create(ITokenCall.class);
      TokenFieldsCall body = new TokenFieldsCall(userName, password);

      return tokenCall.getTokenCall(body.getGrant_type(), body.getUserName(), body.getPassword());
    }

}
