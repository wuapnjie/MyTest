package com.flying.xiaopo.poishuhui.Views.Fragments;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flying.xiaopo.poishuhui.Adapters.ContainerLinearAdapter;
import com.flying.xiaopo.poishuhui.Beans.ContainerBean;
import com.flying.xiaopo.poishuhui.R;
import com.flying.xiaopo.poishuhui.Utils.HtmlUtil;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by lenovo on 2015/9/3.
 */
public class ComicNewsFragment extends Fragment {
    Context context;
    @InjectView(R.id.rv_comic_news)
    RecyclerView rv_comic_news;
    LinearLayoutManager manager;
    ContainerLinearAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        context = getActivity();
        adapter = new ContainerLinearAdapter(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_comic_news, container, false);
        ButterKnife.inject(this, rootView);
        manager = new LinearLayoutManager(context);
        rv_comic_news.setLayoutManager(manager);
        rv_comic_news.setAdapter(adapter);
        startLoad(HtmlUtil.URL_NEWS);
        return rootView;
    }

    private void startLoad(String urlNews) {
        NewsTask task = new NewsTask() {
            @Override
            protected void onPostExecute(List<ContainerBean> containerBeans) {
                super.onPostExecute(containerBeans);
                adapter.obtainData(containerBeans);
                adapter.notifyDataSetChanged();
            }
        };
        task.execute(urlNews);
    }

    private class NewsTask extends AsyncTask<String, Void, List<ContainerBean>> {
        @Override
        protected List<ContainerBean> doInBackground(String... params) {
            return HtmlUtil.obtainContainer(params[0]);
        }
    }
}
