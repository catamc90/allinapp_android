package com.allin.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.allin.R;
import com.allin.response.NotificationInfo;
import com.allin.util.FontUtils;
import com.allin.util.MyApplication;

/**
 * Created by harry on 11/30/17.
 */

public class NotificationHolder extends RecyclerView.ViewHolder {

    public LinearLayout llContainerNotification;
    public TextView tvNotificationTitle;
    public TextView tvNotification;
    public ImageView ivNotification;

    public NotificationHolder(View itemView) {
        super(itemView);
        llContainerNotification = itemView.findViewById(R.id.ll_container_notification);
        tvNotificationTitle = itemView.findViewById(R.id.tv_notification_title);
        tvNotification = itemView.findViewById(R.id.tv_notification);
        ivNotification = itemView.findViewById(R.id.iv_notification);
    }

    public void bind(NotificationInfo data) {
        tvNotificationTitle.setText("" + data.subject);
        tvNotification.setText("" + data.body);
        if (data.readTime == null) {
            ivNotification.setImageResource(R.drawable.ic_notification_green);
            tvNotificationTitle.setTextColor(MyApplication.getMyApplcation().getResources().getColor(R.color.green));
//            tvNotification.setTextColor(MyApplication.getMyApplcation().getResources().getColor(R.color.green));
        } else {
            ivNotification.setImageResource(R.drawable.ic_notification_gray);
            tvNotificationTitle.setTextColor(MyApplication.getMyApplcation().getResources().getColor(R.color.light_gray_4));
//            tvNotification.setTextColor(MyApplication.getMyApplcation().getResources().getColor(R.color.black));
        }
    }

    public void setFonts(FontUtils fonts) {
        tvNotificationTitle.setTypeface(fonts.getMontserratRegular());
        tvNotification.setTypeface(fonts.getMontserratRegular());
    }

}
