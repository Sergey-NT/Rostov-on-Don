package ru.rnd_airport.rostov_on_don.Adapter;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import ru.rnd_airport.rostov_on_don.Fragment.Fragment;
import ru.rnd_airport.rostov_on_don.R;

public class TabsPagerFragmentAdapter extends FragmentPagerAdapter {

    private String[] tabs;
    private String planeNumber;
    private String direction;

    public TabsPagerFragmentAdapter(Context context, String planeNumber, String direction, FragmentManager fm) {
        super(fm);

        this.planeNumber = planeNumber;
        this.direction = direction;

        tabs = new String[] {
                context.getString(R.string.tabs_item_arrival),
                context.getString(R.string.tabs_item_departure)
        };
    }

    @Override
    public android.support.v4.app.Fragment getItem(int position) {
        switch (position) {
            case 0:
                if (direction != null && direction.equals("arrival")) {
                    return Fragment.getInstance("arrival", planeNumber);
                } else {
                    return Fragment.getInstance("arrival", null);
                }
            case 1:
                if (direction != null && direction.equals("departure")) {
                    return Fragment.getInstance("departure", planeNumber);
                } else {
                    return Fragment.getInstance("departure", null);
                }
        }
        return null;
    }

    @Override
    public int getCount() {
        return tabs.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabs[position];
    }
}