package com.pober.sqlcurriculumdesign;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.getbase.floatingactionbutton.*;
import com.pober.sqlcurriculumdesign.fragments.OperationFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private FloatingActionButton queryRepe, queryImport, queryExport;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, OperationFragment.newInstance()).commit();

        queryRepe = (FloatingActionButton) findViewById(R.id.action_query_repe);
        queryImport = (FloatingActionButton) findViewById(R.id.action_query_import);
        queryExport = (FloatingActionButton) findViewById(R.id.action_query_export);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.action_query_repe:{

            }break;
            case R.id.action_query_import:{

            }break;
            case R.id.action_query_export:{

            }
        }
    }
}
