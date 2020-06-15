package com.edevelopers.fastecom.models;

public class Productcat {

    private String PC_ID, PC_DEFAULT, PC_LEVEL, PC_LEVEL_NAME1, PC_LEVEL_NAME2, PC_LEVEL_NAME3, PC_LEVEL_NAME4, PRODUCT_CATEGORY_LEVEL, CREATED_DATE, PC_MODEL, PC_ORDER, PC_UID, PC_NAME, PC_TYPE;
    boolean selected = false;

    public Productcat(String PC_ID, String PC_NAME,
                      String PC_DEFAULT, String PC_LEVEL,
                      String PC_LEVEL_NAME1, String PC_LEVEL_NAME2,
                      String PC_LEVEL_NAME3, String PC_LEVEL_NAME4,
                      String PRODUCT_CATEGORY_LEVEL, String PC_MODEL,
                      String PC_ORDER, String PC_UID,
                      String CREATED_DATE, String PC_TYPE, boolean selected) {
        super();
        this.PC_ID = PC_ID;
        this.PC_NAME = PC_NAME;
        this.PC_DEFAULT = PC_DEFAULT;
        this.PC_LEVEL = PC_LEVEL;
        this.PC_LEVEL_NAME1 = PC_LEVEL_NAME1;
        this.PC_LEVEL_NAME2 = PC_LEVEL_NAME2;
        this.PC_LEVEL_NAME3 = PC_LEVEL_NAME3;
        this.PC_LEVEL_NAME4 = PC_LEVEL_NAME4;
        this.PRODUCT_CATEGORY_LEVEL = PRODUCT_CATEGORY_LEVEL;
        this.PC_MODEL = PC_MODEL;
        this.PC_ORDER = PC_ORDER;
        this.PC_UID = PC_UID;
        this.CREATED_DATE = CREATED_DATE;
        this.PC_TYPE = PC_TYPE;
        this.selected = selected;
    }

    public Productcat() {

    }

    public String getPC_ID() {
        return PC_ID;
    }
    public void setPC_ID(String col1) {
        this.PC_ID = PC_ID;
    }

    public String getPC_NAME() {
        return PC_NAME;
    }
    public void setPC_NAME(String PC_NAME) {
        this.PC_NAME = PC_NAME;
    }

    public String getPC_DEFAULT() { return PC_DEFAULT; }
    public void setPC_DEFAULT(String PC_DEFAULT) { this.PC_DEFAULT =PC_DEFAULT; }

    public String getPC_LEVEL()
    {
        return PC_LEVEL;
    }
    public void setPC_LEVEL(String PC_DEFAULT) { this.PC_LEVEL =PC_LEVEL; }

    public String getPC_LEVEL_NAME1()
    {
        return PC_LEVEL_NAME1;
    }
    public void setPC_LEVEL_NAME1(String PC_LEVEL_NAME1)
    {
        this.PC_LEVEL_NAME1 =PC_LEVEL_NAME1;
    }

    public String getPC_LEVEL_NAME2()
    {
        return PC_LEVEL_NAME2;
    }
    public void setPC_LEVEL_NAME2(String PC_LEVEL_NAME2)
    {
        this.PC_LEVEL_NAME2 =PC_LEVEL_NAME2;
    }

    public String getPC_LEVEL_NAME3()
    {
        return PC_LEVEL_NAME3;
    }
    public void setPC_LEVEL_NAME3(String PC_LEVEL_NAME3)
    {
        this.PC_LEVEL_NAME3 =PC_LEVEL_NAME3;
    }

    public String getPC_LEVEL_NAME4()
    {
        return PC_LEVEL_NAME4;
    }
    public void setPC_LEVEL_NAME4(String PC_LEVEL_NAME4)
    {
        this.PC_LEVEL_NAME4 =PC_LEVEL_NAME4;
    }

    public String getPRODUCT_CATEGORY_LEVEL()
    {
        return PRODUCT_CATEGORY_LEVEL;
    }
    public void setPRODUCT_CATEGORY_LEVEL(String PRODUCT_CATEGORY_LEVEL) { this.PRODUCT_CATEGORY_LEVEL =PRODUCT_CATEGORY_LEVEL; }

    public String getPC_MODEL()
    {
        return PC_MODEL;
    }
    public void setPC_MODEL(String PC_MODEL)
    {
        this.PC_MODEL =PC_MODEL;
    }

    public String getPC_ORDER()
    {
        return PC_ORDER;
    }
    public void setPC_ORDER(String PC_ORDER)
    {
        this.PC_ORDER =PC_ORDER;
    }

    public String getPC_UID()
    {
        return PC_UID;
    }
    public void setPC_UID(String PC_UID)
    {
        this.PC_UID =PC_UID;
    }

    public String getCREATED_DATE()
    {
        return CREATED_DATE;
    }
    public void setCREATED_DATE(String CREATED_DATE)
    {
        this.CREATED_DATE =CREATED_DATE;
    }

    public String getPC_TYPE()
    {
        return PC_TYPE;
    }
    public void setPC_TYPE(String PC_TYPE)
    {
        this.PC_TYPE =PC_TYPE;
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