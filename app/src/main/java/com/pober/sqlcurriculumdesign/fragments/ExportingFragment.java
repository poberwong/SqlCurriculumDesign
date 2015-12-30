package com.pober.sqlcurriculumdesign.fragments;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.dd.CircularProgressButton;
import com.pober.sqlcurriculumdesign.R;

/**
 * Created by Bob on 15/12/28.
 */
public class ExportingFragment extends Fragment {
    CircularProgressButton cBAccount;

    public static ExportingFragment newInstance() {
        return new ExportingFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_exporting, null);
        cBAccount = (CircularProgressButton) rootView.findViewById(R.id.cb_account);

        cBAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cBAccount.getProgress() == 0) {
                    simulateSuccessProgress(cBAccount);
                } else {
                    cBAccount.setProgress(0);
                }
            }
        });
        return rootView;
    }

    private void simulateSuccessProgress(final CircularProgressButton button) {
        ValueAnimator widthAnimation = ValueAnimator.ofInt(1, 100);
        widthAnimation.setDuration(1500);
        widthAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        widthAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Integer value = (Integer) animation.getAnimatedValue();
                button.setProgress(value);
            }
        });
        widthAnimation.start();
    }

    private void simulateErrorProgress(final CircularProgressButton button) {
        ValueAnimator widthAnimation = ValueAnimator.ofInt(1, 99);
        widthAnimation.setDuration(1500);
        widthAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        widthAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Integer value = (Integer) animation.getAnimatedValue();
                button.setProgress(value);
                if (value == 99) {
                    button.setProgress(-1);
                }
            }
        });
        widthAnimation.start();
    }
}
