/*
 * Copyright (C) 2012-2013 Tencent. All Rights Reserved.
 * 
 */

package com.camerafilter.qiyunwang.camerafilter;

import android.content.Context;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.util.AttributeSet;
import com.tencent.filter.QImage;
import com.tencent.util.LogUtil;

import javax.microedition.khronos.opengles.GL10;

public class CameraPreview_23 extends GLCameraPreview {
	// set drawing
	private volatile boolean mWillDrawing;
    protected static String TAG = CameraPreview_23.class.getSimpleName();
//
//	int texture = 0;
//	Bitmap bitmap;

	public CameraPreview_23(Context context) {
		super(context);
		mFilterProces = new FilterProcess23();
	}

	public CameraPreview_23(Context context, AttributeSet attrs) {
		super(context, attrs);
		mFilterProces = new FilterProcess23();
	}

	public void onDrawFrame(GL10 glUnused) {
		mWillDrawing = false;
		super.onDrawFrame(glUnused);
	}
	
	public void setPreviewShaderID(int shader){
	    ((FilterProcess23) mFilterProces).setPreviewShaderID(shader);
    }

	public void onPreviewFrame(final QImage image, Camera camera) {
        LogUtil.i(TAG, "[onPreviewFrame] + BEGIN");

        if (mWillDrawing) {
			requestRender();
			return;
		}

		mAcceptData = false;
		if (!mHaveFrame)
			mFilterProces.previewStart();
		mHaveFrame = true;
		queueEvent(new Runnable() {
			@Override
			public void run() {
				((FilterProcess23) mFilterProces).updatePreviewImage(image);
				// GLSLRender.nativeTextImage(image);
			}
		});
		requestRender();
		mWillDrawing = true;
        LogUtil.i(TAG, "[onPreviewFrame] + END");

    }
	public void onPreviewFrame(final Bitmap bitmap) {
        LogUtil.i(TAG, "[onPreviewFrame] + BEGIN");

        if (mWillDrawing) {
			requestRender();
			return;
		}

		mAcceptData = false;
		if (!mHaveFrame)
			mFilterProces.previewStart();
		mHaveFrame = true;
		queueEvent(new Runnable() {
			@Override
			public void run() {
				((FilterProcess23) mFilterProces).updatePreviewBitmap(bitmap);
				// GLSLRender.nativePreviewData(data, width, height);
			}
		});
		requestRender();
		mWillDrawing = true;
        LogUtil.i(TAG, "[onPreviewFrame] + END");

    }

	public void onPreviewFrame(final byte[] data, final int width, final int height) {
        LogUtil.i(TAG, "[onPreviewFrame] + BEGIN");

        if (mWillDrawing) {
			requestRender();
			return;
		}

		mAcceptData = false;
		if (!mHaveFrame)
			mFilterProces.previewStart();
		mHaveFrame = true;
		queueEvent(new Runnable() {
			@Override
			public void run() {
				((FilterProcess23) mFilterProces).updatePreviewData(data, width, height);
			}
		});
		requestRender();
		mWillDrawing = true;
        LogUtil.i(TAG, "[onPreviewFrame] + END");

    }

	public boolean isNormalFilter() {
		return ((FilterProcess23) mFilterProces).isNormalFilter();
	}


	public void setAspectRatio(double aspectRatio) {
		mFilterProces.setScreenAspectRatio(aspectRatio);

	}
}
