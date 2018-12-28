package com.ihoment.base.weak;

import java.lang.ref.WeakReference;
import java.util.TimerTask;

/**
 * CountDownTimer的弱饮用
 *
 * @e-mail sky.wang@oceanwing.com
 * @date 2016-03-23
 */
public class WeakTimerTask extends TimerTask {
    private WeakReference<ITimerTask> mReference;

    public WeakTimerTask(ITimerTask ref) {
        mReference = new WeakReference(ref);
    }

    @Override
    public void run() {
        final ITimerTask reference = mReference.get();
        if (reference != null) {
            reference.onTaskRun();
        }
    }

    public interface ITimerTask {
        void onTaskRun();
    }
}