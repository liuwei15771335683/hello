package com.example.classtest.datastorage;

import android.app.Activity;
import android.app.Notification;
import android.os.Bundle;
import android.widget.TextView;

import com.example.classtest.BaseActivity;
import com.example.classtest.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * @author Neal 2016-08-26
 * @descriptin 缓存数据读写的demo
 */
public class CacheFile extends BaseActivity {
    //缓存文件数据内容
    TextView tv_cacheData;
    //缓存文件的文件名
    String FILE_NAME = "cache file";
    //缓存文件的数据
    String DATA = "Hello cache file!";

    @Override
    protected int setContent() {
        return R.layout.activity_cache_file;
    }

    @Override
    protected void initView() {
        tv_cacheData = (TextView) findViewById(R.id.tv_cacheData);

        writeFile(FILE_NAME);
        readFile(FILE_NAME);
        deleteCache(getCacheDir() + "/" + FILE_NAME);
    }

    @Override
    protected void setListener() {

    }

    /**
     * @param fileName 文件名
     * @description 写入一个缓存文件
     */
    private void writeFile(String fileName) {
        //根据缓存文件创建一个File
        File file = new File(getCacheDir(), fileName);

        try {
            //开启缓存文件上的输出流
            FileOutputStream fop = new FileOutputStream(file);
            //向输出流中写入数据
            fop.write(DATA.getBytes("UTF-8"));
            //关闭输出流
            fop.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * @param fileName 文件名
     * @description 读取一个缓存文件的数据
     */
    private void readFile(String fileName) {
        //根据缓存文件创建一个File
        File file = new File(getCacheDir(), fileName);

        try {
            //开启缓存文件上的输入流
            FileInputStream fip = new FileInputStream(file);
            //创建临时字符保存数据
            String temp = "";
            //数据缓冲区
            byte[] buffer = new byte[1024];
            try {
                //向缓冲区中读入数据，直至读到-1(数据读完)
                while (fip.read(buffer) != -1) {
                    temp += new String(buffer);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            //关闭输入流
            fip.close();
            //内部存储器文件系统绝对目录
            temp += " getFilesDir() = " + getFilesDir() + "\n\n";
            //创建或者打开一个内部存储空间的目录（相对路径）
            temp += "getDir = " + getDir("cache", MODE_PRIVATE) + "\n\n";
            //获得文件列表(缓存文件夹中的)
            File cachePath = getCacheDir();
            //遍历缓存文件夹下的文件，打印路径
            for (File cacheFile : cachePath.listFiles()) {
                temp += "fileList()'s file = " + cacheFile.getPath() + "\n";
            }

            //在UI上更新数据
            tv_cacheData.setText(temp);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * @description 删除某个缓存文件
     * @param fileName 删除文件的文件名
     */
    private void deleteCache(String fileName) {
        File file = new File(fileName);
        file.delete();
    }
}
