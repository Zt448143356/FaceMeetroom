package com.example.harbour.facemeetroom.api;


import com.example.harbour.facemeetroom.model.bean.MyRoomData;
import com.example.harbour.facemeetroom.model.bean.MyRoomReserveDown;
import com.example.harbour.facemeetroom.model.bean.RecommendRoomData;
import com.example.harbour.facemeetroom.model.bean.RoomData;
import com.example.harbour.facemeetroom.db.entity.RoomReserveUp;
import com.example.harbour.facemeetroom.model.bean.SearchPost;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RoomDataApiManger {
    private static final String ENDPOINT = "https://www.skeye-unity.cn";

    private static final Retrofit sRetrofit = new Retrofit .Builder()
            .baseUrl(ENDPOINT)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // 使用RxJava作为回调适配器
            .build();

    private static final downloadApiService apiManager = sRetrofit.create(downloadApiService.class);

    public static Observable<RecommendRoomData> getRecommendDatas(){
        return apiManager.gerRecommendRoomData();
    }

    public static Observable<String> cancelRoom(MyRoomReserveDown myRoomReserveDown){
        return apiManager.cancelRoom(myRoomReserveDown);
    }

    public static Observable<MyRoomData> getSearchMyRoomDatas1(String url){
        return apiManager.getUserRoom1(ENDPOINT+ "/api/phone_get_booked_room/" + url);
    }

    public static Observable<RoomData> getSearchRoomDatas(SearchPost searchPost){
        return apiManager.getSearchRoomDatas(searchPost);
    }

    public static Observable<String> AddReserve(MyRoomReserveDown myRoomReserveDown){
        return apiManager.getAddReserve(myRoomReserveDown);
    }

    public static Observable<ResponseBody> AddReserve1(MyRoomReserveDown myRoomReserveDown){
        return apiManager.getAddReserve1(myRoomReserveDown);
    }
}
