package com.example.dotlinked_proyecto.provider.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dotlinked_proyecto.R;
import com.example.dotlinked_proyecto.bean.ServiceOrderDetail;

import java.util.List;

public class RecyclerViewOrderDetailAdapter extends RecyclerView.Adapter<RecyclerViewOrderDetailAdapter.ViewHolder>{

  private List<ServiceOrderDetail> serviceOrderDetails;
  private LayoutInflater mInflater;
  private ItemClickListener mClickListener;

  // data is passed into the constructor
  public RecyclerViewOrderDetailAdapter(Context context, List<ServiceOrderDetail> data) {
    this.mInflater = LayoutInflater.from(context);
    this.serviceOrderDetails = data;
  }


  @NonNull
  @Override
  public RecyclerViewOrderDetailAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = mInflater.inflate(R.layout.recycler_seg_orders_detail_row, parent, false);
    return new RecyclerViewOrderDetailAdapter.ViewHolder(view);
  }

  // binds the data to the TextView in each row
  @Override
  public void onBindViewHolder(@NonNull RecyclerViewOrderDetailAdapter.ViewHolder holder, int position) {
    ServiceOrderDetail orderDetail = serviceOrderDetails.get(position);




  }

  // total number of rows
  @Override
  public int getItemCount() {
    return serviceOrderDetails.size();
  }

  // convenience method for getting data at click position
  public ServiceOrderDetail getItem(int id) {
    return serviceOrderDetails.get(id);
  }

  // allows clicks events to be caught
  public void setClickListener(RecyclerViewOrderDetailAdapter.ItemClickListener itemClickListener) {
    this.mClickListener = itemClickListener;
  }

  // parent activity will implement this method to respond to click events
  public interface ItemClickListener {
    void onItemClick(View view, int position);
  }


  public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


    ViewHolder(View itemView) {
      super(itemView);

      itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
      if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
    }
  }
}
