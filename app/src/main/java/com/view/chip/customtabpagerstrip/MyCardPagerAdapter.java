package com.view.chip.customtabpagerstrip;

import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.HashMap;

public class MyCardPagerAdapter extends PagerAdapter {
    private SparseArray<MyCardListView> map = new SparseArray<>();
    private HashMap<String, String> classifyHM = new HashMap<>();
    private final String[] mTypes = {"我的会员卡", "领取的会员卡" };
    private Handler mHandler = new Handler();
    private FragmentActivity activity;

    public enum TabType {
        TAB_MY_CARD,
        TAB_DRAW_CARD,
    }

    public MyCardPagerAdapter(FragmentActivity activity) {
        this.activity = activity;
    }

    @Override
    public Object instantiateItem(ViewGroup container,
                                  int pos) {
        final int position = pos;
        final MyCardListView list0 = map.get(position);

        if (list0 != null) {
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            container.addView(list0, params);
            return list0;
        }
        final MyCardListView list = (MyCardListView) LayoutInflater.from(activity).inflate(R.layout.my_card_list_view, null);
        list.setPosition(pos);
        list.setFragmentActivity(activity);
        final boolean needRefresh;
        if (0 == pos) {
            needRefresh = true;
        } else {
            needRefresh = false;
        }



        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        container.addView(list, params);
        map.put(position, list);
        classifyHM.put(position + "", position + "");
        return list;
    }

    @Override
    public void destroyItem(ViewGroup container,
                            int position,
                            Object object) {
        container.removeView((View) object);
        map.remove(position);
    }

    public MyCardListView getListViewAtPosition(int position) {
        return map.get(position);
    }

    @Override
    public boolean isViewFromObject(View arg0,
                                    Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public int getCount() {
        return mTypes == null ? 0 : mTypes.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTypes == null ? "" : mTypes[position];
    }
}
