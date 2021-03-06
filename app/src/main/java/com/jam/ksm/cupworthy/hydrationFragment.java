package com.jam.ksm.cupworthy;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * HydrationFragment informs the user, based on alcohol consumption, how many glasses of water
 * they need to drink in order to re-hydrate.
 */
public class hydrationFragment extends Fragment implements View.OnClickListener /*implements View.OnClickListener*/ {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "debugging";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Context context;

    private OnFragmentInteractionListener mListener;

    private Button drinkButton;

    private SharedPreferences mPrefs;
    private String mKey;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment hydrationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static hydrationFragment newInstance(String param1, String param2) {
        hydrationFragment fragment = new hydrationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public hydrationFragment() {
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

        View view = inflater.inflate(R.layout.fragment_hydration, container, false);

        drinkButton = (Button) view.findViewById(R.id.drinkAGlass);
        Log.d(TAG, drinkButton.getText().toString());
        drinkButton.setOnClickListener(this);

        mKey = getString(R.string.preference_name);
        mPrefs = getActivity().getSharedPreferences(mKey, Context.MODE_PRIVATE);

        String setNumber = getNumberOfDrinks();

        //set up the layout
        TextView drinks;
        if (Integer.parseInt(setNumber) == 0){
            drinks = (TextView) view.findViewById(R.id.hydrationText);
            drinks.setText("You are properly hydrated!");
        }
        drinks = (TextView) view.findViewById(R.id.howManyGlassesNeeded);
        drinks.setText("You need to drink " + setNumber + " glasses of water to stay hydrated");

        Log.d(TAG, "setNumber = " + setNumber);

        //set up the glass image views
        int drinkValue = Integer.parseInt(setNumber);

        ImageView image;
        if (drinkValue >= 1){
            image = (ImageView) view.findViewById(R.id.glass1);
            image.setVisibility(View.VISIBLE);
        }
        if (drinkValue >= 2){
            image = (ImageView) view.findViewById(R.id.glass2);
            image.setVisibility(View.VISIBLE);
        }
        if (drinkValue >= 3){
            image = (ImageView) view.findViewById(R.id.glass3);
            image.setVisibility(View.VISIBLE);
        }
        if (drinkValue >= 4){
            image = (ImageView) view.findViewById(R.id.plus_sign);
            image.setVisibility(View.VISIBLE);
        }

        return view;
    }

    String getNumberOfDrinks(){
        //use sharedPrefs to get the number of drinks you need to drink
        mKey = context.getString(R.string.preference_key_drink_number);

        String temp = mPrefs.getString(mKey, "");
        //if it isn't 0 or empty, set that as the number of drinks, otherwise set it to 0
        if ( temp != "0" && temp != "") {
            return "" + ((int) Double.parseDouble(temp));
        }
        else {
            return "0";
        }



    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public void onClick (View v){
        switch (v.getId()){
            case R.id.drinkAGlass:
                reduceWaterCups();
                break;
            default:
                break;
        }
    }

    public void reduceWaterCups(){
        SharedPreferences.Editor mEditor = mPrefs.edit();
        String mKey;

        mKey = context.getString(R.string.preference_key_drink_number);
        int drink_num;
        if (mPrefs.getString(mKey, "") != "") {
            drink_num = Integer.parseInt(mPrefs.getString(mKey, ""));
        } else
            drink_num = 0;

        if (drink_num >= 1){
            drink_num -= 1;
        }
        else{
            drink_num = 0;
        }

        mEditor.putString(mKey, "" + drink_num);
        mEditor.commit();

        String temp = mPrefs.getString(mKey, "");

        TextView textView = (TextView) getView().findViewById(R.id.howManyGlassesNeeded);
        CharSequence message = textView.getText();
        int numberOfDrinks = Integer.parseInt(Character.toString(message.charAt(18)));
        if (numberOfDrinks > 0){
            numberOfDrinks --;
        }
        String newNumber = Integer.toString(numberOfDrinks);
        String text = "You need to drink " + newNumber + " glasses of water to stay hydrated.";
        textView.setText(text);

        ImageView image;

        switch (numberOfDrinks){
            case 0:
                image = (ImageView) getView().findViewById(R.id.glass1);
                image.setVisibility(View.INVISIBLE);
                break;
            case 1:
                image = (ImageView) getView().findViewById(R.id.glass2);
                image.setVisibility(View.INVISIBLE);
                break;
            case 2:
                image = (ImageView) getView().findViewById(R.id.glass3);
                image.setVisibility(View.INVISIBLE);
                break;
            case 3:
                //make the plus sign go away, but fix that first
                image = (ImageView) getView().findViewById(R.id.plus_sign);
                image.setVisibility(View.INVISIBLE);
                break;

            default:
                break;
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
