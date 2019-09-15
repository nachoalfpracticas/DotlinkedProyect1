package com.example.dotlinked_proyecto.interfaces;


import com.example.dotlinked_proyecto.api.Class.Token;
import com.example.dotlinked_proyecto.bean.Person;

import java.util.List;

import retrofit2.Call;

public interface ILoginService {
    Call<Token> login(String userName, String password);

  Call<List<Person>> getPersonInfo(String token);
}
