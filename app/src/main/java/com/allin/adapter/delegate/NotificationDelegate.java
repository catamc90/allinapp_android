package com.allin.adapter.delegate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.allin.R;
import com.allin.adapter.NotificationAdapter;
import com.allin.adapter.interfaces.OnNotificationEventListener;
import com.allin.adapter.viewholder.NotificationHolder;
import com.allin.response.NotificationInfo;
import com.allin.util.FontUtils;
import com.allin.util.MyApplication;

/**
 * Created by harry on 11/30/17.
 */

public class NotificationDelegate {

    private Context mContext;
    private FontUtils fontUtils;

    public NotificationDelegate() {
    }

    public NotificationHolder onCreateNotificationHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        fontUtils=new FontUtils(mContext);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_notification, parent, false);
        return new NotificationHolder(view);
    }

    public void onBindNotificationHolder(final NotificationAdapter notificationAdapter, final NotificationInfo data, final NotificationHolder holder, final int position, final OnNotificationEventListener onNotificationEventListener) {
        holder.bind(data);
        holder.setFonts(fontUtils);
        holder.llContainerNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (data.readTime == null) {
                    holder.ivNotification.setImageResource(R.drawable.ic_notification_gray);
                    holder.tvNotificationTitle.setTextColor(MyApplication.getMyApplcation().getResources().getColor(R.color.light_gray_8));
//                    holder.tvNotification.setTextColor(MyApplication.getMyApplcation().getResources().getColor(R.color.black));
                    if (onNotificationEventListener != null)
                        onNotificationEventListener.onNotificationClick(position);
                }
            }
        });
    }
}
