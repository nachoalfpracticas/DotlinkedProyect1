package com.example.dotlinked_proyecto.interfaces;

import com.example.dotlinked_proyecto.bean.Company;

import java.util.List;

import retrofit2.Call;

public interface ICompanyByRolService {
  Call<List<Company>> getCompanyByRol(String rol, String token);
}
