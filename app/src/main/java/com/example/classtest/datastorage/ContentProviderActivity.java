package com.example.classtest.datastorage;


import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.classtest.BaseActivity;
import com.example.classtest.R;

/**
 * Created by L on 2016/9/6.
 */
public class ContentProviderActivity extends BaseActivity {
    //用来显示获取的数据内容
    TextView tv_data;

    //用来获取用户输入的编辑框
    EditText et_search;
    //执行查找的按钮
    Button bt_search;

    @Override
    protected int setContent() {

        return R.layout.acticity_content_provider;
    }

    @Override
    protected void initView() {
        tv_data = (TextView)findViewById(R.id.tv_data);
        et_search=(EditText)findViewById(R.id.et_search);
        bt_search=(Button)findViewById(R.id.bt_search);
        //queryImage();
    }

    @Override
    protected void setListener() {

    }
    /**
     * @description 使用ContentProvider的方式查询手机中的图片信息
     * @param view 点击的视图
     */
    public void queryImage(View view){
        //查询条件语句
        String mSlectionClause = null ;
        //查询条件的参数
        String [] mSlectionArgs = {""};
        //用户输入的查询关键词
        String mSearch;
        //构造查询的投影
        String [] mprojection = new String[]{
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.DISPLAY_NAME // 图片显示的名字
        };
        //如若查询条件（WHERE）为空，则返回所有的列
        if (TextUtils.isEmpty(mSearch=et_search.getText().toString())){
            //构造查询条件语句
            mSlectionClause = null ;
            //构造查询条件的参数
            mSlectionArgs = null;
        }else {
            //构造查询条件语句
            mSlectionClause = MediaStore.Images.Media._ID + " =? ";
            //构造查询条件的参数
            mSlectionArgs[0] = mSearch;
        }
        //查询指定uri的ContentProvider,返回一个游标
        Cursor mCursor = getContentResolver().query(
              MediaStore.Images.Media.EXTERNAL_CONTENT_URI,//用来查询公共图片的URI（相当于一个表名）
              mprojection,
              mSlectionClause,
              mSlectionArgs,
              null
        );
        //如若游标没有正确实例化
        if (mCursor == null){
            //打印一条错误的日志
            Log.e("liuwei","您根据ID查询的照片neir并没有正确的实例化！");
        }
        //若查询不到任何行
        else if(mCursor.getCount() < 1){
            //弹出一个提示
            Toast.makeText(this,"没有匹配的数据，请重新输入！",Toast.LENGTH_SHORT).show();
        }
        //如果正确返回了数据
        else{
            //确定需要的数据的小标，减少IndexOrThrow的调用，提高效率
            int idIndex = mCursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID);
            int displayIndex = mCursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME);
            //游标移动到第一行，否则游标的位置会在-1停留
            mCursor.moveToFirst();
            //用来存储临时拼接数据的字符串
            String temp = "";
            do{
                String id = mCursor.getString(idIndex);
                String display = mCursor.getString(displayIndex);
                temp += "id =" +id +"\n" +" idsplay =" +display +" \n\n";
            }while (mCursor.moveToNext());
            //关闭游标
            mCursor.close();
            //设置UI
            tv_data.setText(temp);
        }


    }




    /**
     * @description 使用ContentProvider的方式查询手机中的图片信息
     */
    private  void queryImage(){
        //查询指定的uri的ContentProvider,返回一个游标
        Cursor mCursor = getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,//用来查询公共图片的URI
                //相当于一个表名 （）
                null,//相当于COLUMNS,这里null指的是返回整张表
                null,//相当于Where
                null,//相当于WHERE ARGS
                null
        );
        //判断游标状态，不为空且有数据是开始遍历
        if (mCursor!= null&&mCursor.getCount()>0){
            //确定需要的数据下表，减少IndexOrThrow的调用,提高效率
            int idIndex = mCursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID);
            int heightIndex = mCursor.getColumnIndexOrThrow(MediaStore.Images.Media.HEIGHT);
            //游标移动到第一行，否则油表位置会在-1停留
            mCursor.moveToFirst();
            //用来存储临时拼接数据的字符串
            String temp="";
            do {
                String id = mCursor.getString(idIndex);
                String height = mCursor.getString(heightIndex);
                temp +="id ="+id +"\n" + "height ="+height+"\n\n";
            }while (mCursor.moveToNext());
            //关闭游标
            mCursor.close();
            //设置UI
            tv_data.setText(temp);
        }

    }
    /**
     * @description 使用ContentProvider的方式查询手机中的单张图片信息
     */
    private void querySingleImage(){
        //指定查询某一行的URI

        Uri singleUri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,69);
        //查询指定uri的ContentProvider,返回一个游标
        Cursor mCursor =getContentResolver().query(
                singleUri,//用来查询公共图片的URI（相当于一个表名）
                null,
                null,
                null,
                null
        );
        //判断游标状态，不为空且有数据时开始遍历
        if (mCursor != null&&mCursor.getCount() >0){
            //确定需要的数据下标 ，减少IndexOrThrow的调用提高效率
            int idIndex =mCursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID);
            int heightIndex = mCursor.getColumnIndexOrThrow(MediaStore.Images.Media.HEIGHT);
            //游标移动到第一行，否则游标的位置会在—1停留
            mCursor.moveToFirst();
            //用来存储临时拼接数据的字符串
            String temp ="" ;
            do {
                String id =mCursor.getString(idIndex);
                String height=mCursor.getString(heightIndex);
                temp += "id= "+id+"\n"+"height= "+height +"\n\n";
            }while (mCursor.moveToNext());
            //g关闭游标
            mCursor.close();
            //设置UI
            tv_data.setText(temp);
        }

    }






}
