package com.pober.sqlcurriculumdesign.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pober.sqlcurriculumdesign.R;
import com.pober.sqlcurriculumdesign.adapters.DataAdapter;
import com.pober.sqlcurriculumdesign.adapters.PagerLayoutAdapter;
import com.pober.sqlcurriculumdesign.models.OperateItem;
import com.pober.sqlcurriculumdesign.models.RepertoryItem;
import com.pober.sqlcurriculumdesign.utils.PagerSlidingTabStripEx;

import java.util.List;
import java.util.Objects;

/**
 * 总共有三种数据类型，1、库存   2、进货    3、售货
 */

public class ContentFragment extends Fragment {

    private List<RepertoryItem> repeItems;
    private List<OperateItem> operateItems;

    private DataAdapter mDataAdapter;
    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;

    public ContentFragment(){}
    @SuppressLint("ValidFragment")
    public ContentFragment(List<RepertoryItem> repeItems, List<OperateItem> operateItems) {
        this.repeItems = repeItems;
        this.operateItems= operateItems;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView= (ViewGroup) inflater.inflate(R.layout.fragment_content, null);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview_content);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mToolbar = (Toolbar) activity.findViewById(R.id.toolbar);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (repeItems!= null){
            mDataAdapter = new DataAdapter(getActivity(), repeItems, null);
        }else {
            mDataAdapter = new DataAdapter(getActivity(), null, operateItems);
        }
        mRecyclerView.setAdapter(mDataAdapter);
    }
}
