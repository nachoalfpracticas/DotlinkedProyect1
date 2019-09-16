package com.example.dotlinked_proyecto.api.connection;

import androidx.annotation.Nullable;

import java.io.IOException;

public class NoConnectivityException extends IOException {

  public NoConnectivityException(String message) {
    super(message);
  }

  @Override
  public String getMessage() {
    return super.getMessage();
    // You can send any message whatever you want from here ok.
  }

  @Nullable
  @Override
  public synchronized Throwable getCause() {
    return super.getCause();
  }
}
