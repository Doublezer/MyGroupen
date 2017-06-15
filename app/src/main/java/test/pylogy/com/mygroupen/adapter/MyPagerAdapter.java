package test.pylogy.com.mygroupen.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Administrator on 2017/6/15 0015.
 */

public class MyPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> list;
    public MyPagerAdapter(FragmentManager fm) {
        super(fm);
    }
    public void setList(List<Fragment> list){
        this.list=list;
        notifyDataSetChanged();
    }
    @Override
    public Fragment getItem(int position) {

        return list.get(position);
    }

    @Override
    public int getCount() {
        return list==null?0:list.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return position+"";
    }
}
