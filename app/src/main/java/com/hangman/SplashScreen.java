package com.hangman;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;

import com.hangman.word.GlobalWords;

import static com.hangman.database.ReadXML.readFile;

public class SplashScreen extends Activity implements GlobalWords {

    Intent intent = new Intent();

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        intent.setClass(this, Menu.class);
        Thread splashScreen = new Thread(){
            @Override
            public void run(){
                try{
                    readFile(getApplicationContext());
                    startActivity(intent);
                } catch(ActivityNotFoundException anf){
                    anf.printStackTrace();
                } catch(Exception ex){
                    ex.printStackTrace();
                } finally {
                    finish();
                }
            }
        };

        splashScreen.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        return true;
    }
}
