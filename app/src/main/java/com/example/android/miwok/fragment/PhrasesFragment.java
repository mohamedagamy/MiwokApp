package com.example.android.miwok.fragment;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.android.miwok.R;
import com.example.android.miwok.model.Word;
import com.example.android.miwok.adapter.WordAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by agamy on 11/24/2017.
 */

public class PhrasesFragment extends Fragment {


    private MediaPlayer mediaPlayer;
    private AudioManager mAudioManager;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = getLayoutInflater().inflate(R.layout.master_fragment_layout, container,false);
        mAudioManager= (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        final List<Word> words = new ArrayList<>();
        words.add(new Word(getString(R.string.phrase_where_are_you_going), getString(R.string.miwok_phrase_where_are_you_going),R.raw.phrase_where_are_you_going));
        words.add(new Word(getString(R.string.phrase_what_is_your_name), getString(R.string.miwok_phrase_what_is_your_name),R.raw.phrase_what_is_your_name));
        words.add(new Word(getString(R.string.phrase_my_name_is), getString(R.string.miwok_phrase_my_name_is),R.raw.phrase_my_name_is));
        words.add(new Word(getString(R.string.phrase_how_are_you_feeling), getString(R.string.miwok_phrase_how_are_you_feeling),R.raw.phrase_how_are_you_feeling));
        words.add(new Word(getString(R.string.phrase_im_feeling_good), getString(R.string.miwok_phrase_im_feeling_good),R.raw.phrase_im_feeling_good));
        words.add(new Word(getString(R.string.phrase_are_you_coming), getString(R.string.miwok_phrase_are_you_coming),R.raw.phrase_are_you_coming));
        words.add(new Word(getString(R.string.phrase_yes_im_coming), getString(R.string.miwok_phrase_yes_im_coming),R.raw.phrase_yes_im_coming));
        words.add(new Word(getString(R.string.phrase_im_coming), getString(R.string.miwok_phrase_im_coming),R.raw.phrase_im_coming));
        words.add(new Word(getString(R.string.phrase_lets_go), getString(R.string.miwok_phrase_lets_go),R.raw.phrase_lets_go));
        words.add(new Word(getString(R.string.phrase_come_here), getString(R.string.miwok_phrase_come_here),R.raw.phrase_come_here));

        WordAdapter itemsAdapter = new WordAdapter(getActivity(), words , R.color.category_phrases);
        ListView listView = view.findViewById(R.id.list);
        listView.setAdapter(itemsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                releaseMediaPlayer();

                Word currentWord=words.get(i);
                if (currentWord != null) {
                    startMediaPlayerAudio(currentWord);
                }

            }
        });

        return view;
    }

    private void releaseMediaPlayer()
    {
        if (mediaPlayer != null) {
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    private void startMediaPlayerAudio(Word currentWord) {
        // Request audio focus so in order to play the audio file. The app needs to play a
        // short audio file, so we will request audio focus with a short amount of time
        // with AUDIOFOCUS_GAIN_TRANSIENT.
        int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,
                AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

        if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            // We have audio focus now.

            // Create and setup the {@link MediaPlayer} for the audio resource associated
            // with the current word
            mediaPlayer = MediaPlayer.create(getActivity(), currentWord.getmAudioResourceId());

            // Start the audio file
            mediaPlayer.start();

            // Setup a listener on the media player, so that we can stop and release the
            // media player once the sound has finished playing.
            mediaPlayer.setOnCompletionListener(mCompletionListener);
        }
    }

    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            // Now that the sound file has finished playing, release the media player resources.
            releaseMediaPlayer();
        }
    };
    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                    focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                // The AUDIOFOCUS_LOSS_TRANSIENT case means that we've lost audio focus for a
                // short amount of time. The AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK case means that
                // our app is allowed to continue playing sound but at a lower volume. We'll treat
                // both cases the same way because our app is playing short sound files.

                // Pause playback and reset player to the start of the file. That way, we can
                // play the word from the beginning when we resume playback.
                if (mediaPlayer != null) {
                    mediaPlayer.pause();
                    mediaPlayer.seekTo(0);
                }
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                // The AUDIOFOCUS_GAIN case means we have regained focus and can resume playback.
                if (mediaPlayer != null) {
                    mediaPlayer.start();
                }
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                // The AUDIOFOCUS_LOSS case means we've lost audio focus and
                // Stop playback and clean up resources
                releaseMediaPlayer();
            }
        }
    };

    @Override
    public void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }
}
