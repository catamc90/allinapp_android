package com.allin.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.allin.R;
import com.allin.response.UserInfo;
import com.allin.util.Constants;
import com.allin.util.FontUtils;
import com.allin.util.MyApplication;
import com.allin.util.PreferenceManager;
import com.allin.view.CircleImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.bumptech.glide.request.RequestOptions;

/**
 * Created by harry on 11/30/17.
 */

public class LeaderboardHolder extends RecyclerView.ViewHolder {

    public CircleImageView civProfilePhoto;
    public TextView tvUserName;
    public TextView tvItemCount;
    public TextView tvSocialName;
    public TextView tvFeedName;
    public TextView tvSocialCount;
    public TextView tvFeedCount;

    public LeaderboardHolder(View itemView) {
        super(itemView);
        civProfilePhoto = itemView.findViewById(R.id.civ_profile_photo);
        tvUserName = itemView.findViewById(R.id.tv_user_name);
        tvItemCount = itemView.findViewById(R.id.tv_item_count);
        tvSocialName = itemView.findViewById(R.id.tv_social_name);
        tvFeedName = itemView.findViewById(R.id.tv_feed_name);
        tvSocialCount = itemView.findViewById(R.id.tv_social_count);
        tvFeedCount = itemView.findViewById(R.id.tv_feed_count);
    }

    public void bind(UserInfo data) {
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.img_profile_sml);
        Glide.with(MyApplication.getMyApplcation().getAppContext())
                .load(new GlideUrl(String.format(Constants.URLs.URL_GET_USER_IMAGE, data.userId), new LazyHeaders.Builder()
                        .addHeader("Authorization", "Bearer " + PreferenceManager.getString(MyApplication.getMyApplcation().getAppContext(), Constants.PrefsKey.PREFS_ACCESS_TOKEN))
                        .build()))
                .apply(options)
                .into(civProfilePhoto);


        tvUserName.setText("" + data.displayName);
        tvItemCount.setText(String.format(MyApplication.getMyApplcation().getStringResource(R.string.total_points), data.assetCount));

        civProfilePhoto.setBorderColor(MyApplication.getMyApplcation().getColorResource(R.color.gold));

        tvSocialCount.setText("" + data.social);
        tvFeedCount.setText("" + data.feed);

    }

    public void setFonts(FontUtils fonts) {
        tvUserName.setTypeface(fonts.getMontserratRegular());
        tvItemCount.setTypeface(fonts.getMontserratRegular());
        tvSocialName.setTypeface(fonts.getMontserratRegular());
        tvFeedName.setTypeface(fonts.getMontserratRegular());
        tvSocialCount.setTypeface(fonts.getMontserratRegular());
        tvFeedCount.setTypeface(fonts.getMontserratRegular());

    }
}
