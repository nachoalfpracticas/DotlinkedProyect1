package com.example.dotlinked_proyecto.Persistence;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

import com.example.dotlinked_proyecto.api.Class.Token;
import com.example.dotlinked_proyecto.bean.Appointment;
import com.example.dotlinked_proyecto.bean.Company;
import com.example.dotlinked_proyecto.bean.Person;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Session {
  private SharedPreferences prefs;
  private UserPreferences userPref;
  private List<UserPreferences> listUsersPreferences = new ArrayList<>();


  public Session(Context ctx) {
    prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
  }

  public String getSessionUser() {
    return prefs.getString("user", "");
  }

  public void setSessionUser(String user) {
    prefs.edit().putString("user", user).apply();
  }

  public void deleteSessionUser() {
    prefs.edit().remove("user").apply();
  }

  public String getUserRoles() {
    return prefs.getString("userRoles", "");
  }

  public void setUserRoles(String roles) {
    prefs.edit().putString("userRoles", roles).apply();
  }

  public void deleteUserRoles() {
    prefs.edit().remove("userRoles").apply();
  }

  public String getRolUserSelected() {
    return prefs.getString("rolUserSelected", "");
  }

  public void setRolUserSelected(String rolUserSelected) {
    prefs.edit().putString("rolUserSelected", rolUserSelected).apply();
  }

  public void setRememberMeUser(Boolean remember) {
    prefs.edit().putBoolean("userRemember", remember).apply();
  }

  public Token getToken() {
    return new Gson().fromJson(prefs.getString("token", ""), Token.class);
  }

  public void setToken(Token token) {
    prefs.edit().putString("token", new Gson().toJson(token)).apply();
  }

  public void deleteToken() {
    prefs.edit().remove("token").apply();
  }

  public List<Company> getCompaniesUserByRol() {
    Gson gson = new Gson();
    Type listCompaniesType = new TypeToken<List<Company>>() {
    }.getType();
    return gson.fromJson(prefs.getString("listCompanies", ""), listCompaniesType);
  }

  public void setCompaniesUserByRol(List<Company> companyList) {
    prefs.edit().putString("listCompanies", new Gson().toJson(companyList)).apply();
  }

  public List<Appointment> getAppointmentsOfUser() {
    Gson gson = new Gson();
    Type appointmentsOfUser = new TypeToken<List<Appointment>>() {
    }.getType();
    return gson.fromJson(prefs.getString("appointmentsOfUser", ""), appointmentsOfUser);
  }

  public void setAppointmentsOfUser(List<Appointment> appointmentsOfUser) {
    prefs.edit().putString("appointmentsOfUser", new Gson().toJson(appointmentsOfUser)).apply();
  }

  public void deleteAppointmentsOfUser() {
    prefs.edit().remove("appointmentsOfUser").apply();
  }

  public String getCompanyIdUser() {
    return prefs.getString("userCompanyId", "");
  }

  public void setCompanyIdUser(String IdCompany) {
    prefs.edit().putString("userCompanyId", IdCompany).apply();
  }

  public void deleteCompanyIdUser() {
    prefs.edit().remove("userCompanyId").apply();
  }

  public Boolean gerUserRememberMe() {
    return prefs.getBoolean("userRemember", false);
  }

  public void deleteUserRemember() {
    prefs.edit().remove("userRemember").apply();
  }

  public void deleteAllSessionUser() {
    prefs.edit().clear().apply();
  }

  public boolean getUserUseFingerprint() {
    return prefs.getBoolean("userFingerprint", false);
  }

  public void setUserUseFingerprint(boolean use) {
    prefs.edit().putBoolean("userFingerprint", use).apply();
  }

  public void deleteUseFingerprint() {
    prefs.edit().remove("userFingerprint").apply();
  }

  public void setListTenantsForContact(List<Person> personList) {
    prefs.edit().putString("listTenants", new Gson().toJson(personList)).apply();
  }

  public List<Person> getTenantsForContact() {
    Gson gson = new Gson();
    Type listTenantsType = new TypeToken<List<Person>>() {
    }.getType();
    return gson.fromJson(prefs.getString("listTenants", ""), listTenantsType);
  }

  public void setTenantSelect(Person person) {
    prefs.edit().putString("tenantSelect", new Gson().toJson(person)).apply();
  }

  public Person getTenantSelect() {
    return new Gson().fromJson(prefs.getString("tenantSelect", ""), Person.class);
  }
}

