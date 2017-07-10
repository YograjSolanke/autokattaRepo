package autokatta.com.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import autokatta.com.R;
import autokatta.com.initial_fragment.GroupDetailTabs;

public class GroupsActivity extends AppCompatActivity {
    Bundle b = new Bundle();
    String className;

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
        Intent i = getIntent();
        String grouptype = i.getStringExtra("grouptype");
        className = i.getStringExtra("className");
        if (grouptype != null) {
            b.putString("grouptype", i.getStringExtra("grouptype"));
            b.putString("className", i.getStringExtra("className"));
            b.putString("bundle_GroupId", i.getStringExtra("bundle_GroupId"));
            b.putString("bundle_GroupName", i.getStringExtra("bundle_GroupName"));
            b.putString("tabIndex", i.getStringExtra("tabIndex"));
            b.putString("bundle_Contact", i.getStringExtra("bundle_Contact"));
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
       /* super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);*/
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

        /*int fragments = getSupportFragmentManager().getBackStackEntryCount();
        if (fragments == 1) {
            *//*ActivityOptions options = ActivityOptions.makeCustomAnimation(GroupsActivity.this, R.anim.pull_in_left, R.anim.push_out_right);
            startActivity(new Intent(getApplicationContext(), GroupsActivity.class), options.toBundle());*//*
            finish();
        } else {
            if (getFragmentManager().getBackStackEntryCount() > 1) {
                getFragmentManager().popBackStack();
            } else {
                super.onBackPressed();
                finishActivity(1);
                *//*if (className == null) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        ActivityOptions options = ActivityOptions.makeCustomAnimation(GroupsActivity.this, R.anim.pull_in_left, R.anim.push_out_right);
                        startActivity(new Intent(getApplicationContext(), UserProfile.class), options.toBundle());
                        finish();
                    } else {
                        finish();
                        startActivity(new Intent(getApplicationContext(), UserProfile.class));
                    }
                }*//*
            }
        }*/
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