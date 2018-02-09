package com.allin.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.allin.R;
import com.allin.enums.SocialType;
import com.allin.response.AssetInfo;
import com.allin.util.Constants;
import com.allin.util.FontUtils;
import com.allin.util.MyApplication;
import com.allin.util.PreferenceManager;
import com.allin.view.CircleImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.bumptech.glide.request.RequestOptions;

import eightbitlab.com.blurview.BlurView;

/**
 * Created by harry on 11/29/17.
 */

public class FeedHolder extends RecyclerView.ViewHolder {

    public FrameLayout rlContainer;
    public FrameLayout flContainerTransparent;
    public ImageView ivFeedPhoto;
    public ImageView ivInstagram;
    public ImageView ivTwitter;
    public ImageView ivFacebook;
    public ImageView ivFavourite;
    public TextView tvCaption;
    public TextView tvUserName;
    public TextView tvVotes;
    public CircleImageView ivUserPhoto;
    public BlurView blurView;


    public FeedHolder(View itemView) {
        super(itemView);
        rlContainer = itemView.findViewById(R.id.rl_container);
        ivFeedPhoto = itemView.findViewById(R.id.iv_feed_photo);
        ivInstagram = itemView.findViewById(R.id.iv_instagram);
        ivTwitter = itemView.findViewById(R.id.iv_twitter);
        ivFacebook = itemView.findViewById(R.id.iv_facebook);
        ivUserPhoto = itemView.findViewById(R.id.iv_user_photo);
        ivFavourite = itemView.findViewById(R.id.iv_favourite);
        flContainerTransparent = itemView.findViewById(R.id.fl_container_transparent);
        tvUserName = itemView.findViewById(R.id.tv_user_name);
        tvCaption = itemView.findViewById(R.id.tv_hash_tag);
        tvVotes = itemView.findViewById(R.id.tv_votes);
        blurView = itemView.findViewById(R.id.blurView);
    }

    public void bind(AssetInfo data) {

        Glide.with(MyApplication.getMyApplcation().getAppContext())
                .load(new GlideUrl(Constants.URLs.URL_GET_ASSET_IMAGE + data.assetId, new LazyHeaders.Builder()
                        .addHeader("Authorization", "Bearer " + PreferenceManager.getString(MyApplication.getMyApplcation().getAppContext(), Constants.PrefsKey.PREFS_ACCESS_TOKEN))
                        .build()))
                .into(ivFeedPhoto);

        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.img_profile_sml);
        Glide.with(MyApplication.getMyApplcation().getAppContext())
                .load(new GlideUrl(String.format(Constants.URLs.URL_GET_USER_IMAGE, data.userId), new LazyHeaders.Builder()
                        .addHeader("Authorization", "Bearer " + PreferenceManager.getString(MyApplication.getMyApplcation().getAppContext(), Constants.PrefsKey.PREFS_ACCESS_TOKEN))
                        .build()))
                .apply(options)
                .into(ivUserPhoto);

        if (data.userDisplayName == null || data.userDisplayName.equals(""))
            tvUserName.setText("");
        else
            tvUserName.setText("- " + data.userDisplayName);
        tvCaption.setText("" + data.caption);

        if ((data.social & SocialType.INSTAGRAM.getSocialType()) > 0)
            ivInstagram.setVisibility(View.VISIBLE);
        else
            ivInstagram.setVisibility(View.GONE);

        if ((data.social & SocialType.TWITTER.getSocialType()) > 0)
            ivTwitter.setVisibility(View.VISIBLE);
        else
            ivTwitter.setVisibility(View.GONE);

        if ((data.social & SocialType.FACEBOOK.getSocialType()) > 0)
            ivFacebook.setVisibility(View.VISIBLE);
        else
            ivFacebook.setVisibility(View.GONE);

        if (data.votes > 0) {
            tvVotes.setVisibility(View.VISIBLE);
            tvVotes.setText("" + data.votes);
        } else {
            tvVotes.setVisibility(View.GONE);
        }
        if (data.starred)
            ivFavourite.setImageResource(R.drawable.ic_star2);
        else
            ivFavourite.setImageResource(R.drawable.ic_star);
        switch (data.userRanking) {
            case 1:
                ivUserPhoto.setBorderColor(MyApplication.getMyApplcation().getColorResource(R.color.gold));
                break;
            case 2:
                ivUserPhoto.setBorderColor(MyApplication.getMyApplcation().getColorResource(R.color.silver));
                break;
            case 3:
                ivUserPhoto.setBorderColor(MyApplication.getMyApplcation().getColorResource(R.color.bronze));
                break;
            default:
                ivUserPhoto.setBorderColor(MyApplication.getMyApplcation().getColorResource(R.color.trans_white));
                break;
        }

    }

    public void setFonts(FontUtils fonts) {
        tvUserName.setTypeface(fonts.getMontserratRegular());
        tvVotes.setTypeface(fonts.getMontserratRegular());
        tvCaption.setTypeface(fonts.getMontserratRegular());
    }
}
