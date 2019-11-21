package com.example.harbour.facemeetroom.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.harbour.facemeetroom.R;
import com.example.harbour.facemeetroom.db.dbutils.RoomUtils;
import com.example.harbour.facemeetroom.db.entity.Room;
import com.example.harbour.facemeetroom.widget.recycler.OnItemClickLitener;
import com.example.harbour.facemeetroom.widget.recycler.RlistSearchRoomAdapter;

import java.util.ArrayList;

public class SearchResultActivity extends AppCompatActivity {

    private RecyclerView mRlistRecyclerView;
    private RlistSearchRoomAdapter mAdapter;
    private RoomUtils roomUtils;
    private Room room;
    private Intent intent;
    private Toast toast=null;
    private String roomId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        intent = getIntent();
        roomId = intent.getStringExtra("roomId");
        initView();// 初始化控件
        upui();
    }

    public void back(View view){
        roomUtils.deleteRoom();
        roomUtils.close();
        finish();
    }

    private void initView() {
        mRlistRecyclerView = (RecyclerView) findViewById(R.id.rlist_recycler_view);
        mRlistRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        roomUtils = new RoomUtils(this);
    }

    private void upui(){
        setDate(roomUtils.listAll());
    }

    private void setDate(ArrayList<Room> data){
        mAdapter = new RlistSearchRoomAdapter(data);
        mRlistRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickLitener(new OnItemClickLitener() {

            @Override
            public void onItemClick(View view, int position) {
                room = roomUtils.listAll().get(position);
                roomUtils.deleteRoom();
                if (roomUtils.insertRoom(room)){
                    roomUtils.close();
                    intent = new Intent(SearchResultActivity.this,ReserveActivity.class);
                    intent.putExtra("roomId",roomId);
                    startActivity(intent);
                    finish();
                }
            }
        });
        mRlistRecyclerView.setAdapter(mAdapter);
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
