package com.example.dotlinked_proyecto.webView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.fragment.app.Fragment;

import com.example.dotlinked_proyecto.R;

public class WebViewFragment extends Fragment {

  public WebViewFragment() {
    // Required empty public constructor
  }


  // TODO: Rename and change types and number of parameters
  static WebViewFragment newInstance() {
    WebViewFragment fragment = new WebViewFragment();
    Bundle args = new Bundle();
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_webview, container, false);
    WebView webView = view.findViewById(R.id.webView);
    webView.setWebViewClient(new WebViewClient());
    WebSettings webSettings = webView.getSettings();
    webSettings.setJavaScriptEnabled(true);
    webView.setWebChromeClient(new WebChromeClient());

    // Esta es el objeto al que llamo en archivo ejemplo.html
    webView.addJavascriptInterface(new WebAppInterface(getActivity()), "Android");

    webView.loadUrl(WebViewActivity.webViewUrl);

    return view;
  }
}
