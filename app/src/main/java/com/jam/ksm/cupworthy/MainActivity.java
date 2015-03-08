package com.jam.ksm.cupworthy;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.ViewPager;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.tuenti.smsradar.SmsListener;
import com.tuenti.smsradar.SmsRadar;

import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.Hashtable;
import java.util.Locale;


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

    private String number;
    private IntentFilter filter;
    private boolean isReceiverRegistered = false;
    private OutgoingCallReceiver receiver;
    private String blockedNumbers[];
    private Hashtable<String, String> blacklist = new Hashtable<String, String>();

    private SharedPreferences mPrefs;
    private String mKey;

    private Context context;
    private boolean call_flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //set up blocked # list
        blockedNumbers = new String[] {"9148743753"};

        // set up shared preferences
        mKey = getString(R.string.preference_name);
        mPrefs = getSharedPreferences(mKey, Context.MODE_PRIVATE);

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


        context = this;

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



        final NotificationCompat.Builder warning =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.smirnoff_ice)
                        .setContentTitle("My Notification")
                        .setContentText("testingtesting");


        //taken from android developer site
        Intent notification = new Intent(this, MainActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(notification);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0, PendingIntent.FLAG_UPDATE_CURRENT);
        warning.setContentIntent(resultPendingIntent);
        final NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        SmsRadar.initializeSmsRadarService(context, new SmsListener() {

            //            blacklist = (Hashtable<String, String>) loadHash(blacklist);
            public void onSmsSent(com.tuenti.smsradar.Sms sms) {
                Toast.makeText(context, "sms sent", Toast.LENGTH_LONG).show();
                blacklist = (Hashtable<String, String>) blacklistFragment.loadHash(blacklist, context);

                String phone_number = sms.getAddress();
                Toast.makeText(context, "phone_number = " + phone_number, Toast.LENGTH_LONG).show();

                if (blacklist.containsKey(number)) {
                    notificationManager.notify(1, warning.build());
                    displayAlertDialog(blacklist.get(number));
                }
            }

            @Override
            public void onSmsReceived(com.tuenti.smsradar.Sms sms) {
                //don't need to do anything here
                Toast.makeText(context, "sms received", Toast.LENGTH_LONG).show();


            }

        });

    }

    @Override
    public void onResume(){
        super.onResume();

        // set up broadcast receiver
        filter = new IntentFilter();
        filter.addAction("android.intent.action.NEW_OUTGOING_CALL");
        receiver = new OutgoingCallReceiver();
        if (! isReceiverRegistered){
            registerReceiver(receiver, filter);
            isReceiverRegistered = true;
        }
        // set up phone state listener
        EndCallListener callListener = new EndCallListener(this);
        TelephonyManager mTM = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        mTM.listen(callListener, PhoneStateListener.LISTEN_CALL_STATE);

        Log.d("CS69","registered phone receiver");
    }

    @Override
    public void onPause(){
        super.onPause();
        if(isReceiverRegistered) {
            //unregisterReceiver(receiver);
            isReceiverRegistered = false;
            //Log.d("CS69","unregistered phone receiver");
        }
    }

   @Override
   public void onDestroy(){
       super.onDestroy();
       if (isReceiverRegistered){
           unregisterReceiver(receiver);
           Log.d("CS69","unregistered phone receiver");
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

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }


    /**
     * phone EndCallListener to return to CupWorthy after the Taxi call was made...
     * code inspiration from StackOverflow:
     * http://stackoverflow.com/questions/1556987/how-to-make-a-phone-call-in-android-and-come-back-to-my-activity-when-the-call-i
     */
    public class EndCallListener extends PhoneStateListener {
        Context context;


        public EndCallListener(Context context) {
            super();
            this.context = context;
        }

        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            if (TelephonyManager.CALL_STATE_RINGING == state) {
            }
            if (TelephonyManager.CALL_STATE_OFFHOOK == state) {
                //wait for phone to go off-hook -- means that a call has begun
                // set the call_flag so that we know our app initiated the call.
                call_flag = true;

                // get BAC from shared preferences
                mKey = getString(R.string.preference_key_bac);

                String bac = mPrefs.getString(mKey, "");
                blacklist = (Hashtable<String, String>) blacklistFragment.loadHash( blacklist,context);

                // block call if and only if BAC is over Globals' intoxication level
                if(blacklist.containsKey(number) && (bac != "" && Double.parseDouble(bac) >= Globals.INTOX)){
                    endBlockedCall();
                    Toast.makeText(getApplicationContext(), "ENDING BLOCKED CALL", Toast.LENGTH_SHORT).show();
                }



            }
            if (TelephonyManager.CALL_STATE_IDLE == state) {
                //when this state occurs, and call_flag is set, restart Cupworthy app
                if (call_flag) {
                    call_flag = false;
                    Intent i = context.getPackageManager().getLaunchIntentForPackage(
                            context.getPackageName());

                    // flag activity single top
                    i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

                    //Uncomment the following if you want to restart the application instead of bring to front.
                    //i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(i);
                }
            }
        }

        private void endBlockedCall() {
            try {
                TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

                Class<?> c = Class.forName(tm.getClass().getName());
                Method m = c.getDeclaredMethod("getITelephony");
                m.setAccessible(true);
                Object telephonyService = m.invoke(tm);
                Class<?> telephonyServiceClass = Class.forName(telephonyService.getClass().getName());
                Method endCallMethod = telephonyServiceClass.getDeclaredMethod("endCall");
                endCallMethod.invoke(telephonyService);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public class OutgoingCallReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            number = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
            Log.i("OutgoingCallReceiver", number);
            String info = "Detect Calls sample application\nOutgoing number: " + number;
            //Toast.makeText(context, info, Toast.LENGTH_LONG).show();
            //Toast.makeText(context, "number dialed was " + number + "!", Toast.LENGTH_SHORT);

        }
    }

    public boolean displayAlertDialog(String name) {

        AlertDialog.Builder ad = new AlertDialog.Builder(context);
        ad.setTitle("Text Alert!");
        ad.setMessage(name + " is on your blacklist! You shouldn't be texting them!");

        ad.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int arg1) {
                        //dont need to do anything
                    }
                }
        );

        ad.show();

        return true;
    }
}





