package com.example.dotlinked_proyecto.claims;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;

import com.example.dotlinked_proyecto.Persistence.Session;
import com.example.dotlinked_proyecto.R;
import com.example.dotlinked_proyecto.Utils.Util;
import com.example.dotlinked_proyecto.bean.Claim;
import com.google.gson.Gson;

import java.util.Objects;

public class ClaimDetailActivity extends AppCompatActivity {

  Claim claim;
  Session session;
  private AppCompatTextView tvClaimSubject;
  private AppCompatTextView tvClaimDate;
  private AppCompatTextView tvClaimStatus;
  private AppCompatTextView tvUserName;
  private AppCompatTextView tvClaimDescription;
  private AppCompatTextView tvClaimUpdateDate;
  private AppCompatTextView lblClaimUpdateDate;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_claim_detail);
    session = new Session(this);
    Toolbar toolbar = findViewById(R.id.toolbar_claim_detail);
    setSupportActionBar(toolbar);
    Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setDisplayShowHomeEnabled(true);

    Bundle bundle = getIntent().getExtras();
    if (bundle != null) {
      String strClaim = bundle.getString("claim", "");
      claim = new Gson().fromJson(strClaim, Claim.class);
    }

    tvUserName = findViewById(R.id.claim_user);
    tvClaimSubject = findViewById(R.id.claim_subject);
    tvClaimDate = findViewById(R.id.claim_broadcast_date);
    tvClaimStatus = findViewById(R.id.claim_status);
    tvClaimDescription = findViewById(R.id.claim_description);
    tvClaimUpdateDate = findViewById(R.id.claim_updateDate);
    lblClaimUpdateDate = findViewById(R.id.lblStatus);

    if (claim != null) {

      tvUserName.setText(session.getTenantSelect().getFullName());
      tvClaimSubject.setText(claim.getSubject());
      tvClaimDescription.setText(claim.getDescription());
      tvClaimStatus.setText(claim.getDescriptionStatus());

      tvClaimDate.setText(Util.formatDateToLocale(this, claim.getBroadcastDate()));

      if (claim.getUpdateDate() != null && !claim.getUpdateDate().isEmpty()) {
        lblClaimUpdateDate.setVisibility(View.VISIBLE);
        tvClaimUpdateDate.setVisibility(View.VISIBLE);
        tvClaimUpdateDate.setText(Util.unTranslateRoles(this, claim.getUpdateDate().split("T")[0]));
      }
      setTitle(String.format(getString(R.string.claim_Id), " : " + claim.getClaimId()));

    }


  }
  // https://es.stackoverflow.com/questions/8387/bóton-de-atrás-en-el-título-de-la-activity
  @Override
  public boolean onSupportNavigateUp() {
    onBackPressed();
    return false;
  }
  @Override
  public void onBackPressed() {
    finish();
  }
}
