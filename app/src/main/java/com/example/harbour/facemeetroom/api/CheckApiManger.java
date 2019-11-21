package com.example.harbour.facemeetroom.api;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class CheckApiManger {
    private static final String ENDPOINT = "https://www.skeye-unity.cn";

    private static final Retrofit sRetrofit = new Retrofit .Builder()
            .baseUrl(ENDPOINT)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // 使用RxJava作为回调适配器
            .build();

    private static final checkApiService apiManager = sRetrofit.create(checkApiService.class);

    public static Observable<String> checkUsername(String username){
        return apiManager.checkUser("/api/name_check/"+username);
    }

    public static Observable<String> checkEmail(String email){
        return apiManager.checkEmail("/api/email_check/"+email);
    }
}
