package com.ppjun.mvpdemo.Login;

import com.ppjun.mvpdemo.BasePresenter;
import com.ppjun.mvpdemo.BaseView;

/**
 * Package :com.ppjun.mvpdemo.Login
 * Description :
 * Author :Rc3
 * Created at :2016/11/1 11:35.
 */

public interface LoginConstract {

    //View接口负责显示和view相关的方法（例如显示吐司，显示progressbar 登陆成功或者失败的逻辑）
    interface View extends BaseView<Presenter> {
        void showErrorMsg();

        void showProgress();

        void hideProgress();

        void onLoginSuccess();

        void onLoginError();


    }

    //Presenter接口负责登录请求的方法
    interface Presenter extends BasePresenter {
        void login(String username, String password);
    }
}
