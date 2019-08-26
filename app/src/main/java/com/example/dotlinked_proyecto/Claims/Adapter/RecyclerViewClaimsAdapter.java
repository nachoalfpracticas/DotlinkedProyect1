package com.example.dotlinked_proyecto.Claims.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dotlinked_proyecto.R;
import com.example.dotlinked_proyecto.bean.Claim;

import java.util.List;

public class RecyclerViewClaimsAdapter extends RecyclerView.Adapter<RecyclerViewClaimsAdapter.ViewHolder> {

  private List<Claim> claimData;
  private LayoutInflater mInflater;
  private ItemClickListener mClickListener;

  public RecyclerViewClaimsAdapter(Context context, List<Claim> claimData) {
    this.mInflater = LayoutInflater.from(context);
    this.claimData = claimData;
  }


  @NonNull
  @Override
  public RecyclerViewClaimsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = mInflater.inflate(R.layout.recyclerview_row, parent, false);
    return new RecyclerViewClaimsAdapter.ViewHolder(view);
  }

  // binds the data to the TextView in each row
  @Override
  public void onBindViewHolder(@NonNull RecyclerViewClaimsAdapter.ViewHolder holder, int position) {
    Claim claim = claimData.get(position);
    holder.tvClaimSubject.setText(claim.getSubject());
  }

  // total number of rows
  @Override
  public int getItemCount() {
    return claimData.size();
  }

  // convenience method for getting data at click position
  public Claim getItem(int id) {
    return claimData.get(id);
  }

  // allows clicks events to be caught
  public void setClickListener(RecyclerViewClaimsAdapter.ItemClickListener itemClickListener) {
    this.mClickListener = itemClickListener;
  }

  // parent activity will implement this method to respond to click events
  public interface ItemClickListener {
    void onItemClick(View view, int position);
  }


  public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    TextView tvClaimSubject;

    ViewHolder(View itemView) {
      super(itemView);
      tvClaimSubject = itemView.findViewById(R.id.tvEventTitle);
      itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
      if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
    }
  }
}
