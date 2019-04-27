package ir.drax.networkChangeNotifierSample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import ir.drax.netwatch.NetWatch;
import ir.drax.netwatch.cb.NetworkChangeReceiver_navigator;

public class MainActivity extends AppCompatActivity implements NetworkChangeReceiver_navigator {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NetWatch.builder(this)
                .setIcon(R.drawable.ic_signal_wifi_off)
                .setCallBack(this)
                .build();
    }


    @Override
    public void onConnected(int source) {
        Toast.makeText(MainActivity.this, "connected", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDisconnected() {
        Toast.makeText(MainActivity.this, "disconnected", Toast.LENGTH_SHORT).show();
    }
}

