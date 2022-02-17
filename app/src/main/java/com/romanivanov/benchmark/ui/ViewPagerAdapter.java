package com.romanivanov.benchmark.ui;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.romanivanov.benchmark.R;
import com.romanivanov.benchmark.ui.assembly.AssemblyFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private final Context context;

    public ViewPagerAdapter(@NonNull FragmentManager fm, Context context) {
        super(fm, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.context = context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return AssemblyFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        CharSequence charSequence;
        if (position == 0) {
            charSequence = context.getString(R.string.lists);
        } else if (position == 1) {
            charSequence = context.getString(R.string.maps);
        } else {
            throw new RuntimeException("Unknown assembly type");
        }
        return charSequence;
    }
}
