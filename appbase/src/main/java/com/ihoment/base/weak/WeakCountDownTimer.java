package com.ihoment.base.weak;

import android.os.CountDownTimer;

import java.lang.ref.WeakReference;

/**
 * CountDownTimer的弱引用
 *
 * @e-mail sky.wang@oceanwing.com
 * @date 2016-03-23
 */
public class WeakCountDownTimer extends CountDownTimer {
    private WeakReference<ICountDown> mReference;

    /**
     * @param millisInFuture    表示总的计时时间。即，从调用start()开始到调用onFinish() 的时间
     * @param countDownInterval 表示轮询通知时间。即，每个countDownInterval调用一次onTick
     */
    public WeakCountDownTimer(ICountDown ref, long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        mReference = new WeakReference(ref);
    }

    @Override
    public void onTick(long millisUntilFinished) {
        final ICountDown reference = mReference.get();
        if (reference != null) {
            reference.onTick(millisUntilFinished);
        }
    }

    @Override
    public void onFinish() {
        final ICountDown reference = mReference.get();
        if (reference != null) {
            reference.onFinish();
        }
    }

    public interface ICountDown {
        void onTick(long millisUntilFinished);

        void onFinish();
    }
}
