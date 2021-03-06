package autokatta.com.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import autokatta.com.R;
import autokatta.com.broadcastreceiver.Receiver;
import autokatta.com.fragment.MyBroadcastGroupsFragment;

import static autokatta.com.broadcastreceiver.Receiver.IS_NETWORK_AVAILABLE;

public class MyBroadcastGroupsActivity extends AppCompatActivity {
    boolean isNetworkAvailable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_broadcast_groups);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("My BroadCast Groups");
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            //getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        }

        IntentFilter intentFilter = new IntentFilter(Receiver.NETWORK_AVAILABLE_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                isNetworkAvailable = intent.getBooleanExtra(IS_NETWORK_AVAILABLE, false);
                String networkStatus = isNetworkAvailable ? "Connected" : "Disconnected";
                Snackbar.make(findViewById(R.id.activity_my_broadcast_groups), "Network Status: " + networkStatus, Snackbar.LENGTH_LONG).show();
            }
        }, intentFilter);

        MyBroadcastGroupsFragment broadcastGroupsFragment = new MyBroadcastGroupsFragment();
        /*FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.broadcast_groups_container, new MyBroadcastGroupsFragment()).commit();*/
        Bundle b = new Bundle();
        if (getIntent().getExtras() != null) {
            b.putString("title", getIntent().getExtras().getString("title"));
            b.putString("price", getIntent().getExtras().getString("price"));
            b.putString("category", getIntent().getExtras().getString("category"));
            b.putString("brand", getIntent().getExtras().getString("brand"));
            b.putString("model", getIntent().getExtras().getString("model"));
            b.putString("image", getIntent().getExtras().getString("image"));
            b.putString("rto_city", getIntent().getExtras().getString("rto_city"));
            b.putString("manufacture_year", getIntent().getExtras().getString("manufacture_year"));
            b.putString("kms", getIntent().getExtras().getString("kms"));

            broadcastGroupsFragment.setArguments(b);
        }
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.broadcast_groups_container, broadcastGroupsFragment, "myBroadcastGroupsFragment")
                    .addToBackStack("myBroadcastGroupsFragment")
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
        //super.onBackPressed();
        int fragments = getSupportFragmentManager().getBackStackEntryCount();
        if (fragments == 1) {
            finish();
            overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
        } else {
            if (getFragmentManager().getBackStackEntryCount() > 1) {
                getFragmentManager().popBackStack();
            } else {
                super.onBackPressed();
            }
        }
    }
}
