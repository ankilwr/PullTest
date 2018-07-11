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
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private PullRecyclerView pullView;

    private LayoutInflater inflater;
    private TextAdapter adapter;

    private Handler handler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inflater = LayoutInflater.from(this);

        pullView = findViewById(R.id.pullView);
        pullView.setPullListener(new PullRecyclerView.PullListener() {
            @Override
            public void onLoadData(final boolean isRefresh, final int page) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        List<String> list = new ArrayList<>();
                            for (int i = 15; i > 0; i--) {
                                list.add("测试" + ((page == 0 ? 1:page)*15 - i + 1));
                            }
                            if (page == 1) {
                            adapter.resetDatas(list);
                        } else {
                            adapter.addDatas(list);
                        }
                        pullView.loadFinish(isRefresh, page < 5);
                    }
                }, 2000);
            }
        });
        pullView.setPullEnable(true, true);
        pullView.getSwipeRecyclerView().setLayoutManager(new GridLayoutManager(this, 1));

        pullView.getSwipeRecyclerView().setSwipeMenuCreator(swipeMenuCreator);

        //mRecyclerView.setSwipeItemClickListener(mItemClickListener); // RecyclerView Item点击监听。
        adapter = new TextAdapter();
        pullView.setAdapter(adapter);
        pullView.pullRefreshing();
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
