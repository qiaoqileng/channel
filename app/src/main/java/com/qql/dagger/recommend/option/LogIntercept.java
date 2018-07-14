package com.qql.dagger.recommend.option;

import android.text.TextUtils;

import com.qql.dagger.recommend.utils.DateFormat;
import com.qql.dagger.recommend.utils.LogUtil;

import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

public class LogIntercept implements Interceptor {
    long i = 1;
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        String params = parseParams(request);
        LogUtil.d("TAG:" + i + " " + DateFormat.getDateAsString(new Date(), "yyyy-MM-dd HH:mm:ss") + ": " + params + "\n");
        Response response = chain.proceed(request);
        String responseStr = readResponseStr(response);
        LogUtil.d("TAG:"+i + " " + DateFormat.getDateAsString(new Date(),"yyyy-MM-dd HH:mm:ss") + ": " + responseStr + "\n");
        i++;
        return response;
    }

    /**
     * 解析请求参数
     * @param request
     * @return
     */
    public static String parseParams(Request request) {
        //GET POST DELETE PUT PATCH
        String method = request.method();
        String params = null;
        if ("GET".equals(method)) {
            params = doGet(request);
        } else if ("POST".equals(method) || "PUT".equals(method) || "DELETE".equals(method) || "PATCH".equals(method)) {
            RequestBody body = request.body();
            if (body != null && body instanceof FormBody) {
                params = doForm(request);
            }
        }
        if (!TextUtils.isEmpty(params)){
            return request.url().url() + "?" + params;
        } else {
            return request.url().url().toString();
        }
    }
    /**
     * 获取get方式的请求参数
     * @param request
     * @return
     */
    private static String doGet(Request request) {
        StringBuilder sb = new StringBuilder();
        HttpUrl url = request.url();
        Set<String> strings = url.queryParameterNames();
        if (strings != null) {
            Iterator<String> iterator = strings.iterator();
            int i = 0;
            while (iterator.hasNext()) {
                String name = iterator.next();
                String value = url.queryParameterValue(i);
                sb.append(name).append("=").append(value).append("&");
                i++;
            }
        }
        if (sb.length() > 0){
            return sb.substring(0,sb.length()-1);
        } else {
            return sb.toString();
        }
    }

    /**
     * 获取表单的请求参数
     * @param request
     * @return
     */
    private static String doForm(Request request) {
        StringBuilder sb = new StringBuilder();
        FormBody body = null;
        try {
            body = (FormBody) request.body();
        } catch (ClassCastException c) {
        }
        if (body != null) {
            int size = body.size();
            if (size > 0) {
                for (int i = 0; i < size; i++) {
                    sb.append(body.name(i)).append("=").append(body.value(i)).append("&");
                }
            }
        }
        if (sb.length() > 0){
            return sb.substring(0,sb.length()-1);
        } else {
            return sb.toString();
        }
    }

    /**
     * 读取Response返回String内容
     * @param response
     * @return
     */
    private String readResponseStr(Response response) {
        ResponseBody body = response.body();
        BufferedSource source = body.source();
        try {
            source.request(Long.MAX_VALUE);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        MediaType contentType = body.contentType();
        Charset charset = Charset.forName("UTF-8");
        if (contentType != null) {
            charset = contentType.charset(charset);
        }
        String s = null;
        Buffer buffer = source.buffer();
        if (isPlaintext(buffer)) {
            s = buffer.clone().readString(charset);
        }
        return s;
    }

    static boolean isPlaintext(Buffer buffer) {
        try {
            Buffer prefix = new Buffer();
            long byteCount = buffer.size() < 64 ? buffer.size() : 64;
            buffer.copyTo(prefix, 0, byteCount);
            for (int i = 0; i < 16; i++) {
                if (prefix.exhausted()) {
                    break;
                }
                int codePoint = prefix.readUtf8CodePoint();
                if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                    return false;
                }
            }
            return true;
        } catch (EOFException e) {
            return false; // Truncated UTF-8 sequence.
        }
    }

}
