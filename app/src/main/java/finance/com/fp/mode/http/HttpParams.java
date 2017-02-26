package finance.com.fp.mode.http;

import android.text.TextUtils;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;


/**
 * Description：
 *
 * @Author桑小年
 * @Data：2016/11/10 10:47
 */
public class HttpParams {

    private static HttpParams instance;

    private static Map<String, String> params;

    private HttpParams() {
        params = new TreeMap<String, String>(
                new Comparator<String>() {
                    @Override
                    public int compare(String o1, String o2) {
                        return o1.compareTo(o2);
                    }
                }
        );
    }

    public static HttpParams getInstance() {
        if (instance == null) {
            synchronized (HttpParams.class) {
                if (instance == null) {
                    instance = new HttpParams();
                }
            }
        }
        params.clear();
        return instance;
    }


    public Map<String, String> getParams() {

        return params;
    }

    public Map<String, String> removeNull() {
        Map<String,String> map = new HashMap<>();
        Iterator<String> iter = params.keySet().iterator();
        while (iter.hasNext()) {
            String key = iter.next();
            String value = params.get(key);
            if (!TextUtils.isEmpty(value)) {
                map.put(key,value);
            }

        }

        return map;
    }

    public void put(String key, String value) {
        params.put(key, value);
    }

    public void putAll(Map<String, String> params) {
        this.params.putAll(params);
    }

    public void remove(String key) {
        params.remove(key);
    }

    @Override
    public String toString() {

        return encodParams();
    }

    private String encodParams() {
        StringBuffer sb = new StringBuffer();
        Set<String> set = params.keySet();

        Iterator<String> iter = params.keySet().iterator();
        while (iter.hasNext()) {
            String key = iter.next();
            String value = params.get(key);
            if (!TextUtils.isEmpty(value)) {
                sb.append(key).append("=").append(value).append("&");
            }

        }

        return sb.toString().substring(0, sb.length() - 1);
    }

    public String getValue(String secret) {
        // 第一步：检查参数是否已经排序
        String[] keys = params.keySet().toArray(new String[0]);
        Arrays.sort(keys);

        // 第二步：把所有参数名和参数值串在一起
        StringBuilder query = new StringBuilder();
        query.append(secret);

        for (String key : keys) {
            String value = params.get(key);
            if (!TextUtils.isEmpty(key) && !TextUtils.isEmpty(value)) {
                query.append(key).append(value);
            }
        }
        query.append(secret);
        return query.toString();
    }
}
