package com.qql.dagger.recommend.view;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Handler;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ImageView.ScaleType;

public class MainGroupView extends LinearLayout{

    private ImageView dragView;

    private View oriDragView;

    protected Handler handler = new Handler();

    public static float childRatio = .9f;
    protected int dragviewW, dragViewY, padding, dpi, scroll = 0;

    //dragging vars 拖拽变量
    protected int dragged = -1, lastX = -1, lastY = -1, lastTarget = -1;

    protected float lastDelta = 0;

    //anim vars 动画变量
    public static int animT = 150;

    private int heightPixels;

    private boolean touching;

    public MainGroupView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }



    @SuppressLint("NewApi")
    public MainGroupView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
        initLayout();
        setChildrenDrawingOrderEnabled(true);
        handler.removeCallbacks(updateTask);
        handler.postAtTime(updateTask, SystemClock.uptimeMillis() + 500);
    }




    private void initLayout() {
        // TODO Auto-generated method stub
        getLocationInWindow(mViewLocations);
    }



    public MainGroupView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        initLayout();
        setChildrenDrawingOrderEnabled(true);
        handler.removeCallbacks(updateTask);
        handler.postAtTime(updateTask, SystemClock.uptimeMillis() + 500);
        DisplayMetrics metrics = new DisplayMetrics();
        ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(metrics);
        heightPixels = metrics.heightPixels;
    }

    public void addDragView(View dragView){
        oriDragView = dragView;
        initDragView(dragView);
    }

    public void layoutDragView(int l,int t,int r,int b){
        setDragViewVisable(true);
        dragged = 1;
        dragviewW = r-l;
        dragViewY = b-t;
//		dragView.layout(l, t, r, b);
        int [] layoutPoint = getAbsolutonLayout(oriDragView);
        this.dragView.layout(layoutPoint[0], layoutPoint[1]-mViewLocations[1], layoutPoint[0]+dragviewW, layoutPoint[1]+dragViewY-mViewLocations[1]);
    }


    public void setDragViewVisable(boolean visable){
        if(visable==false){
            if(dragView.getVisibility() != View.GONE){
                dragView.setVisibility(View.GONE);
                dragView.clearAnimation();
                dragView = null;
                dragged = -1;
            }
        } else{
            if(dragView.getVisibility() == View.GONE){
                dragView.setVisibility(View.VISIBLE);
            }
        }
    }



    @SuppressLint("WrongCall")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        System.out.println("MainGroupView.onTouchEvent()");
        int action = event.getAction();
        switch (action & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                touching = true;
                lastX = (int) event.getX();
                lastY = (int) event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                int delta = lastY - (int)event.getY();
                scroll += delta;
                clampScroll();
                onLayout(true, getLeft(), getTop(), getRight(), getBottom());
                lastX = (int) event.getX();
                lastY = (int) event.getY();
                lastDelta = delta;
                break;
            case MotionEvent.ACTION_UP:
                touching = false;
                break;
            default:
                break;
        }

        return true;
    }



    private Bitmap getDragViewBitmap(View dragView) {
        return Bitmap.createBitmap(dragView.getWidth(), dragView.getHeight(), Bitmap.Config.RGB_565);
        // TODO Auto-generated method stub
    }



    private void initDragView(View dragView){
        initLayout();
        int dvTop = dragView.getTop();
        int dvLeft = dragView.getLeft();
        int dvRight = dragView.getRight();
        int dvBottom = dragView.getBottom();
        int parentTop = 0;
        int parentLeft = 0;
        int parentRight = 0;
        int parentBottom = 0;
        ViewParent parent = dragView.getParent();
        if(parent instanceof ViewGroup){
            parentTop = ((ViewGroup) parent).getTop();
            parentLeft = ((ViewGroup) parent).getLeft();
            parentRight = ((ViewGroup) parent).getRight();
            parentBottom = ((ViewGroup) parent).getBottom();
        }
        if(this.dragView == null){
            this.dragView = new ImageView(getContext());
            addView(this.dragView);
        }
        dragviewW = (dvRight+parentRight)-(dvLeft+parentLeft);
        dragViewY = (dvBottom+parentBottom)-(dvLeft+parentLeft);
        Bitmap bmp = getDragViewBitmap(dragView);
        dragView.draw(new Canvas(bmp));
        this.dragView.setScaleType(ScaleType.FIT_XY);
        this.dragView.setImageBitmap(bmp);
        this.dragView.measure(dragviewW, dragViewY);
        layoutDragView(dvLeft+parentLeft, dvTop+parentTop, dvRight+parentRight, dvBottom+parentBottom);
    }



    @SuppressLint("DrawAllocation")
    @Override
    protected void onLayout(boolean changed, int left, int top, int right,
                            int bottom) {
        // TODO Auto-generated method stub
        super.onLayout(changed, left, top, right, bottom);
        if(this.dragView != null && oriDragView!= null){
            int [] layoutPoint = getAbsolutonLayout(oriDragView);
            this.dragView.layout(layoutPoint[0], layoutPoint[1]-mViewLocations[1], layoutPoint[0]+dragviewW, layoutPoint[1]+dragViewY-mViewLocations[1]);
//			layoutDragView(oriDragView.getLeft(), oriDragView.getTop(), oriDragView.getRight(), oriDragView.getBottom());
        }
        if(scroll<0){
            int childCount = getChildCount();
            for(int i = 0 ; i < childCount; i++){
                View childAt = getChildAt(i);
                getPaddingTop();
                childAt.layout(childAt.getLeft(), childAt.getTop()-scroll, childAt.getRight(), childAt.getBottom()-scroll);
            }
        }
    }

    private int[] getAbsolutonLayout(View oriDragView) {
        int[] location = new int[2];
        // TODO Auto-generated method stub
        oriDragView.getLocationInWindow(location);
        return location;
    }



    protected void clampScroll()
    {
        int stretch = 3, overreach = getHeight() / 5;
        int max = getMaxScroll();
        max = Math.max(max, 0);

        if (scroll < -overreach)
        {
            scroll = -overreach;
            lastDelta = 0;
        }
        else if (scroll > max + overreach)
        {
            scroll = max + overreach;
            lastDelta = 0;
        }
        else if (scroll < 0)
        {
            if (scroll >= -stretch)
                scroll = 0;
            else if (!touching)
                scroll -= scroll / stretch;
        }
        else if (scroll > max)
        {
            if (scroll <= max + stretch)
                scroll = max;
            else if (!touching)
                scroll += (max - scroll) / stretch;
        }
    }

    protected int getMaxScroll()
    {

        return heightPixels/5;
    }

    protected Runnable updateTask = new Runnable() {
        @SuppressLint("WrongCall")
        public void run()
        {
            if (dragged != -1)
            {
                if (lastY < padding * 3 && scroll > 0)
                    scroll -= 20;
                else if (lastY > getBottom() - getTop() - (padding * 3) && scroll < getMaxScroll())
                    scroll += 20;
            }
            else if (lastDelta != 0 && !touching)
            {
                scroll += lastDelta;
                lastDelta *= .9;
                if (Math.abs(lastDelta) < .25)
                    lastDelta = 0;
            }
            clampScroll();
            int left = getLeft();
            int top = getTop();
            int right = getRight();
            int bottom = getBottom();
            onLayout(true, left, top, right, bottom);
            handler.postDelayed(this, 25);
        }
    };

    private int[] mViewLocations = new int[2];
}
