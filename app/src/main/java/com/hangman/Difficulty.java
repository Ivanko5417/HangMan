package com.hangman;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.hangman.database.LocalStorage;

public class Difficulty extends Activity {

    private LocalStorage localStorage;
    private Intent intent = new Intent();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        localStorage = new LocalStorage(this);
        setContentView(R.layout.difficulty);
    }


    public void chooseDifficulty(View view) {
        Button btn = (Button) findViewById(view.getId());
        intent.setClass(this, HangManMain.class);
        intent.putExtra("CATEGORY", getIntent().getStringExtra("CATEGORY"));
        int minDifficulty = 1;
        int maxDifficulty = 4;
        CharSequence text = btn.getText();
        if ("Легко".equals(text)) {
            minDifficulty = 1;
            maxDifficulty = 4;
        } else if ("Средне".equals(text)) {
            minDifficulty = 5;
            maxDifficulty = 6;
        } else if ("Сложно".equals(text)) {
            minDifficulty = 7;
            maxDifficulty = 10000;
        }
        intent.putExtra("MIN_DIFFICULTY", ""+minDifficulty);
        intent.putExtra("MAX_DIFFICULTY", ""+maxDifficulty);
        startActivity(intent);
    }

    public void reset(View view) {
        intent.putExtra("RESET", "TRUE");
        HangManMain.resetScore = true;
        Toast toast = Toast.makeText(getApplicationContext(),
                "Ваши результаты были сброшены",
                Toast.LENGTH_SHORT);
        toast.show();
    }
}
