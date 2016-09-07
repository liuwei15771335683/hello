package com.example.classtest.animation.viewanim;

import android.app.Activity;
import android.os.Bundle;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import com.example.classtest.R;
/**
 * @author Neal 2016-08-23
 *         用来展示在XML和代码中实现ViewAnimation的demo类
 */
public class ViewAnim extends Activity {

    //XML加载动画的TextView
    TextView tv_xml;
    //代码加载动画的TextView
    TextView tv_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_anim);
        initView();//控件
        initAnim();//动画
    }

    /**
     * 装载控件
     */
    private void initView() {
        tv_xml = (TextView) findViewById(R.id.tv_XMLView);
        tv_code = (TextView) findViewById(R.id.tv_codeView);
    }

    /**
     * 装载动画
     */
    private void initAnim() {
        //通过XML方式实现ViewAnimation
        //加载XML中的View Animation
        final Animation anim = AnimationUtils.loadAnimation(this, R.anim.viewanim);
        //设置动画状态监听
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //动画播放结束后重新开始播放
                tv_xml.startAnimation(animation);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        //启动动画
        tv_xml.startAnimation(anim);


        //通过代码的方式实现ViewAnimation
        //创建一个旋转动画
        RotateAnimation rotateAnimation = new RotateAnimation(
                0f, //开始角度
                360f, //结束角度
                Animation.RELATIVE_TO_SELF, //旋转点X轴的类型（相对于控件本身）
                .5f, //旋转点X轴的百分比（自身宽度的50%）
                Animation.RELATIVE_TO_SELF,//旋转点Y轴的类型（相对于控件本身）
                .5f//旋转点Y轴的百分比（自身宽度的50%）
        );
        //设置旋转的持续时间
        rotateAnimation.setDuration(1000);
        //设置旋转的插值器(加速)
        rotateAnimation.setInterpolator(new AccelerateInterpolator());

        //创建一个平移动画
        TranslateAnimation translateAnimation = new TranslateAnimation(
                0f,//起始X点的值
                400f,//到达X点的值
                0f,//起始Y点的值
                400f//到达Y点的类型（这里表示相对于控件本身的点）
        );
        //设置旋转的持续时间
        translateAnimation.setDuration(1000);
        //设置旋转的插值器(加速)
        translateAnimation.setInterpolator(new AccelerateInterpolator());
        //设置动画播放延迟
        translateAnimation.setStartOffset(1000);

        //创建缩放动画
        ScaleAnimation scaleAnimation = new ScaleAnimation(
                1.0f, //起始X轴的缩放值
                3.0f, //目标X轴的缩放值
                1.0f, //起始Y轴的缩放值
                3.0f, //目标Y轴的缩放值
                Animation.RELATIVE_TO_SELF, //缩放点X的类型(这里为相对于控件自身)
                .5f,//缩放点X的值(这里为相对于控件自身50%)
                Animation.RELATIVE_TO_SELF,//缩放点Y的类型(这里为相对于控件自身)
                .5f//缩放点Y的值(这里为相对于控件自身50%)
        );
        //设置缩放的持续时间
        scaleAnimation.setDuration(1000);
        //设置缩放的插值器(加速)
        scaleAnimation.setInterpolator(new AccelerateInterpolator());
        //设置动画播放延迟
        scaleAnimation.setStartOffset(1000);

        //创建透明度动画
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
        //设置透明的持续时间
        alphaAnimation.setDuration(1000);
        //设置透明的插值器(加速)
        alphaAnimation.setInterpolator(new AccelerateInterpolator());
        //alphaAnimation
        alphaAnimation.setStartOffset(1000);

        //创建两个动画集合
        final AnimationSet allSet = new AnimationSet(false);//总集合
        AnimationSet childSet = new AnimationSet(false);//子集合
        //向子集合中添加动画
        childSet.addAnimation(translateAnimation);//添加平移动画
        childSet.addAnimation(scaleAnimation);//添加缩放动画
        childSet.addAnimation(alphaAnimation);//添加透明度动画
        //向总集中添加动画
        allSet.addAnimation(rotateAnimation);//添加旋转动画
        allSet.addAnimation(childSet);//添加子集动画
        //添加动画播放完毕后的监听以实现动画循环
        allSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                ////启动动画的方式1
                //tv_code.startAnimation(allSet);

                //启动动画的方式2
                //停止动画后重设动画的状态
                allSet.reset();
                //重新播放动画
                allSet.start();

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        ////启动动画的方式1
        //tv_code.startAnimation(allSet);

        //启动动画的方式二
        //把动画设置给View（另一种启动动画的方式）
        tv_code.setAnimation(allSet);
        //启动动画
        allSet.start();


    }
}
