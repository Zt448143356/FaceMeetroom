package com.example.harbour.facemeetroom.widget.recycler;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.harbour.facemeetroom.R;
import com.example.harbour.facemeetroom.db.entity.RecommendRoom;
import com.example.harbour.facemeetroom.db.entity.Room;


public class RlistRecommendRoomHolder extends RecyclerView.ViewHolder{
    private TextView mRoomid;
    private TextView mContent;
    private Button recommend_button;
    private RelativeLayout itemLayout;


    public RlistRecommendRoomHolder(View itemView){
        super(itemView);
        itemLayout = (RelativeLayout) itemView.findViewById(R.id.my_recommoned_room_layout);
        mRoomid = (TextView) itemView.findViewById(R.id.recommend_room_id_tx);
        mContent =  (TextView) itemView.findViewById(R.id.recommend_content_tx);
        recommend_button = (Button) itemView.findViewById(R.id.recommend_button);
        recommend_button = (Button) itemView.findViewById(R.id.recommend_button);
    }

    public void bind(RecommendRoom recommendRoom){
        mRoomid.setText("roomid:"+recommendRoom.getRoomid());
        mContent.setText("content:"+recommendRoom.getContent());
    }
}
