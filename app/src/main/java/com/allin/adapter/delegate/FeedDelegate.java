package com.allin.adapter.delegate;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.allin.R;
import com.allin.adapter.FeedAdapter;
import com.allin.adapter.interfaces.OnFeedEventListener;
import com.allin.adapter.viewholder.FeedHolder;
import com.allin.response.AssetInfo;
import com.allin.util.FontUtils;
import com.eightbitlab.supportrenderscriptblur.SupportRenderScriptBlur;

import eightbitlab.com.blurview.BlurView;

/**
 * Created by harry on 11/29/17.
 */

public class FeedDelegate {

    private Context mContext;
    private FontUtils fontUtils;

    public FeedDelegate() {

    }

    public FeedHolder onCreateFeedHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        fontUtils=new FontUtils(mContext);
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_feed, parent, false);
        return new FeedHolder(view);
    }

    public void onBindFeedHolder(final FeedAdapter feedAdapter, final AssetInfo data, final FeedHolder holder, final int position, final OnFeedEventListener feedEventListener) {
        holder.bind(data);
        holder.setFonts(fontUtils);
        holder.rlContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                feedEventListener.onClick(position);
                if (holder.blurView.getVisibility() == View.GONE)
                    holder.blurView.setVisibility(View.VISIBLE);
                else
                    holder.blurView.setVisibility(View.GONE);
            }
        });

        holder.ivFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                data.starred = !data.starred;
                if (data.starred) {
                    holder.ivFavourite.setImageResource(R.drawable.ic_star2);
                    holder.tvVotes.setText("" + (data.votes = data.votes + 1));
                } else {
                    holder.ivFavourite.setImageResource(R.drawable.ic_star);
                    holder.tvVotes.setText("" + (data.votes == 0 ? "" : (data.votes = data.votes - 1)));
                }
                if (data.votes > 0) {
                    holder.tvVotes.setVisibility(View.VISIBLE);
                } else {
                    holder.tvVotes.setVisibility(View.GONE);
                }
                feedEventListener.onFavouriteClick(position, data.starred);
            }
        });

        final Drawable windowBackground = ((Activity) feedAdapter.getmContext()).getWindow().getDecorView().getBackground();

        final BlurView.ControllerSettings topViewSettings = holder.blurView.setupWith(holder.rlContainer)
                .windowBackground(windowBackground)
                .blurAlgorithm(new SupportRenderScriptBlur(feedAdapter.getmContext()))
                .blurRadius(25f);
    }
}
