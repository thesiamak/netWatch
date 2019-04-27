package ir.drax.networkChangeNotifierSample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import ir.drax.netwatch.NetWatch;
import ir.drax.netwatch.cb.NetworkChangeReceiver_navigator;

public class MainActivity extends AppCompatActivity implements NetworkChangeReceiver_navigator {

    TextView statusTv ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        statusTv = findViewById(R.id.liveStatus);

        NetWatch.builder(this)
                .setIcon(R.drawable.ic_signal_wifi_off)
                .setCallBack(this)
                .build();
    }


    @Override
    public void onConnected(int source) {
        statusTv.setText(R.string.connected);
        statusTv.setTextColor(getResources().getColor(android.R.color.black));
    }

    @Override
    public void onDisconnected() {
        statusTv.setText(R.string.disconnected);
        statusTv.setTextColor(getResources().getColor(android.R.color.holo_red_light));
    }
}

