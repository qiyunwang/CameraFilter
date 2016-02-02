package com.camerafilter.qiyunwang.camerafilter.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.camerafilter.qiyunwang.camerafilter.R;
import com.camerafilter.qiyunwang.camerafilter.view.horizontallistview.HorizontalListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qiyunwang on 16/2/2.
 */
public class PhotoFilterView extends LinearLayout implements IPhotoFilterView {
    private static final String TAG = "PhotoFilterView";

    private HorizontalListView mHorizontalListView;
    private HorizontalListViewAdapter mHorizontalListViewAdapter;
    private OnPhotoSelectorListener mOnPhotoSelectorListener;
    
    public PhotoFilterView(Context context) {
        super(context);
        loadViewLayout();
    }

    public PhotoFilterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        loadViewLayout();
    }

    public PhotoFilterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        loadViewLayout();
    }

    @Override
    public void refreshPhotoResources(List<PhotoFilterParam> params) {
        if(mHorizontalListViewAdapter == null) {
            mHorizontalListViewAdapter = new HorizontalListViewAdapter(getContext(), params);
            mHorizontalListViewAdapter.setOnPhotoSelectListener(mOnPhotoSelectorListener);
            
            if(mHorizontalListView != null) {
                mHorizontalListView.setAdapter(mHorizontalListViewAdapter);
            }
        } else {
            mHorizontalListViewAdapter.notifyDataSetChanged(params);
        }
    }

    @Override
    public void setOnPhotoSeleteorListener(OnPhotoSelectorListener listener) {
        mOnPhotoSelectorListener = listener;
        
        if(mHorizontalListViewAdapter != null) {
            mHorizontalListViewAdapter.setOnPhotoSelectListener(listener);
        }
    }
    
    private void loadViewLayout() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.photo_filter_layout, this);

        mHorizontalListView = (HorizontalListView) findViewById(R.id.horizontal_photo_list);
    }
    
    private static class HorizontalListViewAdapter extends ArrayAdapter<PhotoFilterParam> {
        private static final String TAG = "HorizontalListViewAdapter";
        private LayoutInflater mLayoutInflater;
        
        private List<PhotoFilterParam> mPhotoFilterParams;
        
        private OnPhotoSelectorListener mOnPhotoSelectListener;
        
        public HorizontalListViewAdapter(Context context, List<PhotoFilterParam> params) {
            super(context, R.layout.photo_filter_item, params);
            mLayoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            
            mPhotoFilterParams = new ArrayList<>();
            mPhotoFilterParams.addAll(params);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            
            if(convertView == null) {
                holder = new ViewHolder();
                
                convertView = mLayoutInflater.inflate(R.layout.photo_filter_item, parent, false);
                holder.photoImageView = (ImageView) convertView.findViewById(R.id.image_view);
                
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            
            updateViewData(holder, position);
            
            return convertView;
        }

        @Override
        public int getCount() {
            return mPhotoFilterParams == null ? 0 : mPhotoFilterParams.size();
        }

        @Override
        public PhotoFilterParam getItem(int position) {
            return mPhotoFilterParams.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public void notifyDataSetChanged(List<PhotoFilterParam> params) {
            if(mPhotoFilterParams == null) {
                mPhotoFilterParams = new ArrayList<>();
            } else {
                mPhotoFilterParams.clear();
            }
            
            mPhotoFilterParams.addAll(params);
        }
        
        public void setOnPhotoSelectListener(OnPhotoSelectorListener listener) {
            mOnPhotoSelectListener = listener;
        }
        
        private void updateViewData(ViewHolder holder, int position) {
            PhotoFilterParam param = getItem(position);
            
            if(param == null) {
                Log.d(TAG, "param == null.");
                return;
            }
            
            if(holder == null) {
                Log.d(TAG, "holder == null.");
                return;
            }
            
            if(holder.photoImageView != null && param.bitmap != null) {
                holder.photoImageView.setImageBitmap(param.bitmap);
            }
        }

        private static class ViewHolder {
            public ImageView photoImageView;
        }
    }
}
