package ir.drax.netwatch;

import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.io.IOException;

import ir.drax.netwatch.cb.NetworkChangeReceiver_navigator;
import ir.drax.netwatch.cb.Ping_navigator;


public class NetworkChangeReceiver extends BroadcastReceiver {

    private static String TAG = NetworkChangeReceiver.class.getSimpleName();
    private static int CONNECTED_WIFI = 1;
    private static int CONNECTED_MOBILE = 2;
    private static int DISCONNECTED = 0;
    private static int CONNECTED = 3;
    private static int LAST_STATE = -1;
    private static int NOTIFICATIONS_ID=987;
    private static int GENERAL_PING_INTERVAL = 20;
    private static int notificationIcon = R.drawable.ic_nosignal;
    private static NetworkChangeReceiver_navigator uiNavigator;
    private static String message ;
    private static int repeat = 1 ;
    private static int delay = 0 ;
    private static Handler pingHandler = new Handler();
    private static Ping ping = new Ping();
    private static boolean autoCancel = true, notificationEnabled = true;

    public NetworkChangeReceiver() {
        super();
        Log.e( TAG, "Starting ...");
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e( TAG, "onReceive ..." + getConnectivityStatus(context));
        if (LAST_STATE == getConnectivityStatus(context))return;


        checkState(context,0 , 4);
    }

    private static void detectAndAct(Context context, int status){
        if (LAST_STATE == status)return;

        Log.e( TAG, "" + status);

        if (status == DISCONNECTED) {
            if (uiNavigator == null){
                hideNotification(context);//do not care about net changes when app is closed
                return;
            }
            else uiNavigator.onDisconnected();

            if (notificationEnabled) {

                Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), getNotificationIcon());
                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                        /*.setContentIntent(PendingIntent.getActivity(
                                context,
                                0,
                                notifyIntent,
                                PendingIntent.FLAG_UPDATE_CURRENT
                        ))*/
                        .setSmallIcon(getNotificationIcon())
                        .setLargeIcon(bitmap)
                        .setColor(Color.parseColor("#ffffff"))
                        .setContentTitle(message == null ? context.getString(R.string.netwatch_lost_connection) : message)
                        .setAutoCancel(autoCancel)
                        .setOngoing(true);
                //.setContentText(context.getString(R.string.lost_internet)  + " - " + context.getString(R.string.app_name));
                NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    mBuilder.setChannelId(TAG);
                    createChannel(notificationManager);
                }

                notificationManager.notify(NOTIFICATIONS_ID, mBuilder.build());
            }
        } else {
            hideNotification(context);
            if (uiNavigator!=null)
                uiNavigator.onConnected(status);
        }

        LAST_STATE = status;
    }
    public static void hideNotification(Context context){
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(NOTIFICATIONS_ID);
    }

    private static int getConnectivityStatus(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        if (null != activeNetwork) {

            return CONNECTED;
        }
        return DISCONNECTED;
    }

    private static int getConnectionType(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        if (null != activeNetwork) {

            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                return CONNECTED_WIFI;

            if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                return CONNECTED_MOBILE;
        }
        return DISCONNECTED;
    }

    private static int getNotificationIcon() {
        return notificationIcon;
    }

    @TargetApi(Build.VERSION_CODES.O)
    private static void createChannel(NotificationManager manager) {
        int importance = NotificationManager.IMPORTANCE_HIGH;

        NotificationChannel mChannel = new NotificationChannel(TAG, TAG, importance);
        mChannel.setDescription("");
        mChannel.enableLights(true);
        mChannel.setLightColor(Color.BLUE);
        manager.createNotificationChannel(mChannel);
    }

    public static void setNotificationIcon(int notificationIcon) {
        NetworkChangeReceiver.notificationIcon = notificationIcon;
    }

    public static void setUiNavigator(NetworkChangeReceiver_navigator uiNavigator) {
        NetworkChangeReceiver.uiNavigator = uiNavigator;
    }

    public static void setMessage(String message) {
        NetworkChangeReceiver.message = message;
    }

    public static void checkState(Context context){
        checkState(context,delay,repeat);
    }
    public static  void checkState(Context context,int delay,int repeat){
        if (repeat==0){
            NetworkChangeReceiver.repeat = 1;
            NetworkChangeReceiver.delay = GENERAL_PING_INTERVAL;


        }else {
            NetworkChangeReceiver.repeat = repeat;
            NetworkChangeReceiver.delay = delay;
        }
        pingHandler.removeCallbacks(ping);
        pingHandler.postDelayed(ping.setContext(context).setCb(new Ping_navigator() {
            @Override
            public void timeout(Context context) {
                if (NetworkChangeReceiver.repeat == 1) {
                    detectAndAct(context, NetworkChangeReceiver.DISCONNECTED);
                    NetworkChangeReceiver.delay = GENERAL_PING_INTERVAL ;
                    NetworkChangeReceiver.repeat = 1 ;
                }
            }

            @Override
            public void replied(Context context) {
                detectAndAct(context ,NetworkChangeReceiver.CONNECTED);
                NetworkChangeReceiver.delay = GENERAL_PING_INTERVAL ;
                NetworkChangeReceiver.repeat = 1 ;
            }

            @Override
            public void ended(Context context) {
                NetworkChangeReceiver.repeat = NetworkChangeReceiver.repeat - 1;
                checkState(context,NetworkChangeReceiver.delay ,NetworkChangeReceiver.repeat );
            }
        }),1000 * NetworkChangeReceiver.delay);
    }

    public static void setAutoCancel(boolean autoCancel) {
        NetworkChangeReceiver.autoCancel = autoCancel;
    }

    public static void setNotificationEnabled(boolean notificationEnabled) {
        NetworkChangeReceiver.notificationEnabled = notificationEnabled;
    }
}