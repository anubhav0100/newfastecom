package com.edevelopers.fastecom.models;

public class Product {
    private String PC_ID, PC_NAME, DESCRIPTION, META_TAG, META_DESCRIPTION, META_KEYWORD, PC_TYPE, PC_PRIORITY, PRODUCT_CATEGORY_LEVEL, PC_MODEL, PC_SKU, PC_UPC, PC_EAN, PC_JAN, PC_ISBN, PC_MPN,PC_LOCATION, PC_QUANTITY, PC_STOCK_STATUS_ID, PC_IMG_ID, PC_MANUFACTURING_ID, PC_SHIPPING, PC_PRICE, PC_POINTS, PC_TAX_CLASS_ID, PC_DATA_AVAILABLE, PC_WEIGHT, PC_WEIGHT_CLASS_ID, PC_LENGTH, PC_WIDTH, PC_HEIGHT, PC_LENGTH_CLASS_ID, PC_SUBTRACT, PC_MINIMUM, PC_SORT_ORDER, PC_STATUS, CREATED_DATE;
    boolean selected = false;
    // private int teamWins;
    public Product (String PC_ID, String PC_NAME, String DESCRIPTION, String META_TAG, String META_DESCRIPTION, String META_KEYWORD, String PC_TYPE, String PC_PRIORITY, String PRODUCT_CATEGORY_LEVEL, String PC_MODEL, String PC_SKU, String PC_UPC, String PC_EAN, String PC_JAN, String PC_ISBN, String PC_MPN, String PC_LOCATION, String PC_QUANTITY, String PC_STOCK_STATUS_ID, String PC_IMG_ID, String PC_MANUFACTURING_ID, String PC_SHIPPING, String PC_PRICE, String PC_POINTS, String PC_TAX_CLASS_ID, String PC_DATA_AVAILABLE, String PC_WEIGHT, String PC_WEIGHT_CLASS_ID, String PC_LENGTH, String PC_WIDTH, String PC_HEIGHT, String PC_LENGTH_CLASS_ID, String PC_SUBTRACT, String PC_MINIMUM, String PC_SORT_ORDER, String PC_STATUS, String CREATED_DATE, boolean selected )
    {
        super();
        this.PC_ID=PC_ID;
        this. PC_NAME=PC_NAME;
        this.DESCRIPTION=DESCRIPTION;
        this.META_TAG=META_TAG;
        this.META_DESCRIPTION=META_DESCRIPTION;
        this.META_KEYWORD=META_KEYWORD;
        this.PC_TYPE=PC_TYPE;
        this.PC_PRIORITY=PC_PRIORITY;
        this.PRODUCT_CATEGORY_LEVEL=PRODUCT_CATEGORY_LEVEL;
        this.PC_MODEL=PC_MODEL;
        this.PC_SKU=PC_SKU;
        this.PC_UPC=PC_UPC;
        this.PC_EAN=PC_EAN;
        this.PC_JAN=PC_JAN;
        this.PC_ISBN=PC_ISBN;
        this.PC_MPN=PC_MPN;
        this.PC_LOCATION=PC_LOCATION;
        this.PC_QUANTITY=PC_QUANTITY;
        this.PC_STOCK_STATUS_ID=PC_STOCK_STATUS_ID;
        this.PC_IMG_ID=PC_IMG_ID;
        this.PC_MANUFACTURING_ID=PC_MANUFACTURING_ID;
        this.PC_SHIPPING=PC_SHIPPING;
        this.PC_PRICE=PC_PRICE;
        this.PC_POINTS=PC_POINTS;
        this.PC_TAX_CLASS_ID=PC_TAX_CLASS_ID;
        this.PC_DATA_AVAILABLE=PC_DATA_AVAILABLE;
        this.PC_WEIGHT=PC_WEIGHT;
        this.PC_WEIGHT_CLASS_ID=PC_WEIGHT_CLASS_ID;
        this.PC_LENGTH=PC_LENGTH;
        this.PC_WIDTH=PC_WIDTH;
        this.PC_HEIGHT=PC_HEIGHT;
        this.PC_LENGTH_CLASS_ID=PC_LENGTH_CLASS_ID;
        this.PC_SUBTRACT=PC_SUBTRACT;
        this.PC_MINIMUM=PC_MINIMUM;
        this.PC_SORT_ORDER=PC_SORT_ORDER;
        this.PC_STATUS=PC_STATUS;
        this.CREATED_DATE=CREATED_DATE;

        this.selected = selected;

    }

    public Product() {
    }

    public String getPC_ID()
    {
        return PC_ID;
    }

    public void setPC_ID(String col1)
    {
        this.PC_ID =PC_ID;
    }

    public String getPC_NAME()
    {
        return PC_NAME;
    }
    public void setPC_NAME(String PC_NAME)
    {
        this.PC_NAME =PC_NAME;
    }

    public void setDESCRIPTION(String DESCRIPTION)
    {
        this.DESCRIPTION =DESCRIPTION;
    }
    public String getDESCRIPTION()
    {
        return DESCRIPTION;
    }

    public void setMETA_TAG(String col4)
    {
        this.META_TAG =META_TAG;
    }
    public String getMETA_TAG()
    {
        return META_TAG;
    }

    public void setMETA_DESCRIPTION(String META_DESCRIPTION) {this.META_DESCRIPTION =META_DESCRIPTION; }
    public String getMETA_DESCRIPTION()
    {
        return META_DESCRIPTION;
    }

    public void setMETA_KEYWORD(String META_KEYWORD)
    {
        this.META_KEYWORD =META_KEYWORD;
    }
    public String getMETA_KEYWORD()
    {
        return META_KEYWORD;
    }

    public void setPC_TYPE(String PC_TYPE)
    {
        this.PC_TYPE =PC_TYPE;
    }
    public String getPC_TYPE()
    {
        return PC_TYPE;
    }

    public void setPC_PRIORITY(String PC_PRIORITY)
    {
        this.PC_PRIORITY =PC_PRIORITY;
    }
    public String getPC_PRIORITY()
    {
        return PC_PRIORITY;
    }

    public void setPRODUCT_CATEGORY_LEVEL(String PRODUCT_CATEGORY_LEVEL) {this.PRODUCT_CATEGORY_LEVEL =PRODUCT_CATEGORY_LEVEL; }
    public String getPRODUCT_CATEGORY_LEVEL()
    {
        return PRODUCT_CATEGORY_LEVEL;
    }

    public void setPC_MODEL(String PC_MODEL)
    {
        this.PC_MODEL =PC_MODEL;
    }
    public String getPC_MODEL()
    {
        return PC_MODEL;
    }

    public void setPC_SKU(String PC_SKU)
    {
        this.PC_SKU =PC_SKU;
    }
    public String getPC_SKU()
    {
        return PC_SKU;
    }

    public void setPC_UPC(String PC_UPC)
    {
        this.PC_UPC =PC_UPC;
    }
    public String getPC_UPC()
    {
        return PC_UPC;
    }

    public void setPC_EAN(String PC_EAN)
    {
        this.PC_EAN =PC_EAN;
    }
    public String getPC_EAN()
    {
        return PC_EAN;
    }

    public void setPC_JAN(String PC_JAN)
    {
        this.PC_JAN =PC_JAN;
    }
    public String getPC_JAN()
    {
        return PC_JAN;
    }

    public void setPC_ISBN(String col5)
    {
        this.PC_ISBN =PC_ISBN;
    }
    public String getPC_ISBN()
    {
        return PC_ISBN;
    }

    public void setPC_MPN(String PC_MPN)
    {
        this.PC_MPN =PC_MPN;
    }
    public String getPC_MPN()
    {
        return PC_MPN;
    }

    public void setPC_LOCATION(String PC_LOCATION)
    {
        this.PC_LOCATION =PC_LOCATION;
    }
    public String getPC_LOCATION()
    {
        return PC_LOCATION;
    }

    public void setPC_QUANTITY(String PC_QUANTITY)
    {
        this.PC_QUANTITY =PC_QUANTITY;
    }
    public String getPC_QUANTITY()
    {
        return PC_QUANTITY;
    }

    public void setPC_STOCK_STATUS_ID(String PC_STOCK_STATUS_ID) {this.PC_STOCK_STATUS_ID =PC_STOCK_STATUS_ID; }
    public String getPC_STOCK_STATUS_ID()
    {
        return PC_STOCK_STATUS_ID;
    }

    public void setPC_IMG_ID(String PC_IMG_ID)
    {
        this.PC_IMG_ID =PC_IMG_ID;
    }
    public String getPC_IMG_ID()
    {
        return PC_IMG_ID;
    }

    public void setPC_MANUFACTURING_ID(String PC_MANUFACTURING_ID) { this.PC_MANUFACTURING_ID =PC_MANUFACTURING_ID; }
    public String getPC_MANUFACTURING_ID()
    {
        return PC_MANUFACTURING_ID;
    }

    public void setPC_SHIPPING(String PC_SHIPPING)
    {
        this.PC_SHIPPING =PC_SHIPPING;
    }
    public String getPC_SHIPPING()
    {
        return PC_SHIPPING;
    }

    public void setPC_PRICE(String PC_PRICE)
    {
        this.PC_PRICE =PC_PRICE;
    }
    public String getPC_PRICE()
    {
        return PC_PRICE;
    }

    public void setPC_POINTS(String PC_POINTS)
    {
        this.PC_POINTS =PC_POINTS;
    }
    public String getPC_POINTS()
    {
        return PC_POINTS;
    }

    public void setPC_TAX_CLASS_ID(String PC_TAX_CLASS_ID) {this.PC_TAX_CLASS_ID =PC_TAX_CLASS_ID; }
    public String getPC_TAX_CLASS_ID()
    {
        return PC_TAX_CLASS_ID;
    }

    public void setPC_DATA_AVAILABLE(String PC_DATA_AVAILABLE) { this.PC_DATA_AVAILABLE =PC_DATA_AVAILABLE; }
    public String getPC_DATA_AVAILABLE()
    {
        return PC_DATA_AVAILABLE;
    }

    public void setPC_WEIGHT(String PC_WEIGHT)
    {
        this.PC_WEIGHT =PC_WEIGHT;
    }
    public String getPC_WEIGHT()
    {
        return PC_WEIGHT;
    }

    public void setPC_WEIGHT_CLASS_ID(String PC_WEIGHT_CLASS_ID) { this.PC_WEIGHT_CLASS_ID =PC_WEIGHT_CLASS_ID;}
    public String getPC_WEIGHT_CLASS_ID()
    {
        return PC_WEIGHT_CLASS_ID;
    }

    public void setPC_LENGTH(String PC_LENGTH)
    {
        this.PC_LENGTH =PC_LENGTH;
    }
    public String getPC_LENGTH()
    {
        return PC_LENGTH;
    }

    public void setPC_WIDTH(String PC_WIDTH)
    {
        this.PC_WIDTH =PC_WIDTH;
    }
    public String getPC_WIDTH()
    {
        return PC_WIDTH;
    }

    public void setPC_HEIGHT(String PC_HEIGHT) {this.PC_HEIGHT =PC_HEIGHT;}
    public String getPC_HEIGHT()
    {
        return PC_HEIGHT;
    }

    public void setPC_LENGTH_CLASS_ID(String PC_LENGTH_CLASS_ID) {this.PC_LENGTH_CLASS_ID =PC_LENGTH_CLASS_ID; }
    public String getPC_LENGTH_CLASS_ID()
    {
        return PC_LENGTH_CLASS_ID;
    }

    public void setPC_SUBTRACT(String PC_SUBTRACT)
    {
        this.PC_SUBTRACT =PC_SUBTRACT;
    }
    public String getPC_SUBTRACT()
    {
        return PC_SUBTRACT;
    }

    public void setPC_MINIMUM(String PC_MINIMUM)
    {
        this.PC_MINIMUM =PC_MINIMUM;
    }
    public String getPC_MINIMUM()
    {
        return PC_MINIMUM;
    }

    public void setPC_SORT_ORDER(String PC_SORT_ORDER)
    {
        this.PC_SORT_ORDER =PC_SORT_ORDER;
    }
    public String getPC_SORT_ORDER()
    {
        return PC_SORT_ORDER;
    }

    public void setPC_STATUS(String PC_STATUS)
    {
        this.PC_STATUS =PC_STATUS;
    }
    public String getPC_STATUS()
    {
        return PC_STATUS;
    }

    public String getCREATED_DATE()
    {
        return CREATED_DATE;
    }
    public void setCREATED_DATE(String col5)
    {
        this.CREATED_DATE =CREATED_DATE;
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
