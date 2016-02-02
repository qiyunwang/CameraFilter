package com.camerafilter.qiyunwang.camerafilter.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import com.camerafilter.qiyunwang.camerafilter.R;
import com.camerafilter.qiyunwang.camerafilter.filter.QlippieFilterFactory;
import com.camerafilter.qiyunwang.camerafilter.view.IPhotoFilterView.OnAdjustParamSelectorListener;
import com.camerafilter.qiyunwang.camerafilter.view.IPhotoFilterView.OnPhotoSelectorListener;
import com.camerafilter.qiyunwang.camerafilter.view.IPhotoFilterView.PhotoFilterParam;
import com.camerafilter.qiyunwang.camerafilter.view.PhotoFilterView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qiyunwang on 16/1/28.
 */
public class FilterImageListActivity extends Activity {
    private static final String TAG = "FilterImageListActivity";
    
    private float mAdjustParam = 1.0f;
    
    private PhotoFilterView mPhotoFilterView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPhotoFilterView = new PhotoFilterView(this);
        setContentView(mPhotoFilterView);

        List<PhotoFilterParam> imageFilters = new ArrayList<>();
        imageFilters.add(new PhotoFilterParam("清新美白", "MIC_Portait", 8));
        imageFilters.add(new PhotoFilterParam("甜美可人", "MIC_Portait", 2));
        imageFilters.add(new PhotoFilterParam("白嫩可人", "MIC_Portait", 3));
        imageFilters.add(new PhotoFilterParam("淡雅", "WEICO_FILM", 0));
        imageFilters.add(new PhotoFilterParam("蓝韵(现代)", "MIC_HUDSON", 0));
        imageFilters.add(new PhotoFilterParam("绚丽", "MIC_GLOW", 0));
        imageFilters.add(new PhotoFilterParam("胶片", "MIC_LOMOFILM", 0));
        imageFilters.add(new PhotoFilterParam("秋日私语", "MIC_EARLYBIRD", 0));
        imageFilters.add(new PhotoFilterParam("夏日晨光", "MIC_RISE", 0));
        imageFilters.add(new PhotoFilterParam("美味", "MIC_JINGWU1", 0));
        imageFilters.add(new PhotoFilterParam("经典黑白", "WEICO_BW", 0));

        loadPhotoFilter(imageFilters);
        mPhotoFilterView.setOnPhotoSeleteorListener(mOnPhotoSelectorListener);
        mPhotoFilterView.setOnAdjustParamSelectorListener(mOnAdjustParamSelectorListener);
        mPhotoFilterView.setEffectTitle("");
    }
    
    public void loadPhotoFilter(List<PhotoFilterParam> imageFilters) {
        final int length = imageFilters.size();
        
        for(int position = 0; position < length; position ++) {
            PhotoFilterParam param = imageFilters.get(position);
            param.bitmap = obtainThumbnailPhotoBitmap();;
            Log.d(TAG, "param.bitmap = " + param.bitmap);
            
            renderBitmapFilter(param, position, imageFilters);
        }
    }
    
    public void renderBitmapFilter(PhotoFilterParam param, final int position, final List<PhotoFilterParam> imageFilters) {
        final int length = imageFilters.size();

        asyncRenderBitmap(param.bitmap, param, mAdjustParam, new OnRenderUIThreadCallback() {
            @Override
            public void onUICallback(Bitmap bitmap, PhotoFilterParam param) {
                if(position == length - 1) {
                    mPhotoFilterView.refreshPhotoResources(imageFilters);
                }
            }
        });
    }

    private Bitmap obtainThumbnailPhotoBitmap() {
        Drawable drawable = getResources().getDrawable(R.drawable.qlippie_image);
        Bitmap result = ((BitmapDrawable) drawable).getBitmap();
        //result = ThumbnailUtils.extractThumbnail(result, 100, 100);
        return new BitmapDrawable(result).getBitmap().copy(Config.ARGB_8888, false);
    }

    private void asyncRenderBitmap(final Bitmap bitmap, final PhotoFilterParam param, float adjustParam,final OnRenderUIThreadCallback callback) {
        updateAdjustParam(adjustParam);
        
        QlippieFilterFactory.renderBitmapByFilterIDAsync(bitmap, param.filterEnum, param.effectIndex, adjustParam, new Runnable() {

            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        callback.onUICallback(bitmap, param);
                    }
                });
            }
        });
    }

    private void updateAdjustParam(float adjustParam) {
        if(mPhotoFilterView != null) {
            mPhotoFilterView.setAdjustParam(adjustParam);
        }
    }

    private OnPhotoSelectorListener mOnPhotoSelectorListener = new OnPhotoSelectorListener() {
        @Override
        public void onSelector(PhotoFilterParam param) {
            final Bitmap bitmap = obtainThumbnailPhotoBitmap();
            asyncRenderBitmap(bitmap, param, mAdjustParam, new OnRenderUIThreadCallback() {
                @Override
                public void onUICallback(Bitmap bitmap, PhotoFilterParam param) {
                    if(mPhotoFilterView != null) {
                        mPhotoFilterView.setEffectPhoto(bitmap);
                        mPhotoFilterView.setEffectTitle(param.name);
                    }
                }
            });
        }
    };

    private OnAdjustParamSelectorListener mOnAdjustParamSelectorListener = new OnAdjustParamSelectorListener() {
        @Override
        public void onAdjustParamSelector(PhotoFilterParam param, float adjustParam) {
            if(param == null) {
                Log.d(TAG, "param == null.");
                return;
            }
            
            final Bitmap bitmap = obtainThumbnailPhotoBitmap();
            asyncRenderBitmap(bitmap, param, adjustParam, new OnRenderUIThreadCallback() {
                @Override
                public void onUICallback(Bitmap bitmap, PhotoFilterParam param) {
                    if(mPhotoFilterView != null) {
                        mPhotoFilterView.setEffectPhoto(bitmap);
                        mPhotoFilterView.setEffectTitle(param.name);
                    }
                }
            });
        }
    };
    
    private interface OnRenderUIThreadCallback {
        void onUICallback(Bitmap bitmap, PhotoFilterParam param);
    }
}
