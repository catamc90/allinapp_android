package com.allin.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.allin.adapter.delegate.LeaderboardDelegate;
import com.allin.adapter.interfaces.OnLeaderboardEventListener;
import com.allin.adapter.viewholder.LeaderboardHolder;
import com.allin.response.UserInfo;

import java.util.ArrayList;

/**
 * Created by harry on 11/30/17.
 */

public class LeaderboardAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<UserInfo> alData;
    private LeaderboardDelegate leaderboardDelegate;
    private OnLeaderboardEventListener onLeaderboardEventListener;

    public LeaderboardAdapter(Context mContext, ArrayList<UserInfo> alData, LeaderboardDelegate leaderboardDelegate, OnLeaderboardEventListener onLeaderboardEventListener) {
        this.mContext = mContext;
        this.alData = alData;
        this.leaderboardDelegate = leaderboardDelegate;
        this.onLeaderboardEventListener = onLeaderboardEventListener;
    }

    public void add(UserInfo data) {
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

    public void addData(ArrayList<UserInfo> data) {
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

    public void addAll(ArrayList<UserInfo> data) {
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
        return leaderboardDelegate.onCreateLeaderboardHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        UserInfo data = alData.get(position);
        leaderboardDelegate.onBindLeaderboardHolder(this, data, (LeaderboardHolder) holder, position, onLeaderboardEventListener);
    }

    @Override
    public int getItemCount() {
        return alData.size();
    }

    public static class LeaderboardAdapterBuilder {
        private Context mContext;
        private ArrayList<UserInfo> aldata;
        private LeaderboardDelegate leaderboardDelegate;
        private OnLeaderboardEventListener onLeaderboardEventListener;

        public LeaderboardAdapterBuilder with(@NonNull Context context) {
            this.mContext = context;
            return this;
        }

        public LeaderboardAdapterBuilder setData(@NonNull ArrayList<UserInfo> data) {
            this.aldata = data;
            return this;
        }

        public LeaderboardAdapterBuilder setLeaderboardDelegate(LeaderboardDelegate leaderboardDelegate) {
            this.leaderboardDelegate = leaderboardDelegate;
            return this;
        }

        public LeaderboardAdapterBuilder setLeaderboardEventListener(OnLeaderboardEventListener leaderboardEventListener) {
            this.onLeaderboardEventListener = leaderboardEventListener;
            return this;
        }

        public LeaderboardAdapter create() {
            return new LeaderboardAdapter(mContext, aldata, leaderboardDelegate, onLeaderboardEventListener);
        }

    }
}
