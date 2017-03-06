package com.my.greendaotest;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.my.greendaotest.entity.DaoMaster;
import com.my.greendaotest.entity.DaoSession;

/**
 * Created by ZongJie on 2017/3/6.
 */

public class MyApplication extends Application {
    private static MyApplication mMyApplication;
    private DaoMaster.DevOpenHelper mHelper;
    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        mMyApplication = this;
        setDatabase();
    }

    public static MyApplication getmMyApplication(){
        if(mMyApplication == null){
            return new MyApplication();
        }
        return mMyApplication;
    }

    private void setDatabase(){
        // 通过DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的SQLiteOpenHelper 对象。
        // 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为greenDAO 已经帮你做了。
        // 注意：默认的DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
        // 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
        mHelper = new DaoMaster.DevOpenHelper(this, "userbean.db", null);
        db = mHelper.getWritableDatabase();
        // 注意：该数据库连接属于DaoMaster，所以多个 Session 指的是相同的数据库连接。
        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
    }

    public DaoSession getmDaoSession(){
        return mDaoSession;
    }
    public SQLiteDatabase getDb(){
        return db;
    }
}
