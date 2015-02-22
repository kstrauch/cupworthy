package com.jam.ksm.cupworthy;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Intent;
import android.provider.ContactsContract.Contacts;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.util.Log;
import android.database.Cursor;
import android.content.ContentResolver;
import android.app.Application;
import android.content.Context;
import android.provider.ContactsContract.Contacts;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;
import java.util.List;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link blacklistFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link blacklistFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class blacklistFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private static final int CONTACT_PICKER_RESULT = 1001;
    private Button contactButton;
    private Context context;
    private Context context2;
    private Intent contactPickerIntent;
    private Activity activity;
    private List<String> blacklist = new ArrayList<String>();
    private ArrayAdapter<String> mForecastAdapter;
    private View view;



    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment blacklistFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static blacklistFragment newInstance(String param1, String param2) {
        blacklistFragment fragment = new blacklistFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public blacklistFragment() {
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

        view = inflater.inflate(R.layout.fragment_blacklist, container, false);

        // Inflate the layout for this fragment
        contactButton = (Button) view.findViewById(R.id.addContact);
        contactButton.setOnClickListener(this);

        blacklist.add("test");

        mForecastAdapter = new ArrayAdapter<String>(
                getActivity(),
                R.layout.contact_list,
                R.id.blacklist_textview,
                blacklist);

        ListView listView = (ListView) view.findViewById(
                R.id.listview_blacklist);
        listView.setAdapter(mForecastAdapter);

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

        contactPickerIntent = new Intent(Intent.ACTION_PICK,
                Contacts.CONTENT_URI);
        //getActivity().startActivity(contactPickerIntent)
        startActivityForResult(contactPickerIntent, CONTACT_PICKER_RESULT);
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



//    protected void getContactData(int request, int result, Intent data){
//        if (result == Activity.RESULT_OK) {
//            switch (request) {
//                case CONTACT_PICKER_RESULT:
//                    //ADD STUFF HERE
//                    Cursor cursor = null;
//                    String contact = "";
//
//                    try{
//                        Uri contactUri = data.getData();
//                        Log.v("Contact Data", "Got to a contact result: " + contactUri.toString());
//
//                        String id = contactUri.getLastPathSegment();
//                        cursor = context.getContentResolver().query(Email.CONTACT_ID + "=?", new String[] {id}, null);
//
//                        int nameId = cursor.getColumnIndex(Contacts.DISPLAY_NAME);
//
//                        int phoneIdx = cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.DATA);
//
//                        if (cursor.moveToFirst()){
//                            name = cursor.getString(nameId);
//                        }
//                    }
//
//                    break;
//            }
//        }else{
//            Log.w("Getting Data", "Warning: activity result not ok");
//        }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        String DEBUG_TAG = "onActivityResult";
        activity = getActivity();
        if (resultCode == activity.RESULT_OK) {
            switch (requestCode) {
                case CONTACT_PICKER_RESULT:
                    Cursor cursor = null;
                    String phone = "", name = "";
                    try {
                        Uri result = data.getData();
                        //Log.v(DEBUG_TAG, "Got a contact result: " + result.toString());

                        // get the contact id from the Uri
                        String id = result.getLastPathSegment();

                        // query for everything email
                        cursor = activity.getContentResolver().query(Phone.CONTENT_URI, null, Phone.CONTACT_ID + "=?", new String[]{id}, null);

                        int nameId = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);

                        int phoneIdx = cursor.getColumnIndex(Email.DATA);

                        // let's just get the first email
                        if (cursor.moveToFirst()) {
                            phone = cursor.getString(phoneIdx);
                            name = cursor.getString(nameId);

                        } else {
                            Log.w(DEBUG_TAG, "No results");
                        }
                    } catch (Exception e) {
                        Log.e(DEBUG_TAG, "Failed to get data", e);
                    } finally {
                        if (cursor != null) {
                            cursor.close();
                        }
                        Toast.makeText(activity, name + ": " + phone, Toast.LENGTH_LONG).show();
                        blacklist.add(name);



                    }
                    break;
            }

        } else {
            Log.w(DEBUG_TAG, "Warning: activity result not ok");
        }

    }}


