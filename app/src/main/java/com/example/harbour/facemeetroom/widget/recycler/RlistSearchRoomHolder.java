package com.example.harbour.facemeetroom.widget.recycler;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.harbour.facemeetroom.R;
import com.example.harbour.facemeetroom.db.entity.Room;


public class RlistSearchRoomHolder extends RecyclerView.ViewHolder{
    private TextView mTimeTextView;
    private TextView mContentTextView;
    private TextView mUserTextView;
    private LinearLayout itemLayout;

    private Room mroom;

    public RlistSearchRoomHolder(View itemView){
        super(itemView);
        itemLayout = (LinearLayout) itemView.findViewById(R.id.item_layout);
        mTimeTextView = (TextView) itemView.findViewById(R.id.item_time);
        mContentTextView =  (TextView) itemView.findViewById(R.id.item_content);
        mUserTextView = (TextView) itemView.findViewById(R.id.item_user);
    }

    public void bind( Room room){
        mroom  = room;
        mTimeTextView.setText("time:"+mroom.getTime());
        mContentTextView.setText("content:"+mroom.getContent());
        mUserTextView.setText("user:"+mroom.getUser());
    }
}
