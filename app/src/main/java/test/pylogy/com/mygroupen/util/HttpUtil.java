package test.pylogy.com.mygroupen.util;

import android.content.Context;
import android.provider.SyncStateContract;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import test.pylogy.com.mygroupen.constants.Constants;

/**
 * 网络访问工具类
 *
 * 符合大众点评服务器要求的地址：
 * 网址部分？参数1=值1&参数2=值2.。
 * String apiUrl = "http://api.dianping.com/v1/business/find_businesses/appKey="49814079"&sign="MD5字符串"&city="上海UTF8"&category="美食UTF8""
 *
 * 请求地址签名生成
 * 利用AppKey，AppSecret，以及其他访问参数（例如city，category等）
 * 1.首先将AppKey，AppSecret，以及其他访问参数拼接成一个字符串；
 * 再将拼接好的字符串进行转码（转码算法为SHA1算法）；
 *
 * Created by Administrator on 2017/6/19 0019.
 */

public abstract class HttpUtil {
    private HttpUtil(){};
    public static String getURL(String url, Map<String, String> params) {

        String result = "";

        String sign = getSign(Constants.DianPin.APPKEY,Constants.DianPin.APPSECRET, params);

        String query = getQuery(Constants.DianPin.APPKEY, sign, params);

        result = url + "?" + query;

        return result;

    }


    /**
     * 获取请求地址中的签名
     *
     * @param appkey
     * @param appsecret
     * @param params
     * @return
     */
    private static String getSign(String appkey, String appsecret, Map<String, String> params) {

        StringBuilder stringBuilder = new StringBuilder();

// 对参数名进行字典排序
        String[] keyArray = params.keySet().toArray(new String[0]);
        Arrays.sort(keyArray);
// 拼接有序的参数名-值串
        stringBuilder.append(appkey);
        for (String key : keyArray) {
            stringBuilder.append(key).append(params.get(key));
        }
        String codes = stringBuilder.append(appsecret).toString();
        //纯JAVA环境中，利用Codec对字符串进行SHA1转码采用如下方式
        //String sign = org.apache.commons.codec.digest.DigestUtils.shaHex(codes).toUpperCase();
        //安卓环境中，利用Codec对字符串进行SHA1转码采用如下方式
        String sign = new String(Hex.encodeHex(DigestUtils.sha(codes))).toUpperCase();
        return sign;
    }

    /**
     * 获取请求地址中的参数部分
     *
     * @param appkey
     * @param sign
     * @param params
     * @return
     */
    private static String getQuery(String appkey, String sign, Map<String, String> params) {
        try {
            // 添加签名
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("appkey=").append(appkey).append("&sign=").append(sign);
            for (Map.Entry<String, String> entry : params.entrySet())
            {
                stringBuilder.append('&').append(entry.getKey()).append('=').append(URLEncoder.encode(entry.getValue(),"utf8"));
            }
            String queryString = stringBuilder.toString();

            return queryString;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            //扔异常
            throw new RuntimeException("使用了不正确的字符集名称");
        }
    }


    public static void testHttpURLConnection(){

        //获取符合大众点评要求的请求地址
        Map<String,String> params = new HashMap<String,String>();
        params.put("city","北京");
        params.put("category","美食");
        final String url = getURL("http://api.dianping.com/v1/business/find_businesses",params);
        Log.d("TAG", "生成的网络请求地址是："+url);
        new Thread(){
            @Override
            public void run() {
                try {
                    super.run();
                    URL u = new URL(url);
                    HttpURLConnection connection = (HttpURLConnection) u.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setDoInput(true);//该方法可写可不写，因为默认就是true
                    connection.connect();
                    InputStream inputStream = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder sb = new StringBuilder();
                    String line = null;
                    while ((line = reader.readLine())!=null){
                        sb.append(line);
                    }
                    reader.close();
                    String response = sb.toString();
                    Log.d("TAG", "HttpURLConnection获得的服务器响应内容："+response);


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public static void testVolley(Context context, VolleyClient.OnVolleyLoadCompleteListener onVolleyLoadCompleteListener){
        VolleyClient.getVolleyClient(context).test(onVolleyLoadCompleteListener);
    }
}
