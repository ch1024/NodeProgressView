package com.chen.myapplication;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class RoundSeekBar extends View {

    private int maxProgress=100;
    private int minProgress=0;
    private int backgroundColor=Color.parseColor("#7FFFFFFF");
    private boolean backgroundRadius=false;
    private float progressHeight=10;
    private int startColor=Color.parseColor("#aab8d4");
    private int endColor=Color.parseColor("#7FFFFFFF");
    private int currentProgress=0;
    private int thumbColor=Color.parseColor("#800000");
    private Drawable thumbImage;
    private boolean thumbShow=true;
    private float thumbWidth=20;
    private int nodeColor=Color.parseColor("#FF03DAC5");
    private Drawable nodeImage;
    private float nodeWidth=10;

    private Paint mPaint;
    private Paint nodePaint;
    private Paint thumbPaint;
    //整体高度
    private int mViewHeight;
    private int mViewWidth;
    private float mProgressWidth;
    private float mProgressLinerWidth;
    private int mProgressHeight;
    private float paddingLeftX;
    private float paddingRightX;
    private float mProgressCenterY;
    private float mProgressLinerY;
    private float mCurrentProgress;



    public RoundSeekBar(Context context) {
        super(context);
        init(context, null);
    }
    public RoundSeekBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public RoundSeekBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs,defStyleAttr);
        init(context, attrs);
    }

    public RoundSeekBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs!=null){
            TypedArray typedArray = getResources().obtainAttributes(attrs, R.styleable.RoundSeekBar);
            maxProgress = typedArray.getInt(R.styleable.RoundSeekBar_seek_maxProgress, 100);
            minProgress = typedArray.getInt(R.styleable.RoundSeekBar_seek_minProgress, 0);
            backgroundColor = typedArray.getColor(R.styleable.RoundSeekBar_seek_backgroundColor, Color.parseColor("#7FFFFFFF"));
            backgroundRadius = typedArray.getBoolean(R.styleable.RoundSeekBar_seek_backgroundRadius, false);
            progressHeight = typedArray.getDimension(R.styleable.RoundSeekBar_seek_progressHeight, 10);
            startColor = typedArray.getColor(R.styleable.RoundSeekBar_seek_startColor, Color.parseColor("#aab8d4"));
            endColor = typedArray.getColor(R.styleable.RoundSeekBar_seek_endColor, Color.parseColor("#7FFFFFFF"));
            currentProgress = typedArray.getInt(R.styleable.RoundSeekBar_seek_progress, 0);
            thumbColor = typedArray.getColor(R.styleable.RoundSeekBar_seek_thumbColor,Color.parseColor("#FF03DAC5"));
            thumbWidth = typedArray.getDimension(R.styleable.RoundSeekBar_seek_thumbWidth,10);
            thumbImage = typedArray.getDrawable(R.styleable.RoundSeekBar_seek_thumbImage);
            thumbShow = typedArray.getBoolean(R.styleable.RoundSeekBar_seek_thumbShow,true);
            nodeColor = typedArray.getColor(R.styleable.RoundSeekBar_seek_NodeColor,Color.parseColor("#FF03DAC5"));
            nodeImage = typedArray.getDrawable(R.styleable.RoundSeekBar_seek_NodeImage);
            nodeWidth = typedArray.getDimension(R.styleable.RoundSeekBar_seek_NodeWidth,10);
            typedArray.recycle();
        }
        mPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        nodePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        thumbPaint = new Paint(Paint.ANTI_ALIAS_FLAG);




    }

    private void initNodeImage() {
        if (nodeImage!=null){
            int width = nodeImage.getIntrinsicWidth();
            int height = nodeImage.getIntrinsicHeight();

            //超出进度条高度以及宽度（按照高度，缩放矩形）
            if (height>mViewHeight || width>mProgressWidth){
                Bitmap oldBit = drawableToBitmap(nodeImage);
                Matrix matrix = new Matrix();
                float scaleWidth =(float) nodeWidth / width;
                float scaleHeight =(float) nodeWidth / height;
                matrix.postScale(scaleWidth,scaleHeight);
                Bitmap newBit = Bitmap.createBitmap(oldBit, 0, 0, width, height, matrix, true);
                this.nodeImage=new BitmapDrawable(null,newBit);
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //当前进度
        initCurrent();
        //画背景
        drawBackground(canvas);
        //画当前进度
        drawCurrentProgress(canvas);
        //画节点
        drawNode(canvas);
        //画拖拽点
        drawThumb(canvas);
    }

    /**
     * 当前进度计算
     */
    private void initCurrent() {
        mCurrentProgress = (float) (currentProgress*1.0/maxProgress);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getMode(widthMeasureSpec);
        int height = MeasureSpec.getMode(heightMeasureSpec);
        int at_height=40; //设置Warp后，采用希望高度
        int at_width=300; //设置Warp后，采用希望宽度
        switch (width){
            case MeasureSpec.AT_MOST:
                break;
            case MeasureSpec.EXACTLY:
                at_width = MeasureSpec.getSize(widthMeasureSpec);
                break;
            case MeasureSpec.UNSPECIFIED:
                break;
        }
        switch (height){
            case MeasureSpec.AT_MOST:
                break;
            case MeasureSpec.EXACTLY:
                at_height = MeasureSpec.getSize(heightMeasureSpec);
                break;
            case MeasureSpec.UNSPECIFIED:
                break;
        }

        setMeasuredDimension(at_width,at_height);

        initNum();
        //初始化节点图片
        initNodeImage();
        //初始化拖拽图片
        initThumbImage();
    }

    /**
     * 计算
     */
    private void initNum() {
        //整体高度
        mViewHeight =getMeasuredHeight();
        mViewWidth =getMeasuredWidth();

        //起始位置
        paddingLeftX =backgroundRadius?progressHeight/2+getPaddingLeft():+getPaddingLeft();
        paddingRightX =backgroundRadius?progressHeight/2+getPaddingRight():+getPaddingRight();
        //进度线宽度(由于简单，进度圆之接线条替换了，cap)
        mProgressLinerWidth =mViewWidth-(paddingLeftX + paddingRightX);
        //真实宽度（控制宽度）
        mProgressWidth =mViewWidth-(getPaddingLeft() + getPaddingRight());

        //画线进度中间线
        mProgressCenterY =(mViewHeight+getPaddingTop()+getPaddingBottom()+progressHeight)/2;
        //进度条y轴（画线,节点）
        mProgressLinerY =(mViewHeight+getPaddingTop()+getPaddingBottom()+progressHeight)/2-(progressHeight/2);


    }

    private void drawThumb(Canvas canvas) {
        if (!thumbShow) return;
        if (thumbWidth>mViewHeight) thumbWidth=mViewHeight;
        if (thumbImage!=null){
            //拖拽图片
            Bitmap bitmap = drawableToBitmap(thumbImage);

            float left = mCurrentProgress * mProgressLinerWidth + paddingLeftX-bitmap.getWidth()/2;
            float top;
            if (bitmap.getHeight()==mViewHeight){
                top=0;
            }else {
                top=(mViewHeight+bitmap.getHeight())/2-bitmap.getHeight();
            }
            canvas.drawBitmap(bitmap,left,top,thumbPaint);
        }else {
            //拖拽圆点
            thumbPaint.setColor(thumbColor);
            float left;
            if (backgroundRadius){
                left =(mCurrentProgress * mProgressLinerWidth)+paddingLeftX+paddingRightX-thumbWidth/2;
            }else {
                left=(mCurrentProgress * mProgressLinerWidth)+paddingLeftX+paddingRightX;
            }
            canvas.drawCircle(left,mProgressLinerY,thumbWidth/2,thumbPaint);
        }
        canvas.save();

    }

    private void initThumbImage() {
        if (thumbImage!=null){
            int width = thumbImage.getIntrinsicWidth();
            int height = thumbImage.getIntrinsicHeight();

            //超出进度条高度以及宽度（按照高度，缩放矩形）
            if (height>mViewHeight || width>mProgressWidth){
                Bitmap oldBit = drawableToBitmap(thumbImage);
                Matrix matrix = new Matrix();
                float scaleWidth =(float) thumbWidth / width;
                float scaleHeight =(float) thumbWidth / height;
                matrix.postScale(scaleWidth,scaleHeight);
                Bitmap newBit = Bitmap.createBitmap(oldBit, 0, 0, width, height, matrix, true);
                this.thumbImage=new BitmapDrawable(null,newBit);
            }
        }
    }

    private void drawNode(Canvas canvas) {
        if (mProgressNode.size()>0){
            mProgressNodeRect.clear();
            if (nodeImage!=null){
                //节点图片
                Bitmap bitmap = drawableToBitmap(nodeImage);
                for (int i = 0; i <mProgressNode.size(); i++) {
                    Integer integer = mProgressNode.get(i);
                    //当前节点位置(中间位置)
                    float v = (float) (integer * 1.0 / maxProgress);
                    float left = (v * mProgressLinerWidth)+paddingLeftX- bitmap.getWidth()/2;
                    //居中位置
                    float top;
                    if (mViewHeight==bitmap.getHeight()){
                        top=0;
                    }else {
                        top=(mViewHeight+bitmap.getHeight())/2-bitmap.getHeight();
                    }
                    canvas.drawBitmap(bitmap,left,top,nodePaint);
                    canvas.save();
                    mProgressNodeRect.add(new RectF(left,top,left+bitmap.getWidth(),top+bitmap.getHeight()));
                }
            }else {
                //节点原点
                nodePaint.setColor(nodeColor);
                for (int i = 0; i <mProgressNode.size(); i++) {
                    Integer integer = mProgressNode.get(i);
                    //当前节点位置
                    float v = (float) (integer * 1.0 / maxProgress);
                    float left=(v * mProgressLinerWidth)+paddingLeftX+paddingRightX-nodeWidth/2;
                    canvas.drawCircle(left,mProgressLinerY,nodeWidth/2,nodePaint);
                    canvas.save();
                    mProgressNodeRect.add(new RectF(left,mProgressLinerY,left+nodeWidth,mProgressLinerY+nodeWidth));
                }
            }
        }
    }
    //保存进度节点
    private List<Integer> mProgressNode=new ArrayList<>();
    //保存进度节点位置
    private List<RectF> mProgressNodeRect=new ArrayList<>();


    private Bitmap drawableToBitmap(Drawable nodeImage) {
        int width = nodeImage.getIntrinsicWidth();
        int height = nodeImage.getIntrinsicHeight();
        Bitmap.Config config = nodeImage.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565;
        Bitmap bitmap = Bitmap.createBitmap(width, height, config);
        Canvas canvas = new Canvas(bitmap);
        nodeImage.setBounds(0,0,width,height);
        nodeImage.draw(canvas);
        return bitmap;
    }

    private void drawCurrentProgress(Canvas canvas) {
        if (mCurrentProgress==0) return;
        mPaint.setColor(startColor);

        //mProgressLinerWidth-node.width=当前进度条总宽
        //也就是node.width+当前总宽度=mProgressLinerWidth

        canvas.drawLine(paddingLeftX,mProgressLinerY,mCurrentProgress*mProgressLinerWidth+paddingLeftX,mProgressLinerY,mPaint);
        canvas.save();
    }

    private void drawBackground(Canvas canvas) {
        mPaint.setStrokeWidth(progressHeight);
        if (backgroundRadius){
            mPaint.setStrokeCap(Paint.Cap.ROUND);
        }else {
            mPaint.setStrokeCap(Paint.Cap.SQUARE);
        }
        mPaint.setColor(backgroundColor);

        canvas.drawLine(paddingLeftX,mProgressLinerY,paddingLeftX+mProgressLinerWidth,mProgressLinerY,mPaint);
        canvas.save();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                boolean change = setProgressUp(event);
                if (roundSeekBarListener!=null){
                    roundSeekBarListener.onStartTrackingTouch(this);
                    if (change) roundSeekBarListener.onProgressChanged(this,currentProgress);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                boolean changeMove = setProgressUp(event);
                if (roundSeekBarListener!=null){
                    if (changeMove) roundSeekBarListener.onProgressChanged(this,currentProgress);
                }
                break;
            case MotionEvent.ACTION_UP:
                boolean changeUp = setProgressUp(event);
                if (roundSeekBarListener!=null){
                    roundSeekBarListener.onStopTrackingTouch(this);
                    if (changeUp) roundSeekBarListener.onProgressChanged(this,currentProgress);
                }
                break;
        }
        return true;


    }

    private boolean setProgressUp(MotionEvent event) {
        if (!thumbShow) return false;
        float x = event.getX();
        float y = event.getY();
        if (x<paddingLeftX || x>mViewWidth-paddingRightX) return false;

        //点击当前位置
        //转换率
        float currentX = x - paddingLeftX>0 ? x - paddingLeftX:0;

        float l=currentX/mProgressLinerWidth;
        //当前progress
        int upProgress = (int) Math.floor(l * maxProgress);
        //进度是否改变
        boolean isChange=false;
        if (currentProgress!=upProgress) isChange=true;
        currentProgress=upProgress;
        postInvalidate();
        return isChange;
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        return super.dispatchTouchEvent(event);
    }

    private RoundSeekBarListener roundSeekBarListener;

    public void setRoundSeekBarListener(RoundSeekBarListener roundSeekBarListener) {
        this.roundSeekBarListener = roundSeekBarListener;
    }

    public interface RoundSeekBarListener{
        void onStartTrackingTouch(RoundSeekBar seekBar);
        void onStopTrackingTouch(RoundSeekBar seekBar);
        void onProgressChanged(RoundSeekBar seekBar,int progress);
    }

    //------------------------对外开放方法-----------------------------

    /**
     * 设置进度节点
     * @param node 进度节点
     */
    public void setNode(@NonNull List<Integer> node){
        this.mProgressNode.clear();
        this.mProgressNode.addAll(node);
        invalidate();
    }

    /**
     * 设置进度
     */
    public void setProgress(int progress){
        if (progress>maxProgress) return;
        currentProgress=progress;
        postInvalidate();
    }
    /**
     * 获取进度
     */
    public int getProgress(){
        return currentProgress;
    }
}
