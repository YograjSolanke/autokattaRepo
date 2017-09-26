package autokatta.com.my_profile_container;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.github.clans.fab.FloatingActionButton;

import autokatta.com.R;
import autokatta.com.fragment_profile.Groups;
import autokatta.com.view.GroupTabs;

public class GroupContainer extends AppCompatActivity implements View.OnClickListener {

    FloatingActionButton mCreateGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_container);
        mCreateGroup = (FloatingActionButton) findViewById(R.id.create_group);
        mCreateGroup.setOnClickListener(this);
        setTitle("My Group");
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.myGroupFrame, new Groups(), "myGroupFrame")
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
            case R.id.create_group:
                Intent intent = new Intent(getApplicationContext(), GroupTabs.class);
                intent.putExtra("ClassName", "Groups");
                startActivity(intent);
                break;
        }
    }
}
