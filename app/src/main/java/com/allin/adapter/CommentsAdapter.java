package com.allin.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.allin.adapter.delegate.CommentDelegate;
import com.allin.adapter.interfaces.OnCommentEventListner;
import com.allin.adapter.viewholder.CommentHolder;

import java.util.ArrayList;

/**
 * Created by harry on 1/20/18.
 */

public class CommentsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<String> alData;
    private CommentDelegate commentDelegate;
    private OnCommentEventListner onCommentEventListner;

    public CommentsAdapter(Context mContext, ArrayList<String> alData, CommentDelegate commentDelegate, OnCommentEventListner onCommentEventListner) {
        this.mContext = mContext;
        this.alData = alData;
        this.commentDelegate = commentDelegate;
        this.onCommentEventListner = onCommentEventListner;
    }

    public void add(String data) {
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

    public void addData(ArrayList<String> data) {
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

    public void addAll(ArrayList<String> data) {
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
        return commentDelegate.onCreateCommentHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        String data = alData.get(position);
        commentDelegate.onBindCommentHolder(this, data, (CommentHolder) holder, position, onCommentEventListner);
    }

    @Override
    public int getItemCount() {
        return alData.size();
    }

    public static class CommentsAdapterBuilder {

        private Context mContext;
        private ArrayList<String> alData;
        private CommentDelegate commentDelegate;
        private OnCommentEventListner onCommentEventListner;

        public CommentsAdapterBuilder with(Context context) {
            this.mContext = context;
            return this;
        }

        public CommentsAdapterBuilder setData(ArrayList<String> data) {
            this.alData = data;
            return this;
        }

        public CommentsAdapterBuilder setDelegate(CommentDelegate delegate) {
            this.commentDelegate = delegate;
            return this;
        }

        public CommentsAdapterBuilder setOnCommentsEventListener(OnCommentEventListner onCommentsEventListener){
            this.onCommentEventListner=onCommentsEventListener;
            return this;

        }

        public CommentsAdapter create(){
            return new CommentsAdapter(mContext,alData,commentDelegate,onCommentEventListner);
        }
    }

}
