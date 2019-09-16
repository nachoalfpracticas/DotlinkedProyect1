package com.example.dotlinked_proyecto.appServices;

import com.example.dotlinked_proyecto.api.RetrofitSourcesAPI.IClaimsApiCalls;
import com.example.dotlinked_proyecto.bean.Claim;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;

public class ClaimsPersonService implements IClaimsApiCalls {
  private IClaimsApiCalls iClaimsApiCalls;

  public ClaimsPersonService() {
    Retrofit retrofit = com.example.dotlinked_proyecto.api.connection.Connection.getRetrofitClient();
    if (retrofit != null)
      iClaimsApiCalls = retrofit.create(IClaimsApiCalls.class);
  }

  @Override
  public Call<List<Claim>> getListComplaintsPerson(String token) {
    return iClaimsApiCalls.getListComplaintsPerson(token);
  }

  @Override
  public Call<String> createClaim(String subject, String description, int personId, int companyId, String token) {
    return iClaimsApiCalls.createClaim(subject, description, personId, companyId, token);
  }
}
