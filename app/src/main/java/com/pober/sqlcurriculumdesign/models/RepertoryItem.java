package com.pober.sqlcurriculumdesign.models;

/**
 * Created by Bob on 15/12/25.
 */
public class RepertoryItem {

    private String barCode;   //条形码
    private String goodsName;   //商品名
    private String count; //库存数量
    private String manufacturer;    //生产厂家
    private String standard;    //日期
    private String retailPrice;    //进价

    public static final String BAR_CODE= "barCode";
    public static final String GOODS_NAME= "goodsName";
    public static final String COUNT= "count";
    public static final String MANUFACTURER= "manufacturer";
    public static final String STANDARD= "standard";
    public static final String RETAIL_PRICE= "retailPrice";

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(String retailPrice) {
        this.retailPrice = retailPrice;
    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }
}
