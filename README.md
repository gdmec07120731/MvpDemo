 MvpDemo
简单的mvp架构登录demo，根据功能分包，参考自[google mvp](https://github.com/googlesamples/android-architecture)




> 主要面向有mvp经验的读者，阅读时间大约 **5 min**



mvp 的思想是把显示和业务逻辑从activity，fragment这些View中分离。让model和view的通信通过Presenter来搞定，这样子就降低了一个activity成千代码量的可能，以后修改业务逻辑，新增方法只要model层实现好，然后在Presenter层暴露接口给view调用就好了，mvp相比mvc会提项目结构易读性，也有一些不足就是要多写类来协助开发，不太熟悉的同学就会拉慢开发速度。
```java
View<===>Presenter<===>Model
```
### 项目主要有以下类组成
```java
----login
      |----LoginActivity.java
      |----LoginContract.java
      |----LoginPresenter.java
----BaseView.java
----BasePresenter.java
```


### 新建类BaseView.java 
```java
public interface BaseView<T> {
    //在view层 给LoginConstract.Presenter赋值
    void setPresenter(T presenter);
}
```

### 新建类BasePresenter.java
```java
public interface BasePresenter {
//start方法表示进行数据初始化，这方法感觉没用
    void start();
}
```

这两个base类是所有模块都用到的基类。这里我们就不动它，然后具体某个模块(比如登陆模块用到的方法就在Login这个包里面新建LoginConstract。

然后新建两个接口分别是继承BaseView的view接口，注意这里的BaseView带泛型指向下面同级新建好的Presenter就行了。

以及继承BasePresenter的Presenter接口，把操作ui界面的方法和业务逻辑的方法写进对应的view和presenter接口，就有了下面的LoginConstract类)

### 新建类LoginConstract.java

```java
public interface LoginConstract{
  public interface View extends BaseView<Presenter>{
     void showProgressBar();//show进度条的方法
     void hideProgressBar
     void onLoginSuccess();//登录成功的方法
     void onLoginError();
     //...
  }
  public interface Presenter extends BasePresenter{

     void login(String username,String password);  //登录的方法
     //...
  }
}
```

### 新建类LoginPresenter.java

LoginPresenter会实现LoginContract.Presenter接口，在login方法写网络请求的逻辑代码。可以理解为持有view对象，并且操作业务逻辑(登陆，注册，获取列表数据)的类，有一个LoginConstract.View的全局变量mView。这个对象要赋值才能使用，所以我们会在LoginPresenter构造方法给mView赋值。并且在构造函数调用mView的setPresenter给View层的负责登陆业务的Presenter赋值。

```java
public class LoginPresenter implements LoginConstract.Presenter {
    private LoginConstract.View mView;


    public LoginPresenter(LoginConstract.View view) {
        this.mView = view;
        this.mView.setPresenter(this);

    }

    @Override
    public void login(final String username, final String password) {
           //判断username pwd这些参数合法性
           //期间调用mView.showProgressBar();等操作ui的方法
           //发送网络请求
        }
```

### 新建类LoginActivity.java

类要实现LoginConstract.View接口，期间会实现大量在LoginConstract.View的ui方法例如showProgressBar()啊。并且有2个全局变量分别是LoginConstract.Presenter类型的mPresenter 和LoginPresenter类型的mLoginPresenter变量。

1. 在setPresenter要给mPresenter 赋值。

2. 在onCreate要实例化就是要new一个mLoginPresenter，之后才能在点击事件调用login方法拉起登录，在点击事件调用mPresenter.login(...);。

3. 然后就是正常逻辑的initViews() 和initListeners()方法，给控件实例化，和注册点击事件等。

```java
public class LoginActivity extends Activity implements LoginConstract.View {

    private LoginConstract.Presenter mPresenter;
    private LoginPresenter mLoginPresenter;
    private EditText mUsername;
   //...

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
       //...
        mLoginPresenter = new LoginPresenter(this);

    }


    @Override
    public void setPresenter(LoginConstract.Presenter presenter) {
        this.mPresenter = presenter;
    }
   }
```

实现了LoginConstract.View接口就要重写setPresenter方法给LoginConstract.Presenter 类型的全局变量赋值。要调用mPresenter.login方法必须先实例化mLoginPresenter这个对象。



注意，mPresenter主要是为了让view绑定Presenter，一定要先实例化mLoginPresenter，因为在LoginPresenter的构造函数中，会传入mView,才会让LoginPresenter的mView有值，执行LoginPresenter里面的login方法不会报错。





>
> 注释

- LoginConstract.View  mView;                    **掌控view操作ui的所有方法的对象**。

- LoginConstract.Presenter mPresenter;   **掌控view中操作业务逻辑的所有方法的对象**。
- LoginPresenter mLoginPresenter;            **让view绑定Presenter**

