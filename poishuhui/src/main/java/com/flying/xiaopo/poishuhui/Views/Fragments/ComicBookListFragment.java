package com.flying.xiaopo.poishuhui.Views.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flying.xiaopo.poishuhui.Adapters.GridAdapter;
import com.flying.xiaopo.poishuhui.Adapters.Impl.OnCellClickListener;
import com.flying.xiaopo.poishuhui.Beans.ItemBean;
import com.flying.xiaopo.poishuhui.R;
import com.flying.xiaopo.poishuhui.Utils.HtmlTask;
import com.flying.xiaopo.poishuhui.Utils.Utils;
import com.flying.xiaopo.poishuhui.Views.Activities.ComicDetailActivity;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 漫画图书列表Fragment
 * Created by lenovo on 2015/8/20.
 */
public class ComicBookListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, OnCellClickListener {
    public static final String INTENT_KEY = "torv";

    @InjectView(R.id.rv_third_list)
    RecyclerView rv_third_list;

    @InjectView(R.id.refresh_booklist)
    SwipeRefreshLayout refresh_booklist;

    GridLayoutManager manager;

    GridAdapter adapter;

    String taskURL = null;

    Context context;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
        adapter = new GridAdapter(context) {
            @Override
            public void onBindViewHolder(CellViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                holder.getTv_title().setHeight(Utils.dp2px(30));
                holder.getTv_title().setTextSize(20.0f);
            }
        };
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_comic_booklist, container, false);
        ButterKnife.inject(this, rootView);
        manager = new GridLayoutManager(context, 2, LinearLayoutManager.VERTICAL, false);

        rv_third_list.setLayoutManager(manager);
        rv_third_list.setAdapter(adapter);

        refresh_booklist.setOnRefreshListener(this);
        refresh_booklist.setRefreshing(true);

        onRefresh();

        return rootView;
    }

    public String getTaskURL() {
        return taskURL;
    }

    public void setTaskURL(String taskURL) {
        this.taskURL = taskURL;
    }

    protected void startLoad(String URL) {
        HtmlTask task = new HtmlTask() {
            @Override
            protected void onPostExecute(List<ItemBean> baseBeans) {
                super.onPostExecute(baseBeans);
                adapter.obtainData(baseBeans);
                adapter.notifyDataSetChanged();
                adapter.setOnCellClickListener(ComicBookListFragment.this);
                refresh_booklist.setRefreshing(false);
            }
        };
        task.execute(URL, HtmlTask.TASK_LIST);
    }


    @Override
    public void onRefresh() {
        if (taskURL != null) startLoad(taskURL);
    }

    //TODO
    @Override
    public void onCellClick(View view, int position) {
        Intent intent = new Intent();
        intent.putExtra(INTENT_KEY, adapter.getData().get(position).getLink());
        intent.setClass(context, ComicDetailActivity.class);
        startActivity(intent);
    }
}
