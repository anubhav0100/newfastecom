package com.edevelopers.tdp_main.models;

/**
 * Created by Anubhav Singh on 3/18/2020
 */

public class Team {
    private String col1,col2,col3,col4,col5;
    boolean selected = false;
    // private int teamWins;
    public Team(String col1, String col2, String col3, String col4, String col5, boolean selected )
    {
        super();
        this.col1=col1;
        this. col2=col2;
        this.col3=col3;
        this.col4=col4;
        this.col5=col5;
        this.selected = selected;

    }

    public Team() {
    }

    public String getcol1()
    {
        return col1;
    }

    public void setcol1(String col1)
    {
        this.col1 =col1;
    }
    public String getcol2()
    {
        return col2;
    }
    public void setcol2(String col2)
    {
        this.col2 =col2;
    }
    public String getcol3()
    {
        return col3;
    }
    public void setcol3(String col3)
    {
        this.col3 =col3;
    }
    public String getcol4()
    {
        return col4;
    }
    public void setcol4(String col4)
    {
        this.col4 =col4;
    }
    public String getcol5()
    {
        return col5;
    }
    public void setcol5(String col5)
    {
        this.col5 =col5;
    }
    public boolean isSelected()
    {
        return selected;
    }
    public void setSelected(boolean selected)
    {
        this.selected = selected;
    }
    public void set(Object ref, String trim) {


    }
}

