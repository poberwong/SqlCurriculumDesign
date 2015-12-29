package com.pober.sqlcurriculumdesign.models;

/**
 * Created by Bob on 15/12/25.
 */
public class ExportItem implements OperateItem{
    private int seqCode;   //顺序码
    private String barCode;   //条形码
    private String exportPrice;    //零售价
    private String date;    //日期
    private String count; //数量

    public static final String SEQ_CODE= "seqCode";//这几个常量主要用户数据库的操作
    public static final String BAR_CODE= "barCode";
    public static final String EXPORT_PRICE= "exportPrice";
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

    public String getPrice() {
        return exportPrice;
    }

    public void setPrice(String exportPrice) {
        this.exportPrice = exportPrice;
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
