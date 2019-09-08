package com.example.dotlinked_proyecto.API.Connection;

import android.util.Log;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Connection {

  //la url del servidor donde conectamos
  private static final String SERVER_URL = "https://apinewgezzone-1-dev-as.azurewebsites.net";

  private static Retrofit retrofit;

  private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
  private static OkHttpClient client = getHttpClient().build();

  private static OkHttpClient.Builder getHttpClient() {
    httpClient.addInterceptor(chain -> {
      Request original = chain.request();

      Request request = original.newBuilder()
          .header("Content-Type", "application/x-www-form-urlencoded")
          .header("cache-control", "no-cache")
          .method(original.method(), original.body())
          .build();
      return chain.proceed(request);

    });
    return httpClient;
  }

  public static Retrofit getRetrofitClient() {
    //If condition to ensure we don't create multiple retrofit instances in a single application
    if (retrofit == null) {
      retrofit = new Retrofit.Builder()
          .baseUrl(SERVER_URL)
          .addConverterFactory(GsonConverterFactory.create())
          .client(client)
          .build();
    }
    Log.d("RESPONSE", "URL retrofit: " + retrofit.baseUrl().toString());
    return retrofit;
  }
}
