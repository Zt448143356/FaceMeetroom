package com.example.harbour.facemeetroom.api;

import com.example.harbour.facemeetroom.db.entity.RoomTimes;
import com.example.harbour.facemeetroom.db.entity.User;
import com.example.harbour.facemeetroom.model.bean.EditPassword;
import com.example.harbour.facemeetroom.model.bean.FaceResultData;
import com.example.harbour.facemeetroom.model.bean.FaceUp;
import com.example.harbour.facemeetroom.model.bean.LoginData;
import com.example.harbour.facemeetroom.model.bean.LoginUserUp;
import com.example.harbour.facemeetroom.model.bean.MyRoomData;
import com.example.harbour.facemeetroom.model.bean.MyRoomReserveDown;
import com.example.harbour.facemeetroom.model.bean.RecommendRoomData;
import com.example.harbour.facemeetroom.model.bean.ReserveUsersdown;
import com.example.harbour.facemeetroom.model.bean.RoomData;
import com.example.harbour.facemeetroom.model.bean.SearchPost;
import com.example.harbour.facemeetroom.model.bean.RegisterUserUp;
import com.example.harbour.facemeetroom.widget.expandableListView.bean.TitleInfo;

import java.util.ArrayList;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface downloadApiService {

    @GET("/api/phone_recommend_room")
    Observable<RecommendRoomData> gerRecommendRoomData();

    @GET("/api/phone_get_all_users")
    Observable<ReserveUsersdown> getReserveUsersDatas();

    @GET
    Observable<RoomTimes> getRoomTime(@Url String url);

    @GET
    Observable<MyRoomData> getUserRoom1(@Url String url);

    @GET
    Observable<User> getUserInfo(@Url String url);

    @GET("/api/phone_category")
    Observable<TitleInfo> getUserpeople();

    @POST("/face/add")
    Observable<FaceResultData> upfacefeature(@Body FaceUp faceUp);

    @POST("/face/update")
    Observable<FaceResultData> updatefacefeature(@Body FaceUp faceUp);

    @POST("/api/update_password")
    Observable<String> getEditPassword(@Body EditPassword editPassword);

    @POST
    Observable<LoginData> getUserInfo(@Body RegisterUserUp registerUserUp , @Url String id);

    @POST("/api/sign_up")
    Observable<LoginData> register(@Body RegisterUserUp registerUserUp);

    @POST("/api/phone_sign_in")
    Observable<LoginData> getLoginUser(@Body LoginUserUp loginUserUp);

    @POST("/api/phone_show_room")
    Observable<RoomData> getSearchRoomDatas(@Body SearchPost searchPost);

    @POST("/api/phone_order_room")
    Observable<String> getAddReserve(@Body MyRoomReserveDown myRoomReserveDown);

    @POST("/api/phone_order_room")
    Observable<ResponseBody> getAddReserve1(@Body MyRoomReserveDown myRoomReserveDown);

    @POST("api/phone_cancel_order")
    Observable<String> cancelRoom(@Body MyRoomReserveDown myRoomReserveDown);

}
