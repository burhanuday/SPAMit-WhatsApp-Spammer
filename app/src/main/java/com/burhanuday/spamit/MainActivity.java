package com.burhanuday.spamit;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import javax.xml.datatype.Duration;

public class MainActivity extends AppCompatActivity {
    EditText message,count;
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("com.burhanuday.spamit", MODE_PRIVATE);
        message = (EditText)findViewById(R.id.et_message);
        count = (EditText) findViewById(R.id.et_count);

        message.setText(sharedPreferences.getString("message","Check this out!"));
        count.setText(Integer.toString(sharedPreferences.getInt("count", 30)));
        changeListeners();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_settings:
                startActivity(new Intent(this, Pref.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void changeListeners(){
        message.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                sharedPreferences.edit().putString("message", message.getText().toString()).apply();
            }
        });

        count.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(count.length() == 0){
                    sharedPreferences.edit().putInt("count",30).apply();
                }else{
                    sharedPreferences.edit().putInt("count",Integer.parseInt(count.getText().toString())).apply();

                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (sharedPreferences.getBoolean("firstrun", true)) {
            Intent opentut = new Intent(getBaseContext(),Tutorial.class);
            startActivity(opentut);
            sharedPreferences.edit().putBoolean("firstrun", false).apply();
            finish();
        }
    }

    public void htu(View view) {
        Intent howto = new Intent(MainActivity.this, Tutorial.class);
        startActivity(howto);
        finish();
    }
}
