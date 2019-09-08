package com.example.dotlinked_proyecto.services.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dotlinked_proyecto.R;
import com.example.dotlinked_proyecto.bean.Service;
import com.example.dotlinked_proyecto.bean.ServiceInfo;

import java.util.List;

public class RecyclerViewShceduleServiceAdapter extends RecyclerView.Adapter<RecyclerViewShceduleServiceAdapter.ViewHolder> {

  private List<ServiceInfo> servicesData;
  private LayoutInflater mInflater;
  private ItemClickListener mClickListener;

  // data is passed into the constructor
  public RecyclerViewShceduleServiceAdapter(Context context, List<ServiceInfo> data) {
    this.mInflater = LayoutInflater.from(context);
    this.servicesData = data;
  }


  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = mInflater.inflate(R.layout.recycler_service_shcedule_row, parent, false);
    return new ViewHolder(view);
  }

  // binds the data to the TextView in each row
  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    ServiceInfo service = (ServiceInfo) getItem(position);
    holder.tvServiceDate.setText(service.getDateInit());
    holder.tvServiceHour.setText(service.getHour().split(" ")[0]);
  }

  // total number of rows
  @Override
  public int getItemCount() {
    return servicesData.size();
  }

  // convenience method for getting data at click position
  private Service getItem(int id) {
    return servicesData.get(id);
  }

  // allows clicks events to be caught
  public void setClickListener(ItemClickListener itemClickListener) {
    this.mClickListener = itemClickListener;
  }

  // parent activity will implement this method to respond to click events
  public interface ItemClickListener {
    void onItemClick(View view, int position);
  }


  public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    TextView tvServiceDate;
    TextView tvServiceHour;

    ViewHolder(View itemView) {
      super(itemView);
      tvServiceDate = itemView.findViewById(R.id.tv_service_date_available);
      tvServiceHour = itemView.findViewById(R.id.tv_service_hour_available);

      itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
      if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
    }
  }
}
