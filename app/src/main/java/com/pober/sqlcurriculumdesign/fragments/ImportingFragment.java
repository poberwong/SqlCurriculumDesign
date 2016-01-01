package com.pober.sqlcurriculumdesign.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.pober.sqlcurriculumdesign.MainActivity;
import com.pober.sqlcurriculumdesign.R;
import com.pober.sqlcurriculumdesign.models.ImportItem;
import com.pober.sqlcurriculumdesign.models.RepertoryItem;
import com.pober.sqlcurriculumdesign.utils.EasyUtils;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.Date;
import java.util.List;

/**
 * Created by Bob on 15/12/28.
 */
public class ImportingFragment extends Fragment {
    View rootView;
    private FloatingActionButton fbSubmit;
    private MaterialEditText eTBarCode, eTGoodsName, eTManu, eTStandard, eTRetailPrice, eTImportPrice, eTCount, eTDate;
    private boolean isInRepe= false;
    public static ImportingFragment newInstance(){
        return new ImportingFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_importing, null);
        initView(rootView);
        fbSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitMessage();
            }
        });
        eTBarCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                resetFab();
                isInRepe = false;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                eTDate.setText(DateFormat.format("yyyy/MM/dd HH:mm", new Date()).toString());
                List<RepertoryItem> items = MainActivity.service.RepeQuery(RepertoryItem.BAR_CODE+ "= ?", new String[]{eTBarCode.getText().toString()});
                if (items.size()> 0){
                    Toast.makeText(getActivity(), items.size()+"", Toast.LENGTH_SHORT).show();
                    isInRepe = true;
                    fillWithRepeInfo(items.get(0));
                } else {
                    isInRepe = false;
                    resetWidget();
                }
            }
        });

        return rootView;
    }

    private void initView(View view){
        fbSubmit = (FloatingActionButton) view.findViewById(R.id.submit_import);
        eTBarCode = (MaterialEditText) view.findViewById(R.id.et_bar_code);
        eTGoodsName = (MaterialEditText) view.findViewById(R.id.et_goods_name);
        eTManu = (MaterialEditText) view.findViewById(R.id.et_manu);
        eTStandard = (MaterialEditText) view.findViewById(R.id.et_standard);
        eTRetailPrice = (MaterialEditText) view.findViewById(R.id.et_retail_price);
        eTImportPrice = (MaterialEditText) view.findViewById(R.id.et_import_price);
        eTCount = (MaterialEditText) view.findViewById(R.id.et_count);
        eTDate = (MaterialEditText) view.findViewById(R.id.et_date);
        eTDate.setEnabled(false);
    }

    private void resetFab(){
        fbSubmit.setIcon(R.drawable.fab_tag_follow);
        fbSubmit.setColorNormal(getResources().getColor(R.color.white));
        fbSubmit.setColorPressed(getResources().getColor(R.color.half_white));
    }

    private void fillWithRepeInfo(RepertoryItem item){
        eTGoodsName.setText(item.getGoodsName());
        eTManu.setText(item.getManufacturer());
        eTStandard.setText(item.getStandard());
        eTRetailPrice.setText(item.getRetailPrice());
    }

    private void resetWidget(){
        eTGoodsName.setText("");
        eTManu.setText("");
        eTStandard.setText("");
        eTRetailPrice.setText("");
        eTCount.setText("");
        eTImportPrice.setText("");
    }

    // TODO: 16/1/1 deal with regular expression
    private void submitMessage(){
        ImportItem importItem = null;
        RepertoryItem repertoryItem = null;
        if (!eTBarCode.getText().toString().isEmpty() && !eTImportPrice.getText().toString().isEmpty() && !eTCount
                .getText().toString().isEmpty() && !eTDate.getText().toString().isEmpty()){
            importItem = new ImportItem();
            importItem.setBarCode(eTBarCode.getText().toString());
            importItem.setPrice(eTImportPrice.getText().toString());
            importItem.setCount(eTCount.getText().toString());
            importItem.setDate(EasyUtils.date2Str(eTDate.getText().toString()));
        } else {
            Toast.makeText(getActivity(), "请补全信息~", Toast.LENGTH_SHORT).show();
            return;
        }

        //只有未在库中时，才检查后几个控件
        if (!isInRepe && !eTBarCode.getText().toString().isEmpty() && !eTGoodsName.getText().toString().isEmpty() && !eTCount.getText().toString().isEmpty()
                && !eTManu.getText().toString().isEmpty() && !eTStandard.getText().toString().isEmpty() && !eTRetailPrice.getText().toString().isEmpty()){
            repertoryItem = new RepertoryItem();
            repertoryItem.setBarCode(eTBarCode.getText().toString());
            repertoryItem.setGoodsName(eTGoodsName.getText().toString());
            repertoryItem.setCount(eTCount.getText().toString());
            repertoryItem.setManufacturer(eTManu.getText().toString());
            repertoryItem.setStandard(eTStandard.getText().toString());
            repertoryItem.setRetailPrice(eTRetailPrice.getText().toString());
        } else if(isInRepe){
            repertoryItem = null;
        } else {
            Toast.makeText(getActivity(), "请补全信息~", Toast.LENGTH_SHORT).show();
            return;
        }

       if (MainActivity.service.importGoods(importItem, repertoryItem)){
           fbSubmit.setIcon(R.drawable.fab_tag_followed);
           fbSubmit.setColorNormal(getResources().getColor(R.color.green));
           fbSubmit.setColorPressed(getResources().getColor(R.color.half_green));
           EasyUtils.showSnackBar(getActivity(), rootView, "入库成功...", R.color.half_green);
       } else {
           EasyUtils.showSnackBar(getActivity(), rootView, "入库失败...", R.color.half_red);
       }
    }
}
