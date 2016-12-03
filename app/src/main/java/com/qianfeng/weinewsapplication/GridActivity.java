package com.qianfeng.weinewsapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.qianfeng.adapter.GrideViewpagerAdapter;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class GridActivity extends AppCompatActivity {

    @ViewInject(R.id.welcomPic)ImageView welcomPic;
    @ViewInject(R.id.welcomViewPager)ViewPager welcomViewPager;
    private SharedPreferences sharedPreferences;
    private Integer[] imgs = {R.mipmap.guide_1,R.mipmap.guide_2,R.mipmap.guide_3,R.mipmap.guide_4};
    private ImageView[] imgViews = new ImageView[4];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid);

        x.view().inject(this);

        welcomPager();
    }
    public void welcomPager(){
        new CountDownTimer(3000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Toast.makeText(GridActivity.this, "_(:з」∠)1", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFinish() {
                Toast.makeText(GridActivity.this, "_(:з」∠)2", Toast.LENGTH_SHORT).show();
                welcomPic.setVisibility(View.GONE);
                welcomViewPager.setVisibility(View.VISIBLE);
                gridePager();
            }
        }.start();
    }
    public void gridePager(){
        sharedPreferences = getSharedPreferences("sp_data", Context.MODE_PRIVATE);
        //
        switch (sharedPreferences.getInt("sp_num",-1)){
            case -1:
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("sp_num",1);
                editor.commit();
                //
                for (int i = 0;i < 4;i++){
                    ImageView imageView = new ImageView(this);
                    imageView.setImageResource(imgs[i]);
                    imgViews[i]=imageView;
                }
                //touch监听 滑动距离实现跳转
                imgViews[3].setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (touchCount==0){
                            touchCount = 1;
                            xStart = (int) event.getX();
                        }else {
                            touchCount = 0;
                            xEnd = (int) event.getX();
                        }
                        if (xStart-xEnd>20){
                            //Toast.makeText(GridActivity.this, "_(:з」∠)_", Toast.LENGTH_SHORT).show();
                            finishActivity();
                        }

                        return false;
                    }
                });
                
                //
                GrideViewpagerAdapter adapter = new GrideViewpagerAdapter(imgViews);
                welcomViewPager.setAdapter(adapter);
                //
                

                break;
            case 1:
                finishActivity();
                break;
        }
    }
    //
    private int xStart = 0;
    private int xEnd = 0;
    private int touchCount = 0;


    public void finishActivity(){
        Intent intent = new Intent(this,MainActivity.class);

        intent.putExtra("flag",2);

        startActivity(intent);

        this.finish();

    }

}
