package com.ihoment.base;

/**
 * Created by wuwenlong on 3/24/16.
 */
public enum RUNMODE {
    ci, qa, pr;

    public static RUNMODE current = qa;

    public static boolean is_production() {
        return current == pr;
    }
}
