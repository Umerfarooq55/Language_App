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
public class PhrasesFragment extends Fragment {
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

    public PhrasesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.word_list, container, false);
        audioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Word> words = new ArrayList<Word>();

        words.add(new Word("Main Khush hun", "I am Happy.", R.raw.pi_am_happy));
        words.add(new Word("Main tyar hun", "I am Ready. ", R.raw.pi_am_ready));
        words.add(new Word("kia tum theek ho?", "Are you alright?", R.raw.pare_you_alright));
        words.add(new Word("Mujhay aik cup coffee do", "Give me cup of Coffee.", R.raw.pgive_me_cup_of_coffee));
        words.add(new Word("kia tumnay dhoondh lia?", "Can you find it?", R.raw.pcan_you_find_it));
        words.add(new Word("Maray pas buht kitabain hain", "I have many books!", R.raw.pi_have_many_books));
        words.add(new Word("Koi nahi janta.", "No one Knows.", R.raw.pno_one_knows));
        words.add(new Word("Woh achay log hain.", "They are good people.", R.raw.pthey_are_good_people));
        words.add(new Word("Tum Zaror Jao.", "Yo must Go.", R.raw.pyou_must_go));
        words.add(new Word("Hum ail dusray say piyar kartay.", "We love each other.", R.raw.pwe_love_each_other));
        WordAdapter adapter = new WordAdapter(getActivity(), words, R.color.category_Phrases);

        // Get a reference to the ListView, and attach the adapter to the listView.
        ListView listView = (ListView) rootView.findViewById(R.id.word_list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(Phrases.this, "List Item Clicked", Toast.LENGTH_SHORT).show();
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

    public void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }


}
