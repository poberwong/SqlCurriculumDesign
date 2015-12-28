package com.pober.sqlcurriculumdesign.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.pober.sqlcurriculumdesign.models.ExportItem;
import com.pober.sqlcurriculumdesign.models.ImportItem;
import com.pober.sqlcurriculumdesign.models.RepertoryItem;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户信息操作的封装
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

    public synchronized boolean ImportGoods(ImportItem item){
        if (hasRepe(item.getBarCode())){//库存中有商品记录，就在加入进货表后直接更新库存数量
            insertImport(item);
            updateRepe(item.getBarCode(), Integer.parseInt(item.getCount()));
        }else {
            insertRepe(new RepertoryItem());
        }
    }

    public synchronized boolean ExportGoods()

    /**
     * 插入一条进货记录
     * @param item
     */
    public synchronized void insertImport(ImportItem item) {
        db = helper.getWritableDatabase();
        values.clear();//清空values

        values.put(ImportItem.SEQ_CODE, item.getSeqCode());
        values.put(ImportItem.BAR_CODE, item.getBarCode());
        values.put(ImportItem.DATE, item.getDate());
        values.put(ImportItem.IMPORT_PRICE, item.getImportPrice());
        values.put(ImportItem.COUNT, item.getCount());

        db.insert(DBInfo.Table.IMPORT_TABLE_CREATE, null, values);
        db.close();
    }

    /**
     * 插入一条出售记录
     * @param item
     * db.execSQL("insert into ? (?,?,?,?,?) values(?,?,?,?,?)", new Object[]{DBInfo.Table.IMPORT_TABLE_CREATE ,ExportItem.SEQ_CODE, ExportItem.BAR_CODE, ExportItem.DATE, ExportItem.EXPORT_PRICE, ExportItem.COUNT, item.getSeqCode(), item.getBarCode(), item.getDate(), item.getExportPrice(), item.getCount()});
     */
    public synchronized void insertExport(ExportItem item) {
        db = helper.getWritableDatabase();
        values.clear();//清空values

        values.put(ExportItem.SEQ_CODE, item.getSeqCode());
        values.put(ExportItem.BAR_CODE, item.getBarCode());
        values.put(ExportItem.DATE, item.getDate());
        values.put(ExportItem.EXPORT_PRICE, item.getExportPrice());
        values.put(ExportItem.COUNT, item.getCount());

        db.insert(DBInfo.Table.IMPORT_TABLE_CREATE, null, values);
        db.close();
    }

    public synchronized void insertRepe( RepertoryItem item){
        db= helper.getWritableDatabase();
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
    public synchronized void updateRepe(String barCode,int count){
        db= helper.getWritableDatabase();
        values.clear();
        values.put(RepertoryItem.COUNT, count + getRepeCount(barCode));
        db.update(DBInfo.Table.REPE_TABLE_NAME, values, RepertoryItem.BAR_CODE + "= ?", new String[]{barCode});
        db.close();
    }

    /**
     * Repertory 表：增，改，查。
     */

    /**
     * 根据条形码查询数据库中是否有对应的商品
     * @param barCode
     * @return
     */
    public synchronized boolean hasRepe( String barCode){
        db = helper.getReadableDatabase();
        Cursor cursor= db.query(DBInfo.Table.REPE_TABLE_NAME, null, RepertoryItem.BAR_CODE+ "= ?",new String[]{barCode},null,null,null);
        db.close();
        if (cursor.getCount() == 0){
            cursor.close();
            return false;
        }else{
            cursor.close();
            return true;
        }

    }

    public synchronized int getRepeCount( String barCode){
        db = helper.getReadableDatabase();
        Cursor cursor= db.query(DBInfo.Table.REPE_TABLE_NAME, new String[]{RepertoryItem.COUNT}, RepertoryItem.BAR_CODE+ "= ?",new String[]{barCode},null,null,null);
        cursor.moveToFirst();
        int result = Integer.parseInt(cursor.getString(0));
        db.close();
        cursor.close();
        return result;
    }

    /**
     * 查库模块
     * @param selection
     * @param selectionArgs
     * @return
     */
    public synchronized List<RepertoryItem> RepeQuery(String selection, String[] selectionArgs){
        List<RepertoryItem> items = new ArrayList<>();
        RepertoryItem item;
        db = helper.getReadableDatabase();
        Cursor cursor= db.query(DBInfo.Table.REPE_TABLE_NAME, null, selection, selectionArgs, null, null, null);
        while(cursor.moveToNext()){
            item = new RepertoryItem();
            item.setBarCode(cursor.getColumnName(cursor.getColumnIndex(RepertoryItem.BAR_CODE)));
            item.setGoodsName(cursor.getColumnName(cursor.getColumnIndex(RepertoryItem.GOODS_NAME)));
            item.setCount(cursor.getColumnName(cursor.getColumnIndex(RepertoryItem.COUNT)));
            item.setManufacturer(cursor.getColumnName(cursor.getColumnIndex(RepertoryItem.MANUFACTURER)));
            item.setStandard(cursor.getColumnName(cursor.getColumnIndex(RepertoryItem.STANDARD)));
            item.setRetailPrice(cursor.getColumnName(cursor.getColumnIndex(RepertoryItem.RETAIL_PRICE)));
            items.add(item);
        }
        cursor.close();
        db.close();
        return items;
    }

    public synchronized List<ExportItem> exportQuery(String selection, String[] selectionArgs){
        List<ExportItem> items = new ArrayList<>();
        ExportItem item;
        db = helper.getReadableDatabase();
        Cursor cursor= db.query(DBInfo.Table.EXPORT_TABLE_NAME, null, selection,selectionArgs,null,null,null);
        while(cursor.moveToNext()){
            item = new ExportItem();
            item.setBarCode(cursor.getColumnName(cursor.getColumnIndex(ExportItem.BAR_CODE)));
            item.setExportPrice(cursor.getColumnName(cursor.getColumnIndex(ExportItem.EXPORT_PRICE)));
            item.setCount(cursor.getColumnName(cursor.getColumnIndex(RepertoryItem.COUNT)));
            item.setDate(cursor.getColumnName(cursor.getColumnIndex(ExportItem.DATE)));
            items.add(item);
        }
        cursor.close();
        db.close();
        return items;
    }

    public synchronized List<ImportItem> importQuery(String selection, String[] selectionArgs){
        List<ImportItem> items = new ArrayList<>();
        ImportItem item;
        db = helper.getReadableDatabase();
        Cursor cursor= db.query(DBInfo.Table.EXPORT_TABLE_NAME, null, selection,selectionArgs,null,null,null);
        while(cursor.moveToNext()){
            item = new ImportItem();
            item.setBarCode(cursor.getColumnName(cursor.getColumnIndex(ExportItem.BAR_CODE)));
            item.setImportPrice(cursor.getColumnName(cursor.getColumnIndex(ExportItem.EXPORT_PRICE)));
            item.setCount(cursor.getColumnName(cursor.getColumnIndex(RepertoryItem.COUNT)));
            item.setDate(cursor.getColumnName(cursor.getColumnIndex(ExportItem.DATE)));
            items.add(item);
        }
        cursor.close();
        db.close();
        return items;
    }

}
