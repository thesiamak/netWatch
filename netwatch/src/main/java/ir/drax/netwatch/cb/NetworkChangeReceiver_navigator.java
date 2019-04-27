package ir.drax.netwatch.cb;

public interface NetworkChangeReceiver_navigator {
    void onConnected(int source);
    void onDisconnected();
}
