package com.edevelopers.fastecom.models;

/**
 * Created by Anubhav Singh on 3/18/2020
 */

public class Doctors_Team {
    private String col1,col2,col3,col4,col5;
    public byte[] img1;
    // private int teamWins;
    public Doctors_Team(String col1, String col2, String col3, String col4, String col5, byte[] img1 ) {
        super();
        this.col1 = col1;
        this.col2 = col2;
        this.col3 = col3;
        this.col4 = col4;
        this.col5 = col5;
        this.img1 = img1;

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
    public byte[] getimg1()
    {
        return img1;
    }
    public void setimg1(byte[] img1 )
    {
        this.img1=img1;

    }

    public void set(Object ref, String trim) {


    }
}

