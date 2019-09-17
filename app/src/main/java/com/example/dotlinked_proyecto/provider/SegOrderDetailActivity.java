package com.example.dotlinked_proyecto.provider;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dotlinked_proyecto.Persistence.Session;
import com.example.dotlinked_proyecto.R;
import com.example.dotlinked_proyecto.Utils.CenterZoomLayoutManager;
import com.example.dotlinked_proyecto.Utils.UtilMessages;
import com.example.dotlinked_proyecto.activities.login.AccessActivity;
import com.example.dotlinked_proyecto.api.connection.NoConnectivityException;
import com.example.dotlinked_proyecto.appServices.ProviderService;
import com.example.dotlinked_proyecto.bean.Order;
import com.example.dotlinked_proyecto.bean.ServiceOrderDetail;
import com.example.dotlinked_proyecto.provider.Adapter.RecyclerViewOrderDetailAdapter;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.util.Log.d;

public class SegOrderDetailActivity extends AppCompatActivity {

  private final static String TAG_FRAGMENT = "fragment";
  private final static String TAG_AUTH = "needAuth";

  private RecyclerView recyclerView;
  private RecyclerViewOrderDetailAdapter adapter;
  private List<ServiceOrderDetail> serviceOrderDetailList;
  private ProviderService providerService;
  private Order orderService;
  private Session session;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    serviceOrderDetailList = new ArrayList<>();
    setContentView(R.layout.activity_seg_order_detail);

    Toolbar toolbar = findViewById(R.id.toolbar_service_order_detail);
    setSupportActionBar(toolbar);
    Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setDisplayShowHomeEnabled(true);

    session = new Session(this);
    providerService = new ProviderService();
    recyclerView = findViewById(R.id.rv_service_orders_details);
//    LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
//    recyclerView.setLayoutManager(layoutManager);
    setRecyclerViewAdapter(new ArrayList<>());

    Bundle bundle = getIntent().getExtras();
    if (bundle != null) {
      orderService = new Gson().fromJson(bundle.getString(getString(R.string.orderService)), Order.class);
      setTitle(String.format(getString(R.string.order_number_toolbar), orderService.getNumOrder()));
    }

    getOrdersDetail();
  }

  @SuppressWarnings("NullableProblems")
  private void getOrdersDetail() {
    String access_token = session.getToken().getAccess_token();
    Call<List<ServiceOrderDetail>> call =  providerService.listServiceOrderDetail(orderService.getOrderHisLd(),
                                                                            "bearer " + access_token);
    call.enqueue(new Callback<List<ServiceOrderDetail>>() {

      @Override
      public void onResponse(Call<List<ServiceOrderDetail>> call, Response<List<ServiceOrderDetail>> response) {
        if (response.body() != null && response.body().size() > 0) {
          serviceOrderDetailList = response.body();
          onSetRecyclerView(0);
        } else {

        }
      }

      @Override
      public void onFailure(Call<List<ServiceOrderDetail>> call, Throwable t) {
        if(t instanceof NoConnectivityException) {
          UtilMessages.withoutInternet(SegOrderDetailActivity.this);
        } else {
          UtilMessages.showLoadDataError(SegOrderDetailActivity.this);
        }
        d("RESPONSE", "Error getOrdersDetail: " + t.getMessage());
      }
    });
  }

  private void setRecyclerViewAdapter(List<ServiceOrderDetail> serviceOrderDetailList) {
    adapter = new RecyclerViewOrderDetailAdapter(this, serviceOrderDetailList);
    recyclerView.setAdapter(adapter);
  }


  private void onSetRecyclerView(int position) {
    CenterZoomLayoutManager layoutManager =
            new CenterZoomLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

    recyclerView.setLayoutManager(layoutManager);
    setRecyclerViewAdapter(serviceOrderDetailList);
    // Scroll to the position we want to snap to
    layoutManager.scrollToPosition(position);
    // Wait until the RecyclerView is laid out.
    recyclerView.post(() -> {
      // Shift the view to snap  near the center of the screen.
      // This does not have to be precise.
      int dx = recyclerView.getWidth();
      recyclerView.scrollBy(-dx, 0);
      // Assign the LinearSnapHelper that will initially snap the near-center view.
      LinearSnapHelper snapHelper = new LinearSnapHelper();
      snapHelper.attachToRecyclerView(recyclerView);
    });
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    if (item.getItemId() == R.id.home) {
      navigateToAccessActivity();
    }
    return super.onOptionsItemSelected(item);
  }

  private void navigateToAccessActivity() {
    Intent intent = new Intent(this, AccessActivity.class)
        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
    intent.putExtra(TAG_AUTH, false);
    intent.putExtra(TAG_FRAGMENT, true);
    startActivity(intent);
    finish();
  }

  @Override
  public boolean onSupportNavigateUp() {
    onBackPressed();
    return false;
  }

  @Override
  public void onBackPressed()  {
    finish();
  }
}
