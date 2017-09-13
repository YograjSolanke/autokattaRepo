package autokatta.com.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import autokatta.com.R;
import autokatta.com.app_info.SearchStoreAppIntro;
import autokatta.com.initial_fragment.StoreSearchFragment;


public class SearchStoreActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences = null;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_store);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Search Store");
        sharedPreferences = getSharedPreferences(getString(R.string.firstRun), MODE_PRIVATE);
        startActivity(new Intent(getApplicationContext(), SearchStoreAppIntro.class));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.searchStoreFrame, new StoreSearchFragment(), "storeSearchFragment")
                            .addToBackStack("storeSearchFragment")
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
        int fragment = getSupportFragmentManager().getBackStackEntryCount();
        if (fragment == 1) {
            finish();
        } else {
            if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                getSupportFragmentManager().popBackStack();
            } else {
                super.onBackPressed();
                overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
            }
        }
        /*super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);*/
    }

    /*@Override
    protected void onResume() {
        super.onResume();
        if (sharedPreferences.getBoolean("searchStoreFirstRun", true)) {
            startActivity(new Intent(getApplicationContext(), SearchStoreAppIntro.class));
            editor = sharedPreferences.edit();
            editor.putBoolean("searchStoreFirstRun", false);
            editor.apply();
        }
    }*/

}
