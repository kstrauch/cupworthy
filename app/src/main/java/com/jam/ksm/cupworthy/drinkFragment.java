package com.jam.ksm.cupworthy;

import android.app.Activity;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Context;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import android.content.Context;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link drinkFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link drinkFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class drinkFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Context context;
    private Button enterButton;
    private ImageButton beerButton, shotButton, iceButton, wineButton;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment drinkFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static drinkFragment newInstance(String param1, String param2) {
        drinkFragment fragment = new drinkFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public drinkFragment() {
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

        context = this.getActivity();

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_drink, container, false);

        // set up all the buttons and listeners
        enterButton = (Button) view.findViewById(R.id.enter);
        enterButton.setOnClickListener(this);
        beerButton = (ImageButton) view.findViewById(R.id.imageButtonBeer);
        beerButton.setOnClickListener(this);
        shotButton = (ImageButton) view.findViewById(R.id.imageButtonShot);
        shotButton.setOnClickListener(this);
        iceButton = (ImageButton) view.findViewById(R.id.imageButtonIce);
        iceButton.setOnClickListener(this);
        wineButton = (ImageButton) view.findViewById(R.id.imageButtonWine);
        wineButton.setOnClickListener(this);


        // get all the user info from shared preferences

        // get the shared prefs
        String mKey = getString(R.string.preference_name);
        SharedPreferences mPrefs = getActivity().getSharedPreferences(mKey, Context.MODE_PRIVATE);

        // weight
        mKey = getString(R.string.preference_key_weight);
        String weight = mPrefs.getString(mKey, "");
        //Toast.makeText(getActivity(), "weight = " + weight, Toast.LENGTH_SHORT).show();

        // height
        mKey = getString(R.string.preference_key_height);
        String height = mPrefs.getString(mKey, "");
       // Toast.makeText(getActivity(), "height = " + height, Toast.LENGTH_SHORT).show();

        // age
        mKey = getString(R.string.preference_key_age);
        String age = mPrefs.getString(mKey, "");
        //Toast.makeText(getActivity(), "age = " + age, Toast.LENGTH_SHORT).show();

        // gender -- remember 0 is female, 1 is male
        mKey = getString(R.string.preference_key_gender);
        int gender = mPrefs.getInt(mKey, -1);
        String strGender = "male";
        if (gender == 0){
            strGender = "female";
        }
        //Toast.makeText(getActivity(), "gender = " + strGender, Toast.LENGTH_SHORT).show();


        // Inflate the layout for this fragment
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
    public void onClick(View v) {
        switch (v.getId()) {
            // when refresh button is clicked, disable the button and call
            // manageMyIDinBackground() on the context
            // this will refresh the swipe/balance information
            case R.id.enter:
                enterButton.setEnabled(false);
                break;
            case R.id.imageButtonBeer:
                Toast.makeText(getActivity(), "beer pressed", Toast.LENGTH_SHORT).show();


                break;
            case R.id.imageButtonIce:
                Toast.makeText(getActivity(), "ice pressed", Toast.LENGTH_SHORT).show();
                break;
            case R.id.imageButtonShot:
                Toast.makeText(getActivity(), "shot pressed", Toast.LENGTH_SHORT).show();
                break;
            case R.id.imageButtonWine:
                Toast.makeText(getActivity(), "wine pressed", Toast.LENGTH_SHORT).show();
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
