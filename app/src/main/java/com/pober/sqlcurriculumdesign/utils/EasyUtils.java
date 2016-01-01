package com.pober.sqlcurriculumdesign.utils;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by Bob on 15/12/31.
 */
public class EasyUtils {
    public static void showSnackBar(Context context,View view, String text, int colorRes){
        Snackbar snackbar=  Snackbar.make(view, text, Snackbar.LENGTH_SHORT);
        snackbar.getView().setBackgroundColor(context.getResources().getColor(colorRes));
        ((LinearLayout)snackbar.getView()).setGravity(Gravity.CENTER);
        snackbar.show();
    }

    public static String date2Str(String date){
        date = date.replace("/", "");
        date = date.replace(" ", "");
        date = date.replace(":", "");
        return date.substring(date.length()- 8);
    }

    public static String strToDate(String date){
        date= "2016/"+date.substring(0,2)+"/"+date.substring(2,4)+" "+date.substring(4,6)+":"+date.substring(6);
        return date;
    }
}
