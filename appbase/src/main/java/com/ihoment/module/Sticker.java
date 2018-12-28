package com.ihoment.module;

/**
 * Created by wuwenlong on 08/06/2017.
 */

public class Sticker {
    public int id;
    public String name;
    public String icon;
    public String path;
    public StickerType type;
    //图片格局
    public int rows;
    public int columns;
    //轮换间隔
    public int mills;

    public enum StickerType{
        Headband,Eyebrow,EyelashUp,EyelashDown,Nose,Beard,Moustache
    }
    public Sticker(){

    }
    public Sticker(int id,String name,String icon,String path,StickerType type,int rows,int columns,int mills){
        this.id=id;
        this.name=name;
        this.icon=icon;
        this.path=path;
        this.type=type;
        this.rows=rows;
        this.columns=columns;
        this.mills=mills;
    }
}
