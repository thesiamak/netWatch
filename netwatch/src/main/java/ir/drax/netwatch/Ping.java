package ir.drax.netwatch;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

import ir.drax.netwatch.cb.Ping_navigator;

class Ping extends AsyncTask<Context, Void, Context> {
    private Ping_navigator cb;
    private int mExitValue = 0;
    private Thread activeThread;

    Ping() {
    }

    @Override
    protected Context doInBackground(Context... contexts) {
        try
        {
            activeThread=Thread.currentThread();
            Thread.sleep(NetworkChangeReceiver.getDelay());
            pingProcess(contexts[0]);

        } catch (InterruptedException ignore) {
            pingProcess(contexts[0]);
            if (NetworkChangeReceiver.isLogsEnabled())
                System.out.println("Ping Exception:"+ignore.getMessage());
        }

        return contexts[0];
    }
    @Override
    protected void onPostExecute(Context context) {
        if(mExitValue==0){
            cb.replied(context);

        }else{
            cb.timeout(context);

        }
        cb.ended(context);
        super.onPostExecute(context);
    }

    private void pingProcess(Context context){
        try {
            Runtime runtime = Runtime.getRuntime();
            Process mIpAddrProcess = runtime.exec("/system/bin/ping -c 1 " + context.getString(R.string.netwatch_target_ping_server_ip_add));
            mExitValue = mIpAddrProcess.waitFor();
            if (NetworkChangeReceiver.isLogsEnabled())
                System.out.println(" Ping mExitValue " + mExitValue);


        } catch (InterruptedException ignore) {
            ignore.printStackTrace();
            if (NetworkChangeReceiver.isLogsEnabled())
                System.out.println("Ping Exception:"+ignore);

        }catch (IOException e) {
            e.printStackTrace();
            if (NetworkChangeReceiver.isLogsEnabled())
                System.out.println("Ping Exception:"+e);
        }
    }

    public Ping(Ping_navigator cb) {
        this.cb = cb;
    }

    Ping setCb(Ping_navigator cb) {
        this.cb = cb;
        return this;
    }

    void resume(){
        if (activeThread==null){
            activeThread=Thread.currentThread();
            System.out.println("Ping 22Exception:********");

        }
        if (activeThread.isAlive()){
            System.out.println("Ping 33Exception:********");
            activeThread.interrupt();

        }

    }

}