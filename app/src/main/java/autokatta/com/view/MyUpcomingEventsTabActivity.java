package autokatta.com.view;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import autokatta.com.R;
import autokatta.com.initial_fragment.MyUpcomingEventTabFragment;

public class MyUpcomingEventsTabActivity extends AppCompatActivity {

    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_upcoming_events_tab);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setTitle("My Upcoming Events");


        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    setSupportActionBar(toolbar);

                    if (getSupportActionBar() != null) {
                        getSupportActionBar().setDisplayShowHomeEnabled(true);
                        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.upcomingEventFrame, new MyUpcomingEventTabFragment(), "myUpcomingEventTabFragment")
                .addToBackStack("myUpcomingEventTabFragment")
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

        int fragment = getSupportFragmentManager().getBackStackEntryCount();
        if (fragment == 1) {
            finish();
            overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);

        } else {
            if (getFragmentManager().getBackStackEntryCount() > 1) {
                getFragmentManager().popBackStack();
            } else
                super.onBackPressed();
        }
    }

}
