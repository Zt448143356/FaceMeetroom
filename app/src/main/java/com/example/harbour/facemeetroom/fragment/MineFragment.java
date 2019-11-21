package com.example.harbour.facemeetroom.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.example.harbour.facemeetroom.R;
import com.example.harbour.facemeetroom.activity.ChoiceActivity;
import com.example.harbour.facemeetroom.activity.ErrorActivity;
import com.example.harbour.facemeetroom.activity.MyRoomActivity;
import com.example.harbour.facemeetroom.activity.ScanActivity;
import com.example.harbour.facemeetroom.activity.edit.EditInfoActivity;
import com.example.harbour.facemeetroom.activity.edit.EditPasswordActivity;
import com.example.harbour.facemeetroom.api.RoomDataApiManger;
import com.example.harbour.facemeetroom.db.dbutils.RoomUtils;
import com.example.harbour.facemeetroom.db.dbutils.UserUtils;
import com.example.harbour.facemeetroom.db.entity.RoomTimes;
import com.example.harbour.facemeetroom.db.entity.User;
import com.example.harbour.facemeetroom.model.bean.MyRoomData;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MineFragment extends Fragment {

    // 创建ImageLoader对象
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private TextView name,now_number,day_number;
    private Button logout,myreserve,edit_password,message,edit_info;
    private CircleImageView circleImageView;
    private Intent intent;
    private Toast toast =null;
    private UserUtils userUtils;
    private RoomDataApiManger roomDataApiManger = new RoomDataApiManger();
    private RoomUtils roomUtils;
    private String url;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userUtils = new UserUtils(getActivity());
        roomUtils = new RoomUtils(getActivity());
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine,container,false);
        initView(view);// 初始化控件
        getBitmap();
        upui(userUtils.listAll().get(0));//更新ui
        setListener();
        return view;
    }

    private void getBitmap(){
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.head)// 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.drawable.head)// 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.alert_button)// 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true)// 设置下载的图片是否缓存在内存中
                .cacheOnDisk(true)// 设置下载的图片是否缓存在SD卡中
                .build();// 创建DisplayImageOptions对象
        // 使用ImageLoader加载图片
        String a = userUtils.listAll().get(0).getPicture();
        imageLoader.displayImage(a,circleImageView,options);
    }

    public void upui(User user){
        url = String.valueOf(user.getId());
        name.setText(user.getName());
    }

    private void initView(View view) {
        name = (TextView) view.findViewById(R.id.mine_name);
        now_number = (TextView) view.findViewById(R.id.now_room_number);
        day_number = (TextView) view.findViewById(R.id.day_room_number);
        logout = (Button) view.findViewById(R.id.mine_logout_button);
        myreserve = (Button) view.findViewById(R.id.mine_reserver);
        edit_password = (Button) view.findViewById(R.id.mine_ed_password_button);
        message = (Button) view.findViewById(R.id.mine_message_button);
        edit_info = (Button) view.findViewById(R.id.info_button);
        circleImageView = (CircleImageView) view.findViewById(R.id.my_head_portrait);
        if (userUtils.listAllRoomTime().size()>0){
            RoomTimes roomTimes = userUtils.listAllRoomTime().get(0);
            now_number.setText(""+roomTimes.getNowNumber());
            day_number.setText(""+roomTimes.getDayNumber());
        }
    }

    private void setListener() {
        //下面的tab设置点击监听
        logout.setOnClickListener(mListerner);
        myreserve.setOnClickListener(mListerner);
        edit_password.setOnClickListener(mListerner);
        message.setOnClickListener(mListerner);
        edit_info.setOnClickListener(mListerner);
    }
    View.OnClickListener mListerner = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.mine_logout_button:                            //注销按钮
                    userUtils.deleteUser();
                    if (userUtils.listAll().size()>0){
                        showToast(getString(R.string.logout_failed));
                        intent = new Intent(getActivity(),ErrorActivity.class);
                        startActivity(intent);
                    }else {
                        showToast(getString(R.string.logout_success));
                        intent = new Intent(getActivity(),ChoiceActivity.class);
                        startActivity(intent);
                    }
                    break;
                case R.id.mine_reserver:
                    searchMyRoom();
                    break;
                case R.id.mine_ed_password_button:
                    intent = new Intent(getActivity(),EditPasswordActivity.class);
                    startActivity(intent);
                    break;
                case  R.id.mine_message_button:
                    intent = new Intent(getActivity(),ScanActivity.class);
                    startActivity(intent);
                    break;
                case R.id.info_button:
                    intent = new Intent(getActivity(),EditInfoActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    };

    private void searchMyRoom(){
        if (userUtils.listAllRoomTime().size()>0){
            RoomTimes roomTimes = userUtils.listAllRoomTime().get(0);
            now_number.setText(""+roomTimes.getNowNumber());
            day_number.setText(""+roomTimes.getDayNumber());
        }
        roomDataApiManger.getSearchMyRoomDatas1(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MyRoomData>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(MyRoomData myRoomData) {
                        roomUtils.deleteMyRoom();
                        if (myRoomData.getStatus()==200){
                            roomUtils.insertMultRoomReserve(myRoomData.getData());
                            intent = new Intent(getActivity(),MyRoomActivity.class);
                            startActivity(intent);
                        }
                        else {
                            showToast("暂无预定");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        showToast(getString(R.string.visit_failed));
                        intent = new Intent(getActivity(),ErrorActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

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
