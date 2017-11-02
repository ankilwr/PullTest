package com.shihang.pulltorefresh;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.shihang.pulltorefresh.view.SwipeRefreshLayout;
import com.yanzhenjie.recyclerview.swipe.R;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

public class PullRecyclerView extends FrameLayout {

    private SwipeRefreshLayout swipeRefreshLayout;
    private SwipeMenuRecyclerView recyclerView;
    private LoadListener listener;

    public interface LoadListener extends SwipeRefreshLayout.OnRefreshListener, SwipeMenuRecyclerView.LoadMoreListener {
    }

    private LoadListener pullListener = new LoadListener() {
        @Override
        public void onRefresh() {
            if(listener != null) listener.onRefresh();
            recyclerView.setLoadMoreView(null);
        }

        @Override
        public void onLoadMore() {
            if(listener != null) listener.onLoadMore();
        }
    };

    public void setPullListener(LoadListener listener){
        this.listener = listener;
        swipeRefreshLayout.setOnRefreshListener(pullListener);
        recyclerView.setLoadMoreListener(pullListener);
    }

    public void setPullEnable(boolean header, boolean footer){
        recyclerView.setLoadMoreEnable(header, footer);
    }

    public SwipeMenuRecyclerView getSwipeRecyclerView(){
        return recyclerView;
    }

    public PullRecyclerView(@NonNull Context context) {
        this(context, null);
    }

    public PullRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inflate(getContext(), R.layout.layout_pull_to_refresh, this);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setRefreshLayout(swipeRefreshLayout);

    }


}
