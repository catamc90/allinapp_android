package com.allin.adapter.delegate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.allin.R;
import com.allin.adapter.LeaderboardAdapter;
import com.allin.adapter.interfaces.OnLeaderboardEventListener;
import com.allin.adapter.viewholder.LeaderboardHolder;
import com.allin.response.UserInfo;
import com.allin.util.FontUtils;

/**
 * Created by harry on 11/30/17.
 */

public class LeaderboardDelegate {

    private Context mContext;
    private FontUtils fontUtils;

    public LeaderboardDelegate() {
    }

    public LeaderboardHolder onCreateLeaderboardHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        fontUtils=new FontUtils(mContext);
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_leaderboard, parent, false);
        return new LeaderboardHolder(view);
    }

    public void onBindLeaderboardHolder(final LeaderboardAdapter leaderboardAdapter, UserInfo data, LeaderboardHolder holder, final int position, final OnLeaderboardEventListener onLeaderboardEventListener) {
        holder.bind(data);
        holder.setFonts(fontUtils);
    }

}
