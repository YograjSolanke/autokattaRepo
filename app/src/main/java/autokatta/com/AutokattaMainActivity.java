package autokatta.com;

import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

import autokatta.com.adapter.TabAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.broadcastreceiver.BackgroundService;
import autokatta.com.broadcastreceiver.Receiver;
import autokatta.com.fragment.FavoriteNotificationFragment;
import autokatta.com.fragment.GroupNotification;
import autokatta.com.fragment.StoreNotification;
import autokatta.com.fragment.WallNotificationFragment;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import autokatta.com.other.DemoDelAct;
import autokatta.com.other.EnquiryActivity;
import autokatta.com.other.Leads;
import autokatta.com.other.PostStatus;
import autokatta.com.other.SearchActivity;
import autokatta.com.other.SessionManagement;
import autokatta.com.other.ShowQrCode;
import autokatta.com.register.RegistrationContinue;
import autokatta.com.response.ModelFirebase;
import autokatta.com.view.BroadcastMessageActivity;
import autokatta.com.view.BrowseStoreActivity;
import autokatta.com.view.BussinessChatActivity;
import autokatta.com.view.GroupTabs;
import autokatta.com.view.MyAutokattaContactsActivity;
import autokatta.com.view.MyBroadcastGroupsActivity;
import autokatta.com.view.MySearchActivity;
import autokatta.com.view.MyStoreListActivity;
import autokatta.com.view.OtherProfile;
import autokatta.com.view.ProductViewActivity;
import autokatta.com.view.SearchNewVehicleActivity;
import autokatta.com.view.SearchStoreActivity;
import autokatta.com.view.SearchVehicleActivity;
import autokatta.com.view.ServiceViewActivity;
import autokatta.com.view.StoreViewActivity;
import autokatta.com.view.UserProfile;
import autokatta.com.view.VehicleDetails;
import autokatta.com.view.VehicleUpload;
import retrofit2.Response;

import static autokatta.com.broadcastreceiver.Receiver.IS_NETWORK_AVAILABLE;

public class AutokattaMainActivity extends AppCompatActivity implements RequestNotifier {
    private DrawerLayout mDrawerLayout;
    boolean isNetworkAvailable;
    SessionManagement session;
    SharedPreferences sharedPreferences = null;
    SharedPreferences.Editor editor;
    private SearchView mSearchView;
    //qr code scanner object
    private IntentIntegrator qrScan;
    private ViewPager viewPager;
    TabLayout tabLayout;
    //TextView mLeads;
    Locale myLocale;
    String mLanguage;
    AlertDialog alertDialog;

    private boolean isBackgroundServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        assert manager != null;
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                System.out.println("class" + serviceClass.getName());
                System.out.println("class1" + service.service.getClassName());
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autokatta_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*Activity activity=this;
        new GoogleChecker(activity, false);*/

        if (getSupportActionBar() != null) {
            getSupportActionBar().setElevation(0);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_menu);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            //getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        }
        fcmRegister();
        sharedPreferences = getSharedPreferences(getString(R.string.firstRun), MODE_PRIVATE);
        Log.i("Language", "->" + mLanguage);
        session = new SessionManagement(getApplicationContext());
        mLanguage = getSharedPreferences(getString(R.string.firstRun), MODE_PRIVATE).getString("Language", "");
        setLocale(mLanguage);

        /* *
         * Call this function whenever you want to check user login
         * This will redirect user to LoginActivity is he is not
         * logged in
         * */
        session.checkLogin();
        //intializing scan object
        qrScan = new IntentIntegrator(this);

        if (!isBackgroundServiceRunning(BackgroundService.class)) {
            startService(new Intent(AutokattaMainActivity.this, BackgroundService.class));
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

        /*
            for push notifications
         */

        if (getIntent().getExtras() != null) {
            String status = getIntent().getExtras().getString("Like", "");
            String contact = getIntent().getExtras().getString("contact", "");

            switch (status) {
                case "Profile": {
                    Bundle mBundle = new Bundle();
                    mBundle.putString("like", status);
                    mBundle.putString("firebaseContact", contact);
                    Intent intent = new Intent(getApplicationContext(), OtherProfile.class);
                    intent.putExtras(mBundle);
                    startActivity(intent);
                    break;
                }
                case "Group": {

                }
                case "Store": {

                }
                case "UploadVehicle": {

                }
                case "New": {

                }

            }

        }
        /*
        For share contents...
         */
        Uri data = getIntent().getData();
        if (data != null) {
            Log.i("URI", "->" + data.toString());
            String[] commaSplit = data.toString().split("/");

            switch (commaSplit[3]) {
                case "store": {
                    int storeId = Integer.parseInt(commaSplit[5]);
                    Log.i("store", "->" + commaSplit[3]);
                    Bundle mBundle = new Bundle();
                    mBundle.putString("action", commaSplit[4]);
                    mBundle.putInt("store_id", storeId);
                    mBundle.putString("StoreContact", commaSplit[6]);
                    Intent store = new Intent(getApplicationContext(), StoreViewActivity.class);
                    store.putExtras(mBundle);
                    startActivity(store);
                    break;
                }
                case "profile": {
                    Log.i("profile", "->" + commaSplit[3]);
                    String otherContact = commaSplit[5];

                    Intent intent = new Intent(getApplicationContext(), OtherProfile.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("contactOtherProfile", otherContact);
                    bundle.putString("uri", data.toString());
                    intent.putExtras(bundle);
                    startActivity(intent);

                    break;
                }
                case "vehicle": {
                    Log.i("vehicle", "->" + commaSplit[3]);
                    int vehicleId = Integer.parseInt(commaSplit[5]);
                    Bundle mBundle = new Bundle();
                    mBundle.putString("uri", data.toString());
                    mBundle.putInt("vehicle_id", vehicleId);
                    Intent vehicle = new Intent(getApplicationContext(), VehicleDetails.class);
                    vehicle.putExtras(mBundle);
                    startActivity(vehicle);
                    break;
                }
                case "product": {
                    Log.i("product", "->" + commaSplit[3]);
                    int productId = Integer.parseInt(commaSplit[4]);
                    Bundle mBundle = new Bundle();
                    Intent intent = new Intent(getApplicationContext(), ProductViewActivity.class);
                    intent.putExtra("product_id", productId);
                    mBundle.putString("uri", data.toString());
                    startActivity(intent);
                    break;
                }
                case "service": {
                    Log.i("service", "->" + commaSplit[3]);
                    int serviceId = Integer.parseInt(commaSplit[4]);
                    Bundle mBundle = new Bundle();
                    Intent intent = new Intent(getApplicationContext(), ServiceViewActivity.class);
                    intent.putExtra("service_id", serviceId);
                    mBundle.putString("uri", data.toString());
                    startActivity(intent);
                    break;
                }
            }
        }


        IntentFilter intentFilter = new IntentFilter(Receiver.NETWORK_AVAILABLE_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                isNetworkAvailable = intent.getBooleanExtra(IS_NETWORK_AVAILABLE, false);
                String networkStatus = isNetworkAvailable ? "Connected" : "Disconnected";
                Snackbar.make(findViewById(R.id.activity_autokatta_main), "Network Status: " + networkStatus, Snackbar.LENGTH_LONG).show();
                Snackbar.make(findViewById(R.id.activity_autokatta_main), "No Internet", Snackbar.LENGTH_LONG)
                        .setAction("Go Online", null).show();
            }
        }, intentFilter);

        final LinearLayout mainView = (LinearLayout) findViewById(R.id.content_dash_board);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            public void onDrawerClosed(View view) {
                supportInvalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                supportInvalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                mainView.setTranslationX(slideOffset * drawerView.getWidth());
                mDrawerLayout.bringChildToFront(drawerView);
                mDrawerLayout.requestLayout();
            }
        };
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }

        /*mLeads = (TextView) MenuItemCompat.getActionView(navigationView.getMenu().
                findItem(R.id.leads));
        mLeads.setGravity(Gravity.CENTER_VERTICAL);
        mLeads.setTypeface(null, Typeface.BOLD);
        mLeads.setTextColor(getResources().getColor(R.color.red));
        //count is added
        mLeads.setText("7");*/

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        if (viewPager != null) {
            setupViewPager(viewPager);
        }

        final com.github.clans.fab.FloatingActionButton fab = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), PostStatus.class));
            }
        });

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.mipmap.ic_web);
        //tabLayout.getTabAt(1).setIcon(R.mipmap.ic_calendar_check);
        /*tabLayout.getTabAt(2).setIcon(R.mipmap.ic_launcher);*/
        //tabLayout.getTabAt(2).setIcon(R.mipmap.ic_calendar_check);
        tabLayout.getTabAt(1).setIcon(R.mipmap.ic_cart);
        tabLayout.getTabAt(2).setIcon(R.mipmap.ic_account_multiple);
        tabLayout.getTabAt(3).setIcon(R.mipmap.ic_heart);


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if (tab.getPosition() == 0)
                    fab.setVisibility(View.VISIBLE);
                else
                    fab.setVisibility(View.GONE);
                Log.i("Position", "->" + tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        try {
            tabLayout.getTabAt(0).getIcon().setAlpha(255);
            tabLayout.getTabAt(1).getIcon().setAlpha(128);
            tabLayout.getTabAt(2).getIcon().setAlpha(128);
            tabLayout.getTabAt(3).getIcon().setAlpha(128);
            //tabLayout.getTabAt(4).getIcon().setAlpha(128);
            //tabLayout.getTabAt(5).getIcon().setAlpha(128);

            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    switch (position) {
                        case 0:
                            tabLayout.getTabAt(0).getIcon().setAlpha(255);
                            tabLayout.getTabAt(1).getIcon().setAlpha(128);
                            tabLayout.getTabAt(2).getIcon().setAlpha(128);
                            tabLayout.getTabAt(3).getIcon().setAlpha(128);
                            //tabLayout.getTabAt(4).getIcon().setAlpha(128);
                            //tabLayout.getTabAt(5).getIcon().setAlpha(128);
                            break;
                        case 1:
                            tabLayout.getTabAt(0).getIcon().setAlpha(128);
                            tabLayout.getTabAt(1).getIcon().setAlpha(255);
                            tabLayout.getTabAt(2).getIcon().setAlpha(128);
                            tabLayout.getTabAt(3).getIcon().setAlpha(128);
                            //tabLayout.getTabAt(4).getIcon().setAlpha(128);
                            //tabLayout.getTabAt(5).getIcon().setAlpha(128);
                            break;
                        case 2:
                            tabLayout.getTabAt(0).getIcon().setAlpha(128);
                            tabLayout.getTabAt(1).getIcon().setAlpha(128);
                            tabLayout.getTabAt(2).getIcon().setAlpha(255);
                            tabLayout.getTabAt(3).getIcon().setAlpha(128);
                            //tabLayout.getTabAt(4).getIcon().setAlpha(128);
                            //tabLayout.getTabAt(5).getIcon().setAlpha(128);
                            break;
                        case 3:
                            tabLayout.getTabAt(0).getIcon().setAlpha(128);
                            tabLayout.getTabAt(1).getIcon().setAlpha(128);
                            tabLayout.getTabAt(2).getIcon().setAlpha(128);
                            tabLayout.getTabAt(3).getIcon().setAlpha(255);
                            //tabLayout.getTabAt(4).getIcon().setAlpha(128);
                            //tabLayout.getTabAt(5).getIcon().setAlpha(128);
                            break;
                        case 4:
                            tabLayout.getTabAt(0).getIcon().setAlpha(128);
                            tabLayout.getTabAt(1).getIcon().setAlpha(128);
                            tabLayout.getTabAt(2).getIcon().setAlpha(128);
                            tabLayout.getTabAt(3).getIcon().setAlpha(128);
                            //tabLayout.getTabAt(4).getIcon().setAlpha(255);
                            //tabLayout.getTabAt(5).getIcon().setAlpha(128);
                            break;
                        case 5:
                            tabLayout.getTabAt(0).getIcon().setAlpha(128);
                            tabLayout.getTabAt(1).getIcon().setAlpha(128);
                            tabLayout.getTabAt(2).getIcon().setAlpha(128);
                            tabLayout.getTabAt(3).getIcon().setAlpha(128);
                            //tabLayout.getTabAt(4).getIcon().setAlpha(128);
                            //tabLayout.getTabAt(5).getIcon().setAlpha(255);
                            break;
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        mDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                if (mLanguage.equals("mr")) {
                    if (navigationView != null) {
                        navigationView.getMenu().getItem(0).setTitle("मुख्यपृष्ठ");
                        navigationView.getMenu().getItem(1).setTitle("माझे प्रोफाइल");
                        navigationView.getMenu().getItem(2).setTitle("माझे ऑटोकट्टा संपर्क");
                        navigationView.getMenu().getItem(3).setTitle("टोळी");
                        navigationView.getMenu().getItem(4).setTitle("स्टोअर");
                        navigationView.getMenu().getItem(4).getSubMenu().getItem(0).setTitle("माझे स्टोअर");
                        navigationView.getMenu().getItem(4).getSubMenu().getItem(1).setTitle("ब्राउझ स्टोअर");
                        navigationView.getMenu().getItem(4).getSubMenu().getItem(2).setTitle("शोध स्टोअर");
                        navigationView.getMenu().getItem(5).setTitle("वाहन");
                        navigationView.getMenu().getItem(5).getSubMenu().getItem(0).setTitle("वाहन अपलोड करा");
                        navigationView.getMenu().getItem(5).getSubMenu().getItem(1).setTitle("वाहन शोधा");
                        navigationView.getMenu().getItem(5).getSubMenu().getItem(2).setTitle("माझे शोध");
                        navigationView.getMenu().getItem(5).getSubMenu().getItem(3).setTitle("माझे इन्व्हेंटरी / कॅटलॉग");
                        navigationView.getMenu().getItem(6).setTitle("लिलाव");
                        navigationView.getMenu().getItem(6).getSubMenu().getItem(0).setTitle("इव्हेंट तयार करा");
                        navigationView.getMenu().getItem(6).getSubMenu().getItem(1).setTitle("माझे सक्रिय इव्हेंट");
                        navigationView.getMenu().getItem(6).getSubMenu().getItem(2).setTitle("माझे आगामी कार्यक्रम");
                        navigationView.getMenu().getItem(6).getSubMenu().getItem(3).setTitle("माझे जतन केलेले इव्हेंट");
                        navigationView.getMenu().getItem(6).getSubMenu().getItem(4).setTitle("माझे शेवटचा कार्यक्रम");
                        navigationView.getMenu().getItem(6).getSubMenu().getItem(5).setTitle("माझे ब्लॅकलिस्ट केलेले सदस्य");
                        navigationView.getMenu().getItem(7).setTitle("इतर");
                        navigationView.getMenu().getItem(7).getSubMenu().getItem(0).setTitle("ब्रॉडकास्ट गट");
                        navigationView.getMenu().getItem(7).getSubMenu().getItem(1).setTitle("माझे प्रसारण संदेश");
                        navigationView.getMenu().getItem(7).getSubMenu().getItem(2).setTitle("व्यवसाय चॅट");
                        navigationView.getMenu().getItem(7).getSubMenu().getItem(3).setTitle("भाषा बदला");
                    }
                }/*else {
                    navigationView.getMenu().getItem(0).setTitle("Home");
                }*/
            }

            @Override
            public void onDrawerOpened(View drawerView) {

            }

            @Override
            public void onDrawerClosed(View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
    }

    /*
    change language
     */
    public void changeLanguageClicked() {
        String locale = (getResources().getConfiguration().locale.toString().toLowerCase().equals(Locale.US.toString().toLowerCase())) ? "mr" : Locale.US.toString();
        Locale myLocale = new Locale(locale);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        Intent refresh = new Intent(this, AutokattaMainActivity.class);
        startActivity(refresh);
        finish();
    }

    private void setupViewPager(ViewPager viewPager) {
        TabAdapter adapter = new TabAdapter(getSupportFragmentManager());
        adapter.addFragment(new WallNotificationFragment());
        //adapter.addFragment(new SocialFragment());
        /*adapter.addFragment(new UpdatesFragment());*/
        /*adapter.addFragment(new AuctionNotification());*/
        adapter.addFragment(new StoreNotification());
        adapter.addFragment(new GroupNotification());
        adapter.addFragment(new FavoriteNotificationFragment());
        viewPager.setAdapter(adapter);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        ActivityOptions options = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.ok_left_to_right, R.anim.ok_left_to_right);
                        if (menuItem.getItemId() == R.id.home) {
                            startActivity(new Intent(AutokattaMainActivity.this, AutokattaMainActivity.class));
                            finish();
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
                            Bundle bundle = new Bundle();
                            bundle.putString("className", "Main");
                            startActivity(new Intent(AutokattaMainActivity.this, SearchVehicleActivity.class).putExtras(bundle));
                        } else if (menuItem.getItemId() == R.id.search_new_vehicle) {
                            startActivity(new Intent(AutokattaMainActivity.this, SearchNewVehicleActivity.class));
                        } else if (menuItem.getItemId() == R.id.my_search) {
                            startActivity(new Intent(AutokattaMainActivity.this, MySearchActivity.class));
                        } else if (menuItem.getItemId() == R.id.my_vehicle) {
                            startActivity(new Intent(AutokattaMainActivity.this, DemoDelAct.class));
                        } else if (menuItem.getItemId() == R.id.create_event) {
                            CustomToast.customToast(getApplicationContext(), "Coming soon... be connected for update");
                            //startActivity(new Intent(AutokattaMainActivity.this, Create_Event.class));
                        } else if (menuItem.getItemId() == R.id.active_event) {
                            CustomToast.customToast(getApplicationContext(), "Coming soon... be connected for update");
                            //startActivity(new Intent(AutokattaMainActivity.this, MyActiveEventsTabActivity.class));
                        } else if (menuItem.getItemId() == R.id.upcoming_event) {
                            CustomToast.customToast(getApplicationContext(), "Coming soon... be connected for update");
                            //startActivity(new Intent(AutokattaMainActivity.this, MyUpcomingEventsTabActivity.class));
                        } else if (menuItem.getItemId() == R.id.saved_event) {
                            CustomToast.customToast(getApplicationContext(), "Coming soon... be connected for update");
                            //startActivity(new Intent(AutokattaMainActivity.this, MySavedAuctionEventActivity.class));
                        } else if (menuItem.getItemId() == R.id.ended_event) {
                            CustomToast.customToast(getApplicationContext(), "Coming soon... be connected for update");
                            //startActivity(new Intent(AutokattaMainActivity.this, MyEndedEventTabActivity.class));
                        } else if (menuItem.getItemId() == R.id.black_list_contact) {
                            CustomToast.customToast(getApplicationContext(), "Coming soon... be connected for update");
                            //startActivity(new Intent(AutokattaMainActivity.this, BlackListedMemberActivity.class));
                        } else if (menuItem.getItemId() == R.id.broadcast_groups) {
                            startActivity(new Intent(AutokattaMainActivity.this, MyBroadcastGroupsActivity.class));
                        } else if (menuItem.getItemId() == R.id.my_broadcast_message) {
                            startActivity(new Intent(AutokattaMainActivity.this, BroadcastMessageActivity.class));
                        } else if (menuItem.getItemId() == R.id.business_Chat) {
                            startActivity(new Intent(AutokattaMainActivity.this, BussinessChatActivity.class));
                        } /*else if (menuItem.getItemId() == R.id.locality) {
                            startActivity(new Intent(AutokattaMainActivity.this, MapsActivity.class));
                            //startActivity(new Intent(AutokattaMainActivity.this, DeleteActivity.class));
                        } */ else if (menuItem.getItemId() == R.id.enquiries) {
                            startActivity(new Intent(AutokattaMainActivity.this, EnquiryActivity.class));
                        } else if (menuItem.getItemId() == R.id.leads) {
                            startActivity(new Intent(AutokattaMainActivity.this, Leads.class));
                        } else if (menuItem.getItemId() == R.id.change_language) {
                            openDialog();
                        } else if (menuItem.getItemId() == R.id.sign_out) {
                            AlertDialog.Builder alert = new AlertDialog.Builder(AutokattaMainActivity.this);
                            alert.setTitle(getString(R.string.alert_title));
                            alert.setMessage(getString(R.string.sign_out_message));
                            alert.setIconAttribute(android.R.attr.alertDialogIcon);

                            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    session.logoutUser();
                                    getApplicationContext().stopService(new Intent(AutokattaMainActivity.this, BackgroundService.class));
                                    finish();
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
                        return true;
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_autokatta_main, menu);
        mSearchView = (SearchView) menu.findItem(R.id.action_searchs).getActionView();
        /*mSearchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setupSearchView();
            }
        });*/

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

            case R.id.action_searchs:
                setupSearchView();
                return true;

            case R.id.qr_code_scan:
                LayoutInflater layoutInflater = LayoutInflater.from(this);
                View view = layoutInflater.inflate(R.layout.qr_code_choice_layout, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Autokatta");
                builder.setIcon(R.drawable.logo48x48);
                builder.setView(view);

                RadioGroup mRadioGroup = (RadioGroup) view.findViewById(R.id.myQrGroup);
                mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                        if (checkedId == R.id.show) {
                            startActivity(new Intent(getApplicationContext(), ShowQrCode.class));
                            alertDialog.dismiss();
                        } else if (checkedId == R.id.scan) {
                            qrScan.initiateScan();
                            alertDialog.dismiss();
                        }
                    }
                });
                alertDialog = builder.create();
                alertDialog.show();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // your code here, you can use newConfig.locale if you need to check the language
        // or just re-set all the labels to desired string resource
        super.onConfigurationChanged(newConfig);
    }

    private void setLocale(String language) {
        myLocale = new Locale(language);
        saveLocale(language);
        Resources resources = getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        Configuration configuration = resources.getConfiguration();
        configuration.locale = myLocale;
        resources.updateConfiguration(configuration, displayMetrics);
    }

    private void saveLocale(String language) {
        mLanguage = language;
        getSharedPreferences(getString(R.string.firstRun), MODE_PRIVATE).edit().putString("Language", language).apply();
    }

    private void openDialog() {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View view = layoutInflater.inflate(R.layout.change_language_title, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Autokatta");
        builder.setIcon(R.drawable.logo48x48);
        builder.setView(view);

        //final TextView mMarathi = (TextView) view.findViewById(R.id.marathi);
        //final TextView mEnglish = (TextView) view.findViewById(R.id.english);
        RadioGroup mRadioGroup = (RadioGroup) view.findViewById(R.id.myRadioGroup);
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (checkedId == R.id.english) {
                    setLocale("en");
                } else if (checkedId == R.id.marathi) {
                    setLocale("mr");
                } else if (checkedId == R.id.hindi) {
                    setLocale("hi");
                }
            }
        });

        /*mMarathi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMarathiStr = mMarathi.getText().toString();
                mMarathi.setBackgroundColor(Color.parseColor("#f7f7f7"));
                setLocale("mr");
            }
        });

        mEnglish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mEnglishStr = mEnglish.getText().toString();
                mEnglish.setBackgroundColor(Color.parseColor("#f7f7f7"));
                setLocale("en");
            }
        });*/

        builder.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent refresh = new Intent(getApplicationContext(), AutokattaMainActivity.class);
                        finish();
                        startActivity(refresh);
                    }
                }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    /*
    On Back Pressed...
     */

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
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

    @Override
    protected void onResume() {
        super.onResume();
        setLocale(mLanguage);
        invalidateOptionsMenu();
        //changeLanguageClicked();
        if (sharedPreferences.getBoolean("firstRun", true)) {
            //You can perform anything over here. This will call only first time
            startActivity(new Intent(getApplicationContext(), RegistrationContinue.class));
            editor = sharedPreferences.edit();
            editor.putBoolean("firstRun", false);
            editor.apply();
        }
    }

    private void setupSearchView() {
        ActivityOptions options = ActivityOptions.makeCustomAnimation(AutokattaMainActivity.this, R.anim.ok_left_to_right, R.anim.ok_right_to_left);
        startActivity(new Intent(getApplicationContext(), SearchActivity.class));
    }

    private void fcmRegister() {
        if (!getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getBoolean("isRegistered", false)) {
            final String contact = getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", null);
            Log.i("Number is", "->" + contact);
            if (contact != null) {
                final String key = getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("fcm_key", null);
                Log.i("FCM is", "->" + key);
                if (key != null) {
                    ApiCall mApiCall = new ApiCall(getApplicationContext(), this);
                    ModelFirebase firebase = new ModelFirebase();
                    firebase.setContactNumber(contact);
                    firebase.setTokenKey(key);
                    mApiCall.firebaseToken(contact, key);
                } else {
                    String fcm = FirebaseInstanceId.getInstance().getToken();
                    getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).edit().putString("fcm_key", fcm).apply();
                    ApiCall mApiCall = new ApiCall(getApplicationContext(), this);
                    ModelFirebase firebase = new ModelFirebase();
                    firebase.setContactNumber(contact);
                    firebase.setTokenKey(fcm);
                    mApiCall.firebaseToken(contact, fcm);
                }
            } else {
                Log.e("Contact is", "null");
            }
        } else {
            Log.e("Firebase", "done");
        }
    }

    @Override
    public void notifySuccess(Response<?> response) {

    }

    @Override
    public void notifyError(Throwable error) {

    }

    @Override
    public void notifyString(String str) {
        if (str != null) {
            Log.i("Firebase Installed:", "->" + str);
            getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).edit().putBoolean("isRegistered", true).apply();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            //if qrcode has nothing in it
            if (result.getContents() == null) {
                Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
            } else {
                //if qr contains data
                try {
                    //converting the data to json
                    JSONObject obj = new JSONObject(result.getContents());
                    Log.i("JSON", "RESULT" + result.getContents());
                    /*//setting values to textviews
                    textViewName.setText(obj.getString("name"));
                    textViewAddress.setText(obj.getString("address"));*/
                    Toast.makeText(getApplicationContext(), " " + obj.getString("storeid") + "->" + obj.getString("storecontact"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                    //if control comes here
                    //that means the encoded format not matches
                    //in this case you can display whatever data is available on the qrcode
                    //to a toast
                    Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
