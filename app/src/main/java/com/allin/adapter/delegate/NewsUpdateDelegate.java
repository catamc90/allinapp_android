package com.allin.adapter.delegate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.allin.R;
import com.allin.adapter.NewsUpdateAdapter;
import com.allin.adapter.interfaces.OnNewsUpdateEventListener;
import com.allin.adapter.viewholder.NewsUpdateHolder;
import com.allin.response.NewsInfo;
import com.allin.util.FontUtils;
import com.allin.util.MyApplication;

/**
 * Created by harry on 11/30/17.
 */

public class NewsUpdateDelegate {

    private Context mContext;
    private FontUtils fontUtils;

    public NewsUpdateDelegate() {
    }

    public NewsUpdateHolder onCreateNewsUpdateHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        fontUtils=new FontUtils(mContext);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_news_update, parent, false);
        return new NewsUpdateHolder(view);
    }

    public void onBindNewsUpdateHolder(final NewsUpdateAdapter newsUpdateAdapter, final NewsInfo data, final NewsUpdateHolder holder, final int position, final OnNewsUpdateEventListener onNewsUpdateEventListener) {
        holder.bind(data);
        holder.setFonts(fontUtils);
        holder.llContainerNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (data.readTime == null) {
                    holder.tvNewsReadStatus.setText(MyApplication.getMyApplcation().getStringResource(R.string.news));
                    holder.tvNewsReadStatus.setBackgroundColor(MyApplication.getMyApplcation().getColorResource(R.color.light_gray_3));
                }
                if (onNewsUpdateEventListener != null)
                    onNewsUpdateEventListener.onNewsClick(position);
            }
        });
    }
}
