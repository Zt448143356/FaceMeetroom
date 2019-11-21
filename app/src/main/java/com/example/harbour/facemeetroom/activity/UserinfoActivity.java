package com.example.harbour.facemeetroom.activity;


import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.harbour.facemeetroom.R;
import com.example.harbour.facemeetroom.api.UserDataApiManger;
import com.example.harbour.facemeetroom.db.dbutils.UserUtils;
import com.example.harbour.facemeetroom.db.entity.RoomTimes;
import com.example.harbour.facemeetroom.fragment.CategoryFragment;
import com.example.harbour.facemeetroom.fragment.MineFragment;
import com.example.harbour.facemeetroom.fragment.MyAdapter;
import com.example.harbour.facemeetroom.fragment.infoFragment;
import com.example.harbour.facemeetroom.widget.MyImageView;

import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class UserinfoActivity extends AppCompatActivity implements View.OnClickListener {


    private ViewPager mViewPager;
    private MyImageView mIvHome;
    private TextView mTvHome;

    private MyImageView mIvCategory;
    private TextView mTvCategory;

    private MyImageView mIvMine;
    private TextView mTvMine;

    private ArrayList<Fragment> mFragments;

    private LinearLayout mLinearLayoutHome;
    private LinearLayout mLinearLayoutCategory;
    private LinearLayout mLinearLayoutMine;

    private UserUtils userUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        userUtils = new UserUtils(this);
        initView();// 初始化控件
        initData(); // 初始化数据(也就是fragments)
        initSelectImage();// 初始化渐变的图片
        aboutViewpager(); // 关于viewpager
        setListener(); // viewpager设置滑动监听
        getRoomTime();//得到roomTime，当日预定会议室数量
    }

    private void getRoomTime(){
        UserDataApiManger.getRoomTime(String.valueOf(userUtils.listAll().get(0).getId()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RoomTimes>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(RoomTimes roomTimes) {
                        userUtils.deleteRoomTime();
                        userUtils.inserRoomRime(roomTimes);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    private void initView() {
        mLinearLayoutHome = (LinearLayout) findViewById(R.id.ll_home);
        mLinearLayoutCategory = (LinearLayout) findViewById(R.id.ll_categroy);
        mLinearLayoutMine = (LinearLayout) findViewById(R.id.ll_mine);

        mViewPager = (ViewPager) findViewById(R.id.vp);

        mIvHome = (MyImageView) findViewById(R.id.iv1);
        mTvHome = (TextView) findViewById(R.id.rb1);

        mIvCategory = (MyImageView) findViewById(R.id.iv2);
        mTvCategory = (TextView) findViewById(R.id.rb2);

        mIvMine = (MyImageView) findViewById(R.id.iv3);
        mTvMine = (TextView) findViewById(R.id.rb3);

    }
    private void initData() {
        mFragments = new ArrayList<>();
        mFragments.add(new infoFragment());
        mFragments.add(new CategoryFragment());
        mFragments.add(new MineFragment());
    }
    private void initSelectImage() {
        mIvHome.setImages(R.drawable.info_normal, R.drawable.info_selected);
        mIvCategory.setImages(R.drawable.category_normal, R.drawable.category_selected);
        mIvMine.setImages(R.drawable.mine_normal, R.drawable.mine_selected);
    }
    private void aboutViewpager() {
        MyAdapter myAdapter = new MyAdapter(getSupportFragmentManager(), mFragments);// 初始化adapter
        mViewPager.setAdapter(myAdapter); // 设置adapter
    }
    private void setListener() {
        //下面的tab设置点击监听
        mLinearLayoutHome.setOnClickListener(this);
        mLinearLayoutCategory.setOnClickListener(this);
        mLinearLayoutMine.setOnClickListener(this);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPs) {
                setImageView(position);// 更改图片
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }
    //改变图片
    private void setImageView(int position) {
        switch (position) {
            case 0:
                mIvHome.transformPage(1);
                mIvCategory.transformPage(0);
                mIvMine.transformPage(0);
                break;
            case 1:
                mIvHome.transformPage(0);
                mIvCategory.transformPage(1);
                mIvMine.transformPage(0);
                break;
            case 2:
                mIvHome.transformPage(0);
                mIvCategory.transformPage(0);
                mIvMine.transformPage(1);
                break;
        }
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_home:
                mViewPager.setCurrentItem(0);
                break;
            case R.id.ll_categroy:
                mViewPager.setCurrentItem(1);
                break;
            case R.id.ll_mine:
                mViewPager.setCurrentItem(2);
                break;
        }
    }

}
