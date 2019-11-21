package com.example.harbour.facemeetroom.widget.recycler;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.harbour.facemeetroom.R;
import com.example.harbour.facemeetroom.db.entity.RecommendRoom;
import com.example.harbour.facemeetroom.db.entity.Room;

import java.util.ArrayList;
import java.util.List;

public class RlistRecommendRoomAdapter extends RecyclerView.Adapter<RlistRecommendRoomHolder>{

    private List<RecommendRoom> mRooms = new ArrayList<>();

    public RlistRecommendRoomAdapter(List<RecommendRoom> rlist){
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
    public RlistRecommendRoomHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_recommend_room_cardview, parent, false);
        RlistRecommendRoomHolder viewHolder = new RlistRecommendRoomHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RlistRecommendRoomHolder holder, final int position){
        RecommendRoom room = mRooms.get(position);
        holder.bind(room);
        //通过为条目设置点击事件触发回调
        if (mOnItemClickLitener != null) {
            holder.itemView.findViewById(R.id.recommend_button).setOnClickListener(new View.OnClickListener() {
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
