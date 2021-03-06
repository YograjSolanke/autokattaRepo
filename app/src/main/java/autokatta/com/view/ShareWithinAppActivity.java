package autokatta.com.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import autokatta.com.R;
import autokatta.com.broadcastreceiver.Receiver;
import autokatta.com.share.ShareWithinAppTabFragment;

import static autokatta.com.broadcastreceiver.Receiver.IS_NETWORK_AVAILABLE;

public class ShareWithinAppActivity extends AppCompatActivity {

    boolean isNetworkAvailable;
    CoordinatorLayout mShareApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_within_app);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Share Within App");
        mShareApp = (CoordinatorLayout) findViewById(R.id.shareApp);
        IntentFilter intentFilter = new IntentFilter(Receiver.NETWORK_AVAILABLE_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                isNetworkAvailable = intent.getBooleanExtra(IS_NETWORK_AVAILABLE, false);
                String networkStatus = isNetworkAvailable ? "Connected" : "Disconnected";
                Snackbar.make(findViewById(R.id.shareApp), "Network Status: " + networkStatus, Snackbar.LENGTH_LONG).show();
            }
        }, intentFilter);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.shareInApp_container, new ShareWithinAppTabFragment(), "shareWithinAppTabFragment")
                .commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        int fragments = getSupportFragmentManager().getBackStackEntryCount();
        if (fragments == 1) {
            finish();
        } else {
            if (getFragmentManager().getBackStackEntryCount() > 1) {
                getFragmentManager().popBackStack();
            } else {
                super.onBackPressed();
                overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
            }
        }
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            ActivityOptions options = ActivityOptions.makeCustomAnimation(this, R.anim.pull_in_left, R.anim.push_out_right);
            startActivity(new Intent(getApplicationContext(), AutokattaMainActivity.class), options.toBundle());
            finish();
        } else {
            finish();
            startActivity(new Intent(getApplicationContext(), AutokattaMainActivity.class));
        }*/
    }
}
