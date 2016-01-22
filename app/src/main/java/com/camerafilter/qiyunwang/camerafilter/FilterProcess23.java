package com.camerafilter.qiyunwang.camerafilter;

import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import com.tencent.filter.BaseFilter;
import com.tencent.filter.Frame;
import com.tencent.filter.GLSLRender;
import com.tencent.filter.QImage;

public class FilterProcess23 extends FilterProcess {

	private int[] mPreviewTextureId = new int[] { 0 };
	
	private int previewShaderID = GLSLRender.FILTER_SHADER_YUV;
	BaseFilter mPreviewFilter;
	Frame mYuvFrame = new Frame();

	
	

	public void showPreview(int width, int height) {
		if (mHaveFrameCount < SHOW_DELAY_COUNT) {
			mHaveFrameCount++;

			GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
			GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
			GLES20.glFinish();
			return;
		}
		if (mPreviewFilter != null) {

			mFilter.nativeSetRotationAndFlip(rotation, flipx, flipy);
			mFilter.RenderProcess(mYuvFrame.getTextureId(), width, height, 0,
					mAspectRatio, mPriewFrame);

		} else {
			mFilter.nativeSetRotationAndFlip(rotation, flipx, flipy);
			mFilter.RenderProcess(mPreviewTextureId[0], width, height, 0,
					mAspectRatio, mPriewFrame);
		}

	}

	public void intial() {
		super.intial();
		GLES20.glGenTextures(mPreviewTextureId.length, mPreviewTextureId, 0);

	}

	public void clear() {
		mPriewFrame.clear();
		mPictureFrame.clear();
		mYuvFrame.clear();

		GLES20.glDeleteTextures(mPreviewTextureId.length, mPreviewTextureId, 0);

		if (mPreviewFilter != null) {
			mPreviewFilter.ClearGLSL();
			mPreviewFilter = null;
		} else {
			mFilter.ClearGLSL();
		}

	}

	public void changeFilter(String filterID, int effectIndex) {
		super.changeFilter( filterID, effectIndex);
		if (mPreviewFilter != null)
			mPreviewFilter.setNextFilter(mFilter,null);
	}

	public void updatePreviewImage(QImage image) {
		GLSLRender.nativeTextImage(image, mPreviewTextureId[0]);
	}
	public void updatePreviewBitmap(Bitmap bitmap) {
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mPreviewTextureId[0]);
//		int internalFormat = GLUtils.getInternalFormat(bitmap);
//		int type = GLUtils.getType(bitmap);
//		GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, internalFormat, bitmap, type, 0);
		GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);
		GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D,
				GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
		GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D,
				GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
		GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S,
				GLES20.GL_CLAMP_TO_EDGE);
		GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T,
				GLES20.GL_CLAMP_TO_EDGE);
//		checkGlError("texImage2D");
	}
	final 

	public void updatePreviewData(byte[] data, int width, int height) {
		GLSLRender.nativePreviewData(data, mPreviewTextureId[0], width, height);
		if (mPreviewFilter == null) {
			mPreviewFilter = new BaseFilter(previewShaderID);
//			mPreviewFilter.setNextFilter(mFilter,null);
			mPreviewFilter.ApplyGLSLFilter(true,0,0);
		}
		if (mPreviewFilter != null) {
			mPreviewFilter.nativeSetRotationAndFlip(0, 0, 0);
			mPreviewFilter.RenderProcess(mPreviewTextureId[0], width, height,
					-1, mAspectRatio, mYuvFrame);
		}
	}

	public void setPreviewShaderID(int shader){
	    previewShaderID = shader;
	}

	

}
