package com.pober.sqlcurriculumdesign.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pober.sqlcurriculumdesign.R;

/**
 * Created by Bob on 15/12/28.
 */
public class ExportingFragment extends Fragment {
    public static ExportingFragment newInstance(){
        return new ExportingFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_exporting, null);
    }
}
