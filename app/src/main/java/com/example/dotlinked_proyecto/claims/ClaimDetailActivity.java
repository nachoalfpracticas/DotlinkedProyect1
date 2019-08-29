package com.example.dotlinked_proyecto.claims;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;

import com.example.dotlinked_proyecto.Persistence.Session;
import com.example.dotlinked_proyecto.R;
import com.example.dotlinked_proyecto.bean.Claim;
import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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

      DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
      Date date = null;
      try {
        date = formatter.parse(claim.getBroadcastDate());
      } catch (ParseException e) {
        e.printStackTrace();
      }
      String fechapatron = getString(R.string.date_format);
      DateFormat df = new SimpleDateFormat(fechapatron,Locale.getDefault());
      String sdate = df.format(date);
      tvClaimDate.setText(String.valueOf(sdate));

      tvClaimStatus.setText(claim.getDescription());
      setTitle(String.format(getString(R.string.claim_Id), " : " + claim.getClaimId()));

    }


  }

  @Override
  public void onBackPressed() {
    finish();
  }
}
