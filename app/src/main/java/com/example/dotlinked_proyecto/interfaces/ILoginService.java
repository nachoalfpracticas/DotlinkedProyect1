package com.example.dotlinked_proyecto.interfaces;


import com.example.dotlinked_proyecto.API.Class.Token;
import com.example.dotlinked_proyecto.bean.Person;

import retrofit2.Call;

public interface ILoginService {
    Call<Token> login(String userName, String password);

    Call<Person> getPersonInfo(String token);
}
