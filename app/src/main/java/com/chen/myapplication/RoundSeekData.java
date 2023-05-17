package com.chen.myapplication;

import android.graphics.Color;
import android.graphics.drawable.Drawable;

/**
 * 扩展外部，内部RoundSeekBar设置
 */
public class RoundSeekData {
    //进度条宽度
    private int layout_width=300;
    //高度
    private int layout_height=40;
    //最大进度
    private int maxProgress=100;
    private int minProgress;
    //进度条背景色
    private int backgroundColor=Color.parseColor("#7FFFFFFF");;

    //是否是圆角
    private boolean backgroundRadius = false;
    //进度条高度
    private float progressHeight=10;
    //当前进度前面颜色
    private int startColor=Color.parseColor("#aab8d4");
    //设置进度
    private int currentProgress=0;
    //设置拖拽点颜色
    private int thumbColor=Color.parseColor("#800000");
    //设置拖拽点图片
    private Drawable thumbImage;
    //设置拖拽是否显示
    private boolean thumbShow = true;
    //拖拽点宽度
    private float thumbWidth=20;
    //节点颜色
    private int nodeColor=Color.parseColor("#FF03DAC5");
    //节点图片
    private Drawable nodeImage;
    //节点宽度
    private float nodeWidth=10;
    //外边距，margin
    private int marginTop;

    public int getMarginTop() {
        return marginTop;
    }

    public void setMarginTop(int marginTop) {
        this.marginTop = marginTop;
    }

    public int getLayout_width() {
        return layout_width;
    }

    public void setLayout_width(int layout_width) {
        this.layout_width = layout_width;
    }

    public int getLayout_height() {
        return layout_height;
    }

    public void setLayout_height(int layout_height) {
        this.layout_height = layout_height;
    }

    public int getMaxProgress() {
        return maxProgress;
    }

    public void setMaxProgress(int maxProgress) {
        this.maxProgress = maxProgress;
    }

    public int getMinProgress() {
        return minProgress;
    }

    public void setMinProgress(int minProgress) {
        this.minProgress = minProgress;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public boolean isBackgroundRadius() {
        return backgroundRadius;
    }

    public void setBackgroundRadius(boolean backgroundRadius) {
        this.backgroundRadius = backgroundRadius;
    }

    public float getProgressHeight() {
        return progressHeight;
    }

    public void setProgressHeight(float progressHeight) {
        this.progressHeight = progressHeight;
    }

    public int getStartColor() {
        return startColor;
    }

    public void setStartColor(int startColor) {
        this.startColor = startColor;
    }

    public int getCurrentProgress() {
        return currentProgress;
    }

    public void setCurrentProgress(int currentProgress) {
        this.currentProgress = currentProgress;
    }

    public int getThumbColor() {
        return thumbColor;
    }

    public void setThumbColor(int thumbColor) {
        this.thumbColor = thumbColor;
    }

    public Drawable getThumbImage() {
        return thumbImage;
    }

    public void setThumbImage(Drawable thumbImage) {
        this.thumbImage = thumbImage;
    }

    public boolean isThumbShow() {
        return thumbShow;
    }

    public void setThumbShow(boolean thumbShow) {
        this.thumbShow = thumbShow;
    }

    public float getThumbWidth() {
        return thumbWidth;
    }

    public void setThumbWidth(float thumbWidth) {
        this.thumbWidth = thumbWidth;
    }

    public int getNodeColor() {
        return nodeColor;
    }

    public void setNodeColor(int nodeColor) {
        this.nodeColor = nodeColor;
    }

    public Drawable getNodeImage() {
        return nodeImage;
    }

    public void setNodeImage(Drawable nodeImage) {
        this.nodeImage = nodeImage;
    }

    public float getNodeWidth() {
        return nodeWidth;
    }

    public void setNodeWidth(float nodeWidth) {
        this.nodeWidth = nodeWidth;
    }
}
