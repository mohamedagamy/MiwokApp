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

public class NumbersFragment extends Fragment {

    private MediaPlayer mediaPlayer;
    private AudioManager mAudioManager;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = getLayoutInflater().inflate(R.layout.master_fragment_layout, container, false);

        mAudioManager= (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        final List<Word> words=new ArrayList<>();
        words.add(new Word(getString(R.string.number_one), getString(R.string.miwok_number_one), R.drawable.number_one, R.raw.number_one));
        words.add(new Word(getString(R.string.number_two), getString(R.string.miwok_number_two), R.drawable.number_two, R.raw.number_two));
        words.add(new Word(getString(R.string.number_three), getString(R.string.miwok_number_three), R.drawable.number_three, R.raw.number_three));
        words.add(new Word(getString(R.string.number_four), getString(R.string.miwok_number_four), R.drawable.number_four, R.raw.number_four));
        words.add(new Word(getString(R.string.number_five), getString(R.string.miwok_number_five), R.drawable.number_five, R.raw.number_five));
        words.add(new Word(getString(R.string.number_six), getString(R.string.miwok_number_six), R.drawable.number_six, R.raw.number_six));
        words.add(new Word(getString(R.string.number_seven), getString(R.string.miwok_number_seven), R.drawable.number_seven, R.raw.number_seven));
        words.add(new Word(getString(R.string.number_eight), getString(R.string.miwok_number_eight), R.drawable.number_eight, R.raw.number_eight));
        words.add(new Word(getString(R.string.number_nine), getString(R.string.miwok_number_nine), R.drawable.number_nine, R.raw.number_nine));
        words.add(new Word(getString(R.string.number_ten), getString(R.string.miwok_number_ten), R.drawable.number_ten, R.raw.number_ten));

        WordAdapter itemsAdapter = new WordAdapter(getActivity(), words , R.color.category_numbers);
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



    @Override
    public void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    private void releaseMediaPlayer()
    {
        if (mediaPlayer != null) {
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
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

}
