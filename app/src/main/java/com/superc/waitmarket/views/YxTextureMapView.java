package com.superc.waitmarket.views;

import android.content.Context;

import com.amap.api.maps.TextureMapView;

public class YxTextureMapView extends TextureMapView {
    private int DEFAULT_SIZE=450;

    public YxTextureMapView(Context context) {
        super(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        final int width = getMySize(widthMeasureSpec);
        final int height = getMySize(heightMeasureSpec);
        final int min = Math.min(width, height);//保证控件为方形
        setMeasuredDimension(min, min);
    }
    /**
     * 获取测量大小
     */
    private int getMySize(int measureSpec) {
        int result;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;//确切大小,所以将得到的尺寸给view
        } else if (specMode == MeasureSpec.AT_MOST) {
            //默认值为450px,此处要结合父控件给子控件的最多大小(要不然会填充父控件),所以采用最小值
            result = Math.min(DEFAULT_SIZE, specSize);
        } else {
            result = DEFAULT_SIZE;
        }
        return result;
    }
}
