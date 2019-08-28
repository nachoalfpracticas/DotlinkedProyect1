package com.example.dotlinked_proyecto.bean;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Person {
  @SerializedName("personId")
  @Expose
  private int personId;
  @SerializedName("nombre")
  @Expose
  private String nombre;
  @SerializedName("apellido")
  @Expose
  private String lastName;
  @SerializedName("nombreApellido")
  @Expose
  private String fullName;


  @SerializedName("dni")
  @Expose
  private String dni;
  @SerializedName("direccion")
  @Expose
  private String direccion;
  @SerializedName("telefonoFijo")
  @Expose
  private String landline;
  @SerializedName("movil")
  @Expose
  private String movil;
  @SerializedName("email")
  @Expose
  private String email;
  @SerializedName("nameRolesPersonaId")
  @Expose
  private String rol;

  private int postalCode;
  private String fechaNacimiento;
  private String activo;
  private String fechaDesactivada;


  Person() {
    }

  public int getPersonId() {
    return personId;
    }

  public void setPersonId(int personId) {
    this.personId = personId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getFullName() {
    return this.fullName;
    }

  public void setFullName(String name, String lastName) {
    this.fullName = lastName + "," + name;
    }

  public String getDni() {
    return dni;
    }

  public void setDni(String dni) {
    this.dni = dni;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

  public String getLandline() {
    return landline;
    }

  public void setLandline(String landline) {
    this.landline = landline;
    }

    public String getMovil() {
        return movil;
    }

    public void setMovil(String movil) {
        this.movil = movil;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

  public int getPostalCode() {
    return postalCode;
    }

  public void setPostalCode(int postalCode) {
    this.postalCode = postalCode;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getActivo() {
        return activo;
    }

    public void setActivo(String activo) {
        this.activo = activo;
    }

    public String getFechaDesactivada() {
        return fechaDesactivada;
    }

    public void setFechaDesactivada(String fechaDesactivada) {
        this.fechaDesactivada = fechaDesactivada;
    }

  public String getRol() {
    return rol;
  }

  public void setRol(String rol) {
    this.rol = rol;
  }

  @NonNull
  @Override
  public String toString() {
    return "Person{" +
            "personId=" + personId +
            ", nombre='" + nombre + '\'' +
            ", lastName='" + lastName + '\'' +
            ", fullName='" + fullName + '\'' +
            ", dni='" + dni + '\'' +
            ", direccion='" + direccion + '\'' +
            ", landline='" + landline + '\'' +
            ", movil='" + movil + '\'' +
            ", email='" + email + '\'' +
            ", rol='" + rol + '\'' +
            ", postalCode=" + postalCode +
            ", fechaNacimiento='" + fechaNacimiento + '\'' +
            ", activo='" + activo + '\'' +
            ", fechaDesactivada='" + fechaDesactivada + '\'' +
            '}';
  }
}
