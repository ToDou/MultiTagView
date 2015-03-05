package com.tudou.library.drawable;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.shapes.RoundRectShape;

/**
 * Created by wwwtodoucom on 15-1-6.
 */
public class RoundRectDrawable extends Drawable {

    private static final float DEFAUT_RADIUS = 6.f;
    private Paint mPaint = new Paint();
    private RoundRectShape mShape;
    private float[] mOuter;
    private int mColor;
    private int mPressColor;
    private float mTopLeftRedius = DEFAUT_RADIUS;
    private float mTopRightRedius = DEFAUT_RADIUS;
    private float mBottomLeftRedius = DEFAUT_RADIUS;
    private float mBottomRightRedius = DEFAUT_RADIUS;

    public RoundRectDrawable() {
        mColor = Color.WHITE;
        mPressColor = Color.WHITE;
        mPaint.setColor(mColor);
        mPaint.setAntiAlias(true); //设置锯齿化
    }

    public int getColor() {
        return mColor;
    }

    public void setColor(int mColor) {
        this.mColor = mColor;
        mPaint.setColor(mColor);
    }

    public int getPressColor() {
        return mPressColor;
    }

    public void setPressColor(int mPressColor) {
        this.mPressColor = mPressColor;
    }

    public float getTopLeftRedius() {
        return mTopLeftRedius;
    }

    public void setTopLeftRedius(float mTopLeftRedius) {
        this.mTopLeftRedius = mTopLeftRedius;
    }

    public float getTopRightRedius() {
        return mTopRightRedius;
    }

    public void setTopRightRedius(float mTopRightRedius) {
        this.mTopRightRedius = mTopRightRedius;
    }

    public float getBottomLeftRedius() {
        return mBottomLeftRedius;
    }

    public void setBottomLeftRedius(float mBottomLeftRedius) {
        this.mBottomLeftRedius = mBottomLeftRedius;
    }

    public float getBottomRightRedius() {
        return mBottomRightRedius;
    }

    public void setBottomRightRedius(float mBottomRightRedius) {
        this.mBottomRightRedius = mBottomRightRedius;
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        refreshShape();
        mShape.resize(bounds.right - bounds.left, bounds.bottom - bounds.top);
    }

    private void refreshShape() {
        mOuter = new float[] {
                mTopLeftRedius, mTopLeftRedius
                ,mTopRightRedius, mTopRightRedius
                ,mBottomLeftRedius, mBottomLeftRedius
                ,mBottomRightRedius, mBottomRightRedius
        };
        mShape = new RoundRectShape(mOuter, null, null);
    }

    @Override
    public void draw(Canvas canvas) {
        mShape.draw(canvas, mPaint);
    }

    @Override
    public void setAlpha(int alpha) {
        mPaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        mPaint.setColorFilter(cf);
    }

    @Override
    public int getOpacity() {
        return mPaint.getAlpha();
    }

    public void setDefautRadius(float mDefautRedius) {
        mTopLeftRedius = mDefautRedius;
        mTopRightRedius = mDefautRedius;
        mBottomLeftRedius = mDefautRedius;
        mBottomRightRedius = mDefautRedius;
    }
}
