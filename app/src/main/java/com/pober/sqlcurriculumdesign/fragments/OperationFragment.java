package com.pober.sqlcurriculumdesign.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pober.sqlcurriculumdesign.R;
import com.pober.sqlcurriculumdesign.adapters.PagerLayoutAdapter;
import com.pober.sqlcurriculumdesign.utils.PagerSlidingTabStripEx;

/**
 * 包含两个Fragment页面，分别是售货，进货
 */

public class OperationFragment extends Fragment implements ViewPager.OnPageChangeListener {
    View mFragmentContainer;
    PagerSlidingTabStripEx mTabs;//tabs
    ViewPager mPager;
    PagerLayoutAdapter mPagerAdapter;
    Toolbar mToolbar;

    public static OperationFragment newInstance() {
        return new OperationFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tabs, null);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mToolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        mFragmentContainer = activity.findViewById(R.id.fragment_container);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPager = (ViewPager) view.findViewById(R.id.pager);
        mPager.setOffscreenPageLimit(1);
        mTabs = (PagerSlidingTabStripEx) view.findViewById(R.id.tabs);
        mTabs.setShouldExpand(true); //此行代码一定要在此才能生效

        mPagerAdapter = new PagerLayoutAdapter(getActivity(), getChildFragmentManager());

        mPager.setAdapter(mPagerAdapter);
        mTabs.setViewPager(mPager);
        //TODO 修改颜色
//        mTabs.setSelectedColor("#ffffff");
//        mTabs.setUnSelectedColor("#eeeeee");
        mTabs.setIndicatorColor(getResources().getColor(R.color.white));
        mTabs.setBackgroundResource(R.color.colorPrimary);
//        mTabs.setBackgroundResource(android.R.color.transparent);
//        mTabs.correctColor(0);
        mTabs.correctAlpha(0);
        mTabs.setOnPageChangeListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mTabs.correctAlpha(position);
//          mTabs.correctColor(position);

        if (mPager != null && mPager.getAdapter() != null) {
            mPager.getAdapter().getPageTitle(position);
            if (mToolbar != null) {
                mToolbar.setTitle(mPager.getAdapter().getPageTitle(position));
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

}
