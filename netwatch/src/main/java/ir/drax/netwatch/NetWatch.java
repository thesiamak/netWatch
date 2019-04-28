package ir.drax.netwatch;

import android.app.Activity;
import android.content.Context;

public class NetWatch {
    public static Builder builder(Activity activity){
        return Builder.getInstance(activity);
    }

    public static void unregister(Context context){
        Builder.getInstance(context).unregister();
    }

}
