package com.example.dotlinked_proyecto.webView;

import android.content.Context;
import android.webkit.JavascriptInterface;

import com.example.dotlinked_proyecto.R;
import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;

public class WebAppInterface {

  private Context context;

  public WebAppInterface(Context context) {
    this.context = context;
  }

  @JavascriptInterface
  public void showJavascriptRespone(String message) {
    NiftyDialogBuilder niftyDialogBuilder = NiftyDialogBuilder.getInstance(context);
    niftyDialogBuilder
        .withTitle(context.getResources().getString(R.string.load_data))
        .withMessage(message)
        .withDialogColor("#59ABDE")
        .withButton1Text(context.getResources().getString(R.string.OK))
        .withDuration(400)
        .withEffect(Effectstype.Slidetop)
        .isCancelableOnTouchOutside(false)
        .setButton1Click(v2 -> {
          niftyDialogBuilder.dismiss();
        })
        .show();


  }
}
