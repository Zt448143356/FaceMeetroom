package com.example.harbour.facemeetroom.util;

import android.content.Context;
import android.util.DisplayMetrics;

import static com.example.harbour.facemeetroom.scan.ScanApplication.sAppContext;

public class ScreenUtils {
    private ScreenUtils() {
        throw new AssertionError();
    }

    /**
     * 获取屏幕宽度
     *
     * @return
     */
    public static int getScreenWidth() {
        Context context = sAppContext;
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.widthPixels;
    }

    /**
     * 获取屏幕高度
     *
     * @return
     */
    public static int getScreenHeight() {
        Context context = sAppContext;
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.heightPixels;
    }
}
