package autokatta.com;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import autokatta.com.adapter.TabAdapter;
import autokatta.com.broadcastreceiver.Receiver;
import autokatta.com.fragment.AuctionNotification;
import autokatta.com.fragment.SocialFragment;
import autokatta.com.fragment.StoreNotification;
import autokatta.com.fragment.UpdatesFragment;
import autokatta.com.fragment.WallNotificationFragment;
import autokatta.com.fragment_profile.About;
import autokatta.com.other.SessionManagement;
import autokatta.com.view.BlackListedMemberActivity;
import autokatta.com.view.BroadcastMessageActivity;
import autokatta.com.view.BrowseStoreActivity;
import autokatta.com.view.Create_Event;
import autokatta.com.view.GroupTabs;
import autokatta.com.view.MyActiveEventsTabActivity;
import autokatta.com.view.MyAutokattaContactsActivity;
import autokatta.com.view.MyBroadcastGroupsActivity;
import autokatta.com.view.MyEndedEventTabActivity;
import autokatta.com.view.MySavedAuctionEventActivity;
import autokatta.com.view.MySearchActivity;
import autokatta.com.view.MyStoreListActivity;
import autokatta.com.view.MyUpcomingEventsTabActivity;
import autokatta.com.view.MyUploadedVehiclesActivity;
import autokatta.com.view.SearchStoreActivity;
import autokatta.com.view.UserProfile;
import autokatta.com.view.VehicleDetails;
import autokatta.com.view.VehicleUpload;

import static autokatta.com.broadcastreceiver.Receiver.IS_NETWORK_AVAILABLE;

public class AutokattaMainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    boolean isNetworkAvailable;
    SessionManagement session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autokatta_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        session = new SessionManagement(getApplicationContext());
        if (getSupportActionBar() != null) {
            getSupportActionBar().setElevation(0);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_menu);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            //getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        }
       /* DbOperation dbAdpter = new DbOperation(getApplicationContext());
        dbAdpter.OPEN();
        Cursor cursor = dbAdpter.getAutokattaContact();
        if (cursor.getCount()>0){
            cursor.moveToFirst();
            do {
                Log.i(DbConstants.TAG,cursor.getString(cursor.getColumnIndex(DbConstants.userName))+" = "+cursor.getString(cursor.getColumnIndex(DbConstants.contact)));
            }while (cursor.moveToNext());
        }
        dbAdpter.CLOSE();*/
        /*final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.mipmap.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);*/

        IntentFilter intentFilter = new IntentFilter(Receiver.NETWORK_AVAILABLE_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                isNetworkAvailable = intent.getBooleanExtra(IS_NETWORK_AVAILABLE, false);
                String networkStatus = isNetworkAvailable ? "Connected" : "Disconnected";
                //Snackbar.make(findViewById(R.id.activity_autokatta_main), "Network Status: " + networkStatus, Snackbar.LENGTH_LONG).show();
                Snackbar.make(findViewById(R.id.activity_autokatta_main), "No Internet", Snackbar.LENGTH_LONG)
                        .setAction("Go Online", null).show();
            }
        }, intentFilter);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        if (viewPager != null) {
            setupViewPager(viewPager);
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.mipmap.ic_web);
        tabLayout.getTabAt(1).setIcon(R.mipmap.ic_bell);
        tabLayout.getTabAt(2).setIcon(R.mipmap.ic_launcher);
        tabLayout.getTabAt(3).setIcon(R.mipmap.ic_calendar_check);
        tabLayout.getTabAt(4).setIcon(R.mipmap.ic_cart);
        tabLayout.getTabAt(5).setIcon(R.mipmap.ic_account_multiple);
        tabLayout.getTabAt(6).setIcon(R.mipmap.ic_heart);
    }
    private void setupViewPager(ViewPager viewPager) {
        TabAdapter adapter = new TabAdapter(getSupportFragmentManager());
        adapter.addFragment(new WallNotificationFragment());
        adapter.addFragment(new SocialFragment());
        adapter.addFragment(new UpdatesFragment());
        adapter.addFragment(new AuctionNotification());
        adapter.addFragment(new StoreNotification());
        adapter.addFragment(new WallNotificationFragment());
        adapter.addFragment(new About());
        viewPager.setAdapter(adapter);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();

                        if (menuItem.getItemId() == R.id.home) {

                        } else if (menuItem.getItemId() == R.id.profile) {
                            startActivity(new Intent(AutokattaMainActivity.this, UserProfile.class));
                        } else if (menuItem.getItemId() == R.id.my_contacts) {
                            startActivity(new Intent(AutokattaMainActivity.this, MyAutokattaContactsActivity.class));
                        } else if (menuItem.getItemId() == R.id.group) {
                            startActivity(new Intent(AutokattaMainActivity.this, GroupTabs.class));
                        } else if (menuItem.getItemId() == R.id.my_store) {
                            startActivity(new Intent(AutokattaMainActivity.this, MyStoreListActivity.class));
                        } else if (menuItem.getItemId() == R.id.browse_store) {
                            startActivity(new Intent(AutokattaMainActivity.this, BrowseStoreActivity.class));
                        } else if (menuItem.getItemId() == R.id.search_store) {
                            startActivity(new Intent(AutokattaMainActivity.this, SearchStoreActivity.class));
                        } else if (menuItem.getItemId() == R.id.upload_vehicle) {
                            Intent intent = new Intent(AutokattaMainActivity.this, VehicleUpload.class);
                            startActivity(intent);
                        } else if (menuItem.getItemId() == R.id.search_vehicle) {

                        } else if (menuItem.getItemId() == R.id.my_search) {
                            startActivity(new Intent(AutokattaMainActivity.this, MySearchActivity.class));
                        } else if (menuItem.getItemId() == R.id.my_vehicle) {
                            startActivity(new Intent(AutokattaMainActivity.this, MyUploadedVehiclesActivity.class));

                        } else if (menuItem.getItemId() == R.id.create_event) {
                            startActivity(new Intent(AutokattaMainActivity.this, Create_Event.class));

                        } else if (menuItem.getItemId() == R.id.active_event) {
                            startActivity(new Intent(AutokattaMainActivity.this, MyActiveEventsTabActivity.class));

                        } else if (menuItem.getItemId() == R.id.upcoming_event) {
                            startActivity(new Intent(AutokattaMainActivity.this, MyUpcomingEventsTabActivity.class));
                        } else if (menuItem.getItemId() == R.id.saved_event) {

                            startActivity(new Intent(AutokattaMainActivity.this, MySavedAuctionEventActivity.class));

                        } else if (menuItem.getItemId() == R.id.ended_event) {
                            startActivity(new Intent(AutokattaMainActivity.this, MyEndedEventTabActivity.class));

                        } else if (menuItem.getItemId() == R.id.black_list_contact) {
                            startActivity(new Intent(AutokattaMainActivity.this, BlackListedMemberActivity.class));

                        } else if (menuItem.getItemId() == R.id.broadcast_groups) {
                            startActivity(new Intent(AutokattaMainActivity.this, MyBroadcastGroupsActivity.class));

                        } else if (menuItem.getItemId() == R.id.my_broadcast_message) {
                            startActivity(new Intent(AutokattaMainActivity.this, BroadcastMessageActivity.class));
                        } else if (menuItem.getItemId() == R.id.business_Chat) {
startActivity(new Intent(AutokattaMainActivity.this, VehicleDetails.class));
                        } else if (menuItem.getItemId() == R.id.locality) {

                        } else if (menuItem.getItemId() == R.id.sign_out) {
                            session.logoutUser();
                        }
                        return true;
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_autokatta_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
       /* int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }*/
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*
    On Back Pressed...
     */

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(getString(R.string.alert_title));
        alert.setMessage(getString(R.string.alert_msg));
        alert.setIconAttribute(android.R.attr.alertDialogIcon);

        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                System.exit(0);
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }

        });
        alert.create();
        alert.show();
    }
}
