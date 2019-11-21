package com.example.harbour.facemeetroom.widget.recycler;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.harbour.facemeetroom.R;
import com.example.harbour.facemeetroom.db.entity.Room;
import com.example.harbour.facemeetroom.model.bean.MyRoomReserveDown;


public class RlistMyReserveRoomHolder extends RecyclerView.ViewHolder{
    private TextView RoomId;
    private TextView UserName;
    private TextView Date;
    private TextView Time;
    private TextView Content;
    private TextView naem;
    private RelativeLayout itemLayout;

    private MyRoomReserveDown myRoomReserveDown;

    public RlistMyReserveRoomHolder(View itemView){
        super(itemView);
        itemLayout = (RelativeLayout) itemView.findViewById(R.id.my_reserve_room_layout);
        RoomId = (TextView) itemView.findViewById(R.id.my_reserve_roomid);
        UserName =  (TextView) itemView.findViewById(R.id.my_reserve_username);
        Date = (TextView) itemView.findViewById(R.id.my_reserve_date);
        Time = (TextView) itemView.findViewById(R.id.my_reserve_time);
        Content =  (TextView) itemView.findViewById(R.id.my_reserve_content);
        naem = (TextView) itemView.findViewById(R.id.my_reserve_name);
    }

    public void bind( MyRoomReserveDown myRoomReserve){
        myRoomReserveDown  = myRoomReserve;
        RoomId.setText("房间ID:"+String.valueOf(myRoomReserve.getRoomid()));
        UserName.setText("发起人:"+myRoomReserve.getUsername());
        Date.setText("日期:"+myRoomReserve.getDate());
        Time.setText("time:"+myRoomReserve.getTime());
        Content.setText("content:"+myRoomReserve.getContent());
        naem.setText("user:"+myRoomReserve.getName().toString());
    }
}
