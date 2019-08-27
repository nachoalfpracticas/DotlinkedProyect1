package com.example.dotlinked_proyecto.claims;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageButton;

import com.example.dotlinked_proyecto.R;
import com.example.dotlinked_proyecto.Utils.Check;
import com.example.dotlinked_proyecto.Utils.UtilMessages;
import com.example.dotlinked_proyecto.activities.BaseActivity;
import com.google.android.material.textfield.TextInputLayout;

public class AddNewClaimActivity extends AppCompatActivity {
  private Check check;
  private TextInputLayout tilSubject;
  private AppCompatEditText etClaimSubject;
  private TextInputLayout tilDescription;
  private AppCompatEditText etClaimDescription;
  private AppCompatButton btnAction;


  @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.new_claim_dialog);
    check = new Check(this);

    AppCompatImageButton btnClose = findViewById(R.id.button_close);
    btnAction = findViewById(R.id.btn_action);
    btnAction.setEnabled(false);

    tilSubject = findViewById(R.id.til_subject);
    etClaimSubject = findViewById(R.id.et_subject);

    tilDescription = findViewById(R.id.til_description);
    etClaimDescription = findViewById(R.id.et_description);

    btnClose.setOnClickListener(v -> {
      if (!chekFields()) {
        UtilMessages.DissmisFields(this);
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
    Intent intent = new Intent(this, BaseActivity.class);
    startActivity(intent);
  }

  public boolean chekFields() {
    return check.checkEditText(tilSubject, etClaimSubject)
            || check.checkEditText(tilDescription, etClaimDescription);
  }

  public void setButtonAction() {
    if (!chekFields()) {
      btnAction.setText(getString(R.string.save));
      btnAction.setEnabled(true);
    } else {
      btnAction.setText(getString(R.string.action));
      btnAction.setEnabled(false);
    }
  }
}
