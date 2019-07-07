package com.example.apit.task.view;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apit.task.R;
import com.example.apit.task.app.App;
import com.example.apit.task.model.Category;
import com.example.apit.task.model.NEW_TASKS;
import com.example.apit.task.model.Tasks;
import com.example.apit.task.presenter.NewTaskPresenter;
import com.example.apit.task.repositories.imp.NewTaskRepositoryImp;
import com.example.apit.task.utils.PrefUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewTaskDetails extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.mytext)
    TextView mytext;
    @BindView(R.id.taskNo)
            TextView taskNo;
    @BindView(R.id.taskName)
            TextView taskName;
    @BindView(R.id.taskprio)
            TextView textPriority;
    @BindView(R.id.taskState)
            TextView taskState;
    @BindView(R.id.task_req)
            TextView task_req;
    @BindView(R.id.task_category)
            TextView task_category;

    @BindView(R.id.task_startDate)
            TextView task_startDate;
    @BindView(R.id.task_endDate)
    TextView task_endDate;
    @BindView(R.id.task_personResp)
            TextView task_personResp;

    @BindView(R.id.editBtn)
            Button editBtn;


    Tasks taskModel;
    private NEW_TASKS task;
    private Category userCategory;
    String selected;

    private ProgressDialog progressDialog;
    private NewTaskPresenter newTaskPresenter;
    SwipeRefreshLayout swipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task_details);
        ButterKnife.bind(this);


        selected = PrefUtils.getKeys(this,getResources().getString(R.string.search_selected));
        switch (selected) {
            case "1":
                mytext.setText("المهام الواردة حديثا");
                editBtn.setVisibility(View.GONE);

                break;
            case "2":
                mytext.setText("المهام الحالية");
                editBtn.setOnClickListener(this);
                break;
            case "3":
                mytext.setText("المهام المتأخرة");
                editBtn.setOnClickListener(this);
                break;
            case "4":
                mytext.setText("المهام المنتهية");
                editBtn.setOnClickListener(this);
                break;
        }

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        taskModel = (Tasks) bundle.getSerializable("myObject");

        if(taskModel!=null){
            taskNo.setText(String.valueOf(taskModel.TASK_SYSKEY));
            taskName.setText(taskModel.TASK_NAME);
            textPriority.setText(taskModel.TASK_PRIORITY);
            taskState.setText(taskModel.TASK_STATUS);
            task_req.setText(taskModel.TASK_REQUEST);
            task_category.setText(taskModel.TASK_CATEGORY);
            task_startDate.setText(taskModel.TASK_START_DATE_HIJRI_AR);
            task_endDate.setText(taskModel.TASK_END_DATE_HIJRI_AR);
            task_personResp.setText(taskModel.USER_RESPONSIABLE);
        }

    }

    @Override
    public void onClick(View view) {
        int id=view.getId();

        if(id==R.id.editBtn){
            NewTaskInterface newTaskInterface = new NewTaskInterface() {
                @Override
                public void RetrieveTask(NEW_TASKS Task) {
                    task = Task;
                    Intent i = new Intent(App.getContext(), TaskActivity.class);
                    Bundle b = new Bundle();
                    b.putSerializable("Task", task);
                    b.putSerializable("Category", userCategory);
                    i.putExtras(b);
                    startActivity(i);
                    finish();
                }

                @Override
                public void EditTask(String value) {

                }

                @Override
                public void NotTaskFound(String error) {
                    progressDialog.dismiss();
                }

                @Override
                public void RetrieveCategory(Category category) {
                    progressDialog.dismiss();
                    if (category!=null) {
                        userCategory = category;
                        Show(taskModel);
                    }else {
                        Show();
                    }

                }

                @Override
                public void NotCategory(String error) {
                    progressDialog.dismiss();
                }
            };
            newTaskPresenter = new NewTaskPresenter(newTaskInterface, new NewTaskRepositoryImp());
            Show();
        }



        /*if(id==R.id.accecptBtn){
            NewTaskInterface newTaskInterface = new NewTaskInterface() {
                @Override
                public void RetrieveTask(NEW_TASKS Task) {

                }

                @Override
                public void EditTask(String value) {
                    progressDialog.dismiss();
                    Intent i = new Intent(NewTaskDetails.this, NewTaskHeads.class);
                   startActivity(i);
                }

                @Override
                public void NotTaskFound(String error) {
                    progressDialog.dismiss();
                    Snackbar.make(swipeRefreshLayout, error, Snackbar.LENGTH_LONG).show();
                }

                @Override
                public void RetrieveCategory(Category Task) {

                }

                @Override
                public void NotCategory(String error) {

                }
            };

            newTaskPresenter = new NewTaskPresenter(newTaskInterface,new NewTaskRepositoryImp());
            Edit(taskModel , "Accept");
        }
        else if(id==R.id.refuseBtn){
            LayoutInflater li = LayoutInflater.from(this);
            final View promptsView = li.inflate(R.layout.prompts, null);
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setView(promptsView);

            // Set an EditText view to get user input
            final EditText input =  promptsView.findViewById(R.id.causeEt);
            final Button send =  promptsView.findViewById(R.id.sendBtn);

            final AlertDialog mAlertDialog = alert.create();
            mAlertDialog.setOnShowListener(new DialogInterface.OnShowListener() {

                @Override
                public void onShow(DialogInterface dialog) {

                    send.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View view) {
                            // TODO Do something
                            if (input.getText().toString().equals("")) {
                                Toast.makeText(NewTaskDetails.this, "من فضلك ادخل سبب الرفض..." ,Toast.LENGTH_LONG).show();
                            } else {
                                NewTaskInterface newTaskInterface = new NewTaskInterface() {
                                    @Override
                                    public void RetrieveTask(NEW_TASKS Task) {
                                    }

                                    @Override
                                    public void EditTask(String value) {
                                        progressDialog.dismiss();
                                        Intent i = new Intent(NewTaskDetails.this, NewTaskHeads.class);
                                       startActivity(i);
                                    }

                                    @Override
                                    public void NotTaskFound(String error) {
                                        progressDialog.dismiss();

                                    }

                                    @Override
                                    public void RetrieveCategory(Category category) {
                                    }

                                    @Override
                                    public void NotCategory(String error) {

                                    }
                                };


                                newTaskPresenter = new NewTaskPresenter(newTaskInterface, new NewTaskRepositoryImp());
                                Edit(taskModel, "Reject");
                                mAlertDialog.dismiss();
                            }
                        }
                    });


                }
            });
            mAlertDialog.show();
        }*/

    }

    private void Show() {
        progressDialog= new ProgressDialog(this,R.style.AppTheme_Dark_Dialog);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("يتم تحميل بيانات المهمة...");
        progressDialog.show();
        newTaskPresenter.getCategory();
    }

    private void Show(Tasks tasks) {
        String syskey = String.valueOf(tasks.TASK_SYSKEY);
        newTaskPresenter.Task(syskey);
    }

    public void Edit(Tasks tasks,String type){
        progressDialog= new ProgressDialog(this,R.style.AppTheme_Dark_Dialog);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("يتم تحميل بيانات المهمة...");
        progressDialog.show();
        String syskey = String.valueOf(tasks.TASK_SYSKEY);
        newTaskPresenter.getTask(syskey , type);
        newTaskPresenter.getCategory();
    }
}
