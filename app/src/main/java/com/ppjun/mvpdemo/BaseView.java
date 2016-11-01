package com.ppjun.mvpdemo;

/**
 * Package :com.ppjun.mvpdemo
 * Description :
 * Author :Rc3
 * Created at :2016/11/1 11:28.
 */

public interface BaseView<T> {
    //在view层 给LoginConstract.Presenter赋值
    void setPresenter(T presenter);
}
