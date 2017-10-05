package autokatta.com.register;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import autokatta.com.R;
import autokatta.com.view.CreateStoreActivity;

public class CreateStoreContainer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_store_container);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String mCall = getIntent().getExtras().getString("className");
                Bundle bundle = new Bundle();
                bundle.putString("className", mCall);
//                CreateStoreFragment storeFragment = new CreateStoreFragment();
//                storeFragment.setArguments(bundle);
//                FragmentManager manager = getSupportFragmentManager();
//                FragmentTransaction mFragmentTransaction = manager.beginTransaction();
//                mFragmentTransaction.replace(R.id.create_store_container, storeFragment).commit();
                ActivityOptions options = ActivityOptions.makeCustomAnimation(CreateStoreContainer.this, R.anim.ok_left_to_right, R.anim.ok_right_to_left);
                Intent intent = new Intent(CreateStoreContainer.this, CreateStoreActivity.class);
                intent.putExtras(bundle);
                startActivity(intent, options.toBundle());
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
        finish();
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
    }
}