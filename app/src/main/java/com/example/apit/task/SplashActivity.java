package com.example.apit.task;

import android.content.Intent;
import android.icu.text.UnicodeSetSpanner;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.apit.task.app.App;
import com.example.apit.task.model.User;
import com.example.apit.task.repositories.ForgetPassword.ForgetImpPresenter;
import com.example.apit.task.repositories.ForgetPassword.ForgetPassInterface;
import com.example.apit.task.repositories.ForgetPassword.ForgetPassModelImp;
import com.example.apit.task.utils.PrefUtils;
import com.example.apit.task.view.LoginActivity;
import com.example.apit.task.view.NewTaskActivity;
import com.example.apit.task.view.NewTaskDetails;
import com.example.apit.task.view.TaskActivity;

public class SplashActivity extends AppCompatActivity implements ForgetPassInterface.forgetPassViewInterface {

    ForgetPassInterface.forgetPassPresenterInterface presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.setContext(this);

        presenter = new ForgetImpPresenter(new ForgetPassModelImp(),this);
        String isLogin = PrefUtils.getKeys(App.getContext(), getString(R.string.login_key));
        if(isLogin != null){
            startActivity(new Intent(SplashActivity.this, NewTaskActivity.class));
            presenter.requestUserDataFromServer("Here is the nick name of the user");
            finish();
        }else {
            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            finish();
        }
    }

    @Override
    public void getUserDataSuccessfully(User user) {
        Toast.makeText(this,"user name is "+user.getUSERNAME(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void failedToGetUserData(Throwable t) {
        Log.v("Splash","error is "+t);
    }
}
