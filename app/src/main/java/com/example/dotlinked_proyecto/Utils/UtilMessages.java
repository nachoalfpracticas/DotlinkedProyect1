package com.example.dotlinked_proyecto.Utils;

import android.app.Activity;
import android.os.Build;
import android.service.autofill.FieldClassification;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.example.dotlinked_proyecto.API.Class.Token;
import com.example.dotlinked_proyecto.Persistence.Session;
import com.example.dotlinked_proyecto.R;
import com.example.dotlinked_proyecto.services.LoginService;
import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UtilMessages {

  public static void showExitMessage(final Activity activity) {
    final NiftyDialogBuilder exitDialog = NiftyDialogBuilder.getInstance(activity);
    exitDialog
        .withTitle(activity.getString(R.string.exitApp))
        .withMessage(activity.getString(R.string.exitYes))
        .withDialogColor("#1c90ec")
        .withButton1Text(activity.getString(R.string.Yes))
        .withButton2Text(activity.getString(R.string.No))
        .withDuration(700)
        .withEffect(Effectstype.RotateBottom)
        .isCancelableOnTouchOutside(false)
        .setButton1Click(v -> {
          exitDialog.dismiss();
          activity.finish();
        }).setButton2Click(v -> exitDialog.dismiss())
        .show();
  }

  public static void showLoadDataError(Activity activity, String message) {
    NiftyDialogBuilder loadGamerErr = NiftyDialogBuilder.getInstance(activity);
    loadGamerErr
        .withTitle(activity.getResources().getString(R.string.load_data))
        .withMessage(message)
        .withDialogColor("#ED0D17")
        .withButton1Text(activity.getResources().getString(R.string.OK))
        .withDuration(400)
        .withEffect(Effectstype.Slidetop)
        .isCancelableOnTouchOutside(false)
        .setButton1Click(v2 -> {
          activity.finish();
          activity.startActivity(activity.getIntent());
          loadGamerErr.dismiss();
        })
        .show();
  }

  public static void showLoadDataError(Activity activity, FieldClassification.Match matchSelected) {
    NiftyDialogBuilder loadMatchErr = NiftyDialogBuilder.getInstance(activity);
    loadMatchErr
        .withTitle(activity.getResources().getString(R.string.load_data))
        .withMessage(R.string.load_data_err)
        .withDialogColor("#ED0D17")
        .withButton1Text(activity.getResources().getString(R.string.OK))
        .withDuration(400)
        .withEffect(Effectstype.Slidetop)
        .isCancelableOnTouchOutside(false)
        .setButton1Click(v2 -> {
          activity.setResult(Activity.RESULT_OK);
//          Intent intent = new Intent(activity, MatchDetailActivity.class);
//          intent.putExtra(MatchesActivity.EXTRA_MATCH_ID, matchSelected.getId());
//          activity.startActivityForResult(intent, MatchesFragment.REQUEST_DETAIL_MATCH);
          loadMatchErr.dismiss();
          activity.finish();

        })
        .show();
  }

  @RequiresApi(api = Build.VERSION_CODES.N)
  static void showLoginView(Activity activity, String userName, String rol, String companyName) {
    Session session = new Session(activity);
    Check check = new Check();
    LoginService loginService = new LoginService();

    View view = activity.getLayoutInflater().inflate(activity.getResources().getLayout(R.layout.input_password), null);
    EditText edtPassword = view.findViewById(R.id.et_password);
    TextInputLayout textInputLayout = view.findViewById(R.id.til_password);

    edtPassword.requestFocus();
    final NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(activity);
    dialogBuilder
        .withTitle(activity.getString(R.string.password))
        .withIcon(R.drawable.key)
        .withMessage(String.format(activity.getString(R.string.input_password_access_rol_company), rol, companyName))
        .withMessageColor("#FF0000")
        .withDialogColor("#1c90ec")
        .withButton1Text(activity.getString(R.string.access))
        .withButton2Text(activity.getResources().getString(R.string.cancel))
        .withDuration(700)
        .withEffect(Effectstype.Slidetop)
        .isCancelableOnTouchOutside(false)
        .setButton1Click(v -> {
          String password = edtPassword.getText().toString();
          check.checkPassword(activity, password, textInputLayout);
          if (textInputLayout.isErrorEnabled()) {
            Toast.makeText(activity, textInputLayout.getError(), Toast.LENGTH_LONG).show();
          } else {
            Call<Token> call = loginService.login(userName, password);
            call.enqueue(new Callback<Token>() {
              @Override
              public void onResponse(Call<Token> call, Response<Token> response) {
                if (response.body() != null) {
                  Token token = response.body();
                  session.setToken(token);
                  if (!token.getUserName().isEmpty()) {
                    Log.d("RESPONSE", "Response getToken " + token.toString());
                    Util.navigationTo(activity, false, rol, userName, companyName);
                  }
                } else {
                  Log.d("RESPONSE", "Error response getToken " + response.message());
                  Toast.makeText(activity, activity.getString(R.string.wrong_credentials), Toast.LENGTH_LONG).show();
                }
              }

              @Override
              public void onFailure(Call call, Throwable t) {
                Log.d("RESPONSE", "Error getToken: " + t.getCause());
              }
            });
            dialogBuilder.dismiss();
          }
        })
        .setButton2Click(v1 -> {
          dialogBuilder.dismiss();
        })
        .setCustomView(view, activity)
        .show();
  }

  public static void fingerPrintNoHasEnrolledFingerprints(Activity activity) {
    final NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(activity);
    dialogBuilder
        .withTitle(activity.getString(R.string.fingerprint))
        .withIcon(R.drawable.fingerprint_black_24x24)
        .withMessage(activity.getString(R.string.not_fingerprint_hasEnrolledFingerprints))
        .withMessageColor("#000000")
        .withDialogColor("#1c90ec")
        .withButton1Text(activity.getResources().getString(R.string.cancel))
        .withDuration(700)
        .withEffect(Effectstype.Slidetop)
        .isCancelableOnTouchOutside(false)
        .setButton1Click(v1 -> {
          dialogBuilder.dismiss();
        })
        .show();
  }

  public static NiftyDialogBuilder showMessageUseFingerPrint(Activity activity) {
    final NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(activity);
    dialogBuilder
        .withTitle(activity.getString(R.string.fingerprint))
        .withIcon(R.drawable.fingerprint_black_24x24)
        .withMessage(activity.getString(R.string.use_fingerprint))
        .withMessageColor("#000000")
        .withDialogColor("#1c90ec");

    return dialogBuilder;
  }

}