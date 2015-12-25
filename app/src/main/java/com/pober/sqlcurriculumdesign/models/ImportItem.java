package com.pober.sqlcurriculumdesign.models;

/**
 * Created by Bob on 15/12/25.
 */
public class ImportItem {
    private int seqCode;   //顺序码
    private String barCode;   //条形码
    private String importPrice;    //进价
    private String date;    //日期
    private String count; //采购数量

    public static final String SEQ_CODE= "seqCode";//这几个常量主要用户数据库的操作
    public static final String BAR_CODE= "barCode";
    public static final String IMPORT_PRICE= "importPrice";
    public static final String DATE= "date";
    public static final String COUNT= "count";

    public int getSeqCode() {
        return seqCode;
    }

    public void setSeqCode(int seqCode) {
        this.seqCode = seqCode;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getImportPrice() {
        return importPrice;
    }

    public void setImportPrice(String importPrice) {
        this.importPrice = importPrice;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
