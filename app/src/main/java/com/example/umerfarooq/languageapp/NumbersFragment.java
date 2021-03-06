package com.example.umerfarooq.languageapp;


import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class NumbersFragment extends Fragment {
    private MediaPlayer mediaPlayer;
    private AudioManager audioManager;
    AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener =
            new AudioManager.OnAudioFocusChangeListener() {
                @Override
                public void onAudioFocusChange(int focusChange) {
                    if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                            focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                        mediaPlayer.pause();
                        mediaPlayer.seekTo(0);
                    } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                        mediaPlayer.start();

                    } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                        releaseMediaPlayer();
                    }
                }
            };

    public NumbersFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.word_list, container, false);

        /** TODO: Insert all the code from the NumberActivity’s onCreate() method after the setContentView method call */


        audioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Word> words = new ArrayList<Word>();

        words.add(new Word("One", "Aik", R.drawable.one, R.raw.aik));
        words.add(new Word("Two", "Dow", R.drawable.two, R.raw.dou));
        words.add(new Word("Three", "Teen", R.drawable.three, R.raw.teen));
        words.add(new Word("Four", "Chaar", R.drawable.four, R.raw.chaar));
        words.add(new Word("Five", "Paanch", R.drawable.five, R.raw.paanch));
        words.add(new Word("Six", "chay", R.drawable.six, R.raw.chay));
        words.add(new Word("Seven", "Saath", R.drawable.seven, R.raw.saath));
        words.add(new Word("Eight", "Aath", R.drawable.eight, R.raw.aath));
        words.add(new Word("Nine", "Nau", R.drawable.nine, R.raw.nau));
        words.add(new Word("Ten", "Das", R.drawable.ten, R.raw.das));
        WordAdapter adapter = new WordAdapter(getActivity(), words, R.color.category_numbers);

        // Get a reference to the ListView, and attach the adapter to the listView.
        ListView listView = (ListView) rootView.findViewById(R.id.word_list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(Numbers.this, "List Item Clicked", Toast.LENGTH_SHORT).show();
                Word word = words.get(position);


                int result = audioManager.requestAudioFocus(mOnAudioFocusChangeListener,
                        AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {

//                    audioManager.registerMediaButtonEventReceiver(RemoteControlReceiver);
                }

                mediaPlayer = MediaPlayer.create(getActivity(), word.getaudio());
                mediaPlayer.start();

                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        releaseMediaPlayer();

                    }


                });
            }
        });


        return rootView;
    }

    public void releaseMediaPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.release();

            mediaPlayer = null;

            audioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }


    }

    @Override
    public void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

}


