package com.example.harbour.facemeetroom.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.harbour.facemeetroom.R;
import com.example.harbour.facemeetroom.activity.ReserveActivity;
import com.example.harbour.facemeetroom.activity.ScanInfoActivity;
import com.example.harbour.facemeetroom.activity.SearchActivity;
import com.example.harbour.facemeetroom.activity.SearchResultActivity;
import com.example.harbour.facemeetroom.api.RoomDataApiManger;
import com.example.harbour.facemeetroom.db.dbutils.RoomUtils;
import com.example.harbour.facemeetroom.db.entity.RecommendRoom;
import com.example.harbour.facemeetroom.db.entity.Room;
import com.example.harbour.facemeetroom.model.bean.RecommendRoomData;
import com.example.harbour.facemeetroom.model.bean.RoomData;
import com.example.harbour.facemeetroom.model.bean.SearchPost;
import com.example.harbour.facemeetroom.widget.recycler.OnItemClickLitener;
import com.example.harbour.facemeetroom.widget.recycler.RlistMyReserveRoomAdapter;
import com.example.harbour.facemeetroom.widget.recycler.RlistRecommendRoomAdapter;
import com.example.harbour.facemeetroom.widget.recycler.RlistSearchRoomAdapter;

import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class infoFragment extends Fragment {

    private Toast toast =null;
    private EditText search;
    private Intent intent;
    private RoomUtils roomUtils = new RoomUtils(getActivity());
    private RecyclerView mRlistRecyclerView;
    private RlistRecommendRoomAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info,container,false);
        initView(view);// 初始化控件
        setListener();
        if(roomUtils.listAllRecommendRoom().size()>0){
            setDate(roomUtils.listAllRecommendRoom());
        }
        return view;
    }



    private void setDate(ArrayList<RecommendRoom> data){
        mAdapter = new RlistRecommendRoomAdapter(data);
        mRlistRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickLitener(new OnItemClickLitener() {

            @Override
            public void onItemClick(View view, int position) {
                intent = new Intent(getActivity(),ScanInfoActivity.class);
                intent.putExtra("roomId",roomUtils.listAllRecommendRoom().get(position).getRoomid());
                startActivity(intent);
            }
        });
    }

    private void initView(View view) {
        search = (EditText) view.findViewById(R.id.search_edit);
        mRlistRecyclerView = (RecyclerView) view.findViewById(R.id.recommend_recycler);
    }

    private void setListener() {
        search.setOnFocusChangeListener(onFocusChangeListener);
        search.setOnClickListener(onClickListener);
    }
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            intent = new Intent(getActivity(),SearchActivity.class);
            startActivity(intent);
        }
    };

    View.OnFocusChangeListener onFocusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus){
                intent = new Intent(getActivity(),SearchActivity.class);
                startActivity(intent);
            }
        }
    };



    private void showToast(String s) {
        if (toast == null) {
            toast = Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT);
            toast.show();
        } else {
            toast.setText(s);
            toast.show();
        }
    }
}
