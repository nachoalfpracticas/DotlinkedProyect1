package com.example.dotlinked_proyecto.personal.Adapter;

import android.view.View;

public interface ClickListener {
  void onPositionClicked(View view, int position);

  void onLongClicked(View view, int position);
}
