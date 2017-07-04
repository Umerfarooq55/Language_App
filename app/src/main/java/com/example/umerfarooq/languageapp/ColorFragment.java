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
public class ColorFragment extends Fragment {
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


    public ColorFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.word_list, container, false);


        audioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
        final ArrayList<Word> words = new ArrayList<Word>();

        words.add(new Word("Black", "Kala", R.drawable.black, R.raw.kala));
        words.add(new Word("Dark Blue", "Nila", R.drawable.darkblue, R.raw.neela));
        words.add(new Word("Red", "Surkh", R.drawable.red, R.raw.surkh));
        words.add(new Word("Gray", "khakeseterey", R.drawable.gray, R.raw.khak));
        words.add(new Word("Orange", "Narangi", R.drawable.orange, R.raw.narangi));
        words.add(new Word("Yellow", "Zard", R.drawable.yellow, R.raw.zard));
        words.add(new Word("Green", "Sabaz", R.drawable.green, R.raw.sabaz));
        words.add(new Word("Blue", "Nila", R.drawable.blue, R.raw.neela));
        words.add(new Word("Purple", "Jamni", R.drawable.purple, R.raw.jamni));
        words.add(new Word("Brown", "ketehe'iy", R.drawable.brown, R.raw.khtai));
        WordAdapter adapter = new WordAdapter(getActivity(), words, R.color.category_color);

        // Get a reference to the ListView, and attach the adapter to the listView.
        ListView listView = (ListView) rootView.findViewById(R.id.word_list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(Colors.this, "List Item Clicked", Toast.LENGTH_SHORT).show();
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
