package autokatta.com.other;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import autokatta.com.R;
import autokatta.com.app_info.BusinessChatAppIntro;
import autokatta.com.fragment.BussinessMsgSenders;
import autokatta.com.view.BussinessChatTabs;

/**
 * Created by ak-005 on 4/11/17.
 */

public class ContainerForLoadingOffer extends AppCompatActivity {

    Bundle b1=new Bundle();
    BussinessChatTabs bussinessChatTabs=new BussinessChatTabs();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.container_for_loading_offer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Offer Chat");
        startActivity(new Intent(getApplicationContext(), BusinessChatAppIntro.class));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            //    getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        }

       /* FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.bussines_chat_container, new BussinessChatFragment()).commit();*/
        if (getIntent() != null) {
            // b1.putString("callfrom", getIntent().getStringExtra("callfrom"));
            // bussinessChatTabs.setArguments(b1);

            Bundle b = new Bundle();
            b.putInt("product_id", 0);
            b.putInt("service_id", 0);
            b.putInt("vehicle_id", getIntent().getIntExtra("vehicle_id", 0));
            b.putString("keyword", "Used Vehicle");
            b.putString("title", getIntent().getStringExtra("title"));
            b.putString("price", getIntent().getStringExtra("price"));
            b.putString("category", getIntent().getStringExtra("category"));
            b.putString("brand", getIntent().getStringExtra("brand"));
            b.putString("model", getIntent().getStringExtra("model"));
            b.putString("image", getIntent().getStringExtra("image"));


            BussinessMsgSenders obj = new BussinessMsgSenders();
            obj.setArguments(b);

           getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.ok_left_to_right, R.anim.ok_right_to_left)
                    .replace(R.id.offer_chat_container, obj, "MyUploadedVehiclesFragment")
                    .addToBackStack("MyUploadedVehiclesFragment")
                    .commit();
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
        //super.onBackPressed();
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
