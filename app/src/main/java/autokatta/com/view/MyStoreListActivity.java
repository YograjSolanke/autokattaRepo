package autokatta.com.view;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import autokatta.com.R;
import autokatta.com.initial_fragment.MyStoreListFragment;
import autokatta.com.my_store.CreateStoreFragment;

public class MyStoreListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_store_list);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    setSupportActionBar(toolbar);

                    if (getSupportActionBar() != null) {
                        getSupportActionBar().setDisplayShowHomeEnabled(true);
                        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    }

                    if (getIntent().getExtras() != null) {
                        Bundle bundle = new Bundle();
                        bundle.putString("className", getIntent().getExtras().getString("className"));
                        bundle.putString("store_id", getIntent().getExtras().getString("store_id"));
                        CreateStoreFragment fragment = new CreateStoreFragment();
                        fragment.setArguments(bundle);
                        FragmentManager mFragmentManager = getSupportFragmentManager();
                        FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
                        mFragmentTransaction.replace(R.id.myStoreListFrame, fragment).commit();

                    } else {


                        FragmentManager mFragmentManager = getSupportFragmentManager();
                        FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
                        mFragmentTransaction.replace(R.id.myStoreListFrame, new MyStoreListFragment()).commit();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }


}
