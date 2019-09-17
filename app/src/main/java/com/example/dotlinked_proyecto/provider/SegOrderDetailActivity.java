package com.example.dotlinked_proyecto.provider;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.dotlinked_proyecto.Persistence.Session;
import com.example.dotlinked_proyecto.R;
import com.example.dotlinked_proyecto.Utils.CenterZoomLayoutManager;
import com.example.dotlinked_proyecto.Utils.UtilMessages;
import com.example.dotlinked_proyecto.api.connection.NoConnectivityException;
import com.example.dotlinked_proyecto.appServices.ProviderService;
import com.example.dotlinked_proyecto.bean.ServiceOrderDetail;
import com.example.dotlinked_proyecto.provider.Adapter.RecyclerViewOrderDetailAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.util.Log.d;

public class SegOrderDetailActivity extends AppCompatActivity {

  private RecyclerView recyclerView;
  private RecyclerViewOrderDetailAdapter adapter;
  private List<ServiceOrderDetail> serviceOrderDetailList;
  private ProviderService providerService;
  private String orderServiceId;
  private Session session;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    serviceOrderDetailList = new ArrayList<>();
    setContentView(R.layout.activity_seg_order_detail);

    session = new Session(this);
    providerService = new ProviderService();



    Bundle bundle = getIntent().getExtras();
    if (bundle != null) {
      orderServiceId = bundle.getString(getString(R.string.orderServiceId));
    }

    getOrdersDetail();
    onSetRecyclerView();


  }

  @SuppressWarnings("NullableProblems")
  private void getOrdersDetail() {
    String access_token = session.getToken().getAccess_token();
    Call<List<ServiceOrderDetail>> call =  providerService.listServiceOrderDetail(Integer.parseInt(orderServiceId),
                                                                            "bearer " + access_token);
    call.enqueue(new Callback<List<ServiceOrderDetail>>() {

      @Override
      public void onResponse(Call<List<ServiceOrderDetail>> call, Response<List<ServiceOrderDetail>> response) {
        if (response.body() != null && response.body().size() > 0) {
          serviceOrderDetailList = response.body();
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

  private void setRecyclerViewAdapter() {
    adapter = new RecyclerViewOrderDetailAdapter(this, serviceOrderDetailList);
    recyclerView.setAdapter(adapter);
  }


  private void onSetRecyclerView() {
    CenterZoomLayoutManager layoutManager =
            new CenterZoomLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
    recyclerView = findViewById(R.id.rv_service_orders_details);
    recyclerView.setLayoutManager(layoutManager);
    setRecyclerViewAdapter();
//    // Scroll to the position we want to snap to
//    layoutManager.scrollToPosition(0);
//    // Wait until the RecyclerView is laid out.
//    recyclerView.post(() -> {
//      // Shift the view to snap  near the center of the screen.
//      // This does not have to be precise.
//      int dx = recyclerView.getWidth();
//      recyclerView.scrollBy(-dx, 0);
//      // Assign the LinearSnapHelper that will initially snap the near-center view.
//      LinearSnapHelper snapHelper = new LinearSnapHelper();
//      snapHelper.attachToRecyclerView(recyclerView);
//    });
  }
}
