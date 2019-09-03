package com.example.dotlinked_proyecto.bean;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Person {
  @SerializedName("personaId")
  @Expose
  private int personId;
  @SerializedName("nombre")
  @Expose
  private String name;
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
  private String address;
  @SerializedName("telefonoFijo")
  @Expose
  private String landLine;
  @SerializedName("movil")
  @Expose
  private String mobile;
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

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
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

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getLandLine() {
    return landLine;
  }

  public void setLandLine(String landLine) {
    this.landLine = landLine;
  }

  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
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
            ", name='" + name + '\'' +
            ", lastName='" + lastName + '\'' +
            ", fullName='" + fullName + '\'' +
            ", dni='" + dni + '\'' +
            ", address='" + address + '\'' +
            ", landLine='" + landLine + '\'' +
            ", mobile='" + mobile + '\'' +
            ", email='" + email + '\'' +
            ", rol='" + rol + '\'' +
            ", postalCode=" + postalCode +
            ", fechaNacimiento='" + fechaNacimiento + '\'' +
            ", activo='" + activo + '\'' +
            ", fechaDesactivada='" + fechaDesactivada + '\'' +
            '}';
  }
}
