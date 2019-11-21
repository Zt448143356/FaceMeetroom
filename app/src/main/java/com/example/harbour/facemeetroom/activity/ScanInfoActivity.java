package com.example.harbour.facemeetroom.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.harbour.facemeetroom.R;
import com.example.harbour.facemeetroom.api.RoomDataApiManger;
import com.example.harbour.facemeetroom.datepicker.CustomDatePicker;
import com.example.harbour.facemeetroom.datepicker.DateFormatUtils;
import com.example.harbour.facemeetroom.db.dbutils.RoomUtils;
import com.example.harbour.facemeetroom.model.bean.RoomData;
import com.example.harbour.facemeetroom.model.bean.SearchPost;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ScanInfoActivity extends AppCompatActivity implements View.OnClickListener {

    private Intent intent;
    private TextView mdate;
    private CustomDatePicker mDatePicker;
    private String roomId;
    private Button search;
    private SearchPost searchPost = new SearchPost();
    private TextView room;
    private RoomUtils roomUtils;
    private Toast toast = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_info);
        roomUtils = new RoomUtils(getApplication());
        intent = getIntent();
        roomId = intent.getStringExtra("roomId");

        findViewById(R.id.ll_date).setOnClickListener(this);
        mdate = findViewById(R.id.tv_selected_date);
        initDatePicker();

        room = findViewById(R.id.rooid_scan);
        room.setText("房间号码："+roomId);

        search = findViewById(R.id.search_scan);
        search.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_date:
                // 日期格式为yyyy-MM-dd
                mDatePicker.show(mdate.getText().toString());
                break;
            case R.id.search_scan:
                searchPost.setRoomId(roomId);
                searchPost.setRoomTime(mdate.getText().toString().trim());
                RoomDataApiManger.getSearchRoomDatas(searchPost)
                        .subscribeOn(Schedulers.io())
                        .subscribe(new Observer<RoomData>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(RoomData roomData) {
                                if(roomData.getStatus()==200){
                                    roomId = roomData.getRoomid();
                                    roomUtils.deleteRoom();
                                    roomUtils.insertMultRoom(roomData.getData());
                                    roomUtils.close();
                                }
                                else {
                                    showToast("未查询到，请重新查询");
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                e.printStackTrace();
                                showToast(getString(R.string.visit_failed));
                                intent = new Intent(ScanInfoActivity.this,ErrorActivity.class);
                                startActivity(intent);
                            }

                            @Override
                            public void onComplete() {
                                intent = new Intent(ScanInfoActivity.this,SearchResultActivity.class);
                                intent.putExtra("roomId",roomId);
                                startActivity(intent);
                                finish();
                            }
                        });
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDatePicker.onDestroy();
    }

    private void initDatePicker() {
        long beginTimestamp = DateFormatUtils.str2Long("2019-01-01", false);
        long endTimestamp = DateFormatUtils.str2Long("2099-12-31", false);
        long newTimestamp = System.currentTimeMillis();


        mdate.setText(DateFormatUtils.long2Str(newTimestamp, false));

        // 通过时间戳初始化日期，毫秒级别
        mDatePicker = new CustomDatePicker(this, new CustomDatePicker.Callback() {
            @Override
            public void onTimeSelected(long timestamp) {
                mdate.setText(DateFormatUtils.long2Str(timestamp, false));
            }
        }, beginTimestamp, endTimestamp);
        // 不允许点击屏幕或物理返回键关闭
        mDatePicker.setCancelable(false);
        // 不显示时和分
        mDatePicker.setCanShowPreciseTime(false);
        // 允许循环滚动
        mDatePicker.setScrollLoop(true);
        // 允许滚动动画
        mDatePicker.setCanShowAnim(true);
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
