package autokatta.com.networkreceiver;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import autokatta.com.broadcastreceiver.Receiver;

import static android.content.Context.CONNECTIVITY_SERVICE;

/**
 * Created by ak-001 on 18/3/17.
 */

public class ConnectionDetector {

    private Activity _context;

    public ConnectionDetector(Activity _context) {
        this._context = _context;
    }

    public boolean isConnectedToInternet() {
        try {
            if (_context != null) {
                ConnectivityManager connectivityManager = (ConnectivityManager) _context.getSystemService(CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                return networkInfo != null && networkInfo.isConnected();
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(Receiver.class.getName(), e.getMessage());
            return false;
        }
    }

    public boolean isConnectedToInternet(Context _context) {
        try {
            if (_context != null) {
                ConnectivityManager connectivityManager = (ConnectivityManager) _context.getSystemService(CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                return networkInfo != null && networkInfo.isConnected();
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(Receiver.class.getName(), e.getMessage());
            return false;
        }
    }
}
