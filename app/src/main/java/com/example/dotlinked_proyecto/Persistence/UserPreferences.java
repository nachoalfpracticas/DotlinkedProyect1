package com.example.dotlinked_proyecto.Persistence;

class UserPreferences {
  private String userName;
  private String[] roles;

  public UserPreferences(String userName) {
    this.userName = userName;
  }

  public UserPreferences() {
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }


  public String[] getRoles() {
    return roles;
  }

  public void setRoles(String[] roles) {
    this.roles = roles;
  }
}
