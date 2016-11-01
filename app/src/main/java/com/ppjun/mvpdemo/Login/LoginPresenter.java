package com.ppjun.mvpdemo.Login;

import android.os.Handler;
import android.text.TextUtils;

/**
 * Package :com.ppjun.mvpdemo.Login
 * Description :主要负责业务逻辑例如登录的网络请求，其中会有view是实例，在登录过程显示progress等ui操作。
 * 这里的构造方法主要是给LoginContract.View赋值。以及view和Presenter的关联
 * Author :Rc3
 * Created at :2016/11/1 11:35.
 */

public class LoginPresenter implements LoginConstract.Presenter {
    private LoginConstract.View mView;
    private Handler mHandler = new Handler();

    public LoginPresenter(LoginConstract.View view) {
        this.mView = view;
        this.mView.setPresenter(this);

    }

    @Override
    public void login(final String username, final String password) {
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            mView.showErrorMsg();
            return;
        }


        mView.showProgress();

        new Thread(new Runnable() {
            @Override
            public void run() {

                  //模拟请求时间
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if ("123".equals(username) && "123".equals(password)) {
                            mView.hideProgress();
                            mView.onLoginSuccess();
                        } else {
                            mView.hideProgress();

                            mView.onLoginError();
                        }
                    }
                });
            }
        }).start();
    }

    @Override
    public void start() {

    }
}
