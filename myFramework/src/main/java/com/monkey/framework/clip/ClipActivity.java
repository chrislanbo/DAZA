package com.monkey.framework.clip;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;


import com.monkey.framework.R;
import com.monkey.framework.app.ActivityManager;
import com.monkey.framework.app.BaseActivity;
import com.monkey.framework.utils.BitmapUtil;
import com.monkey.framework.utils.SDCardHelper;
import com.monkey.framework.view.LoadingView;

import java.io.File;


public class ClipActivity extends BaseActivity {
    // 保存头像的文件夹
    public final String SDPATH = SDCardHelper.getSDCardAbsolutePath().getAbsolutePath()
            + File.separator + "union";
    private ClipImageLayout mClipImageLayout;
    private String path;
    private LoadingView loadingDialog;
    private Button action_cancel;
    private Button action_clip;
    private String fileHeader = "file://";

    @Override
    public int getLayoutRes() {
        return R.layout.aty_clipimage;
    }

    @Override
    protected void afterInject(Bundle savedInstanceState) {
        super.afterInject(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        loadingDialog = getLoadingView();
        path = getIntent().getStringExtra("path");
        path = path.substring(fileHeader.length());
        if (TextUtils.isEmpty(path) || !(new File(path).exists())) {
            Toast.makeText(this, "图片加载失败", Toast.LENGTH_SHORT).show();
            return;
        }
        Bitmap bitmap = BitmapUtil.convertToBitmap(path, 600, 600);
        if (bitmap == null) {
            Toast.makeText(this, "图片加载失败", Toast.LENGTH_SHORT).show();
            return;
        }
        mClipImageLayout = (ClipImageLayout) findViewById(R.id.id_clipImageLayout);
        mClipImageLayout.setBitmap(bitmap);
        initView();
    }


    public void initView() {
        action_clip = (Button) findViewById(R.id.action_clip);
        action_cancel = (Button) findViewById(R.id.action_cancel);
        action_clip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingDialog.showLoadingView();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Bitmap bitmap = mClipImageLayout.clip();
                        bitmap = BitmapUtil.compressBitmap(
                                BitmapUtil.Bitmap2Bytes(bitmap), 300, 300);
                        String path = "pic" + System.currentTimeMillis() + ".jpeg";
                        SDCardHelper.saveDataToSDCard(SDPATH, path,
                                BitmapUtil.Bitmap2Bytes(bitmap));
                        loadingDialog.hideLoadingView();
                        Intent intent = new Intent();
                        intent.putExtra("path", fileHeader + SDPATH + File.separator + path);
                        bitmap.recycle();
                        setResult(RESULT_OK, intent);
                        ActivityManager.finish(ClipActivity.class);
                    }
                }).start();
            }
        });
        action_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                ActivityManager.finish(ClipActivity.class);
            }
        });
    }
}
