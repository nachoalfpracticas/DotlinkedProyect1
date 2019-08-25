package com.example.dotlinked_proyecto.interfaces;


import com.example.dotlinked_proyecto.API.Class.Token;

import retrofit2.Call;

public interface ILoginService {
    Call<Token> login(String userName, String password);
}
