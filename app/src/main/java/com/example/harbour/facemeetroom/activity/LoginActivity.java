package com.example.harbour.facemeetroom.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.example.harbour.facemeetroom.R;
import com.example.harbour.facemeetroom.api.UserDataApiManger;
import com.example.harbour.facemeetroom.db.dbutils.UserUtils;
import com.example.harbour.facemeetroom.db.entity.EmailPassword;
import com.example.harbour.facemeetroom.db.entity.User;
import com.example.harbour.facemeetroom.model.bean.LoginData;
import com.example.harbour.facemeetroom.model.bean.LoginUserUp;
import com.example.harbour.facemeetroom.util.login.MD5Utils;
import com.nostra13.universalimageloader.core.ImageLoader;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LoginActivity extends AppCompatActivity {

    private ImageLoader imageLoader = ImageLoader.getInstance();
    private final String TAG = LoginActivity.class.getSimpleName();
    private Toast toast = null;
    private LoginUserUp loginUserUp = new LoginUserUp();
    private EditText username;
    private EditText password;
    private CheckBox rememberCheck;
    private CircleImageView head_image;
    private Button loginButton;
    private ProgressBar progressBar;
    private UserUtils userUtils = new UserUtils(this);
    private Intent intent;
    private EmailPassword emailPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();// 初始化控件
    }


    private void initView(){
        username = (EditText) findViewById(R.id.login_edit_account);
        password = (EditText) findViewById(R.id.login_edit_password);
        head_image = (CircleImageView) findViewById(R.id.head_portrait);
        rememberCheck = (CheckBox) findViewById(R.id.remember);
        loginButton = (Button) findViewById(R.id.login_btn_login);
        progressBar = (ProgressBar) findViewById(R.id.login_progressBar);
        progressBar.setVisibility(View.GONE);
        username.setOnFocusChangeListener(onFocusChangeListener);
        password.setOnFocusChangeListener(onFocusChangeListener);
        userUtils = new UserUtils(this);
        if(userUtils.listAllEmailPassword().size()>0){
            emailPassword = new EmailPassword();
            emailPassword =userUtils.listAllEmailPassword().get(0);
            username.setText(emailPassword.getEmail());
            //password.setText(emailPassword.getPassword());
            password.setText("1         ");
            String image =emailPassword.getPicture();
            imageLoader.displayImage(image,head_image);
        }
    }
    public void login(View view){
        if (ifUsernamePasswordValue()){
            progressBar.setVisibility(View.VISIBLE);
            //loginUserUp.setPassword(password.getText().toString().trim());
            loginUserUp.setEmail(username.getText().toString().trim());
            if (password.getText().toString().equals("1         ")){
                loginUserUp.setPassword(emailPassword.getPassword());
            }else {
                loginUserUp.setPassword(MD5Utils.encode(password.getText().toString().trim()));
            }
            UserDataApiManger.getUserDatas(loginUserUp)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<LoginData>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(LoginData loginData) {
                            if (loginData.getStatus()==200){
                                User user = new User();
                                user.setName(loginData.getPerson().getName());
                                user.setId(Long.valueOf(loginData.getPerson().getId()));
                                user.setPicture(loginData.getPerson().getUrl());
                                userUtils.inserUser(user);
                                if (rememberCheck.isChecked()){
                                    userUtils.deleteEmailPassword();
                                    EmailPassword emailPassword =new EmailPassword();
                                    emailPassword.setEmail(loginUserUp.getEmail());
                                    emailPassword.setPassword(loginUserUp.getPassword());
                                    emailPassword.setPicture(loginData.getPerson().getUrl());
                                    userUtils.inserEmailPassword(emailPassword);
                                }else {
                                    userUtils.deleteEmailPassword();
                                }
                                userUtils.close();
                                intent =new Intent(LoginActivity.this,UserinfoActivity.class);
                                startActivity(intent);
                                finish();
                            }else if (loginData.getStatus()==400){
                                //管理员登录
                                showToast(getString(R.string.admin_login));
                            }else if (loginData.getStatus()==404){
                                //账号密码错误
                                showToast(getString(R.string.login_failed));
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                            progressBar.setVisibility(View.GONE);
                            intent = new Intent(LoginActivity.this, ErrorActivity.class);
                            startActivity(intent);
                        }

                        @Override
                        public void onComplete() {
                            progressBar.setVisibility(View.GONE);
                        }
                    });
        }
    }


    public boolean ifUsernamePasswordValue(){
        if (username.getText().toString().trim().equals("")){
            showToast(getString(R.string.username_empty));
            return false;
        }
        else if (password.getText().toString().trim().equals("")){
            showToast(getString(R.string.password_empty));
            return false;
        }
        return true;
    }

    private void showToast(String s) {
        if (toast == null) {
            toast = Toast.makeText(this, s, Toast.LENGTH_SHORT);
            toast.show();
        } else {
            toast.setText(s);
            toast.show();
        }
    }

    private EditText.OnFocusChangeListener onFocusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            EditText editText = (EditText) v;
            String hint;
            if(hasFocus){
                hint = editText.getHint().toString();
                editText.setTag(hint);
                editText.setHint("");
            }else {
                hint = editText.getTag().toString();
                editText.setHint(hint);
            }
        }
    };


}

