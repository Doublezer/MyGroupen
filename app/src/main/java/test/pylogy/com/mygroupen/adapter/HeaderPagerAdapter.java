package test.pylogy.com.mygroupen.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import test.pylogy.com.mygroupen.fragment.HdFragment_1;
import test.pylogy.com.mygroupen.fragment.HdFragment_2;
import test.pylogy.com.mygroupen.fragment.HdFragment_3;

/**
 * Created by Administrator on 2017/6/16 0016.
 */

public class HeaderPagerAdapter extends FragmentPagerAdapter {

    public HeaderPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new HdFragment_1();
            case 1:
                return new HdFragment_2();
            case 2:
                return new HdFragment_3();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
