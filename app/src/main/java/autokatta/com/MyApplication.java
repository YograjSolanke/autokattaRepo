package autokatta.com;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.preference.PreferenceManager;
import android.support.multidex.MultiDex;

import java.util.Locale;

import autokatta.com.broadcastreceiver.ConnectionReceiver;

/**
 * Created by ak-001 on 6/6/17.
 */

public class MyApplication extends android.support.multidex.MultiDexApplication {
    private static MyApplication mInstance;
    private Locale locale = null;

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (locale != null) {
            newConfig.locale = locale;
            Locale.setDefault(locale);
            getBaseContext().getResources().updateConfiguration(newConfig, getBaseContext().getResources().getDisplayMetrics());
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;
        MultiDex.install(this); //for device installation error

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);

        Configuration config = getBaseContext().getResources().getConfiguration();

        String lang = settings.getString(getString(R.string.firstRun), "");
        if (!"".equals(lang) && !config.locale.getLanguage().equals(lang)) {
            locale = new Locale(lang);
            Locale.setDefault(locale);
            config.locale = locale;
            getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        }
    }

    public static synchronized MyApplication getInstance() {
        return mInstance;
    }

    public void setConnectivityListener(ConnectionReceiver.ConnectivityReceiverListener listener) {
        ConnectionReceiver.connectivityReceiverListener = listener;
    }
}
