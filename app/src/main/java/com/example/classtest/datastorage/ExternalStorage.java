package com.example.classtest.datastorage;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.classtest.BaseActivity;
import com.example.classtest.R;

import java.io.File;

public class ExternalStorage extends BaseActivity implements View.OnClickListener {
    //创建公共外部存储器相册目录按钮
    Button bt_mkAlbum;
    //创建私有外部存储器手机硬盘目录按钮
    Button bt_mkPrivate;
    //创建私有外部存储器SD卡目录按钮
    Button bt_mkPrivatePhone;

    //自定义相册文件名
    private static final String ALBUM_NAME = "MyAlbum";
    //自定义外部存储器私有文件名
    private static final String PRIVATE_FILE = "MyPrivateFile";
    //自定义外部存储器手机硬件私有文件名
    private static final String PRIVATE_FILE_PHONE = "MyPrivateFilePhone";


    @Override
    protected int setContent() {
        return R.layout.activity_external_storage;
    }

    @Override
    protected void initView() {
        bt_mkAlbum = (Button) findViewById(R.id.bt_mkAlbum);
        bt_mkPrivate = (Button) findViewById(R.id.bt_Private);
        bt_mkPrivatePhone = (Button) findViewById(R.id.bt_mkPrivatePhone);
    }

    @Override
    protected void setListener() {
        bt_mkAlbum.setOnClickListener(this);
        bt_mkPrivate.setOnClickListener(this);
        bt_mkPrivatePhone.setOnClickListener(this);
    }

    /**
     * 检查设备可读状态
     *
     * @param
     */
    private boolean checkReadState() {
        //获得设备当前状态
        String state = Environment.getExternalStorageState();
        //判断是否可读
        if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state) || Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /**
     * 检查设备可写状态
     *
     * @param
     */
    private boolean checkWriteState() {
        //获得设备当前状态
        String state = Environment.//外部状态
                getExternalStorageState();//外部存储器的状态
        //判断是否可读
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /**
     * @param fileName; 创建一个相册目录下的文件
     */
    private void createAlbum(String fileName) {
        //检查设备可写状态
        if (checkWriteState()) {
            //根据相册目录创建一个File对象
            File file = new File(
                    Environment.getExternalStoragePublicDirectory
                            (Environment.DIRECTORY_PICTURES), fileName);
            //创建一个多级目录，返回成功或者失败的布尔值
            if (!file.exists()&&!file.mkdirs()) {
                //若失败，提示
                Toast.makeText(this, "Create directory failed! ",
                        Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this,"Create directory success!"
                        ,Toast.LENGTH_SHORT).show();

            }

        }
    }
    /**
     * @param fileName 目录或文件名
     * @description 创建一个私有外部存储目录（一般只在手机硬盘中）
     *
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private  void createPrivate(String fileName){
        //检查设备可写状态
        if (checkWriteState()){
            //根据相册目录创建一个File对象
            File file =new File(
                    getExternalFilesDirs(null)[0],fileName);//0为一般手机硬盘，1一般为SD卡，这里可能会根据设备类型产生差异
        //创建多级目录，返回成功或者失败的布尔值
        if (!file.exists()&&!file.mkdirs()){
        //若失败，提示
            Toast.makeText(this,"Create directory failed!"
                    ,Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this,"Create directory success!"
                ,Toast.LENGTH_SHORT).show();

        }
        }

    }
    /**
     * 目录或文件名
     * 创建一个私有外部存储器（手机硬盘中）
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void createPrivaiePhone(String fileName){
        //检查设备可写状态
        if (checkWriteState()){
            //根据相册目录创建一个File对象
            File file = new File(getExternalFilesDirs(null)[0],  // 0为一般手机硬盘，1.一般为SD卡，这里可能会根据设备类型产生差异
                    fileName);
            //创建一个多级目录，返回成功或者失败的布尔值
            if (!file.exists()&&!file.mkdirs()){
                //若失败，提示
                Toast.makeText(this,"Create directory failed！",Toast.LENGTH_SHORT).show();

            }else{
                Toast.makeText(this,"Create directory success!"
                        ,Toast.LENGTH_SHORT).show();

            }

        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_mkAlbum:  //创建公有相册目录
            createAlbum(ALBUM_NAME);
                break;
            case R.id.bt_Private: //创建私有外部存储器目录（手机硬盘和SD中）
             createPrivate(PRIVATE_FILE);
                break;
            case R.id.bt_mkPrivatePhone://创建私有外部存储器目录（手机SD卡）
                    createPrivaiePhone(PRIVATE_FILE_PHONE);
                break;
        }

    }
}