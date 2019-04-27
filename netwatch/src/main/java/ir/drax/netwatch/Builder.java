package ir.drax.netwatch;

import android.app.Activity;

import ir.drax.netwatch.cb.NetworkChangeReceiver_navigator;

public class Builder {
    private Activity activity;

    public Builder(Activity activity) {
        this.activity = activity;
    }

    public Builder setIcon(int icon){
        NetworkChangeReceiver.setNotificationIcon(icon);
        return this;
    }
    public void build(){
        NetworkChangeReceiver.checkState(activity);
    }

    public Builder setCallBack(NetworkChangeReceiver_navigator uiNavigator){
        NetworkChangeReceiver.setUiNavigator(uiNavigator);
        return this;
    }

    public void setMessage(String message){
        NetworkChangeReceiver.setMessage(message);
    }

    public Builder setNotificationAutoCancel(boolean autoCancel) {
        NetworkChangeReceiver.setAutoCancel(autoCancel);
        return this;
    }
    public Builder setNotificationEnabled(boolean enabled) {
        NetworkChangeReceiver.setNotificationEnabled(enabled);
        return this;
    }
}
