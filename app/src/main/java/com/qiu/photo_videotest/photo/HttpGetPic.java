package com.qiu.photo_videotest.photo;

import android.os.Handler;
import android.os.Message;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by lenovo on 2016/11/21.
 */

public class HttpGetPic implements Runnable{

    private String url;
    private Handler handler;
    private String tag;

    public HttpGetPic(String url,Handler handler,String tag) {
        this.url = url;
        this.handler = handler;
        this.tag = tag;
    }

    @Override
    public void run(){
        // 获取我们回调主ui的message
        Message msg = handler.obtainMessage();
        URL urlGetPic = null;
        try {
            urlGetPic = new URL(url);
            HttpURLConnection conn = (HttpURLConnection)urlGetPic.openConnection();
            conn.setRequestMethod("GET");   //设置请求方法为GET
            conn.setReadTimeout(5*1000);    //设置请求过时时间为5秒
            InputStream inputStream = conn.getInputStream();   //通过输入流获得图片数据
            byte[] data = StreamTool.readInputStream(inputStream);     //获得图片的二进制数据
            if ("img1".equals(tag)) {
                msg.what = 2001;
            } else if ("img2".equals(tag)) {
                msg.what = 2002;
            } else if ("img3".equals(tag)) {
                msg.what = 2003;
            }
            msg.obj = data;
        } catch (Exception e) {
            msg.what = 404;
            e.printStackTrace();
        }
        // 给主ui发送消息传递数据
        handler.sendMessage(msg);
    }
}
