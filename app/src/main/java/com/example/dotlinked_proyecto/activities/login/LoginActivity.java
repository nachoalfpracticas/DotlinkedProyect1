package com.example.dotlinked_proyecto.activities.login;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.dotlinked_proyecto.Persistence.Session;
import com.example.dotlinked_proyecto.R;
import com.example.dotlinked_proyecto.Utils.Check;
import com.example.dotlinked_proyecto.Utils.Util;
import com.example.dotlinked_proyecto.Utils.UtilMessages;
import com.example.dotlinked_proyecto.api.Class.Token;
import com.example.dotlinked_proyecto.api.connection.NoConnectivityException;
import com.example.dotlinked_proyecto.appServices.LoginService;
import com.example.dotlinked_proyecto.appServices.RolService;
import com.example.dotlinked_proyecto.bean.Person;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressWarnings("NullableProblems")
public class LoginActivity extends AppCompatActivity {

  private EditText edtUserName;
  private EditText edtPassword;
  private TextView userForgotPassword;
  private TextInputLayout tilEmail;
  private TextInputLayout tilPassword;

  private LoginService loginService;
  private RolService rolService;

  private Check check;
  private String[] roles;
  private Session session;
  private boolean rememberMe;
  @SuppressLint("StaticFieldLeak")
  public static Context context;

  @RequiresApi(api = Build.VERSION_CODES.M)
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);
    check = new Check(this);
    session = new Session(LoginActivity.this);
    context = getApplicationContext();
    loginService = new LoginService();
    rolService = new RolService();
    roles = new String[2];


    // Si guardado en session el  recuérdame y además el username,
    // se pasará directamente a la AccessActivity.
    // Esto es cuando venimos de restaurar la App.
    // En esa actividad deberá escribir de nuevo la password, param needAuth = true.
    if (!session.getSessionUser().isEmpty() && session.gerUserRememberMe()) {
      startAccessActivity(true);
    }

    userForgotPassword = findViewById(R.id.forgotPass);
    userForgotPassword.setVisibility(View.GONE);

    tilEmail = findViewById(R.id.til_email);
    tilPassword = findViewById(R.id.til_password);

    CheckBox checkRememberMe = findViewById(R.id.checkRememberMe);
    CheckBox checkFingerPrint = findViewById(R.id.checkUseFingerprint);
    edtUserName = findViewById(R.id.et_email);
    edtPassword = findViewById(R.id.et_password);
    CardView btnSend = findViewById(R.id.cv_access);

    // Si el dispositivo dispone de sistema de huellas, se verá el checkbox para usarlo.
    if (Check.checkFingerprint(this)) {
      checkFingerPrint.setVisibility(View.VISIBLE);
    } else {
      checkFingerPrint.setVisibility(View.GONE);
    }
    // Si es visible (el checkbox), se podrá optar por la opción de usar la huella.
    checkFingerPrint.setOnCheckedChangeListener((buttonView, isChecked) -> {
      // TODO implementar en session el useFingerprint
      session.setUserUseFingerprint(isChecked);
      Log.d("APP", "Checked useFingerprint: " + isChecked);
    });

    // Si se marca se recuerda al usuario y no tiene que logearse constantemente.
    checkRememberMe.setOnCheckedChangeListener((buttonView, isChecked) -> {
      rememberMe = isChecked;
      session.setRememberMeUser(rememberMe);
      Log.d("APP", "Checked remember me: " + isChecked);
    });

    // Se desplaza a la pantalla para recuperar la contraseña, aparece sólo cua el usuario no "acierta" con la password.
    userForgotPassword.setOnClickListener(v -> {
      Intent intent = new Intent(this, ForgotPasswordActivity.class);
      startActivity(intent);
      finish();
    });

    btnSend.setOnClickListener(v1 -> {
      String username = edtUserName.getText().toString();
      String password = edtPassword.getText().toString();

      // Se obtiene la llamada Http de loginService.
      Call<Token> call = loginService.login(username, password);
      // Se comprueban los campos, si están vacíos o no cumplen con las reglas (Regex).
      if (checkFields()) {
        // Se inicia la llamada Http.
        call.enqueue(new Callback<Token>() {
          @Override
          public void onResponse(Call<Token> call, Response<Token> response) {
            if (response.body() != null) {
              Token token = response.body();
              if (getToken(token)) {
                Log.d("RESPONSE", "Response getToken " + token.toString());
                setPersonInfo(token.getAccess_token());
                //Ha ido bien y se hace la llamada Http para conseguir el/los rol/es
                ListRolesByToken(token.getAccess_token(), token.getUserName());
                session.setSessionUser(token.getUserName());
                session.setToken(token);
              }
            } else {
              // Las credenciales son incorrectas response = Bad request.
              Log.d("RESPONSE", "Error response getToken " + response.message());
              UtilMessages.showWrongCredentials(LoginActivity.this);
              Toast.makeText(LoginActivity.this, getString(R.string.wrong_credentials), Toast.LENGTH_LONG).show();
              userForgotPassword.setVisibility(View.VISIBLE);
            }
          }

          // Se ha producido algún error en la conexión ???
          @Override
          public void onFailure(Call call, Throwable t) {
            Log.d("RESPONSE", "Error getToken: " + t.getMessage());
            if(t instanceof NoConnectivityException) {
             UtilMessages.withoutInternet(LoginActivity.this);
            }
            Toast.makeText(LoginActivity.this, getString(R.string.error_connection), Toast.LENGTH_LONG).show();
          }
        });
      }
    });
  }

  private void setPersonInfo(String token) {
    Call<List<Person>> call = loginService.getPersonInfo("bearer " + token);
    call.enqueue(new Callback<List<Person>>() {
      @Override
      public void onResponse(Call<List<Person>> call, Response<List<Person>> response) {
        if (response.body() != null) {
          Person person = response.body().get(0);
          if (person != null)
            session.setTenantSelect(person);
          else
            UtilMessages.showLoadDataError(LoginActivity.this, response.message());
        }
      }

      @Override
      public void onFailure(Call<List<Person>> call, Throwable t) {
        UtilMessages.showLoadDataError(LoginActivity.this, t.getMessage());
        Log.d("RESPONSE", "Error setPersonInfo: " + t.getMessage());
      }
    });
  }

  private void ListRolesByToken(String access_token, String userName) {
    Call<String[]> call = rolService.getRoles("bearer " + access_token);
    call.enqueue(new Callback<String[]>() {
      @RequiresApi(api = Build.VERSION_CODES.N)
      @Override
      public void onResponse(Call<String[]> call, Response<String[]> response) {
        if (response.body() != null) {
          roles = response.body();
          roles = Util.translateRoles(LoginActivity.this, Arrays.asList(roles)).toArray(new String[0]);
          if (!Arrays.toString(roles).isEmpty()) {
            // roles = new String[]{"Inquilino", "Proveedor"};
            session.setUserRoles(new Gson().toJson(roles));
            Log.d("RESPONSE", "Response ListRolesByToken: " + Arrays.toString(roles));
            // Ha ido bien y se pasa a la actividad AccessActivity
            // En esa actividad no deberá escribir de nuevo la password, param needAuth = false.
            // puesto que venimos de loguearnos.
            startAccessActivity(false);
          }
        } else {
          Log.d("RESPONSE", "Error response ListRolesByToken " + response.message());
        }
      }

      @Override
      public void onFailure(Call call, Throwable t) {
        UtilMessages.showLoadDataError(LoginActivity.this, t.getMessage());
        Log.d("RESPONSE", "Error ListRolesByToken: " + t.getMessage());
      }
    });
  }


  public void registerUser(View view) {
  }


  public boolean checkFields() {
    boolean successEmail = check.checkEmail(edtUserName, tilEmail);
    if (!successEmail) {
      Toast.makeText(this, Objects.requireNonNull(tilEmail.getError()).toString(), Toast.LENGTH_LONG).show();
      return false;
    }
    check.checkPassword(edtPassword.getText().toString(), tilPassword);
    if (tilPassword.isErrorEnabled()) {
      Toast.makeText(this, Objects.requireNonNull(tilPassword.getError()).toString(), Toast.LENGTH_LONG).show();
      return false;
    }
    return true;
  }

  public boolean getToken(Token token) {
    boolean loginResult;
    loginResult = token != null;
    if (!loginResult && !tilPassword.isErrorEnabled()) {
      Toast.makeText(this, getString(R.string.wrong_credentials), Toast.LENGTH_LONG).show();
      userForgotPassword.setVisibility(View.VISIBLE);
    }
    return loginResult;
  }

  private void startAccessActivity(boolean needAuth) {
    Intent intent = new Intent(LoginActivity.this, AccessActivity.class);
    intent.putExtra("needAuth", needAuth);
    startActivity(intent);
    finish();
  }

  @Override
  public void onBackPressed() {
    UtilMessages.showExitMessage(this);
  }
}

