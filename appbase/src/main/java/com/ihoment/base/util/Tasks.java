package com.ihoment.base.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuwenlong on 09/05/2017.
 */

public class Tasks {
    private List<Runnable> tasks;
    public Tasks(){
        this.tasks=new ArrayList<Runnable>();
    }
    public void add(Runnable runnable)
    {
        synchronized (this.tasks)
        {
            this.tasks.add(runnable);
        }
    }
    public void clear(){
        this.tasks.clear();
    }
    public void process()
    {
        if (this.tasks.size() == 0) {}
        List<Runnable> runnables=new ArrayList();
        synchronized (this.tasks){
            runnables.addAll(this.tasks);
            this.tasks.clear();
        }
        for(Runnable runnable:runnables){
            runnable.run();
        }
    }
}
