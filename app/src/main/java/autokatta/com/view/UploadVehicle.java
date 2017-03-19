package autokatta.com.view;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;

import autokatta.com.R;
import autokatta.com.apicall.ApiCall;
import autokatta.com.fragment_profile.Groups;
import autokatta.com.fragment_profile.Katta;
import autokatta.com.fragment_profile.Modules;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.networkreceiver.ConnectionDetector;
import autokatta.com.other.CustomToast;
import autokatta.com.upload_vehicle.Title;
import retrofit2.Response;

public class UploadVehicle extends AppCompatActivity implements RequestNotifier {

    ListView mVehicleList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_vehicle);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mVehicleList = (ListView) findViewById(R.id.vehicle_list);
        getData();
        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
//            }
        });*/
       // initViewPager();
    }

    private void getData() {
        ApiCall mApiCall = new ApiCall(UploadVehicle.this, this);
        mApiCall.getVehicleCount("7841023392");
    }

    @Override
    public void notifySuccess(Response<?> response) {
        //No need to work
    }

    @Override
    public void notifyError(Throwable error) {
        if (error instanceof SocketTimeoutException) {
            Toast.makeText(getApplicationContext(), getString(R.string._404), Toast.LENGTH_SHORT).show();
        } else if (error instanceof NullPointerException) {
            Toast.makeText(getApplicationContext(), getString(R.string.no_response), Toast.LENGTH_SHORT).show();
        } else if (error instanceof ClassCastException) {
            Toast.makeText(getApplicationContext(), getString(R.string.no_response), Toast.LENGTH_SHORT).show();
        } else {
            Log.i("Check Class-"
                    , "Login Activity");
        }
    }

    @Override
    public void notifyString(String str) {
           if (!str.isEmpty()){
               int _str = Integer.parseInt(str);
               if (_str>2){
                   new AlertDialog.Builder(getApplicationContext())
                           .setTitle("Upload Vehicle")
                           .setMessage("You already uploaded"+_str+"vehicles if you wan to upload more vehicles you have to " +
                                   "pay please press yes to payment gateway")
                           .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                       public void onClick(DialogInterface dialog, int which) {
                           getVehicleData();
                       }
                   }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                       public void onClick(DialogInterface dialog, int which) {
                           onBackPressed();

                       }
                   })
                           .setIcon(android.R.drawable.ic_dialog_alert)
                           .show();
               }
           }else {
               CustomToast.customToast(getApplicationContext(),getString(R.string.no_response));
           }
    }

    private void getVehicleData() {
        ApiCall mApiCall = new ApiCall(UploadVehicle.this, this);
    }
    /*private void initViewPager() {
        ViewPager pager = (ViewPager) findViewById(R.id.view_pager);
        if (pager != null) {
            pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        }
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int pos) {
            switch (pos) {

                case 0:
                    return Title.newInstance("Title");
                case 1:
                    return Katta.newInstance("Two");
                case 2:
                    return Katta.newInstance("Three");
                case 3:
                    return Katta.newInstance("Four");
                case 4:
                    return Katta.newInstance("Five");
                default:
                    return Katta.newInstance("Six");
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Vehicle Title";
                case 1:
                    return "Title2";
                case 2:
                    return "Title3";
                default:
                    return "Title default";

            }
        }

        @Override
        public int getCount() {
            return 5;
        }

    }*/

}
