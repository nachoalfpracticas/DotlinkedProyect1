package com.example.dotlinked_proyecto.personal.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dotlinked_proyecto.R;
import com.example.dotlinked_proyecto.bean.Person;

import java.util.List;


public class RecyclerViewPersonalAdapter extends RecyclerView.Adapter<RecyclerViewPersonalAdapter.ViewHolder> {

  private List<Person> personData;
  private LayoutInflater mInflater;
  private ItemClickListener mClickListener;

  public RecyclerViewPersonalAdapter(Context context, List<Person> personData) {
    this.mInflater = LayoutInflater.from(context);
    this.personData = personData;
  }

  @NonNull
  @Override
  public RecyclerViewPersonalAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = mInflater.inflate(R.layout.recycler_personal_row, parent, false);
    return new RecyclerViewPersonalAdapter.ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull RecyclerViewPersonalAdapter.ViewHolder holder, int position) {
    Person person = personData.get(position);
    holder.tvPersonName.setText(person.getFullName().split(",")[0]);
    holder.tvPersonLastName.setText(person.getFullName().split(",")[1]);
    holder.tvPersonMobile.setText(person.getMovil());
    holder.tvPersonEmail.setText(person.getEmail());
    holder.tvPersonRol.setText(person.getRol());
    holder.tvPersonMobile.setOnClickListener(view -> Log.d("RESPONSE", "Click in " + view.getId()));

  }

  @Override
  public int getItemCount() {
    return personData.size();
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
    TextView tvPersonName;
    TextView tvPersonLastName;
    TextView tvPersonMobile;
    TextView tvPersonEmail;
    TextView tvPersonRol;

    ViewHolder(View itemView) {
      super(itemView);
      tvPersonName = itemView.findViewById(R.id.tv_person_name);
      tvPersonLastName = itemView.findViewById(R.id.tv_person_lastName);
      tvPersonMobile = itemView.findViewById(R.id.tv_person_mobile);
      tvPersonEmail = itemView.findViewById(R.id.tv_person_email);
      tvPersonRol = itemView.findViewById(R.id.tv_person_rol);

      itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
      if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
    }

  }

}
