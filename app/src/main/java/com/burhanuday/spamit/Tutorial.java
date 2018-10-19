package com.burhanuday.spamit;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

public class Tutorial extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);
    }

    public void openSt(View view) {
        startActivity(new Intent(Settings.ACTION_INPUT_METHOD_SETTINGS));
        Toast.makeText(this, "Enable SPAMit and go back", Toast.LENGTH_LONG).show();
    }

    public void selectKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showInputMethodPicker();
        Toast.makeText(Tutorial.this, "Select SPAMit", Toast.LENGTH_LONG).show();
    }

    public void con_app(View view) {
        Intent startApp = new Intent(this, MainActivity.class);
        startActivity(startApp);
        finish();
    }
}
