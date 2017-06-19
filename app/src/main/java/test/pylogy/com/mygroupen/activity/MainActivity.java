package test.pylogy.com.mygroupen.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import test.pylogy.com.mygroupen.R;
import test.pylogy.com.mygroupen.adapter.HeaderPagerAdapter;
import test.pylogy.com.mygroupen.adapter.MyAapater;
import test.pylogy.com.mygroupen.entity.MyBusiness;
import test.pylogy.com.mygroupen.entity.YouLike;
import test.pylogy.com.mygroupen.util.HttpUtil;
import test.pylogy.com.mygroupen.util.VolleyClient;


public class MainActivity extends AppCompatActivity implements
        PullToRefreshBase.OnRefreshListener2
        ,AbsListView.OnScrollListener
        ,ViewPager.OnPageChangeListener,VolleyClient.OnVolleyLoadCompleteListener{

    @BindView(R.id.main_pullrefresh_wrap)
    PullToRefreshListView pullToRefreshListView;
    @BindView(R.id.main_rg_wrap)
    RadioGroup radioGroup;
    @BindView(R.id.main_iv_arrow)
    ImageView iv_arrow;
    @BindView(R.id.main_tv_cityname)
    TextView tv_city;
    ListView listView;
    List<YouLike> list;
    MyAapater myAapater;

    private boolean isShow=true;
    private Menu aMenu;
    private Toolbar toolbar;
    private View headView;
    private ViewPager viewPager;
    private RadioGroup rg_main_head_wrap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        judgeStatus();
        initialListView();
    }
    //透明状态栏设置
    private void judgeStatus() {
        if (Build.VERSION.SDK_INT>=21){
            View decorView=getWindow().getDecorView();
            int option=View.SYSTEM_UI_FLAG_LAYOUT_STABLE|View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }
    //初始化控件和设置监听
    private void initialListView() {
        toolbar = (Toolbar) findViewById(R.id.main_toolbar_wrap);
        setSupportActionBar(toolbar);
        ((RadioButton)radioGroup.getChildAt(0)).setChecked(true);
        list=new LinkedList<>();
        for (int i=0;i<20;i++){
            YouLike youLike=new YouLike("title"+i,"distance"+i,"info"+i,"price"+i,"sales"+i);
            list.add(youLike);
        }
        HttpUtil.testVolley(this,this);
        listView=pullToRefreshListView.getRefreshableView();
        myAapater=new MyAapater(list);
        listView.setAdapter(myAapater);
        headView = LayoutInflater.from(this).inflate(R.layout.main_item_header,null);
        rg_main_head_wrap = (RadioGroup) headView.findViewById(R.id.main_item_header_rg_warp);
        viewPager = (ViewPager) headView.findViewById(R.id.main_item_header_vp);
        FragmentManager fm=getSupportFragmentManager();
        HeaderPagerAdapter headerPagerAdapter=new HeaderPagerAdapter(fm);
        viewPager.setAdapter(headerPagerAdapter);
        viewPager.addOnPageChangeListener(this);
        listView.addHeaderView(headView);
        listView.setOnScrollListener(this);

        //
        pullToRefreshListView.setOnRefreshListener(this);
    }
    //PullDownToRefresh刷新监听
    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                YouLike youLike=new YouLike("title new","0.0","[这是新刷新的数据]","¥ 100","100");
                list.add(0,youLike);
                myAapater.notifyDataSetChanged();
                pullToRefreshListView.onRefreshComplete();
            }
        },1500);

    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {

    }
    //创建menu菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar,menu);
        aMenu=menu;
        checkOptionMenu();
        return super.onCreateOptionsMenu(menu);
    }
    //通过isShow判断是否要显示/隐藏menu菜单
    private void checkOptionMenu(){
        if(null != aMenu){
            if(isShow){
                for (int i = 0; i < aMenu.size(); i++){
                    setIconEnable(aMenu,true);
                    aMenu.getItem(i).setVisible(true);
                    aMenu.getItem(i).setEnabled(true);
                }
            }else{
                for (int i = 0; i < aMenu.size(); i++){
                    aMenu.getItem(i).setVisible(false);
                    aMenu.getItem(i).setEnabled(false);
                }
            }
        }
    }
    //设置menu菜单icon显示
    private void setIconEnable(Menu menu, boolean enable) {
        if (menu != null) {
            if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
                try {
                    Method m = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, enable);
                } catch (Exception e) {
                }
            }
        }
    }
    //这是ListView的滚动监听
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        Log.i("yhf123","onScroll firstVisibleItem="+firstVisibleItem);
        Log.i("yhf123","onScroll visibleItemCount="+visibleItemCount);
        Log.i("yhf123","onScroll totalItemCount="+totalItemCount);
        if (firstVisibleItem!=0){
            isShow=false;
            checkOptionMenu();
            iv_arrow.setVisibility(View.GONE);
            tv_city.setVisibility(View.GONE);
        }else {
            isShow=true;
            checkOptionMenu();
            iv_arrow.setVisibility(View.VISIBLE);
            tv_city.setVisibility(View.VISIBLE);
        }
    }

    //viewPager的监听
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        RadioButton radioButton;
        for (int i=0;i<3;i++){
            radioButton= (RadioButton) rg_main_head_wrap.getChildAt(i);
            if (position==i){
                radioButton.setChecked(true);
                radioButton.setBackgroundResource(R.drawable.banner_dot_pressed);
            }else {
                radioButton.setChecked(false);
                radioButton.setBackgroundResource(R.drawable.banner_dot);
            }
        }
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void OnBusinessComplete(MyBusiness myBusiness) {
        Log.i("yhf123",myBusiness.getCount()+"");
    }
}
