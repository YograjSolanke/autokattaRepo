package autokatta.com.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import autokatta.com.networkreceiver.ConnectionDetector;

/**
 * Created by ak-001 on 18/3/17.
 */

public class Receiver extends BroadcastReceiver {

    public static final String NETWORK_AVAILABLE_ACTION = "com.autokatta.deepak.NetworkAvailable";
    public static final String IS_NETWORK_AVAILABLE = "isNetworkAvailable";
    ConnectionDetector mConnectionDetector;
    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            Intent networkStateIntent = new Intent(NETWORK_AVAILABLE_ACTION);
            networkStateIntent.putExtra(IS_NETWORK_AVAILABLE, mConnectionDetector.isConnectedToInternet(context));
            LocalBroadcastManager.getInstance(context).sendBroadcast(networkStateIntent);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
