package com.example.classtest;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

/**
 * @author Neal
 * @description Acitivy的基类
 */
public abstract class BaseActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置无标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //初始化Activity
        setContentView(setContent());
        initView();
        setListener();
    }

    /**
     * @description 设置当前页的布局文件
     */
    protected abstract int setContent();

    /**
     * @description 加载控件
     */
    protected abstract void initView();

    /**
     * @description 设置监听
     */
    protected abstract void setListener();


}
