package com.pober.sqlcurriculumdesign;

import android.os.Build;
import android.os.Bundle;
import android.os.Trace;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.pober.sqlcurriculumdesign.db.DBInfo;
import com.pober.sqlcurriculumdesign.db.DBService;
import com.pober.sqlcurriculumdesign.fragments.ContentFragment;
import com.pober.sqlcurriculumdesign.fragments.OperationFragment;
import com.pober.sqlcurriculumdesign.models.ImportItem;
import com.pober.sqlcurriculumdesign.models.OperateItem;
import com.pober.sqlcurriculumdesign.models.RepertoryItem;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.yalantis.contextmenu.lib.ContextMenuDialogFragment;
import com.yalantis.contextmenu.lib.MenuObject;
import com.yalantis.contextmenu.lib.MenuParams;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemClickListener;

import java.util.ArrayList;
import java.util.List;

import me.drakeet.materialdialog.MaterialDialog;

public class MainActivity extends AppCompatActivity implements OnMenuItemClickListener {

    public static DBService service;
    private ContextMenuDialogFragment mMenuDialogFragment;
    private FragmentManager mFragmentManager;
    private Fragment currentFragment;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        service = DBService.getInstance(this);
        mFragmentManager = getSupportFragmentManager();
        currentFragment = OperationFragment.newInstance();
        mFragmentManager.beginTransaction().replace(R.id.fragment_container, currentFragment).commit();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }

        MenuParams menuParams = new MenuParams();
        menuParams.setActionBarSize((int) getResources().getDimension(R.dimen.actionbar_size));
        menuParams.setMenuObjects(getMenuObjects());
        menuParams.setClosableOutside(true);
        // set other settings to meet your needs
        initMenuFragment();
    }


    private void initMenuFragment() {
        MenuParams menuParams = new MenuParams();
        menuParams.setActionBarSize((int) getResources().getDimension(R.dimen.actionbar_size));
        menuParams.setMenuObjects(getMenuObjects());
        menuParams.setClosableOutside(false);
        mMenuDialogFragment = ContextMenuDialogFragment.newInstance(menuParams);
        mMenuDialogFragment.setItemClickListener(this);
    }

    private List<MenuObject> getMenuObjects() {

        List<MenuObject> menuObjects = new ArrayList<>();

        MenuObject close = new MenuObject("取消");
        close.setResource(R.drawable.icon_back);
        close.setBgColor(getResources().getColor(R.color.half_red));

        MenuObject repeQuery = new MenuObject("库存查询");
        repeQuery.setResource(R.drawable.tab_home_icon);
        repeQuery.setBgColor(getResources().getColor(R.color.half_blue));

        MenuObject importQuery = new MenuObject("进货查询");
        importQuery.setResource(R.drawable.tab_explore_icon);
        importQuery.setBgColor(getResources().getColor(R.color.half_green));

        MenuObject exportQuery = new MenuObject("售货查询");
        exportQuery.setResource(R.drawable.tab_feed_icon);
        exportQuery.setBgColor(getResources().getColor(R.color.half_black));

        menuObjects.add(close);
        menuObjects.add(repeQuery);
        menuObjects.add(importQuery);
        menuObjects.add(exportQuery);
        return menuObjects;
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.module_query:
                if (mFragmentManager.findFragmentByTag(ContextMenuDialogFragment.TAG) == null) {
                    mMenuDialogFragment.show(mFragmentManager, ContextMenuDialogFragment.TAG);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMenuItemClick(View clickedView, int position) {
        switch (position) {
            case 1: {
                showQueryDialog("库存查询", DBInfo.Table.REPE_TABLE_NAME, false);
            }
            break;
            case 2: {
                showQueryDialog("进货查询", DBInfo.Table.IMPORT_TABLE_NAME, true);
            }
            break;
            case 3: {
                showQueryDialog("售货查询", DBInfo.Table.EXPORT_TABLE_NAME, true);
            }
        }
    }

    public void showQueryDialog(final String title, final String tableName, final boolean canQueryDate) {
        final MaterialEditText etGoodsName, etBarCode, etFromTime, etToTime;
        TextView view;
        View contentView = getLayoutInflater().inflate(R.layout.dialog_query, null);
        etGoodsName = (MaterialEditText) contentView.findViewById(R.id.et_goods_name);
        etBarCode = (MaterialEditText) contentView.findViewById(R.id.et_bar_code);
        etFromTime = (MaterialEditText) contentView.findViewById(R.id.et_from_time);
        etToTime = (MaterialEditText) contentView.findViewById(R.id.et_to_time);
        view = (TextView) contentView.findViewById(R.id.tv_from_to);

        if (!canQueryDate) {
            etFromTime.setVisibility(View.GONE);
            etToTime.setVisibility(View.GONE);
            view.setVisibility(View.GONE);
        }

        final MaterialDialog mMaterialDialog = new MaterialDialog(this).setContentView(contentView)
                .setTitle(title);
        mMaterialDialog.setPositiveButton("查询", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!etGoodsName.getText().toString().isEmpty() || !etBarCode.getText().toString().isEmpty() || (!etFromTime.getText().toString().isEmpty() && !etToTime.getText().toString().isEmpty())) {
                    query();
                } else {
                    Toast.makeText(MainActivity.this, "请补全信息~", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            private void query() {
                if (canQueryDate) {
                    if (tableName.equals(DBInfo.Table.IMPORT_TABLE_NAME)) {
                        List<OperateItem> items = service.importQuery(getArgs(tableName + "."), null);
                        replaceFragment(new ContentFragment(null, items));
                    } else if (tableName.equals(DBInfo.Table.EXPORT_TABLE_NAME)) {
                        List<OperateItem> items = service.exportQuery(getArgs(tableName + "."), null);
                        replaceFragment(new ContentFragment(null, items));
                    }
                } else {
                    List<RepertoryItem> items = service.RepeQuery(getArgs(tableName + "."), null);
                    replaceFragment(new ContentFragment(items, null));
                }
                mMaterialDialog.dismiss();
            }

            private void replaceFragment(Fragment fragment) {
                mToolbar.setTitle(title);
                FragmentTransaction transaction;
                transaction = mFragmentManager.beginTransaction();
                transaction.replace(R.id.fragment_container, fragment).commit();
                currentFragment = fragment;
            }

            private String getArgs(String tableName) {
                String selection = "";
                boolean first = false, second = false;
                if (!etGoodsName.getText().toString().isEmpty()) {
                    selection += RepertoryItem.GOODS_NAME + "= '" + etGoodsName.getText().toString() + "'";
                    first = true;
                }

                if (!etBarCode.getText().toString().isEmpty()) {
                    if (first == true) {
                        selection += " and ";
                    }
                    selection += tableName + ImportItem.BAR_CODE + "= " + etBarCode.getText().toString();
                    second = true;
                }

                if (canQueryDate && !etFromTime.getText().toString().isEmpty() && !etToTime.getText().toString().isEmpty()) {
                    if (second == true) {
                        selection += " and ";
                    }
                    selection += ImportItem.DATE + " between '" + etFromTime.getText().toString() + "' and '" + etToTime.getText().toString() + "'";
//                    selection += ImportItem.DATE + " < " + etToTime.getText().toString() + " and " + ImportItem.DATE + " > " + etFromTime.getText().toString();
                }
                return selection;
            }
        }).setNegativeButton("取消", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMaterialDialog.dismiss();
            }
        });
        mMaterialDialog.show();
    }

    @Override
    public void onBackPressed() {
        if (currentFragment instanceof OperationFragment){
            super.onBackPressed();
        }else {
            Fragment fragment= OperationFragment.newInstance();
            mFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();
            currentFragment = fragment;
            mToolbar.setTitle("进 货");
        }
    }
}