package com.example.harbour.facemeetroom.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SearchActivity extends AppCompatActivity {

    private EditText roomid;
    private TextView roomTime;
    private CustomDatePicker mDatePicker;
    private Button search;
    private Intent intent;
    private Toast toast =null;
    private RoomUtils roomUtils;
    private SearchPost searchPost = new SearchPost();
    private String roomId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        roomUtils = new RoomUtils(this);
        initView();// 初始化控件
        setListener();
    }

    private void initView() {
        roomid = (EditText) findViewById(R.id.room_id);
        roomTime = (TextView) findViewById(R.id.room_time);
        search = (Button) findViewById(R.id.search);
    }

    public void back(View view){
        roomUtils.deleteRoom();
        roomUtils.close();
        finish();
    }

    private void setListener() {
        search.setOnClickListener(mListerner);
        findViewById(R.id.ll_date).setOnClickListener(mListerner);
        initDatePicker();
    }
    View.OnClickListener mListerner = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ll_date:
                    // 日期格式为yyyy-MM-dd
                    mDatePicker.show(roomTime.getText().toString());
                    break;
                case R.id.search:
                    //搜索按钮
                    insert();
                    break;
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDatePicker.onDestroy();
    }

    private void insert(){
        if(isture()){
            searchPost.setRoomId(roomid.getText().toString().trim());
            searchPost.setRoomTime(roomTime.getText().toString().trim());
            RoomDataApiManger.getSearchRoomDatas(searchPost)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
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

                                intent = new Intent(SearchActivity.this,SearchResultActivity.class);
                                intent.putExtra("roomId",roomId);
                                startActivity(intent);
                            }
                            else {
                                showToast("未查询到，请重新查询");
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                            intent = new Intent(SearchActivity.this, ErrorActivity.class);
                            startActivity(intent);
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }
    }

    private boolean isture(){
        if (roomid.getText().toString().trim().equals("")){
            showToast("请输入房间号码！");
            return false;
        }
        return true;
    }

    private void initDatePicker() {
        long beginTimestamp = DateFormatUtils.str2Long("2019-01-01", false);
        long endTimestamp = DateFormatUtils.str2Long("2099-12-31", false);
        long newTimestamp = System.currentTimeMillis();


        roomTime.setText(DateFormatUtils.long2Str(newTimestamp, false));

        // 通过时间戳初始化日期，毫秒级别
        mDatePicker = new CustomDatePicker(this, new CustomDatePicker.Callback() {
            @Override
            public void onTimeSelected(long timestamp) {
                roomTime.setText(DateFormatUtils.long2Str(timestamp, false));
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
            toast = Toast.makeText(SearchActivity.this, s, Toast.LENGTH_SHORT);
            toast.show();
        } else {
            toast.setText(s);
            toast.show();
        }
    }
}
