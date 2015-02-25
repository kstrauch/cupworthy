package com.jam.ksm.cupworthy;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Locale;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v13.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.net.Uri;
import android.content.Intent;
import java.util.Map;
import java.util.HashMap;


public class MainActivity extends Activity implements ActionBar.TabListener, hydrationFragment.OnFragmentInteractionListener, drinkFragment.OnFragmentInteractionListener, safeRideFragment.OnFragmentInteractionListener, blacklistFragment.OnFragmentInteractionListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v13.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;

    public SQLiteDatabase database;
    public MySQLiteHelper bacDBHelper;

    //public static final String COLUMN_COMMENT = "comment";

    private static final String DATABASE_NAME = "bac.db";
    private static final int DATABASE_VERSION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // database stuff
        //Context context = getApplicationContext();
        //bacDBHelper = new MySQLiteHelper(this);
        //database = bacDBHelper.getWritableDatabase();
        //createDatabase();

        //BACEntry bacEntry = bacDBHelper.fetchEntry("f",90,3);
        //Toast.makeText(this, "weight is " + bacEntry.getWeight() +  " bac is" + bacEntry.getBAC(),Toast.LENGTH_SHORT).show();

        // Set up the action bar.
        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // When swiping between different sections, select the corresponding
        // tab. We can also use ActionBar.Tab#select() to do this if we have
        // a reference to the Tab.
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by
            // the adapter. Also specify this Activity object, which implements
            // the TabListener interface, as the callback (listener) for when
            // this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }
    }


    public void createDatabase(){

        String line = "";
        BufferedReader br = null;
        InputStream is = null;
        try {
            is = getAssets().open("bac.csv");
            br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            while ((line = br.readLine()) != null) {

                bacDBHelper.insertEntry(line);
            }
            Toast.makeText(this, "database made", Toast.LENGTH_SHORT).show();


        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            Toast.makeText(this, "settings pressed", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, prefActivity.class);
            startActivity(intent);
            return true;
        }
        else if(id == R.id.action_cup){
            Toast.makeText(this, "red cup pressed", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, cupActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in
        // the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onFragmentInteraction(Uri uri){
        Toast.makeText(this, "Success", Toast.LENGTH_SHORT);
    }
    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            Fragment fragment = new Fragment();

            switch (position) {
                case 0:
                    fragment = new drinkFragment();
                    break;
                case 1:
                    fragment = new hydrationFragment();
                    break;
                case 2:
                    fragment = new safeRideFragment();
                    break;
                case 3:
                    fragment = new blacklistFragment();
                    break;
                default:
                    break;
            }
            return fragment;

            // return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.fragment_1).toUpperCase(l);
                case 1:
                    return getString(R.string.fragment_2).toUpperCase(l);

                case 2:
                    return getString(R.string.fragment_3).toUpperCase(l);

                case 3:
                    return getString(R.string.fragment_4).toUpperCase(l);
            }
            return null;

        }
    }
}