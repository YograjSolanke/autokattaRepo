package autokatta.com.my_profile_container;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.github.clans.fab.FloatingActionButton;

import autokatta.com.R;
import autokatta.com.fragment_profile.AboutStore;
import autokatta.com.view.CreateStoreActivity;

public class StoreContainer extends AppCompatActivity implements View.OnClickListener {

    FloatingActionButton mFabStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_container);

        mFabStore = (FloatingActionButton) findViewById(R.id.create_store);

        mFabStore.setOnClickListener(this);
        setTitle("My Store");
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.myStoreFrame, new AboutStore(), "myStoreFrame")
                            //.addToBackStack("MyUploadedVehicleTabs")
                            .commit();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
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
        super.onBackPressed();
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.create_store:
                Bundle bundle = new Bundle();
                // bundle.putString("store_id", Store_id);
                bundle.putString("className", "AboutStore");
                ActivityOptions options = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.ok_left_to_right,
                        R.anim.ok_right_to_left);
                Intent intents = new Intent(getApplicationContext(), CreateStoreActivity.class);
                intents.putExtras(bundle);
                startActivity(intents, options.toBundle());
                break;
        }
    }
    /*
    Destroy Activity
     */

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
