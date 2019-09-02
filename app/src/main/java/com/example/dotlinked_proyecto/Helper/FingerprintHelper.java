package com.example.dotlinked_proyecto.Helper;

import android.content.Context;
import android.content.Intent;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.CancellationSignal;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.example.dotlinked_proyecto.activities.BaseActivity;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;


@RequiresApi(api = Build.VERSION_CODES.M)
public class FingerprintHelper extends FingerprintManager.AuthenticationCallback {

  private Context mContext;
  private NiftyDialogBuilder dialogBuilder;

  public FingerprintHelper(Context mContext, NiftyDialogBuilder dialogBuilder) {
    this.mContext = mContext;
    this.dialogBuilder = dialogBuilder;
  }


  @RequiresApi(api = Build.VERSION_CODES.M)
  public void startAuth(FingerprintManager manager, FingerprintManager.CryptoObject cryptoObject) {
    CancellationSignal cancellationSignal = new CancellationSignal();
    manager.authenticate(cryptoObject, cancellationSignal, 0, this, null);
  }


  @Override
  public void onAuthenticationError(int errMsgId, CharSequence errString) {
    this.showInformationMessage("Auth error" + errString);
  }


  @Override
  public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
    this.showInformationMessage("Auth help\n" + helpString);
  }


  @Override
  public void onAuthenticationFailed() {
    this.showInformationMessage("Auth failed.");
  }


  @Override
  public void onAuthenticationSucceeded(android.hardware.fingerprint.FingerprintManager.AuthenticationResult result) {
    this.showInformationMessage("Auth succeeded.");
    mContext.startActivity(new Intent(mContext, BaseActivity.class));
  }


  private void showInformationMessage(String msg) {

    Toast.makeText(mContext, msg, Toast.LENGTH_LONG).show();
    dialogBuilder.dismiss();
  }
}
