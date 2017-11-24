package com.example.android.miwok.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.android.miwok.R;
import com.example.android.miwok.fragment.ColorsFragment;
import com.example.android.miwok.fragment.FamilyFragment;
import com.example.android.miwok.fragment.NumbersFragment;
import com.example.android.miwok.fragment.PhrasesFragment;

/**
 * Created by agamy on 11/24/2017.
 */

public class CategoryAdapter extends FragmentPagerAdapter {
    private Context mContext;
    public CategoryAdapter(FragmentManager fm , Context context) {
        super(fm);
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        switch (position)
        {
            case 0:
                return mContext.getString(R.string.category_numbers);
            case 1:
                return mContext.getString(R.string.category_family);
            case 2:
                return mContext.getString(R.string.category_colors);
            case 3:
                return mContext.getString(R.string.category_phrases);

            default:
                return null;
        }
    }

    @Override
    public Fragment getItem(int position) {

        switch (position)
        {
            case 0:
                return new NumbersFragment();
            case 1:
                return new FamilyFragment();
            case 2:
                return new ColorsFragment();
            case 3:
                return new PhrasesFragment();

            default:
                return null;
        }



    }

}
