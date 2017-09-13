package autokatta.com.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import autokatta.com.R;
import autokatta.com.app_info.MyInventoryAppIntro;
import autokatta.com.initial_fragment.MyUploadedVehicleTabs;

public class MyUploadedVehiclesActivity extends AppCompatActivity {
    Toolbar toolbar;
    SharedPreferences sharedPreferences = null;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_uploaded_vehicles);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sharedPreferences = getSharedPreferences(getString(R.string.firstRun), MODE_PRIVATE);
        setTitle("My Inventory");
        startActivity(new Intent(getApplicationContext(), MyInventoryAppIntro.class));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.myUploadedVehicleFrame, new MyUploadedVehicleTabs(), "MyUploadedVehicleTabs")
                            //.addToBackStack("MyUploadedVehicleTabs")
                            .commit();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        /*FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.myUploadedVehicleFrame, new MyUploadedVehiclesFragment()).commit();*/
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
        } else {
            if (getFragmentManager().getBackStackEntryCount() > 1) {
                getFragmentManager().popBackStack();
            } else {
                super.onBackPressed();
                overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
            }
        }
    }

    /*@Override
    protected void onResume() {
        super.onResume();
        if (sharedPreferences.getBoolean("inventoryFirstRun", true)) {
            startActivity(new Intent(getApplicationContext(), MyInventoryAppIntro.class));
            editor = sharedPreferences.edit();
            editor.putBoolean("inventoryFirstRun", false);
            editor.apply();
        }
    }*/
}
