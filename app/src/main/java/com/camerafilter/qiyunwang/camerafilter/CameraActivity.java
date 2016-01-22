/*
 * Copyright (C) 2012 CyberAgent
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.camerafilter.qiyunwang.camerafilter;

import android.app.Activity;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PreviewCallback;
import android.hardware.Camera.Size;
import android.os.Build;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import com.camerafilter.qiyunwang.camerafilter.GPUImageFilterTools.OnGpuImageFilterChosenListener;

import java.io.IOException;
import java.util.List;

public class CameraActivity extends Activity implements OnClickListener {

    private CameraPreview_23 mGPUImageView;

    private Camera mCameraDevice;

    private int front, back;
    private boolean useback = true;
    private SurfaceView display;
    Size previewSize;

    private Button mDialigClick;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        findViewById(R.id.button_choose_filter).setOnClickListener(this);
        findViewById(R.id.button_click_dialog).setOnClickListener(this);

        mGPUImageView = (CameraPreview_23) findViewById(R.id.surfaceView);
        mGPUImageView.setRotationAndFlip(90, 0, 0);
        back = CameraHolder.instance().getBackCameraId();
        front = CameraHolder.instance().getFrontCameraId();
        try {
            mCameraDevice = CameraHolder.instance().open(back);
        } catch (CameraHardwareException e) {
            // In eng build, we throw the exception so that test tool
            // can detect it and report it
            if ("eng".equals(Build.TYPE)) {
                throw new RuntimeException("openCamera failed", e);
            }
        }

        updateCamera();

        display = (SurfaceView) findViewById(R.id.displaysurface);
        display.setVisibility(View.VISIBLE);

        SurfaceHolder sufaceHolder = display.getHolder();

        sufaceHolder.addCallback(new Callback() {

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                mCameraDevice.stopPreview();
                mCameraDevice.setPreviewCallback(null);
                try {
                    mCameraDevice.setPreviewDisplay(null);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }

            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    mCameraDevice.setPreviewDisplay(holder);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                mCameraDevice.setPreviewCallback(new PreviewCallback() {

                    @Override
                    public void onPreviewFrame(byte[] data, Camera camera) {
                        mGPUImageView.onPreviewFrame(data, previewSize.width, previewSize.height);

                    }
                });
                mCameraDevice.startPreview();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                // TODO Auto-generated method stub

            }
        });

        View cameraSwitchView = findViewById(R.id.img_switch_camera);
        cameraSwitchView.setOnClickListener(this);
    }

    private void updateCamera() {
        Parameters params = mCameraDevice.getParameters();
        previewSize = params.getPreviewSize();
        float ration = previewSize.width / previewSize.height;
        if (!useback) {
            ration = previewSize.height / previewSize.width;
        }

        mGPUImageView.setAspectRatio(ration);
    }

    private Size getOptimalPreviewSize(List<Size> sizes, int w, int h) {
        final double ASPECT_TOLERANCE = 0.05;
        double targetRatio = (double) w / h;
        if (sizes == null)
            return null;

        Size optimalSize = null;
        double minDiff = Double.MAX_VALUE;

        int targetHeight = h;

        // Try to find an size match aspect ratio and size
        for (Size size : sizes) {
            double ratio = (double) size.width / size.height;
            if (Math.abs(ratio - targetRatio) > ASPECT_TOLERANCE)
                continue;
            if (Math.abs(size.height - targetHeight) < minDiff) {
                optimalSize = size;
                minDiff = Math.abs(size.height - targetHeight);
            }
        }
        // Cannot find the one match the aspect ratio, ignore the requirement
        if (optimalSize == null) {
            minDiff = Double.MAX_VALUE;
            for (Size size : sizes) {
                if (Math.abs(size.height - targetHeight) < minDiff) {
                    optimalSize = size;
                    minDiff = Math.abs(size.height - targetHeight);
                }
            }
        }
        return optimalSize;
    }

    @Override
    protected void onResume() {
        super.onResume();

        mCameraDevice.startPreview();
    }

    @Override
    protected void onPause() {
        mCameraDevice.stopPreview();
        super.onPause();
    }

    @Override
    public void onClick(final View v) {
        switch (v.getId()) {
            case R.id.button_choose_filter:
                GPUImageFilterTools.generateNextFilter(this, new OnGpuImageFilterChosenListener() {

                    public void onGpuImageFilterChosenListener(String filterName, String filterID, int effect) {
                        mGPUImageView.setFilter(filterID, effect);
                        Toast.makeText(CameraActivity.this, filterName, Toast.LENGTH_SHORT).show();
                    }
                });

                break;
            case R.id.button_click_dialog:
                ColorMatrixSelectorDialog dialog = new ColorMatrixSelectorDialog(this);
                dialog.show();
                break;
            case R.id.img_switch_camera:
                mCameraDevice.stopPreview();
                mCameraDevice.setPreviewCallback(null);
                try {
                    mCameraDevice.setPreviewDisplay(null);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                int cameraid = front;
                if (useback) {
                    mGPUImageView.setRotationAndFlip(270, 0, 0);
                    cameraid = front;
                } else {
                    mGPUImageView.setRotationAndFlip(90, 0, 0);
                    cameraid = back;
                }
                useback = !useback;

                try {
                    mCameraDevice = CameraHolder.instance().open(cameraid);
                } catch (CameraHardwareException e) {
                    // In eng build, we throw the exception so that test tool
                    // can detect it and report it
                    if ("eng".equals(Build.TYPE)) {
                        throw new RuntimeException("openCamera failed", e);
                    }
                }
                updateCamera();
                try {
                    mCameraDevice.setPreviewDisplay(display.getHolder());
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                mCameraDevice.setPreviewCallback(new PreviewCallback() {

                    @Override
                    public void onPreviewFrame(byte[] data, Camera camera) {
                        mGPUImageView.onPreviewFrame(data, previewSize.width, previewSize.height);

                    }
                });
                mCameraDevice.startPreview();
                break;
        }
    }

    static {
        try {
            System.loadLibrary("image_filter_common");
            System.loadLibrary("image_filter_gpu");
        } catch (UnsatisfiedLinkError e1) {
            e1.printStackTrace();
        }
    }
}
