package com.example.harbour.facemeetroom.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.example.harbour.facemeetroom.R;
import com.example.harbour.facemeetroom.api.CheckApiManger;
import com.example.harbour.facemeetroom.api.UserDataApiManger;
import com.example.harbour.facemeetroom.db.dbutils.UserUtils;
import com.example.harbour.facemeetroom.db.entity.User;
import com.example.harbour.facemeetroom.model.bean.FaceResultData;
import com.example.harbour.facemeetroom.model.bean.FaceUp;
import com.example.harbour.facemeetroom.model.bean.LoginData;
import com.example.harbour.facemeetroom.model.bean.RegisterUserUp;
import com.example.harbour.facemeetroom.util.BitmapBase64Util;
import com.example.harbour.facemeetroom.util.login.MD5Utils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class InfoRegisterActivity extends AppCompatActivity {

    private final String TAG = InfoRegisterActivity.class.getSimpleName();
    private byte[] facefeature;
    private String picture;
    private RegisterUserUp user = new RegisterUserUp();
    private UserUtils userUtils;
    private Intent intent;
    private Toast toast=null;
    private FaceUp faceUp = new FaceUp();
    private List<String> list = new ArrayList<String>();
    private Spinner mspinner;
    private ArrayAdapter<String> adapter;
    //ture为重复,false为没有重复
    private boolean username = true;
    private boolean email = true;
    //false为已经进行了脸部注册，true为还未进行注册
    private boolean flag_face = true;
    /**
     * 请求选择本地图片文件的请求码
     */
    private static final int ACTION_CHOOSE_IMAGE = 0x201;
    /**
     * 被处理的图片
     */
    private Bitmap mBitmap = null;

    //用户名，密码，再次输入的密码的控件
    private EditText et_user_name,et_psw,et_psw_again,et_email,et_age;
    private TextView username_repetition,email_repetition,te_face_register;
    private RadioGroup Sex;
    private Button btn_register;
    private CircleImageView img_upload;
    private ImageView face_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_register);
        //设置此界面为竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        init();
    }

    private void init(){
        //从activity_register.xml 页面中获取对应的UI控件
        img_upload = (CircleImageView) findViewById(R.id.img_upload_img);
        btn_register = (Button) findViewById(R.id.btn_register);
        et_user_name= (EditText) findViewById(R.id.et_user_name);
        et_psw= (EditText) findViewById(R.id.et_psw);
        et_psw_again= (EditText) findViewById(R.id.et_psw_again);
        Sex= (RadioGroup) findViewById(R.id.SexRadio);
        et_email = (EditText) findViewById(R.id.et_email);
        et_age = (EditText) findViewById(R.id.et_age);
        mspinner = (Spinner) findViewById(R.id.spinner);
        email_repetition = (TextView) findViewById(R.id.email_repetition);
        username_repetition = (TextView) findViewById(R.id.username_repetition);
        face_register = (ImageView) findViewById(R.id.face_register);
        te_face_register = (TextView) findViewById(R.id.te_face_register);
        //头像点击监听设置1
        img_upload.setOnClickListener(onClickListener);
        face_register.setOnClickListener(ImageListener);
        //设置按钮监听
        btn_register.setOnClickListener(ButtonListener);
        list.add("分组一");
        list.add("分组二");
        list.add("分组三");
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, list);
        mspinner.setAdapter(adapter);
        //设置监听
        mspinner.setOnItemSelectedListener(onItemSelectedListener);
        et_email.setOnFocusChangeListener(checkEmail);
        et_user_name.setOnFocusChangeListener(checkUsernaem);
    }

    ImageView.OnClickListener ImageListener = new ImageView.OnClickListener(){
        @Override
        public void onClick(View v) {
            jumpToFaceRegisterActivity();
        }
    };

    View.OnClickListener ButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            jumpToUserinfoActivity();
        }
    };

    Spinner.OnItemSelectedListener onItemSelectedListener = new Spinner.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            user.setGroup(adapter.getItem(position));
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            showToast(getString(R.string.please_choose));
        }
    };

    private void jumpToFaceRegisterActivity(){
        intent = new Intent(getApplication(),FaceRegisterActivity.class);
        startActivityForResult(intent,1);
    }




    public void jumpToUserinfoActivity() {

        //判断输入框内容
        int sex;
        String sexSting = "";
        int sexChoseId = Sex.getCheckedRadioButtonId();
        switch (sexChoseId) {
            case R.id.mainRegisterRdBtnFemale:
                sex = 0;
                sexSting = getString(R.string.female);
                break;
            case R.id.mainRegisterRdBtnMale:
                sex = 1;
                sexSting = getString(R.string.male);
                break;
            case R.id.mainRegisterRdBtnM:
                sex = 2;
                sexSting = getString(R.string.secrecy);
                break;
            default:
                sex = -1;
                break;
        }
        if(isUserNameAndPwdValid(sex)){
            //User用户信息添加
            user.setName(et_user_name.getText().toString().trim());
            user.setPassword(MD5Utils.encode(et_psw.getText().toString().trim()));
            //test
            //user.setPassword(et_psw.getText().toString().trim());
            user.setSex(sexSting);
            user.setEmail(et_email.getText().toString().trim());
            user.setAge(et_age.getText().toString().trim());
            Bitmap bitmap =((BitmapDrawable) ((CircleImageView) img_upload).getDrawable()).getBitmap();
            user.setPicture_url(BitmapBase64Util.bitmapToBase64(bitmap));

            //操作数据库
            userUtils = new UserUtils(this);

            UserDataApiManger.register(user)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<LoginData>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(LoginData loginData) {
                            User user = new User();
                            user.setName(loginData.getPerson().getName());
                            faceUp.setId(loginData.getPerson().getId());
                            user.setId(Long.valueOf(loginData.getPerson().getId()));
                            user.setPicture(loginData.getPerson().getUrl());
                            userUtils.inserUser(user);
                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                            intent = new Intent(InfoRegisterActivity.this,ErrorActivity.class);
                            startActivity(intent);
                        }

                        @Override
                        public void onComplete() {
                            userUtils.close();
                            UserDataApiManger.getFaceResultData(faceUp)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new Observer<FaceResultData>() {
                                        @Override
                                        public void onSubscribe(Disposable d) {

                                        }

                                        @Override
                                        public void onNext(FaceResultData faceResultData) {
                                            if (faceResultData.getData().equals("1")){
                                                showToast(getString(R.string.register_success));
                                            }else {
                                                showToast(getString(R.string.register_failed));
                                            }
                                        }

                                        @Override
                                        public void onError(Throwable e) {
                                            e.printStackTrace();
                                            intent = new Intent(InfoRegisterActivity.this, ErrorActivity.class);
                                            startActivity(intent);
                                        }

                                        @Override
                                        public void onComplete() {
                                            intent =new Intent(InfoRegisterActivity.this,UserinfoActivity.class);
                                            startActivity(intent);
                                        }
                                    });
                        }
                    });

        }
    }

    private   boolean isUserNameAndPwdValid(int sex) {
        if (et_user_name.getText().toString().trim().equals("")) {
            showToast(getString(R.string.username_empty));
            return false;
        } else if (et_psw.getText().toString().trim().equals("")) {
            showToast(getString(R.string.password_empty));
            return false;
        }else if(et_psw_again.getText().toString().trim().equals("")) {
            showToast(getString(R.string.password_check_empty));
            return false;
        }else if (!et_psw.getText().toString().trim().equals(et_psw_again.getText().toString().trim())){
            showToast(getString(R.string.password_equal));
            return false;
        }else if(sex<0){
            showToast(getString(R.string.sex_empty));
            return false;
        }else if (et_email.getText().toString().trim().equals("")){
            showToast(getString(R.string.email_empty));
            return false;
        }else if(et_age.getText().toString().trim().equals("")){
            showToast(getString(R.string.age_empty));
            return false;
        }else if (et_user_name.getText().toString().trim().length()>=10){
            showToast(getString(R.string.username_beyond));
            return false;
        }else if (username){
            showToast(getString(R.string.username_duplicate));
            return false;
        }else if (email){
            showToast(getString(R.string.email_duplicate));
            return false;
        }else if (flag_face){
            showToast(getString(R.string.please_face_register));
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

    /**
     * 检测是否重复
     */
    private EditText.OnFocusChangeListener checkUsernaem = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            EditText editText = (EditText) v;
            String text =editText.getText().toString().trim();
            if(!hasFocus){
                CheckApiManger.checkUsername(text)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<String>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(String s) {
                                if(s.equals("0")){
                                    username_repetition.setVisibility(username_repetition.VISIBLE);
                                    username = true;
                                }else{
                                    username_repetition.setVisibility(username_repetition.INVISIBLE);
                                    username = false;
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                e.printStackTrace();
                            }

                            @Override
                            public void onComplete() {

                            }
                        });
            }
        }
    };

    private boolean isEmail(String email){
        //Pattern p = Pattern.compile("\\w+@(\\w+.)+[a-z]{2,3}"); //简单匹配
        Pattern p =  Pattern.compile("^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$");//复杂匹配
        Matcher m = p.matcher(email);
        return m.matches();
    }

    private EditText.OnFocusChangeListener checkEmail = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            final EditText editText = (EditText) v;
            if(!isEmail(editText.getText().toString().trim())){
                email_repetition.setText(getString(R.string.email_layout_error));
                email =false;
            }else {
                String text =editText.getText().toString().trim();
                if(!hasFocus){
                    CheckApiManger.checkEmail(text)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<String>() {
                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onNext(String s) {
                                    if(s.equals("0")){
                                        email_repetition.setText(R.string.email_repetition);
                                        email = true;
                                    }else {
                                        email_repetition.setText("");
                                        email = false;
                                    }
                                }

                                @Override
                                public void onError(Throwable e) {
                                    e.printStackTrace();
                                }

                                @Override
                                public void onComplete() {

                                }
                            });
                }
            }
        }
    };

    /**
     * 从本地选择文件
     *
     * @param view
     */
    private CircleImageView.OnClickListener onClickListener = new CircleImageView.OnClickListener() {

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
            startActivityForResult(intent, ACTION_CHOOSE_IMAGE);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ACTION_CHOOSE_IMAGE) {
            if (data == null || data.getData() == null) {
                showToast(getString(R.string.get_picture_failed));
                return;
            }

            mBitmap = getBitmapFromUri(data.getData(), this);

            if (mBitmap == null) {
                showToast(getString(R.string.get_picture_failed));
                return;
            }
            img_upload.setImageBitmap(mBitmap);
            picture= BitmapBase64Util.bitmapToBase64(mBitmap);
        }
        if (requestCode == 1){
            facefeature = data.getByteArrayExtra("faceData");
            faceUp.setData(facefeature);
            Bitmap pass = BitmapFactory.decodeResource(getResources(),R.drawable.pass);
            face_register.setImageBitmap(pass);
            flag_face =false;
        }
    }

    public Bitmap getBitmapFromUri(Uri uri, Context context) {
        if (uri == null || context == null) {
            return null;
        }
        try {
            return MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }



}
