package com.allin.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.allin.adapter.delegate.FeedDelegate;
import com.allin.adapter.interfaces.OnFeedEventListener;
import com.allin.adapter.viewholder.FeedHolder;
import com.allin.response.AssetInfo;

import java.util.ArrayList;

/**
 * Created by harry on 11/29/17.
 */

public class FeedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<AssetInfo> alData;
    private FeedDelegate feedDelegate;
    private OnFeedEventListener feedEventListener;

    public FeedAdapter(Context mContext, ArrayList<AssetInfo> alData, FeedDelegate feedDelegate, OnFeedEventListener feedEventListener) {
        this.mContext = mContext;
        this.alData = alData;
        this.feedDelegate = feedDelegate;
        this.feedEventListener = feedEventListener;
    }

    public Context getmContext() {
        return mContext;
    }

    public void add(AssetInfo data) {
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

    public void addData(ArrayList<AssetInfo> data) {
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

    public void addAll(ArrayList<AssetInfo> data) {
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
        return feedDelegate.onCreateFeedHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final AssetInfo data = alData.get(position);
        feedDelegate.onBindFeedHolder(this, data, (FeedHolder) holder, position, feedEventListener);
    }

    @Override
    public int getItemCount() {
        return alData.size();
    }

    public static class FeedAdapterBuilder {

        private Context mContext;
        private ArrayList<AssetInfo> alData;
        private FeedDelegate feedDelegate;
        private OnFeedEventListener feedEventListener;

        public FeedAdapterBuilder with(@NonNull Context context) {
            this.mContext = context;
            return this;
        }

        public FeedAdapterBuilder setData(@NonNull ArrayList<AssetInfo> data) {
            this.alData = data;
            return this;
        }

        public FeedAdapterBuilder setFeedDelegate(FeedDelegate feedDelegate) {
            this.feedDelegate = feedDelegate;
            return this;
        }

        public FeedAdapterBuilder setFeedEventListener(OnFeedEventListener feedEventListener) {
            this.feedEventListener = feedEventListener;
            return this;
        }

        public FeedAdapter create() {
            return new FeedAdapter(mContext, alData, feedDelegate, feedEventListener);
        }
    }
}
