package com.example.dotlinked_proyecto.api.connection;

import android.util.Log;

import com.example.dotlinked_proyecto.activities.login.LoginActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

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
    httpClient.connectTimeout(30, TimeUnit.SECONDS);
    httpClient.readTimeout(30, TimeUnit.SECONDS);

    httpClient.addInterceptor(new WithoutInternetInterceptor(LoginActivity.context));

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
    Gson gson = new GsonBuilder()
        .setLenient()
        .create();
    //If condition to ensure we don't create multiple retrofit instances in a single application
    if (retrofit == null) {
      retrofit = new Retrofit.Builder()
          .baseUrl(/*Environment.isEmulator(LoginActivity.context) ? "https://f83c405b.ngrok.io" :*/ SERVER_URL)
          .addConverterFactory(GsonConverterFactory.create(gson))
          .client(client)
          .build();
    }
    Log.d("RESPONSE", "URL retrofit: " + retrofit.baseUrl().toString());
    return retrofit;
  }
}
