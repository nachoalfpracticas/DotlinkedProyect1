package com.example.dotlinked_proyecto.claims;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;

import com.example.dotlinked_proyecto.Persistence.Session;
import com.example.dotlinked_proyecto.R;
import com.example.dotlinked_proyecto.bean.Claim;
import com.google.gson.Gson;

public class ClaimDetailActivity extends AppCompatActivity {

  Claim claim;
  Session session;
  private AppCompatTextView tvClaimSubject;
  private AppCompatTextView tvClaimDate;
  private AppCompatTextView tvClaimStatus;
  private AppCompatTextView tvUserName;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_claim_detail);
    session = new Session(this);
    Toolbar toolbar = findViewById(R.id.toolbar_claim_detail);
    setSupportActionBar(toolbar);

    Bundle bundle = getIntent().getExtras();
    if (bundle != null) {
      String strClaim = bundle.getString("claim", "");
      claim = new Gson().fromJson(strClaim, Claim.class);
    }

    tvUserName = findViewById(R.id.claim_user);
    tvClaimSubject = findViewById(R.id.claim_subject);
    tvClaimDate = findViewById(R.id.claim_broadcast_date);
    tvClaimStatus = findViewById(R.id.claim_status);

    if (claim != null) {

      tvUserName.setText(session.getSessionUser());
      tvClaimSubject.setText(claim.getSubject());
      tvClaimDate.setText(claim.getBroadcastDate());
      tvClaimStatus.setText(claim.getDescription());
      setTitle(String.format(getString(R.string.claim_Id), " : " + claim.getClaimId()));

    }


  }

  @Override
  public void onBackPressed() {
    finish();
  }
}
