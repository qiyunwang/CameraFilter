package com.camerafilter.qiyunwang.camerafilter.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
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
    private OnAdjustParamSelectorListener mOnAdjustParamSelectorListener;
    
    private ImageView mEffectPhotoView;
    private TextView mEffectTitleView;
    
    private SeekBar mAdjustSeekBar;
    private TextView mAdjustSeekValue;

    private PhotoFilterParam mCurrentFilterParam;

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
    }

    @Override
    public void setOnAdjustParamSelectorListener(OnAdjustParamSelectorListener listener) {
        mOnAdjustParamSelectorListener = listener;
    }

    @Override
    public void setEffectPhoto(Bitmap bitmap) {
        if(mEffectPhotoView != null) {
            mEffectPhotoView.setImageBitmap(bitmap);
        }
    }

    @Override
    public void setEffectTitle(String title) {
        if(mEffectTitleView != null) {
            mEffectTitleView.setText(title);
        }
    }

    @Override
    public void setAdjustParam(float adjustParam) {
        if(mAdjustSeekBar != null && mCurrentFilterParam != null) {
            float current = (float)mAdjustSeekBar.getProgress() / 100;
            
            if(current != adjustParam) {
                mAdjustSeekBar.setProgress((int) (100 * adjustParam));
            }
        }
    }

    private void loadViewLayout() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.photo_filter_layout, this);

        mHorizontalListView = (HorizontalListView) findViewById(R.id.horizontal_photo_list);
        mEffectPhotoView = (ImageView) findViewById(R.id.effect_photo);
        mEffectTitleView = (TextView) findViewById(R.id.effect_title); 
        
        mAdjustSeekBar = (SeekBar) findViewById(R.id.adjust_param_seek);
        mAdjustSeekValue = (TextView) findViewById(R.id.adjust_param_seek_value);
        
        mHorizontalListView.setOnItemClickListener(mOnItemClickListener);
        mAdjustSeekBar.setOnSeekBarChangeListener(mOnSeekBarChangListener);
    }
    
    private OnItemClickListener mOnItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if(mOnPhotoSelectorListener != null) {
                if(mHorizontalListViewAdapter != null) {
                    List<PhotoFilterParam> params = mHorizontalListViewAdapter.obtainPhotoFilterParams();
                    
                    if(params != null && params.size() > position) {
                        mCurrentFilterParam = params.get(position);
                        mOnPhotoSelectorListener.onSelector(mCurrentFilterParam);
                    }
                }
            }
        }
    };

    private OnSeekBarChangeListener mOnSeekBarChangListener = new OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if(mAdjustSeekValue != null) {
                mAdjustSeekValue.setText(progress + "");
                
                if(mOnAdjustParamSelectorListener != null) {
                    mOnAdjustParamSelectorListener.onAdjustParamSelector(mCurrentFilterParam, (float)progress / 100);
                }
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };
    
    private static class HorizontalListViewAdapter extends ArrayAdapter<PhotoFilterParam> {
        private static final String TAG = "HorizontalListViewAdapter";
        private LayoutInflater mLayoutInflater;
        
        private List<PhotoFilterParam> mPhotoFilterParams;
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

        public List<PhotoFilterParam> obtainPhotoFilterParams() {
            return mPhotoFilterParams;
        }
        
        public void notifyDataSetChanged(List<PhotoFilterParam> params) {
            if(mPhotoFilterParams == null) {
                mPhotoFilterParams = new ArrayList<>();
            } else {
                mPhotoFilterParams.clear();
            }
            
            mPhotoFilterParams.addAll(params);
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
                holder.photoImageView.setTag(position);
            }
        }
        
        private static class ViewHolder {
            public ImageView photoImageView;
        }
    }
}
