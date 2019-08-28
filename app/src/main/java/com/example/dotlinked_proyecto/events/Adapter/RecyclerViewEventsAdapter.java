package com.example.dotlinked_proyecto.events.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dotlinked_proyecto.R;
import com.example.dotlinked_proyecto.bean.Event;

import java.util.List;

public class RecyclerViewEventsAdapter extends RecyclerView.Adapter<RecyclerViewEventsAdapter.ViewHolder> {
  private List<Event> eventsData;
  private LayoutInflater mInflater;
  private ItemClickListener mClickListener;

  // data is passed into the constructor
  public RecyclerViewEventsAdapter(Context context, List<Event> data) {
    this.mInflater = LayoutInflater.from(context);
    this.eventsData = data;
  }


  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = mInflater.inflate(R.layout.recycler_events_row, parent, false);
    return new ViewHolder(view);
  }

  // binds the data to the TextView in each row
  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    Event event = eventsData.get(position);
    holder.tvEventTitle.setText(event.getTitulo());
  }

  // total number of rows
  @Override
  public int getItemCount() {
    return eventsData.size();
  }

  // convenience method for getting data at click position
  public Event getItem(int id) {
    return eventsData.get(id);
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
    TextView tvEventTitle;

    ViewHolder(View itemView) {
      super(itemView);
      tvEventTitle = itemView.findViewById(R.id.tvEventTitle);
      itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
      if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
    }
  }
}
