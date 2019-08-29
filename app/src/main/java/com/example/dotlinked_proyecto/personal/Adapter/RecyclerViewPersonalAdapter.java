package com.example.dotlinked_proyecto.personal.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dotlinked_proyecto.R;
import com.example.dotlinked_proyecto.bean.Person;

import java.lang.ref.WeakReference;
import java.util.List;


public class RecyclerViewPersonalAdapter extends RecyclerView.Adapter<RecyclerViewPersonalAdapter.ViewHolder> {

  private List<Person> personData;
  private LayoutInflater mInflater;
  private ClickListener mClickListener;


  public RecyclerViewPersonalAdapter(Context context, List<Person> personData, ClickListener listener) {
    this.mInflater = LayoutInflater.from(context);
    this.personData = personData;
    this.mClickListener = listener;

  }

  @NonNull
  @Override
  public RecyclerViewPersonalAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = mInflater.inflate(R.layout.recycler_personal_row, parent, false);
    return new RecyclerViewPersonalAdapter.ViewHolder(view, mClickListener);
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
  public void setClickListener(ClickListener itemClickListener) {
    this.mClickListener = itemClickListener;
  }

  // parent activity will implement this method to respond to click events
  public interface ItemClickListener {
    void onItemClick(View view, int position);
  }


  public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
    TextView tvPersonName;
    TextView tvPersonLastName;
    TextView tvPersonMobile;
    TextView tvPersonEmail;
    TextView tvPersonRol;
    private TextView iconTextView;
    private WeakReference<ClickListener> listenerRef;

    ViewHolder(View itemView, ClickListener listener) {
      super(itemView);
      listenerRef = new WeakReference<>(listener);
      tvPersonName = itemView.findViewById(R.id.tv_person_name);
      tvPersonLastName = itemView.findViewById(R.id.tv_person_lastName);
      tvPersonMobile = itemView.findViewById(R.id.tv_person_mobile);
      tvPersonEmail = itemView.findViewById(R.id.tv_person_email);
      tvPersonRol = itemView.findViewById(R.id.tv_person_rol);
      iconTextView = itemView.findViewById(R.id.tv_person_email);
      itemView.setOnClickListener(this);
      iconTextView.setOnClickListener(this);
      iconTextView.setOnLongClickListener(this);

    }

    @Override
    public void onClick(View view) {

      if (view.getId() == iconTextView.getId()) {
        Toast.makeText(view.getContext(), "ITEM PRESSED = " + String.valueOf(getAdapterPosition()), Toast.LENGTH_SHORT).show();
      } else {
        Toast.makeText(view.getContext(), "ROW PRESSED = " + String.valueOf(getAdapterPosition()), Toast.LENGTH_SHORT).show();
      }

      int pos = getAdapterPosition();
      listenerRef.get().onPositionClicked(view, pos);
    }
    //onLongClickListener for view
    @Override
    public boolean onLongClick(View v) {

      final AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
      builder.setTitle("Hello Dialog")
              .setMessage("LONG CLICK DIALOG WINDOW FOR ICON " + String.valueOf(getAdapterPosition()))
              .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
              });

      builder.create().show();
      listenerRef.get().onLongClicked(v, getAdapterPosition());
      return true;
    }
  }

}
