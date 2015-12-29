package com.pober.sqlcurriculumdesign.models;

/**
 * Created by Bob on 15/12/29.
 */
public interface OperateItem {
    int getSeqCode();

    void setSeqCode(int seqCode);

    String getBarCode();

    void setBarCode(String barCode);

    String getPrice();

    void setPrice(String exportPrice);

    String getDate();

    void setDate(String date);

    String getCount();

    void setCount(String count);
}
