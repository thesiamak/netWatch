package ir.drax.netwatch;

import android.app.Activity;

public class NetWatch {
    public static Builder builder(Activity activity){
        /*NetworkChangeReceiver networkChangeReceiver = new NetworkChangeReceiver();
        activity.registerReceiver(networkChangeReceiver,null);*/
        return new Builder(activity);
    }
}
