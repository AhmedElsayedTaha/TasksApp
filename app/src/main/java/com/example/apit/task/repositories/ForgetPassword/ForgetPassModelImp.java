package com.example.apit.task.repositories.ForgetPassword;

import com.example.apit.task.app.App;
import com.example.apit.task.model.User;
import com.example.apit.task.network.ApiClient;
import com.example.apit.task.network.ApiService;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class ForgetPassModelImp implements ForgetPassInterface.forgetPassModelInterface {
    private CompositeDisposable disposable = new CompositeDisposable();

    @Override
    public void forgetPasswordData(final OnFinishedListnerInterface onFinishedListnerInterface, String nickName) {
      ApiService apiService = ApiClient.getClient(App.getContext()).create(ApiService.class);
      disposable.add(apiService.getUserData(nickName)
              .subscribeOn(Schedulers.io())
              .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<User>() {

                    @Override
                    public void onSuccess(User user) {

                        onFinishedListnerInterface.onFinishedListner(user);
                    }

                    @Override
                    public void onError(Throwable e) {
                        onFinishedListnerInterface.onFailListner(e);
                    }
                }));
    }
}
