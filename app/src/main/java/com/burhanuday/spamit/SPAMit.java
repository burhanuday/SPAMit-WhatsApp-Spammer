package com.burhanuday.spamit;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.media.AudioManager;
import android.os.Handler;
import android.os.Vibrator;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;

public class SPAMit extends InputMethodService implements KeyboardView.OnKeyboardActionListener {
    private KeyboardView kv;
    private Keyboard keyboard, caps;
    SharedPreferences sharedPreferences;
    String message;
    int count, toSend, counter;
    InputConnection inputConnection;
    boolean capsLock = false;

    @Override
    public void onCreate() {super.onCreate();}

    @Override
    public void onStartInputView(EditorInfo info, boolean restarting) {
        super.onStartInputView(info, restarting);
        setInputView(onCreateInputView());
    }

    @Override
    public View onCreateInputView() {
        sharedPreferences = getSharedPreferences("com.burhanuday.spamit", MODE_PRIVATE);

        message = sharedPreferences.getString("message", "check this out!");
        count = sharedPreferences.getInt("count", 30);
        toSend = sharedPreferences.getInt("toSend", 0);
        counter = sharedPreferences.getInt("counter", 30);

        inputConnection = getCurrentInputConnection();

        kv = (KeyboardView) getLayoutInflater().inflate(R.layout.keyboard,null);
        keyboard = new Keyboard(this, R.xml.qwerty);
        caps = new Keyboard(this, R.xml.caps);
        kv.setKeyboard(keyboard);
        kv.setPreviewEnabled(false);
        kv.setOnKeyboardActionListener(this);

        if(counter>1 && toSend == 1){
            counter--;
            sharedPreferences.edit().putInt("counter", counter).apply();

            inputConnection.commitText(message, 1);
            inputConnection.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER));
            inputConnection.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER));

        }else if (counter == 1 && toSend == 1) {
            if (sharedPreferences.getBoolean("last_message", true)) {
                inputConnection.commitText(getString(R.string.app_share), 1);
                inputConnection.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER));
                inputConnection.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER));
            }
            counter--;
            sharedPreferences.edit().putInt("counter", counter).apply();
        }else{
            sharedPreferences.edit().putInt("toSend", 0).apply();
        }

        return kv;
    }

    @Override
    public void onPress(int i) {}

    @Override
    public void onRelease(int i) {}

    @Override
    public void onKey(int primaryCode, int[] ints) {
        InputConnection ic = getCurrentInputConnection();
        if (sharedPreferences.getBoolean("vibrate", true)){
            playClick();
        }

        switch(primaryCode){
            case Keyboard.KEYCODE_DELETE :
                ic.deleteSurroundingText(1, 0);
                break;
            case Keyboard.KEYCODE_DONE:
                ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER));
                break;
            case -1000:
                //send button
                message = sharedPreferences.getString("message", "check this out!");
                count = sharedPreferences.getInt("count", 30);
                sharedPreferences.edit().putInt("toSend", 1).apply();
                sharedPreferences.edit().putInt("counter", count).apply();
                ic.commitText(message, 1);
                ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER));
                ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER));
                break;
            case -1001:
                //edit message button
                Intent openset = new Intent(getApplicationContext(),MainActivity.class);
                openset.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(openset);
                break;
            case -1002:
                //exit button
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showInputMethodPicker();
                break;
            case  -1003:
                break;
            case -1004:
                //caps
                if (!capsLock){
                    kv.setKeyboard(caps);
                }else {
                    kv.setKeyboard(keyboard);
                }
                capsLock = !capsLock;
                break;
            default:
                char code = (char)primaryCode;
                ic.commitText(String.valueOf(code),1);
        }

    }

    private void playClick(){
        Vibrator vb = (Vibrator)   getSystemService(Context.VIBRATOR_SERVICE);
        vb.vibrate(30);

        AudioManager am = (AudioManager)getSystemService(AUDIO_SERVICE);
        am.playSoundEffect(AudioManager.FX_KEY_CLICK);
    }

    @Override
    public void onText(CharSequence charSequence) {}

    @Override
    public void swipeLeft() {}

    @Override
    public void swipeRight() {}

    @Override
    public void swipeDown() {}

    @Override
    public void swipeUp() {}
}
