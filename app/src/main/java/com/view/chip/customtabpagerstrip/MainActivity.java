package com.view.chip.customtabpagerstrip;

import android.app.Activity;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class MainActivity extends FragmentActivity {
    private ImageView mImageView;// 横向下划线
    private int pWidth;// 图片宽度
    private int offset;// 图片偏移量
    private int currentIndex; // 当前标签位置
    private ViewPager mViewPager = null;
    private MyCardPagerAdapter mCardPagerAdapter = null;
    private MyCardTabPagerStrip mTabPagerStrip;
    private int mInitNavigateTo = -1;
    private boolean isFirst = true;
    int s1;//第一个字符的长度
    int s2;//第二个字符的长度

    int screenWidth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    @Override
    public void onResume() {
        super.onResume();
        Activity act = MainActivity.this;
        if (act == null) {
            return;
        }
        if (isFirst) {
            switchToTab(mInitNavigateTo);
            isFirst = false;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void initView() {


        mTabPagerStrip = (MyCardTabPagerStrip) findViewById(R.id.tab_indicator);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mCardPagerAdapter = new MyCardPagerAdapter(MainActivity.this);
        mViewPager.setAdapter(mCardPagerAdapter);
        mTabPagerStrip.setViewPager(mViewPager);
        mTabPagerStrip.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(final int position) {
                doTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        mTabPagerStrip.setOnTabClickedListener(new BaseTabPagerStrip.IOnTabClickedListener() {
            @Override
            public void onTabClicked(int tab, boolean tabChanged) {
                if (tabChanged) {
                    doTab(tab);
                }
            }
        });

        // step5>>初始化横向划线的位置，需要计算偏移量并移动ImageView，有两个方法
        mImageView = (ImageView) findViewById(R.id.cursor);
        // 通过BitmapFactory获得横向划线图片宽度
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.select);
        s1 = mTabPagerStrip.getmPicIv()[0];
        s2 = mTabPagerStrip.getmPicIv()[1];
        pWidth = s1;
        // 获取手机屏幕分辨率(宽度)
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        display.getMetrics(dm);
        screenWidth = dm.widthPixels - DeviceUtils.dip2px(this, 160);

        // 初始化偏移量，计算原理如下
        // 每一个标签页所占：offset+pWidth+offset...[以此类推]
        offset = (screenWidth / 2 - pWidth) / 2;
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mImageView.getLayoutParams();
        params.width = pWidth;
        params.leftMargin = offset + DeviceUtils.dip2px(this, 80);
        mImageView.setLayoutParams(params);
        Matrix matrix = new Matrix();
        matrix.postTranslate(offset, 0);
        mImageView.setImageMatrix(matrix);
    }

    private void doTab(int position) {
        if (mCardPagerAdapter != null) {
            MyCardListView list = mCardPagerAdapter.getListViewAtPosition(position);
            if (list != null) {
            }
        }


        // 获取每一个标签页所占宽度
      /*  int section = offset * 2 + pWidth;
        Animation animation = new TranslateAnimation(section * currentIndex, section * position, 0, 0);
        currentIndex = position;
        animation.setDuration(300l);
        animation.setFillAfter(true);
        mImageView.startAnimation(animation);*/
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mImageView.getLayoutParams();

        if (position == 0) {
            int section = (screenWidth - s1) / 2;
            Animation animation = new TranslateAnimation(section * currentIndex, section * position, 0, 0);
            currentIndex = position;
            animation.setDuration(300l);
            animation.setFillAfter(true);
            mImageView.startAnimation(animation);
            params.width = s1;
            mImageView.setLayoutParams(params);
        } else if (position == 1) {

            int section = (screenWidth - s1 - s2) / 2 + s1;
            Animation animation = new TranslateAnimation(section * currentIndex, section * position, 0, 0);
            currentIndex = position;
            animation.setDuration(300l);
            animation.setFillAfter(true);
            mImageView.startAnimation(animation);
            params.width = s2;
            mImageView.setLayoutParams(params);
        }

    }

    public int getTabPosition() {
        if (mTabPagerStrip == null) {
            return MyCardPagerAdapter.TabType.TAB_MY_CARD.ordinal();
        }
        return mTabPagerStrip.getCurrentPage();
    }

    public int switchToTab(int tab) {
        if (mViewPager != null) {
            if (getTabPosition() != tab) {
                mViewPager.setCurrentItem(tab);
            }
            mInitNavigateTo = -1;
            return 0;
        } else {
            mInitNavigateTo = tab;
            return -1;
        }
    }




}