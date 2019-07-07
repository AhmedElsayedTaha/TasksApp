package com.example.apit.task.repositories.ForgetPassword;

import com.example.apit.task.model.User;

public interface ForgetPassInterface {

    public interface forgetPassPresenterInterface{
        void requestUserDataFromServer(String nickName);
    }

    public interface forgetPassModelInterface{
        interface OnFinishedListnerInterface{
            void onFinishedListner(User user);
            void onFailListner(Throwable t);
        }
        void forgetPasswordData(OnFinishedListnerInterface onFinishedListnerInterface,String nickName);
    }

    public interface forgetPassViewInterface{
        void getUserDataSuccessfully(User user);
        void failedToGetUserData(Throwable t);
    }
}
