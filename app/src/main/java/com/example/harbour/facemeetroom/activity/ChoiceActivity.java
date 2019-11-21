package com.example.harbour.facemeetroom.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.arcsoft.face.ErrorInfo;
import com.arcsoft.face.FaceEngine;
import com.example.harbour.facemeetroom.R;
import com.example.harbour.facemeetroom.api.RoomDataApiManger;
import com.example.harbour.facemeetroom.common.Constants;
import com.example.harbour.facemeetroom.db.dbutils.RoomUtils;
import com.example.harbour.facemeetroom.db.dbutils.UserUtils;
import com.example.harbour.facemeetroom.model.bean.RecommendRoomData;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ChoiceActivity extends AppCompatActivity {

    private Toast toast = null;
    private static final int ACTION_REQUEST_PERMISSIONS = 0x001;
    private static final String[] NEEDED_PERMISSIONS = new String[]{
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.CAMERA,
    };
    private Intent intent;
    private UserUtils userUtils = new UserUtils(this);
    private RoomUtils roomUtils = new RoomUtils(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getRecommendData();
        judge();
        setContentView(R.layout.activity_choice);
        activeEngine(null);
    }

    private void getRecommendData(){
        RoomDataApiManger.getRecommendDatas()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RecommendRoomData>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(RecommendRoomData recommendRoomData) {
                        if (recommendRoomData.getStatus()==200){
                            roomUtils.deleteRecomendRoom();
                            roomUtils.insertMulRecommendRoom(recommendRoomData.getData());
                        }else {
                            showToast("获取推荐信息失败!");
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


    private void judge(){
        if(userUtils.listAll().size()>0){
            intent =new Intent(ChoiceActivity.this,UserinfoActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public void createAccount(View view){
        intent =new Intent(ChoiceActivity.this,InfoRegisterActivity.class);
        startActivity(intent);
        finish();
    }

    public void signIn(View view){
        intent =new Intent(ChoiceActivity.this,LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void activeEngine(final View view) {
        if (!checkPermissions(NEEDED_PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, NEEDED_PERMISSIONS, ACTION_REQUEST_PERMISSIONS);
            return;
        }
        if (view != null) {
            view.setClickable(false);
        }
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                FaceEngine faceEngine = new FaceEngine();
                int activeCode = faceEngine.active(ChoiceActivity.this, Constants.APP_ID, Constants.SDK_KEY);
                emitter.onNext(activeCode);
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer activeCode) {
                        if (activeCode == ErrorInfo.MOK) {
                            showToast(getString(R.string.active_success));
                        } else if (activeCode == ErrorInfo.MERR_ASF_ALREADY_ACTIVATED) {
                        } else {
                            showToast(getString(R.string.active_failed, activeCode));
                            intent = new Intent(ChoiceActivity.this, ErrorActivity.class);
                            startActivity(intent);
                        }

                        if (view != null) {
                            view.setClickable(true);
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

    private boolean checkPermissions(String[] neededPermissions) {
        if (neededPermissions == null || neededPermissions.length == 0) {
            return true;
        }
        boolean allGranted = true;
        for (String neededPermission : neededPermissions) {
            allGranted &= ContextCompat.checkSelfPermission(this, neededPermission) == PackageManager.PERMISSION_GRANTED;
        }
        return allGranted;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == ACTION_REQUEST_PERMISSIONS) {
            boolean isAllGranted = true;
            for (int grantResult : grantResults) {
                isAllGranted &= (grantResult == PackageManager.PERMISSION_GRANTED);
            }
            if (isAllGranted) {
                activeEngine(null);
            } else {
                showToast(getString(R.string.permission_denied));
                intent = new Intent(ChoiceActivity.this, ErrorActivity.class);
                startActivity(intent);
            }
        }
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
