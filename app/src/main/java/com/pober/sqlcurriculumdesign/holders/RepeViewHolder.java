package com.pober.sqlcurriculumdesign.holders;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pober.sqlcurriculumdesign.R;
import com.pober.sqlcurriculumdesign.models.RepertoryItem;

/**
 * Created by Bob on 15/12/29.
 */

public class RepeViewHolder extends RecyclerView.ViewHolder {
    TextView tvBarCode, tvGoodsName, tvCount, tvManu, tvStandard, tvRetailPrice;
    public RepeViewHolder(Context context, View view){
        super(view);
        initView(view);

        //当然，我们也可以在这里使用view来对RecyclerView的一个item进行事件监听,也可以使用
        //tv等子控件来实现item的子控件的事件监听。这也是我之所以要传context的原因之一呢～
    }

    private void initView(View view){
        tvBarCode = (TextView) view.findViewById(R.id.tv_bar_code);
        tvGoodsName = (TextView) view.findViewById(R.id.tv_goods_name);
        tvCount = (TextView) view.findViewById(R.id.tv_count);
        tvManu = (TextView) view.findViewById(R.id.tv_manu);
        tvStandard = (TextView) view.findViewById(R.id.tv_standard);
        tvRetailPrice = (TextView) view.findViewById(R.id.tv_retail_price);
    }

    public static RepeViewHolder newInstance(Activity context, ViewGroup parent){
        View view = LayoutInflater.from(
                context).inflate(R.layout.repe_item, parent,false);
        return new RepeViewHolder(context, view);
    }

    //你没看错，数据绑定也被整合进来了，
    //将adapter里的数据根据position获取到后传进来。当然，也可以根据具体情况来做调整。
    public void onBinViewHolder(RepertoryItem data){
        tvBarCode.append(data.getBarCode());
        tvGoodsName.append(data.getGoodsName());
        tvCount.append(data.getCount());
        tvManu.append(data.getManufacturer());
        tvStandard.append(data.getStandard());
        tvRetailPrice.append(data.getRetailPrice());
    }
}