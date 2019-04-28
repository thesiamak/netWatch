package ir.drax.netwatch;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.NotificationCompat;

import ir.drax.netwatch.cb.NetworkChangeReceiver_navigator;

public class Builder {
    private static Builder instance;
    private NetworkChangeReceiver receiver;
    private IntentFilter filter = new IntentFilter();
    private Context context;

    static Builder getInstance(Context context){
        if (instance==null)
            instance = new Builder(context);

        return instance;
    }

    private Builder(Context context) {
        this.context = context;
        receiver = new NetworkChangeReceiver();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
    }

    public Builder setIcon(int icon){
        NetworkChangeReceiver.setNotificationIcon(icon);
        return this;
    }
    public void build(){
        context.startService(new Intent(context, OnKillApp.class));
        context.registerReceiver(receiver , filter);

        NetworkChangeReceiver.checkState(context);
    }

    public Builder setCallBack(NetworkChangeReceiver_navigator uiNavigator){
        NetworkChangeReceiver.setUiNavigator(uiNavigator);
        return this;
    }

    public void setMessage(String message){
        NetworkChangeReceiver.setMessage(message);
    }

    public Builder setNotificationCancelable(boolean autoCancel) {
        NetworkChangeReceiver.setCancelable(autoCancel);
        return this;
    }
    public Builder setNotificationEnabled(boolean enabled) {
        NetworkChangeReceiver.setNotificationEnabled(enabled);
        return this;
    }

    public Builder setNotificationBuilder(NotificationCompat.Builder mBuilder) {
        NetworkChangeReceiver.setNotificationBuilder(mBuilder);
        return this;
    }

    void unregister() {
        receiver.unregister(context);
    }


}
