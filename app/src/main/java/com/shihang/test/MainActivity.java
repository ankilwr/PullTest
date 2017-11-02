package com.shihang.test;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.shihang.pulltorefresh.PullRecyclerView;
import com.shihang.pulltorefresh.PullRecyclerView.LoadListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private PullRecyclerView pullView;
    private SwipeMenuRecyclerView mRecyclerView;

    private LayoutInflater inflater;
    private TextAdapter adapter;

    private Handler handler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inflater = LayoutInflater.from(this);

        pullView = (PullRecyclerView) findViewById(R.id.pullView);
        pullView.setPullListener(refreshListener);
        pullView.setPullEnable(true, true);
        mRecyclerView = pullView.getSwipeRecyclerView();
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 1));

        mRecyclerView.setSwipeMenuCreator(swipeMenuCreator);

        //mRecyclerView.setSwipeItemClickListener(mItemClickListener); // RecyclerView Item点击监听。
        adapter = new TextAdapter();
        mRecyclerView.setAdapter(adapter);
    }


    private SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
            SwipeMenuItem deleteItem = new SwipeMenuItem(MainActivity.this)
                    .setBackground(R.drawable.red)
                    .setText("删除")
                    .setTextColor(Color.WHITE)
                    .setWidth(140)
                    .setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
            swipeRightMenu.addMenuItem(deleteItem);// 添加菜单到右侧。

            SwipeMenuItem addItem = new SwipeMenuItem(MainActivity.this)
                    .setBackground(R.drawable.blue)
                    .setText("添加")
                    .setTextColor(Color.WHITE)
                    .setWidth(140)
                    .setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
            swipeRightMenu.addMenuItem(addItem); // 添加菜单到右侧。
        }
    };


    private LoadListener refreshListener = new LoadListener() {

        private void loadData(final int start) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    List<String> list = new ArrayList<>();
                    for (int i = start; i < start + 15; i++) {
                        list.add("测试" + i);
                    }
                    if (start == 0) {
                        adapter.resetDatas(list);
                    } else {
                        adapter.addDatas(list);
                    }
                    mRecyclerView.loadMoreFinish(true, start < 60);
                }
            }, 2000);
        }

        @Override
        public void onRefresh() {
            loadData(0);
        }

        @Override
        public void onLoadMore() {
            loadData(adapter.getItemCount());
        }
    };

    public class TextAdapter extends RecyclerView.Adapter<MainActivity.ViewHolder> {

        private List<String> beans = new ArrayList<>();

        public void addDatas(List<String> beans) {
            this.beans.addAll(beans);
            notifyDataSetChanged();
        }

        public void resetDatas(List<String> beans) {
            if (beans != null) this.beans = beans;
            notifyDataSetChanged();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(inflater.inflate(R.layout.item_text, parent, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.text.setText(beans.get(position));
        }

        @Override
        public int getItemCount() {
            return beans.size();
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView text;

        public ViewHolder(View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.text);
        }
    }

}
