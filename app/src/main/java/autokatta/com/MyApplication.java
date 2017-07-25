package autokatta.com;

import android.support.multidex.MultiDex;

import autokatta.com.broadcastreceiver.ConnectionReceiver;

/**
 * Created by ak-001 on 6/6/17.
 */

public class MyApplication extends android.support.multidex.MultiDexApplication {
    private static MyApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;
        MultiDex.install(this); //for device installation error
    }

    public static synchronized MyApplication getInstance() {
        return mInstance;
    }

    public void setConnectivityListener(ConnectionReceiver.ConnectivityReceiverListener listener) {
        ConnectionReceiver.connectivityReceiverListener = listener;
    }
}
