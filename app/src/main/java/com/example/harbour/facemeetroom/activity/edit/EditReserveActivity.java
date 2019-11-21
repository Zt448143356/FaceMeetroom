package com.example.harbour.facemeetroom.activity.edit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.harbour.facemeetroom.R;
import com.example.harbour.facemeetroom.activity.ErrorActivity;
import com.example.harbour.facemeetroom.activity.UserinfoActivity;
import com.example.harbour.facemeetroom.api.RoomDataApiManger;
import com.example.harbour.facemeetroom.api.UserDataApiManger;
import com.example.harbour.facemeetroom.db.dbutils.RoomUtils;
import com.example.harbour.facemeetroom.db.dbutils.UserUtils;
import com.example.harbour.facemeetroom.db.entity.RoomReserveUp;
import com.example.harbour.facemeetroom.model.bean.MyRoomReserveDown;
import com.example.harbour.facemeetroom.model.bean.ReserveUsersdown;
import com.example.harbour.facemeetroom.model.bean.person;
import com.example.harbour.facemeetroom.widget.recycler.RlistUserAdapter;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class EditReserveActivity extends AppCompatActivity {

    private ImageLoader imageLoader = ImageLoader.getInstance();
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
    private ArrayList<person> psersons;
    private ArrayList<String> namelist = new ArrayList<>();
    private ArrayList<String> idlist = new ArrayList<>();
    private ArrayList<String> up = new ArrayList<String>();
    private HashMap<Integer, Boolean> map;
    private RoomReserveUp roomReserveUp;
    private MyRoomReserveDown myRoomReserveDown = new MyRoomReserveDown();
    private Button all;
    private Button notall;
    private Button reserve;
    private Button cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_reserve);
        getUsers();
        initView();// 初始化控件


    }

    public void back(View view){
        finish();
    }

    private void initView() {
        roomUtils = new RoomUtils(this);
        userUtils = new UserUtils(this);
        roomReserveUp = roomUtils.listAllmyRoom().get(0);
        roomId = (TextView) findViewById(R.id.edit_reserve_room_id);
        roomId.setText("房间号码:  "+roomReserveUp.getRoomid());
        roomTime = (TextView) findViewById(R.id.edit_reserve_room_time);
        roomTime.setText("时间:  "+roomReserveUp.getDate()+" "+roomReserveUp.getTime());
        content = (EditText) findViewById(R.id.edit_content);
        content.setText(roomReserveUp.getContent());
        mRlistRecyclerView = (RecyclerView) findViewById(R.id.rlist_recycler_view);
        mRlistRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        all = (Button) findViewById(R.id.edit_button_all);
        notall = (Button) findViewById(R.id.edit_button_notall);
        reserve = (Button) findViewById(R.id.edit_button_reserve);
        cancel = (Button) findViewById(R.id.edit_button_cancel_reserve);
        all.setOnClickListener(onClickListener);
        notall.setOnClickListener(onClickListener);
        reserve.setOnClickListener(onClickListener);
        cancel.setOnClickListener(onClickListener);
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
                        for (com.example.harbour.facemeetroom.model.bean.person person:psersons){
                            namelist.add(person.getName());
                            idlist.add(person.getId());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        intent = new Intent(EditReserveActivity.this, ErrorActivity.class);
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
                case R.id.edit_button_reserve:
                    map = mAdapter.getMap();
                    myRoomReserveDown.setUsername(String.valueOf(userUtils.listAll().get(0).getId()));
                    myRoomReserveDown.setDate(roomReserveUp.getDate());
                    myRoomReserveDown.setTime(roomReserveUp.getTime());
                    myRoomReserveDown.setRoomid(String.valueOf(roomReserveUp.getRoomid()));
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
                                        showToast("修改成功");
                                    }else {
                                        showToast("修改失败");
                                    }
                                }

                                @Override
                                public void onError(Throwable e) {
                                    e.printStackTrace();
                                    intent = new Intent(EditReserveActivity.this, ErrorActivity.class);
                                    startActivity(intent);
                                }

                                @Override
                                public void onComplete() {
                                    intent = new Intent(EditReserveActivity.this,UserinfoActivity.class);
                                    startActivity(intent);
                                }
                            });
                    break;
                case  R.id.edit_button_all:
                    mAdapter.selectAll();
                    break;
                case R.id.edit_button_notall:
                    mAdapter.neverAll();
                    break;
                case R.id.edit_button_cancel_reserve:
                    myRoomReserveDown.setDate(roomReserveUp.getDate());
                    myRoomReserveDown.setTime(roomReserveUp.getTime());
                    myRoomReserveDown.setRoomid(String.valueOf(roomReserveUp.getRoomid()));
                    RoomDataApiManger.cancelRoom(myRoomReserveDown)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<String>() {
                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onNext(String s) {
                                    if (s.equals("1")){
                                        showToast(getString(R.string.cancel_room_success));
                                        finish();
                                    }else {
                                        showToast(getString(R.string.cancel_room_failed));
                                    }
                                }

                                @Override
                                public void onError(Throwable e) {
                                    e.printStackTrace();
                                    intent = new Intent(EditReserveActivity.this, ErrorActivity.class);
                                    startActivity(intent);
                                }

                                @Override
                                public void onComplete() {

                                }
                            });
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
