package com.jam.ksm.cupworthy;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


public class cupActivity extends Activity implements View.OnClickListener {

    // global var to keep track of progress on slider
    int vert_progress=0;
    private final int TOP_PROGRESS_LEVEL = 10000;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cup);

        final ImageView blueCup =(ImageView) findViewById(R.id.blueCup);
        final SeekBar verticalSlider=(SeekBar) findViewById(R.id.verticalSlider);
        final Button submitButton=(Button) findViewById(R.id.submitButton);

        blueCup.setImageResource(R.drawable.cup_progress);

        blueCup.setImageLevel(vert_progress);
        Toast.makeText(this, "progress is..." + vert_progress, Toast.LENGTH_SHORT).show();
        Toast.makeText(context, "blue cup image level is..." + blueCup.getDrawable().getLevel(), Toast.LENGTH_SHORT).show();

        verticalSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // TODO Auto-generated method stub
                vert_progress = progress;
                int level = (int) ( ( (double) vert_progress  / seekBar.getMax()) * TOP_PROGRESS_LEVEL);
                Toast.makeText(context, "level is..." + level, Toast.LENGTH_SHORT).show();

                //blueCup.setImageLevel(level);
                //blueCup.setAlpha(127);
                //blueCup.setVisibility(View.VISIBLE);
               // blueCup.getDrawable().setLevel(level);

                blueCup.setImageLevel(level);
                blueCup.getDrawable().setLevel(level);

                Toast.makeText(context, "progress is..." + vert_progress, Toast.LENGTH_SHORT).show();

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cup, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.submitButton:
                Toast.makeText(this, "progress is..." + vert_progress, Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
}
