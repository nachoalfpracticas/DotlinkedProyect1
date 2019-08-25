package com.example.dotlinked_proyecto.API.RetrofitSources;

public class TokenFieldsCall {
  private String userName;
  private String password;
  private String grant_type;

  public String getUserName() {
    return userName;
  }

  public String getPassword() {
    return password;
  }

  public String getGrant_type() {
    return grant_type;
  }

  public TokenFieldsCall(String userName, String password) {
    this.userName = userName;
    this.password = password;
    this.grant_type = "password";
  }

  @Override
  public String toString() {
    return "grant_type=" + grant_type +
            "&userName=" + userName +
            "&password=" + password ;
  }
}
