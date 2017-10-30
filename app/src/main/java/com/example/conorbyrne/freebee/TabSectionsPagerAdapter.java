package com.example.conorbyrne.freebee;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


class TabSectionsPagerAdapter extends FragmentPagerAdapter {


    public TabSectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0: WantFragment wantFragment = new WantFragment();
                return wantFragment;

            case 1: HaveFragment haveFragment = new HaveFragment();
                return haveFragment;

            default:
                return null;
        }
    }

    // Number of tabs
    @Override
    public int getCount() {
        return 2 ;
    }

    // Set page title
    public CharSequence getPageTitle(int position){

        switch (position){
            case 0:
                return "I WANT";

            case 1:
                return "I HAVE";

            default:
                return null;
        }
    }
}
