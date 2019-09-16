package com.example.dotlinked_proyecto.api.Class;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

// Class Token.
public class Token {
  @SerializedName("access_token")
  @Expose
  private String access_token;

  @SerializedName("token_type")
  @Expose
  private String token_type;

  @SerializedName("userName")
  @Expose
  private String userName;

  @SerializedName(".issues")
  @Expose
  private String issues;

  @SerializedName(".expires")
  @Expose
  private String expires;

  public String getAccess_token() {
    return access_token;
  }

  private String getToken_type() {
    return token_type;
  }

  public String getUserName() {
    return userName;
  }

  private String getIssues() {
    return issues;
  }

  private String getExpires() {
    return expires;
  }

  @NonNull
  @Override
  public String toString() {
    return "Token{" +
            "access_token='" + getAccess_token() + '\'' +
            ", token_type='" + getToken_type() + '\'' +
            ", expires='" + getExpires() + '\'' +
            ", userName='" + getUserName() + '\'' +
            ", issues='" + getIssues() + '\'' +
            ", expires='" + getExpires() + '\'' +
            '}';
  }
}
