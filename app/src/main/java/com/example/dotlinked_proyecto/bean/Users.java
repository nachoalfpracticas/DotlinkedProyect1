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

    public String getNombre() {
        return nombre;
    }

  public String getLastName() {
        return apellido;
    }

    public String getMovil() {
        return movil;
    }

    public String getEmail() {
        return email;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

  public void setLastName(String lastName) {
    this.apellido = lastName;
    }

    public void setMovil(String movil) {
        this.movil = movil;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

