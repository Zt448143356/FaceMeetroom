package com.example.harbour.facemeetroom.activity.edit;

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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.example.harbour.facemeetroom.R;
import com.example.harbour.facemeetroom.activity.ErrorActivity;
import com.example.harbour.facemeetroom.activity.FaceRegisterActivity;
import com.example.harbour.facemeetroom.activity.InfoRegisterActivity;
import com.example.harbour.facemeetroom.activity.LoginActivity;
import com.example.harbour.facemeetroom.activity.UserinfoActivity;
import com.example.harbour.facemeetroom.api.CheckApiManger;
import com.example.harbour.facemeetroom.api.UserDataApiManger;
import com.example.harbour.facemeetroom.db.dbutils.UserUtils;
import com.example.harbour.facemeetroom.db.entity.User;
import com.example.harbour.facemeetroom.model.bean.FaceResultData;
import com.example.harbour.facemeetroom.model.bean.FaceUp;
import com.example.harbour.facemeetroom.model.bean.LoginData;
import com.example.harbour.facemeetroom.model.bean.RegisterUserUp;
import com.example.harbour.facemeetroom.util.BitmapBase64Util;
import com.nostra13.universalimageloader.core.ImageLoader;

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
import okhttp3.ResponseBody;

public class EditInfoActivity extends AppCompatActivity {

    private final String TAG = InfoRegisterActivity.class.getSimpleName();
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private byte[] facefeature;
    private String picture;
    private User getuser = new User();
    private RegisterUserUp user = new RegisterUserUp();
    private UserUtils userUtils;
    private Intent intent;
    private Toast toast=null;
    private FaceUp faceUp = new FaceUp();
    private List<String> list = new ArrayList<String>();
    private Spinner mspinner;
    private ArrayAdapter<String> adapter;
    //ture为重复,false为没有重复
    private boolean username = false;
    private boolean email = false;
    //ture为已经进行了脸部数据更改，false未更改
    private boolean flag_face = false;
    /**
     * 请求选择本地图片文件的请求码
     */
    private static final int ACTION_CHOOSE_IMAGE = 0x201;
    /**
     * 被处理的图片
     */
    private Bitmap mBitmap = null;

    //用户名，密码，再次输入的密码的控件
    private EditText ed_user_name,ed_email,ed_age;
    private TextView username_repetition,email_repetition;
    private RadioGroup Sex;
    private RadioButton ed_Female,ed_Male,ed_private;
    private Button ed_button,ed_back_button;
    private CircleImageView img_upload;
    private ImageView ed_faceinfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_info);
        //设置此界面为竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //操作数据库
        userUtils = new UserUtils(this);
        init();
        getUserInfo();

    }

    private void getUserInfo(){
        getuser.setId(userUtils.listAll().get(0).getId());
        UserDataApiManger.getUserInfo(String.valueOf(getuser.getId()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<User>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(User user) {
                        getuser.setPicture(user.getPicture());
                        getuser.setName(user.getName());
                        getuser.setSex(user.getSex());
                        getuser.setAge(user.getAge());
                        getuser.setGroup(user.getGroup());
                        getuser.setEmail(user.getEmail());
                        upui(getuser);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        intent = new Intent(EditInfoActivity.this, ErrorActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void upui(User user){
        int group = 0;
        switch (user.getGroup()){
            case "分组一":
                group=0;
                break;
            case "分组二":
                group=1;
                break;
            case "分组三":
                group=2;
                break;
        }
        mspinner.setSelection(group,true);
        imageLoader.displayImage(user.getPicture(),img_upload);
        ed_user_name.setText(user.getName());
        ed_email.setText(user.getEmail());
        ed_age.setText(user.getAge());
        int sex = 3;
        switch (user.getSex()){
            case "男":
                ed_Female.setChecked(true);
                break;
            case "女":
                ed_Male.setChecked(true);
                break;
            case "保密":
                ed_private.setChecked(true);
                break;
        }

    }

    private void init(){
        //从activity_register.xml 页面中获取对应的UI控件
        mspinner = (Spinner) findViewById(R.id.ed_spinner);
        img_upload = (CircleImageView) findViewById(R.id.ed_img_upload_img);
        ed_faceinfo = (ImageView) findViewById(R.id.ed_faceinfo);
        ed_user_name= (EditText) findViewById(R.id.ed_user_name);
        Sex= (RadioGroup) findViewById(R.id.SexRadio);
        ed_Female = (RadioButton) findViewById(R.id.ed_Female);
        ed_Male = (RadioButton) findViewById(R.id.ed_Male);
        ed_private = (RadioButton) findViewById(R.id.ed_private);
        ed_email = (EditText) findViewById(R.id.ed_email);
        ed_age = (EditText) findViewById(R.id.ed_age);
        email_repetition = (TextView) findViewById(R.id.email_repetition);
        username_repetition = (TextView) findViewById(R.id.username_repetition);
        ed_button = (Button) findViewById(R.id.ed_button);
        ed_back_button = (Button) findViewById(R.id.ed_back_button);
        //头像点击监听设置
        img_upload.setOnClickListener(onClickListener);
        ed_faceinfo.setOnClickListener(ImageListener);
        //设置按钮监听
        ed_button.setOnClickListener(ButtonListener);
        ed_back_button.setOnClickListener(ButtonListener);

        list.add("分组一");
        list.add("分组二");
        list.add("分组三");
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, list);
        mspinner.setAdapter(adapter);
        //设置监听
        mspinner.setOnItemSelectedListener(onItemSelectedListener);
        ed_email.setOnFocusChangeListener(checkEmail);
        ed_user_name.setOnFocusChangeListener(checkUsernaem);
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
            switch (v.getId()){
                case R.id.ed_button:
                    jumpToUserinfoActivity();
                    break;
                case R.id.ed_back_button:
                    finish();
                    break;
            }

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
            case R.id.ed_Female:
                sex = 0;
                sexSting = "女";
                break;
            case R.id.ed_Male:
                sex = 1;
                sexSting = "男";
                break;
            case R.id.ed_private:
                sex = 2;
                sexSting = "保密";
                break;
            default:
                sex = -1;
                break;
        }
        if(isUserNameAndPwdValid(sex)){
            //User用户信息添加
            user.setName(ed_user_name.getText().toString().trim());
            //user.setPassword(MD5Utils.encode(et_psw.getText().toString().trim()));
            //test
            user.setSex(sexSting);
            user.setEmail(ed_email.getText().toString().trim());
            user.setAge(ed_age.getText().toString().trim());
            Bitmap bitmap =((BitmapDrawable) ((CircleImageView) img_upload).getDrawable()).getBitmap();
            user.setPicture_url(BitmapBase64Util.bitmapToBase64(bitmap));

            UserDataApiManger.editInfo(user,String.valueOf(getuser.getId()))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<LoginData>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(LoginData loginData) {
                            User user = new User();
                            faceUp.setId(loginData.getPerson().getId());
                            user.setName(loginData.getPerson().getName());
                            user.setId(Long.valueOf(loginData.getPerson().getId()));
                            user.setPicture(loginData.getPerson().getUrl());
                            userUtils.deleteUser();
                            userUtils.inserUser(user);
                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                            intent = new Intent(EditInfoActivity.this, ErrorActivity.class);
                            startActivity(intent);
                        }

                        @Override
                        public void onComplete() {
                            if (flag_face){
                                userUtils.close();
                                UserDataApiManger.getUpdataFaceResultData(faceUp)
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(new Observer<FaceResultData>() {
                                            @Override
                                            public void onSubscribe(Disposable d) {

                                            }

                                            @Override
                                            public void onNext(FaceResultData faceResultData) {
                                                if (faceResultData.getData().equals("1")){
                                                    showToast(getString(R.string.modify_success));
                                                }else {
                                                    showToast(getString(R.string.modify_fail));
                                                }
                                            }

                                            @Override
                                            public void onError(Throwable e) {
                                                e.printStackTrace();
                                                intent = new Intent(EditInfoActivity.this, ErrorActivity.class);
                                                startActivity(intent);
                                            }

                                            @Override
                                            public void onComplete() {
                                                intent =new Intent(EditInfoActivity.this,UserinfoActivity.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                        });
                            }else {
                                showToast(getString(R.string.modify_success));
                                finish();
                            }
                        }
                    });
        }
    }

    private   boolean isUserNameAndPwdValid(int sex) {
        if (ed_user_name.getText().toString().trim().equals("")) {
            showToast(getString(R.string.username_empty));
            return false;
        }else if(sex<0){
            showToast(getString(R.string.sex_empty));
            return false;
        }else if (ed_email.getText().toString().trim().equals("")){
            showToast(getString(R.string.email_empty));
            return false;
        }else if(ed_age.getText().toString().trim().equals("")){
            showToast(getString(R.string.age_empty));
            return false;
        }else if (ed_user_name.getText().toString().trim().length()>=10){
            showToast(getString(R.string.username_beyond));
            return false;
        }else if (username){
            showToast(getString(R.string.username_duplicate));
            return false;
        }else if (email){
            showToast(getString(R.string.email_duplicate));
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
            String text ="/api/name_check/"+editText.getText().toString().trim()+"/"+getuser.getId();
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
        Pattern p =  Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");//复杂匹配
        Matcher m = p.matcher(email);
        return m.matches();
    }

    private EditText.OnFocusChangeListener checkEmail = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            final EditText editText = (EditText) v;
            if(!isEmail(editText.getText().toString().trim())){
                email_repetition.setText("邮箱格式不符合！");
                email =false;
            }else {
                String text ="/api/email_check/"+editText.getText().toString().trim() + "/"+ getuser.getId();
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
                                        email_repetition.setText("邮箱重复！请修改");
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
            ed_faceinfo.setImageBitmap(pass);
            flag_face =true;
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
