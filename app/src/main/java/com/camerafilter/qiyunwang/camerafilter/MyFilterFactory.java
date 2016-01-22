package com.camerafilter.qiyunwang.camerafilter;

import android.graphics.Bitmap;
import com.microrapid.lensFlare.LensFlareCpuFilter;
import com.microrapid.opencv.BokehFilter;
import com.microrapid.opencv.DepthFilter;
import com.microrapid.opencv.GlowForgCpuFilter;
import com.tencent.filter.AlphaAdjustFilter;
import com.tencent.filter.BaibianFilter;
import com.tencent.filter.BaseFilter;
import com.tencent.filter.BeautysFilter;
import com.tencent.filter.CartoonEditorFilter;
import com.tencent.filter.CartoonFilter;
import com.tencent.filter.ColorFilterSH;
import com.tencent.filter.Curve2D;
import com.tencent.filter.DarkCornerCurve2D;
import com.tencent.filter.DeconvSharpenFilter;
import com.tencent.filter.DofCpuFilter;
import com.tencent.filter.DpiLensFilter;
import com.tencent.filter.FlaresFilter;
import com.tencent.filter.Frame;
import com.tencent.filter.FrameMontageFilter;
import com.tencent.filter.GLSLRender;
import com.tencent.filter.HDRHSVFilter;
import com.tencent.filter.HdrFilter;
import com.tencent.filter.InkFilter;
import com.tencent.filter.InstantFilter;
import com.tencent.filter.MangaFilter;
import com.tencent.filter.MayFairFilter;
import com.tencent.filter.NightRGBStretchFilter;
import com.tencent.filter.OilPaintFilter;
import com.tencent.filter.OilPaintNewFilter;
import com.tencent.filter.Param.TextureResParam;
import com.tencent.filter.PosterFilter;
import com.tencent.filter.PrintFilter;
import com.tencent.filter.QImage;
import com.tencent.filter.ShareFilm_1;
import com.tencent.filter.ttpic.TTPicFilterFactory;
import com.tencent.view.FilterEngineFactory;
import com.tencent.view.FilterFactory;
import com.tencent.view.Photo;
import com.tencent.view.RendererUtils;

import java.util.HashMap;

/**
 * Created by qiyunwang on 16/1/21.
 */
public class MyFilterFactory extends FilterFactory {
    public static BaseFilter createFilter(int filterEnum) {
        Object filter = null;
        float[] identityMat;
        switch(filterEnum) {
            case 0:
                filter = new BaseFilter(GLSLRender.FILTER_SHADER_NONE);
                break;
            case 1:
                filter = new AlphaAdjustFilter(GLSLRender.FILTER_ALPHA_ADJUST);
                break;
            case 2:
                filter = new BeautysFilter(GLSLRender.FILTER_SHADER_NONE, 1);
                break;
            case 3:
                filter = new HdrFilter();
                break;
            case 4:
                filter = new Curve2D("rise.png");
                break;
            case 5:
                filter = new BeautysFilter(GLSLRender.FILTER_SHADER_NONE, 0);
                break;
            case 6:
                filter = new Curve2D("qingyi.png");
                break;
            case 7:
                filter = new Curve2D("hudson.png");
                break;
            case 8:
                filter = new Curve2D("kafei.png");
                break;
            case 9:
                filter = new BaibianFilter(GLSLRender.FILTER_BB_JINGWU, "bb_jingwu.png", 1.0F, 0.4F, 1.0F, 1.22F, 1.0F);
                break;
            case 10:
                filter = new BaibianFilter(GLSLRender.FILTER_BB_JINGWU, "bb_jingwu.png", 1.0F, 0.4F, 1.0F, 1.0F, 1.0F);
                break;
            case 11:
                filter = new BaibianFilter(GLSLRender.FILTER_BB_MINGLIANG, "bb_mingliang.png", 1.0F, 0.4F, 1.0F, 1.12F, 1.02F);
                break;
            case 12:
                filter = new BaibianFilter(GLSLRender.FILTER_SHADER_NONE, (String)null, 0.3F, 0.5F, 0.0F, 0.12F, 0.02F);
                break;
            case 13:
                filter = new FlaresFilter(0);
                break;
            case 14:
                filter = new Curve2D("nongyu.png");
                break;
            case 15:
                identityMat = new float[]{1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F};
                filter = new DarkCornerCurve2D(identityMat, 0.5F, 1.0F);
                ((BaseFilter)filter).addParam(new TextureResParam("inputImageTexture2", "lomofilm.png", '蓂'));
                break;
            case 16:
                filter = new Curve2D("xpro2.png");
                break;
            case 17:
                identityMat = new float[]{1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F};
                filter = new DarkCornerCurve2D(identityMat, 0.35F, 1.721429F);
                ((BaseFilter)filter).addParam(new TextureResParam("inputImageTexture2", "daianna.png", '蓂'));
                break;
            case 18:
                identityMat = new float[]{0.299F, 0.299F, 0.299F, 0.0F, 0.587F, 0.587F, 0.587F, 0.0F, 0.114F, 0.114F, 0.114F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F};
                filter = new DarkCornerCurve2D(identityMat, 0.275F, 1.4004412F);
                ((BaseFilter)filter).addParam(new TextureResParam("inputImageTexture2", "qingseniandai.png", '蓂'));
                break;
            case 19:
                identityMat = new float[]{0.5326F, 0.1993F, 0.1993F, 0.0F, 0.391F, 0.7243F, 0.391F, 0.0F, 0.076F, 0.076F, 0.4093F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F};
                filter = new DarkCornerCurve2D(identityMat, 0.2F, 1.8F);
                ((BaseFilter)filter).addParam(new TextureResParam("inputImageTexture2", "country.png", '蓂'));
                break;
            case 20:
                identityMat = new float[]{0.6495F, 0.1495F, 0.1495F, 0.0F, 0.294F, 0.794F, 0.294F, 0.0F, 0.057F, 0.057F, 0.557F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F};
                filter = new DarkCornerCurve2D(identityMat, 0.175F, 1.0154F);
                ((BaseFilter)filter).addParam(new TextureResParam("inputImageTexture2", "lake.png", '蓂'));
                break;
            case 21:
                identityMat = new float[]{1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F};
                filter = new DarkCornerCurve2D(identityMat, 0.35F, 1.721429F);
                ((BaseFilter)filter).addParam(new TextureResParam("inputImageTexture2", "instant.png", '蓂'));
                break;
            case 22:
                identityMat = new float[]{1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F};
                filter = new DarkCornerCurve2D(identityMat, 0.3F, 1.490898F);
                ((BaseFilter)filter).addParam(new TextureResParam("inputImageTexture2", "lomo.png", '蓂'));
                break;
            case 23:
                identityMat = new float[]{1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F};
                filter = new DarkCornerCurve2D(identityMat, 0.35F, 1.721429F);
                ((BaseFilter)filter).addParam(new TextureResParam("inputImageTexture2", "pro.png", '蓂'));
                break;
            case 24:
                filter = new Curve2D("shishang.png");
                identityMat = new float[]{0.5326F, 0.1993F, 0.1993F, 0.0F, 0.391F, 0.7243F, 0.391F, 0.0F, 0.076F, 0.076F, 0.4093F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F};
                ((Curve2D)filter).setMatrix(identityMat);
                break;
            case 25:
                filter = new Curve2D("huanxiang.png");
                identityMat = new float[]{0.5326F, 0.1993F, 0.1993F, 0.0F, 0.391F, 0.7243F, 0.391F, 0.0F, 0.076F, 0.076F, 0.4093F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F};
                ((Curve2D)filter).setMatrix(identityMat);
                break;
            case 26:
                filter = new Curve2D("gudian.png");
                identityMat = new float[]{0.5326F, 0.1993F, 0.1993F, 0.0F, 0.391F, 0.7243F, 0.391F, 0.0F, 0.076F, 0.076F, 0.4093F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F};
                ((Curve2D)filter).setMatrix(identityMat);
                break;
            case 27:
                filter = new Curve2D("qiurisiyu.png");
                break;
            case 28:
                filter = new Curve2D("nashville.png");
                break;
            case 29:
                filter = new Curve2D("valencia.png");
                break;
            case 30:
                filter = new Curve2D("toaster.png");
                break;
            case 31:
                Curve2D filter1 = new Curve2D(GLSLRender.FILTER_QQ_ROWFILM, "tonny01.png");
                identityMat = new float[]{0.965F, 0.015F, 0.015F, 0.0F, 0.03F, 0.97999996F, 0.03F, 0.0F, 0.005F, 0.005F, 0.955F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F};
                ((Curve2D)filter1).setMatrix(identityMat);
            case 107:
                filter = new Curve2D(GLSLRender.FILTER_SHADER_SKETCH, "sketch.png");
                identityMat = new float[]{0.299F, 0.299F, 0.299F, 0.0F, 0.587F, 0.587F, 0.587F, 0.0F, 0.114F, 0.114F, 0.114F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F};
                ((Curve2D)filter).setMatrix(identityMat);
                break;
            case 32:
                filter = new Curve2D("f1977.png");
                break;
            case 33:
                filter = new Curve2D("kelvin.png");
                break;
            case 34:
                filter = new Curve2D("qingxiheibai.png");
                identityMat = new float[]{0.299F, 0.299F, 0.299F, 0.0F, 0.587F, 0.587F, 0.587F, 0.0F, 0.114F, 0.114F, 0.114F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F};
                ((Curve2D)filter).setMatrix(identityMat);
                break;
            case 35:
                filter = new Curve2D("walden.png");
                break;
            case 36:
                filter = new Curve2D("brannan.png");
                identityMat = new float[]{0.76633334F, 0.09966666F, 0.09966666F, 0.0F, 0.19566667F, 0.86233336F, 0.19566667F, 0.0F, 0.038F, 0.038F, 0.7046667F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F};
                ((Curve2D)filter).setMatrix(identityMat);
                break;
            case 37:
                filter = new Curve2D("food.jpg");
                break;
            case 38:
                filter = new Curve2D("paintink.png");
                identityMat = new float[]{0.299F, 0.299F, 0.299F, 0.0F, 0.587F, 0.587F, 0.587F, 0.0F, 0.114F, 0.114F, 0.114F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F};
                ((Curve2D)filter).setMatrix(identityMat);
                break;
            case 39:
                filter = new Curve2D("sierra.png");
                break;
            case 40:
                filter = new Curve2D("sutro.png");
                break;
            case 41:
            case 42:
            case 43:
            case 44:
            case 45:
            case 46:
            case 47:
            case 48:
            case 49:
            case 50:
            case 51:
            case 52:
            case 53:
            case 54:
            case 55:
            case 56:
            case 57:
            case 58:
            case 59:
            case 60:
            case 61:
            case 62:
            case 63:
            case 64:
            case 65:
            case 66:
            case 67:
            case 68:
            case 69:
            case 78:
            case 79:
            case 84:
            case 108:
            case 111:
            case 112:
            case 113:
            case 114:
            case 115:
            case 116:
            case 118:
            case 123:
            case 127:
            case 128:
            case 129:
            case 130:
            case 131:
            case 152:
            case 153:
            case 154:
            case 155:
            case 156:
            case 157:
            case 158:
            case 159:
            case 160:
            case 161:
            case 162:
            case 163:
            case 164:
            case 165:
            case 166:
            case 167:
            case 168:
            case 169:
            case 170:
            case 171:
            case 172:
            case 173:
            case 174:
            case 175:
            case 176:
            case 177:
            case 178:
            case 179:
            case 180:
            case 181:
            case 182:
            case 183:
            case 184:
            case 185:
            case 186:
            case 187:
            case 188:
            case 189:
            case 190:
            case 191:
            case 192:
            case 193:
            case 194:
            case 195:
            case 196:
            case 197:
            case 198:
            case 199:
            case 200:
            case 201:
            case 204:
            case 205:
            case 206:
            case 207:
            case 208:
            case 209:
            case 210:
            case 211:
            case 212:
            case 213:
            default:
                filter = TTPicFilterFactory.creatFilterById(filterEnum);
                break;
            case 70:
                filter = new Curve2D("ziluolan.png");
                break;
            case 71:
                filter = new Curve2D("jingdianheibai.png");
                identityMat = new float[]{0.299F, 0.299F, 0.299F, 0.0F, 0.587F, 0.587F, 0.587F, 0.0F, 0.114F, 0.114F, 0.114F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F};
                ((Curve2D)filter).setMatrix(identityMat);
                break;
            case 72:
                filter = new Curve2D("danya.png");
                identityMat = new float[]{0.825F, 0.075F, 0.075F, 0.0F, 0.147F, 0.897F, 0.147F, 0.0F, 0.029F, 0.029F, 0.779F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F};
                ((Curve2D)filter).setMatrix(identityMat);
                break;
            case 73:
                filter = new Curve2D("shenchen.png");
                identityMat = new float[]{0.5326F, 0.1993F, 0.1993F, 0.0F, 0.391F, 0.7243F, 0.391F, 0.0F, 0.076F, 0.076F, 0.4093F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F};
                ((Curve2D)filter).setMatrix(identityMat);
                break;
            case 74:
                filter = new Curve2D("loft.png");
                identityMat = new float[]{0.825F, 0.075F, 0.075F, 0.0F, 0.147F, 0.897F, 0.147F, 0.0F, 0.029F, 0.029F, 0.779F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F};
                ((Curve2D)filter).setMatrix(identityMat);
                break;
            case 75:
                filter = new Curve2D("yinzhuang.png");
                identityMat = new float[]{0.299F, 0.299F, 0.299F, 0.0F, 0.587F, 0.587F, 0.587F, 0.0F, 0.114F, 0.114F, 0.114F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F};
                ((Curve2D)filter).setMatrix(identityMat);
                break;
            case 76:
                filter = new Curve2D("wangshi.png");
                identityMat = new float[]{0.5326F, 0.1993F, 0.1993F, 0.0F, 0.391F, 0.7243F, 0.391F, 0.0F, 0.076F, 0.076F, 0.4093F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F};
                ((Curve2D)filter).setMatrix(identityMat);
                break;
            case 77:
                filter = new Curve2D("mingliang.png");
                break;
            case 80:
                filter = new HDRHSVFilter();
                break;
            case 81:
                filter = new NightRGBStretchFilter();
                break;
            case 82:
                filter = new BokehFilter();
                break;
            case 83:
                filter = new BaseFilter(GLSLRender.FILTER_ABAO);
                ((BaseFilter)filter).addParam(new TextureResParam("inputImageTexture2", "abaofilter.png", '蓂'));
                break;
            case 85:
                filter = new BaseFilter(GLSLRender.FILTER_WARM, "warmfilter.png");
                break;
            case 86:
                filter = new BaseFilter(GLSLRender.FILTER_LITTLE_SUN_SHINE, "yangguangwuyu.jpg");
                break;
            case 87:
                filter = new BaseFilter(GLSLRender.FILTER_COLORPENCIL, "colorPencil.png");
                break;
            case 88:
                filter = new BaseFilter(GLSLRender.FILTER_NEW_SKETCH, "sketch.png");
                break;
            case 89:
                filter = new BaseFilter(GLSLRender.FILTER_NEWABAO, "NewAbao.png");
                break;
            case 90:
                filter = new BaseFilter(GLSLRender.FILTER_MIC_DRYINK);
                break;
            case 91:
                filter = new BaseFilter(GLSLRender.FILTER_MARK_THOSE_YEAR, "cengjing.bmp");
                break;
            case 92:
                filter = new BaseFilter(GLSLRender.FILTER_MARK_COLOR);
                break;
            case 93:
                filter = new BaseFilter(GLSLRender.FILTER_MARK);
                break;
            case 94:
                filter = new CartoonFilter(0);
                break;
            case 95:
                filter = new CartoonFilter(1);
                break;
            case 96:
                filter = new CartoonFilter(2);
                break;
            case 97:
                filter = new CartoonEditorFilter(0);
                break;
            case 98:
                filter = new CartoonEditorFilter(1);
                break;
            case 99:
                filter = new OilPaintFilter(0);
                break;
            case 100:
                filter = new OilPaintFilter(1);
                break;
            case 101:
                filter = new OilPaintNewFilter(0, 5, 15);
                break;
            case 102:
                filter = new OilPaintNewFilter(1, 3, 255);
                break;
            case 103:
                filter = new InkFilter(0);
                break;
            case 104:
                filter = new InkFilter(1);
                break;
            case 105:
                filter = new BaseFilter(GLSLRender.FILTER_SHADER_PAINT);
                break;
            case 106:
                filter = new PrintFilter();
                break;
            case 109:
                filter = new DepthFilter();
                break;
            case 110:
                filter = new DofCpuFilter(0);
                break;
            case 117:
                filter = new AlphaAdjustFilter(GLSLRender.FILTER_ALPHA_ADJUST_REAL);
                break;
            case 119:
                filter = new DofCpuFilter(1);
                break;
            case 120:
                filter = new PosterFilter(0);
                break;
            case 121:
                filter = new MangaFilter(0);
                break;
            case 122:
                filter = new FrameMontageFilter(0);
                break;
            case 124:
                filter = new DeconvSharpenFilter(GLSLRender.FILTER_SHADER_NONE, (String)null, 0.3F, 0.5F, 0.0F, 0.12F, 0.02F);
                break;
            case 125:
                filter = new DpiLensFilter();
                break;
            case 126:
                filter = new GlowForgCpuFilter();
                break;
            case 132:
                filter = new BaseFilter(GLSLRender.FILTER_FISHEYE);
                break;
            case 133:
                filter = new BaseFilter(GLSLRender.FILTER_FLEX);
                break;
            case 134:
                filter = new BaseFilter(GLSLRender.FILTER_KALEIDOSCOPE);
                break;
            case 135:
                filter = new BaseFilter(GLSLRender.FILTER_MIRRO_V);
                break;
            case 136:
                filter = new BaseFilter(GLSLRender.FILTER_MIRRO_H);
                break;
            case 137:
                filter = new BaseFilter(GLSLRender.FILTER_SQUEEZE);
                break;
            case 138:
                filter = new BaseFilter(GLSLRender.FILTER_TIMECHANNEL);
                break;
            case 139:
                filter = new BaseFilter(GLSLRender.FILTER_THERMALIMAGE);
                break;
            case 140:
                filter = new BaseFilter(GLSLRender.FILTER_XRAY);
                break;
            case 141:
                filter = new BaseFilter(GLSLRender.FILTER_TWIRL);
                break;
            case 142:
                filter = new MayFairFilter();
                break;
            case 143:
                filter = new InstantFilter();
                break;
            case 144:
                filter = new BaseFilter(GLSLRender.FILTER_SHADER_AMARO2);
                ((BaseFilter)filter).addParam(new TextureResParam("inputImageTexture2", "amarobb.png", '蓂'));
                ((BaseFilter)filter).addParam(new TextureResParam("inputImageTexture3", "amarooverlaymap.png", '蓃'));
                ((BaseFilter)filter).addParam(new TextureResParam("inputImageTexture4", "amaromap.png", '蓄'));
                break;
            case 145:
                filter = new Curve2D("diana+.png");
                break;
            case 146:
                filter = new Curve2D("1949.png");
                identityMat = new float[]{0.6495F, 0.1495F, 0.1495F, 0.0F, 0.294F, 0.794F, 0.294F, 0.0F, 0.057F, 0.057F, 0.557F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F};
                ((Curve2D)filter).setMatrix(identityMat);
                break;
            case 147:
                filter = new Curve2D("holga.png");
                break;
            case 148:
                filter = new Curve2D("forest.png");
                break;
            case 149:
                filter = new Curve2D("oldschool.png");
                identityMat = new float[]{0.825F, 0.075F, 0.075F, 0.0F, 0.147F, 0.897F, 0.147F, 0.0F, 0.029F, 0.029F, 0.779F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F};
                ((Curve2D)filter).setMatrix(identityMat);
                break;
            case 150:
                filter = new Curve2D("weico_instant.png");
                break;
            case 151:
                filter = new Curve2D("london.png");
                identityMat = new float[]{0.5326F, 0.1993F, 0.1993F, 0.0F, 0.391F, 0.7243F, 0.391F, 0.0F, 0.076F, 0.076F, 0.4093F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F};
                ((Curve2D)filter).setMatrix(identityMat);
                break;
            case 202:
                filter = new ColorFilterSH();
                break;
            case 203:
                filter = new LensFlareCpuFilter();
                break;
            case 214:
                filter = new ShareFilm_1();
        }

        return (BaseFilter)filter;
    }

    public static boolean isSupportOrignalProcess(int filterId) {
        switch(filterId) {
            case 94:
            case 95:
            case 96:
            case 97:
            case 98:
            case 99:
            case 100:
            case 101:
            case 102:
            case 103:
            case 104:
                return false;
            default:
                return true;
        }
    }

    public static void renderBitmapByFilterID(Bitmap src, int filterId, int effectIndex, float adjustParam) {
        BaseFilter newfilter = createFilter(filterId);
        HashMap param = new HashMap();
        param.put("effectIndex", Integer.valueOf(effectIndex));
        newfilter.setParameterDic(param);
        param.clear();
        if(newfilter.isAdjustFilter()) {
            newfilter.setAdjustParam(adjustParam);
        }

        QImage srcImage;
        if(newfilter.isGPUProcess()) {
            newfilter.ApplyGLSLFilter(true, (float)src.getWidth(), (float)src.getHeight());
            if(!newfilter.isAdjustFilter() && adjustParam < 1.0F) {
                BaseFilter qimage = createFilter(117);
                newfilter.getLastFilter().setNextFilter(qimage, new int[]{-1});
                qimage.setAdjustParam(adjustParam);
                qimage.ApplyGLSLFilter(true, (float)src.getWidth(), (float)src.getHeight());
                qimage = null;
            }

            Frame qimage1 = new Frame();
            if(newfilter.isSupportForGloableRender()) {
                srcImage = QImage.BindBitmap(src);
                newfilter.RendProcessImage(srcImage, qimage1);
                srcImage.UnBindBitmap(src);
            } else {
                Photo srcImage1 = Photo.createWithoutRecycle(src);
                newfilter.RenderProcess(srcImage1.texture(), srcImage1.width(), srcImage1.height(), srcImage1.texture(), 0.0D, qimage1);
                RendererUtils.saveTextureToBitmap(srcImage1.texture(), srcImage1.width(), srcImage1.height(), src);
                srcImage1.clear();
            }

            newfilter.ClearGLSL();
            qimage1.clear();
        } else {
            QImage qimage2 = QImage.Bitmap2QImage(src);
            newfilter.ApplyFilter(qimage2);
            if(!newfilter.isAdjustFilter() && adjustParam < 1.0F) {
                srcImage = QImage.Bitmap2QImage(src);
                BaseFilter alphaflier = createFilter(1);
                alphaflier.setAdjustParam(adjustParam);
                alphaflier.ApplyGLSLFilter(true, (float)qimage2.getWidth(), (float)qimage2.getHeight());
                int filterResult = RendererUtils.createTexture();
                GLSLRender.nativeTextImage(qimage2, filterResult);
                alphaflier.setTextureParam(filterResult, 0);
                Frame frame = new Frame();
                newfilter.RendProcessImage(srcImage, frame);
                frame.clear();
                alphaflier.ClearGLSL();
                RendererUtils.clearTexture(filterResult);
                srcImage.ToBitmap(src);
                srcImage.Dispose();
            } else {
                qimage2.ToBitmap(src);
            }

            qimage2.Dispose();
        }

    }

    public static void renderBitmapByFilterIDSync(final Bitmap src, final int filterId, final int effectIndex, final float adjustParam) {
        Runnable runable = new Runnable() {
            public void run() {
                FilterEngineFactory.getInstance().usecurruntContext();
                FilterFactory.renderBitmapByFilterID(src, filterId, effectIndex, adjustParam);
                synchronized(this) {
                    this.notify();
                }
            }
        };
        FilterEngineFactory.getInstance().queue(runable);
        synchronized(runable) {
            try {
                runable.wait();
            } catch (InterruptedException var8) {
                var8.printStackTrace();
            }

        }
    }

    public static void renderBitmapByFilterIDAsync(final Bitmap src, final int filterId, final int effectIndex, final float adjustParam, final Runnable run) {
        Runnable runable = new Runnable() {
            public void run() {
                FilterEngineFactory.getInstance().usecurruntContext();
                FilterFactory.renderBitmapByFilterID(src, filterId, effectIndex, adjustParam);
                if(run != null) {
                    run.run();
                }

            }
        };
        FilterEngineFactory.getInstance().queue(runable);
    }

    public static void renderBitmapByFilterIDAsync(Bitmap src, String filterEnum, int effectIndex, float adjustParam, Runnable run) {
        int filterID = filterIdFromString(filterEnum, effectIndex);
        renderBitmapByFilterIDAsync(src, filterID, effectIndex, adjustParam, run);
    }

    public static void renderBitmapByFilterIDSync(Bitmap src, String filterEnum, int effectIndex, float adjustParam) {
        int filterID = filterIdFromString(filterEnum, effectIndex);
        renderBitmapByFilterIDSync(src, filterID, effectIndex, adjustParam);
    }

    public static BaseFilter createFilter(String filterEnum, int effectIndex) {
        int filterID = filterIdFromString(filterEnum, effectIndex);
        BaseFilter filter = createFilter(filterID);
        HashMap parammap = new HashMap();
        parammap.put("effectIndex", Integer.valueOf(effectIndex));
        filter.setParameterDic(parammap);
        parammap.clear();
        return filter;
    }

    public static int filterIdFromString(String name, int effectIndex) {
        short filterId = 0;
        if(name == null) {
            return filterId;
        } else {
            if(name.equals("MIC_Montage")) {
                filterId = 122;
            } else if(name.equals("MIC_GLOW")) {
                filterId = 80;
            } else if(name.equals("MIC_AVG_ROUND")) {
                filterId = 119;
            } else if(name.equals("MIC_OILPAINTING")) {
                filterId = 99;
            } else if(name.equals("MIC_MANGASAVE")) {
                filterId = 121;
            } else if(name.equals("MIC_Poster")) {
                filterId = 120;
            } else if(!name.equals("MR_COLORPENCIL") && !name.equals("MIC_COLORPENCIL")) {
                if(name.equals("MIC_MARK")) {
                    filterId = 93;
                } else if(name.equals("MIC_NEWSKETCH")) {
                    filterId = 88;
                } else if(name.equals("MIC_COLOR_INK")) {
                    filterId = 104;
                } else if(name.equals("MIC_WaterInk")) {
                    filterId = 103;
                } else if(name.equals("MIC_CartoonRomantic")) {
                    filterId = 96;
                } else if(name.equals("MIC_GLOW_CPU_FILTER")) {
                    filterId = 126;
                } else if(name.equals("MIC_GLOW_FORG_FILTER")) {
                    filterId = 126;
                } else if(name.equals("MIC_Portait")) {
                    filterId = 2;
                } else if(name.equals("MIC_GLOW")) {
                    filterId = 80;
                } else if(name.equals("MIC_JINGWU1")) {
                    filterId = 9;
                } else if(name.equals("MIC_Flares")) {
                    filterId = 13;
                } else if(name.equals("MIC_Portait_NB")) {
                    filterId = 5;
                } else if(name.equals("MIC_LOMOFILM")) {
                    filterId = 15;
                } else if(name.equals("MIC_LENS")) {
                    filterId = 0;
                } else if(name.equals("MIC_DARKLOMO")) {
                    filterId = 22;
                } else if(name.equals("MIC_MINGLIANG")) {
                    filterId = 11;
                } else if(name.equals("MIC_SHARE_FILM")) {
                    filterId = 214;
                } else if(name.equals("MIC_ABAO")) {
                    filterId = 83;
                } else if(name.equals("MIC_WARM")) {
                    filterId = 85;
                } else if(!name.equals("MIC_COLOR_SH") && !name.equals("MIC_PTU_COLOR_SH")) {
                    if(!name.equals("MIC_SHISHANG_SH") && !name.equals("MIC_PTU_SHISHANG_SH")) {
                        if(name.equals("MIC_FUGU_SH") || name.equals("MIC_PTU_FUGU_SH")) {
                            filterId = 201;
                        }
                    } else {
                        filterId = 200;
                    }
                } else {
                    filterId = 202;
                }
            } else {
                filterId = 87;
            }

            return name.equals("MIC_PTU_SNOW")?222:(name.equals("MIC_PTU_GAOLENG")?223:(name.equals("MIC_PTU_FEN")?204:(name.equals("MIC_PTU_FUGUHUANG")?205:(name.equals("MIC_PTU_GOGUANGLANZI")?206:(name.equals("MIC_PTU_HEIBAI")?207:(name.equals("MIC_PTU_HUAIJIU")?208:(name.equals("MIC_PTU_JIAOPIAN")?209:(name.equals("MIC_PTU_LAN")?210:(name.equals("MIC_PTU_LANTUISE")?211:(name.equals("MIC_PTU_MOLV")?212:(name.equals("MIC_PTU_NUANHUANG")?213:(name.equals("MIC_PTU_FEN2")?215:(name.equals("MIC_PTU_HEIBAI2")?216:(name.equals("MIC_PTU_DRAMA")?217:(name.equals("MIC_PTU_NIGHT")?218:(name.equals("MIC_PTU_FUGU")?219:(name.equals("MIC_PTU_HEIBAI3")?220:(name.equals("MIC_1977")?32:(name.equals("MIC_AMARO")?6:(name.equals("MIC_WALDEN")?35:(name.equals("MIC_BRANNAN")?36:(name.equals("MIC_EARLYBIRD")?27:(!name.equals("MIC_HEFE") && !name.equals("MIC_HEFEI")?(name.equals("MIC_HUDSON")?7:(name.equals("MIC_INKWELL")?38:(name.equals("MIC_KELVIN")?33:(name.equals("MIC_LOFI")?14:(name.equals("MIC_NASHVILLE")?28:(name.equals("MIC_RISE")?4:(name.equals("MIC_SIERRA")?39:(name.equals("MIC_SUTRO")?40:(name.equals("MIC_TOASTER")?30:(name.equals("MIC_VALENCIA")?29:(!name.equals("MIC_XPRO2") && !name.equals("MIC_XPRO")?(name.equals("WEICO_FILM")?72:(name.equals("WEICO_DIANA")?145:(name.equals("WEICO_BW")?71:(name.equals("WEICO_VIOLET")?70:(name.equals("WEICO_1949")?146:(name.equals("WEICO_LOFT")?74:(name.equals("WEICO_HOLGA")?147:(name.equals("WEICO_FOREST")?148:(name.equals("WEICO_OLDSCHOOL")?149:(name.equals("WEICO_INSTANT")?150:(!name.equals("MIC_MINGLIANG") && !name.equals("WEICO_SUN")?(name.equals("WEICO_LONDON")?151:(name.equals("WEICO_INDIGO")?73:(name.equals("WEICO_SILVER")?75:(name.equals("WEICO_MOMENT")?76:(name.equals("QQ_TONNYBW")?34:(name.equals("QQ_TONNYNOSTALGIC")?26:(name.equals("QQ_TONNYCHURCH")?24:(!name.equals("MIC_DARKLOMO") && !name.equals("MIC_LOMO")?(name.equals("MIC_PRO")?23:(name.equals("MIC_LAKE")?20:(name.equals("MIC_DIANA")?17:(name.equals("MIC_LOMOFILM")?15:(name.equals("MIC_ANSEL")?18:(name.equals("MIC_COUNTRY")?19:(name.equals("MIC_INSTANT")?21:filterId))))))):22)))))))):77))))))))))):16))))))))))):8)))))))))))))))))))))));
        }
    }
}
