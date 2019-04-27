package ir.drax.netwatch.cb;

import android.content.Context;

public interface Ping_navigator {
    public void timeout(Context context);
    public void replied(Context context);
    public void ended(Context context);
}
