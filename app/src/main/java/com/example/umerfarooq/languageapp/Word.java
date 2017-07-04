package com.example.umerfarooq.languageapp;

/**
 * Created by Umerfarooq on 5/4/2017.
 */

public class Word {

    private static final int NO_IMAGE = -1;
    private String mEnglish;
    private String mUrdu;
    private int mImage = NO_IMAGE;
    private int maudio;


    public Word(String English, String Urdu, int Image, int audio) {

        mEnglish = English;
        mUrdu = Urdu;
        mImage = Image;
        maudio = audio;
    }

    public Word(String English, String Urdu, int audio) {

        mEnglish = English;
        mUrdu = Urdu;
        maudio = audio;

    }

    public String getEnglish() {

        return mEnglish;
    }

    public String getUrdu() {

        return mUrdu;
    }

    public int getmImage() {

        return mImage;
    }

    public Boolean hasImage() {

        return mImage != NO_IMAGE;
    }

    public int getaudio() {

        return maudio;

    }

}





