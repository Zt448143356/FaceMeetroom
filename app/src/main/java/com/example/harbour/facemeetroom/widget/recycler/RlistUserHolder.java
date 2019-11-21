package com.example.harbour.facemeetroom.widget.recycler;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import com.example.harbour.facemeetroom.R;

public class RlistUserHolder extends RecyclerView.ViewHolder{
    View itemView;
    private LinearLayout linearLayout;
    private CheckBox userView;


    public RlistUserHolder(View itemView){
        super(itemView);
        this.itemView = itemView;
        userView = (CheckBox) itemView.findViewById(R.id.item_user);
        linearLayout = (LinearLayout) itemView.findViewById(R.id.item_user_layout);
    }

    public void bind( String user){
       userView.setText(user);
    }

    public View getItemView() {
        return itemView;
    }

    public CheckBox getUserView() {
        return userView;
    }

    public void setItemView(View itemView) {
        this.itemView = itemView;
    }

    public void setUserView(CheckBox userView) {
        this.userView = userView;
    }
}
