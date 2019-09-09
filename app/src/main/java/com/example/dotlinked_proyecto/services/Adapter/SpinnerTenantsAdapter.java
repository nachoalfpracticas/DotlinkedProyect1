package com.example.dotlinked_proyecto.services.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.dotlinked_proyecto.bean.Person;

import java.util.List;

public class SpinnerTenantsAdapter extends ArrayAdapter<Person> {

  private Context context;
  private List<Person> values;

  public SpinnerTenantsAdapter(@NonNull Context context, int textViewResourceId, @NonNull List<Person> personList) {
    super(context, textViewResourceId, personList);
    this.context = context;
    this.values = personList;
  }

  @Override
  public int getCount() {
    return values.size();
  }

  @Override
  public Person getItem(int position) {
    return values.get(position);
  }

  @Override
  public long getItemId(int position) {
    return position;
  }

  @NonNull
  @Override
  public View getView(int position, View convertView, @NonNull ViewGroup parent) {
    // I created a dynamic TextView here, but you can reference your own  custom layout for each spinner item
    TextView label = (TextView) super.getView(position, convertView, parent);
    label.setTextColor(Color.WHITE);

    label.setTextSize(1, 18.0f);
    // Then you can get the current item using the values array (Users array) and the current position
    // You can NOW reference each method you has created in your bean object (User class)
    label.setText(values.get(position).getName());

    // And finally return your dynamic (or custom) view for each spinner item
    return label;
  }

  // And here is when the "chooser" is popped up
  // Normally is the same view, but you can customize it if you want
  @Override
  public View getDropDownView(int position, View convertView,
                              @NonNull ViewGroup parent) {
    TextView label = (TextView) super.getDropDownView(position, convertView, parent);
    label.setTextColor(Color.WHITE);
    label.setMinWidth(250);
    label.setTextSize(1, 18.0f);
    label.setText(values.get(position).getName());

    return label;
  }
}

