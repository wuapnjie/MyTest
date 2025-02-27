package com.flying.xiaopo.poishuhui.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flying.xiaopo.poishuhui.Beans.ChildItemBean;
import com.flying.xiaopo.poishuhui.R;
import com.flying.xiaopo.poishuhui.Views.Activities.NewsDetailActivity;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 第4个Fragment的news的adapter
 * Created by xiaopo on 2015/9/3.
 */
public class ChildNewsAdapter extends RecyclerView.Adapter<ChildNewsAdapter.ChildViewHolder> {
    private LayoutInflater inflater;
    private Context context;
    private List<ChildItemBean> child_data;

    public ChildNewsAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void obtainData(List<ChildItemBean> child_data) {
        this.child_data = child_data;
    }

    public List<ChildItemBean> getData() {
        return child_data;
    }


    @Override
    public ChildViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.child_news_item, parent, false);
        return new ChildViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ChildViewHolder holder, final int position) {
        final ChildItemBean bean = child_data.get(position);
        int resId = position % 2 == 0 ? R.color.news_gray : R.color.news_white;
        holder.child_news_container.setBackgroundResource(resId);
        holder.tv_news_title.setText(bean.getChildTitle());
        holder.tv_created_time.setText(bean.getCreatedTime());
        holder.child_news_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(context,NewsDetailActivity.class);
                intent.putExtra(NewsDetailActivity.INTENT_KEY_URL, child_data.get(position).getLink());
                intent.putExtra(NewsDetailActivity.INTENT_KEY_TITLE,child_data.get(position).getChildTitle());
                intent.putExtra(NewsDetailActivity.INTENT_KEY_TIME,child_data.get(position).getCreatedTime());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return child_data == null ? 0 : child_data.size();
    }


    public class ChildViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.tv_news_title)
        TextView tv_news_title;
        @InjectView(R.id.tv_created_time)
        TextView tv_created_time;
        @InjectView(R.id.child_news_container)
        LinearLayout child_news_container;

        public ChildViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }
}
