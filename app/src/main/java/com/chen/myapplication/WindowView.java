package com.chen.myapplication;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * 扩展其他需求，完成进度条顶部窗口，可用于视频当前帧，以及数字等
 * 样式可按钮需求修改
 * 注意，内部添加RoundSeekBar,所以在一些参数，通过代码设置
 */

public class WindowView extends FrameLayout implements RoundSeekBar.RoundSeekBarListener {
    public enum SeekWindowShow{
        Permanent,
        Touch_Show
    }
    SeekWindowShow seekWindowShow=SeekWindowShow.Permanent;
    //参数
    private RoundSeekData roundSeekBarData;
    //设置window大小
    private int windowWidth=200;
    private int windowHeight=200;
    //添加window容器
    private LinearLayout topWindow;


    private RoundSeekBar roundSeekBar;

    public WindowView(@NonNull Context context) {
        this(context,null);
    }

    public WindowView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public WindowView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr,0);
    }

    public WindowView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        roundSeekBar = new RoundSeekBar(getContext());
        //添加基本组件
        addView(roundSeekBar);
        roundSeekBar.setRoundSeekBarListener(this);
        //添加浮窗（通过监听来调整位置）
        topWindow=new LinearLayout(getContext());
        topWindow.setVisibility(GONE);
        topWindow.setBackgroundColor(Color.BLUE);
        addView(topWindow);
    }


//    private void showTopWindow(boolean visibility, MotionEvent event) {
//        View view = findViewById(WindowId);
////        if (view==null) addView(mTopWindow);
//        if (seekWindowShow){
//            float x = event.getX();
//            if (x < paddingLeftX || x > mViewWidth - paddingRightX) return;
//            float currentX = x - paddingLeftX > 0 ? x - paddingLeftX : 0;
//
//            if (visibility && mTopWindow.getVisibility()!=View.VISIBLE){
//                mTopWindow.setVisibility(View.VISIBLE);
//            }else if (mTopWindow.getVisibility()!=View.GONE){
//                mTopWindow.setVisibility(View.GONE);
//            }
//
//            mTopWindow.setTranslationX((currentX+topWindowWidth) /2);
//        }
//    }

    /**
     * 设置RoundSeekBar属性
     */
    public void setRoundSeekBarData(@NonNull RoundSeekData roundSeekBarData){
        this.roundSeekBarData = roundSeekBarData;
        roundSeekBar.setFistData(roundSeekBarData);
    }

    /**
     * 调用RoundSeekBarApi
     */
    public RoundSeekBar getRoundSeekBar(){
        return roundSeekBar;
    }


    public int getWindowWidth() {
        return windowWidth;
    }

    public int getWindowHeight() {
        return windowHeight;
    }

    public void setWindowWidth(int windowWidth,int windowHeight) {
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
        if (roundSeekBar.getLayoutParams()!=null){
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) roundSeekBar.getLayoutParams();
            params.topMargin=windowHeight;
            roundSeekBar.setLayoutParams(params);
        }else {
            FrameLayout.LayoutParams params =new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.topMargin=windowHeight;
            roundSeekBar.setLayoutParams(params);
        }
        //设置window
        if (topWindow.getLayoutParams()!=null){
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) topWindow.getLayoutParams();
            params.width=windowWidth;
            params.height=windowHeight;
            topWindow.setLayoutParams(params);
        }
        initTopTranslation(getRoundSeekBar());
    }

    /**
     * 计算当前window位置
     */
    private void initTopTranslation(RoundSeekBar roundSeekBar) {
        switch (seekWindowShow){
            case Permanent:
                topWindow.setVisibility(VISIBLE);
                break;
        }
        numTranslation(roundSeekBar);
    }

    /**
     * 计算当前window位置
     */
    private void numTranslation(RoundSeekBar roundSeekBar) {
        float leftX = roundSeekBar.getCurrentLeftX();
        float topY = roundSeekBar.getCurrentTopY();
        float thumbHeight = roundSeekBar.getThumbHeight();

        float v = leftX - (topWindow.getWidth() / 2);
        //最小边界
        if (v<=0){
            v=0;
        }
        //最大边界
        if (leftX+(topWindow.getWidth() / 2)>=getMeasuredWidth()){
            v=getMeasuredWidth()-topWindow.getWidth();
        }
        Log.e("11111", "numTranslation: "+leftX+"====="+topY );
        topWindow.setTranslationX(v);
        topWindow.setTranslationY(topY-thumbHeight);
    }

    @Override
    public void onStartTrackingTouch(RoundSeekBar seekBar) {
        switch (seekWindowShow){
            case Touch_Show:
                numTranslation(seekBar);
                if (topWindow.getVisibility()!=VISIBLE)
                    topWindow.setVisibility(VISIBLE);
                break;
        }
    }

    @Override
    public void onStopTrackingTouch(RoundSeekBar seekBar) {
        switch (seekWindowShow){
            case Touch_Show:
                numTranslation(seekBar);
                if (topWindow.getVisibility()!=GONE)
                    topWindow.setVisibility(GONE);
                break;
        }
    }

    @Override
    public void onProgressChanged(RoundSeekBar seekBar, int progress) {
        numTranslation(seekBar);
    }
}
