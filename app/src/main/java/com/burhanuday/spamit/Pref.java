package com.burhanuday.spamit;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;

public class Pref extends AppCompatActivity {

    Switch vibrate, last_message;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        vibrate = (Switch)findViewById(R.id.switch_vibrate);
        last_message = (Switch) findViewById(R.id.switch_share);
        sharedPreferences = getSharedPreferences("com.burhanuday.spamit", MODE_PRIVATE);

        initialise();
        setChangeListeners();

    }

    public void initialise(){
        if(sharedPreferences.getBoolean("vibrate", true)){
            vibrate.setChecked(true);
        }else {
            vibrate.setChecked(false);
        }

        if (sharedPreferences.getBoolean("last_message", true)){
            last_message.setChecked(true);
        }else {
            last_message.setChecked(false);
        }
    }

    public void setChangeListeners(){
        vibrate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                sharedPreferences.edit().putBoolean("vibrate", b).commit();
            }
        });

        last_message.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                sharedPreferences.edit().putBoolean("last_message", b).commit();
            }
        });
    }
}
