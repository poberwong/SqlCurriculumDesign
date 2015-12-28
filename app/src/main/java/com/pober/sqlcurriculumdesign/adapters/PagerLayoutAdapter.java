package com.pober.sqlcurriculumdesign.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.astuetz.PagerSlidingTabStrip;
import com.pober.sqlcurriculumdesign.R;
import com.pober.sqlcurriculumdesign.fragments.ExportingFragment;
import com.pober.sqlcurriculumdesign.fragments.ImportingFragment;

import java.util.HashMap;
import java.util.Map;


public class PagerLayoutAdapter extends FragmentPagerAdapter implements PagerSlidingTabStrip.CustomTabProvider {

    private static final Integer[] CATEGORY_TITLE = {R.string.importing, R.string.exporting};
    private static final Integer[] CATEGORY_ICON = {R.drawable.tab_home_icon, R.drawable.tab_feed_icon};
    private Map<Integer, Fragment> contentFragmentMap = new HashMap<>();

    private LayoutInflater mInflater;
    private Context mContext;
    private String[] mTitles;

    public PagerLayoutAdapter(Context context, FragmentManager fm) {
        super(fm);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mContext = context;
        mTitles = new String[CATEGORY_TITLE.length];
        for (int i = 0; i < CATEGORY_TITLE.length; i++) {
            mTitles[i] = context.getString(CATEGORY_TITLE[i]);
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }

    @Override
    public int getCount() {
        return mTitles.length;
    }

    @Override
    public Fragment getItem(int position) {//在这里给ViewPager里填充具体的布局，即就是ContentFragment
        Fragment contentFragment;
        if (position == 0) {
            contentFragment = ImportingFragment.newInstance();
//            contentFragment = 进货页面;
        } else {
            contentFragment = ExportingFragment.newInstance();
//            contentFragment = 售货页面;
        }
        contentFragmentMap.put(position, contentFragment);
        return contentFragment;
    }

    public Map<Integer, Fragment> getContentFragmentMap() {
        return contentFragmentMap;
    }
    @Override
    public View getCustomTabView(ViewGroup parent, int position) {
        ImageView tabView = (ImageView)mInflater.inflate(R.layout.tab_icon, parent, false);
//        tabView.setBackgroundResource(CATEGORY_ICON[position]);
        tabView.setImageResource(CATEGORY_ICON[position]);
        return tabView;
    }
}
