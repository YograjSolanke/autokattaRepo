package autokatta.com.other;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import autokatta.com.R;
import autokatta.com.my_inventory_container.BuyerNotificationContainer;
import autokatta.com.my_inventory_container.MyVehicleContainer;
import autokatta.com.my_inventory_container.NewVehicleContainer;
import autokatta.com.my_inventory_container.ProductContainer;
import autokatta.com.my_inventory_container.ServiceContainer;
import autokatta.com.my_inventory_container.SoldVehicleContainer;
import autokatta.com.my_inventory_container.TransferStock;
import autokatta.com.my_inventory_container.UsedVehicleContainer;
import autokatta.com.view.BussinessChatActivity;
import autokatta.com.view.ManualEnquiryMainActivity;

public class InventoryDashboard extends AppCompatActivity {

    GridView androidGridView;
    int mStoreID;
    String mBundleContact;
    Bundle mBundle;

    String[] gridViewString;

    int[] gridViewImageId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_del);
        setTitle("My Inventory");
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


        if (getIntent().getExtras() != null) {
            mStoreID = getIntent().getExtras().getInt("bundle_storeId", 0);
            mBundleContact = getIntent().getExtras().getString("bundle_contact", "");
        }

        if (mStoreID == 0) {
            gridViewString = new String[]{
                    "Product", "Service", "Used Vehicle", "New Vehicle", "Sold Vehicle", "My Vehicle",
                    "Transfer Stock", "Manual Enquiry", "Business Chat", "Search Leads",};

            gridViewImageId = new int[]{
                    R.mipmap.product, R.mipmap.services, R.mipmap.used_vehicle, R.mipmap.new_vehicle, R.mipmap.sold_vehicle,
                    R.mipmap.my_vehicle, R.mipmap.transfer_stock, R.mipmap.manual_enquiry, R.mipmap.business_chat, R.mipmap.search_leads,};
        } else {
            gridViewString = new String[]{
                    "Product", "Service", "Used Vehicle", "New Vehicle", "Sold Vehicle",
                    "Transfer Stock", "Manual Enquiry",};

            gridViewImageId = new int[]{
                    R.mipmap.product, R.mipmap.services, R.mipmap.used_vehicle, R.mipmap.new_vehicle, R.mipmap.sold_vehicle,
                    R.mipmap.transfer_stock, R.mipmap.manual_enquiry,};
        }

        mBundle = new Bundle();
        mBundle.putInt("bundle_storeId", mStoreID);
        mBundle.putString("bundle_contact", mBundleContact);


        CustomGridViewActivity adapterViewAndroid = new CustomGridViewActivity(InventoryDashboard.this, gridViewString, gridViewImageId);
        androidGridView = (GridView) findViewById(R.id.grid_view_image_text);
        androidGridView.setAdapter(adapterViewAndroid);
        androidGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int i, long id) {
                if (mStoreID == 0) {
                    switch (gridViewString[+i]) {

                        case "Product":
                            startActivity(new Intent(getApplicationContext(), ProductContainer.class).putExtras(mBundle));
                            break;
                        case "Service":
                            startActivity(new Intent(getApplicationContext(), ServiceContainer.class).putExtras(mBundle));
                            break;
                        case "Used Vehicle":
                            startActivity(new Intent(getApplicationContext(), UsedVehicleContainer.class).putExtras(mBundle));
                            break;
                        case "New Vehicle":
                            startActivity(new Intent(getApplicationContext(), NewVehicleContainer.class).putExtras(mBundle));
                            break;
                        case "Sold Vehicle":
                            startActivity(new Intent(getApplicationContext(), SoldVehicleContainer.class).putExtras(mBundle));
                            break;
                        case "My Vehicle":
                            startActivity(new Intent(getApplicationContext(), MyVehicleContainer.class));
                            break;
                        case "Transfer Stock":
                            startActivity(new Intent(getApplicationContext(), TransferStock.class).putExtras(mBundle));
                            break;
                        case "Search Leads":
                            startActivity(new Intent(getApplicationContext(), BuyerNotificationContainer.class));
                            break;
                        case "Manual Enquiry":
                            startActivity(new Intent(getApplicationContext(), ManualEnquiryMainActivity.class).putExtras(mBundle));
                            break;
                        case "Business Chat":
                            startActivity(new Intent(getApplicationContext(), BussinessChatActivity.class));
                            break;
                    }
                } else {
                    switch (gridViewString[+i]) {

                        case "Product":
                            startActivity(new Intent(getApplicationContext(), ProductContainer.class).putExtras(mBundle));
                            break;
                        case "Service":
                            startActivity(new Intent(getApplicationContext(), ServiceContainer.class).putExtras(mBundle));
                            break;
                        case "Used Vehicle":
                            startActivity(new Intent(getApplicationContext(), UsedVehicleContainer.class).putExtras(mBundle));
                            break;
                        case "New Vehicle":
                            startActivity(new Intent(getApplicationContext(), NewVehicleContainer.class).putExtras(mBundle));
                            break;
                        case "Sold Vehicle":
                            startActivity(new Intent(getApplicationContext(), SoldVehicleContainer.class).putExtras(mBundle));
                            break;
                        case "Transfer Stock":
                            startActivity(new Intent(getApplicationContext(), TransferStock.class).putExtras(mBundle));
                            break;
                        case "Manual Enquiry":
                            startActivity(new Intent(getApplicationContext(), ManualEnquiryMainActivity.class).putExtras(mBundle));
                            break;
                    }
                }
            }
        });
    }

    public class CustomGridViewActivity extends BaseAdapter {

        private Context mContext;
        private final String[] gridViewString;
        private final int[] gridViewImageId;

        CustomGridViewActivity(Context context, String[] gridViewString, int[] gridViewImageId) {
            mContext = context;
            this.gridViewImageId = gridViewImageId;
            this.gridViewString = gridViewString;
        }

        @Override
        public int getCount() {
            return gridViewString.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup parent) {
            View gridViewAndroid;
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (convertView == null) {
                gridViewAndroid = new View(mContext);
                gridViewAndroid = inflater.inflate(R.layout.gridview_layout, null);
                TextView textViewAndroid = (TextView) gridViewAndroid.findViewById(R.id.android_gridview_text);
                ImageView imageViewAndroid = (ImageView) gridViewAndroid.findViewById(R.id.android_gridview_image);
                textViewAndroid.setText(gridViewString[i]);
                imageViewAndroid.setImageResource(gridViewImageId[i]);
            } else {
                gridViewAndroid = (View) convertView;
            }
            return gridViewAndroid;
        }
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
