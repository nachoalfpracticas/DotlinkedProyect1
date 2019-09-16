package com.example.dotlinked_proyecto.provider.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dotlinked_proyecto.R;
import com.example.dotlinked_proyecto.bean.Order;

import java.util.List;

public class RecyclerViewProviderAdapter extends RecyclerView.Adapter<RecyclerViewProviderAdapter.ViewHolder> {


  private List<Order> ordersData;
  private LayoutInflater mInflater;
  private RecyclerViewProviderAdapter.ItemClickListener mClickListener;

  // data is passed into the constructor
  public RecyclerViewProviderAdapter(Context context, List<Order> data) {
    this.mInflater = LayoutInflater.from(context);
    this.ordersData = data;
  }


  @NonNull
  @Override
  public RecyclerViewProviderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = mInflater.inflate(R.layout.recycler_orders_row, parent, false);
    return new RecyclerViewProviderAdapter.ViewHolder(view);
  }

  // binds the data to the TextView in each row
  @Override
  public void onBindViewHolder(@NonNull RecyclerViewProviderAdapter.ViewHolder holder, int position) {
    Order order = ordersData.get(position);
    holder.tvOrderTitle.setText(order.getTitle());
    holder.tvOrderStatus.setText(order.getStatus());
    holder.tvOrderNumber.setText(order.getNumOrder());
    holder.tvOrderDate.setText(order.getDate().split("T")[0]);
    holder.tvOrderInterventions.setText(String.valueOf(order.getNumObservation()));


  }

  // total number of rows
  @Override
  public int getItemCount() {
    return ordersData.size();
  }

  // convenience method for getting data at click position
  public Order getItem(int id) {
    return ordersData.get(id);
  }

  // allows clicks events to be caught
  public void setClickListener(RecyclerViewProviderAdapter.ItemClickListener itemClickListener) {
    this.mClickListener = itemClickListener;
  }

  // parent activity will implement this method to respond to click events
  public interface ItemClickListener {
    void onItemClick(View view, int position);
  }


  public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    TextView tvOrderTitle;
    TextView tvOrderDate;
    TextView tvOrderNumber;
    TextView tvOrderStatus;
    TextView tvOrderInterventions;

    ViewHolder(View itemView) {
      super(itemView);
      tvOrderTitle = itemView.findViewById(R.id.tv_order_title);
      tvOrderDate = itemView.findViewById(R.id.tv_order_date);
      tvOrderNumber = itemView.findViewById(R.id.tv_oder_number);
      tvOrderStatus = itemView.findViewById(R.id.tv_order_status);
      tvOrderInterventions = itemView.findViewById(R.id.tv_order_inter);

      itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
      if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
    }
  }
}
