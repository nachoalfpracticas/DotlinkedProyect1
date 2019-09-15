package com.example.dotlinked_proyecto.activities;

import android.content.Context;

import com.example.dotlinked_proyecto.Utils.Check;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

public class WithoutInternetInterceptor implements Interceptor {

  private Context mContext;

  public WithoutInternetInterceptor(Context context) {
    mContext = context;
  }

  @Override
  public Response intercept(Chain chain) throws IOException {
    if (Check.checkInternetConnection(mContext)) {
      throw new 
    }
  }
}
