package com.ihoment.base.process;

import android.os.IBinder;
import android.os.RemoteException;

import com.ihoment.base.messenger.ProcessMessageClient;
import com.ihoment.base.messenger.ProcessMessageServer;

/**
 * Created by canxixie on 2017/9/4.
 * <p>
 * 用于进程间通信
 */

public abstract class IhomentMessenger {


    private boolean isServer;

    ProcessMessageClient mProcessMessageClient;

    ProcessMessageServer mProcessMessageServer;

    /**
     * server端使用
     */
    public IhomentMessenger() {
        isServer = true;
        mProcessMessageServer = new ProcessMessageServer.Stub() {

            @Override
            public void postMessage(int type, String msg) throws RemoteException {
                HandleMessage(type, msg);
            }

            @Override
            public void setClient(ProcessMessageClient clent) throws RemoteException {
                mProcessMessageClient = clent;
            }
        };

    }

    public IBinder getServerBinder() {

        return (IBinder) mProcessMessageServer;
    }

    /**
     * client 端使用
     *
     * @param service
     */
    public IhomentMessenger(IBinder service) {
        isServer = false;
        mProcessMessageServer = ProcessMessageServer.Stub.asInterface(service);
        mProcessMessageClient = new ProcessMessageClient.Stub() {

            @Override
            public void postMessage(int type, String msg) throws RemoteException {
                HandleMessage(type, msg);
            }
        };

        try {
            mProcessMessageServer.setClient(mProcessMessageClient);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送消息
     *
     * @param type
     * @param msg
     */
    public void post(int type, String msg) {
        try {
            if (isServer) {
                mProcessMessageClient.postMessage(type, msg);
            } else {
                mProcessMessageServer.postMessage(type, msg);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    /**
     * 消息处理
     *
     * @param type
     * @param msg
     */
    public abstract void HandleMessage(int type, String msg);
}
