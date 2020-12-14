package com.eli.zfb_zlb.act;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;

import com.eli.zfb_zlb.R;
import com.eli.zfb_zlb.util.Utils;
import com.eli.zfb_zlb.widget.FaceView;

import java.io.IOException;
import java.util.Arrays;

public class CameraActivity extends BaseActivity {

    private static final String TAG = "CameraActivity";

    private Camera camera;
    private boolean isPreview = false;

    private FaceView faceView;
    Handler hintHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            int i = (int) (1 + Math.random() * 4);
            switch (i) {
                case 1:
                    faceView.resetPositionStart();
                    faceView.updateTipsInfo("没有检测到人脸");
                    break;
                case 2:
                    faceView.backAnimator();
                    faceView.updateTipsInfo("请露正脸");
                    break;
                case 3:
                    faceView.pauseAnimator();
                    faceView.updateTipsInfo("眨眨眼");
                    break;
                case 4:
                    faceView.startAnimator();
                    faceView.updateTipsInfo("离近一点");
                    break;
                default:
                    break;
            }

            sendMessageDelayed(this.obtainMessage(), 2000);
        }
    };
    private TextView hintText;
    private SurfaceView mSurfaceView;
    private SurfaceHolder mSurfaceHolder;
    private SurfaceHolder.Callback mSurfaceCallback = new SurfaceHolder.Callback() {
        @Override
        public void surfaceCreated(SurfaceHolder surfaceHolder) {
            try {
                //打开硬件摄像头，这里导包得时候一定要注意是android.hardware.Camera
                // Camera,open() 默认返回的后置摄像头信息
                //设置角度，此处 CameraId 我默认 为 1 （前置）
                if (Camera.getNumberOfCameras() > 1) {
                    camera = Camera.open(1);
                } else {
                    camera = Camera.open(0);
                }
                //设置相机角度
                camera.setDisplayOrientation(90);
                //通过SurfaceView显示取景画面
                camera.setPreviewDisplay(surfaceHolder);
                //开始预览
                camera.startPreview();
                //设置是否预览参数为真
                isPreview = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height) {
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
            if (camera != null) {
                if (isPreview) {//正在预览
                    try {
                        camera.stopPreview();
                        camera.release();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.setTransparentForWindow(this);

        setContentView(R.layout.activity_camera);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 0);
        } else {
            startPreview();
        }
    }

    @Override
    protected void onDestroy() {
        if (camera != null) {
            if (isPreview) {//正在预览
                try {
                    camera.stopPreview();
                    camera.release();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        super.onDestroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 0) {
            Log.i(TAG, "grantResults: " + Arrays.toString(grantResults));
            if (grantResults[0] == 0) {
                startPreview();
            }
        }
    }

    private void startPreview() {
        //初始化布局
        ConstraintLayout constraintLayout = findViewById(R.id.cl_root);
        faceView = findViewById(R.id.fv_title);
        hintText = findViewById(R.id.tv_tip);
        hintText.postDelayed(() -> {
            hintText.setVisibility(View.GONE);
        }, 1000);

        hintText.postDelayed(() -> {
            stopPreview();
            startActivityForResult(new Intent(this, VerifySuccessActivity.class), 102);
        }, 5000);

        hintHandler.sendMessage(hintHandler.obtainMessage());

        //添加布局
        mSurfaceView = new SurfaceView(this);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        mSurfaceView.setLayoutParams(params);
        constraintLayout.addView(mSurfaceView, 0);
        //得到getHolder实例
        mSurfaceHolder = mSurfaceView.getHolder();
        mSurfaceHolder.setFormat(PixelFormat.TRANSPARENT);
        // 添加 Surface 的 callback 接口
        mSurfaceHolder.addCallback(mSurfaceCallback);
    }

    private void stopPreview() {
        hintHandler.removeMessages(0);
        faceView.finnishAnimator();
        if (camera != null) {
            if (isPreview) {//正在预览
                try {
                    camera.stopPreview();
                    camera.release();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.i("elifli", "CameraActivity requestCode: " + requestCode + ", resultCode: " + resultCode);
        if (requestCode == 102 && resultCode == 103) {
            setResult(104);
            finish();
        }
    }
}
