package com.example.dotlinked_proyecto.interfaces;

import retrofit2.Call;

public interface IRolService {
  Call<String[]> getRol(String token);
}
