package com.chen.myapplication;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
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

    private float topWindowTranslationY;
    private float topWindowTranslationX;

    public enum SeekWindowShow{
        Permanent,
        Touch_Show
    }
    SeekWindowShow seekWindowShow=SeekWindowShow.Permanent;
    //参数
    private RoundSeekData roundSeekBarData;
    //设置window大小
    private int windowWidth;
    private int windowHeight;
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

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        RoundSeekBar childAt = null;
        //确认子组件摆放位置
        try {
            childAt = (RoundSeekBar) getChildAt(0);
        }catch (Exception e){
            Log.e("1111111", "onLayout 0: "+e.toString() );
        }
        try {
            childAt = (RoundSeekBar) getChildAt(1);
        }catch (Exception e){
            Log.e("1111111", "onLayout 1: "+e.toString() );
        }
//        if (childAt!=null){
//            childAt
//        }
//        getChildAt(0).getClass();


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
        initWindowData();
    }

    /**
     * 计算初始距离
     */
    private void initWindowData() {

        float viewHeight;   //整体高度
        float viewWidth;   //整体宽度

        if (roundSeekBarData.getLayout_height()>0){
            viewHeight=roundSeekBarData.getLayout_height();
        }else if (roundSeekBarData.getLayout_height()== ViewGroup.LayoutParams.MATCH_PARENT){
            DisplayMetrics metrics = getContext().getResources().getDisplayMetrics();
            viewHeight=metrics.heightPixels;
        }else {
            //默认数值
            viewHeight=40;
        }
        if (roundSeekBarData.getLayout_width()>0){
            viewWidth=roundSeekBarData.getLayout_width();
        }else if (roundSeekBarData.getLayout_width()== ViewGroup.LayoutParams.MATCH_PARENT){
            DisplayMetrics metrics = getContext().getResources().getDisplayMetrics();
            viewWidth=metrics.widthPixels;
        }else {
            //默认数值
            viewWidth=40;
        }
        float progressHeight;
        if (roundSeekBarData.getProgressHeight()>0){
            progressHeight=roundSeekBarData.getProgressHeight();
        }else if (roundSeekBarData.getProgressHeight()== ViewGroup.LayoutParams.MATCH_PARENT){
            DisplayMetrics metrics = getContext().getResources().getDisplayMetrics();
            progressHeight=metrics.heightPixels;
        }else {
            //默认数值
            progressHeight=10;
        }
        float thumbWidth;
        if (roundSeekBarData.getThumbWidth()>0){
            thumbWidth=roundSeekBarData.getThumbWidth();
        }else {
            //默认数值
            thumbWidth=20;
        }
        //计算中间值
        float v = (viewHeight + progressHeight + roundSeekBar.getPaddingTop() + roundSeekBar.getPaddingBottom()) / 2 - progressHeight / 2;
        topWindowTranslationY = v - thumbWidth;


        //计算当前值
        float mProgressLinerWidth = viewWidth - (roundSeekBar.getPaddingLeft() + roundSeekBar.getPaddingRight());
        float mCurrentProgress = (float) (roundSeekBarData.getCurrentProgress() * 1.0 / roundSeekBarData.getMaxProgress());

        topWindowTranslationX = mCurrentProgress * mProgressLinerWidth + roundSeekBar.getPaddingLeft();
        Log.e("1111", "======"+ topWindowTranslationX);
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
        //重新设置
        if (topWindowTranslationY>0){
            topWindow.setTranslationY(topWindowTranslationY);
        }
        if (topWindowTranslationX>0){
            float v = (topWindowTranslationX - (windowWidth / 2.0f));
            topWindow.setTranslationX(v);
        }
    }

    /**
     * 计算当前window位置
     */
    private void numTranslation(RoundSeekBar roundSeekBar) {
        float leftX = roundSeekBar.getCurrentLeftX();
        float topY = roundSeekBar.getCurrentTopY();
        float thumbHeight = roundSeekBar.getThumbHeight();
        float translationY=topY-thumbHeight;
        float translationX=leftX - (windowWidth / 2.0f);;

        //最小边界
        if (translationX<=0.0f){
            translationX=0f;
        }
        //最大边界
        if (translationX+windowWidth >=getMeasuredWidth()){
            translationX=getMeasuredWidth()-topWindow.getWidth();
        }

        topWindow.setTranslationX(translationX);
        topWindow.setTranslationY(translationY);
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
        Log.e("11", "onProgressChanged: " );
    }
}
