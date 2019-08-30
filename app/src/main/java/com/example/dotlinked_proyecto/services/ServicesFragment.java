package com.example.dotlinked_proyecto.services;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.applandeo.materialcalendarview.CalendarView;
import com.example.dotlinked_proyecto.API.Class.Token;
import com.example.dotlinked_proyecto.R;
import com.example.dotlinked_proyecto.events.Adapter.RecyclerViewEventsAdapter;

import java.text.SimpleDateFormat;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ServicesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ServicesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ServicesFragment extends Fragment {
  private static final String ARG_ROL = "rol";
  private static final String ARG_COMPANY_ID = "companyId";
  private static final String ARG_TOKEN = "token";

  private String rol;
  private String companyId;
  private String access_token;

  private OnFragmentInteractionListener mListener;

  private CalendarView mCalendarView;
  private AppCompatTextView tvDateDay;
  private RecyclerView rcDates;

  private RecyclerViewEventsAdapter adapter;
  private SimpleDateFormat df;

  public ServicesFragment() {
    // Required empty public constructor
  }

  public static ServicesFragment newInstance(Token token, String rol, String companyId) {
    ServicesFragment fragment = new ServicesFragment();
    Bundle args = new Bundle();
    args.putString(ARG_TOKEN, token.getAccess_token());
    args.putString(ARG_ROL, rol);
    args.putString(ARG_COMPANY_ID, companyId);
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      access_token = getArguments().getString(ARG_TOKEN);
      rol = getArguments().getString(ARG_ROL);
      companyId = getArguments().getString(ARG_COMPANY_ID);
    }
  }

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {

    View view = inflater.inflate(R.layout.fragment_services, container, false);
    mCalendarView = view.findViewById(R.id.calendarView);
    tvDateDay = view.findViewById(R.id.tv_select_day);
    rcDates = view.findViewById(R.id.rv_dates_calendar);


    return view;
  }

  // TODO: Rename method, update argument and hook method into UI event
  public void onButtonPressed(Uri uri) {
    if (mListener != null) {
      mListener.onFragmentInteraction(uri);
    }
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    if (context instanceof OnFragmentInteractionListener) {
      mListener = (OnFragmentInteractionListener) context;
    } else {
      throw new RuntimeException(context.toString()
              + " must implement OnFragmentInteractionListener");
    }
  }

  @Override
  public void onDetach() {
    super.onDetach();
    mListener = null;
  }

  /**
   * This interface must be implemented by activities that contain this
   * fragment to allow an interaction in this fragment to be communicated
   * to the activity and potentially other fragments contained in that
   * activity.
   * <p>
   * See the Android Training lesson <a href=
   * "http://developer.android.com/training/basics/fragments/communicating.html"
   * >Communicating with Other Fragments</a> for more information.
   */
  public interface OnFragmentInteractionListener {
    // TODO: Update argument type and name
    void onFragmentInteraction(Uri uri);
  }
}
