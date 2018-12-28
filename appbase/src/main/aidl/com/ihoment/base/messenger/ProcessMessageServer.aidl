package com.ihoment.base.messenger;

import com.ihoment.base.messenger.ProcessMessageClient;
interface ProcessMessageServer {
 	void postMessage(int type,String msg);

 	void setClient(ProcessMessageClient clent);

}