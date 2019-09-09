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

import com.example.dotlinked_proyecto.R;
import com.example.dotlinked_proyecto.activities.BaseActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.widget.Toast.makeText;

public class Util {


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
    emailIntent.setDataAndType(Uri.parse("mailto:"), "text/plain");
    emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
    emailIntent.putExtra(Intent.EXTRA_CC, CC);
    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "");
    emailIntent.putExtra(Intent.EXTRA_TEXT, ""); // * configurar email aquí!

    try {
      context.startActivity(Intent.createChooser(emailIntent,"Enviar email"));
      Log.i("EMAIL", "Enviando email...");
    }
    catch (android.content.ActivityNotFoundException e) {
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
    }
    /*else {
      if (activity.getString(R.string.rol_tenant).equals(rol)) {
        intent = new Intent(activity, BaseActivity.class);
        activity.startActivity(intent);
      } else if (activity.getString(R.string.rol_provider).equals(rol)) {
        intent = new Intent(activity, .class);
        activity.startActivity(intent);
      } */
    else {
      activity.finish();
      if (activity.getString(R.string.rol_tenant).equals(rol) ||
          activity.getString(R.string.rol_contact).equals(rol)) {
        intent = new Intent(activity, BaseActivity.class);
        activity.startActivity(intent);
      } else if (activity.getString(R.string.rol_provider).equals(rol)) {
        intent = new Intent(activity, BaseActivity.class);
        activity.startActivity(intent);
      } else if (activity.getString(R.string.rol_doctor).equals(rol)) {
        intent = new Intent(activity, BaseActivity.class);
        activity.startActivity(intent);
      } else if (activity.getString(R.string.rol_super_admin).equals(rol)) {
        intent = new Intent(activity, BaseActivity.class);
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

  public static Date converDate(String dateToConvert) {
    Date date = null;
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    try {
      date = formatter.parse(dateToConvert);
    } catch (ParseException e) {
      e.printStackTrace();
    }

    return date;
  }
}
