package com.example.dotlinked_proyecto.provider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dotlinked_proyecto.Persistence.Session;
import com.example.dotlinked_proyecto.R;
import com.example.dotlinked_proyecto.Utils.UtilMessages;
import com.example.dotlinked_proyecto.activities.login.AccessActivity;
import com.example.dotlinked_proyecto.api.connection.NoConnectivityException;
import com.example.dotlinked_proyecto.appServices.ProviderService;
import com.example.dotlinked_proyecto.bean.Order;
import com.example.dotlinked_proyecto.provider.Adapter.RecyclerViewProviderAdapter;
import com.example.dotlinked_proyecto.services.ServiceOrderActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.util.Log.d;

public class ProviderActivity extends AppCompatActivity {

  private final static String TAG_FRAGMENT = "fragment";
  private final static String TAG_AUTH = "needAuth";
  private Context context;


  private List<Order> orderList;
  private RecyclerViewProviderAdapter adapter;
  private RecyclerView rvOrders;
  private ProviderService providerService;
  private Session session;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_provider);
    context = getApplicationContext();

    session = new Session(this);

    Toolbar toolbar = findViewById(R.id.toolbar_provider);
    setSupportActionBar(toolbar);
    Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setDisplayShowHomeEnabled(true);
    setTitle(getString(R.string.orders_list));

    rvOrders = findViewById(R.id.rv_orders);
    LinearLayoutManager layoutManager = new LinearLayoutManager(context);
    rvOrders.setLayoutManager(layoutManager);
    DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvOrders.getContext(),
            layoutManager.getOrientation());
    dividerItemDecoration.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.divider_recycler)));
    rvOrders.addItemDecoration(dividerItemDecoration);
    setRecyclerViewAdapter(new ArrayList<>());

    getOrders();


  }

  @SuppressWarnings("NullableProblems")
  private void getOrders() {

    providerService = new ProviderService();
    String companyId = session.getCompanyIdUser();
    String access_token = session.getToken().getAccess_token();

    Call<List<Order>> call = providerService.listOrderByCompanyAndProvider(companyId, "bearer " + access_token);
    call.enqueue(new Callback<List<Order>>() {

      @Override
      public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
        if (response.body() != null && response.body().size() > 0) {
          orderList = response.body();
          setRecyclerViewAdapter(orderList);
        }  else {
          UtilMessages.showLoadDataError(ProviderActivity.this);
          Log.d("RESPONSE", "getOrders: " + getString(R.string.save_data_err));
        }
      }

      @Override
      public void onFailure(Call<List<Order>> call, Throwable t) {
        if(t instanceof NoConnectivityException) {
          UtilMessages.withoutInternet(ProviderActivity.this);
        } else {
          UtilMessages.showLoadDataError(ProviderActivity.this);
        }
        d("RESPONSE", "Error getOrders: " + t.getMessage());
      }
    });

  }

  private void setRecyclerViewAdapter(List<Order> orderList) {
    adapter = new RecyclerViewProviderAdapter(this, orderList);
    rvOrders.setAdapter(adapter);
  }

  @Override
  public boolean onSupportNavigateUp() {
    onBackPressed();
    return false;
  }

  @Override
  public void onBackPressed()  {
    Intent intent = new Intent(this, AccessActivity.class)
            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
    intent.putExtra(TAG_AUTH, false);
    intent.putExtra(TAG_FRAGMENT, true);
    startActivity(intent);

  }
}
