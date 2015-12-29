package com.pober.sqlcurriculumdesign.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.pm.LabeledIntent;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.pober.sqlcurriculumdesign.holders.OperateViewHolder;
import com.pober.sqlcurriculumdesign.holders.RepeViewHolder;
import com.pober.sqlcurriculumdesign.models.ExportItem;
import com.pober.sqlcurriculumdesign.models.ImportItem;
import com.pober.sqlcurriculumdesign.models.OperateItem;
import com.pober.sqlcurriculumdesign.models.RepertoryItem;

import java.util.List;

/**
 * Created by Bob on 15/12/29.
 */
public class DataAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Activity context;
    private List<OperateItem> operateItems;
    private List<RepertoryItem> repeItems;

    public DataAdapter(Activity context, List<RepertoryItem> repeItems, List<OperateItem> operateItems) {
        this.context = context;
        if (operateItems != null) {
            this.operateItems = operateItems;
        } else {
            this.repeItems = repeItems;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {//如需要，还要对viewType做判断
        if (operateItems != null) {
            return OperateViewHolder.newInstance(context, parent);
        } else {
            return RepeViewHolder.newInstance(context, parent);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (operateItems != null) {
            ((OperateViewHolder) holder).onBinViewHolder(operateItems.get(position));
        } else {
            ((RepeViewHolder) holder).onBinViewHolder(repeItems.get(position));
        }
    }

    @Override
    public int getItemCount() {
        if (operateItems != null) {
            return operateItems.size();
        } else {
            return repeItems.size();
        }
    }
}
