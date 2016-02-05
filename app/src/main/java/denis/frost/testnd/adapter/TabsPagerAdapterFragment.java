package denis.frost.testnd.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import denis.frost.testnd.fragments.ComingSoonFragment;
import denis.frost.testnd.fragments.ImdbTopFragment;
import denis.frost.testnd.fragments.InTheatersFragment;

public class TabsPagerAdapterFragment extends FragmentPagerAdapter{
    private String [] tabs;
    public TabsPagerAdapterFragment(FragmentManager fm) {
        super(fm);
        tabs = new String [] {"Imdb top 250", "In Theaters", "Coming Soon" };
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabs[position];
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: return ImdbTopFragment.getInstace();
            case 1: return InTheatersFragment.getInstace();
            case 2: return ComingSoonFragment.getInstace();
        }
        return null;
    }

    @Override
    public int getCount() {
        return tabs.length;
    }
}
