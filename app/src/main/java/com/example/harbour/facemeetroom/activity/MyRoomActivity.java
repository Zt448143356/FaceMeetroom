package com.example.harbour.facemeetroom.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.harbour.facemeetroom.R;
import com.example.harbour.facemeetroom.activity.edit.EditReserveActivity;
import com.example.harbour.facemeetroom.db.dbutils.RoomUtils;
import com.example.harbour.facemeetroom.db.dbutils.UserUtils;
import com.example.harbour.facemeetroom.db.entity.RoomReserveUp;
import com.example.harbour.facemeetroom.model.bean.MyRoomReserveDown;
import com.example.harbour.facemeetroom.widget.recycler.OnItemClickLitener;
import com.example.harbour.facemeetroom.widget.recycler.RlistMyReserveRoomAdapter;

import java.util.ArrayList;
import java.util.Arrays;

public class MyRoomActivity extends AppCompatActivity {

    private RecyclerView mRlistRecyclerView;
    private RlistMyReserveRoomAdapter mAdapter;
    private RoomUtils roomUtils;
    private UserUtils userUtils;
    private ArrayList<MyRoomReserveDown> myRoomReserveDown = new ArrayList<MyRoomReserveDown>();
    private ArrayList<RoomReserveUp> arrayList;
    private RoomReserveUp roomReserveUp;
    private Intent intent;
    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_room);
        initView();
        upui();
    }

    private void initView() {
        mRlistRecyclerView = (RecyclerView) findViewById(R.id.rlist_recycler_view);
        mRlistRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        backButton = (Button) findViewById(R.id.myroom_back_button);
        roomUtils = new RoomUtils(this);
        userUtils = new UserUtils(this);
    }

    public void back(View view){
        roomUtils.deleteMyRoom();
        roomUtils.close();
        finish();
    }

    private void upui(){
        arrayList = roomUtils.listAllmyRoom();
        myRoomReserveDown = RoomUpToRoomDown(arrayList);
        setDate(myRoomReserveDown);
    }

    private ArrayList<MyRoomReserveDown> RoomUpToRoomDown(ArrayList<RoomReserveUp> roomReserveUpArrayList){
        ArrayList<MyRoomReserveDown> myRoomReserveDownArrayList = new ArrayList<MyRoomReserveDown>();
        for (RoomReserveUp roomReserveUp :roomReserveUpArrayList){
            MyRoomReserveDown myRoomReserveDown = new MyRoomReserveDown();
            myRoomReserveDown.setTime(roomReserveUp.getTime());
            myRoomReserveDown.setRoomid(String.valueOf(roomReserveUp.getRoomid()));
            myRoomReserveDown.setContent(roomReserveUp.getContent());
            myRoomReserveDown.setDate(roomReserveUp.getDate());
            myRoomReserveDown.setUsername(roomReserveUp.getUsername());
            ArrayList<String> name = new ArrayList<String>(Arrays.asList(roomReserveUp.getNames().split(",")));
            myRoomReserveDown.setName(name);
            myRoomReserveDownArrayList.add(myRoomReserveDown);
        }
        return myRoomReserveDownArrayList;
    }

    private void setDate(ArrayList<MyRoomReserveDown> data){
        mAdapter = new RlistMyReserveRoomAdapter(data);
        mRlistRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickLitener(new OnItemClickLitener() {

            @Override
            public void onItemClick(View view, int position) {
                if (roomUtils.listAllmyRoom().get(position).getUsername().equals(userUtils.listAll().get(0).getName())){
                    roomReserveUp = roomUtils.listAllmyRoom().get(position);
                    roomUtils.deleteMyRoom();
                    if (roomUtils.insertRoomReserve(roomReserveUp)){
                        roomUtils.close();
                        intent = new Intent(MyRoomActivity.this,EditReserveActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }else {
                    Toast.makeText(MyRoomActivity.this,"这是"+roomUtils.listAllmyRoom().get(position).getUsername()+"发起的会议室预定，你无权进行修改！",Toast.LENGTH_LONG).show();
                }
            }
        });
        mRlistRecyclerView.setAdapter(mAdapter);
    }
}
