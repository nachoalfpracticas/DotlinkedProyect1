package com.example.dotlinked_proyecto.interfaces;

import com.example.dotlinked_proyecto.bean.Service;

import java.util.List;

import retrofit2.Call;

public interface IServicesCompanyService {
  Call<List<Service>> getServicesByCompanyId(String companyId, String token);
}
