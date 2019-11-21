package com.example.harbour.facemeetroom.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.harbour.facemeetroom.R;
import com.example.harbour.facemeetroom.api.RoomDataApiManger;
import com.example.harbour.facemeetroom.api.UserDataApiManger;
import com.example.harbour.facemeetroom.db.dbutils.RoomUtils;
import com.example.harbour.facemeetroom.db.dbutils.UserUtils;
import com.example.harbour.facemeetroom.db.entity.Room;
import com.example.harbour.facemeetroom.model.bean.MyRoomReserveDown;
import com.example.harbour.facemeetroom.model.bean.ReserveUsersdown;
import com.example.harbour.facemeetroom.db.entity.RoomReserveUp;
import com.example.harbour.facemeetroom.model.bean.person;
import com.example.harbour.facemeetroom.widget.recycler.RlistUserAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class ReserveActivity extends AppCompatActivity {

    private final static String TAG = "ReserveActivity";
    private RoomUtils roomUtils;
    private UserUtils userUtils;
    private UserDataApiManger userDataApiManger = new UserDataApiManger();
    private Toast toast=null;
    private TextView roomId;
    private TextView roomTime;
    private EditText content;
    private RecyclerView mRlistRecyclerView;
    private RlistUserAdapter mAdapter;
    private Intent intent;
    private String roomid;
    private ArrayList<person> psersons;
    private ArrayList<String> namelist = new ArrayList<>();
    private ArrayList<String> idlist = new ArrayList<>();
    private ArrayList<String> up = new ArrayList<String>();
    private HashMap<Integer, Boolean> map;
    private Room room;
    private MyRoomReserveDown myRoomReserveDown = new MyRoomReserveDown();
    private Button all;
    private Button notall;
    private Button reserve;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve);
        intent = getIntent();
        roomid = intent.getStringExtra("roomId");
        getUsers();
        initView();// 初始化控件

    }
    private void initView() {
        roomUtils = new RoomUtils(this);
        userUtils = new UserUtils(this);
        room = roomUtils.listAll().get(0);
        roomId = (TextView) findViewById(R.id.reserve_room_id);
        roomId.setText("房间号码:  "+roomid);
        roomTime = (TextView) findViewById(R.id.reserve_room_time);
        roomTime.setText("时间:  "+room.getDate()+" "+room.getTime());
        content = (EditText) findViewById(R.id.content);
        content.setText(room.getContent());
        mRlistRecyclerView = (RecyclerView) findViewById(R.id.rlist_recycler_view);
        mRlistRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        all = (Button) findViewById(R.id.button_all);
        notall = (Button) findViewById(R.id.button_notall);
        reserve = (Button) findViewById(R.id.button_reserve);
        all.setOnClickListener(onClickListener);
        notall.setOnClickListener(onClickListener);
        reserve.setOnClickListener(onClickListener);
    }

    public void back(View view){
        finish();
    }

    //得到所有用户名
    private void getUsers(){
        userDataApiManger.getReserveUsersData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ReserveUsersdown>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ReserveUsersdown reserveUsersdown) {
                        psersons = reserveUsersdown.getPsersons();
                        for (person person:psersons){
                            namelist.add(person.getName());
                            idlist.add(person.getId());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        intent = new Intent(ReserveActivity.this, ErrorActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onComplete() {
                        setDate(namelist);
                    }
                });
    }

    private void setDate(ArrayList<String> data){
        mAdapter = new RlistUserAdapter(data);
        mRlistRecyclerView.setAdapter(mAdapter);
    }

    View.OnClickListener onClickListener =new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.button_reserve:
                    map = mAdapter.getMap();
                    myRoomReserveDown.setUsername(String.valueOf(userUtils.listAll().get(0).getId()));
                    myRoomReserveDown.setDate(room.getDate());
                    myRoomReserveDown.setTime(room.getTime());
                    myRoomReserveDown.setRoomid(roomid);
                    Iterator<Map.Entry<Integer, Boolean>> iterator = map.entrySet().iterator();
                    while (iterator.hasNext()) {
                        Map.Entry<Integer, Boolean> entry = iterator.next();
                        if (entry.getValue()){
                            up.add(psersons.get(entry.getKey()).getId());
                        }
                    }
                    myRoomReserveDown.setName(up);
                    //Post
                    RoomDataApiManger.AddReserve(myRoomReserveDown)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<String>() {
                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onNext(String s) {
                                    if (s.equals("1")){
                                        showToast("预定成功");
                                    }else {
                                        showToast("预定失败");
                                    }
                                }

                                @Override
                                public void onError(Throwable e) {
                                    e.printStackTrace();
                                    Log.i(TAG,e.getMessage()+"error");
                                    intent = new Intent(ReserveActivity.this, ErrorActivity.class);
                                    startActivity(intent);
                                }

                                @Override
                                public void onComplete() {
                                    intent = new Intent(ReserveActivity.this,UserinfoActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            });
                    /*RoomDataApiManger.AddReserve1(myRoomReserveDown)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<ResponseBody>() {
                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onNext(ResponseBody responseBody) {
                                    String a = responseBody.toString();
                                }

                                @Override
                                public void onError(Throwable e) {
                                    e.printStackTrace();
                                    Log.i(TAG,e.getMessage()+"error");
                                    intent = new Intent(ReserveActivity.this, ErrorActivity.class);
                                    startActivity(intent);
                                }

                                @Override
                                public void onComplete() {
                                    intent = new Intent(ReserveActivity.this,UserinfoActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            });*/
                    break;
                case  R.id.button_all:
                    mAdapter.selectAll();
                    break;
                case R.id.button_notall:
                    mAdapter.neverAll();
                    break;
            }
        }
    };

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
