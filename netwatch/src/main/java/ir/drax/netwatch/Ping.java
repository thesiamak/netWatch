package ir.drax.netwatch;

import android.content.Context;

import java.io.IOException;

import ir.drax.netwatch.cb.Ping_navigator;

class Ping implements Runnable {
    private Ping_navigator cb;
    private Context context;


    Ping() { }

    public Ping(Ping_navigator cb) {
        this.cb = cb;
    }

    Ping setCb(Ping_navigator cb) {
        this.cb = cb;
        return this;
    }

    Ping setContext(Context context) {
        this.context = context;
        return this;
    }

    @Override
    public void run() {
        System.out.println("ping");
        Runtime runtime = Runtime.getRuntime();
        try
        {
            Process  mIpAddrProcess = runtime.exec("/system/bin/ping -c 1 "+ context.getString(R.string.netwatch_target_ping_server_ip_add));
            int mExitValue = mIpAddrProcess.waitFor();
            System.out.println(" mExitValue "+mExitValue);
            if(mExitValue==0){
                cb.replied(context);

            }else{
                cb.timeout(context);

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
        cb.ended(context);
    }
}