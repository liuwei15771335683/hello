package com.example.classtest.datastorage;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.classtest.BaseActivity;
import com.example.classtest.R;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * @author Neal 2016-08-26
 * @description 内部存储器demo类
 */
public class InternalStorage extends BaseActivity {

    //运行期的内部存储器文件数据
    TextView tv_internalFile;
    //编译期的内部存储器文件数据
    TextView tv_rawFile;
    //运行期内部存储器文件文件名
    String FILE_NAME = "internal file";
    //运行期内部存储器文件数据
    String FILE_DATA = "Hello internal file!";

    @Override
    protected int setContent() {
        return R.layout.activity_internal_storage;
    }

    @Override
    protected void initView() {
        tv_internalFile = (TextView) findViewById(R.id.tv_internalFile);
        tv_rawFile = (TextView) findViewById(R.id.tv_rawFile);

        writeFile(FILE_NAME);//创建internal file文件
        readFile(FILE_NAME);//读取internal file文件
    }

    @Override
    protected void setListener() {

    }

    /**
     * @param fileName 写入文件的文件名
     * @description 向内部存储器中写入文件
     */
    private void writeFile(String fileName) {
        try {
            //创建或者替换一个文件，第一个参数为文件名，第二个参数为文件模式
            FileOutputStream fop = openFileOutput(fileName, MODE_PRIVATE);
            //向文件中写入数据
            fop.write(FILE_DATA.getBytes("UTF-8"));
            //关闭数据流
            fop.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * @param fileName 读取文件的文件名
     * @description 读取内部存储器中的文件
     */
    private void readFile(String fileName) {
        //运行期文件
        try {
            //创建读取文件的输入流
            FileInputStream fip = openFileInput(FILE_NAME);
            //创建保存数据的临时字符
            String temp = "";
            //创建缓冲区
            byte[] buffer = new byte[1024];
            //向缓冲区中循环读入数据，直至数据读完（读到-1时）
            while (fip.read(buffer) != -1) {
                //通过byte数组来拼接字符数据
                temp += new String(buffer);
            }
            //关闭数据流
            fip.close();
            //更新在UI上
            tv_internalFile.setText(temp);

            //编译期文件
            //创建读取raw文件下文件的输入流
            InputStream ip = getResources().openRawResource(R.raw.rawfile);
            //创建保存数据的临时字符
            temp = "";
            //创建缓冲区
            buffer = new byte[1024];
            //向缓冲区中循环读入数据，直至数据读完（读到-1时）
            while (ip.read(buffer) != -1) {
                //通过byte数组来拼接字符数据
                temp += new String(buffer);
            }
            //关闭数据流
            ip.close();
            //更新在UI上
            tv_rawFile.setText(temp);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
