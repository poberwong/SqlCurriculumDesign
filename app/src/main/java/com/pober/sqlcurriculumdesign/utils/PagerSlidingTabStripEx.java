package com.pober.sqlcurriculumdesign.utils;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;

import java.util.ArrayList;

/**
 * the improvement class for PagerSlidingTabStrip
 * 使用mTabs数组来临时存储Tabs，以此来更新里边每一个tab的状态
 * @author Bob
 */
public class PagerSlidingTabStripEx extends PagerSlidingTabStrip {

    private Context mContext;

    private ViewGroup mTabsContainer;
    private ArrayList<Object> mTabs = new ArrayList<>();

    private int mSelectedColor;
    private int mUnSelectedColor;

    private float mSelectedAlpha = 1.0f;//小于1表示百分比，大于1表示具体的十六进制值
    private float mUnSelectedAlpha = 0.3f;

    public PagerSlidingTabStripEx(Context context) {
        this(context, null);
    }

    public PagerSlidingTabStripEx(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PagerSlidingTabStripEx(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
    }

    /**
     *初始化viewPager，mTabs数组
     * @param pager
     */
    @Override
    public void setViewPager(final ViewPager pager) {
        super.setViewPager(pager);
        mTabs.clear();
        mTabsContainer = (ViewGroup) getChildAt(0);
        for (int i = 0; i < mTabsContainer.getChildCount(); i++) {
            mTabs.add(mTabsContainer.getChildAt(i));
        }
    }

    /**
     * 同步修改tab里的字体颜色
     * 适合于tab里仅由默认的文字组成
     * @param selectedPosition
     */
    public void correctColor(int selectedPosition) {
        for (int i = 0; i < mTabs.size(); i++) {
            ((TextView) mTabs.get(i)).setTextColor(mUnSelectedColor);
        }
        ((TextView) mTabs.get(selectedPosition)).setTextColor(mSelectedColor);
    }

    /**
     * 通过透明度，来改变tab里图片的选中状态
     * @param selectedPosition
     */
    public void correctAlpha(int selectedPosition) {
        for (int i = 0; i < mTabs.size(); i++) {//先全部做成未选中，然后再做选中
            ((View)mTabs.get(i)).setAlpha(mUnSelectedAlpha);
        }
        ((View) mTabs.get(selectedPosition)).setAlpha(mSelectedAlpha);
    }

    /**
     * 选中的字体颜色，这里支持Resource资源，也支持字符型的十六进制值
     * @param colorRes
     */
    public void setSelectedColor(int colorRes) {
        mSelectedColor = mContext.getResources().getColor(colorRes);
    }

    /**
     * 选中的字体颜色，这里支持Resource资源，也支持字符型的十六进制值
     * @param color
     */
    public void setSelectedColor(String color) {
        mSelectedColor = Color.parseColor(color);
    }

    /**
     * 未选中的字体颜色，这里支持Resource资源，也支持字符型的十六进制值
     * @param colorRes
     */
    public void setUnSelectedColor(int colorRes) {
        mUnSelectedColor = mContext.getResources().getColor(colorRes);
    }

    /**
     * 未选中的字体颜色，这里支持Resource资源，也支持字符型的十六进制值
     * @param color
     */
    public void setUnSelectedColor(String color) {
        mUnSelectedColor = Color.parseColor(color);
    }


}
