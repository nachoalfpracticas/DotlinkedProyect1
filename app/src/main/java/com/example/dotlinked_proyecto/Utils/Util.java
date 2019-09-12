package com.example.dotlinked_proyecto.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;

import com.example.dotlinked_proyecto.Persistence.Session;
import com.example.dotlinked_proyecto.R;
import com.example.dotlinked_proyecto.activities.BaseActivity;
import com.example.dotlinked_proyecto.activities.ProviderActivity;
import com.example.dotlinked_proyecto.appServices.ServicesCompanyService;
import com.example.dotlinked_proyecto.bean.Appointment;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.util.Log.d;
import static android.widget.Toast.makeText;

public class Util {

  private static List<Appointment> appointmentList;

  public static List<String> translateRoles(Activity activity, List<String> roles) {
    List<String> translatedRoles = new ArrayList<>();

    for (String rol : roles) {
      if (rol.toLowerCase().equals("Inquilino".toLowerCase()) && !translatedRoles.contains(activity.getString(R.string.rol_tenant))) {
        rol = activity.getString(R.string.rol_tenant);
        translatedRoles.add(rol);
      } else if (rol.toLowerCase().equals("Proveedor".toLowerCase()) && !translatedRoles.contains(activity.getString(R.string.rol_provider))) {
        rol = activity.getString(R.string.rol_provider);
        translatedRoles.add(rol);
      } else if (rol.toLowerCase().equals("Contacto".toLowerCase()) && !translatedRoles.contains(activity.getString(R.string.rol_contact))) {
        rol = activity.getString(R.string.rol_contact);
        translatedRoles.add(rol);
      }
    }
    return translatedRoles;
  }

  public static String unTranslateRoles(Activity activity, String rol) {
    if (rol.toLowerCase().equals(activity.getString(R.string.rol_tenant).toLowerCase())) {
      rol = "Inquilino";
    } else if (rol.toLowerCase().equals(activity.getString(R.string.rol_provider).toLowerCase())) {
      rol = "Proveedor";
    } else if (rol.toLowerCase().equals(activity.getString(R.string.rol_contact).toLowerCase())) {
      rol = "Contacto";
    } else if (rol.toLowerCase().equals(activity.getString(R.string.rol_doctor).toLowerCase())) {
      rol = "Medico";
    }
    return rol;
  }

  public static void sendEmail(Context context, String email) {
    String[] TO = {email}; //Direcciones email  a enviar.
    String[] CC = {""}; //Direcciones email con copia.
    Intent emailIntent = new Intent(Intent.ACTION_SEND);
    emailIntent.setData(Uri.parse("mailto:"));
    emailIntent.setType("text/plain");
    emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
    emailIntent.putExtra(Intent.EXTRA_CC, CC);
    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "");
    emailIntent.putExtra(Intent.EXTRA_TEXT, ""); // * configurar email aquí!

    try {
      context.startActivity(Intent.createChooser(emailIntent, "Enviar email"));
      Log.i("EMAIL", "Enviando email...");
    } catch (android.content.ActivityNotFoundException e) {
      makeText(context, "NO existe ningún cliente de email instalado!.", Toast.LENGTH_SHORT).show();
    }
  }

  public static void phoneCall(Context context, String tlf) {
    context.startActivity(new Intent(Intent.ACTION_DIAL).setData(Uri.parse("tel:" + tlf)));
  }

  @RequiresApi(api = Build.VERSION_CODES.N)
  public static void navigationTo(Activity activity,
                                  boolean needAuth,
                                  String rol,
                                  String userName,
                                  String companyName,
                                  CardView button) {
    Intent intent;
    if (needAuth) {
      UtilMessages.showLoginView(activity, userName, rol, companyName, button);
    } else {
      activity.finish();
      if (activity.getString(R.string.rol_tenant).equals(rol) ||
              activity.getString(R.string.rol_contact).equals(rol)) {
        intent = new Intent(activity, BaseActivity.class);
        activity.startActivity(intent);
      } else if (activity.getString(R.string.rol_provider).equals(rol)) {
        intent = new Intent(activity, ProviderActivity.class);
        activity.startActivity(intent);
      } else {
        makeText(activity, "ERROR: " + String.format(activity.getString(R.string.select_item), rol),
                Toast.LENGTH_SHORT).show();
      }
    }
  }

  public static boolean dateCompare(String dateToCompare) {
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    Calendar cal = Calendar.getInstance();
    Date date = new Date();
    Date dateNow = cal.getTime();
    try {
      date = df.parse(dateToCompare);
      assert date != null;
      cal.setTime(date);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return dateNow.after(date);
  }

  public static Date convertDate(String dateToConvert) {
    Date date = null;
    try {
      date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).parse(dateToConvert.replace("T", " "));
    } catch (ParseException e) {
      e.printStackTrace();
    }

    return date;
  }

  public static String formatDateToLocale(Activity activity, String dateToTransform) {
    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    Date date = null;
    try {
      date = formatter.parse(dateToTransform);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    String patternFormat = activity.getString(R.string.date_format);
    DateFormat df = new SimpleDateFormat(patternFormat, Locale.getDefault());
    assert date != null;
    return df.format(date);
  }

  public static String formatToDate(String dateToTransform) {
    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    Date date = null;
    try {
      date = formatter.parse(dateToTransform);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    assert date != null;
    return df.format(date);
  }


  @SuppressWarnings("NullableProblems")
  public static void updateReservedServicesOfUser(Activity activity, String dateInit) {
    ServicesCompanyService companyService = new ServicesCompanyService();
    Session session = new Session(activity);
    String access_token = session.getToken().getAccess_token();
    String rol = session.getRolUserSelected();
    String companyId = session.getCompanyIdUser();
    appointmentList = new ArrayList<>();
    Calendar cal = Calendar.getInstance();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    try {
      cal.setTime(Objects.requireNonNull(dateFormat.parse(dateInit)));
    } catch (ParseException e) {
      e.printStackTrace();
    }
    cal.add(Calendar.MONTH, 1);
    Date d = cal.getTime();
    String dateEnd = dateFormat.format(d);
    Call<List<Appointment>> call = companyService.getReservedServiceOfUser(rol, companyId, dateInit, dateEnd, "bearer " + access_token);
    call.enqueue(new Callback<List<Appointment>>() {
      @Override
      public void onResponse(Call<List<Appointment>> call, Response<List<Appointment>> response) {
        if (response.body() != null && response.body().size() > 0) {
          appointmentList = response.body();
          session.setAppointmentsOfUser(appointmentList);
        } else {
          Toast.makeText(activity, activity.getString(R.string.no_services_data), Toast.LENGTH_LONG).show();
        }
      }

      @Override
      public void onFailure(Call<List<Appointment>> call, Throwable t) {
        d("RESPONSE", "Error updateReservedServicesOfUser: " + t.getCause());
      }
    });
  }

}
