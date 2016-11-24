package com.qiu.photo_videotest.photo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.qiu.photo_videotest.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements View.OnClickListener {

    Button btn_downloadPic;
    LinearLayout ll_addPics;
    ImageView iv_addPic1;
    ImageView iv_addPic2;
    ImageView iv_addPic3;
    LinearLayout ll_showPics;
    ImageView iv_showPic1;
    ImageView iv_showPic2;
    ImageView iv_showPic3;

    private int isProcess = 0;

    private Uri imageUri1;
    private Uri imageUri2;
    private Uri imageUri3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        initOnclick();
    }

    private void initView() {
        btn_downloadPic = (Button) findViewById(R.id.btn_downloadPic);
        ll_addPics = (LinearLayout) findViewById(R.id.ll_add_photo_video);
        iv_addPic1 = (ImageView) findViewById(R.id.iv_add_pic1);
        iv_addPic2 = (ImageView) findViewById(R.id.iv_add_pic2);
        iv_addPic3 = (ImageView) findViewById(R.id.iv_add_pic3);
        ll_showPics = (LinearLayout) findViewById(R.id.ll_show_photo_video);
        iv_showPic1 = (ImageView) findViewById(R.id.iv_show_pic1);
        iv_showPic2 = (ImageView) findViewById(R.id.iv_show_pic2);
        iv_showPic3 = (ImageView) findViewById(R.id.iv_show_pic3);
//        if (isProcess == 0) { //未处理，显示拍照控件
//            ll_addPics.setVisibility(View.VISIBLE);
//            ll_showPics.setVisibility(View.GONE);
//        } else if (isProcess == 1) { //已处理，显示图片和视频控件
//            ll_addPics.setVisibility(View.GONE);
//            ll_showPics.setVisibility(View.VISIBLE);
//        }
    }

    private void initData() {
    }

    private void initOnclick() {
        btn_downloadPic.setOnClickListener(this);
        iv_addPic1.setOnClickListener(this);
        iv_addPic2.setOnClickListener(this);
        iv_addPic3.setOnClickListener(this);
    }

    private String url = "http://wxeis.eis.swdnkj.com/wechatSwdn/img/2000-01.png";
    private String url2 = "http://wxeis.eis.swdnkj.com/wechatSwdn/img/2000-02.png";
    private String url3 = "http://wxeis.eis.swdnkj.com/wechatSwdn/img/2000-03.png";
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            byte[] data = (byte[]) msg.obj;
            Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            if (msg.what == 404) {

            } else if (msg.what == 2001) {
                iv_showPic1.setImageBitmap(bitmap);
            } else if (msg.what == 2002) {
                iv_showPic2.setImageBitmap(bitmap);
            } else if (msg.what == 2003) {
                iv_showPic3.setImageBitmap(bitmap);
            }
        }
    };


    @Override
    public void onClick(View v) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("请选择添加图片方式");
        final String[] categories = {"相册", "拍照"};
        switch (v.getId()) {
            //点击下载图片,巡视和抢修模块中需要加载三张图片
            case R.id.btn_downloadPic:
                //判断当前是否有网络


                List<Runnable> runnables = new ArrayList<Runnable>();
                runnables.add(new HttpGetPic(url, handler, "img1"));
                runnables.add(new HttpGetPic(url2, handler, "img2"));
                runnables.add(new HttpGetPic(url3, handler, "img3"));
                ThreadPoolUtils1.executeBatch(runnables);
                break;
            //点击增加图片1--调用摄像头拍照并将图片显示在控件上
            case R.id.iv_add_pic1:
                builder.setItems(categories, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0://点击从相册加载图片,放在 File(Environment.getExternalStorageDirectory(), "output_image3.jpg")
//                                loadPicFromAlbum();
                                break;
                            case 1://点击调用拍照并加载图片
                                File outputImage = new File(Environment.getExternalStorageDirectory(), "output_image1.jpg");
                                try {
                                    if (outputImage.exists()) {
                                        outputImage.delete();
                                    }
                                    outputImage.createNewFile();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                imageUri1 = Uri.fromFile(outputImage);
                                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri1);
                                startActivityForResult(intent,1);
                                break;
                        }
                    }
                });
                builder.show();
//                UploadUtil upload = UploadUtil.getInstance();
//                upload.uploadFile("E:/receive.jpg", "picqiu_001", "http://wxeis.eis.swdnkj.com/hrkweb/AndroidPic/qx/", null);
                break;
            //点击增加图片2
            case R.id.iv_add_pic2:
                builder.setItems(categories, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0://点击从相册加载图片,放在 File(Environment.getExternalStorageDirectory(), "output_image3.jpg")
//                                loadPicFromAlbum();
                                break;
                            case 1://点击调用拍照并加载图片
                                File outputImage = new File(Environment.getExternalStorageDirectory(), "output_image2.jpg");
                                try {
                                    if (outputImage.exists()) {
                                        outputImage.delete();
                                    }
                                    outputImage.createNewFile();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                imageUri2 = Uri.fromFile(outputImage);
                                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri2);
                                startActivityForResult(intent,2);
                                break;
                        }
                    }
                });
                builder.show();
                break;
            //点击增加图片3
            case R.id.iv_add_pic3:
                builder.setItems(categories, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0://点击从相册加载图片,放在 File(Environment.getExternalStorageDirectory(), "output_image3.jpg")
//                                loadPicFromAlbum();
                                break;
                            case 1://点击调用拍照并加载图片
                                File outputImage = new File(Environment.getExternalStorageDirectory(), "output_image3.jpg");
                                try {
                                    if (outputImage.exists()) {
                                        outputImage.delete();
                                    }
                                    outputImage.createNewFile();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                imageUri3 = Uri.fromFile(outputImage);
                                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri3);
                                startActivityForResult(intent,3);
                                break;
                        }
                    }
                });
                builder.show();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1://从图片1跳转的摄像头应用返回
                if (resultCode == RESULT_OK) {
                    try {
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inSampleSize = 2;
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri1),null,options);
                        iv_addPic1.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case 2://从图片2跳转的摄像头应用返回
                if (resultCode == RESULT_OK) {
                    try {
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inSampleSize = 2;
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri2),null,options);
                        iv_addPic2.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case 3://从图片3跳转的摄像头应用返回
                if (resultCode == RESULT_OK) {
                    try {
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inSampleSize = 2;
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri3),null,options);
                        iv_addPic3.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }
}
