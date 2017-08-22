package com.tobacco.xinyiyun.knowledge.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.orhanobut.logger.Logger;
import com.tobacco.xinyiyun.knowledge.BuildConfig;
import com.tobacco.xinyiyun.knowledge.entity.Collect;

import java.sql.SQLException;

public class DBHelper extends OrmLiteSqliteOpenHelper {
    public static final int VERSION = 2;
    public static String DB_NAME = BuildConfig.APPLICATION_ID;

    /**
     * 增加表在该数组中添加类并修改 {@link #VERSION} 的值
     */
    private Class<?>[] mTableClasss = new Class<?>[]{Collect.class};

    public DBHelper(Context context) {
        super(context, DB_NAME,
                null,
                VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase arg0, ConnectionSource arg1) {

        try {
            Logger.d("create tables!");
            for (int i = 0; i < mTableClasss.length; i++) {
                TableUtils.createTable(arg1, mTableClasss[i]);
            }

        } catch (SQLException e) {
            Logger.e("create table faile!", e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, ConnectionSource arg1, int arg2, int arg3) {
        try {
            Logger.d("onUpgrade tables!");

            for (Class<?> cls : mTableClasss) {
                TableUtils.dropTable(arg1, cls, true);
            }
            onCreate(arg0, arg1);
        } catch (SQLException e) {
            Logger.e(e, "onUpgrade tables!");
        }
    }


}
