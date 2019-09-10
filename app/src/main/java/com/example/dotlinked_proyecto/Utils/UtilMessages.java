package com.example.dotlinked_proyecto.Utils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.service.autofill.FieldClassification;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;

import com.example.dotlinked_proyecto.API.Class.Token;
import com.example.dotlinked_proyecto.Persistence.Session;
import com.example.dotlinked_proyecto.R;
import com.example.dotlinked_proyecto.appServices.LoginService;
import com.example.dotlinked_proyecto.bean.Appointment;
import com.example.dotlinked_proyecto.bean.Service;
import com.example.dotlinked_proyecto.services.ServiceOrderActivity;
import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

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
          activity.finish();
          exitDialog.dismiss();
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
  @SuppressWarnings("NullableProblems")
  static void showLoginView(Activity activity, String userName, String rol, String companyName, CardView button) {
    Session session = new Session(activity);
    Check check = new Check(activity);
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
          check.checkPassword(password, textInputLayout);
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
                    Util.navigationTo(activity, false, rol, userName, companyName, button);
                  }
                } else {
                  Log.d("RESPONSE", "Error response getToken " + response.message());
                  Toast.makeText(activity, activity.getString(R.string.wrong_credentials), Toast.LENGTH_LONG).show();
                }
              }

              @Override
              public void onFailure(Call call, Throwable t) {
                Log.d("RESPONSE", "Error getToken: " + t.getCause());
                button.setEnabled(true);
              }
            });
            dialogBuilder.dismiss();
          }
        })
        .setButton2Click(v1 -> {
          dialogBuilder.dismiss();
          button.setEnabled(true);
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
        .setButton1Click(v1 -> dialogBuilder.dismiss())
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

  public static void DismissFields(Activity activity) {
    final NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(activity);
    dialogBuilder
        .withTitle(activity.getString(R.string.dismiss))
        .withIcon(R.drawable.claim_icon)
        .withMessage(activity.getString(R.string.dismiss_fields))
        .withMessageColor("#FAD201")
        .withDialogColor(R.color.blueDotlinked)
        .withDuration(700)
        .withButton1Text(activity.getString(R.string.OK))
        .withButton2Text(activity.getString(R.string.cancel))
        .withEffect(Effectstype.Slidetop)
        .isCancelableOnTouchOutside(false)
        .setButton1Click(v -> {
          activity.finish();
          dialogBuilder.dismiss();
        })
        .setButton2Click(v1 -> dialogBuilder.dismiss())
        .show();
  }

  public static void withoutInternet(Activity activity) {
    // You can use our utils method to create Drawable with text
    // CalendarUtils.getDrawableText(Context context, String text, Typeface typeface, int color, int size);
    // CalendarUtils.getDrawableText(activity, str, Typeface.SERIF, Color.WHITE, 10);

    String str = activity.getString(R.string.without_connection);
    SpannableString spa = new SpannableString(str);
    int i = str.indexOf("@");
    Drawable d = activity.getResources().getDrawable(R.drawable.claim_icon);
    d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
    spa.setSpan(new ImageSpan(d), i, i + 1, ImageSpan.ALIGN_BOTTOM);

    NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(activity);
    dialogBuilder
        .withTitle(activity.getString(R.string.internet))
        .withIcon(R.drawable.internet64)
        .withDividerColor(R.color.daysLabelColor)
        .withMessage(spa)
        .withMessageColor("#FAD201")
        .withDialogColor(R.color.blueDotlinked)
        .withDuration(700)
        .withButton1Text(activity.getString(R.string.OK))
        .withEffect(Effectstype.RotateBottom)
        .isCancelableOnTouchOutside(false)
        .setButton1Click(v -> dialogBuilder.dismiss())
        .show();
  }

  public static void showAppointmentInfo(Activity activity,
                                         Service serviceSelected,
                                         Appointment appointment,
                                         String serviceName,
                                         String date,
                                         String time) {


    NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(activity);
    dialogBuilder
        .withTitle("    " + activity.getString(R.string.appointment_info))
        .withIcon(R.drawable.ic_dates_icon_white)
        .withDividerColor(R.color.daysLabelColor)
            .withMessage(String.format(activity.getString(R.string.appointment_msg), serviceName, Util.formatDateToLocale(activity, date), time))
        .withMessageColor("#FAD201")
        .withDialogColor(R.color.blueDotlinked)
        .withButton1Text(activity.getString(R.string.Yes))
        .withButton2Text(activity.getString(R.string.cancel))
        .withDuration(700)
        .withEffect(Effectstype.RotateBottom)
        .isCancelableOnTouchOutside(false)
        .setButton1Click(v2 -> {
          Intent intent = new Intent(activity, ServiceOrderActivity.class);
          intent.putExtra("appointment", new Gson().toJson(appointment));
          intent.putExtra("serviceSelected", new Gson().toJson(serviceSelected));
          intent.putExtra("change", true);
          activity.startActivity(intent);
          activity.finish();
          dialogBuilder.dismiss();
        })
        .setButton2Click(v3 -> {
          activity.finish();
          dialogBuilder.dismiss();
        })
        .show();
  }

  public static void showAppointmentInfoDateNotAvailable(Activity activity,
                                                         String serviceName,
                                                         String date,
                                                         String time) {
    NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(activity);
    dialogBuilder
        .withTitle("    " + activity.getString(R.string.appointment_info))
        .withIcon(R.drawable.ic_dates_icon_white)
        .withDividerColor(R.color.daysLabelColor)
        .withMessage(String.format(activity.getString(R.string.appointment_msg), serviceName, date, time))
        .withMessageColor("#FAD201")
        .withDialogColor(R.color.blueDotlinked)
        .withButton1Text(activity.getString(R.string.Yes))
        .withButton2Text(activity.getString(R.string.cancel))
        .withDuration(700)
        .withEffect(Effectstype.RotateBottom)
        .isCancelableOnTouchOutside(false)
        .setButton1Click(v2 -> {

          activity.finish();
          dialogBuilder.dismiss();
        })
        .setButton2Click(v3 -> {
          activity.finish();
          dialogBuilder.dismiss();
        })
        .show();
  }

  public static void showAppointmentInfoNewOrChange(Activity activity,
                                                    Service serviceSelected,
                                                    Appointment appointment,
                                                    String serviceName,
                                                    String date,
                                                    String time) {

    NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(activity);
    dialogBuilder
            .withTitle("    " + activity.getString(R.string.appointment_info))
            .withIcon(R.drawable.ic_dates_icon_white)
            .withDividerColor(R.color.daysLabelColor)
            .withMessage(String.format(activity.getString(R.string.appointment_msg_new_or_change), serviceName, Util.formatDateToLocale(activity, date), time))
            .withMessageColor("#FAD201")
            .withDialogColor(R.color.blueDotlinked)
            .withButton1Text(activity.getString(R.string.change))
            .withButton2Text(activity.getString(R.string.newAppointment))
            .withDuration(700)
            .withEffect(Effectstype.RotateBottom)
            .isCancelableOnTouchOutside(false)
            .setButton1Click(v2 -> {
              Intent intent = new Intent(activity, ServiceOrderActivity.class);
              intent.putExtra("appointment", new Gson().toJson(appointment));
              intent.putExtra("serviceSelected", new Gson().toJson(serviceSelected));
              activity.startActivity(intent);
              activity.finish();
              dialogBuilder.dismiss();
            })
            .setButton2Click(v3 -> {
              Intent intent = new Intent(activity, ServiceOrderActivity.class);
              intent.putExtra("serviceSelected", new Gson().toJson(serviceSelected));
              activity.startActivity(intent);
              activity.finish();
              dialogBuilder.dismiss();
            })
            .show();
  }

  public static void showMessageDontHoursAvailable(Activity activity, String date) {

    String str = String.format(activity.getString(R.string.without_dates_for_day_selected), date);
    SpannableString spa = new SpannableString(str);
    int i = str.indexOf("@");
    Drawable d = activity.getResources().getDrawable(R.drawable.claim_icon);
    d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
    spa.setSpan(new ImageSpan(d), i, i + 1, ImageSpan.ALIGN_BOTTOM);

    NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(activity);
    dialogBuilder
            .withTitle(activity.getString(R.string.appointment_info))
            .withIcon(R.drawable.ic_dates_icon_white)
            .withDividerColor(R.color.daysLabelColor)
            .withMessage(spa)
            .withMessageColor("#FAD201")
            .withDialogColor(R.color.blueDotlinked)
            .withDuration(700)
            .withButton1Text(activity.getString(R.string.OK))
            .withButton2Text(activity.getString(R.string.cancel))
            .withEffect(Effectstype.RotateBottom)
            .isCancelableOnTouchOutside(false)
            .setButton1Click(v -> dialogBuilder.dismiss())
            .setButton2Click(view -> {
              activity.finish();
              dialogBuilder.dismiss();
            })
            .show();

  }
}


