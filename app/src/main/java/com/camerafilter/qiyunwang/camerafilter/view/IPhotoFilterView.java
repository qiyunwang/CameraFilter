package com.camerafilter.qiyunwang.camerafilter.view;

import android.graphics.Bitmap;

import java.util.List;

/**
 * Created by qiyunwang on 16/2/2.
 */
public interface IPhotoFilterView {
    /** 
     * 刷新资源列表资源
     * @param params 照片资源列表
     * */
    void refreshPhotoResources(List<PhotoFilterParam> params);
    
    /** 显示效果图标 */
    void setEffectPhoto(Bitmap bitmap);
    
    /** 显示效果标题 */
    void setEffectTitle(String title);
    
    /** 设置调整参数值 */
    void setAdjustParam(float adjustParam);

    /**
     * 设置照片选中监听器
     * @param listener 图片点击监听对象
     * */
    void setOnPhotoSeleteorListener(OnPhotoSelectorListener listener);

    /**
     * 设置调整参数监听器
     * @param listener 调整参数监听对象
     * */
    void setOnAdjustParamSelectorListener(OnAdjustParamSelectorListener listener);

    /** param class */
    class PhotoFilterParam {
        public String name;
        public String filterEnum;
        public int effectIndex;
        public Bitmap bitmap;

        public PhotoFilterParam(String name, String filterEnum, int effectIndex) {
            this.name = name;
            this.filterEnum = filterEnum;
            this.effectIndex = effectIndex;
        }
    }

    /** 用于监听选中照片时触发回调 */
    interface OnPhotoSelectorListener {
        /** 图片列表点击后触发 */
        void onSelector(PhotoFilterParam param);
    }
    
    /** 用于监听参数调整值 */
    interface OnAdjustParamSelectorListener {
        /** 调整参数值时触发监听 */
        void onAdjustParamSelector(PhotoFilterParam param, float adjustParam);
    }
}
