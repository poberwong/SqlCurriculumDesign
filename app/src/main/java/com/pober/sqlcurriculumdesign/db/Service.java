package com.pober.sqlcurriculumdesign.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.bob.digcsdn.models.BlogItem;
import com.bob.digcsdn.utils.LogUtil;
import com.pober.sqlcurriculumdesign.models.ImportItem;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户信息操作的封装
 *
 * @author Bob
 */
public class Service {//存的时候不要存入id，获取时需要取出id，因为在必要时，我们需要根据id来显示到listView中
    private DBHelper helper;
    private SQLiteDatabase db;
    private static Service blogService;
    private ContentValues values;

    private Service(Context context) {
        // TODO Auto-generated constructor stub
        helper = new DBHelper(context);
        values = new ContentValues();
    }

    public static synchronized Service getInstance(Context context) {//单例获取UserService对象
        if (blogService == null) {
            blogService = new Service(context);
        }
        return blogService;
    }

    /**
     * 插入某一个类别的所有博客
     *
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
     * 删除一种栏目博客的数据存储
     *
     * @param blogType
     */
    public synchronized void delete(int blogType) {
        db = helper.getWritableDatabase();
        db.delete(DBInfo.Table.BLOG_TABLE_NAME, BlogItem.BLOGTYPE + "= ?", new String[]{blogType + ""});
        db.close();
    }

    /**
     * 查找某一栏目下的所有博客,这里只会存上第一页的博客，因为在加载的时候，并没有存库
     *
     * @param blogType
     * @return
     */
    public synchronized List<BlogItem> loadBlog(int blogType) {
        List<BlogItem> blogs = new ArrayList<>();
        BlogItem item;
        db = helper.getReadableDatabase();

        Cursor cursor = db.query(DBInfo.Table.BLOG_TABLE_NAME, null, BlogItem.BLOGTYPE + "= ?", new String[]{blogType + ""}, null, null, null);
        while (cursor.moveToNext()) {//第一步就直接转向第一条记录，为空则退出
            item = new BlogItem();
            item.setTitle(cursor.getString(cursor.getColumnIndex(BlogItem.TITLE)));
            item.setContent(cursor.getString(cursor.getColumnIndex(BlogItem.CONTENT)));
            item.setDate(cursor.getString(cursor.getColumnIndex(BlogItem.DATE)));
            item.setImgLink(cursor.getString(cursor.getColumnIndex(BlogItem.IMG)));
            item.setLink(cursor.getString(cursor.getColumnIndex(BlogItem.LINK)));
            item.setBlogType(blogType);

            blogs.add(item);
        }
        cursor.close();
        db.close();
        return blogs;
    }

}
