package com.example.apit.task.view;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.apit.task.R;
import com.example.apit.task.utils.PrefUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewTaskActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.current_task)
    RelativeLayout current_task;
    @BindView(R.id.newTasks)
    RelativeLayout newTasks;
    @BindView(R.id.finishedTasks)
    RelativeLayout finishedTasks;
    @BindView(R.id.lateTasks)
    RelativeLayout lateTasks;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);
        ButterKnife.bind(this);

        current_task.setOnClickListener(this);
        newTasks.setOnClickListener(this);
        finishedTasks.setOnClickListener(this);
        lateTasks.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id=view.getId() ;
        if (id == R.id.current_task) {
            PrefUtils.storeKeys(this, this.getString(R.string.search_selected),"2");
            Intent intent = new Intent(this , NewTaskHeads.class);
            startActivity(intent);
        }
        else if(id==R.id.newTasks){
            PrefUtils.storeKeys(this, this.getString(R.string.search_selected), "1");
            Intent intent = new Intent(this , NewTaskHeads.class);
            startActivity(intent);
        }
        else if(id == R.id.finishedTasks){
            PrefUtils.storeKeys(this, this.getString(R.string.search_selected),"4");
            Intent intent = new Intent(this , NewTaskHeads.class);
            startActivity(intent);
        }
        else if(id==R.id.lateTasks){
            PrefUtils.storeKeys(this, this.getString(R.string.search_selected), "3");
            Intent intent = new Intent(this , NewTaskHeads.class);
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        NewTaskActivity.this.finish();
        super.onBackPressed();
    }
}
