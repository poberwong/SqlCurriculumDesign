package com.pober.sqlcurriculumdesign.holders;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pober.sqlcurriculumdesign.R;
import com.pober.sqlcurriculumdesign.models.ExportItem;
import com.pober.sqlcurriculumdesign.models.ImportItem;
import com.pober.sqlcurriculumdesign.models.OperateItem;
import com.pober.sqlcurriculumdesign.models.RepertoryItem;

/**
 * Created by Bob on 15/12/29.
 */

public class OperateViewHolder extends RecyclerView.ViewHolder {
    TextView tvBarCode, tvSeqCode, tvPrice, tvCount, tvDate;
    public OperateViewHolder(Context context, View view){
        super(view);
        initView(view);

        //当然，我们也可以在这里使用view来对RecyclerView的一个item进行事件监听,也可以使用
        //tv等子控件来实现item的子控件的事件监听。这也是我之所以要传context的原因之一呢～
    }

    private void initView(View view){
        tvBarCode = (TextView) view.findViewById(R.id.tv_bar_code);
        tvSeqCode = (TextView) view.findViewById(R.id.tv_seq_code);
        tvCount = (TextView) view.findViewById(R.id.tv_count);
        tvPrice = (TextView) view.findViewById(R.id.tv_price);
        tvDate = (TextView) view.findViewById(R.id.tv_date);
    }

    public static OperateViewHolder newInstance(Activity context, ViewGroup parent){
        View view = LayoutInflater.from(
                context).inflate(R.layout.operate_item, parent,false);
        return new OperateViewHolder(context, view);
    }

    //你没看错，数据绑定也被整合进来了，
    //将adapter里的数据根据position获取到后传进来。当然，也可以根据具体情况来做调整。
    public void onBinViewHolder(OperateItem data){
        tvSeqCode.append(data.getSeqCode() + "");
        tvBarCode.append(data.getBarCode());
        tvCount.append(data.getCount());
        tvDate.append(data.getDate());
        if (data instanceof ImportItem){
            tvPrice.setText("进价: "+ data.getPrice());
        }else{
            tvPrice.setText("零售价: " + data.getPrice());
        }
    }
}