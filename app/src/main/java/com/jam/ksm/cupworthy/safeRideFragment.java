package com.jam.ksm.cupworthy;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.lang.reflect.Method;

import static java.lang.Class.forName;


/**
 * SafeRideFragment
 *
 * This fragment enables two image buttons (UBER and a Taxi) when the user's BAC is greater than 0.08
 * Clicking on the UBER button opens UBER, and the taxi button calls the saved Taxi service.
 */
public class safeRideFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private SharedPreferences mPrefs;
    private String mKey;
    private Context context;


    private boolean call_flag;

    private ImageButton uberButton, taxiButton;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment safeRideFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static safeRideFragment newInstance(String param1, String param2) {
        safeRideFragment fragment = new safeRideFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public safeRideFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_safe_ride, container, false);

        TextView safeText = (TextView) view.findViewById(R.id.safeRideText);

        mKey = getString(R.string.preference_name);
        mPrefs = getActivity().getSharedPreferences(mKey, Context.MODE_PRIVATE);

        mKey = getString(R.string.preference_key_bac);
        String bac = mPrefs.getString(mKey, "");

        uberButton = (ImageButton) view.findViewById(R.id.uberView);
        uberButton.setOnClickListener(this);

        taxiButton = (ImageButton) view.findViewById(R.id.taxiView);
        taxiButton.setOnClickListener(this);

        //call_flag = false;
        context = getActivity();

       /* EndCallListener callListener = new EndCallListener(context);
        TelephonyManager mTM = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
        mTM.listen(callListener, PhoneStateListener.LISTEN_CALL_STATE);
*/

        if (bac == "") {
            bac = "0.0";
        }

        // if the user is not safe to drive, enable the imagebuttons -- so that when clicked they
        // open Uber or call a local taxi, respectively...
        if (Double.parseDouble(bac) >= 0.08) {
            safeText.setText("Your bac is " + bac + ". It is not safe to drive. Hire an Uber or Taxi!");
            uberButton.setOnClickListener(this);
            taxiButton.setOnClickListener(this);
        } else {
            safeText.setText("Your bac is " + bac + ". It is safe to drive :)");
            uberButton.setOnClickListener(null);
            taxiButton.setOnClickListener(null);

        }


        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            // open Uber App when the button is pressed
            case R.id.uberView:
                PackageManager manager = getActivity().getPackageManager();
                try {
                    Intent i = manager.getLaunchIntentForPackage("com.ubercab");
                    if (i == null)
                        throw new PackageManager.NameNotFoundException();
                    i.addCategory(Intent.CATEGORY_LAUNCHER);
                    startActivity(i);
                } catch (PackageManager.NameNotFoundException e) {
                    Toast.makeText(getActivity(), "Need to download uber!", Toast.LENGTH_SHORT).show();
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.ubercab"));
                    startActivity(browserIntent);
                }
                break;
            case R.id.taxiView:
                mKey = getString(R.string.preference_key_taxi);
                String taxi_num = mPrefs.getString(mKey, "");
                if (taxi_num != "") {
                    String url = "tel:" + taxi_num;
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(url));
                    startActivity(intent);
                } else {
                    // user hasn't yet entered a local taxi number
                    Toast.makeText(getActivity(), "Please enter a taxi number in settings", Toast.LENGTH_SHORT).show();
                }

                break;

            default:
                break;
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
