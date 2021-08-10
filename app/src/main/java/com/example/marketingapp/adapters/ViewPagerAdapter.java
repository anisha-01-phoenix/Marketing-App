package com.example.marketingapp.adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.marketingapp.fragments.Fragment_Order;
import com.example.marketingapp.fragments.Fragment_Pending;
import com.example.marketingapp.fragments.Fragment_Processing;

import java.util.ArrayList;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    ArrayList<Fragment> fragments = new ArrayList<>();
    ArrayList<String> strings = new ArrayList<>();

    public ViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new Fragment_Order();
            case 1:
                return new Fragment_Pending();
            case 2:
                return new Fragment_Processing();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    public void add(Fragment fr, String str) {
        fragments.add(fr);
        strings.add(str);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return strings.get(position);
    }
}
