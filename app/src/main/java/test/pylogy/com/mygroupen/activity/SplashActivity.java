package test.pylogy.com.mygroupen.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.view.View;

import test.pylogy.com.mygroupen.R;
import test.pylogy.com.mygroupen.util.SPutil;


public class SplashActivity extends Activity {
    SPutil sPutil;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        if (Build.VERSION.SDK_INT>=21){
            View decorView=getWindow().getDecorView();
            int option=View.SYSTEM_UI_FLAG_LAYOUT_STABLE|View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        sPutil=new SPutil(this);
        //界面停留几秒钟
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent;
                if (true){
                    //向新手指导页跳转
                    sPutil.setFirst(false);
                    intent=new Intent(SplashActivity.this,GuideActivity.class);
                }else{
                    //向主页跳转
                    intent=new Intent(SplashActivity.this,MainActivity.class);
                }
                startActivity(intent);
                finish();
            }
        },3000);
        //根据是否是第一次使用进行相应的界面跳转；

    }
}
