package com.example.harbour.facemeetroom.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.harbour.facemeetroom.R;
import com.example.harbour.facemeetroom.activity.ErrorActivity;
import com.example.harbour.facemeetroom.activity.LoginActivity;
import com.example.harbour.facemeetroom.api.UserDataApiManger;
import com.example.harbour.facemeetroom.widget.expandableListView.EListViewUtils;
import com.example.harbour.facemeetroom.widget.expandableListView.ExpandableListAdapter;
import com.example.harbour.facemeetroom.widget.expandableListView.bean.ContentInfo;
import com.example.harbour.facemeetroom.widget.expandableListView.bean.TitleInfo;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CategoryFragment extends Fragment implements ExpandableListAdapter.OnGroupExpanded{

    private ExpandableListView mElistview;
    private ExpandableListAdapter expandableListAdapter;
    private List<ContentInfo> mList = new ArrayList<>();
    private Toast toast = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category,container,false);
        mElistview = view.findViewById(R.id.mElistview);
        getcategory();
        return view;
    }

    private void getcategory(){
        UserDataApiManger.getUserpeople()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TitleInfo>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(TitleInfo titleInfo) {
                        if (titleInfo.getStatus()==200){
                            mList=titleInfo.getContentInfos();
                        }else {
                            showToast(getString(R.string.category_result_failed));
                        }
                        mList=titleInfo.getContentInfos();
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        Intent intent = new Intent(getActivity(), ErrorActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onComplete() {
                        initAdapter();
                    }
                });
    }


    /**
     * 初始化适配器
     */
    private void initAdapter() {
        expandableListAdapter = new ExpandableListAdapter(mList, getContext());
        mElistview.setAdapter(expandableListAdapter);
    }

    /**
     * ExpandableListView条目点击事件
     */
    private void initListenter() {
        //子对象点击监听事件
        mElistview.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Toast.makeText(getActivity(),"123",Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        //组对象点击监听事件
        mElistview.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

                return false;//请务必返回false，否则分组不会展开
            }
        });
        //组对象判断分组监听事件
        expandableListAdapter.setOnGroupExPanded(this);
    }

    /**
     * 监听是否关闭其他的组对象
     * @param groupPostion
     */
    @Override
    public void onGroupExpanded(int groupPostion) {
        EListViewUtils utils=new EListViewUtils();
        utils.expandOnlyOne(groupPostion,mElistview);
    }

    /**
     * Toast封装
     * @param s 为显示内容
     */
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
