package com.example.dotlinked_proyecto.API.Class;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

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
    private String xpires;

    public String getAccess_token() {
        return access_token;
    }

    public String getToken_type() {
        return token_type;
    }

    public String getUserName() {
        return userName;
    }

    public String getIssues() {
        return issues;
    }

    public String getXpires() {
        return xpires;
    }

    @Override
    public String toString() {
        return "Token{" +
            "access_token='" + getAccess_token() + '\'' +
            ", token_type='" + getToken_type() + '\'' +
            ", expires='" + getXpires() + '\'' +
            ", userName='" + getUserName() + '\'' +
            ", issues='" + getIssues() + '\'' +
            ", xpires='" + getXpires() + '\'' +
            '}';
    }
}
