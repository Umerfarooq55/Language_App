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
public class FamilyFragment extends Fragment {
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


    public FamilyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.word_list, container, false);

        audioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Word> words = new ArrayList<Word>();
        words.add(new Word("GrandFather", "Dada", R.drawable.dada, R.raw.dada));
        words.add(new Word("GrandMother", "Dadi", R.drawable.dadi, R.raw.dadi));
        words.add(new Word("Mother", "Maa", R.drawable.mother, R.raw.maa));
        words.add(new Word("Father", "Walid", R.drawable.father, R.raw.walid));
        words.add(new Word("Son", "Baita", R.drawable.boy, R.raw.baita));
        words.add(new Word("Daughter", "Baiti", R.drawable.sister, R.raw.baiti));
        words.add(new Word("Brother", "Bhai", R.drawable.brother, R.raw.bhai));
//        words.add(new Word("Sister","Behan",R.drawable.sister,R.raw.pillow_talk));
        words.add(new Word("Husband", "Shohar", R.drawable.husband, R.raw.shohar));
        words.add(new Word("Wife", "Biwi", R.drawable.wife, R.raw.biwi));

        WordAdapter adapter = new WordAdapter(getActivity(), words, R.color.category_family);

        // Get a reference to the ListView, and attach the adapter to the listView.
        ListView listView = (ListView) rootView.findViewById(R.id.word_list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(Family.this, "List Item Clicked", Toast.LENGTH_SHORT).show();
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



