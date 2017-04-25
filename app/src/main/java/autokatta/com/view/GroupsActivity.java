package autokatta.com.view;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import autokatta.com.R;
import autokatta.com.initial_fragment.GroupDetailTabs;

public class GroupsActivity extends AppCompatActivity {
    Bundle b=new Bundle();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        setTitle("Groups");
        GroupDetailTabs groupDetailTabs = new GroupDetailTabs();
        Intent i=getIntent();
        String grouptype= i.getStringExtra("grouptype");
        String className = i.getStringExtra("className");
        if (grouptype!=null)
        {
            b.putString("grouptype",i.getStringExtra("grouptype"));
            groupDetailTabs.setArguments(b);
        }
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.profile_groups_container, groupDetailTabs, "GroupActivity")
                .addToBackStack("GroupActivity")
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
        super.onBackPressed();
        int fragments = getSupportFragmentManager().getBackStackEntryCount();
        if (fragments == 1) {
            ActivityOptions options = ActivityOptions.makeCustomAnimation(GroupsActivity.this, R.anim.pull_in_left, R.anim.push_out_right);
            startActivity(new Intent(getApplicationContext(), GroupsActivity.class), options.toBundle());
            finish();
        } else {
            if (getFragmentManager().getBackStackEntryCount() > 1) {
                getFragmentManager().popBackStack();
            } else {
                super.onBackPressed();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    ActivityOptions options = ActivityOptions.makeCustomAnimation(GroupsActivity.this, R.anim.pull_in_left, R.anim.push_out_right);
                    startActivity(new Intent(getApplicationContext(), UserProfile.class), options.toBundle());
                    finish();
                } else {
                    finish();
                    startActivity(new Intent(getApplicationContext(), UserProfile.class));
                }
            }
        }
        /*if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            this.finish();
        }*/
        //additional code
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            ActivityOptions options = ActivityOptions.makeCustomAnimation(GroupsActivity.this, R.anim.pull_in_left, R.anim.push_out_right);
            startActivity(new Intent(getApplicationContext(), UserProfile.class), options.toBundle());
            finish();
        } else {
            finish();
            startActivity(new Intent(getApplicationContext(), UserProfile.class));
        }*/
    }
}