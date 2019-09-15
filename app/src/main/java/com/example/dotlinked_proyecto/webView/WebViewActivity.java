package com.example.dotlinked_proyecto.webView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dotlinked_proyecto.R;
import com.example.dotlinked_proyecto.activities.BaseActivity;

public class WebViewActivity extends AppCompatActivity {

  // Esta es la url de mi pc en local http://localhost ( corriendo en xammp ) de mi ejemplo.
  // 10.0.2.2 es el enlace al local de tu pc en el emulador.
  // Para este tipo de respuesta en texto/plano has de añadir esto en tu android manifest
  // android:usesCleartextTraffic="true"
  // como puedes ver en el mio.
  public static final String webViewUrl = "http://10.0.2.2:80/ejemplo.html";
  // Esta es una variable estática mia.
  private final static String TAG_FRAGMENT = "fragment";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_webview);

  }

  public void viewWebView(View view) {
    WebViewFragment webViewFragment = WebViewFragment.newInstance();
    getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, webViewFragment).commit();
  }

  @Override
  public void onBackPressed() {
    Intent intent = new Intent(this, BaseActivity.class);
    intent.putExtra(TAG_FRAGMENT, getString(R.string.events));
    startActivity(intent);
  }

  public void exit(View view) {
    onBackPressed();
  }
}
