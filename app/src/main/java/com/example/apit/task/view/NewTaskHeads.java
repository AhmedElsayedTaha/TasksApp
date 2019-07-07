package com.example.apit.task.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apit.task.R;
import com.example.apit.task.app.App;
import com.example.apit.task.model.Tasks;
import com.example.apit.task.presenter.TasksPresenter;
import com.example.apit.task.repositories.imp.TasksRepositoryImp;
import com.example.apit.task.utils.PrefUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewTaskHeads extends AppCompatActivity implements TasksInterface{



    @BindView(R.id.taskRec)
    RecyclerView taskRec;
    @BindView(R.id.noTaskLin)
    LinearLayout noTaskLin;
    @BindView(R.id.mytext)
    TextView mytext;
    @BindView(R.id.swip)
    SwipeRefreshLayout swipRefresh;
    @BindView(R.id.nointernerLin)
            LinearLayout noInternetLin;
    @BindView(R.id.exitBtn)
    Button exitBtn;
    @BindView(R.id.retryBtn)
            Button retryBtn;

    TasksPresenter presenter;
    TasksInterface taskInterface;
    ProgressDialog progressDialog;
    Context context;
    String selected;
    TasksAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task_heads);
        ButterKnife.bind(this);
        App.setContext(this);
        context = App.getContext();
        progressDialog= new ProgressDialog(this,R.style.AppTheme_Dark_Dialog);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("يتم تحميل المهام...");
        progressDialog.show();
        taskInterface = this;
        presenter = new TasksPresenter(new TasksRepositoryImp(), taskInterface);
        presenter.taskRequest();

        selected = PrefUtils.getKeys(this,getResources().getString(R.string.search_selected));
        switch (selected) {
            case "1":
                 mytext.setText("المهام الواردة حديثا");
                break;
            case "2":
                 mytext.setText("المهام الحالية");
                break;
            case "3":
                mytext.setText("المهام المتأخرة");
                break;
            case "4":
                mytext.setText("المهام المنتهية");
                break;
        }

        swipRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.taskRequest();

                if(!isNetworkAvailable()){
                    noInternetLin.setVisibility(View.VISIBLE);
                    noTaskLin.setVisibility(View.GONE);
                    buttonNoInterner();
                }
                else {
                    noInternetLin.setVisibility(View.GONE);
                }
            }
        });

        if(!isNetworkAvailable()){
            noInternetLin.setVisibility(View.VISIBLE);
            noTaskLin.setVisibility(View.GONE);
            buttonNoInterner();
        }
        else {
            noInternetLin.setVisibility(View.GONE);
        }

    }

    @Override
    public void getTasks(Tasks[] tasks) {
        if(tasks!=null) {
            progressDialog.dismiss();
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            taskRec.setLayoutManager(layoutManager);
             adapter = new TasksAdapter(tasks);
            taskRec.setAdapter(adapter);
            noTaskLin.setVisibility(View.GONE);
        }

        stopLoading();
    }

    @Override
    public void noTasks(String message) {
        progressDialog.dismiss();
    //    if(adapter!=null) {
          taskRec.setAdapter(null);
      //  }
        //Toast.makeText(this,"No Tasks here",Toast.LENGTH_SHORT).show();

        stopLoading();
        if(!isNetworkAvailable()){
            noInternetLin.setVisibility(View.VISIBLE);
            noTaskLin.setVisibility(View.GONE);
            buttonNoInterner();
        }
        else {
            noTaskLin.setVisibility(View.VISIBLE);
        }
    }

    public void stopLoading(){
        swipRefresh.setRefreshing(false);
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        presenter.taskRequest();

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), NewTaskActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        super.onBackPressed();
    }

    public void buttonNoInterner(){
        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), NewTaskActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        retryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.taskRequest();
            }
        });
    }
}
