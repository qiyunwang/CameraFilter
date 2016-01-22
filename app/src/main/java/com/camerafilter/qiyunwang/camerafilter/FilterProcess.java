package com.camerafilter.qiyunwang.camerafilter;

import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import com.camerafilter.qiyunwang.camerafilter.MyFilterFactory.OnChangeFilterListener;
import com.tencent.filter.BaseFilter;
import com.tencent.filter.Frame;
import com.tencent.filter.GLSLRender;
import com.tencent.filter.QImage;
import com.tencent.util.BitmapUtils;
import com.tencent.util.LogUtil;
import com.tencent.util.PhoneProperty;
import com.tencent.view.FilterEnum;
import com.tencent.view.FilterFactory;

public class FilterProcess {
	BaseFilter mFilter = FilterFactory.createFilter(FilterEnum.MIC_LENS);
	private String filterId = "MIC_LENS";
	private int effectIndex = 0;
	Frame mPriewFrame = new Frame();
	Frame mPictureFrame = new Frame();
	double mAspectRatio = 0.0;
	int mExposure;
	int rotation = 0;
    int flipx = 0;
    int flipy = 0;
	// avoid blue screen
	int mHaveFrameCount = 0;
	public static int SHOW_DELAY_COUNT = 0;
	static {
		// delay 7 frames
		SHOW_DELAY_COUNT = PhoneProperty.instance().getDelayFrameCount();
		if (PhoneProperty.instance().isDelayDisplayGSLView()|| !PhoneProperty.instance().isAdaptive()) {
			SHOW_DELAY_COUNT = 7;
		}
	}
	
	public void showPreview(int width, int height) {

	}

	public void intial() {
		mFilter.ApplyGLSLFilter(true,0,0);
	}

	public void clear() {
		mPriewFrame.clear();
		mPictureFrame.clear();
		mFilter.ClearGLSL();
	}

	public void previewStart() {
		mHaveFrameCount = 0;
	}
	public void setScreenAspectRatio(double aspectRatio) {
		this.mAspectRatio = aspectRatio;
	}

	public void changeFilter(String filterID, int effectIndex) {
		if (mFilter != null && this.filterId.equals(filterID) && this.effectIndex == effectIndex) {
			return;
		}
		if (mFilter != null) {
			mFilter.ClearGLSL();
		}
		this.filterId = filterID;
		this.effectIndex = effectIndex;
		//mFilter =  FilterFactory.createFilter(filterId, effectIndex);
        
        mFilter = MyFilterFactory.createFilter(filterId, effectIndex);   
		mFilter.ApplyGLSLFilter(true, 0, 0);
	}

    public void changeFilter(float[] colorMatrix, String filterID, int effectIndex) {
        if (mFilter != null && this.filterId.equals(filterID) && this.effectIndex == effectIndex) {
            return;
        }
        if (mFilter != null) {
            mFilter.ClearGLSL();
        }
        this.filterId = filterID;
        this.effectIndex = effectIndex;
        //mFilter =  FilterFactory.createFilter(filterId, effectIndex);

        mFilter = MyFilterFactory.createFilter(colorMatrix, filterId, effectIndex);
        mFilter.ApplyGLSLFilter(true, 0, 0);
    }

    public void setOnChangeFilterListener(OnChangeFilterListener listener) {
        MyFilterFactory.setOnChangeFilterListener(listener);
    }

	public Bitmap makeFilter(Bitmap bitmap, int rotation, int flipY,
                             int cameraId, int backupDegree) {
		long currentTime = System.currentTimeMillis();
		mFilter.ApplyGLSLFilter(false,0,0);
		Bitmap result = null;
		if (mFilter.isGPUProcess()) {
			int[] textures = new int[1];
			GLES20.glGenTextures(textures.length, textures, 0);
			//
			float[] matrix = BitmapUtils.rotateAndFlipYMatrix(bitmap, rotation,
					flipY, cameraId, backupDegree);
			mFilter.nativeUpdateModelMatrix(matrix);

			GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
			GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0]);
			GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,
					GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_NEAREST);
			GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,
					GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
			GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,
					GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
			GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,
					GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);
			GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0,
					GLUtils.getInternalFormat(bitmap), bitmap,
					GLUtils.getType(bitmap), 0);

			if (rotation % 180 == 0) {
				long lastTime = System.currentTimeMillis();
				mFilter.RenderProcess(textures[0], bitmap.getWidth(),
						bitmap.getHeight(), -1, 0.0, mPictureFrame);
				LogUtil.d(
						this,
						"nativeRenderTexture " + ":"
								+ (System.currentTimeMillis() - lastTime));
				lastTime = System.currentTimeMillis();
				GLSLRender.nativeCopyPixelToBitmap(bitmap);
				LogUtil.d(
						this,
						"nativeCopyPixelToBitmap " + ":"
								+ (System.currentTimeMillis() - lastTime));
				result = bitmap;
			} else {
				int width = bitmap.getHeight();
				int height = bitmap.getWidth();
				bitmap.recycle();
				bitmap = null;
				result = Bitmap.createBitmap(width, height,
						Bitmap.Config.ARGB_8888);
				mFilter.RenderProcess(textures[0], width, height, -1, 0.0,
						mPictureFrame);
				GLSLRender.nativeCopyPixelToBitmap(result);
			}

			GLES20.glDeleteTextures(textures.length, textures, 0);
		} else {
			bitmap = BitmapUtils.rotateAndFlipY(bitmap, rotation,
					flipY, cameraId, backupDegree);
			QImage qimage = QImage.Bitmap2QImage(bitmap);
			mFilter.ApplyFilter(qimage);
			qimage.ToBitmap(bitmap);
			qimage.Dispose();
			result = bitmap;
		}
		if (mFilter != null) {
			mFilter.ApplyGLSLFilter(true,0,0);
		}
		LogUtil.d(this, "makeFilter :"
				+ (System.currentTimeMillis() - currentTime));
		return result;
	}
	
	public void update() {
		
	}
	public boolean isNormalFilter() {
        if (mFilter != null && mFilter.isNormal()) {
            return true;
        } else {
            return false;
        }
    }
	
	public void setRotationAndFlip(int rotation, int flipx, int flipy) {
        this.rotation = rotation;
        this.flipx = flipx;
        this.flipy = flipy;
    }
	
//	public byte[] makeFilterJepg(byte[] data,int [] jepgSize) {
//		long currentTime = System.currentTimeMillis();
//		
//		//
//		mFilter.ApplyGLSLFilter(false);
//		mFilter.nativeSetRotationAndFlip(0, 0, 0);
//
////		if (mFilter instanceof FaceDetectBaseFilter)
////			((FaceDetectBaseFilter) mFilter).initial(bitmap);
////		byte[] result = GLSLRender.nativeRenderJepgData(data,jepgSize,90);
//		byte[] result = mFilter.RenderProcessJepg(data,jepgSize, mPictureFrame);
//
//		if (mFilter != null) {
//			mFilter.ApplyGLSLFilter(true);
//		}
//		LogUtil.d(
//				this,
//				"makeFilter :"
//						+ (System.currentTimeMillis() - currentTime));
//		return result;
//	}
}
