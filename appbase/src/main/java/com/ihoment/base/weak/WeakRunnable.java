package com.ihoment.base.weak;

import java.lang.ref.WeakReference;

/**
 * Runnable
 *
 * @e-mail sky.wang@oceanwing.com
 * @date 2016-03-23
 */
public class WeakRunnable implements Runnable {
    private WeakReference<RunnableTask> mReference;

    public WeakRunnable(RunnableTask ref) {
        mReference = new WeakReference(ref);
    }

    @Override
    public void run() {
        final RunnableTask reference = mReference.get();
        if (reference == null) return;
        reference.onRun();
    }

    public interface RunnableTask {
        void onRun();
    }
}