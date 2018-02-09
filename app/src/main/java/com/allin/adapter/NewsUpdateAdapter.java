package com.allin.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.allin.adapter.delegate.NewsUpdateDelegate;
import com.allin.adapter.interfaces.OnNewsUpdateEventListener;
import com.allin.adapter.viewholder.NewsUpdateHolder;
import com.allin.response.NewsInfo;

import java.util.ArrayList;

/**
 * Created by harry on 11/30/17.
 */

public class NewsUpdateAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<NewsInfo> alData;
    private NewsUpdateDelegate newsUpdateDelegate;
    private OnNewsUpdateEventListener onNewsUpdateEventListener;

    public NewsUpdateAdapter(Context mContext, ArrayList<NewsInfo> alData, NewsUpdateDelegate newsUpdateDelegate, OnNewsUpdateEventListener onNewsUpdateEventListener) {
        this.mContext = mContext;
        this.alData = alData;
        this.newsUpdateDelegate = newsUpdateDelegate;
        this.onNewsUpdateEventListener = onNewsUpdateEventListener;
    }

    public void add(NewsInfo data) {
        try {
            if (alData == null)
                alData = new ArrayList<>();
            if (data != null) {
                alData.add(data);
                notifyItemChanged(alData.size() - 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addData(ArrayList<NewsInfo> data) {
        try {
            if (alData == null)
                alData = new ArrayList<>();
            if (data != null && data.size() > -1) {
                if (alData.size() > 0)
                    alData.clear();
                alData.addAll(data);
                notifyDataSetChanged();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addAll(ArrayList<NewsInfo> data) {
        try {
            if (alData == null)
                alData = new ArrayList<>();
            if (data != null && data.size() > -1) {
                alData.addAll(data);
                notifyItemRangeChanged(alData.size() - data.size(), data.size());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return newsUpdateDelegate.onCreateNewsUpdateHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        NewsInfo data = alData.get(position);
        newsUpdateDelegate.onBindNewsUpdateHolder(this, data, (NewsUpdateHolder) holder, position, onNewsUpdateEventListener);
    }

    @Override
    public int getItemCount() {
        return alData.size();
    }

    public static class NewsUpdateAdapterBuilder {

        private Context mContext;
        private ArrayList<NewsInfo> alData;
        private NewsUpdateDelegate newsUpdateDelegate;
        private OnNewsUpdateEventListener onNewsUpdateEventListener;

        public NewsUpdateAdapterBuilder with(@NonNull Context context) {
            this.mContext = context;
            return this;
        }

        public NewsUpdateAdapterBuilder setData(@NonNull ArrayList<NewsInfo> data) {
            this.alData = data;
            return this;
        }

        public NewsUpdateAdapterBuilder setNewsUpdateDelegate(NewsUpdateDelegate newsUpdateDelegate) {
            this.newsUpdateDelegate = newsUpdateDelegate;
            return this;
        }

        public NewsUpdateAdapterBuilder setNewsUpdateEventListener(OnNewsUpdateEventListener newsUpdateEventListener) {
            this.onNewsUpdateEventListener = newsUpdateEventListener;
            return this;
        }

        public NewsUpdateAdapter create() {
            return new NewsUpdateAdapter(mContext, alData, newsUpdateDelegate, onNewsUpdateEventListener);
        }

    }
}
