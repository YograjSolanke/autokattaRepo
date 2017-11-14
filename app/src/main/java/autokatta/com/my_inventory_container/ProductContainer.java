package autokatta.com.my_inventory_container;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import autokatta.com.R;
import autokatta.com.inventory_catalog.InventoryProductsFragment;

public class ProductContainer extends AppCompatActivity {
    Bundle mBundle;
    InventoryProductsFragment mProductsFragment = new InventoryProductsFragment();
    String Sharedcontact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_container);
        setTitle("Product");
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    Sharedcontact = getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", "");
                    if (getIntent().getExtras() != null) {
                        mBundle = new Bundle();
                        mBundle.putInt("bundle_storeId", getIntent().getExtras().getInt("bundle_storeId", 0));
                        mBundle.putString("bundle_contact", getIntent().getExtras().getString("bundle_contact", Sharedcontact));
                        mProductsFragment.setArguments(mBundle);
                    }

                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.myProductFrame, mProductsFragment, "myProductFrame")
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
}
