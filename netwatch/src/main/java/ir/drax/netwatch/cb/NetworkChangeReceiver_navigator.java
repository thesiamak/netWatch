package ir.drax.netwatch.cb;

import android.view.View;

public interface NetworkChangeReceiver_navigator {
    void onConnected(int source);
    View onDisconnected();
}
