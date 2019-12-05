package com.hangman;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Menu extends Activity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
    }


    public void chooseCategory(View view) {
        Button btn = (Button) findViewById(view.getId());
        Intent intent = new Intent();
        intent.setClass(this, Difficulty.class);
        intent.putExtra("CATEGORY", btn.getText());
        startActivity(intent);
    }

    public void exit(View view) {
        System.exit(0);
    }
}
