package com.tudou.library.drawable;

import android.graphics.Rect;
import android.graphics.drawable.StateListDrawable;

/**
 * Created by wwwtodoucom on 15-1-6.
 */
public class StateRoundRectDrawable extends StateListDrawable{

    private static final float DEFAUT_RADIUS = 6.f;
    private int mNormalColor;
    private int mPressedColor;
    private float mTopLeftRedius = DEFAUT_RADIUS;
    private float mTopRightRedius = DEFAUT_RADIUS;
    private float mBottomLeftRedius = DEFAUT_RADIUS;
    private float mBottomRightRedius = DEFAUT_RADIUS;
    private RoundRectDrawable mNormalDrawable;
    private RoundRectDrawable mPressedDrawable;

    public StateRoundRectDrawable(int normalCorlor, int pressColor) {
        mNormalColor = normalCorlor;
        mPressedColor = pressColor;
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        if (mNormalDrawable == null) {
            mNormalDrawable = new RoundRectDrawable();
            mNormalDrawable.setTopLeftRedius(mTopLeftRedius);;
            mNormalDrawable.setTopRightRedius(mTopRightRedius);;
            mNormalDrawable.setBottomLeftRedius(mBottomLeftRedius);;
            mNormalDrawable.setBottomRightRedius(mBottomRightRedius);;
            mNormalDrawable.setColor(mNormalColor);;
            mNormalDrawable.setBounds(bounds);
        }
        if (mPressedDrawable == null) {
            mPressedDrawable = new RoundRectDrawable();
            mPressedDrawable.setTopLeftRedius(mTopLeftRedius);;
            mPressedDrawable.setTopRightRedius(mTopRightRedius);;
            mPressedDrawable.setBottomLeftRedius(mBottomLeftRedius);;
            mPressedDrawable.setBottomRightRedius(mBottomRightRedius);;
            mPressedDrawable.setColor(mPressedColor);;
            mPressedDrawable.setBounds(bounds);
        }
        this.addState(new int[] {-android.R.attr.state_pressed}, mNormalDrawable);
        this.addState(new int[] {android.R.attr.state_pressed}, mPressedDrawable);
    }

    public int getNormalColor() {
        return mNormalColor;
    }

    public void setNormalColor(int mNormalColor) {
        this.mNormalColor = mNormalColor;
    }

    public int getPressedColor() {
        return mPressedColor;
    }

    public void setPressedColor(int mPressedColor) {
        this.mPressedColor = mPressedColor;
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

    public float getmBottomLeftRedius() {
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

    public void setDefautRadius(float mDefautRedius) {
        mTopLeftRedius = mDefautRedius;
        mTopRightRedius = mDefautRedius;
        mBottomLeftRedius = mDefautRedius;
        mBottomRightRedius = mDefautRedius;
    }
}
