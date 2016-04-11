package com.view.chip.customtabpagerstrip;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

public abstract class BaseTabPagerStrip extends HorizontalScrollView implements ViewPager.OnPageChangeListener {

    public static interface IOnTabClickedListener {
        void onTabClicked(int tab, boolean tabChanged);
    }

    private LinearLayout mTabViewContainer;

    private ViewPager mViewPager;
    private ViewPager.OnPageChangeListener mPageListener;

    private int mCurrentPage;
    private int mPageScrollState;

    private IOnTabClickedListener mOnTabClickedListener;

    public BaseTabPagerStrip(Context context) {
        this(context, null);
    }

    public BaseTabPagerStrip(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseTabPagerStrip(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mTabViewContainer = createTabViewContainer(context);
        addView(mTabViewContainer);
    }

    public abstract View getTabView(int position);

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        final boolean lockedExpanded = (widthMode == MeasureSpec.EXACTLY);
        setFillViewport(lockedExpanded);

        final int oldWidth = getMeasuredWidth();
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        final int newWidth = getMeasuredWidth();

        if (lockedExpanded && oldWidth != newWidth) {
            // Recenter the tab display if we're at a new (scrollable) size.
            setCurrentTab(mCurrentPage);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        mCurrentPage = position;

        if (mPageListener != null) {
            mPageListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
        }
    }

    @Override
    public void onPageSelected(int position) {
        mCurrentPage = position;
        setCurrentTab(position);

        if (mPageListener != null) {
            mPageListener.onPageSelected(position);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        mPageScrollState = state;

        if (mPageListener != null) {
            mPageListener.onPageScrollStateChanged(state);
        }
    }

    public void setViewPager(ViewPager viewPager) {
        if (viewPager == null) {
            return;
        }

        PagerAdapter adapter = viewPager.getAdapter();
        if (adapter == null) {
            throw new IllegalStateException(
                    "ViewPager does not have adapter instance.");
        }

        mViewPager = viewPager;
        mViewPager.setOnPageChangeListener(this);

        notifyDataSetChanged();
    }

    public void setViewPager(ViewPager viewPager, int position) {
        mCurrentPage = position;
        setViewPager(viewPager);
    }

    public void setOnPageChangeListener(ViewPager.OnPageChangeListener listener) {
        mPageListener = listener;
    }

    public void setOnTabClickedListener(IOnTabClickedListener listener) {
        mOnTabClickedListener = listener;
    }

    public final int getCurrentPage() {
        return mCurrentPage;
    }

    protected View getTabViewClicked(int position) {
        return getTabView(position);
    }

    private void notifyDataSetChanged() {
        mTabViewContainer.removeAllViews();

        PagerAdapter adapter = mViewPager.getAdapter();
        int count = adapter.getCount();
        for (int i = 0; i < count; i++) {
            final int position = i;

            View tabView = getTabView(position);
            getTabViewClicked(position).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    int oldPosition = mCurrentPage;
                    setCurrentTab(position);

                    if (mOnTabClickedListener != null) {
                        mOnTabClickedListener.onTabClicked(
                                position, oldPosition != position);
                    }
                }
            });

            mTabViewContainer.addView(tabView, new LinearLayout.LayoutParams(
                    0, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f));
        }

        if (mCurrentPage >= count) {
            mCurrentPage = count - 1;
        }
        setCurrentTab(mCurrentPage);

        invalidate();
    }

    private void setCurrentTab(int position) {
        if (mViewPager == null) {
            return;
        }

        PagerAdapter adapter = mViewPager.getAdapter();
        if (position < 0 || position >= adapter.getCount()) {
            return;
        }
        mViewPager.setCurrentItem(position, true);

        int tabCount = mTabViewContainer.getChildCount();
        for (int i = 0; i < tabCount; i++) {
            View child = mTabViewContainer.getChildAt(i);
            child.setSelected(i == position);
        }
    }

    private LinearLayout createTabViewContainer(Context ctx) {
        LinearLayout layout = new LinearLayout(ctx);
        layout.setBackgroundColor(Color.TRANSPARENT);
        layout.setOrientation(LinearLayout.HORIZONTAL);
        layout.setGravity(Gravity.CENTER_VERTICAL);

        layout.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));

        return layout;
    }

}
