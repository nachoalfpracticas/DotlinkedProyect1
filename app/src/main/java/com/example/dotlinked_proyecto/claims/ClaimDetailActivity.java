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

    if (claim != null) {

      tvUserName.setText(session.getSessionUser());
      tvClaimSubject.setText(claim.getSubject());
      tvClaimDescription.setText(claim.getDescription());
      tvClaimStatus.setText(claim.getDescriptionStatus());

      DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
      Date date = null;
      try {
        date = formatter.parse(claim.getBroadcastDate());
      } catch (ParseException e) {
        e.printStackTrace();
      }
      String patternFormat = getString(R.string.date_format);
      DateFormat df = new SimpleDateFormat(patternFormat, Locale.getDefault());
      assert date != null;
      String startDate = df.format(date);
      tvClaimDate.setText(startDate);

      if (claim.getUpdateDate() != null && !claim.getUpdateDate().isEmpty()) {
        try {
          date = formatter.parse(claim.getUpdateDate().split("T")[0]);
          assert date != null;
          startDate = df.format(date);
          tvClaimUpdateDate.setText(startDate);
        } catch (ParseException e) {
          e.printStackTrace();
        }
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
