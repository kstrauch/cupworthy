package com.jam.ksm.cupworthy;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Context;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import android.content.Context;

import org.w3c.dom.Text;

import java.text.DecimalFormat;

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
    private ImageButton beerButton, shotButton, iceButton, wineButton, mikesButton, champButton, soloButton;

    private SharedPreferences mPrefs;
    private String mKey;

    private boolean confirmation;

    // used for BAC formula
    private double ALC_CONST = 5.14;
    private double TIME_CONST = 0.015;
    private double F_CONST = 0.66;
    private double M_CONST = 0.73;

    public int MILLIS_PER_DAY = 1000 * 3600 * 24;

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
        //enterButton = (Button) view.findViewById(R.id.enter);
        //enterButton.setOnClickListener(this);
        beerButton = (ImageButton) view.findViewById(R.id.imageButtonBeer);
        beerButton.setOnClickListener(this);
        shotButton = (ImageButton) view.findViewById(R.id.imageButtonShot);
        shotButton.setOnClickListener(this);
        iceButton = (ImageButton) view.findViewById(R.id.imageButtonIce);
        iceButton.setOnClickListener(this);
        wineButton = (ImageButton) view.findViewById(R.id.imageButtonWine);
        wineButton.setOnClickListener(this);
        mikesButton = (ImageButton) view.findViewById(R.id.imageButtonMikes);
        mikesButton.setOnClickListener(this);
        champButton = (ImageButton) view.findViewById(R.id.imageButtonChampagne);
        champButton.setOnClickListener(this);
        soloButton = (ImageButton) view.findViewById(R.id.imageButtonSolo);
        soloButton.setOnClickListener(this);

        // get all the user info from shared preferences

        // get the shared prefs
        mKey = getString(R.string.preference_name);
        mPrefs = getActivity().getSharedPreferences(mKey, Context.MODE_PRIVATE);

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
        if (gender == 0) {
            strGender = "female";
        }
        //Toast.makeText(getActivity(), "gender = " + strGender, Toast.LENGTH_SHORT).show();
        DecimalFormat df = new DecimalFormat("0.00");
        double bac = calculateBAC();
        ((TextView) view.findViewById(R.id.bacText)).setText("BAC: " + df.format(bac));
        ((TextView) view.findViewById(R.id.bacDetails)).setText(getBACInfo(bac));

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
        double bac = calculateBAC();
        //confirmation = false;
        //confirmation = displayAlertDialog();
            switch (v.getId()) {
                // when refresh button is clicked, disable the button and call
                // manageMyIDinBackground() on the context
                // this will refresh the swipe/balance information
                // case R.id.enter:
                //enterButton.setEnabled(false);
                //    Toast.makeText(getActivity(), "enter pressed", Toast.LENGTH_SHORT).show();
                //    break;
                case R.id.imageButtonBeer:
                    Toast.makeText(getActivity(), "beer pressed", Toast.LENGTH_SHORT).show();
                    addDrink(getActivity(), mPrefs,Globals.TYPE_BEER, Globals.BEER_AMT);
                    bac = calculateBAC();
                    Toast.makeText(getActivity(), "bac is" + bac, Toast.LENGTH_SHORT).show();

                    break;
                case R.id.imageButtonIce:
                    Toast.makeText(getActivity(), "ice pressed", Toast.LENGTH_SHORT).show();
                    addDrink(getActivity(), mPrefs,Globals.TYPE_WINE_COOLER, Globals.COOLER_AMT);
                    bac = calculateBAC();
                    Toast.makeText(getActivity(), "bac is" + bac, Toast.LENGTH_SHORT).show();

                    break;
                case R.id.imageButtonShot:
                    Toast.makeText(getActivity(), "shot pressed", Toast.LENGTH_SHORT).show();
                    addDrink(getActivity(), mPrefs,Globals.TYPE_VODKA, Globals.HARD_AMT);
                    bac = calculateBAC();
                    Toast.makeText(getActivity(), "bac is" + bac, Toast.LENGTH_SHORT).show();

                    break;
                case R.id.imageButtonWine:
                    Toast.makeText(getActivity(), "wine pressed", Toast.LENGTH_SHORT).show();
                    addDrink(getActivity(), mPrefs, Globals.TYPE_WINE, Globals.WINE_AMT);
                    bac = calculateBAC();
                    Toast.makeText(getActivity(), "bac is" + bac, Toast.LENGTH_SHORT).show();
                    break;
                case R.id.imageButtonChampagne:
                    Toast.makeText(getActivity(), "champagne pressed", Toast.LENGTH_SHORT).show();
                    addDrink(getActivity(), mPrefs,Globals.TYPE_WINE, Globals.WINE_AMT);
                    bac = calculateBAC();
                    Toast.makeText(getActivity(), "bac is" + bac, Toast.LENGTH_SHORT).show();
                    break;
                case R.id.imageButtonMikes:
                    Toast.makeText(getActivity(), "mikes pressed", Toast.LENGTH_SHORT).show();
                    addDrink(getActivity(), mPrefs,Globals.TYPE_WINE_COOLER, Globals.COOLER_AMT);
                    bac = calculateBAC();
                    Toast.makeText(getActivity(), "bac is" + bac, Toast.LENGTH_SHORT).show();
                    break;
                case R.id.imageButtonSolo:
                    Toast.makeText(getActivity(), "red cup pressed", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(), cupActivity.class);
                    startActivityForResult(intent, Globals.RED_CUP_INTENT);
                   // bac = calculateBAC();
                    //Toast.makeText(getActivity(), "bac is" + bac, Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }

        DecimalFormat df = new DecimalFormat("0.00");
        // df.format(0.912385);

        setText("Your BAC is: " + df.format(bac), R.id.bacText);
        setText(getBACInfo(bac), R.id.bacDetails);
    }


    public static void addDrink(Context context, SharedPreferences mPrefs, int type, double amount) {

        SharedPreferences.Editor mEditor = mPrefs.edit();
        String mKey;

        // update start time for first app usage or if more than a day has passed.
        mKey = context.getString(R.string.preference_key_start_time);
        String start_time = mPrefs.getString(mKey, "");
        if (start_time == "" || (System.currentTimeMillis() - Long.parseLong(start_time) > Globals.MILLIS_PER_DAY)) {
            mEditor.putString(mKey, "" + System.currentTimeMillis());
            mKey = context.getString(R.string.preference_key_consumption);
            mEditor.putString(mKey, "0.0");
        }

        // calculate oz of alc based on the kind of alc/the oz that were drunk
        double oz_alc = Globals.ALC_ARRAY[type] * amount;

        // deal with consumption. update daily consumption amount
        mKey = context.getString(R.string.preference_key_consumption);

        double consumption;
        if (mPrefs.getString(mKey, "") != "") {
            consumption = Double.parseDouble(mPrefs.getString(mKey, ""));
        } else {
            consumption = 0.0;
        }
        consumption += oz_alc;
        mEditor.putString(mKey, "" + consumption);
        mEditor.commit();


        Toast.makeText(context.getApplicationContext(), "Saving drink! type = " + type + "amount = " + amount + " total oz alc = " + oz_alc,
                Toast.LENGTH_SHORT).show();
        // should also make note in database of what was consumed, oz. alcohol, and the timestamp so that we can keep track of days
    }

    public double calculateBAC() {
        SharedPreferences.Editor mEditor = mPrefs.edit();

        mKey = getString(R.string.preference_key_consumption);
        String amount = mPrefs.getString(mKey,"");
        mKey = getString(R.string.preference_key_start_time);
        String start_time = mPrefs.getString(mKey, "");

        // first time using app, set start_time to current sys time and set consumption to 0
        if (start_time == "" || amount ==""){
            start_time = "" + System.currentTimeMillis();
            mEditor.putString(mKey, start_time);
            amount = "0.0";
            mKey = getString(R.string.preference_key_consumption);
            mEditor.putString(mKey,amount);
            mEditor.commit();
        }


        double time_elapsed = (double) (System.currentTimeMillis() - Long.parseLong(start_time)) / Globals.MILLIS_PER_DAY;

      //  mKey = getString(R.string.preference_key_consumption);
        double consumption = Double.parseDouble(amount);

        mKey = getString(R.string.preference_key_weight);
        int weight;
        if (mPrefs.getString(mKey, "") != "") {
            weight = Integer.parseInt(mPrefs.getString(mKey, ""));
        } else {
            Toast.makeText(getActivity(), "update weight in settings", Toast.LENGTH_SHORT).show();
            return 0.0;
        }

        // figure out which R constant to use based on gender
        mKey = getString(R.string.preference_key_gender);
        int gender = mPrefs.getInt(mKey, -1);
        double gender_prop = M_CONST;
        if (gender == 0) {
            gender_prop = F_CONST;
        } else if (gender == -1) {
            Toast.makeText(getActivity(), "update gender in settings!", Toast.LENGTH_SHORT).show();
            return 0.0;
        }


        // based on Widmark's BAC Formula
        double bac = (consumption * ALC_CONST) / (weight * gender_prop) - TIME_CONST * time_elapsed;
        if (bac < 0) {
            bac = 0.0;
        }
        Log.d("CS69", "Consumption is " + consumption);
        Log.d("CS69", "weight is " + weight);
        Log.d("CS69", " Time elapsed is " + time_elapsed);
        Log.d("CS69", "bac is " + bac);

        // update shared preferences with the bac
        mKey = getString(R.string.preference_key_bac);
        DecimalFormat df = new DecimalFormat("0.00");
        mEditor.putString(mKey, "" + df.format(bac));
        mEditor.commit();

        return bac;
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

    public void setText(String text, int name) {
        TextView textView = (TextView) getView().findViewById(name);
        textView.setText(text);
    }

    public String getBACInfo(double bac) {
        String text = "";
        if (bac == 0.0){
           text = "";
        }
        else if (bac <= 0.02) {
            text = Globals.EFFECTS_ARR[0];
        } else if (bac <= 0.05) {
            text = Globals.EFFECTS_ARR[1];
        } else if (bac <= 0.08) {
            text = Globals.EFFECTS_ARR[2];
        } else if (bac <= 0.10) {
            text = Globals.EFFECTS_ARR[3];
        } else {
            text = Globals.EFFECTS_ARR[4];
        }
        return text;
    }

    public boolean displayAlertDialog() {

        Context context = getActivity();
        String title = "Drink Confirmation";
        String message = "Are you sure you'd like to add this drink?";
        String button1String = "Yes";
        String button2String = "Cancel";

        AlertDialog.Builder ad = new AlertDialog.Builder(context);
        ad.setTitle(title);
        ad.setMessage(message);

        ad.setPositiveButton(
                button1String,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int arg1) {
                        confirmation = true;
                    }
                }
        );

        ad.setNegativeButton(
                button2String,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int arg1) {
                        confirmation = false;
                    }
                }
        );


        ad.show();

        return confirmation;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
            // Make sure the request was successful
         if (resultCode == Globals.RESULT_OK) {
             DecimalFormat df = new DecimalFormat("0.00");
             // df.format(0.912385);
             double bac = calculateBAC();
             Toast.makeText(getActivity(), "bac is" + bac, Toast.LENGTH_SHORT).show();

             setText("Your BAC is: " + df.format(bac), R.id.bacText);
             setText(getBACInfo(bac), R.id.bacDetails);

            }
        }


}