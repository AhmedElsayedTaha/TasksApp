package com.example.apit.task.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.LinearLayout;

import com.example.apit.task.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChangePasswordActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.usernameLin)
    LinearLayout userNameLin;
    @BindView(R.id.changePassLin)
    LinearLayout changePassLin;
    @BindView(R.id.btn_next)
    AppCompatButton nextBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        ButterKnife.bind(this);

        nextBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id=view.getId();

        if(id==R.id.btn_next){
            userNameLin.setVisibility(View.GONE);
            changePassLin.setVisibility(View.VISIBLE);

        }

    }
}
