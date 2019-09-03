package com.example.dotlinked_proyecto.bean;

public class Users extends Person {

  private int id;
  private String nombre;
  private String apellido;
  private String movil;
  private String email;

    public Users() {
    }

    public Users(int id, String nombre, String apellido, String movil, String email) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.movil = movil;
        this.email = email;
    }

    public int getId() {
        return id;
    }

  public String getName() {
        return nombre;
    }

  public String getLastName() {
        return apellido;
    }

  public void setName(String name) {
    this.nombre = name;
    }

    public String getEmail() {
        return email;
    }

    public void setId(int id) {
        this.id = id;
    }

  public String getMobile() {
    return movil;
    }

  public void setLastName(String lastName) {
    this.apellido = lastName;
    }

  public void setMobile(String mobile) {
    this.movil = mobile;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

