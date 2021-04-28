package com.creativeitinstitute.mychat.Adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.creativeitinstitute.mychat.Fragments.ChatsFragment;
import com.creativeitinstitute.mychat.Fragments.ProfileFragment;
import com.creativeitinstitute.mychat.Fragments.StoryFragment;
import com.creativeitinstitute.mychat.Fragments.UserFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {


    String[] names = {
          "Stories",  "User", " Contact","Profile"
    };


    public ViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position) {


            case 0:

                return new StoryFragment();
            case 1:

                return new UserFragment();

            case 2:
                return new ChatsFragment();

            case 3:
                return new ProfileFragment();

        }

        return null;
    }

    @Override
    public int getCount() {
        return names.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return names[position];
    }
}
