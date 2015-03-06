package com.jam.ksm.cupworthy;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


/**
 * Profile Activity for CupWorthy. Here the user enters all of their workout
 * profile information -- weight, sex, and taxi number, info necessary for the app.
 *
 * @author Kimberly Strauch
 * @date 3/05/2015
 */
public class prefActivity extends Activity {

    private static final String TAG = "CS69";
    public static final String CUP_PREFS = "MyPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pref);

        // load the user's previously saved profile (or default profile)
        loadProfile();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    // ****************** button click callbacks ***************************//

    public void onCancelClicked(View v) {
        Log.d(TAG, "Cancel button clicked");
        // discard changes and finish the activity
        Toast.makeText(getApplicationContext(), "Exiting profile!",
                Toast.LENGTH_SHORT).show();
        finish();
    }

    public void onSaveClicked(View v) {
        // save the user profile to shared preferences and save photo to
        // internal storage and finish the activity
        saveProfile();
        Log.d(TAG, "Save button clicked");
        finish();
    }

    // load the user data from shared preferences. if there is no data, we do
    // something reasonable, i.e. load empty strings, or unchecked buttons
    private void loadProfile() {

        Log.d(TAG, "loading user data");

        // Load profile photo from internal storage to byte array

        // Load and update all profile views
        // Access the shared preferences the shared preferences

        String mKey = getString(R.string.preference_name);
        SharedPreferences mPrefs = getSharedPreferences(mKey, MODE_PRIVATE);

        // Load the user name
        mKey = getString(R.string.preference_key_weight);
        String mValue = mPrefs.getString(mKey, "");
        if (mValue != "") { // note, don't want to load an empty string! want
            // to see the hints ;)
            ((EditText) findViewById(R.id.editWeight)).setText(mValue);
        }

        // Load the user age
        mKey = getString(R.string.preference_key_age);
        mValue = mPrefs.getString(mKey, "");
        if (mValue != "") {
            ((EditText) findViewById(R.id.editAge)).setText(mValue);
        }
        // Load gender info, and fill in appropriate radio button

        mKey = getString(R.string.preference_key_gender);

        int mIntValue = mPrefs.getInt(mKey, -1);
        // In case there isn't one saved before, we don't want to check a
        // button:
        if (mIntValue >= 0) {
            // Find the radio button that should be checked.
            RadioButton radioBtn = (RadioButton) ((RadioGroup) findViewById(R.id.radioGender))
                    .getChildAt(mIntValue);
            // Check the button.
            radioBtn.setChecked(true);
        }


        // load the taxi number
        mKey = getString(R.string.preference_key_taxi);
        mValue = mPrefs.getString(mKey, "");
        if(mValue != ""){
            ((EditText) findViewById(R.id.editTaxiNum)).setText(mValue);
        }

    }

    // method for saving the user-input profile to shared preferences
    private void saveProfile() {
        Log.d(TAG, "Saving user Data");

        // get shared preferences editor
        // clear any existing data for rewriting purposes
        String mKey = getString(R.string.preference_name);
        SharedPreferences mPrefs = getSharedPreferences(mKey, MODE_PRIVATE);

        SharedPreferences.Editor mEditor = mPrefs.edit();
        mEditor.clear();


        // Save name information
        mKey = getString(R.string.preference_key_age);
        String mValue = (String) ((EditText) findViewById(R.id.editAge))
                .getText().toString();
        mEditor.putString(mKey, mValue);


        // Save weight information
        mKey = getString(R.string.preference_key_weight);
        mValue = (String) ((EditText) findViewById(R.id.editWeight)).getText()
                .toString();
        mEditor.putString(mKey, mValue);

        // Save gender information
        mKey = getString(R.string.preference_key_gender);
        RadioGroup mRadioGroup = (RadioGroup) findViewById(R.id.radioGender);
        int mIntValue = mRadioGroup.indexOfChild(findViewById(mRadioGroup
                .getCheckedRadioButtonId()));
        mEditor.putInt(mKey, mIntValue);


        mKey = getString(R.string.preference_key_taxi);
        mValue = (String) ((EditText) findViewById(R.id.editTaxiNum)).getText().toString();
        mEditor.putString(mKey,mValue);

        // commit all the changes in the shared preferences
        mEditor.commit();

        Toast.makeText(getApplicationContext(), "Saving user data!",
                Toast.LENGTH_SHORT).show();
    }

}
