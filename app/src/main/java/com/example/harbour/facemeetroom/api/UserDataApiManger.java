package com.example.harbour.facemeetroom.api;

import com.example.harbour.facemeetroom.db.entity.RoomTimes;
import com.example.harbour.facemeetroom.db.entity.User;
import com.example.harbour.facemeetroom.model.bean.EditPassword;
import com.example.harbour.facemeetroom.model.bean.FaceResultData;
import com.example.harbour.facemeetroom.model.bean.FaceUp;
import com.example.harbour.facemeetroom.model.bean.LoginData;
import com.example.harbour.facemeetroom.model.bean.LoginUserUp;
import com.example.harbour.facemeetroom.model.bean.RegisterUserUp;
import com.example.harbour.facemeetroom.model.bean.ReserveUsersdown;
import com.example.harbour.facemeetroom.model.bean.UserData;
import com.example.harbour.facemeetroom.widget.expandableListView.bean.TitleInfo;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserDataApiManger {
    private static final String ENDPOINT = "https://www.skeye-unity.cn";

    private static final Retrofit sRetrofit = new Retrofit .Builder()
            .baseUrl(ENDPOINT)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // 使用RxJava作为回调适配器
            .build();

    private static final downloadApiService apiManager = sRetrofit.create(downloadApiService.class);

    public static Observable<RoomTimes> getRoomTime(String id){
        return apiManager.getRoomTime("/api/phone_get_order_message/" + id);
    }

    public static Observable<LoginData> editInfo(RegisterUserUp registerUserUp,String id){
        return apiManager.getUserInfo(registerUserUp,"/api/phone_update_person/" + id);
    }

    public static Observable<String> editPassword(EditPassword editPassword){
        return apiManager.getEditPassword(editPassword);
    }

    public static Observable<User> getUserInfo(String id){
        return apiManager.getUserInfo("/api/get_person_info/" + id);
    }

    public static Observable<LoginData> getUserDatas(LoginUserUp loginUserUp) {
        return apiManager.getLoginUser(loginUserUp);
    }

    public static Observable<LoginData> register(RegisterUserUp user){
        return apiManager.register(user);
    }

    public static Observable<ReserveUsersdown> getReserveUsersData(){
        return apiManager.getReserveUsersDatas();
    }

    public static Observable<FaceResultData> getFaceResultData(FaceUp faceUp){
     return apiManager.upfacefeature(faceUp);
    }

    public static Observable<FaceResultData> getUpdataFaceResultData(FaceUp faceUp){
        return apiManager.updatefacefeature(faceUp);
    }

    public static Observable<TitleInfo> getUserpeople(){
        return apiManager.getUserpeople();
    }
}
