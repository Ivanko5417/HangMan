package com.hangman.guess;

import android.view.View;
import android.widget.TextView;

import com.hangman.word.WordStructure;

import java.util.ArrayList;
import java.util.Random;

public class Guesses {
    private View view;
    private View catView;
    private char[] word;
    private String selectedWord;
    private String selectedCategory;
    private TextView guessedLetters;
    private TextView category;
    private Integer strikes = 0;

    public Guesses(){
        
    }
    
    public char[] assignWord(String[] words, View v){
        this.setView(v);
        guessedLetters = (TextView)v;
        
        char[] localWord;
        Random randomGenerator = new Random();
        int randomInt = randomGenerator.nextInt(words.length);
        selectedWord = words[randomInt];
        String spacedWord = "";
        
        localWord = new char[selectedWord.length()];
        selectedWord.getChars(0, selectedWord.length(), localWord, 0);
        
        for(char ltr : localWord){
            guessedLetters.append("_ ");
            spacedWord = spacedWord + ltr + " ";
        }
        
        localWord = new char[spacedWord.length()];
        spacedWord.getChars(0, spacedWord.length(), localWord, 0);
        this.word = localWord;
                
        return localWord;
    }

    public char[] assignWord(WordStructure[] allWords, View gl, View cat, int minDifficulty, int maxDifficulty){
        ArrayList<WordStructure> words = new ArrayList<WordStructure>();
        for (WordStructure wordObj : allWords) {
            String word = wordObj.getWord();
            if (word.length() >= minDifficulty && word.length() <= maxDifficulty) {
                words.add(wordObj);
            }
        }
        this.setView(gl);
        guessedLetters = (TextView)gl;
        this.setCatView(cat);
        category = (TextView)cat;
        
        char[] localWord;
        Random randomGenerator = new Random();
        int randomInt = randomGenerator.nextInt(words.size());
        selectedWord = words.get(randomInt).getWord();
        selectedCategory = words.get(randomInt).getCategory();
        String spacedWord = "";
        
        localWord = new char[selectedWord.length()];
        selectedWord.getChars(0, selectedWord.length(), localWord, 0);
        
        for(char ltr : localWord){
            guessedLetters.append("_ ");
            spacedWord = spacedWord + ltr + " ";
        }
        
        localWord = new char[spacedWord.length()];
        spacedWord.getChars(0, spacedWord.length(), localWord, 0);
        category.setText(selectedCategory);
        this.word = localWord;
                
        return localWord;
    }
    
    public Integer letterGuess(char letter){
        System.out.println(letter);
        CharSequence original = guessedLetters.getText();
        guessedLetters.setText("");
        
        boolean found = false;
        
        for(int x = 0; x < word.length; x++){
            char wordLtr = word[x];
            
            if(Character.toUpperCase(wordLtr) == Character.toUpperCase(letter)){
                guessedLetters.append(Character.toUpperCase(letter) + "");
                found = true;
            } else {
                guessedLetters.append(Character.toUpperCase(original.charAt(x)) + "");
            }
        }

        if(!found)
            strikes++;
        
        return strikes;
    }

//    public String getWord() { return  }
    
    public View getView(){
        return this.view;
    }

    public void setView(View v) {
        this.view = v;        
    }
    public View getCatView(){
        return this.catView;
    }

    public void setCatView(View v) {
        this.catView = v;        
    }
}
