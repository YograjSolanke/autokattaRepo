package autokatta.com.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import autokatta.com.R;
import autokatta.com.broadcastreceiver.Receiver;
import autokatta.com.initial_fragment.CreateGroupFragment;
import autokatta.com.initial_fragment.GroupMyJoined;

import static autokatta.com.broadcastreceiver.Receiver.IS_NETWORK_AVAILABLE;

public class GroupTabs extends AppCompatActivity {

    boolean isNetworkAvailable;
    String networkStatus;
    CoordinatorLayout mLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_tabs);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle("My Groups");
        mLayout = (CoordinatorLayout) findViewById(R.id.activity_group_tabs);
        IntentFilter intentFilter = new IntentFilter(Receiver.NETWORK_AVAILABLE_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                isNetworkAvailable = intent.getBooleanExtra(IS_NETWORK_AVAILABLE, false);
                networkStatus = isNetworkAvailable ? "Connected" : "Disconnected";
                Snackbar.make(findViewById(R.id.activity_group_tabs), "Network Status: " + networkStatus, Snackbar.LENGTH_LONG).show();
            }
        }, intentFilter);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        CreateGroupFragment createGroupFragment = new CreateGroupFragment();
        Bundle b = new Bundle();
        b.putString("classname", "GroupsTab");
        createGroupFragment.setArguments(b);
        if (getIntent().getExtras() != null) {
            FragmentManager mFragmentManager = getSupportFragmentManager();
            FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
            mFragmentTransaction.replace(R.id.group_container, createGroupFragment, "createGroupFragment")
                    .commit();

        } else {
            getSupportFragmentManager().beginTransaction().
                    replace(R.id.group_container, new GroupMyJoined(), "groupMyJoined")
                    .commit();
        }
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
        int fragment = getSupportFragmentManager().getBackStackEntryCount();
        if (fragment == 1) {
            finish();
            overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
        } else {
            if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                getSupportFragmentManager().popBackStack();
            } else {
                super.onBackPressed();
            }
        }
    }
}
