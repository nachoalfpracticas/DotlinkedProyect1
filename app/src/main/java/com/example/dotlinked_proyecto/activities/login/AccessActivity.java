package com.example.dotlinked_proyecto.activities.login;

import android.annotation.TargetApi;
import android.app.KeyguardManager;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.security.keystore.KeyProperties;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.cardview.widget.CardView;

import com.example.dotlinked_proyecto.API.Class.Token;
import com.example.dotlinked_proyecto.Persistence.Session;
import com.example.dotlinked_proyecto.R;
import com.example.dotlinked_proyecto.Utils.Check;
import com.example.dotlinked_proyecto.Utils.Util;
import com.example.dotlinked_proyecto.Utils.UtilMessages;
import com.example.dotlinked_proyecto.appServices.CompanyByRolService;
import com.example.dotlinked_proyecto.bean.Company;
import com.google.gson.Gson;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccessActivity extends AppCompatActivity {

  private static final String KEY_NAME = "FingerPrintPoC";
  private String[] roles;
  private String userName;
  private boolean needAuth;
  private String rol;
  private Session session;
  private FingerprintManager fingerprintManager;
  private KeyStore keyStore;
  private KeyguardManager keyguardManager;
  private Cipher cipher;
  private List<Company> companyList;
  private CompanyByRolService companyByRolService;
  private String companyName;
  private AppCompatSpinner spnCompanies;
  private CardView btnAccess;
  private Bundle bundle;

  @TargetApi(Build.VERSION_CODES.N)
  @RequiresApi(api = Build.VERSION_CODES.M)
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_access);
    session = new Session(this);
    companyByRolService = new CompanyByRolService();

    bundle = getIntent().getExtras();
    if (bundle != null) {
      needAuth = bundle.getBoolean("needAuth", true);
    }

    roles = new Gson().fromJson(session.getUserRoles(), String[].class);
    userName = session.getSessionUser();

    CardView btnFingerPrint = findViewById(R.id.cv_fingerprint);
    btnFingerPrint.setEnabled(false);

    TextView registeredUser = findViewById(R.id.tv_backToLogin);
    registeredUser.setTextColor(Color.BLUE);
    registeredUser.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
    CheckBox checkNotRemember = findViewById(R.id.checkNotRememberMe);

    if (!session.gerUserRememberMe()) {
      checkNotRemember.setVisibility(View.GONE);
    }

    TextView tatWelcome = findViewById(R.id.tv_welcomeUser);
    btnAccess = findViewById(R.id.cv_access);
    btnAccess.setEnabled(false);
    AppCompatSpinner spn_roles = findViewById(R.id.sp_roles);
    spnCompanies = findViewById(R.id.spn_companiesByRol);

    registeredUser.setText(String.format(getString(R.string.not_user), userName));
    tatWelcome.setText(String.format(getResources().getString(R.string.well_come_user), userName));

    ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, roles);
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    spn_roles.setAdapter(adapter);

    checkNotRemember.setOnCheckedChangeListener((compoundButton, isChecked) -> {
      session.setRememberMeUser(isChecked);
      Log.d("APP", "Checked stop remember me: " + isChecked);
    });

    spn_roles.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @RequiresApi(api = Build.VERSION_CODES.N)
      @Override
      public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
        rol = adapterView.getItemAtPosition(pos).toString();
        session.setRolUserSelected(rol);
        String rolUnTrans = Util.unTranslateRoles(AccessActivity.this, rol);
        Token token = session.getToken();
        if (token != null) {
          ListCompaniesUserByRol(token.getAccess_token(), rolUnTrans);
          Toast.makeText(getApplicationContext(), String.format(getString(R.string.select_item), rol),
              Toast.LENGTH_SHORT).show();
        } else {
          session.deleteAllSessionUser();
          Intent intent = new Intent(AccessActivity.this, LoginActivity.class);
          startActivity(intent);
        }
      }

      @Override
      public void onNothingSelected(AdapterView<?> adapterView) {
        Toast.makeText(getApplicationContext(), String.format(getString(R.string.select_item), rol),
                Toast.LENGTH_SHORT).show();
      }
    });

    registeredUser.setOnClickListener(this::unRegisterUser);

    btnAccess.setOnClickListener(view -> Util.navigationTo(this, needAuth, rol, userName, companyName));

    // Si el dispositivo dispone de sistema de huellas, se verá el botón para usarlo.
    if (Check.checkFingerprint(this)) {

      if (session.getUserUseFingerprint()) {
        btnFingerPrint.setVisibility(View.VISIBLE);
      }
    } else {
      btnAccess.setVisibility(View.GONE);
    }
    btnFingerPrint.setOnClickListener(view -> {

    });
  }

  @RequiresApi(api = Build.VERSION_CODES.N)
  @SuppressWarnings("NullableProblems")
  private void ListCompaniesUserByRol(String access_token, String rol) {
    rol = Util.unTranslateRoles(this, rol);
    Call<List<Company>> call = companyByRolService.getCompanies(rol, "bearer " + access_token);
    call.enqueue(new Callback<List<Company>>() {
      @Override
      public void onResponse(Call<List<Company>> call, Response<List<Company>> response) {
        if (response.body() != null) {
          companyList = response.body();
          if (companyList.size() > 0 && !session.getUserUseFingerprint()) {
            btnAccess.setEnabled(true);
          }
          if (companyList.size() == 1 && roles.length == 1 && needAuth) {
            Util.navigationTo(AccessActivity.this, true, roles[0], userName, companyName);
          }
          // companyList.add(new Company(5, "MyCompany"));
          session.setCompaniesUserByRol(companyList);
          companyList.forEach(c -> Log.d("RESPONSE", "Response ListCompaniesUserByRol: " + c.toString()));
          ArrayList<String> companiesName = new ArrayList<>();
          for (Company company : companyList) {
            companiesName.add(company.getCompanyName());
          }
          ArrayAdapter<String> adapterCom = new ArrayAdapter<>(AccessActivity.this, android.R.layout.simple_spinner_item,
              companiesName);
          adapterCom.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
          spnCompanies.setAdapter(adapterCom);
          spnCompanies.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
              companyName = companiesName.get(pos);
              for (Company comp : companyList) {
                if (comp.getCompanyName().equals(companyName)) {
                  session.setCompanyIdUser(String.valueOf(comp.getCompanyId()));
                  Toast.makeText(getApplicationContext(), String.format(getString(R.string.select_item), companiesName),
                      Toast.LENGTH_SHORT).show();
                  return;
                }
              }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
          });
        } else {
          UtilMessages.showLoadDataError(AccessActivity.this, response.message());
          Log.d("RESPONSE", "Error response ListCompaniesUserByRol " + response.message());
        }
      }
      @Override
      public void onFailure(Call<List<Company>> call, Throwable t) {
        UtilMessages.showLoadDataError(AccessActivity.this, getString(R.string.load_data_err));
        Log.d("RESPONSE", "Error ListCompaniesUserByRol: " + t.getCause());

      }
    });
  }

  @RequiresApi(api = Build.VERSION_CODES.M)
  private void generateKey() {
    try {
      keyStore = KeyStore.getInstance("AndroidKeyStore");
    } catch (Exception e) {
      e.printStackTrace();
    }
    KeyGenerator keyGenerator;
    try {
      keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");
    } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
      throw new RuntimeException("Failed to get KeyGenerator instance", e);
    }
    try {
      keyStore.load(null);
      keyGenerator.init(new
          KeyGenParameterSpec.Builder(KEY_NAME,
          KeyProperties.PURPOSE_ENCRYPT |
              KeyProperties.PURPOSE_DECRYPT)
          .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
          .setUserAuthenticationRequired(true)
          .setEncryptionPaddings(
              KeyProperties.ENCRYPTION_PADDING_PKCS7)
          .build());
      keyGenerator.generateKey();
    } catch (NoSuchAlgorithmException |
        InvalidAlgorithmParameterException
        | CertificateException | IOException e) {
      throw new RuntimeException(e);
    }
  }

  @RequiresApi(api = Build.VERSION_CODES.M)
  public boolean cipherInit() {
    try {
      cipher = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/" + KeyProperties.BLOCK_MODE_CBC + "/" + KeyProperties.ENCRYPTION_PADDING_PKCS7);
    } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
      throw new RuntimeException("Failed to get Cipher", e);
    }
    try {
      keyStore.load(null);
      SecretKey key = (SecretKey) keyStore.getKey(KEY_NAME,
          null);
      cipher.init(Cipher.ENCRYPT_MODE, key);
      return true;
    } catch (KeyPermanentlyInvalidatedException e) {
      return false;
    } catch (KeyStoreException | CertificateException | UnrecoverableKeyException | IOException | NoSuchAlgorithmException | InvalidKeyException e) {
      throw new RuntimeException("Failed to init Cipher", e.getCause());
    }
  }

  private void unRegisterUser(View view) {
    Intent intent = new Intent(this, LoginActivity.class);
    session.deleteAllSessionUser();
    startActivity(intent);
    finish();
  }

  @Override
  public void onBackPressed() {
    UtilMessages.showExitMessage(this);
  }
}
