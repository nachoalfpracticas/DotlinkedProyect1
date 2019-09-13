package com.example.dotlinked_proyecto.claims;

import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageButton;

import com.example.dotlinked_proyecto.Persistence.Session;
import com.example.dotlinked_proyecto.R;
import com.example.dotlinked_proyecto.Utils.Check;
import com.example.dotlinked_proyecto.Utils.UtilMessages;
import com.example.dotlinked_proyecto.appServices.ClaimsPersonService;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.util.Log.d;

public class AddNewClaimActivity extends AppCompatActivity {
  private Check check;
  private TextInputLayout tilSubject;
  private AppCompatEditText etClaimSubject;
  private TextInputLayout tilDescription;
  private AppCompatEditText etClaimDescription;
  private AppCompatButton btnAction;
  private Session session;
  private ClaimsPersonService claimsPersonService;


  @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.new_claim_dialog);
    check = new Check(this);
    session = new Session(this);
    claimsPersonService = new ClaimsPersonService();

    AppCompatImageButton btnClose = findViewById(R.id.button_close);
    btnAction = findViewById(R.id.btn_action);
    btnAction.setEnabled(false);

    tilSubject = findViewById(R.id.til_subject);
    etClaimSubject = findViewById(R.id.et_subject);

    tilDescription = findViewById(R.id.til_description);
    etClaimDescription = findViewById(R.id.et_description);

    btnClose.setOnClickListener(v -> {
      if (checkFields()) {
        UtilMessages.DismissFields(this);
      } else {
        finish();
      }
    });

    etClaimSubject.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
      }

      @Override
      public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
      }

      @Override
      public void afterTextChanged(Editable editable) {
        setButtonAction();
      }
    });

    etClaimDescription.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
      }

      @Override
      public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
      }

      @Override
      public void afterTextChanged(Editable editable) {
        setButtonAction();
      }
    });
  }

  @Override
  public void onBackPressed() {
    finish();
  }

  public boolean checkFields() {
    return check.checkEditText(tilSubject, etClaimSubject)
            && check.checkEditText(tilDescription, etClaimDescription);
  }

  @SuppressWarnings("NullableProblems")
  public void setButtonAction() {
    if (!checkFields()) {
      btnAction.setText(getString(R.string.action));
      btnAction.setEnabled(false);
    } else {
      btnAction.setText(getString(R.string.save));
      btnAction.setEnabled(true);
      btnAction.setOnClickListener(view -> {
        String subject = Objects.requireNonNull(etClaimSubject.getText()).toString();
        String description = Objects.requireNonNull(etClaimDescription.getText()).toString();
        int personId = session.getTenantSelect().getPersonId();
        int companyId = Integer.parseInt(session.getCompanyIdUser());
        String access_toke = session.getToken().getAccess_token();
        Call<String> call = claimsPersonService.createClaim(subject, description, personId, companyId, "bearer " + access_toke);
        call.enqueue(new Callback<String>() {
          @Override
          public void onResponse(Call<String> call, Response<String> response) {
            if (response.body() != null) {
              if (response.body().toLowerCase().equals(getString(R.string.OK).toLowerCase())) {
                UtilMessages.showCreateClaim(AddNewClaimActivity.this, subject);
              } else {
                UtilMessages.showCreateClaim(AddNewClaimActivity.this, null);

              }
            }
          }

          @Override
          public void onFailure(Call<String> call, Throwable t) {
            d("RESPONSE", "Error createClaim: " + t.getCause());
          }
        });

      });
    }
  }
}
