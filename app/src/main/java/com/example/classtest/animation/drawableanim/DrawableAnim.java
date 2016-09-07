package com.example.classtest.animation.drawableanim;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.classtest.R;

/**
 * @author Neal 2016-08-25
 *         用来展示在XML和代码中实现DrawableAnimation的demo类
 */
public class DrawableAnim extends Activity {
    ImageView iv_fish_XML;//通过XML实现帧动画的ImageView
    ImageView iv_fish_code;//通过代码实现帧动画ImageView
    AnimationDrawable xmlAnim;//通过XML实现帧动画的实例
    AnimationDrawable codeAnim;//通过代码实现帧动画的实例

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去掉标题栏和状态栏必须置于setContentView上面
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_drawable_anim);

        initView();//装载view
        initAnimation();//装载动画
    }

    /**
     * 装载控件
     */
    private void initView() {
        iv_fish_XML = (ImageView) findViewById(R.id.iv_fish_XML);
        iv_fish_code = (ImageView) findViewById(R.id.iv_fish_code);
    }

    /**
     * 装载动画
     */
    private void initAnimation() {
        //XML方式
        //把动画的XML设置成ImageView的背景，默认情况下显示第一帧
        iv_fish_XML.setBackgroundResource(R.drawable.fishanim);
        //获得AnimationDrawable实例
        xmlAnim = (AnimationDrawable) iv_fish_XML.getBackground();

        //代码方式
        codeAnim = new AnimationDrawable();
        //逐一添加帧
        codeAnim.addFrame(getResources().getDrawable(R.drawable.fish_01), 200);
        codeAnim.addFrame(getResources().getDrawable(R.drawable.fish_02), 200);
        codeAnim.addFrame(getResources().getDrawable(R.drawable.fish_03), 200);
        codeAnim.addFrame(getResources().getDrawable(R.drawable.fish_04), 200);
        codeAnim.addFrame(getResources().getDrawable(R.drawable.fish_05), 200);
        codeAnim.addFrame(getResources().getDrawable(R.drawable.fish_06), 200);
        codeAnim.addFrame(getResources().getDrawable(R.drawable.fish_07), 200);
        codeAnim.addFrame(getResources().getDrawable(R.drawable.fish_08), 200);
        codeAnim.addFrame(getResources().getDrawable(R.drawable.fish_09), 200);
        codeAnim.addFrame(getResources().getDrawable(R.drawable.fish_10), 200);
        codeAnim.addFrame(getResources().getDrawable(R.drawable.fish_11), 200);
        codeAnim.addFrame(getResources().getDrawable(R.drawable.fish_12), 200);
        codeAnim.addFrame(getResources().getDrawable(R.drawable.fish_13), 200);
        codeAnim.addFrame(getResources().getDrawable(R.drawable.fish_14), 200);
        codeAnim.addFrame(getResources().getDrawable(R.drawable.fish_15), 200);
        codeAnim.addFrame(getResources().getDrawable(R.drawable.fish_16), 200);
        //把动画的设置成ImageView的背景，默认情况下显示第一帧
        iv_fish_code.setBackground(codeAnim);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //每当点击屏幕
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            //停止动画
            xmlAnim.stop();
            //设置动画的当前帧为第一帧
            xmlAnim.selectDrawable(0);
            //启动动画
            xmlAnim.start();

            //停止动画
            codeAnim.stop();
            //设置动画的当前帧为第一帧
            codeAnim.selectDrawable(0);
            //启动动画
            codeAnim.start();
        }
        return true;
    }
}
