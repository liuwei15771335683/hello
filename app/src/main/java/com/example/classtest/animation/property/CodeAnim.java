package com.example.classtest.animation.property;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.FloatEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.classtest.R;
import com.example.classtest.animation.ShapeHolder;
import com.example.classtest.animation.drawableanim.DrawableAnim;
import com.example.classtest.animation.viewanim.ViewAnim;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Neal 2016-08-23
 *         用来展示在代码中实现属性动画的demo类
 */

public class CodeAnim extends Activity implements View.OnClickListener {

    //布局容器
    LinearLayout ll_container;
    //切换至XML实现属性动画的按钮
    Button bt_xml;
    //切换至补间动画的按钮
    Button bt_view;
    //切换至帧动画的按钮
    Button bt_drawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去掉标题栏和状态栏必须置于setContentView上面
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_anim);


        //装载控件
        initView();

        //ValueAnimator
        ValueAnimTest();
    }

    /**
     * 装载View控件
     */
    private void initView() {
        //所有控件的盒子
        ll_container = (LinearLayout) findViewById(R.id.ll_container);
        //自定义的动画控件
        MyAnimView myAnim = new MyAnimView(this);
        //把动画控件加入到控件盒子中
        ll_container.addView(myAnim);

        //设置跳转到XMLAnim的监听
        bt_xml = (Button) findViewById(R.id.bt_xml);
        bt_xml.setOnClickListener(this);

        //设置跳转到补间动画的监听
        bt_view = (Button) findViewById(R.id.bt_view);
        bt_view.setOnClickListener(this);

        //设置跳转到帧动画的监听
        bt_drawable = (Button) findViewById(R.id.bt_drawable);
        bt_drawable.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;//用来切换Acitivity的Intent
        switch (v.getId()) {
            case R.id.bt_xml://点击Start XML Anim
                intent = new Intent(CodeAnim.this, XMLAnim.class);//当前Activity跳转到XMLAnim
                startActivity(intent);//启动Acitivity
                break;
            case R.id.bt_view://点击Start View Anim
                intent = new Intent(CodeAnim.this, ViewAnim.class);//当前Activity跳转到ViewAnim
                startActivity(intent);//启动Acitivity
                break;
            case R.id.bt_drawable://点击Start Drawable Anim
                intent = new Intent(CodeAnim.this, DrawableAnim.class);//当前Activity跳转到DrawableAnim
                startActivity(intent);//启动Acitivity
                break;
        }
    }

    class MyAnimView extends View {
        int RED = 0xffff8080;//浅红
        int BLUE = 0xff8080ff;//浅蓝
        ShapeHolder shapeHolder;
        List<ShapeHolder> balls = new ArrayList<ShapeHolder>();


        //实现一个简单的默认构造器（重写View必须实现一个构造器）
        public MyAnimView(Context context) {
            super(context);
            //创建动画，改变对象的background属性，值会在RED和BLUE之间计算
            ValueAnimator anim = ObjectAnimator.ofInt(this, "backgroundColor", RED, BLUE);
            //动画的播放时间为3000ms
            anim.setDuration(3000);
            //设置动画的重复次数
            anim.setRepeatCount(ValueAnimator.INFINITE);
            //设置动画重复模式
            anim.setRepeatMode(ValueAnimator.REVERSE);
            //设置颜色计算器
            anim.setEvaluator(new ArgbEvaluator());
            //开始动画
            anim.start();

        }

        //自定义view的触摸逻辑
        @Override
        public boolean onTouchEvent(MotionEvent event) {
            //如果触摸事件为点击或移动
            if (event.getAction() == MotionEvent.ACTION_DOWN ||
                    event.getAction() == MotionEvent.ACTION_MOVE) {
                //引用添加的小球
                ShapeHolder ball = addBalls(event.getX(), event.getY());
                //把小球存储到容器中
                balls.add(ball);
                //创建小球下落的动画
                int h = getHeight();//整个view的高
                float currY = event.getY();//当前点击Y的位置
                float currX = event.getX();//当前点击X的位置
                float currWidth = ball.getWidth();//当前的宽度
                float currHieght = ball.getHeight();//当前的高度
                float endY = h - 50f;//view的高度减去小球的高度+50f
                long duration = 500;//从屏幕顶端到底端的掉落时间
                long realDuration = (long) ((h - shapeHolder.getY()) / h * duration);//从屏幕任意位置掉落的真实持续时间

                //小球下落的动画
                ValueAnimator downAnim = ObjectAnimator.ofFloat(shapeHolder,
                        "y", currY, endY);//创建控制y属性的属性动画
                downAnim.setDuration(realDuration);//设置整个动画的持续时间
                downAnim.setInterpolator(new AccelerateInterpolator());//设置一个加速的插值


                //小球压缩的动画
                //1.左移小球的x坐标
                ValueAnimator squash1Anim = ObjectAnimator.ofFloat(shapeHolder,
                        "x", currX, currX - 50f);//创建控制x属性的属性动画,左移50像素
                squash1Anim.setDuration(realDuration / 4);//设置整个动画的持续时间,比掉落时间要短
                squash1Anim.setInterpolator(new DecelerateInterpolator());//设置一个减速的插值（越来越难压）
                //小球回弹
                squash1Anim.setRepeatCount(1);//让它循环播放一次
                squash1Anim.setRepeatMode(ValueAnimator.REVERSE);//反序播放

                //小球压缩的动画
                //2.增加小球的宽度
                ValueAnimator squash2Anim = ObjectAnimator.ofFloat(shapeHolder,
                        "width", currWidth, currWidth + 50f);//创建控制width属性的属性动画,增加50像素
                squash2Anim.setDuration(realDuration / 4);//设置整个动画的持续时间,比掉落时间要短
                squash2Anim.setInterpolator(new DecelerateInterpolator());//设置一个减速的插值（越来越难压）
                //小球回弹
                squash2Anim.setRepeatCount(1);//让它循环播放一次
                squash2Anim.setRepeatMode(ValueAnimator.REVERSE);//反序播放

                //小球压缩的动画
                //3.降低小球
                ValueAnimator strech1Anim = ObjectAnimator.ofFloat(shapeHolder,
                        "y", endY, endY + 50f);//创建控制y属性的属性动画,增加50像素
                strech1Anim.setDuration(realDuration / 4);//设置整个动画的持续时间,比掉落时间要短
                strech1Anim.setInterpolator(new DecelerateInterpolator());//设置一个减速的插值（越来越难压）
                //小球回弹
                strech1Anim.setRepeatCount(1);//让它循环播放一次
                strech1Anim.setRepeatMode(ValueAnimator.REVERSE);//反序播放

                //小球压缩的动画
                //4.减少小球的高度
                ValueAnimator strech2Anim = ObjectAnimator.ofFloat(shapeHolder,
                        "height", currHieght, currHieght - 50);//创建控制height属性的属性动画,增加50像素
                strech2Anim.setDuration(realDuration / 4);//设置整个动画的持续时间,比掉落时间要短
                strech2Anim.setInterpolator(new DecelerateInterpolator());//设置一个减速的插值（越来越难压）
                //小球回弹
                strech2Anim.setRepeatCount(1);//让它循环播放一次
                strech2Anim.setRepeatMode(ValueAnimator.REVERSE);//反序播放

                //小球上升的动画
                ValueAnimator upAnim = ObjectAnimator.ofFloat(shapeHolder,
                        "y", endY, currY);//创建控制y属性的属性动画
                upAnim.setDuration(realDuration);//设置整个动画的持续时间
                upAnim.setInterpolator(new DecelerateInterpolator());//设置一个减速的插值

                //小球消失的动画
                ValueAnimator fadeAnim = ObjectAnimator.ofFloat(shapeHolder,
                        "alpha", 1.0f, 0.0f);//创建控制透明度属性的属性动画
                fadeAnim.setDuration(realDuration / 4);//设置整个动画的持续时间
                fadeAnim.setInterpolator(new LinearInterpolator());//设置一个线性的插值
                //添加一个动画播放结束后的监听
                fadeAnim.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        //把动画完成后的小球移出容器
                        balls.remove(((ObjectAnimator) animation).getTarget());
                    }
                });

                //创建第一组弹球运动的动画
                AnimatorSet bouncerSet = new AnimatorSet();
                bouncerSet.play(downAnim).before(squash1Anim);//在squash1Anim之前播放downAnim
                bouncerSet.play(squash1Anim).with(squash2Anim);//在播放squash1Anim时播放squash2Anim
                bouncerSet.play(squash1Anim).with(strech1Anim);//在播放squash1Anim时播放strech1Anim
                bouncerSet.play(squash1Anim).with(strech2Anim);//在播放squash1Anim时播放strech2Anim
                bouncerSet.play(upAnim).after(strech2Anim);//在播放strech2Anim之后播放upAnim
                //练习多个Animator嵌套
                AnimatorSet animSet = new AnimatorSet();
                animSet.play(fadeAnim).after(bouncerSet);//在bouncerSet之后播放fadeAnim
                animSet.start();//启动动画
            }

            return true;//return true代表触摸事件已经被截取处理完毕
        }

        //绘制自定义的内容只需要重写onDraw方法
        @Override
        protected void onDraw(Canvas canvas) {
            //移动画布至指定的区域
            for (ShapeHolder ball : balls) {
                //canvas.save();与canvas.retore();之间的代码为一帧的绘制
                canvas.save();
                //把画布移动到小球的坐标，默认坐标点是左上角，所以要加偏移量挪到中心
                canvas.translate(ball.getX() - 50f, ball.getY() - 50f);
                ball.getShape().draw(canvas);
                canvas.restore();
                //canvas.save();与canvas.retore();之间的代码为一帧的绘制
            }
        }

        /**
         * 根据坐标值向屏幕中添加小球
         *
         * @param x 屏幕横坐标
         * @param y 屏幕纵坐标
         */
        private ShapeHolder addBalls(float x, float y) {
            //获得随机的RGB值
            int RED = (int) (Math.random() * 255) << 16;
            int GREEN = (int) (Math.random() * 255) << 8;
            int BLUE = (int) (Math.random() * 255);
            //求上面三个集合的并集
            int COLOR = 0xff000000 | RED | GREEN | BLUE;
            //计算一个较深的颜色作为环形放射周围的颜色
            int DARKCOLOR = 0xff000000 | (RED / 4) | (GREEN / 4) | (BLUE / 4);

            //椭圆
            OvalShape circle = new OvalShape();
            //设置椭圆的宽度和和高度
            circle.resize(100f, 100f);
            //创建ShapDrawable
            ShapeDrawable shapeDrawable = new ShapeDrawable(circle);
            //创建自己的ShapDrawable
            shapeHolder = new ShapeHolder(shapeDrawable);
            //设置绘制图形的颜色
            shapeHolder.setColor(COLOR);
            //设置图形的坐标
            shapeHolder.setX(x);
            shapeHolder.setY(y);
            //设置图形的环形放射颜色
            RadialGradient gradient = new RadialGradient(
                    75f, 25f, 100, COLOR, DARKCOLOR, Shader.TileMode.CLAMP);
            shapeHolder.setGradient(gradient);
            //返回设置好的图形
            return shapeHolder;
        }

    }

    /**
     * 测试ValueAnimator
     */
    private void ValueAnimTest() {
        //声明一个动态属性值为float的属性动画，属性值在0~1间计算
        ValueAnimator animation = ValueAnimator.ofFloat(0f, 1f);
        //设置动画的播放时间为1000ms
        animation.setDuration(1000);
        //设置动画属性的计算器
        animation.setEvaluator(new FloatEvaluator());
        //设置动画的加速插值器
        animation.setInterpolator(new AccelerateInterpolator());
        //添加值计算时的监听
        animation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //获取动画值计算途中的值
                Log.i("lichao", (float) animation.getAnimatedValue() + "");
            }
        });
        //开始动画
        animation.start();
    }

}
