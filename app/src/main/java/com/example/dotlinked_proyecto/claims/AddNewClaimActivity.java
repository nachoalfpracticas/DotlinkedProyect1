package com.example.dotlinked_proyecto.claims;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import com.example.dotlinked_proyecto.R;
import com.google.android.material.textfield.TextInputLayout;

public class AddNewClaimActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.new_claim_dialog);


    AppCompatButton btnClose = findViewById(R.id.button_close);
    AppCompatButton btnAction = findViewById(R.id.btn_action);
    TextInputLayout tilSubject = findViewById(R.id.til_subject);
    AppCompatEditText tvSubject = findViewById(R.id.tv_subject);
    AppCompatEditText etClaimSubject = findViewById(R.id.et_subject);

    TextInputLayout tilDescription = findViewById(R.id.til_description);
    AppCompatEditText tvDescription = findViewById(R.id.tv_description);
    AppCompatEditText etClaimDescription = findViewById(R.id.et_description);

    etClaimDescription.setOnClickListener(view -> {

    });

  }

  @Override
  public void onBackPressed() {
    super.onBackPressed();
  }
}
