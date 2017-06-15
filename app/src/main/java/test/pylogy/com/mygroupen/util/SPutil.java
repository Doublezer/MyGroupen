package test.pylogy.com.mygroupen.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.ParcelUuid;
import android.preference.PreferenceManager;
import android.util.Log;

import test.pylogy.com.mygroupen.constants.Constants;


/**
 * 1.Context.getSharedPreferences(文件名，模式);
 * 2.Activity的getPreference(模式)；获取以preference_activity来命名的偏好设置文件；
 * 3.PreferenceManager的getDefaultSharedPreferences（Context）；
 *      获取preference_包名(全局通用的属性)  偏好设置文件；
 *      模式Context_MODE_PRIVATE
 *
 * Created by free.db on 2017/6/15 0015.
 */

public class SPutil {
    private SharedPreferences sp;
    //通过构造器重载
    public SPutil(Context context,String name){
        sp=context.getSharedPreferences(name,Context.MODE_PRIVATE);
    };
    public SPutil(Context context){
        sp=PreferenceManager.getDefaultSharedPreferences(context);
    }
    public boolean isFirst(){
        boolean flag=sp.getBoolean(Constants.SharedPre.FIRST,true);
        return flag;
    }
    public void setFirst(boolean flag){
        SharedPreferences.Editor edit = sp.edit();
        edit.putBoolean(Constants.SharedPre.FIRST,flag);
        edit.commit();
    }

}
