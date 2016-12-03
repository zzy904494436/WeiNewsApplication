package com.qianfeng.weinewsapplication;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.qianfeng.fragment.DiscoverFragment;
import com.qianfeng.fragment.HomeFragment;
import com.qianfeng.fragment.MyselfFragment;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

public class MainActivity extends AppCompatActivity {

    @ViewInject(R.id.radioGroupTab)RadioGroup radioGroupTab;
    @ViewInject(R.id.fragmentLayout)LinearLayout fragmentLayout;
    @ViewInject(R.id.radioButtonDiscover)RadioButton radioButtonDiscover;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    private int i = 0;
    private int flag1 = 1;
    private int flag2 = 1;
    private long timeOnce = 0;
    private long timeSeconds = 0;
    private int flagDiscovers = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        x.view().inject(this);
        //字体颜色大小
        drawbleSetBounds();
        //
        Intent intent = getIntent();
        flagDiscovers = intent.getIntExtra("flag",-1);
        if (flagDiscovers==1){
            fragmentManager = getSupportFragmentManager();
            transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.fragmentLayout,new DiscoverFragment());
            transaction.commit();
            //radioButtonHome.setChecked(false);
            radioButtonDiscover.setChecked(true);
        }else {
            fragmentManager = getSupportFragmentManager();
            transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.fragmentLayout,new HomeFragment());
            transaction.commit();
        }

        radioGroupTab.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.radioButtonHome:
                        transaction = fragmentManager.beginTransaction();
                        transaction.replace(R.id.fragmentLayout,new HomeFragment());
                        break;
                    case R.id.radioButtonDiscover:
                        transaction = fragmentManager.beginTransaction();
                        transaction.replace(R.id.fragmentLayout,new DiscoverFragment());
                        break;
                    case R.id.radioButtonMyself:
                        transaction = fragmentManager.beginTransaction();
                        transaction.replace(R.id.fragmentLayout,new MyselfFragment());
                        break;
                }
                transaction.commit();
            }
        });


    }

    /*RadioButton图片大小 字的颜色变化*/
    public void drawbleSetBounds(){
        int[] radioButtonDrawables = {R.drawable.radiobutton_home_select,R.drawable.radiobutton_find_select,R.drawable.radiobutton_personal_select};
        for (int i = 0; i< radioGroupTab.getChildCount(); i ++){

            RadioButton radioButton = (RadioButton) radioGroupTab.getChildAt(i);
            Drawable drawable = getResources().getDrawable(radioButtonDrawables[i]);
            drawable.setBounds(0,0,70,70);
            radioButton.setCompoundDrawables(null,drawable,null,null);
        }
        radioGroupTab.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.radioButtonHome:
                }
            }
        });
    }

    //完结撒花_(:з」∠)_
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==event.KEYCODE_BACK){
            Toast.makeText(this, "再按一次 退出程序", Toast.LENGTH_SHORT).show();
            //监听两次点击之间的系统时间差  用Math.abs 获取绝对值 因为不一定哪个是先点哪个是后点
            if(flag1==1){
                timeOnce = System.currentTimeMillis();
                flag1=2;
                if(Math.abs(timeSeconds-timeOnce)<1000&&flag2==3){
                    this.finish();
                }
                //flag2=3表示不是第一次点击返回键 bu
                flag2=3;
                return true;
            }else if(flag1==2){
                timeSeconds = System.currentTimeMillis();
                flag1=1;
                if(Math.abs(timeSeconds-timeOnce)<1000&&flag2==3){
                    this.finish();
                }
                return true;
            }

        }
        return super.onKeyDown(keyCode, event);
    }
}
