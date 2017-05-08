package autokatta.com.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import autokatta.com.R;
import autokatta.com.initial_fragment.CreateEventFragment;

public class Create_Event extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    setSupportActionBar(toolbar);

                    if (getSupportActionBar() != null) {
                        getSupportActionBar().setDisplayShowHomeEnabled(true);
                        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                        //getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
                    }


                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.createEventFrame, new CreateEventFragment(), "createEventFragment")
                            .addToBackStack("createEventFragment")
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
