package com.example.harbour.facemeetroom.scan.decode;

import android.os.Handler;
import android.os.Looper;

import com.example.harbour.facemeetroom.activity.ScanActivity;

import java.util.concurrent.CountDownLatch;

final class DecodeThread extends Thread {
    private final ScanActivity mActivity;
    private final CountDownLatch mHandlerInitLatch;
    private Handler mHandler;

    DecodeThread(ScanActivity activity) {
        this.mActivity = activity;
        mHandlerInitLatch = new CountDownLatch(1);
    }

    Handler getHandler() {
        try {
            mHandlerInitLatch.await();
        } catch (InterruptedException ie) {
            // continue?
        }
        return mHandler;
    }

    @Override
    public void run() {
        Looper.prepare();
        mHandler = new DecodeHandler(mActivity);
        mHandlerInitLatch.countDown();
        Looper.loop();
    }
}
