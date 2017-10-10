package autokatta.com.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import autokatta.com.R;
import autokatta.com.app_info.BusinessChatAppIntro;

public class BussinessChatActivity extends AppCompatActivity {

    Bundle b1=new Bundle();
    BussinessChatTabs bussinessChatTabs=new BussinessChatTabs();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bussiness_chat);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Business Chat");
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
           b1.putString("callfrom", getIntent().getStringExtra("callfrom"));
           bussinessChatTabs.setArguments(b1);

       }

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.bussines_chat_container,bussinessChatTabs , "businessChat")
                .addToBackStack("businessChat")
                .commit();
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
