package com.allin.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.allin.R;
import com.allin.activity.interfaces.OnViewMethodInterface;
import com.allin.adapter.CommentsAdapter;
import com.allin.adapter.delegate.CommentDelegate;
import com.allin.adapter.interfaces.OnCommentEventListner;
import com.allin.response.NewsInfo;
import com.allin.util.Constants;
import com.allin.util.MyApplication;
import com.allin.util.PreferenceManager;
import com.allin.util.Utils;
import com.allin.view.AToolbar;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.sothree.slidinguppanel.ScrollableViewHelper;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.ArrayList;

public class NewsDetailActivity extends BaseActivity implements OnViewMethodInterface, OnCommentEventListner {

    private AToolbar aToolbar;
    private ImageView ivNewsImage;
    private ImageView ivPlay;
    private TextView tvHeadline;
    private TextView tvCreationDate;
    private TextView tvNewsDescription;
    private TextView tvCommentsCount;
    private SlidingUpPanelLayout slidingUpPanelLayout;
    private RecyclerView rvComment;
    private CommentsAdapter commentsAdapter;
    private ArrayList<String> alData = new ArrayList<>();

    private NewsInfo newsInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        setActivity(this);

        if (getIntent().hasExtra(Constants.Extras.EXTRA_NEWS_INFO)) {
            newsInfo = (NewsInfo) getIntent().getSerializableExtra(Constants.Extras.EXTRA_NEWS_INFO);
        }

        initToolbar();
        initViews();

        setData();
    }

    public static void startActivity(Context context, NewsInfo newsInfo) {
        Intent intent = new Intent(context, NewsDetailActivity.class);
        intent.putExtra(Constants.Extras.EXTRA_NEWS_INFO, newsInfo);
        context.startActivity(intent);
    }

    @Override
    public void initToolbar() {
        aToolbar = findViewById(R.id.atoolbar);
        aToolbar.setToolbar();
        aToolbar.setTvTitleVisibility(true);
        aToolbar.setTvTitle(getString(R.string.news));
        aToolbar.setToolbarBackButtonVisibility(true);
        aToolbar.setToolbarBackButtonGoToBack();
        aToolbar.setBackgroundColor(getResources().getColor(R.color.black));
        aToolbar.setTvTitleColor(getResources().getColor(R.color.white));
    }

    @Override
    public void initViews() {
        ivNewsImage = findViewById(R.id.iv_news_image);
        ivPlay = findViewById(R.id.iv_play);
        tvHeadline = findViewById(R.id.tv_headline);
        tvCreationDate = findViewById(R.id.tv_creation_date);
        tvNewsDescription = findViewById(R.id.tv_news_description);
        slidingUpPanelLayout = findViewById(R.id.sliding_layout);
        rvComment = findViewById(R.id.rv_comments);
        tvCommentsCount=findViewById(R.id.tv_comments_count);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(NewsDetailActivity.this, LinearLayoutManager.VERTICAL, false);
        commentsAdapter = new CommentsAdapter.CommentsAdapterBuilder()
                .with(NewsDetailActivity.this)
                .setData(alData)
                .setDelegate(new CommentDelegate())
                .setOnCommentsEventListener(this)
                .create();

//        rvComment.setHasFixedSize(true);
        rvComment.setLayoutManager(linearLayoutManager);
        rvComment.setAdapter(commentsAdapter);

        setFonts();
        setEvents();
    }

    private void setFonts(){
        tvHeadline.setTypeface(fontUtils.getMontserratRegular());
        tvCreationDate.setTypeface(fontUtils.getMontserratRegular());
        tvNewsDescription.setTypeface(fontUtils.getMontserratRegular());
        tvCommentsCount.setTypeface(fontUtils.getMontserratRegular());
        aToolbar.getTvTitleCenter().setTypeface(fontUtils.getMontserratRegular());
    }

    @Override
    public void setEvents() {
        ivPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(newsInfo.video));
                    intent.setDataAndType(Uri.parse(newsInfo.video), "video/*");
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    public void setData() {
        try {
            alData.add("");
            alData.add("");
            alData.add("");
            alData.add("");
            if (newsInfo.video == null || newsInfo.video.equals(""))
                ivPlay.setVisibility(View.GONE);
            else
                ivPlay.setVisibility(View.VISIBLE);

            tvHeadline.setText("" + newsInfo.headline);
            tvCreationDate.setText("" + newsInfo.createTime);
            tvNewsDescription.setText("" + newsInfo.body);

            Glide.with(MyApplication.getMyApplcation().getAppContext())
                    .load(new GlideUrl(String.format(Constants.URLs.URL_NEWS_IMAGE, newsInfo.newsId), new LazyHeaders.Builder()
                            .addHeader("Authorization", "Bearer " + PreferenceManager.getString(MyApplication.getMyApplcation().getAppContext(), Constants.PrefsKey.PREFS_ACCESS_TOKEN))
                            .build()))
                    .into(ivNewsImage);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;

        slidingUpPanelLayout.setPanelHeight((int) (height - ivNewsImage.getHeight() - aToolbar.getHeight() - Utils.dpToPx(10)));
        slidingUpPanelLayout.setScrollableViewHelper(new NestedScrollableViewHelper());
    }

    public class NestedScrollableViewHelper extends ScrollableViewHelper {
        public int getScrollableViewScrollPosition(View scrollableView, boolean isSlidingUp) {
            if (scrollableView instanceof ScrollView) {
                if (isSlidingUp) {
                    return scrollableView.getScrollY();
                } else {
                    ScrollView nsv = ((ScrollView) scrollableView);
                    View child = nsv.getChildAt(0);
                    return (child.getBottom() - (nsv.getHeight() + nsv.getScrollY()));
                }
            } else {
                return 0;
            }
        }
    }
}
