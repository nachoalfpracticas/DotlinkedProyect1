package com.example.dotlinked_proyecto.Utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.example.dotlinked_proyecto.R;
import com.example.dotlinked_proyecto.activities.BaseActivity;

import java.util.ArrayList;
import java.util.List;

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

  @RequiresApi(api = Build.VERSION_CODES.N)
  public static void navigationTo(Activity activity, boolean needAuth, String rol, String userName, String companyName) {
    Intent intent;
    if (needAuth) {
      UtilMessages.showLoginView(activity, userName, rol, companyName);
    }
    /*else {
      if (activity.getString(R.string.rol_tenant).equals(rol)) {
        intent = new Intent(activity, BaseActivity.class);
        activity.startActivity(intent);
      } else if (activity.getString(R.string.rol_provider).equals(rol)) {
        intent = new Intent(activity, .class);
        activity.startActivity(intent);
      } else {
        Toast.makeText(activity, "ERROR: " + String.format(activity.getString(R.string.select_item), rol),
                Toast.LENGTH_SHORT).show();
      }*/
    else {
      if ("Inquilino".equals(rol) || "Contacto".equals(rol)) {
        intent = new Intent(activity, BaseActivity.class);
        activity.startActivity(intent);
      } else if ("Proveedor".equals(rol)) {
        intent = new Intent(activity, BaseActivity.class);
        activity.startActivity(intent);
      } else if ("Medico".equals(rol)) {
        intent = new Intent(activity, BaseActivity.class);
        activity.startActivity(intent);
      } else if ("Superadministrador".equals(rol)) {
        intent = new Intent(activity, BaseActivity.class);
        activity.startActivity(intent);
      } else {
        Toast.makeText(activity, "ERROR: " + String.format(activity.getString(R.string.select_item), rol),
                Toast.LENGTH_SHORT).show();
      }
    }
  }
}
