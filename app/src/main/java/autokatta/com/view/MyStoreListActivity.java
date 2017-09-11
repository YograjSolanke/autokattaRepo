package autokatta.com.view;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import autokatta.com.R;
import autokatta.com.initial_fragment.MyStoreListFragment;
import autokatta.com.my_store.CreateStoreFragment;

public class MyStoreListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_store_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("My Store");

        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (getIntent().getExtras() != null) {
                        Bundle bundle = new Bundle();
                        bundle.putString("className", getIntent().getExtras().getString("className"));
                        bundle.putInt("store_id", getIntent().getExtras().getInt("store_id"));
                        CreateStoreFragment fragment = new CreateStoreFragment();
                        fragment.setArguments(bundle);

                        FragmentManager mFragmentManager = getSupportFragmentManager();
                        FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
                        mFragmentTransaction.replace(R.id.myStoreListFrame, fragment, "createStoreFragment")
                                .addToBackStack("createStoreFragment")
                                .commit();

                    } else {
                        FragmentManager mFragmentManager = getSupportFragmentManager();
                        FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
                        mFragmentTransaction.replace(R.id.myStoreListFrame, new MyStoreListFragment(), "myStoreListFragment")
                                .addToBackStack("myStoreListFragment")
                                .commit();
                    }

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
