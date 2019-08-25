package com.example.dotlinked_proyecto.Claims;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import com.example.dotlinked_proyecto.API.Class.Token;
import com.example.dotlinked_proyecto.R;
import com.example.dotlinked_proyecto.bean.Claim;
import com.example.dotlinked_proyecto.services.ListClaimsPersonService;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.util.Log.d;

public class ClaimsFragment extends Fragment {
  private static final String ARG_TOKEN = "token";
  private String access_token;
  private Context context;
  private ListClaimsPersonService listClaimsService;
  private AppCompatTextView tvClaimDate;
  private List<Claim> claimList;
  private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.GERMANY);

  public ClaimsFragment() {
    // Required empty public constructor
  }


  // TODO: Rename and change types and number of parameters
  public static ClaimsFragment newInstance(Token token) {
    ClaimsFragment fragment = new ClaimsFragment();
    Bundle args = new Bundle();
    args.putString(ARG_TOKEN, token.getAccess_token());

    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    context = getContext();
    listClaimsService = new ListClaimsPersonService();
    if (getArguments() != null) {
      access_token = getArguments().getString(ARG_TOKEN);
    }
    getPersonClaims();
  }

  @SuppressWarnings("NullableProblems")
  private void getPersonClaims() {
    Call<List<Claim>> call = listClaimsService.getListComplaintsPerson("bearer " + access_token);
    call.enqueue(new Callback<List<Claim>>() {
      @RequiresApi(api = Build.VERSION_CODES.N)
      @Override
      public void onResponse(Call<List<Claim>> call, Response<List<Claim>> response) {
        if (response.body() != null && response.body().size() > 0 && response.body().get(0).getClaimId() != 0) {
          claimList = response.body();
          claimList.forEach(claim -> d("RESPONSE", "Response getPersonClaims: " + claim.toString()));
          tvClaimDate.setText(df.format(claimList.get(0).getBroadcastDate()));
        } else {
          d("RESPONSE", "Response getPersonClaims []" + response.message());
        }
      }

      @Override
      public void onFailure(Call<List<Claim>> call, Throwable t) {
        d("RESPONSE", "Error getPersonClaims: " + t.getCause());
      }
    });
  }

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_claims, container, false);
    tvClaimDate = view.findViewById(R.id.claim_date);


    return view;
  }

}
