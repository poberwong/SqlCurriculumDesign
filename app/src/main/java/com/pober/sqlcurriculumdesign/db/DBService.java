package com.pober.sqlcurriculumdesign.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;

import com.pober.sqlcurriculumdesign.R;
import com.pober.sqlcurriculumdesign.models.ExportItem;
import com.pober.sqlcurriculumdesign.models.ImportItem;
import com.pober.sqlcurriculumdesign.models.OperateItem;
import com.pober.sqlcurriculumdesign.models.RepertoryItem;
import com.pober.sqlcurriculumdesign.utils.EasyUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户信息操作的封装
 * 在逻辑语句正常的情况下，好像并不能捕捉sql执行失败的exception
 *
 * @author Bob
 */
public class DBService {//存的时候不要存入id，获取时需要取出id，因为在必要时，我们需要根据id来显示到listView中
    private DBHelper helper;
    private SQLiteDatabase db;
    private static DBService DBService;
    private ContentValues values;

    private DBService(Context context) {
        // TODO Auto-generated constructor stub
        helper = new DBHelper(context);
        values = new ContentValues();
    }

    public static synchronized DBService getInstance(Context context) {//单例获取UserService对象
        if (DBService == null) {
            DBService = new DBService(context);
        }
        return DBService;
    }

    public synchronized boolean importGoods(ImportItem importItem, RepertoryItem repeItem) {
        try {
            if (repeItem == null) {//库存中有商品记录，就在加入进货表后直接更新库存数量
                insertImport(importItem);
                updateRepe(importItem.getBarCode(), Integer.parseInt(importItem.getCount()));
                return true;
            } else {
                insertImport(importItem);
                insertRepe(repeItem);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public synchronized boolean exportGoods(Context context, View rootView, List<ExportItem> items) {
        for (ExportItem item : items) {
            if (getRepeCount(item.getBarCode()) - Integer.parseInt(item.getCount()) < 0) {
                return false;
            }
        }
        for (ExportItem item : items) {
            updateRepe(item.getBarCode(), getRepeCount(item.getBarCode()) - Integer.parseInt(item.getCount()) );
            insertExports(item);
        }
        return true;
    }

    /**
     * 插入一条进货记录
     *
     * @param item
     */
    public synchronized void insertImport(ImportItem item) {
        db = helper.getWritableDatabase();
        values.clear();//清空values

        values.put(ImportItem.BAR_CODE, item.getBarCode());
        values.put(ImportItem.DATE, item.getDate());
        values.put(ImportItem.IMPORT_PRICE, item.getPrice());
        values.put(ImportItem.COUNT, item.getCount());

        db.insert(DBInfo.Table.IMPORT_TABLE_NAME, null, values);
        db.close();
    }

    /**
     * 插入一条出售记录
     *
     * @param item db.execSQL("insert into ? (?,?,?,?,?) values(?,?,?,?,?)", new Object[]{DBInfo.Table.IMPORT_TABLE_CREATE ,ExportItem.SEQ_CODE, ExportItem.BAR_CODE, ExportItem.DATE, ExportItem.EXPORT_PRICE, ExportItem.COUNT, item.getSeqCode(), item.getBarCode(), item.getDate(), item.getExportPrice(), item.getCount()});
     */
    public synchronized boolean insertExports(ExportItem item) {
        db = helper.getWritableDatabase();
        values.clear();//清空values
        values.put(ExportItem.BAR_CODE, item.getBarCode());
        values.put(ExportItem.DATE, item.getDate());
        values.put(ExportItem.EXPORT_PRICE, item.getPrice());
        values.put(ExportItem.COUNT, item.getCount());
        db.insert(DBInfo.Table.EXPORT_TABLE_NAME, null, values);
        db.close();
        return true;

    }

    public synchronized void insertRepe(RepertoryItem item) {
        db = helper.getWritableDatabase();
        values.clear();
        values.put(RepertoryItem.BAR_CODE, item.getBarCode());
        values.put(RepertoryItem.GOODS_NAME, item.getGoodsName());
        values.put(RepertoryItem.COUNT, item.getCount());
        values.put(RepertoryItem.MANUFACTURER, item.getManufacturer());
        values.put(RepertoryItem.STANDARD, item.getStandard());
        values.put(RepertoryItem.RETAIL_PRICE, item.getRetailPrice());
        db.insert(DBInfo.Table.REPE_TABLE_NAME, null, values);
        db.close();
    }

    /**
     * 进货/出货之后需要根据条形码更新库存表
     */
    public synchronized boolean updateRepe(String barCode, int count) {
        db = helper.getWritableDatabase();
        values.clear();
        values.put(RepertoryItem.COUNT, count);
        db.update(DBInfo.Table.REPE_TABLE_NAME, values, RepertoryItem.BAR_CODE + "= ?", new String[]{barCode});
        db.close();
        return true;
    }

    /**
     * 根据条形码查询数据库中是否有对应的商品
     *
     * @param barCode
     * @return
     */
    public synchronized boolean hasRepe(String barCode) {
        db = helper.getReadableDatabase();
        Cursor cursor = db.query(DBInfo.Table.REPE_TABLE_NAME, null, RepertoryItem.BAR_CODE + "= ?", new String[]{barCode}, null, null, null);
        db.close();
        if (cursor.getCount() == 0) {
            cursor.close();
            return false;
        } else {
            cursor.close();
            return true;
        }

    }

    public synchronized int getRepeCount(String barCode) {
        db = helper.getReadableDatabase();
        Cursor cursor = db.query(DBInfo.Table.REPE_TABLE_NAME, new String[]{RepertoryItem.COUNT}, RepertoryItem.BAR_CODE + "= ?", new String[]{barCode}, null, null, null);
        cursor.moveToFirst();
        int result = Integer.parseInt(cursor.getString(0));
//        db.close(); //这个db一定不能关，否则返回之后，就无法继续执行update操作了
        cursor.close();
        return result;
    }

    public synchronized String getGoodsName(String barCode) {
        db = helper.getReadableDatabase();
        Cursor cursor = db.query(DBInfo.Table.REPE_TABLE_NAME, new String[]{RepertoryItem.GOODS_NAME}, RepertoryItem.BAR_CODE + "= ?", new String[]{barCode}, null, null, null);
        cursor.moveToFirst();
        String result = cursor.getString(0);
        db.close(); //这个db一定不能关，否则返回之后，就无法继续执行update操作了
        cursor.close();
        return result;
    }


    /**
     * 查库模块
     *
     * @param selection
     * @param selectionArgs
     * @return
     */
    public synchronized List<RepertoryItem> RepeQuery(String selection, String[] selectionArgs) {
        List<RepertoryItem> items = new ArrayList<>();
        RepertoryItem item;
        db = helper.getReadableDatabase();
        Cursor cursor = db.query(DBInfo.Table.REPE_TABLE_NAME, null, selection, selectionArgs, null, null, null);
        while (cursor.moveToNext()) {
            item = new RepertoryItem();
            item.setBarCode(cursor.getString(cursor.getColumnIndex(RepertoryItem.BAR_CODE)));
            item.setGoodsName(cursor.getString(cursor.getColumnIndex(RepertoryItem.GOODS_NAME)));
            item.setCount(cursor.getString(cursor.getColumnIndex(RepertoryItem.COUNT)));
            item.setManufacturer(cursor.getString(cursor.getColumnIndex(RepertoryItem.MANUFACTURER)));
            item.setStandard(cursor.getString(cursor.getColumnIndex(RepertoryItem.STANDARD)));
            item.setRetailPrice(cursor.getString(cursor.getColumnIndex(RepertoryItem.RETAIL_PRICE)));
            items.add(item);
        }
        cursor.close();
        db.close();
        return items;
    }

    public synchronized List<OperateItem> exportQuery(String selection, String[] selectionArgs) {
        List<OperateItem> items = new ArrayList<>();
        ExportItem item;
        db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select seqCode, exportTable.barCode, exportPrice, exportTable.count, date from " + DBInfo.Table.EXPORT_TABLE_NAME + ", " + DBInfo.Table.REPE_TABLE_NAME
                + " where " + DBInfo.Table.EXPORT_TABLE_NAME + "." + "barCode= " + DBInfo.Table.REPE_TABLE_NAME + "." + "barCode and " + selection, selectionArgs);
//        Cursor cursor= db.query(DBInfo.Table.EXPORT_TABLE_NAME, null, selection,selectionArgs,null,null,null);
        while (cursor.moveToNext()) {
            item = new ExportItem();
            item.setSeqCode(cursor.getInt(cursor.getColumnIndex(ExportItem.SEQ_CODE)));
            item.setBarCode(cursor.getString(cursor.getColumnIndex(ExportItem.BAR_CODE)));
            item.setPrice(cursor.getString(cursor.getColumnIndex(ExportItem.EXPORT_PRICE)));
            item.setCount(cursor.getString(cursor.getColumnIndex(ExportItem.COUNT)));
            item.setDate(cursor.getString(cursor.getColumnIndex(ExportItem.DATE)));
            items.add(item);
        }
        cursor.close();
        db.close();
        return items;
    }

    public synchronized List<OperateItem> importQuery(String selection, String[] selectionArgs) {
        List<OperateItem> items = new ArrayList<>();
        ImportItem item;
        db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select seqCode, importTable.barCode, importPrice, importTable.count, date from " + DBInfo.Table.IMPORT_TABLE_NAME + ", " + DBInfo.Table.REPE_TABLE_NAME
                + " where " + selection, selectionArgs);
//        Cursor cursor= db.query(DBInfo.Table.IMPORT_TABLE_NAME, null, selection,selectionArgs,null,null,null);
        while (cursor.moveToNext()) {
            item = new ImportItem();
            item.setSeqCode(cursor.getInt(cursor.getColumnIndex(ImportItem.SEQ_CODE)));
            item.setBarCode(cursor.getString(cursor.getColumnIndex(ImportItem.BAR_CODE)));
            item.setPrice(cursor.getString(cursor.getColumnIndex(ImportItem.IMPORT_PRICE)));
            item.setCount(cursor.getString(cursor.getColumnIndex(ImportItem.COUNT)));
            item.setDate(cursor.getString(cursor.getColumnIndex(ImportItem.DATE)));
            items.add(item);
        }
        cursor.close();
        db.close();
        return items;
    }

}
