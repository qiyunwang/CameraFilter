/*
 * Copyright (C) 2012-2013 Tencent. All Rights Reserved.
 * 
 */

package com.camerafilter.qiyunwang.camerafilter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import com.camerafilter.qiyunwang.camerafilter.MyFilterFactory.OnChangeFilterListener;
import com.tencent.util.LogUtil;
import com.tencent.util.PhoneProperty;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

abstract public class GLCameraPreview extends GLSurfaceView implements
        GLSurfaceView.Renderer {

    public interface GLCameraPreviewListener {
        void onSurfaceChanged(int width, int height);
        void onSurfaceCreatedNotify();
    }
    //fps
    private int mImagecount;
    private long mStarttime;

    private GLCameraPreviewListener listener =null;
    
    public void setListener(GLCameraPreviewListener listener) {
        this.listener = listener;
    }

    FilterProcess mFilterProces ;//= new FilterProcess23();

    int mWindowWidth = 320;
    int mWindowHeight = 480;

    float[] mTransformMatrix = new float[16];

    public boolean mPausePreview;

    volatile boolean mAcceptData = true;

    boolean mHaveFrame = false;

    // use 4.0
    protected boolean mIsUseSurfaceTexture = false;


    String mPendingFilter = "MIC_LENS";
    volatile boolean mIsChangingFilter = false;
    Object mFilterLock = new Object();

    public boolean isAcceptData() {
        return mAcceptData;
    }

    // Begin Fix for bug#9282664
    public void onPause() {
        super.onPause();
    }

    public void onResume() {
        super.onResume();
    }
    // End Fix for bug#9282664

    public void resumePreview() {
    }

    public GLCameraPreview(Context context, AttributeSet attrs) {
        super(context, attrs);
        initial();
    }

    public GLCameraPreview(Context context) {
        super(context);
        initial();
    }

    private void initial() {
        setEGLContextClientVersion(2);
        setEGLConfigChooser(8, 8, 8, 8, 0, 0);// fixed:
        setRenderer(this);

        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);

        getHolder().setFormat(PixelFormat.RGBA_8888);
    }
    
    public void CalculateFPS() {

        if (mImagecount == 0) {
        	mStarttime = System.currentTimeMillis();
        } else if (mImagecount % 10 == 0) {

            long currentTime = System.currentTimeMillis();
            long timeSpan = currentTime - mStarttime;

            int fps = (int) (mImagecount / (timeSpan / 1000.0));

  
        }

        mImagecount++;
        if (mImagecount > 1000) {
        	mImagecount = 0;
        }
    }
   

    public void setFilter(String filterID, final int effectIndex) {
		mImagecount = 0;
		synchronized (mFilterLock) {
			mPendingFilter = filterID;
			if (!mIsChangingFilter) {
				// queue to render thread
				mIsChangingFilter = true;
				queueEvent(new Runnable() {
					@Override
					public void run() {
					    String filter;
						synchronized (mFilterLock) {
							filter = mPendingFilter;
							mIsChangingFilter = false;
						}
						mFilterProces.changeFilter(filter, effectIndex);
					}
				});
			}
		}

	}

    public void setOnChangeFilterListener(OnChangeFilterListener listener) {
        mFilterProces.setOnChangeFilterListener(listener);
    }


    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
//        if (mSnapCount < 4) {
//            canvas.drawRect(0, 0, mWindowWidth, mWindowHeight, mPaint);
//            postInvalidate();
//        }
    }

    public void onDrawFrame(GL10 glUnused) {
        
        if(LogUtil.ENABLE_FPS)
        CalculateFPS();
        // avoid taking photo black screen --weiliang---2012-7-19
        if (mHaveFrame) {
        	/* GT start */
   
        	/* GT end */
        	mFilterProces.showPreview(mWindowWidth, mWindowHeight);
            
            /* GT start */

            /* GT end */
        } else {

            GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
            GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
            GLES20.glFinish();
        }

        mAcceptData = true;
    }
    
	public void clearScreen() {
		mHaveFrame = false;
		requestRender();
	}

    public void onSurfaceChanged(GL10 glUnused, int width, int height) {
        mWindowWidth = width;
        mWindowHeight = height;
        
       
        if(listener != null){
            listener.onSurfaceChanged(width, height);
        }
    }

	@Override
	public void onSurfaceCreated(GL10 glUnused, EGLConfig config) {
		// low end GPU---split preview problem
		if (PhoneProperty.instance().isUseCPUDecodeYUV()) {
			// GL_RENDERER = Adreno (TM) 205 GL_RENDERER = Adreno 200
			// Imagination PowerVR SGX530 PowerVR SGX 530
			// ARM Mail-400
			String gl_renderer = glUnused.glGetString(GL10.GL_RENDERER)
					.toLowerCase();
			if (gl_renderer.contains("adreno") && gl_renderer.contains("200")
					|| gl_renderer.contains("sgx")
					&& gl_renderer.contains("530")) {
				PhoneProperty.instance().setRestrictPreviewData(true);
			}
		}
		 mFilterProces.clear();
	        mFilterProces.intial();
		 if(listener != null){
	            listener.onSurfaceCreatedNotify();
	        }
    }

    

	public void onPreviewPause() {
        mHaveFrame = false;
        if (mFilterProces != null) {
            queueEvent(new Runnable() {
                @Override
                public void run() {
                	mFilterProces.clear();
                }
            });
        }

        this.onPause();
    }

    public void onPreviewResume() {
        this.onResume();
        mHaveFrame = false;
    }

    public void onStartPreview() {
    	mImagecount = 0;
    }

    public void onEndPreview() {
    	mImagecount = 0;
    }

//    private Bitmap makeFilter(Bitmap bitmap, int rotation, int flipY) {
//    	if(bitmap==null)
//    		return null;
//    	long currentTime = System.currentTimeMillis();
//    	
//        int[] textures = new int[1];
//        GLES20.glGenTextures(textures.length, textures, 0);
//        //
//        if (mFilter != null) {
//            mFilter.ApplyGLSLFilter(false, false, false);
//        }
//        
//        if(mFilter instanceof FaceDetectFilter)
//        	((FaceDetectFilter)mFilter).initial(bitmap);
//        
//        GLSLRender.nativeSaveRotationAndFlip();
//        GLSLRender.nativeSetRotationAndFlip(360 - rotation, 0, flipY);
//
//        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
//        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0]);
//        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER,
//                GLES20.GL_NEAREST);
//        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER,
//                GLES20.GL_NEAREST);
//        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S,
//                GLES20.GL_CLAMP_TO_EDGE);
//        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T,
//                GLES20.GL_CLAMP_TO_EDGE);
//        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, GLUtils.getInternalFormat(bitmap), bitmap,
//                GLUtils.getType(bitmap), 0);
//
//        mFilter.OnDrawFrameGLSL();
//        Bitmap result = null;
//        if (rotation % 180 == 0) {
//            GLSLRender.nativeRenderTexture(textures[0], bitmap.getWidth(), bitmap.getHeight(),
//                    -1);
//
//            GLSLRender.nativeCopyPixelToBitmap(bitmap);
//            result = bitmap;
//        } else {
//            int width = bitmap.getHeight();
//            int height = bitmap.getWidth();
//            bitmap.recycle();
//            bitmap = null;
//            result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
//            GLSLRender.nativeRenderTexture(textures[0], width, height, -1);
//            GLSLRender.nativeCopyPixelToBitmap(result);
//        }
//
//        GLES20.glDeleteTextures(textures.length, textures, 0);
//
//        GLSLRender.nativeRestoreRotationAndFlip();
//        if (mFilter != null) {
//            mFilter.ApplyGLSLFilter(mIsUseSurfaceTexture, true, true);
//        }
//        LogUtil.d(this, "makeFilter "+mFilter.getName()+":"+(System.currentTimeMillis()-currentTime));
//        return result;
//    }
    
    

	public void disablePreview() {
		mPausePreview = true;
	}

	public void enablePreview() {
		mPausePreview = false;
	}

	public boolean isDisablePreview() {
		return mPausePreview;
	}
	
	   public void setRotationAndFlip(int rotation, int flipx, int flipy) {
	        mFilterProces.setRotationAndFlip(rotation, flipx, flipy);
	    }

}
