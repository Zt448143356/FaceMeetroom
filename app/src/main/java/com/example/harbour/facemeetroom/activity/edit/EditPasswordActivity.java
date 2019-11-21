package com.example.harbour.facemeetroom.activity.edit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.harbour.facemeetroom.R;
import com.example.harbour.facemeetroom.activity.ErrorActivity;
import com.example.harbour.facemeetroom.activity.LoginActivity;
import com.example.harbour.facemeetroom.api.UserDataApiManger;
import com.example.harbour.facemeetroom.db.dbutils.UserUtils;
import com.example.harbour.facemeetroom.model.bean.EditPassword;
import com.example.harbour.facemeetroom.util.login.MD5Utils;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class EditPasswordActivity extends AppCompatActivity {

    private Toast toast = null;
    private UserUtils userUtils;
    private EditPassword editPassword = new EditPassword();

    //控件
    private EditText or_password,new_password,new_again_password;
    private Button edit_Pas_button,ed_back_button;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_password);
        userUtils = new UserUtils(this);
        initView();
    }

    private void initView(){
        or_password = (EditText) findViewById(R.id.ed_original_password);
        new_password = (EditText) findViewById(R.id.new_psw);
        new_again_password = (EditText) findViewById(R.id.new_psw_again);
        edit_Pas_button = (Button) findViewById(R.id.ed_passsword_button);
        ed_back_button = (Button) findViewById(R.id.ed_back_button_ps);
        progressBar = (ProgressBar) findViewById(R.id.password_progressBar);
        progressBar.setVisibility(View.GONE);
        ed_back_button.setOnClickListener(onClickListener);
        edit_Pas_button.setOnClickListener(onClickListener);
    }

    Button.OnClickListener onClickListener = new Button.OnClickListener(){
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.ed_back_button_ps:
                    finish();
                    break;
                case R.id.ed_passsword_button:
                    if (passwordTure()){
                        progressBar.setVisibility(View.VISIBLE);
                        editPassword.setId(String.valueOf(userUtils.listAll().get(0).getId()));
                        editPassword.setOldPassword(MD5Utils.encode(or_password.getText().toString().trim()));
                        editPassword.setNewPassword(MD5Utils.encode(new_password.getText().toString().trim()));
                        UserDataApiManger.editPassword(editPassword)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Observer<String>() {
                                    @Override
                                    public void onSubscribe(Disposable d) {

                                    }

                                    @Override
                                    public void onNext(String s) {
                                        if (s.equals("1")){
                                            showToast(getString(R.string.modify_success));
                                        }else {
                                            showToast(getString(R.string.modify_fail));
                                        }
                                        progressBar.setVisibility(View.GONE);
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        e.printStackTrace();
                                        progressBar.setVisibility(View.VISIBLE);
                                        Intent intent = new Intent(EditPasswordActivity.this, ErrorActivity.class);
                                        startActivity(intent);
                                    }

                                    @Override
                                    public void onComplete() {
                                        finish();
                                    }
                                });
                    }
                    break;
            }
        }
    };

    private boolean passwordTure(){
        if (or_password.getText().toString().trim().equals("")){
            showToast(getString(R.string.please_input_old_password));
            return false;
        }else if (new_password.getText().toString().trim().equals("")){
            showToast(getString(R.string.please_input_new_password));
            return false;
        }else if (new_again_password.getText().toString().trim().equals("")){
            showToast(getString(R.string.please_input_new_password_again));
            return false;
        } else if (!new_password.getText().toString().trim().equals(new_again_password.getText().toString().trim())){
            showToast(getString(R.string.new_old_password_error));
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
}
