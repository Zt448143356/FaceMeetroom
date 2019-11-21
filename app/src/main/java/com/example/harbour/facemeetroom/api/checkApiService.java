package com.example.harbour.facemeetroom.api;


import java.util.ResourceBundle;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface checkApiService {

    @GET
    Observable<String> checkUser(@Url String username);

    @GET
    Observable<String> checkEmail(@Url String email);
}
