package com.example.classtest.datastorage;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.classtest.BaseActivity;
import com.example.classtest.R;
import com.example.classtest.datastorage.sqlite.MyOpenHelper;
import com.example.classtest.datastorage.sqlite.TypeEntry;

/**
 * @author Neal 2016-08-25
 * @description SharedPreferences的使用demo
 */
public class SharedPre extends BaseActivity implements View.OnClickListener {

    //Shared Preferences获取的值
    TextView tv_spvalue;
    //更改SharedPreferences值的按钮
    Button bt_writeSp;
    //跳转到InternalStorage页面的按钮
    Button bt_internal;
    //跳转到Cache file 页面的按钮
    Button bt_cache;
    //ExterbakStorage 页面的按钮
    Button bt_external;
    //跳转到   ContentProviderActivity页面的按钮
    Button bt_provider;
    //显示数据库内容的文本
    TextView tv_database;
    //自定义数据库帮助类
    MyOpenHelper mHelper;
    //申请外部存储器写权限的申请码
    private final int   WRITE_EXTERNAL_STORAGE = 0;

    //Shared Preferences文件名
    private final String SPFILE_NAME = "spfile";
    //SharedPrenferences存入的布尔值key
    private final String BOOL_NAME = "myBoolean";

    @Override
    protected int setContent() {
        return R.layout.activity_shared_preferences;
    }

    @Override
    protected void initView() {
        mHelper = new MyOpenHelper(this);
        tv_spvalue = (TextView) findViewById(R.id.tv_spValue);
        bt_writeSp = (Button) findViewById(R.id.bt_writeSp);
        bt_internal = (Button) findViewById(R.id.bt_internal);
        bt_cache = (Button) findViewById(R.id.bt_cache);
        bt_external = (Button) findViewById(R.id.bt_external);
        tv_database = (TextView) findViewById(R.id.tv_database);
        bt_provider=(Button)findViewById(R.id.bt_provider);
        // deleteDatabase();
        //  createDatabase();

        requestPermission();
        readSPValue();
    }

    @Override
    protected void setListener() {
        bt_writeSp.setOnClickListener(this);
        bt_internal.setOnClickListener(this);
        bt_cache.setOnClickListener(this);
        bt_external.setOnClickListener(this);
        bt_provider.setOnClickListener(this);
    }




    /**
     * @description 向SharedPreferences中写入值
     */
    private void writeSPValue() {
        //获得Sharedpreferences的实例，参数1为文件名， 参数二为文件模式
        //默认模式为0，即MODE_PRIVATE,有对应文件则读取，无则创建
        SharedPreferences sp = getSharedPreferences(SPFILE_NAME, 0);
        //获得Sharedpreferences的编辑器
        SharedPreferences.Editor editor = sp.edit();
        //向Sharedpreferences添加一个key为"myBoolean"的布尔值
        editor.putBoolean(BOOL_NAME, true);
        //提交数据
        editor.commit();
        //读取sp中的对应boolean数据
        boolean temp = sp.getBoolean(BOOL_NAME, false);
        //设置到UI中去展示
        tv_spvalue.setText(temp + "");
    }

    /**
     * @description 读取SharedPreferences中的值
     */
    private void readSPValue() {
        // 0，参数1为文件名， 参数二为文件模式
        //默认模式为0，即MODE_PRIVATE,有对应文件则读取，无则创建
        SharedPreferences sp = getSharedPreferences(SPFILE_NAME, 0);
        //读取sp中的对应boolean数据
        boolean temp = sp.getBoolean(BOOL_NAME, false);
        //设置到UI中去展示
        tv_spvalue.setText(temp + "");
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.bt_writeSp://Wirte a true value
                writeSPValue();
                break;
            case R.id.bt_internal://Start internal storage activity
                //跳转到内部存储器demo页面
                intent = new Intent(SharedPre.this, InternalStorage.class);
                startActivity(intent);
                break;
            case R.id.bt_cache://Start cache file activity
                //跳转到缓存demo页面
                intent = new Intent(SharedPre.this, CacheFile.class);
                startActivity(intent);
                break;
            case R.id.bt_external:
                //跳到外部存储器的demo界面
                intent = new Intent(SharedPre.this
                        , ExternalStorage.class);
                startActivity(intent);
                break;
            case R.id.bt_provider:
                //跳到外部存储器的ContentProviderActivity界面
                intent = new Intent(SharedPre.this
                        , ContentProviderActivity.class);
                startActivity(intent);
                break;
        }
    }

    /**
     * 创建数据库
     */
    private void createDatabase() {

        //通过帮助类实例化一个可写入的数据库对象
        SQLiteDatabase db = mHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TypeEntry.COLUMMNS_NAME_TYPE, "本地电话");
        contentValues.put(TypeEntry.COLUMMNS_NAME_SUBTABLE, "localservice");
        //向数据库中插入值
        db.insert(TypeEntry.TABLE_NAME, //表名
                null,//当插入空行或者contentvalues为空时替代的空数据内容
                contentValues);//插入的值
        contentValues.put(TypeEntry.COLUMMNS_NAME_TYPE, "订餐电话");
        contentValues.put(TypeEntry.COLUMMNS_NAME_SUBTABLE, "localservice");
        //向数据库中插入值
        db.insert(TypeEntry.TABLE_NAME, //表名
                null,//当插入空行或者contentvalues为空时替代的空数据内容
                contentValues);//插入的值
        contentValues.put(TypeEntry.COLUMMNS_NAME_TYPE, "电话");
        contentValues.put(TypeEntry.COLUMMNS_NAME_SUBTABLE, "localservice");
        //向数据库中插入值
        db.insert(TypeEntry.TABLE_NAME, //表名
                null,//当插入空行或者contentvalues为空时替代的空数据内容
                contentValues);//插入的值
        //更新数据库
        // updateDatabase();
        //读取数据库的内容
        readDatabase();
        //  删除数据库中的数据
        // deleteDatabase();
    }

    /**
     * 从数据库中读取数据
     */
    private void readDatabase() {
        //根据自定义的帮助类获取读取数据库的database对象
        SQLiteDatabase db = mHelper.getReadableDatabase();
        //查询数据库
        Cursor c = db.query(
                TypeEntry.TABLE_NAME, //表名
                new String[]{TypeEntry.COLUMMNS_NAME_TYPE},
                null,
                null,
                null,
                null,
                null
        );
        c.moveToFirst();
        String temp = "";
        do {
            //拼接制定下标查询到的字符串
            temp += c.getString(
                    //返回当前行指定列的下标
                    c.getColumnIndexOrThrow(TypeEntry.COLUMMNS_NAME_TYPE)
            )
                    + "\n";
        } while (c.moveToNext());
        //显示在UI上
        tv_database.setText(temp);
    }

    /**
     * 从数据库中删除数据
     */
    private void deleteDatabase() {
        //根据帮助类获取可写的database对象
        SQLiteDatabase db = mHelper.getWritableDatabase();
        //从数据库中删除第三行
        db.delete(TypeEntry.TABLE_NAME, TypeEntry._ID + "=3",
                null
        );

    }

    //
//    /**
//     * 跟新数据库中的数据
//     */
    private void updateDatabase() {
        //根据帮助类获取可写的database对象
        SQLiteDatabase db = mHelper.getWritableDatabase();
        //准备跟新入表内的值
        ContentValues values = new ContentValues();
        values.put(TypeEntry.COLUMMNS_NAME_TYPE, "公共服务");
        //跟新数据库中_id为4的行中数据
        db.update(TypeEntry.TABLE_NAME,//表名
                values,//放入的值
                TypeEntry._ID + "=1 or " + TypeEntry._ID
                        + "=2", null
        );
    }
    /**
     * @description 申请运行权限
     */
    private void requestPermission(){
        //检查是否拥有外部存储器写权限
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            //申请WRITE_EXTERNAL权限
            ActivityCompat.requestPermissions(this, new  String[]{ Manifest.permission.WRITE_EXTERNAL_STORAGE }
                    ,WRITE_EXTERNAL_STORAGE);
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //判断申请码
        switch(requestCode){
            case WRITE_EXTERNAL_STORAGE:
                if (grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    //申请的第一个权限成功后
                }else{
                    //申请的第一个权限失败后
                    finish();
                }
                break;
        }
    }
}

