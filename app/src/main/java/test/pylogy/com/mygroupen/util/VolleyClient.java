package test.pylogy.com.mygroupen.util;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import test.pylogy.com.mygroupen.entity.MyBusiness;

/**
 * Created by Administrator on 2017/6/19 0019.
 */

public class VolleyClient {
    private static VolleyClient volleyClient;
    public static VolleyClient getVolleyClient(Context context){
        if (volleyClient==null){
            synchronized(VolleyClient.class){
                if (volleyClient==null){
                    return new VolleyClient(context);
                }
            }
        }
        return volleyClient;
    }
    private RequestQueue requestQueue;
    private VolleyClient(Context context){
        requestQueue=Volley.newRequestQueue(context);
    }
    public void test(final OnVolleyLoadCompleteListener onVolleyLoadCompleteListener){
        MyBusiness myBusiness=null;
        Map<String,String> params = new HashMap<String,String>();
        params.put("city","广州");
        params.put("category","美食");
        final String url = HttpUtil.getURL("http://api.dianping.com/v1/business/find_businesses",params);
        Log.i("yhf123",url);
        StringRequest stringRequest=new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.i("yhf123","onResponse()="+s);
                Gson gson=new Gson();
                MyBusiness myBusiness=gson.fromJson(s, MyBusiness.class);
                onVolleyLoadCompleteListener.OnBusinessComplete(myBusiness);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.i("yhf123",volleyError.toString(),volleyError);
            }
        });
        requestQueue.add(stringRequest);
    }
    public interface OnVolleyLoadCompleteListener{
        void OnBusinessComplete(MyBusiness myBusiness);
    }
}
