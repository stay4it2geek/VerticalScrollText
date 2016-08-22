package com.smartphone.imjx.verticalscrolltext;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Scroller;
import android.widget.TextView;

/**
 * Created by imjx on 2016/7/21.
 * 垂直定时滚动走马灯的效果
 */
public class VerticalScrollTextView extends FrameLayout{

    

	/** 改变布局*/
    private boolean ischange = false;
    /** */
    private Scroller mScroller;
    /** 高度*/
    private int mHeight ;
    /** 宽度*/
    private int mWidth ;
    /** 文字改变前的监听   在这里给控件设置内容*/
    private OnPreTextChangeListener mlistener;
    public void setOnPreTextChangeListener(OnPreTextChangeListener mlistener){
        this.mlistener = mlistener;
    }

    
    public VerticalScrollTextView(Context context) {
		super(context);
		mScroller = new Scroller(context);
	}
    public VerticalScrollTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mScroller = new Scroller(context);
	}
    public VerticalScrollTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mScroller = new Scroller(context);
	}

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //获取控件宽高
        mHeight = getChildAt(0).getMeasuredHeight();
         mWidth = getChildAt(0).getMeasuredWidth();
    }

    @Override
    public void computeScroll() {
        //判断scroller是否滚动结束
        if (mScroller.computeScrollOffset()){
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        }else if (getScrollY() == mHeight){
            //改变设置布局参数
            ischange = !ischange;

            postDelayed(new Runnable() {
                @Override
                public void run() {
                    mScroller.startScroll(0, 0, 0, mHeight, 2000);
                    scrollTo(0, 0);
                    requestLayout();

                    if(getChildAt(0).getBottom() == mHeight){
                        mlistener.SetOnPreTextChangeListener((RelativeLayout) getChildAt(0));
                    }else if(getChildAt(1).getBottom() == mHeight){
                        mlistener.SetOnPreTextChangeListener((RelativeLayout) getChildAt(1));
                    }
                    postInvalidate();
                }
            },3000);
        }else if(getScrollY() == 0){
            //第一次进来
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    mScroller.startScroll(0, 0, 0, mHeight,2000);
                    postInvalidate();
                }
            },3000);
        }
    }

    public interface OnPreTextChangeListener{
        void SetOnPreTextChangeListener(RelativeLayout relativeLayout);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if(getChildCount() != 2){
            new IllegalArgumentException("至少需要2个子内容.");
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mScroller.abortAnimation();
        getHandler().removeCallbacksAndMessages(null);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //来回切换内部两个控件的位置   形成来回滑动效果
            if(ischange){
                getChildAt(1).layout(0,0,mWidth,mHeight);
                getChildAt(0).layout(0,mHeight,mWidth,mHeight*2);
            }else{
                getChildAt(0).layout(0,0,mWidth,mHeight);
                getChildAt(1).layout(0,mHeight,mWidth,mHeight*2);
            }
    }
}
