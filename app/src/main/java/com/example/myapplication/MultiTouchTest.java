package com.example.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * 绘制出第二个手指第位置
 */
public class MultiTouchTest extends View {
    String TAG = "Gcs";

    // 用于判断第2个手指是否存在
    boolean haveSecondPoint = false;

    Paint mDeafultPaint;

    float mViewWidth;
    float mViewHeight;

    // 记录第2个手指第位置
    PointF point = new PointF(0, 0);

    public MultiTouchTest(Context context) {
        this(context, null);
    }

    public MultiTouchTest(Context context, AttributeSet attrs) {
        super(context, attrs);
        mDeafultPaint = new Paint();
        mDeafultPaint.setAntiAlias(true);
        mDeafultPaint.setTextAlign(Paint.Align.CENTER);
        mDeafultPaint.setTextSize(30);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mViewHeight = h;
        mViewWidth = w;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int index = event.getActionIndex();

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_POINTER_DOWN:
                // 判断是否是第2个手指按下
                if (event.getPointerId(index) == 1){
                    haveSecondPoint = true;
                    point.set(event.getY(), event.getX());
                }
                break;
            case MotionEvent.ACTION_POINTER_UP:
                // 判断抬起的手指是否是第2个
                if (event.getPointerId(index) == 1){
                    haveSecondPoint = false;
                    point.set(0, 0);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (haveSecondPoint) {
                    // 通过 pointerId 来获取 pointerIndex
                    int pointerIndex = event.findPointerIndex(1);
                    // 通过 pointerIndex 来取出对应的坐标
                    point.set(event.getX(pointerIndex), event.getY(pointerIndex));
                }
                break;
        }

        invalidate();   // 刷新

        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
        canvas.translate(mViewWidth/2, mViewHeight/2);
        canvas.drawText("追踪第2个按下手指的位置", 0, 0, mDeafultPaint);
        canvas.restore();

        // 如果屏幕上有第2个手指则绘制出来其位置
        if (haveSecondPoint) {
            canvas.drawCircle(point.x, point.y, 50, mDeafultPaint);
        }
    }
}