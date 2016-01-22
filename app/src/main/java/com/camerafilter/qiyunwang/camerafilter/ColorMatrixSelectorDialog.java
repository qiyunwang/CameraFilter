package com.camerafilter.qiyunwang.camerafilter;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import java.io.InterruptedIOException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by qiyunwang on 16/1/22.
 */
public class ColorMatrixSelectorDialog extends Dialog {
    private Context mContext;
    private TextView[] mColumnValueViews;
    private SeekBar[] mColumnSeekBars;
    private float[] mColorMatrix;
    
    private OnColorSelectorChangeListener mOnColorSelectorChangeListener;
    
    public ColorMatrixSelectorDialog(Context context) {
        super(context, R.style.CustomDialog);
    }
    
    public ColorMatrixSelectorDialog(Context context, int theme) {
        super(context, theme);     
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    
        setContentView(R.layout.color_matrix_layout);
        
        getWindow().setGravity(Gravity.CENTER);
        getWindow().setLayout(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);

        List<Integer> rowIds = Arrays.asList(R.id.color_matrix_row_red, R.id.color_matrix_row_green, 
                R.id.color_matrix_row_bule, R.id.color_matrix_row_transparent);
        
        List<Integer> columnInds = Arrays.asList(R.id.color_matrix_column_red, R.id.color_matrix_column_green, 
                R.id.color_matrix_column_bule, R.id.color_matrix_column_transparent);
        
        List<String> itemValues = Arrays.asList("R", "G", "B", "A");
        List<String> rowTitleValues = Arrays.asList("Red", "Green", "Blue", "Transparent");

        int length = rowIds.size() * columnInds.size();
        mColumnValueViews = new TextView[length];
        mColumnSeekBars = new SeekBar[length];
        
        for(int i = 0; i < rowIds.size(); i ++) {
            View matrixRowView = findViewById(rowIds.get(i));
            ((TextView)matrixRowView.findViewById(R.id.matrix_row_title)).setText(rowTitleValues.get(i));
            
            for(int j = 0; j < columnInds.size(); j ++) {
                View matrixColumnView = matrixRowView.findViewById(columnInds.get(j));
                
                String itemTitleValue = itemValues.get(i) + itemValues.get(j) + ":";
                ((TextView) matrixColumnView.findViewById(R.id.color_selector_item_title)).setText(itemTitleValue);
                
                int index = (i * rowIds.size()) + j;
                mColumnValueViews[index] = ((TextView) matrixColumnView.findViewById(R.id.color_selector_item_value));
                mColumnValueViews[index].setText("0");

                mColumnSeekBars[index] = ((SeekBar) matrixColumnView.findViewById(R.id.color_selector_item_seek));
                mColumnSeekBars[index].setOnSeekBarChangeListener(mOnSeekBarChangeListener);
                mColumnSeekBars[index].setTag(index);
            }
        }
    }

    public void updateSeeks(float[] colorMatrix) {
        for(int i = 0; i < colorMatrix.length; i++) {
            mColorMatrix = colorMatrix;
            mColumnSeekBars[i].setProgress((int)(colorMatrix[i] * 100));
            mColumnValueViews[i].setText(mColumnSeekBars[i].getProgress() + "");
        }
    }
    
    public void setOnColorSelectorChangeListener(OnColorSelectorChangeListener listener) {
        mOnColorSelectorChangeListener = listener;
    }
    
    private OnSeekBarChangeListener mOnSeekBarChangeListener = new OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            int index = Integer.parseInt(seekBar.getTag().toString());
            mColumnValueViews[index].setText(seekBar.getProgress() + "");
            
            if(mOnColorSelectorChangeListener != null) {
                mColorMatrix[index] = seekBar.getProgress() / 100;
                mOnColorSelectorChangeListener.onColorChange(mColorMatrix);
            }
        }
    };
    
    public interface OnColorSelectorChangeListener {
        void onColorChange(float[] colorMatrix);
    }
}
