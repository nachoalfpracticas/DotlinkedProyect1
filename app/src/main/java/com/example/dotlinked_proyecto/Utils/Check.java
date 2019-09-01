package com.example.dotlinked_proyecto.Utils;

import android.app.Activity;
import android.content.Context;
import android.hardware.fingerprint.FingerprintManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatEditText;

import com.example.dotlinked_proyecto.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Check {

  private static final String PATRON_PWD = "(?=.*[A-Z])(?=.*[0-9])[#@£$-/:-?{-~!\"^_`\\[\\]a-zA-Z0-9]{6,100}";
  private Activity activity;

  public Check(Activity activity) {
    this.activity = activity;
  }

  @RequiresApi(api = Build.VERSION_CODES.M)
  public static boolean checkFingerprint(Activity activity) {
    FingerprintManager fingerprintManager;
    // Reqiere API 23 ,
    fingerprintManager = (FingerprintManager) activity.getSystemService(android.content.Context.FINGERPRINT_SERVICE);
    return Objects.requireNonNull(fingerprintManager).isHardwareDetected();
  }

  public boolean checkEmail(EditText emailEditText, TextInputLayout emailInputLayout) {
    boolean success = true;
    if (emailEditText.getText().toString().trim().isEmpty()) {
      emailInputLayout.setErrorEnabled(true);
      emailInputLayout.setError(activity.getString(R.string.required_fields));
      if (emailEditText.requestFocus()) {
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
      }
      success = false;
    } else {
      if (emailEditText.getText().toString().matches(activity.getString(R.string.email_validation))) {
        emailInputLayout.setErrorEnabled(false);
      } else {
        emailInputLayout.setErrorEnabled(true);
        emailInputLayout.setError(activity.getString(R.string.email_invalid));
        if (emailEditText.requestFocus()) {
          activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
        success = false;
      }
    }
    if (!success)
      Toast.makeText(activity, Objects.requireNonNull(emailInputLayout.getError()).toString(), Toast.LENGTH_LONG).show();
    return success;
  }

  public void checkPassword(String pass, TextInputLayout tilPassword) {

    Pattern p = Pattern.compile(PATRON_PWD);
    Matcher m = p.matcher(pass);
    boolean format = m.matches();

    boolean empty = pass.isEmpty();

    if (empty) {
      tilPassword.setErrorEnabled(true);
      tilPassword.setError(activity.getString(R.string.required_fields));
    } else if (!format) {
      tilPassword.setErrorEnabled(true);
      tilPassword.setError(activity.getString(R.string.password_invalid));
    } else {
      tilPassword.setErrorEnabled(false);
    }
  }

  public boolean checkEditText(TextInputLayout inputLayout, AppCompatEditText editText) {
    inputLayout.setErrorEnabled(false);
    if (editText.getText() == null || editText.getText().toString().trim().isEmpty()) {
      inputLayout.setErrorEnabled(true);
    }
    return !inputLayout.isErrorEnabled();
  }

  public boolean checkInternetConnection(Context context) {
    boolean con = false;
    ConnectivityManager con_manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo ni = Objects.requireNonNull(con_manager).getActiveNetworkInfo();

    if (ni != null) {
      con = ni.isAvailable() && ni.isConnected();
    }
    return con;
  }
}
