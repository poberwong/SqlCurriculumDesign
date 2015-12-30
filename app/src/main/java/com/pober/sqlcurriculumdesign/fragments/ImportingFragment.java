package com.pober.sqlcurriculumdesign.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.pober.sqlcurriculumdesign.R;

/**
 * Created by Bob on 15/12/28.
 */
public class ImportingFragment extends Fragment {
    private FloatingActionButton fbSubmit;
    public static ImportingFragment newInstance(){
        return new ImportingFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_importing, null);
        fbSubmit = (FloatingActionButton) rootView.findViewById(R.id.submit_import);
        fbSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fbSubmit.setIcon(R.drawable.fab_tag_followed);
                fbSubmit.setColorNormal(getResources().getColor(R.color.green));
                fbSubmit.setColorPressed(getResources().getColor(R.color.half_green));
            }
        });
        return rootView;
    }

    private void resetFab(){
        fbSubmit.setIcon(R.drawable.fab_tag_follow);
        fbSubmit.setColorNormal(getResources().getColor(R.color.white));
        fbSubmit.setColorPressed(getResources().getColor(R.color.half_white));
    }
}
