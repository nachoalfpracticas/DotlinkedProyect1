package com.example.dotlinked_proyecto.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Company {

  @SerializedName("empresaId")
  @Expose
  private int companyId;

  @SerializedName("nombre")
  @Expose
  private String companyName;

  public Company(int companyId, String companyName) {
    this.companyId = companyId;
    this.companyName = companyName;
  }

  public int getCompanyId() {
    return companyId;
  }

  public void setCompanyId(int companyId) {
    this.companyId = companyId;
  }

  public String getCompanyName() {
    return companyName;
  }

  public void setCompanyName(String companyName) {
    this.companyName = companyName;
  }

  @Override
  public String toString() {
    return "Company{" +
        "companyId=" + companyId +
        ", companyName='" + companyName + '\'' +
        '}';
  }
}
