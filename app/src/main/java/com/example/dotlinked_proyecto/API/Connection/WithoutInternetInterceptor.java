package com.example.dotlinked_proyecto.api.connection;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.dotlinked_proyecto.R;
import com.example.dotlinked_proyecto.Utils.Check;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class WithoutInternetInterceptor implements Interceptor {

  private Context mContext;

  WithoutInternetInterceptor(Context context) {
    mContext = context;
  }

  @NonNull
  @Override
  public Response intercept(@NonNull Chain chain) throws IOException {
    if (!Check.checkInternetConnection(mContext)) {
      throw new NoConnectivityException(mContext.getString(R.string.without_connection));
    }

    Request.Builder builder = chain.request().newBuilder();
    return chain.proceed(builder.build());
  }
}
