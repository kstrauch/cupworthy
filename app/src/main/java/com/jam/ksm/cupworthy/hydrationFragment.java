package com.jam.ksm.cupworthy;

import android.app.Activity;
import android.content.Context;
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

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link hydrationFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link hydrationFragment#newInstance} factory method to
 * create an instance of this fragment.
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

        //Depending on bac set up the number of glasses shown
        //As of right now, setting them all as visible
        /*view.findViewById(R.id.glass1).setVisibility(View.VISIBLE);
        view.findViewById(R.id.glass2).setVisibility(View.VISIBLE);
        view.findViewById(R.id.glass3).setVisibility(View.VISIBLE);
        view.findViewById(R.id.glass4).setVisibility(View.VISIBLE);
        view.findViewById(R.id.glass5).setVisibility(View.VISIBLE);*/



        return view;
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
                //TextView howManyDrinks = (TextView) v.findViewById(R.id.howManyGlassesNeeded);
                //howManyDrinks.setText("blah");
                //Toast.makeText(getActivity(), "button pressed", Toast.LENGTH_SHORT).show();
                reduceWaterCups();

                break;

            default:
                break;
        }
    }

    public void reduceWaterCups(){
        Log.d(TAG, "in reduce water cups");
        TextView textView = (TextView) getView().findViewById(R.id.howManyGlassesNeeded);
        CharSequence message = textView.getText();
        int numberOfDrinks = Integer.parseInt(Character.toString(message.charAt(18)));
        if (numberOfDrinks > 0){
            numberOfDrinks --;
        }
        String newNumber = Integer.toString(numberOfDrinks);
        String text = "You need to drink " + newNumber + " glasses of water to rehydrate.";
        textView.setText(text);

        ImageView glass;

        switch (numberOfDrinks){
            case 0:
                glass = (ImageView) getView().findViewById(R.id.glass1);
                glass.setVisibility(View.INVISIBLE);
                break;
            case 1:
                glass = (ImageView) getView().findViewById(R.id.glass1);
                glass.setVisibility(View.INVISIBLE);
                break;
            case 2:
                glass = (ImageView) getView().findViewById(R.id.glass1);
                glass.setVisibility(View.INVISIBLE);
                break;
            case 3:
                glass = (ImageView) getView().findViewById(R.id.glass1);
                glass.setVisibility(View.INVISIBLE);
                break;
            case 4:
                glass = (ImageView) getView().findViewById(R.id.glass1);
                glass.setVisibility(View.INVISIBLE);
                break;
            case 5:
                glass = (ImageView) getView().findViewById(R.id.glass1);
                glass.setVisibility(View.INVISIBLE);
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
