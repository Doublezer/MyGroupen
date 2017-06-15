package test.pylogy.com.mygroupen.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.viewpagerindicator.CirclePageIndicator;


import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import test.pylogy.com.mygroupen.R;
import test.pylogy.com.mygroupen.adapter.MyPagerAdapter;
import test.pylogy.com.mygroupen.fragment.VpFragment_1;
import test.pylogy.com.mygroupen.fragment.VpFragment_2;
import test.pylogy.com.mygroupen.fragment.VpFragment_3;
import test.pylogy.com.mygroupen.fragment.VpFragment_4;

public class GuideActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener{

    @BindView(R.id.guide_vp_wrap)
    ViewPager viewPager;
    @BindView(R.id.guide_tv_skip)
    TextView tv_skip;
    @BindView(R.id.guide_btn_start)
    Button btn_start;
    @BindView(R.id.guide_cpi_wrap)
    CirclePageIndicator circlePageIndicator;
    private List<Fragment> list;
    private MyPagerAdapter myPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT>=21){
            View decorView=getWindow().getDecorView();
            int option=View.SYSTEM_UI_FLAG_LAYOUT_STABLE|View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        initialUi();
    }

    private void initialUi() {
        list=new LinkedList<>();
        list.add(new VpFragment_1());
        list.add(new VpFragment_2());
        list.add(new VpFragment_3());
        list.add(new VpFragment_4());
        FragmentManager fm=getSupportFragmentManager();
        myPagerAdapter = new MyPagerAdapter(fm);
        myPagerAdapter.setList(list);
        viewPager.setAdapter(myPagerAdapter);
        viewPager.addOnPageChangeListener(this);
        //获取运城程序锁使用的设备的屏幕密度；
        //低密度：ldpi  (120px/1 inch(英寸2.54cm)显示120）
        //中密度 mdpi      （160px/1 inch）
        //高密度 hdpi      （240px/1 inch）
        //很高密度 xhdpi        （320px/1 inch）
        //非常高密度 xxhdpi      （480px/1 inch）
        //巨高密度  xxxhdpi     （）
        //do是绝对单位 1dp=1/160inch  160dp=1 inch；
        //1dp 在低密度屏幕上  0.75px
        //1dp 在中密度屏幕上   1px;
        //1dp 在高密度屏幕上   1.5px
        //1dp 在很高密度屏幕上  2px;
        //1dp 在非常高密度屏幕上 3px;
        float density=getResources().getDisplayMetrics().density;
        /*
         * 另外一种获取10dp在当前设备屏幕密度上的像素值px
         * float px=TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,10,getResources().getDisplayMetrics());
         *
         */

        circlePageIndicator.setViewPager(viewPager);
        circlePageIndicator.setFillColor(Color.RED);
        circlePageIndicator.setStrokeColor(Color.DKGRAY);
        //10dp在当前设备上所对应的像素值（px）；
        circlePageIndicator.setRadius(5f*density);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }
    @Override
    public void onPageSelected(int position) {
        if (position==3){
            tv_skip.setVisibility(View.GONE);
            btn_start.setVisibility(View.VISIBLE);
            circlePageIndicator.setVisibility(View.GONE);
        }else {
            tv_skip.setVisibility(View.VISIBLE);
            btn_start.setVisibility(View.GONE);
            circlePageIndicator.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
    @OnClick(R.id.guide_tv_skip)
    public void skip(){
        startActivity(new Intent(GuideActivity.this,MainActivity.class));
        finish();
    }
    @OnClick(R.id.guide_btn_start)
    public void start(){
        startActivity(new Intent(GuideActivity.this,MainActivity.class));
        finish();
    }
}
