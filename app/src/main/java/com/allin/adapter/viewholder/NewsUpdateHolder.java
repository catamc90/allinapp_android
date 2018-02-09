package com.allin.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.allin.R;
import com.allin.response.NewsInfo;
import com.allin.util.Constants;
import com.allin.util.FontUtils;
import com.allin.util.MyApplication;
import com.allin.util.PreferenceManager;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;

/**
 * Created by Asif on 11/30/17.
 */

public class NewsUpdateHolder extends RecyclerView.ViewHolder {

    public LinearLayout llContainerNews;
    public ImageView ivNewsImage;
    public TextView tvNewsReadStatus;
    public TextView tvNewsDescription;
    private TextView tvCommentsCount;

    public NewsUpdateHolder(View itemView) {
        super(itemView);

        llContainerNews = itemView.findViewById(R.id.ll_container_news);
        ivNewsImage = itemView.findViewById(R.id.iv_news_image);
        tvNewsReadStatus = itemView.findViewById(R.id.tv_news_read_status);
        tvNewsDescription = itemView.findViewById(R.id.tv_news_description);
        tvCommentsCount = itemView.findViewById(R.id.tv_comments_count);
    }

    public void bind(NewsInfo data) {

        Glide.with(MyApplication.getMyApplcation().getAppContext())
                .load(new GlideUrl(String.format(Constants.URLs.URL_NEWS_IMAGE, data.newsId), new LazyHeaders.Builder()
                        .addHeader("Authorization", "Bearer " + PreferenceManager.getString(MyApplication.getMyApplcation().getAppContext(), Constants.PrefsKey.PREFS_ACCESS_TOKEN))
                        .build()))
                .into(ivNewsImage);

        if (data.readTime == null) {
            tvNewsReadStatus.setText(MyApplication.getMyApplcation().getStringResource(R.string.unread_news));
            tvNewsReadStatus.setBackground(MyApplication.getMyApplcation().getDrawableResource(R.drawable.bg_rounded_rectangle_medium_green));
        } else {
            tvNewsReadStatus.setText(MyApplication.getMyApplcation().getStringResource(R.string.news));
            tvNewsReadStatus.setBackground(MyApplication.getMyApplcation().getDrawableResource(R.drawable.bg_rounded_rectangle_slat_gray));
        }
        tvNewsDescription.setText("" + data.headline);
    }

    public void setFonts(FontUtils fonts) {
        tvNewsReadStatus.setTypeface(fonts.getMontserratRegular());
        tvNewsDescription.setTypeface(fonts.getMontserratRegular());
        tvCommentsCount.setTypeface(fonts.getMontserratRegular());
    }
}
