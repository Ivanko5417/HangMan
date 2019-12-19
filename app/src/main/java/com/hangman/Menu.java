package com.hangman;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Menu extends Activity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
    }


    public void startGame(View view) {
        Intent intent = new Intent();
        intent.setClass(this, Category.class);
        startActivity(intent);
    }

    public void help(View view) {
        Intent intent = new Intent();
        intent.setClass(this, Help.class);
        startActivity(intent);
    }

    public void about(View view) {
        Intent intent = new Intent();
        intent.setClass(this, About.class);
        startActivity(intent);
    }

    public void exit(View view) {
        System.exit(0);
    }
}
