package com.example.apit.task.repositories.ForgetPassword;

import com.example.apit.task.model.User;

public class ForgetImpPresenter implements ForgetPassInterface.forgetPassModelInterface.OnFinishedListnerInterface
                                                ,ForgetPassInterface.forgetPassPresenterInterface{

    ForgetPassInterface.forgetPassModelInterface forgetPassModelInterface;
    ForgetPassInterface.forgetPassViewInterface forgetPassViewInterface;

    public ForgetImpPresenter(ForgetPassInterface.forgetPassModelInterface forgetPassModelInterface, ForgetPassInterface.forgetPassViewInterface forgetPassViewInterface) {
        this.forgetPassModelInterface = forgetPassModelInterface;
        this.forgetPassViewInterface = forgetPassViewInterface;
    }

    @Override
    public void requestUserDataFromServer(String nickName) {
        forgetPassModelInterface.forgetPasswordData(this,nickName);
    }

    @Override
    public void onFinishedListner(User user) {
        if(forgetPassViewInterface!=null){
            forgetPassViewInterface.getUserDataSuccessfully(user);
        }
    }

    @Override
    public void onFailListner(Throwable t) {
        if(forgetPassViewInterface!=null){
            forgetPassViewInterface.failedToGetUserData(t);
        }
    }
}
