package autokatta.com.groups_container;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import autokatta.com.R;
import autokatta.com.groups.GroupCommunicationFragment;

public class CommunicationContainer extends AppCompatActivity {

    GroupCommunicationFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_communication_container);
        setTitle("Communication");
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        Bundle b = new Bundle();
        Bundle b1 = getIntent().getExtras();
        fragment = new GroupCommunicationFragment();
        if (b1 != null) {
            //getActivity().setTitle("Group: " + b1.getString("bundle_GroupName"));
            b.putString("grouptype", b1.getString("grouptype"));
            b.putString("className", b1.getString("className"));
            b.putInt("bundle_GroupId", b1.getInt("bundle_GroupId"));
            b.putString("bundle_GroupName", b1.getString("bundle_GroupName"));
            b.putString("bundle_Contact", b1.getString("bundle_Contact"));
            fragment.setArguments(b);
        }

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.communicationFrame, fragment, "communicationFrame")
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
