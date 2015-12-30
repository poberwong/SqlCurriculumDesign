package com.pober.sqlcurriculumdesign;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.pober.sqlcurriculumdesign.db.DBService;
import com.pober.sqlcurriculumdesign.fragments.OperationFragment;
import com.yalantis.contextmenu.lib.ContextMenuDialogFragment;
import com.yalantis.contextmenu.lib.MenuObject;
import com.yalantis.contextmenu.lib.MenuParams;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnMenuItemClickListener {

    public static DBService service ;
    private ContextMenuDialogFragment mMenuDialogFragment;
    private FragmentManager mFragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        service = DBService.getInstance(this);
        mFragmentManager = getSupportFragmentManager();
        mFragmentManager.beginTransaction().replace(R.id.fragment_container, OperationFragment.newInstance()).commit();

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
        close.setResource(R.drawable.ic_action_cancel);
        close.setBgColor(getResources().getColor(R.color.half_red));

        MenuObject repeQuery = new MenuObject("库存查询");
        repeQuery.setResource(R.drawable.tab_home_icon);
        repeQuery.setBgColor(getResources().getColor(R.color.half_blue));

        MenuObject importQuery = new MenuObject("进货查询");
        importQuery.setResource(R.drawable.tab_explore_icon);
        importQuery.setBgColor(getResources().getColor(R.color.half_green));

        MenuObject exportQuery = new MenuObject("出货查询");
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
        Toast.makeText(this, "Clicked on position: " + position, Toast.LENGTH_SHORT).show();
    }
}
