package com.hangman;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.hangman.database.LocalStorage;
import com.hangman.guess.Guesses;
import com.hangman.word.GlobalWords;
import com.hangman.word.WordStructure;

import java.util.ArrayList;
import java.util.Iterator;

public class HangManMain extends Activity implements GlobalWords {
    //The below array will be used to initialize the database if it is empty
    private WordStructure[] structuredWords;

    public static boolean resetScore = false;
    
    private LocalStorage localStorage;

    private char[] lastWord;
    
    //array "word" used to test individual letters.
    private char[] word;
    
    private TextView guessedLetters;
    private TextView txtWins;
    private TextView txtLosses;
    private TextView txtCategory;

    private String category;
    private int minDifficulty;
    private int maxDifficulty;
    //ImageView to hold the main game screen where the gallows is displayed.
    private ImageView hangmanImage;
    
    //class to handle processing of guesses.
    private Guesses guess;
    
    //strikeNumber will hold the resource value of the gallows images.
    static int[] strikeNumber = new int[8];
    
    Integer strikes = 0;
    Integer wins = 0;
    Integer losses = 0;
    long rowId = 0;

    //static block to initialize the chalk board image array for guesses.
    static{
        strikeNumber[0] = R.drawable.base;
        strikeNumber[1] = R.drawable.strike1;
        strikeNumber[2] = R.drawable.strike2;
        strikeNumber[3] = R.drawable.strike3;
        strikeNumber[4] = R.drawable.strike4;
        strikeNumber[5] = R.drawable.strike5;
        strikeNumber[6] = R.drawable.gameover;
        strikeNumber[7] = R.drawable.youwin;
    }

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        localStorage = new LocalStorage(this);
        if (resetScore) {
            localStorage.setItem("wins", "0");
            localStorage.setItem("loses", "0");
            resetScore = false;
        }
        category = getIntent().getStringExtra("CATEGORY");
        minDifficulty = Integer.parseInt(getIntent().getStringExtra("MIN_DIFFICULTY"));
        maxDifficulty = Integer.parseInt(getIntent().getStringExtra("MAX_DIFFICULTY"));
        //pull text views for updating as the user plays guesses/games.
        guessedLetters = (TextView) findViewById(R.id.GuessedLetters);
        txtWins = (TextView) findViewById(R.id.Win);
        txtLosses = (TextView) findViewById(R.id.Loss);
        txtCategory = (TextView) findViewById(R.id.Category);

        //assign the comic font used in the letter images to the below textviews.
        //can't assign custom fonts in layout xml except for custom controls.        
        Typeface myTypeface = Typeface.createFromAsset(this.getAssets(),
        "comic.ttf");
        guessedLetters.setTypeface(myTypeface);
        txtWins.setTypeface(myTypeface);
        txtLosses.setTypeface(myTypeface);
        txtCategory.setTypeface(myTypeface);

        Iterator<WordStructure> iterator = structuredWordList.iterator();
        ArrayList<WordStructure> cutegoryWordsList = new ArrayList<WordStructure>();

        while (iterator.hasNext()) {
            WordStructure currentWord = iterator.next();
            if (currentWord.getCategory().toLowerCase().equals(category.toLowerCase())) {
                cutegoryWordsList.add(currentWord);
            }
        }

        structuredWords = cutegoryWordsList.toArray(new WordStructure[0]);

        wins = Integer.parseInt(localStorage.getItem("wins"));
        losses = Integer.parseInt(localStorage.getItem("loses"));

        //set category and stats
        txtWins.setText(wins.toString());
        txtLosses.setText(losses.toString());
        txtCategory.setText("misc");

        //set initial gallows image
        hangmanImage = (ImageView) findViewById(R.id.HangManImage);
        hangmanImage.setImageResource(strikeNumber[strikes]);
        
        guess = new Guesses();

        //set the word to be guessed.
        if(lastWord == null || !getLastWord().toString().equals(getWord().toString())){
            setWord(guess.assignWord(structuredWords, guessedLetters, txtCategory, minDifficulty, maxDifficulty));
            setLastWord(getWord());
        }else{
            while(getLastWord().toString().equals(getWord().toString())){
                setWord(guess.assignWord(structuredWords, guessedLetters, txtCategory, minDifficulty, maxDifficulty));
            }
            setLastWord(getWord());
        }        
    }

    private void checkScore() {
        hangmanImage.setImageResource(strikeNumber[strikes]);
        Boolean win = true;
        
        for (int x = 0; x < guessedLetters.length(); x++) {
            if (guessedLetters.getText().charAt(x) == '_')
                win = false;
        }

        if (win) {
            wins += 1;
            localStorage.setItem("wins", ""+wins);
            txtWins.setText(wins.toString());
            hangmanImage.setImageResource(strikeNumber[7]);
            userContinue("Вы победили! Поздравляю!  ");
        }

        if (strikes == 6) {
            char[] word = this.getWord();
            String losingText = "Вы програли! Ваше слово:\n " + new String(this.word).replaceAll(" ", "").toUpperCase();
            losses += 1;
            localStorage.setItem("loses", ""+losses);
            txtLosses.setText(losses.toString());
            userContinue(losingText);
        }
    }

    public char[] getWord() {
        return word;
    }
    
    public char[] getLastWord(){
        return lastWord;
    }

    public void letterClick(View view) {
        Button btn = (Button) findViewById(view.getId());
        btn.setEnabled(false);
        btn.setBackgroundColor(Color.WHITE);
        strikes = guess.letterGuess(btn.getText().charAt(0));
        checkScore();
    }

    private void newGame() {
        strikes = 0;

        Bundle Bundle = new Bundle();
        onCreate(Bundle);
    }

    public void setWord(char[] word) {
        this.word = word;
    }
    
    public void setLastWord(char[] word){
        this.lastWord = word;
    }

    
    //handle the popup used to ask if the user wants to play again or quit.
    public void userContinue(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message + "\nЕщё раз?")
        .setCancelable(false)
        .setPositiveButton("Да",
                new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog,
                    int which) {
                newGame();
            }
        })
        .setNegativeButton("Нет", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                HangManMain.this.finish();
            }
        });

        AlertDialog endGame = builder.create();
        endGame.show();
    }
}