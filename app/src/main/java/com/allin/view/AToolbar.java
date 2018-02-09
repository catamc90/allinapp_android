package com.allin.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.StyleRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.allin.R;

/**
 * Created by harry on 11/30/17.
 */

public class AToolbar extends LinearLayout {

    AppCompatActivity activity;
    private Context mContext;

    private Toolbar toolbar;
    private TextView tvTitle;
    private TextView tvLeft;
    private TextView tvRight;
    private ImageView ivTitle;
    private ImageView ivLeft;
    private ImageView ivRight;

    public AToolbar(Context context) {
        super(context);
        this.mContext = context;
        createView(context, 0);
    }

    public AToolbar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        TypedArray styleAttrs = context.obtainStyledAttributes(attrs, R.styleable.AToolbar);
        int style = styleAttrs.getInt(R.styleable.AToolbar_at_style, 0);
        createView(context, style);
    }

    public AToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        TypedArray styleAttrs = context.obtainStyledAttributes(attrs, R.styleable.AToolbar);
        int style = styleAttrs.getInt(R.styleable.AToolbar_at_style, 0);
        createView(context, style);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public AToolbar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        TypedArray styleAttrs = context.obtainStyledAttributes(attrs, R.styleable.AToolbar);
        int style = styleAttrs.getInt(R.styleable.AToolbar_at_style, 0);
        this.mContext = context;
        createView(context, style);
    }

    private void createView(Context context, int style) {
        activity = (AppCompatActivity) mContext;
        View view;
        if (style == 0)
            view = LayoutInflater.from(context).inflate(R.layout.toolbar, null);
        else
            view = LayoutInflater.from(context).inflate(R.layout.toolbar_black, null);
        initViews(view);
        view.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        addView(view);
    }

    private void initViews(View view) {
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        tvTitle = (TextView) view.findViewById(R.id.tvToolbarTitle);
        tvLeft = (TextView) view.findViewById(R.id.tvToolbarLeft);
        tvRight = (TextView) view.findViewById(R.id.tvToolbarRight);
        ivTitle = (ImageView) view.findViewById(R.id.ivToolbarTitle);
        ivLeft = (ImageView) view.findViewById(R.id.ivToolbarLeft);
        ivRight = (ImageView) view.findViewById(R.id.ivToolbarRight);
    }

    public void setToolbar() {
        activity.setSupportActionBar(toolbar);
    }

    public void setToolbarBackButtonVisibility(boolean isVisible) {
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(isVisible);
        activity.getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    public void setTheme(@StyleRes int theme) {
        toolbar.getContext().setTheme(theme);
    }

    public void setBackgroundColor(@ColorRes int color) {
        toolbar.setBackgroundColor(color);
    }

    public void setTvTitleColor(@ColorInt int color) {
        tvTitle.setTextColor(color);
    }

    public void setTvLeftColor(@ColorInt int color) {
        tvLeft.setTextColor(color);
    }

    public void setTvRightColor(@ColorInt int color) {
        tvRight.setTextColor(color);
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    public TextView getTvLeft() {
        return tvLeft;
    }

    public TextView getTvRight() {
        return tvRight;
    }

    public ImageView getIvTitle() {
        return ivTitle;
    }

    public ImageView getIvLeft() {
        return ivLeft;
    }

    public ImageView getIvRight() {
        return ivRight;
    }

    public void setTvTitleVisibility(boolean isVisible) {
        tvTitle.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    public boolean getTvTitleVisibility() {
        return tvTitle.getVisibility() == View.VISIBLE;
    }

    public void setTvLeftVisibility(boolean isVisible) {
        tvLeft.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    public boolean getTvLeftVisibility() {
        return tvLeft.getVisibility() == View.VISIBLE;
    }

    public void setTvRightVisibility(boolean isVisible) {
        tvRight.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    public boolean getTvRightVisibility() {
        return tvRight.getVisibility() == View.VISIBLE;
    }

    public void setIvTitleVisibility(boolean isVisible) {
        ivTitle.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    public boolean getIvTitleVisibility() {
        return ivTitle.getVisibility() == View.VISIBLE;
    }

    public void setIvLeftVisibility(boolean isVisible) {
        ivLeft.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    public boolean getIvLeftVisibility() {
        return ivLeft.getVisibility() == View.VISIBLE;
    }

    public void setIvRightVisibility(boolean isVisible) {
        ivRight.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    public boolean getIvRightVisibility() {
        return ivRight.getVisibility() == View.VISIBLE;
    }


    public void setTitle(String title) {
        activity.getSupportActionBar().setDisplayShowTitleEnabled(false);
        activity.getSupportActionBar().setTitle("");
        tvTitle.setText("" + title);
    }

    public String getTitle() {
        return tvTitle.getText().toString();
    }

    public void setTvTitle(String title) {
        tvTitle.setText("" + title);
    }

    public String getTvTitle() {
        return tvTitle.getText().toString();
    }

    public TextView getTvTitleCenter() {
        return tvTitle;
    }

    public void setTvLeftTitle(String title) {
        tvLeft.setText("" + title);
    }

    public String getTvLeftTitle() {
        return tvLeft.getText().toString();
    }

    public void setTvRightTitle(String title) {
        tvRight.setText("" + title);
    }

    public String getTvRightTitle() {
        return tvRight.getText().toString();
    }

    public void setIvTitleResource(int resourceId) {
        ivTitle.setImageResource(resourceId);
    }

    public void setIvLeftResource(int resourceId) {
        ivLeft.setImageResource(resourceId);
    }

    public void setIvRightResource(int resourceId) {
        ivRight.setImageResource(resourceId);
    }

    public void setTvLeftClickListener(OnClickListener onClickListener) {
        tvLeft.setOnClickListener(onClickListener);
    }

    public void setTvRightClickListener(OnClickListener onClickListener) {
        tvRight.setOnClickListener(onClickListener);
    }

    public void setIvLeftClickListener(OnClickListener onClickListener) {
        ivLeft.setOnClickListener(onClickListener);
    }

    public void setIvRightClickListener(OnClickListener onClickListener) {
        ivRight.setOnClickListener(onClickListener);
    }

    public void setToolbarBackButtonClickListener(OnClickListener onClickListener) {
        toolbar.setNavigationOnClickListener(onClickListener);
    }

    public void setToolbarBackButtonGoToBack() {
        toolbar.setNavigationOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.onBackPressed();
            }
        });
    }
}
