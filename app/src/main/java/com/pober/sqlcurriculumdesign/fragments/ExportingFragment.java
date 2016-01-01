package com.pober.sqlcurriculumdesign.fragments;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.TextView;
import android.widget.Toast;

import com.dd.CircularProgressButton;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.pober.sqlcurriculumdesign.MainActivity;
import com.pober.sqlcurriculumdesign.R;
import com.pober.sqlcurriculumdesign.models.ExportItem;
import com.pober.sqlcurriculumdesign.models.RepertoryItem;
import com.pober.sqlcurriculumdesign.utils.EasyUtils;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Bob on 15/12/28.
 */
public class ExportingFragment extends Fragment {
    private List<ExportItem> cart;
    private CircularProgressButton cBAccount;
    private FloatingActionButton addOnce;
    private MaterialEditText etBarCode, etCount;
    private TextView tvGoodsName, tvExportPrice, tvDate;
    private View rootView;

    public static ExportingFragment newInstance() {
        return new ExportingFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_exporting, null);
        initView(rootView);

        etBarCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                List<RepertoryItem> items = MainActivity.service.RepeQuery(RepertoryItem.BAR_CODE + "= ?", new String[]{etBarCode.getText().toString()});
                if (items.size() > 0) {
                    Toast.makeText(getActivity(), items.size() + "", Toast.LENGTH_SHORT).show();
                    fillWithRepeInfo(items.get(0));
                } else {
                    resetWidget();
                }
            }
        });

        addOnce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToCart();
            }
        });

        cBAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cBAccount.getProgress() == 0) {
                    if (cart.size() == 0){
                        EasyUtils.showSnackBar(getActivity(), rootView, "购物车为空...", R.color.half_red);
                        return;
                    }
                    if (MainActivity.service.exportGoods(getActivity(), rootView, cart)){
                        simulateSuccessProgress(cBAccount);
                        EasyUtils.showSnackBar(getActivity(), rootView, "应收货款 "+getTotalPrice(), R.color.half_green);
                        cart.clear();
                    } else {
                        EasyUtils.showSnackBar(getActivity(), rootView, "出货失败,部分商品库存不足...", R.color.half_red);
                    }

                }
            }
        });
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cart = new ArrayList<>();
    }

    private void initView(View rootView){
        etBarCode = (MaterialEditText) rootView.findViewById(R.id.et_bar_code);
        addOnce = (FloatingActionButton) rootView.findViewById(R.id.action_add_onece);
        etCount = (MaterialEditText) rootView.findViewById(R.id.et_count);
        tvGoodsName = (TextView) rootView.findViewById(R.id.tv_goods_name);
        tvExportPrice = (TextView) rootView.findViewById(R.id.tv_export_price);
        tvDate = (TextView) rootView.findViewById(R.id.tv_date);
        cBAccount = (CircularProgressButton) rootView.findViewById(R.id.cb_account);
    }


    private void fillWithRepeInfo(RepertoryItem item){
        tvGoodsName.setText(item.getGoodsName());
        tvExportPrice.setText(item.getRetailPrice());
        tvDate.setText(DateFormat.format("yyyy-MM-dd  HH:mm", new Date()).toString());
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

    private int getTotalPrice(){
        int result = 0;
        for (ExportItem exportItem : cart) {
            result += Integer.parseInt(exportItem.getPrice()) * Integer.parseInt(exportItem.getCount());
        }
        return result;
    }

    private void addToCart(){
        ExportItem item;
        if (!etBarCode.getText().toString().isEmpty() && !etCount.getText().toString().isEmpty() && !tvExportPrice.getText().toString().isEmpty()){
            item = new ExportItem(etBarCode.getText().toString(), tvExportPrice.getText().toString(), tvDate.getText().toString(), etCount.getText().toString());
            cart.add(item);
            EasyUtils.showSnackBar(getActivity(), rootView, "记录添加成功", R.color.half_green);
            cBAccount.setProgress(0);
        } else {
            Toast.makeText(getActivity(), "请补全信息~", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    private void resetWidget(){
        etCount.setText("");
        tvGoodsName.setText("");
        tvExportPrice.setText("");
        cBAccount.setProgress(0);
    }
}
