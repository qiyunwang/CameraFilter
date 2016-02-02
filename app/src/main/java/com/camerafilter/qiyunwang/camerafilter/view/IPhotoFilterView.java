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
    
    /** 
     * 设置照片选中监听器
     * @param listener 监听图片点击触发回调
     * */
    void setOnPhotoSeleteorListener(OnPhotoSelectorListener listener);
    
    /** 用于监听选中照片时触发回调 */
    interface OnPhotoSelectorListener {
        /** 图片列表点击后触发 */
        void onSelector(PhotoFilterParam param);
    }
    
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
}
