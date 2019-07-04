package com.thinkitive.accessibilityfullfunctional.service;

import android.accessibilityservice.AccessibilityService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.thinkitive.accessibilityfullfunctional.R;
import com.thinkitive.accessibilityfullfunctional.Utils.AppExecutor;
import com.thinkitive.accessibilityfullfunctional.Utils.DataBaseClient;
import com.thinkitive.accessibilityfullfunctional.model.UserInformationData;

import java.util.List;

public class MyService extends AccessibilityService {
    private String user="";
    private String pass="";
    private AccessibilityEvent event1;
    private AccessibilityNodeInfo accessibilityNodeInfo;
    private String CHANNEL_ID = "1";
    private NotificationManager notificationManager;

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        event1 = event;
        accessibilityNodeInfo = event.getSource();

        Log.d("waste","onAccessibilityEvent: ");

        if (!(accessibilityNodeInfo.isEditable())){
            return;
        }

        final String packName = accessibilityNodeInfo.getPackageName().toString();
       // Log.d("waste","pac: "+packName);


        AppExecutor.getInstance().getDiskIo().execute(new Runnable() {
            @Override
            public void run() {

                final List<UserInformationData> userEntry = DataBaseClient.getInstance(getApplicationContext())
                        .getMainDataBase().userInformationDataDao().getAllUserAndPassword(packName);

                if (userEntry.size() > 0) {

                    AppExecutor.getInstance().getMainThread().execute(new Runnable() {
                        @Override
                        public void run() {

                             user = userEntry.get(0).getUser();
                          //  Log.d("waste","user: "+user);
                            pass = userEntry.get(0).getPassword();
                           // Log.d("waste","pass: "+pass);

                            accessibilityNodeInfo.getViewIdResourceName();
                           // Log.d("waste","view id : "+accessibilityNodeInfo.getViewIdResourceName()
                             //       +" type: "+accessibilityNodeInfo.getInputType());
                            if (accessibilityNodeInfo.isPassword()) {
                                pasteText(accessibilityNodeInfo, pass);
                            } else {
                                pasteText(accessibilityNodeInfo, user);
                            }
                        }
                    });
                }
            }

        });

    }

    public void pasteText(AccessibilityNodeInfo node, String text) {
        Bundle arguments = new Bundle();
        arguments.putString(AccessibilityNodeInfoCompat.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, text);
        node.performAction(AccessibilityNodeInfoCompat.ACTION_SET_TEXT, arguments);
    }

    @Override
    public void onInterrupt() {

    }



    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
       // Log.d("waste","connected");

        Notification.Builder builder = new Notification.Builder(this);
        builder.setSmallIcon(R.drawable.ic_launcher_foreground);
        builder.setContentTitle("Full Functional Accessibility Service");
        builder.setContentText("Started...");
        builder.setAutoCancel(true);
        builder.setOngoing(true);

        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            builder.setChannelId(CHANNEL_ID);

            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(1,builder.build());

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        notificationManager.cancel(1);
    }
}
