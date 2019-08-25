package com.example.dotlinked_proyecto.interfaces;

import com.example.dotlinked_proyecto.bean.Claim;

import java.util.List;

import retrofit2.Call;

public interface IlistClaimsPersonService {
  Call<List<Claim>> getListComplaintsPerson(String token);
}
