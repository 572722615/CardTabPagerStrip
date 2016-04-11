package com.view.chip.customtabpagerstrip;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ListView;

public class MyCardListView extends FrameLayout {
    public ListView mListView;
    private int position;
    private int first_page = 0;
    private int second_page = 0;
    private FragmentActivity activity;

    public MyCardListView(Context context,
                          AttributeSet attrs) {
        super(context, attrs);
    }

    public void setFragmentActivity(FragmentActivity activity) {
        this.activity = activity;
    }

    public void setPosition(int position) {
        this.position = position;
    }







    public void onResume() {
    }

    public void onPause() {
    }

    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
    }



    public void firstRequestData(String classify, boolean needRefresh) {
        if (needRefresh) {
            doFirstRefresh(classify);
        }
    }



    public void refreshList(String classify) {
        doFirstRefresh(classify);
    }

    private void doFirstRefresh(String classify) {
        if (TextUtils.isEmpty(classify)) {
            return;
        }
       /* Map<String, String> requestTag = DiscountDataManager.getInstance().getRequestTag();
        if (requestTag == null || TextUtils.isEmpty(requestTag.get(classify)) ||
                mAdapter.isEmpty()) {
            pullToRefreshLV.setRefreshing(true);
        }*/
    }






}
