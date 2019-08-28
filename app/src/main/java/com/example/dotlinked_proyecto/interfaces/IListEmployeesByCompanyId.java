package com.example.dotlinked_proyecto.interfaces;

import com.example.dotlinked_proyecto.bean.Person;

import java.util.List;

import retrofit2.Call;

public interface IListEmployeesByCompanyId {
  Call<List<Person>> getPersonByCompany(String IdCompany, String token);
}
