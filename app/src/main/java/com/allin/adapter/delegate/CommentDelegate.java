package com.allin.adapter.delegate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.allin.R;
import com.allin.adapter.CommentsAdapter;
import com.allin.adapter.interfaces.OnCommentEventListner;
import com.allin.adapter.viewholder.CommentHolder;
import com.allin.util.FontUtils;

/**
 * Created by harry on 1/20/18.
 */

public class CommentDelegate {

    private Context mContext;
    private FontUtils fontUtils;

    public CommentDelegate() {
    }

    public CommentHolder onCreateCommentHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        fontUtils=new FontUtils(mContext);
        final View view = LayoutInflater.from(mContext).inflate(R.layout.list_item_comment, parent, false);
        return new CommentHolder(view);
    }

    public void onBindCommentHolder(final CommentsAdapter commentsAdapter, final String data, final CommentHolder holder, final int position, final OnCommentEventListner onCommentEventListner) {
        holder.bind(data);
        holder.setFonts(fontUtils);
    }
}
