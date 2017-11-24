package com.example.android.miwok.adapter;

import android.content.Context;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.miwok.R;
import com.example.android.miwok.model.Word;

import java.util.List;

/**
 * Created by agamy on 11/22/2017.
 */

public class WordAdapter extends ArrayAdapter<Word>{

    private int mColorResourceId;
    private MediaPlayer mediaPlayer;

    public WordAdapter(@NonNull Context context, @NonNull List<Word> objects , int colorResourceId ) {
        super(context, 0, objects);
        this.mColorResourceId = colorResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        final Word currentWord=getItem(position);
        TextView defaultTextView= listItemView.findViewById(R.id.default_text_view);
        TextView miwokTextView= listItemView.findViewById(R.id.miwok_text_view);
        ImageView miwokImageView= listItemView.findViewById(R.id.src_image_view);
        LinearLayout linearLayout=listItemView.findViewById(R.id.list_item_background);

        if (currentWord != null) {
            defaultTextView.setText(currentWord.getmDefaultTranslation());
            miwokTextView.setText(currentWord.getmMiwokTranslation());
            linearLayout.setBackgroundResource(mColorResourceId);
            if(currentWord.hasImage()) {
                miwokImageView.setImageResource(currentWord.getmImageResourceId());
                miwokImageView.setVisibility(View.VISIBLE);
            }else {
                miwokImageView.setVisibility(View.GONE);
            }
        }


        return listItemView;
    }

    @Nullable
    @Override
    public Word getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }


}
