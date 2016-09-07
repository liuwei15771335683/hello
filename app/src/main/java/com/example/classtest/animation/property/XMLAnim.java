package com.example.classtest.animation.property;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.example.classtest.R;
import com.example.classtest.animation.ShapeHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Neal 2016-08-24
 *         用来展示在XML中实现属性动画的demo类
 *         其中也穿插了KeyFrame PropertyValuesHolder的使用方法
 */
public class XMLAnim extends Activity {
    //布局容器
    LinearLayout ll_container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //去掉标题栏和状态栏必须置于setContentView上面
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_xmlanim);


        //装载控件
        initView();
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
    }

    class MyAnimView extends View {
        ShapeHolder colorBall;//颜色变化，大小变化的小球
        int RED = 0xffff0000;//红
        int BLUE = 0xff0000ff;//蓝
        ShapeHolder shapeHolder;
        List<ShapeHolder> balls = new ArrayList<ShapeHolder>();//小球列表


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

            //创建颜色变化大小变化的小球
            addColorBall();

        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            //点击或滑动时添加小球
            if (event.getAction() == MotionEvent.ACTION_DOWN
                    || event.getAction() == MotionEvent.ACTION_MOVE) {
                //创建小球保存到ball中
                ShapeHolder ball = addBalls(event.getX(), event.getY());
                //把小球添加到列表中
                balls.add(ball);

                //加载XML属性动画
                AnimatorSet animSet =
                        (AnimatorSet) AnimatorInflater.loadAnimator(XMLAnim.this, R.animator.bouncer);
                //给XML属性动画设置执行对象
                animSet.setTarget(ball);
                //找到最后一组动画
                AnimatorSet lastSet =
                        (AnimatorSet) animSet.getChildAnimations().get(1);
                //找到最后一个动画
                ObjectAnimator lastAnim =
                        (ObjectAnimator) lastSet.getChildAnimations().get(1);
                //设置动画播放结束后的监听
                lastAnim.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        //获取属性对话的目标对象并从列表移除
                        balls.remove(((ObjectAnimator) animation).getTarget());
                    }
                });
                //开始动画
                animSet.start();

            }
            return true;
        }

        /**
         * 大小变动，颜色变化的小球
         */

        private void addColorBall() {
            //创建一个椭圆
            OvalShape circlr = new OvalShape();
            //设置圆的水平直径和垂直直径
            circlr.resize(200f, 200f);
            //创建可绘制图形
            ShapeDrawable shapeDrawable = new ShapeDrawable(circlr);
            //创建自定义图形容器
            colorBall = new ShapeHolder(shapeDrawable);
            //设置球的默认坐标
            colorBall.setX(0f);
            colorBall.setY(0f);

            //创建改变宽度的关键帧
            Keyframe kf0 = Keyframe.ofFloat(0f, 200f);//刚开始时设置为默认宽度
            Keyframe kf1 = Keyframe.ofFloat(.5f, 400f);//运行到一半时设置宽度为400f
            Keyframe kf2 = Keyframe.ofFloat(1f, 100f);//结束时宽度为100f
            //创建保存宽度关键帧的PropertyValuesHolder
            PropertyValuesHolder widthHolder = PropertyValuesHolder.ofKeyframe(
                    "width", kf0, kf1, kf2);

            //创建改变高度的关键帧
            Keyframe kf3 = Keyframe.ofFloat(0f, 200f);//刚开始时设置为默认高度
            Keyframe kf4 = Keyframe.ofFloat(.5f, 400f);//运行到一半时设置高度为400f
            Keyframe kf5 = Keyframe.ofFloat(1f, 100f);//结束时高度为100f
            //创建保存高度关键帧的PropertyValuesHolder
            PropertyValuesHolder heightHolder = PropertyValuesHolder.ofKeyframe(
                    "height", kf3, kf4, kf5);

            //创建改变颜色的关键帧
            Keyframe kf6 = Keyframe.ofInt(0f, 0xffff8080);//刚开始时颜色
            Keyframe kf7 = Keyframe.ofInt(.5f, 0xff80ff80);//运行中期颜色
            Keyframe kf8 = Keyframe.ofInt(1f, 0xff8080ff);//结束时颜色
            //创建保存颜色关键帧的PropertyValuesHolder
            PropertyValuesHolder colorHolder = PropertyValuesHolder.ofKeyframe(
                    "color", kf6, kf7, kf8);
            //设置颜色计算器
            colorHolder.setEvaluator(new ArgbEvaluator());

            //创建可以控制多个属性的ObjectAnimator
            ObjectAnimator colorBallAnim = ObjectAnimator.ofPropertyValuesHolder(
                    colorBall, widthHolder, heightHolder, colorHolder);
            //设置动画的运行时间3000ms
            colorBallAnim.setDuration(3000);
            //设置循环模式和循环次数
            colorBallAnim.setRepeatMode(ValueAnimator.REVERSE);
            colorBallAnim.setRepeatCount(ValueAnimator.INFINITE);
            //执行动画
            colorBallAnim.start();
        }

        //绘制自定义的内容只需要重写onDraw方法
        @Override
        protected void onDraw(Canvas canvas) {


            //绘制小球列表
            for (ShapeHolder ball : balls) {
                //canvas.save();与canvas.retore();之间的代码为一帧的绘制
                canvas.save();
                canvas.translate(ball.getX(), ball.getY());//移动画布至目标位置
                ball.getShape().draw(canvas);//绘制小球
                canvas.restore();
                //canvas.save();与canvas.retore();之间的代码为一帧的绘制
            }


            //canvas.save();与canvas.retore();之间的代码为一帧的绘制
            canvas.save();
            canvas.translate(colorBall.getX(), colorBall.getY());//移动画布至目标位置
            colorBall.getShape().draw(canvas);
            canvas.restore();
            //canvas.save();与canvas.retore();之间的代码为一帧的绘制

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
}
