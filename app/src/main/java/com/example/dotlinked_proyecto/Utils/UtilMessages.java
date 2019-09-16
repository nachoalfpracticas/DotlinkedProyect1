package com.example.dotlinked_proyecto.Utils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import com.example.dotlinked_proyecto.Persistence.Session;
import com.example.dotlinked_proyecto.R;
import com.example.dotlinked_proyecto.activities.BaseActivity;
import com.example.dotlinked_proyecto.activities.login.AccessActivity;
import com.example.dotlinked_proyecto.activities.login.LoginActivity;
import com.example.dotlinked_proyecto.api.Class.Token;
import com.example.dotlinked_proyecto.api.connection.NoConnectivityException;
import com.example.dotlinked_proyecto.appServices.LoginService;
import com.example.dotlinked_proyecto.bean.Appointment;
import com.example.dotlinked_proyecto.bean.Service;
import com.example.dotlinked_proyecto.services.ServiceOrderActivity;
import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UtilMessages {
  private final static String TAG_FRAGMENT = "fragment";
  public static void showExitMessage(final Activity activity) {
    final NiftyDialogBuilder exitDialog = NiftyDialogBuilder.getInstance(activity);
    exitDialog
        .withTitle(activity.getString(R.string.exitApp))
        .withMessage(activity.getString(R.string.exitYes))
        .withDialogColor("#59ABDE")
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
    Session session = new Session(activity);
    NiftyDialogBuilder niftyDialogBuilder = NiftyDialogBuilder.getInstance(activity);
    niftyDialogBuilder
        .withTitle(activity.getResources().getString(R.string.load_data))
        .withMessage(message)
        .withDialogColor("#ED0D17")
        .withButton1Text(activity.getResources().getString(R.string.OK))
        .withDuration(400)
        .withEffect(Effectstype.Slidetop)
        .isCancelableOnTouchOutside(false)
        .setButton1Click(v2 -> {
          session.deleteAllSessionUser();
          Intent intent = new Intent(activity, LoginActivity.class);
          activity.startActivity(intent);
          activity.finish();
          niftyDialogBuilder.dismiss();
        })
        .show();
  }

  public static void showLoadDataError(Activity activity) {
    NiftyDialogBuilder showDataErrWithMessage = NiftyDialogBuilder.getInstance(activity);
    showDataErrWithMessage
        .withTitle(activity.getResources().getString(R.string.load_data))
        .withMessage(R.string.load_data_err)
        .withDialogColor("#ED0D17")
        .withButton1Text(activity.getResources().getString(R.string.OK))
        .withDuration(400)
        .withEffect(Effectstype.Slidetop)
        .isCancelableOnTouchOutside(false)
        .setButton1Click(v2 -> {
          // activity.setResult(Activity.RESULT_OK);
//          Intent intent = new Intent(activity, MatchDetailActivity.class);
//          intent.putExtra(MatchesActivity.EXTRA_MATCH_ID, matchSelected.getId());
//          activity.startActivityForResult(intent, MatchesFragment.REQUEST_DETAIL_MATCH);
          showDataErrWithMessage.dismiss();
//          activity.finish();

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
                if(t instanceof NoConnectivityException) {
                  UtilMessages.withoutInternet(activity);
                }
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
    Drawable d = Objects.requireNonNull(ContextCompat.getDrawable(activity, R.drawable.claim_icon));
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
        .setButton1Click(v -> {
          if (!(activity instanceof LoginActivity) && !(activity instanceof AccessActivity)) {
           Intent intent = new Intent(activity, AccessActivity.class);
           activity.startActivity(intent);
           activity.finish();
          }
          dialogBuilder.dismiss();
        })
        .show();
  }

  public static void showAppointmentInfoChange(Activity activity,
                                         Service serviceSelected,
                                         Appointment appointment) {
    String date = appointment.getDateFrom().split("T")[0];
    String time = appointment.getDateFrom().split("T")[1];

    NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(activity);
    dialogBuilder
        .withTitle("    " + activity.getString(R.string.appointment_info))
        .withIcon(R.drawable.ic_dates_icon_white)
        .withDividerColor(R.color.daysLabelColor)
            .withMessage(String.format(activity.getString(R.string.appointment_msg), serviceSelected.getServiceName(), Util.formatDateToLocale(activity, date), time))
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

  public static void showAppointmentInfoNewOrChange(Activity activity,
                                                    Service serviceSelected,
                                                    Appointment appointment) {

    String date = appointment.getDateFrom().split("T")[0];
    String time = appointment.getDateFrom().split("T")[1];

    NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(activity);
    dialogBuilder
            .withTitle("    " + activity.getString(R.string.appointment_info))
            .withIcon(R.drawable.ic_dates_icon_white)
            .withDividerColor(R.color.daysLabelColor)
            .withMessage(String.format(activity.getString(R.string.appointment_msg_new_or_change), appointment.getServiceName(), Util.formatDateToLocale(activity, date), time))
            .withMessageColor("#FAD201")
            .withDialogColor(R.color.blueDotlinked)
            .withButton1Text(activity.getString(R.string.change))
            .withButton2Text(activity.getString(R.string.newAppointment))
            .withDuration(700)
            .withEffect(Effectstype.RotateBottom)
            .isCancelableOnTouchOutside(true)
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

  public static void showResponseToCreateUpdateAppointment(Activity activity, boolean isNew, String response) {
    int color = 0;
    String msg = "";
    if (isNew && response.toLowerCase().equals(activity.getString(R.string.OK).toLowerCase())) {
      msg = activity.getString(R.string.appointment_created_ok);
      color = Color.GREEN;
    } else if (isNew && response.toLowerCase().contains(activity.getString(R.string.err).toLowerCase())) {
      msg = activity.getString(R.string.appointment_created_error);
      color = Color.RED;
    } else if (!isNew && response.toLowerCase().equals(activity.getString(R.string.OK).toLowerCase())) {
      msg = activity.getString(R.string.appointment_update_ok);
      color = Color.GREEN;
    } else if (!isNew && response.toLowerCase().contains(activity.getString(R.string.err).toLowerCase())) {
      msg = activity.getString(R.string.appointment_update_error);
      color = Color.RED;
    } else if (!isNew && response.toLowerCase().equals((activity.getString(R.string.OK) + activity.getString(R.string.delete)).toLowerCase())) {
      msg = activity.getString(R.string.appointment_delete_ok);
      color = Color.GREEN;
    } else if (!isNew && response.toLowerCase().equals((activity.getString(R.string.err) + activity.getString(R.string.delete)).toLowerCase())) {
      msg = activity.getString(R.string.appointment_delete_error);
      color = Color.RED;
    }

    NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(activity);
    dialogBuilder
            .withTitle("    " + activity.getString(R.string.appointment_info))
            .withIcon(R.drawable.ic_dates_icon_white)
            .withDividerColor(R.color.daysLabelColor)
            .withMessage(msg)
            .withMessageColor(color)
            .withDialogColor(R.color.blueDotlinked)
            .withButton1Text(activity.getString(R.string.OK))
            .withDuration(700)
            .withEffect(Effectstype.RotateBottom)
            .isCancelableOnTouchOutside(false)
            .setButton1Click(v2 -> {
              Intent intent = new Intent(activity, BaseActivity.class);
              intent.putExtra(TAG_FRAGMENT, activity.getString(R.string.services));
              activity.startActivity(intent);
              activity.finish();
              dialogBuilder.dismiss();
            })
            .show();

  }

  public static void showCreateClaim(Activity activity, String subject) {
    int color;
    String msg;
    if (subject != null) {
      msg = String.format(activity.getString(R.string.claim_created_ok), subject);
      color = Color.GREEN;
    } else {
      msg = activity.getString(R.string.claim_created_error);
      color = Color.RED;
    }

    NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(activity);
    dialogBuilder
            .withTitle("    " + activity.getString(R.string.claim_info))
            .withIcon(R.drawable.ic_dates_icon_white)
            .withDividerColor(R.color.daysLabelColor)
            .withMessage(msg)
            .withMessageColor(color)
            .withDialogColor(R.color.blueDotlinked)
            .withButton1Text(activity.getString(R.string.OK))
            .withDuration(700)
            .withEffect(Effectstype.RotateBottom)
            .isCancelableOnTouchOutside(false)
            .setButton1Click(v2 -> {
              Intent intent = new Intent(activity, BaseActivity.class);
              intent.putExtra(TAG_FRAGMENT, activity.getString(R.string.services));
              activity.startActivity(intent);
              activity.finish();
              dialogBuilder.dismiss();
            })
            .show();

  }

  public static void showWrongCredentials(Activity activity) {
    NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(activity);
    dialogBuilder
            .withTitle("    " + activity.getString(R.string.login_info))
            .withIcon(R.drawable.ic_icon_personal)
            .withDividerColor(R.color.daysLabelColor)
            .withMessage(activity.getString(R.string.wrong_credentials))
            .withMessageColor(Color.WHITE)
            .withDialogColor(R.color.blueDotlinked)
            .withButton1Text(activity.getString(R.string.OK))
            .withDuration(700)
            .withEffect(Effectstype.RotateBottom)
            .isCancelableOnTouchOutside(false)
            .setButton1Click(v2 -> dialogBuilder.dismiss())
            .show();
  }
}


