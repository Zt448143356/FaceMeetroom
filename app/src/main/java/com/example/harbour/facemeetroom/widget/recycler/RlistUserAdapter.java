package com.example.harbour.facemeetroom.widget.recycler;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.harbour.facemeetroom.R;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RlistUserAdapter extends RecyclerView.Adapter<RlistUserHolder>{

    private HashMap<Integer, Boolean> map;
    private List<String> usernames = new ArrayList<>();

    public RlistUserAdapter(List<String> rlist){
        map = new HashMap<>();
        usernames =rlist;
        for (int i = 0;i<rlist.size(); i++) {
            //Checkbox初始状态置为false
            map.put(i, false);
        }
    }

    /**
     * 返回当前选择情况
     */
    public HashMap<Integer, Boolean> getMap() {
        return map;
    }

    /**
     *  全选
     */
    public void selectAll() {
        Set<Map.Entry<Integer, Boolean>> entries = map.entrySet();
        for (Map.Entry<Integer, Boolean> entry : entries) {
            entry.setValue(true);
        }
        //刷新适配器
        notifyDataSetChanged();
    }

    /**
     * 全不选
     */
    public void neverAll() {
        Set<Map.Entry<Integer, Boolean>> entries = map.entrySet();
        for (Map.Entry<Integer, Boolean> entry : entries) {
            entry.setValue(false);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RlistUserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_user_cardview, parent, false);
        RlistUserHolder viewHolder = new RlistUserHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RlistUserHolder holder, final int position){
        //holder.getUserView().setText(usernames.get(position));
        holder.bind(usernames.get(position));
        holder.getUserView().setChecked(map.get(position));

        holder.getUserView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                map.put(position,!map.get(position));
                //刷新适配器
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount(){
        return usernames.size();
    }


}
