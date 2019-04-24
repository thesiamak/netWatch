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
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.io.IOException;

import static android.content.Context.NOTIFICATION_SERVICE;

public class NetworkChangeReceiver extends BroadcastReceiver {

    private static String TAG = NetworkChangeReceiver.class.getSimpleName();
    public static int TYPE_WIFI = 1;
    public static int TYPE_MOBILE = 2;
    public static int DISCONNECTED = 0;
    private static int NOTIFICATIONS_ID=987;
    private static String NOTIFICATIONS_CHANNEL_NAME = "NetworkChangeReceiver";
    private static int notificationIcon = R.drawable.ic_nosignal;
    private static NetworkChangeReceiver_navigator uiNavigator;
    private static String message ;

    public NetworkChangeReceiver() {
        super();
        Log.e( TAG, "Starting ...");
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        detectAndAct(context);
    }

    public static void detectAndAct(Context context){
        int status = getConnectivityStatus(context);

        Log.e( TAG, "" + status);

        if (status == DISCONNECTED) {
            if (uiNavigator == null){
                hideNotification(context);//do not care about net changes when app is closed
                return;
            }
            else uiNavigator.onDisconnected();

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
                    .setContentTitle(message==null?context.getString(R.string.netwatch_lost_connection):message )
                    .setAutoCancel(true)
                    .setOngoing(true);
                    //.setContentText(context.getString(R.string.lost_internet)  + " - " + context.getString(R.string.app_name));
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                mBuilder.setChannelId(NOTIFICATIONS_CHANNEL_NAME);
                createChannel(notificationManager);
            }

            notificationManager.notify(NOTIFICATIONS_ID, mBuilder.build());

        } else {
            hideNotification(context);
            if (uiNavigator!=null)
                uiNavigator.onConnected(status);
        }


    }
    public static void hideNotification(Context context){
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancel(NOTIFICATIONS_ID);
    }

    private static int getConnectivityStatus(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        if (null != activeNetwork) {

            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                return TYPE_WIFI;

            if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                return TYPE_MOBILE;
        }
        return DISCONNECTED;
    }

    /*public static int getNotificationIcon() {
        boolean whiteIcon = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP);
        return whiteIcon ? R.drawable.ic_nosignal: R.drawable.ic_nosignal;
    }
*/
    private static int getNotificationIcon() {
        return notificationIcon;
    }

    @TargetApi(Build.VERSION_CODES.O)
    private static void createChannel(NotificationManager manager) {
        int importance = NotificationManager.IMPORTANCE_HIGH;

        NotificationChannel mChannel = new NotificationChannel(NOTIFICATIONS_CHANNEL_NAME, TAG, importance);
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

    private boolean executeCommand(){
        System.out.println("executeCommand");
        Runtime runtime = Runtime.getRuntime();
        try
        {
            Process  mIpAddrProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int mExitValue = mIpAddrProcess.waitFor();
            System.out.println(" mExitValue "+mExitValue);
            if(mExitValue==0){
                return true;
            }else{
                return false;
            }
        }
        catch (InterruptedException ignore)
        {
            ignore.printStackTrace();
            System.out.println(" Exception:"+ignore);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            System.out.println(" Exception:"+e);
        }
        return false;
    }
}