package com.example.prm392front.utils;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.prm392front.R;
import com.example.prm392front.activity.CartActivity;

public class NotificationHelper {

    private static final String CHANNEL_ID   = "cart_channel";
    private static final String CHANNEL_NAME = "Cart Notifications";
    private static final int    NOTIFICATION_ID = 1001;

    // Call this once when the app starts — creates the notification channel
    public static void createNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            channel.setDescription("Shows how many items are in your cart");

            NotificationManager manager = context.getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(channel);
            }
        }
    }

    // Call this whenever cart items change
    public static void showCartNotification(Context context, int itemCount) {
        // If cart is empty, cancel any existing notification
        if (itemCount == 0) {
            cancelCartNotification(context);
            return;
        }

        // Tapping the notification opens CartActivity
        Intent intent = new Intent(context, CartActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(
                context,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        String message = itemCount == 1
                ? "You have 1 item in your cart"
                : "You have " + itemCount + " items in your cart";

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.baseline_shopping_cart_24)  // your cart icon
                .setContentTitle("Your Cart")
                .setContentText(message)
                .setNumber(itemCount)          // ← this is what shows the badge number
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(false)          // keep notification until cart is empty
                .setOnlyAlertOnce(true);       // don't make sound every time count updates

        // Check permission for Android 13+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (context.checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS)
                    != android.content.pm.PackageManager.PERMISSION_GRANTED) {
                return; // permission not granted, skip
            }
        }

        NotificationManagerCompat.from(context).notify(NOTIFICATION_ID, builder.build());
    }

    public static void cancelCartNotification(Context context) {
        NotificationManagerCompat.from(context).cancel(NOTIFICATION_ID);
    }
}