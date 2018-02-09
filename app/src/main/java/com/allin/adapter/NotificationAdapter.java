package com.allin.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;

import com.allin.adapter.delegate.NotificationDelegate;
import com.allin.adapter.interfaces.OnNotificationEventListener;
import com.allin.adapter.viewholder.NotificationHolder;
import com.allin.response.NotificationInfo;

import java.util.ArrayList;

/**
 * Created by harry on 11/30/17.
 */

public class NotificationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<NotificationInfo> alData;
    private NotificationDelegate notificationDelegate;
    private OnNotificationEventListener onNotificationEventListener;

    public NotificationAdapter(Context mContext, ArrayList<NotificationInfo> alData, NotificationDelegate notificationDelegate, OnNotificationEventListener onNotificationEventListener) {
        this.mContext = mContext;
        this.alData = alData;
        this.notificationDelegate = notificationDelegate;
        this.onNotificationEventListener = onNotificationEventListener;
    }

    public void add(NotificationInfo data) {
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

    public void addData(ArrayList<NotificationInfo> data) {
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

    public void addAll(ArrayList<NotificationInfo> data) {
        try {
            if (alData == null)
                alData = new ArrayList<>();
            if (data != null && data.size() > -1) {
                alData.addAll(data);
                notifyDataSetChanged();
//                notifyItemChanged(alData.size() - data.size(), data.size());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return notificationDelegate.onCreateNotificationHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        NotificationInfo data = alData.get(position);
        notificationDelegate.onBindNotificationHolder(this, data, (NotificationHolder) holder, position, onNotificationEventListener);
    }

    @Override
    public int getItemCount() {
        Log.e("SIZE",""+alData.size());
        return alData.size();
    }

    public static class NotificationAdapterBuilder {

        private Context mContext;
        private ArrayList<NotificationInfo> alData;
        private NotificationDelegate notificationDelegate;
        private OnNotificationEventListener onNotificationEventListener;

        public NotificationAdapterBuilder with(@NonNull Context context) {
            this.mContext = context;
            return this;
        }

        public NotificationAdapterBuilder setData(@NonNull ArrayList<NotificationInfo> data) {
            this.alData = data;
            return this;
        }

        public NotificationAdapterBuilder setNotificationDelegate(NotificationDelegate notificationDelegate) {
            this.notificationDelegate = notificationDelegate;
            return this;
        }

        public NotificationAdapterBuilder setNotificationEventListener(OnNotificationEventListener notificationEventListener) {
            this.onNotificationEventListener = notificationEventListener;
            return this;
        }

        public NotificationAdapter create() {
            return new NotificationAdapter(mContext, alData, notificationDelegate, onNotificationEventListener);
        }

    }
}
