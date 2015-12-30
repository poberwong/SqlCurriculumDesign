package com.pober.sqlcurriculumdesign.db;

/**
 * 数据库信息
 *
 * @author Bob
 */

public class DBInfo {

    public static class DB {
        public static String DB_NAME = "BobDesign";
        public static int VERSION = 1;
    }

    public static class Table {
        public static String REPE_TABLE_NAME = "repertoryTable";
        public static String IMPORT_TABLE_NAME = "importTable";
        public static String EXPORT_TABLE_NAME = "exportTable";

        public static String REPE_TABLE_CREATE = "CREATE TABLE "
                + REPE_TABLE_NAME + " (barCode TEXT PRIMARY KEY, goodsName TEXT, count TEXT, " +
                "manufacturer TEXT, standard TEXT, retailPrice TEXT )";
        public static String IMPORT_TABLE_CREATE = "CREATE TABLE "
                + IMPORT_TABLE_NAME + " (seqCode INTEGER PRIMARY KEY, barCode TEXT, importPrice TEXT, " +
                "count TEXT, date TEXT, FOREIGN KEY (barCode) REFERENCES repertoryTable(barCode) )";
        public static String EXPORT_TABLE_CREATE = "CREATE TABLE "
                + EXPORT_TABLE_NAME + " (seqCode INTEGER PRIMARY KEY, barCode TEXT, exportPrice TEXT, " +
                "count TEXT, date TEXT, FOREIGN KEY (barCode) REFERENCES repertoryTable(barCode) )";

    }
}
