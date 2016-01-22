/*
 * Copyright (C) 2012 CyberAgent
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.camerafilter.qiyunwang.camerafilter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import java.util.LinkedList;
import java.util.List;

public class GPUImageFilterTools {  
    
    public interface OnGpuImageFilterChosenListener {
        void onGpuImageFilterChosenListener(String filterName, String filterID, int effectindex);
    }
    
    private static class FilterList {
        public List<String> names = new LinkedList<String>();
        public List<String> filters = new LinkedList<String>();
        public List<Integer> effectIndex = new LinkedList<Integer>();
        public void addFilter(final String name, final String filter) {
            names.add(name);
            filters.add(filter);
            effectIndex.add(0);
        }
        public void addFilter(final String name, final String filter, final Integer filterIndex) {
            names.add(name);
            filters.add(filter);
            effectIndex.add(filterIndex);
        }
    }
    
    final static FilterList realtimefilters = new FilterList();
    static {
        
        realtimefilters.addFilter("彩铅", "MIC_COLORPENCIL",0);
        realtimefilters.addFilter("素描", "MIC_NEWSKETCH",0);
        realtimefilters.addFilter("夏日晨光", "MIC_RISE",0);
        realtimefilters.addFilter("温情", "WEICO_LOFT",0);
        realtimefilters.addFilter("拍立得", "MIC_INSTANT",0);
        realtimefilters.addFilter("LOMO", "MIC_DARKLOMO",0);
        realtimefilters.addFilter("同桌的你", "MIC_WALDEN",0);
        realtimefilters.addFilter("清逸", "MIC_AMARO",0);
        realtimefilters.addFilter("蓝韵(现代)", "MIC_HUDSON",0);
        realtimefilters.addFilter("经典相纸", "MIC_XPRO2",0);
        realtimefilters.addFilter("风景浓郁", "MIC_LOFI",0);
        realtimefilters.addFilter("明亮", "WEICO_SUN",0);
        realtimefilters.addFilter("热情", "MIC_TOASTER",0);
        realtimefilters.addFilter("秋日私语", "MIC_EARLYBIRD",0);
        realtimefilters.addFilter("淡雅", "WEICO_FILM",0);
        
        realtimefilters.addFilter("胶片", "MIC_LOMOFILM",0);
        realtimefilters.addFilter("经典黑白", "WEICO_BW",0);
        realtimefilters.addFilter("怀旧", "MIC_HEFEI",0);
        realtimefilters.addFilter("纪录片", "MIC_SHARE_FILM",0);
        realtimefilters.addFilter("童话", "MIC_NASHVILLE",0);
        realtimefilters.addFilter("美味", "MIC_JINGWU1",0);
        realtimefilters.addFilter("山色(戴安娜)", "MIC_DIANA",0);
        realtimefilters.addFilter("紫罗兰", "WEICO_VIOLET",0);
        realtimefilters.addFilter("洛丽塔", "MIC_ABAO",0);

    }
    final static FilterList filters = new FilterList();
    static {
        filters.addFilter("无效果", "MIC_LENS",0);
        
        for (int i = 0; i < realtimefilters.filters.size(); i++) {
            filters.addFilter(realtimefilters.names.get(i), realtimefilters.filters.get(i),realtimefilters.effectIndex.get(i));
        }

        filters.addFilter("绚丽", "MIC_GLOW", 0);
        filters.addFilter("清新美白", "MIC_Portait", 8);
        filters.addFilter("甜美可人", "MIC_Portait", 2);
        filters.addFilter("暖暖", "MIC_WARM", 0);
        filters.addFilter("白嫩可人", "MIC_Portait", 3);
        filters.addFilter("小清新 ", "MIC_Portait", 1);
        filters.addFilter("天生丽质", "MIC_Portait", 5);
        filters.addFilter("粉嫩", "MIC_Portait", 7);
        filters.addFilter("阳光", "MIC_Portait", 101);
        filters.addFilter("香艳红唇", "MIC_Portait", 4);
        filters.addFilter("光滑皮肤", "MIC_Portait", 0);
        filters.addFilter("红润", "MIC_Portait", 102);
        filters.addFilter("美肤", "MIC_Portait", 6);
    }
    
    
   
   
   
    static int step = 0;
    public static void generateNextFilter(final Context context, final OnGpuImageFilterChosenListener listener){
        listener.onGpuImageFilterChosenListener(realtimefilters.names.get(step),realtimefilters.filters.get(step),realtimefilters.effectIndex.get(step));
        step ++;
        step = step%realtimefilters.filters.size(); 
    }

    
    
    public static void showDialog(final Context context,
            final OnGpuImageFilterChosenListener listener) {
       
	

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Choose a filter");
        builder.setItems(filters.names.toArray(new String[filters.names.size()]),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, final int item) {
                        listener.onGpuImageFilterChosenListener(filters.names.get(item),filters.filters.get(item),filters.effectIndex.get(item));
                    }
                });
        builder.create().show();
    }
   
}
