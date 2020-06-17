package com.digitalcashbag.utilities.notification;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;

import com.digitalcashbag.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import kkm.com.core.utils.LoggerUtil;

public class NotificationUtils {

    private static String TAG = NotificationUtils.class.getSimpleName();

    private Context mContext;

    public NotificationUtils(Context mContext) {
        this.mContext = mContext;
    }

    /**
     * Method checks if the app is in background or not
     */
    static boolean isAppIsInBackground(Context context) {
        boolean isInBackground = true;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
                if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    for (String activeProcess : processInfo.pkgList) {
                        if (activeProcess.equals(context.getPackageName())) {
                            isInBackground = false;
                        }
                    }
                }
            }
        } else {
            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
            ComponentName componentInfo = taskInfo.get(0).topActivity;
            if (componentInfo.getPackageName().equals(context.getPackageName())) {
                isInBackground = false;
            }
        }
        Log.e("App in background: ", "" + isInBackground);
        return isInBackground;
    }

    // Clears notification tray messages
    public static void clearNotifications(Context context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }

    public void showNotificationMessage(String title, String message, String timeStamp, Intent intent) {
        showNotificationMessage(title, message, timeStamp, intent, null);
    }

    public void showNotificationMessage(final String title, final String message, final String timeStamp, Intent intent, String imageUrl) {
        // Check for empty push message
        if (TextUtils.isEmpty(message))
            return;


        // notification icon
        final int icon = R.drawable.bag;

        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        final PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        mContext,
                        0,
                        intent,
                        PendingIntent.FLAG_CANCEL_CURRENT
                );

        final NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                mContext, "my_channel_id_02");
        mBuilder.setColor(ContextCompat.getColor(mContext, R.color.colorAccent));
        final Uri alarmSound = null;
        LoggerUtil.logItem(imageUrl);

        if (!TextUtils.isEmpty(imageUrl)) {
            if (imageUrl.length() > 4 && Patterns.WEB_URL.matcher(imageUrl).matches()) {
                Bitmap bitmap = getBitmapFromURL(imageUrl);
                if (bitmap != null) {
                    LoggerUtil.logItem("Bitmap Create");
                    showBigNotification(bitmap, mBuilder, icon, title, message, timeStamp, resultPendingIntent, alarmSound);
                } else {
                    LoggerUtil.logItem("Bitmap NULL");
                    showSmallNotification(mBuilder, icon, title, message, timeStamp, resultPendingIntent, alarmSound);
                }
            } else {
                LoggerUtil.logItem("URL WRONG");
            }
        } else {
            LoggerUtil.logItem("URL NOT FOUND");
            showSmallNotification(mBuilder, icon, title, message, timeStamp, resultPendingIntent, alarmSound);
        }
    }

    private void showSmallNotification(NotificationCompat.Builder mBuilder, int icon, String title, String message, String timeStamp, PendingIntent resultPendingIntent, Uri alarmSound) {

        NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String NOTIFICATION_CHANNEL_ID = "my_channel_id_02";
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "Cashbag", NotificationManager.IMPORTANCE_HIGH);
            // Configure the notification channel.
            notificationChannel.setDescription("Cashbag");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.BLUE);
            notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
            notificationChannel.enableVibration(true);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        inboxStyle.addLine(message);

        Notification notification;
        notification = mBuilder.setSmallIcon(icon).setTicker(title)
                .setAutoCancel(true)
                .setColor(ContextCompat.getColor(mContext, R.color.colorPrimaryDark))
                .setWhen(System.currentTimeMillis())
                .setDefaults(Notification.DEFAULT_ALL)
                .setTicker(title + message)
                .setContentTitle(title)
                .setContentIntent(resultPendingIntent)
//                .setSound(alarmSound)
                .setContentText(message)
                .setStyle(inboxStyle)
//                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setContentInfo("")
                .build();


        int id = (int) System.currentTimeMillis();
        notificationManager.notify(id, notification);
    }

    // Playing notification sound
//    public void playNotificationSound() {
//        try {
//            Uri alarmSound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
//                    + "://" + mContext.getPackageName() + "/raw/notification.mp3");
//            Ringtone r = RingtoneManager.getRingtone(mContext, alarmSound);
//            r.play();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    private void showBigNotification(Bitmap bitmap, NotificationCompat.Builder mBuilder, int icon, String title, String message, String timeStamp, PendingIntent resultPendingIntent, Uri alarmSound) {
        try {
            NotificationCompat.BigPictureStyle bigPictureStyle = new NotificationCompat.BigPictureStyle();
            bigPictureStyle.setBigContentTitle(title);
            bigPictureStyle.setSummaryText(Html.fromHtml(message).toString());
            bigPictureStyle.bigPicture(bitmap);

            NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                String NOTIFICATION_CHANNEL_ID = "my_channel_id_01";
                NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "Cashbag", NotificationManager.IMPORTANCE_HIGH);
                // Configure the notification channel.
                notificationChannel.setDescription("Cashbag");
                notificationChannel.enableLights(true);
                notificationChannel.setLightColor(Color.RED);
                notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
                notificationChannel.enableVibration(true);
                notificationManager.createNotificationChannel(notificationChannel);
            }

            Notification notification;
            notification = mBuilder.setSmallIcon(icon).setTicker(title)
                    .setAutoCancel(true)
                    .setColor(ContextCompat.getColor(mContext, R.color.colorPrimaryDark))
                    .setWhen(System.currentTimeMillis())
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setTicker(title + message)
                    .setContentTitle(title)
                    .setContentIntent(resultPendingIntent)
                    .setStyle(bigPictureStyle)
                    .setContentText(message)
                    .setContentInfo("")
                    .build();

            int id = (int) System.currentTimeMillis();
            notificationManager.notify(id, notification);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Downloading push notification image before displaying it in
     * the notification tray
     */
    Bitmap getBitmapFromURL(String strURL) {
        try {
            URL url = new URL(strURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            return BitmapFactory.decodeStream(input);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


}