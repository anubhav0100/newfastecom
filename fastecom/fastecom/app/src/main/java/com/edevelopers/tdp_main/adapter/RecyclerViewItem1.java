package com.edevelopers.tdp_main.adapter;

public class RecyclerViewItem1 {

    private int drawableId;
    private String name;
    private String module3;
    private String id;
    private String Anlink;
    private String Acttype;

    public RecyclerViewItem1(int drawableId, String name, String module3, String id , String anlink, String acttype) {
        this.drawableId = drawableId;
        this.name = name;
        this.module3 = module3;
        this.id = id;
        this.Anlink = anlink;
        this.Acttype = acttype;
    }

    public int getDrawableId() {
        return drawableId;
    }

    public String getName() {
        return name;
    }

    public String getModule3() {
        return module3;
    }

    public String getId(){ return id;}
    public String getAnlink(){ return Anlink;}
    public String getActtype(){ return Acttype;}


}
