package com.ihoment.module;


public class Record {
	public int id;
	public int account;
	public String sequence;
	public Contactor caller;
	public Contactor callee;
	public CallType type;
	public long time_call;
	public long time_start;
	public long time_end;
	public RecordStatus status;
	
	public enum CallType{
		video,voice,camera
	}
	public enum RecordStatus{
        calling,missed,busy,rejected,accepted,hangup,silent
	}
}
