package com.example.harbour.facemeetroom.widget.recycler;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.harbour.facemeetroom.R;
import com.example.harbour.facemeetroom.db.entity.Room;
import com.example.harbour.facemeetroom.model.bean.MyRoomReserveDown;

import java.util.ArrayList;
import java.util.List;

public class RlistMyReserveRoomAdapter extends RecyclerView.Adapter<RlistMyReserveRoomHolder>{

    private List<MyRoomReserveDown> mRooms = new ArrayList<>();

    public RlistMyReserveRoomAdapter(List<MyRoomReserveDown> rlist){
        mRooms = rlist;
    }


    //声明自定义的监听接口
    private  OnItemClickLitener mOnItemClickLitener;

    //提供set方法供Activity或Fragment调用
    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener){
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    @NonNull
    @Override
    public RlistMyReserveRoomHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_my_room_cardview, parent, false);
        RlistMyReserveRoomHolder viewHolder = new RlistMyReserveRoomHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RlistMyReserveRoomHolder holder, final int position){
        MyRoomReserveDown room = mRooms.get(position);
        holder.bind(room);
        //通过为条目设置点击事件触发回调
        if (mOnItemClickLitener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickLitener.onItemClick(view, position);
                }
            });
        }
    }

    @Override
    public int getItemCount(){
        return mRooms.size();
    }
}
