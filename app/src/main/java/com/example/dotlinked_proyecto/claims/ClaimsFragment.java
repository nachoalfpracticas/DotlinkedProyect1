package com.example.dotlinked_proyecto.claims;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dotlinked_proyecto.API.Class.Token;
import com.example.dotlinked_proyecto.R;
import com.example.dotlinked_proyecto.appServices.ClaimsPersonService;
import com.example.dotlinked_proyecto.bean.Claim;
import com.example.dotlinked_proyecto.claims.Adapter.RecyclerViewClaimsAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.util.Log.d;

public class ClaimsFragment extends Fragment {
  private static final String ARG_TOKEN = "token";
  private Context context;
  private String access_token;

  private AppCompatEditText tvClaimDate;
  private FloatingActionButton addClaim;
  private AppCompatTextView tvClaimSubject;
  private RecyclerView rvClaimsList;

  private RecyclerViewClaimsAdapter claimsAdapter;
  private ClaimsPersonService listClaimsService;
  private List<Claim> claimList;
  private AppCompatTextView tvClaimStatus;
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
    listClaimsService = new ClaimsPersonService();
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
          tvClaimDate.setText(claimList.get(0).getBroadcastDate().split("T")[0]);
          tvClaimSubject.setText(claimList.get(0).getSubject());
          tvClaimStatus.setText(claimList.get(0).getDescription());
          claimsAdapter = new RecyclerViewClaimsAdapter(context, claimList);
          rvClaimsList.setAdapter(claimsAdapter);
          claimsAdapter.setClickListener((view, position) -> {
            Claim claim = claimList.get(position);
            Intent intent = new Intent(getActivity(), ClaimDetailActivity.class);
            intent.putExtra("claim", new Gson().toJson(claim));
            context.startActivity(intent);
          });
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
    addClaim = view.findViewById(R.id.btnAddClaims);
    tvClaimDate = view.findViewById(R.id.claim_date);
    tvClaimSubject = view.findViewById(R.id.claim_subject);
    tvClaimStatus = view.findViewById(R.id.claim_status);
    rvClaimsList = view.findViewById(R.id.rv_claims);
    LinearLayoutManager layoutManager = new LinearLayoutManager(context);
    rvClaimsList.setLayoutManager(layoutManager);
    DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvClaimsList.getContext(),
            layoutManager.getOrientation());
    dividerItemDecoration.setDrawable(context.getResources().getDrawable(R.drawable.divider_recycler));
    rvClaimsList.addItemDecoration(dividerItemDecoration);

    addClaim.setOnClickListener(view1 -> addNewClaim());

    return view;
  }


  private void addNewClaim() {
    Intent intent = new Intent(getActivity(), AddNewClaimActivity.class);
    startActivity(intent);
  }

}
