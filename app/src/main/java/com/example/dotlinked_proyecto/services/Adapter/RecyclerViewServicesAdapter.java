package com.example.dotlinked_proyecto.services.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dotlinked_proyecto.R;
import com.example.dotlinked_proyecto.Utils.Util;
import com.example.dotlinked_proyecto.bean.Appointment;

import java.util.List;

public class RecyclerViewServicesAdapter extends RecyclerView.Adapter<RecyclerViewServicesAdapter.ViewHolder> {

  private List<Appointment> servicesData;
  private LayoutInflater mInflater;
  private ItemClickListener mClickListener;

  // data is passed into the constructor
  public RecyclerViewServicesAdapter(Context context, List<Appointment> data) {
    this.mInflater = LayoutInflater.from(context);
    this.servicesData = data;
  }


  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = mInflater.inflate(R.layout.recycler_services_row, parent, false);
    return new ViewHolder(view);
  }

  // binds the data to the TextView in each row
  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    Appointment service = getItem(position);
    String date = service.getDateFrom();
      if (Util.dateCompare(date)) {
          holder.tvServiceStatus.setText(R.string.service_status_passed);
          holder.tvServiceStatus.setTextColor(Color.RED);
      } else {
          holder.tvServiceStatus.setText(R.string.service_status_pending);
          holder.tvServiceStatus.setTextColor(Color.GREEN);
      }
    holder.tvServiceName.setText(service.getServiceName());
    holder.tvServiceLocation.setText(service.getLocation());
  }

  // total number of rows
  @Override
  public int getItemCount() {
    return servicesData.size();
  }

  // convenience method for getting data at click position
  private Appointment getItem(int id) {
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
    TextView tvServiceName;
    TextView tvServiceLocation;
      TextView tvServiceStatus;

    ViewHolder(View itemView) {
      super(itemView);
      tvServiceName = itemView.findViewById(R.id.tv_service_name);
      tvServiceLocation = itemView.findViewById(R.id.tv_service_location);
      tvServiceStatus = itemView.findViewById(R.id.tv_service_status);

      itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
      if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
    }
  }
}
