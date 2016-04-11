package com.view.chip.customtabpagerstrip;

import android.content.Context;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public final class MyCardTabPagerStrip extends BaseTabPagerStrip {
    private static final byte TAB_ONE = 0;
    private static final byte TAB_TWO = 1;
    public int[] mPicIvWidth = new int[2];//存放字体的宽度

    public int[] getmPicIv() {
        return mPicIvWidth;
    }


    public final class TabView extends RelativeLayout {
        private View mPicFrame;

        public TextView mPicIv;



        private String mResID;
        private View picFrameLine;
        private Boolean isLast;
        private boolean mSelected;

        public TabView(Context context, String resID, Boolean last) {
            super(context);
            mResID = resID;
            isLast = last;
            init(context, isLast, resID);
        }

        @Override
        public void setSelected(boolean selected) {
            if (mSelected == selected) {
                return;
            }
            mSelected = selected;
            mPicIv.setTextColor(mSelected ? getResources().getColor(R.color.white) : getResources().getColor(R.color.card_light_grey));
        }

        private void init(Context ctx, Boolean isLast, String resID) {
            LayoutInflater.from(ctx).inflate(R.layout.my_tab_view_layout, this);
            mPicFrame = findViewById(R.id.picFrame);
            mPicIv = (TextView) findViewById(R.id.textIv);
            picFrameLine = findViewById(R.id.picFrameLine);
            mPicIv.setText(mResID);

            mPicIv.setTextColor(getResources().getColor(R.color.card_light_grey));
            mPicIv.setTextSize(15);
            mPicIv.setGravity(Gravity.CENTER);
            TextPaint paint = new TextPaint();
            float scaledDensity = ctx.getResources().getDisplayMetrics().scaledDensity;
            paint.setTextSize(scaledDensity * 15);

            if (isLast) {
                mPicIvWidth[1] = (int) paint.measureText(resID);
                picFrameLine.setVisibility(View.GONE);
            }else{
                mPicIvWidth[0] = (int) paint.measureText(resID);
            }
        }



    }

    private View firstView;
    private View secondView;

    public MyCardTabPagerStrip(Context context) {
        this(context, null);
    }

    public MyCardTabPagerStrip(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyCardTabPagerStrip(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public View getTabView(int position) {
        switch (position) {
            case TAB_ONE:
                if (firstView == null) {
                    firstView = new TabView(getContext(),
                            getResources().getString(R.string.my_card), false);
                }
                return firstView;
            case TAB_TWO:
                if (secondView == null) {
                    secondView = new TabView(getContext(),
                            getResources().getString(R.string.draw_card), true);
                }
                return secondView;

        }
        return null;
    }

    @Override
    protected View getTabViewClicked(int position) {
        View view = getTabView(position);
        if (view != null && view instanceof TabView) {
            return ((TabView) view).mPicFrame;
        }
        return super.getTabViewClicked(position);
    }
}
