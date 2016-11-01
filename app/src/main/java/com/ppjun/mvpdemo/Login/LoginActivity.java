package com.ppjun.mvpdemo.Login;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ppjun.mvpdemo.R;

/**
 * Package :com.ppjun.mvpdemo.Login
 * Description :因为view的主要有acivity,所以要实现LoginContract.View的接口中所有ui的方法,
 * 以及给LoginContract.Presenter的实例赋值，还有在生成LoginPresenter的实例 ，登录按钮调用他的登录方法。
 * Author :Rc3
 * Created at :2016/11/1 11:29.
 */

public class LoginActivity extends Activity implements LoginConstract.View {

    private LoginConstract.Presenter mPresenter;
    private LoginPresenter mLoginPresenter;
    private EditText mUsername;
    private EditText mPassword;
    private Button mLogin;
    private ProgressBar mProgress;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();
        initListeners();
    }

    private void initListeners() {
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.login(mUsername.getText().toString(), mPassword.getText().toString());

            }
        });
    }

    private void initViews() {

        mUsername = (EditText) findViewById(R.id.ed_username);
        mPassword = (EditText) findViewById(R.id.ed_password);
        mLogin = (Button) findViewById(R.id.btn_login);
        mProgress = (ProgressBar) findViewById(R.id.progressbar);
        mLoginPresenter = new LoginPresenter(this);//让view绑定presenter

    }

    @Override
    public void showErrorMsg() {
        Toast.makeText(LoginActivity.this, "用户名或者密码格式错误", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgress() {
        mProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgress.setVisibility(View.GONE);
    }

    @Override
    public void onLoginSuccess() {
        Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onLoginError() {
        Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void setPresenter(LoginConstract.Presenter presenter) {
     this.mPresenter = presenter;
    }
}
