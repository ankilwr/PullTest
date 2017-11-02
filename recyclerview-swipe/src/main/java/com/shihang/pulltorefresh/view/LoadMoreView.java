package com.shihang.pulltorefresh.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.yanzhenjie.recyclerview.swipe.R;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

public class LoadMoreView extends LinearLayout implements SwipeMenuRecyclerView.LoadMoreAction {

    private TextView textview;

    public LoadMoreView(Context context) {
        this(context, null);
    }

    public LoadMoreView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inflate(getContext(), R.layout.view_load_more, this);
        textview = findViewById(R.id.textHint);
    }

    @Override
    public void onLoading() {
        setClickable(false);
        textview.setText("正在加载...");
        Log.i("Test", hashCode() + "onLoading:正在加载...");
    }

    @Override
    public void onLoadFinish(boolean hasMore) {
        setClickable(false);
        Log.i("Test", hashCode() + "onLoadFinish:加载更多");
        if(hasMore){
            textview.setText("加载更多");
        }else{
            textview.setText("已经到底了");
        }
    }


    @Override
    public void onLoadError(int errorCode, String errorMessage) {
        setClickable(true);
        textview.setText("加载失败");
    }
}
