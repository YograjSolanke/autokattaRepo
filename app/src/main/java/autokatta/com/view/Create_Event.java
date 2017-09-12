package autokatta.com.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import autokatta.com.R;
import autokatta.com.app_info.CreateEventAppIntro;
import autokatta.com.initial_fragment.CreateEventFragment;

public class Create_Event extends AppCompatActivity {

    SharedPreferences sharedPreferences = null;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Create Event");
        sharedPreferences = getSharedPreferences(getString(R.string.firstRun), MODE_PRIVATE);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
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
    protected void onResume() {
        super.onResume();
        if (sharedPreferences.getBoolean("createEventFirstRun", true)) {
            startActivity(new Intent(getApplicationContext(), CreateEventAppIntro.class));
            editor = sharedPreferences.edit();
            editor.putBoolean("createEventFirstRun", false);
            editor.apply();
        }
    }

    @Override
    public void onBackPressed() {
        /*super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);*/
        //super.onBackPressed();
        int fragments = getSupportFragmentManager().getBackStackEntryCount();
        if (fragments == 1) {
            finish();
        } else {
            if (getFragmentManager().getBackStackEntryCount() > 1) {
                getFragmentManager().popBackStack();
                //Toast.makeText(getApplicationContext(), "clicked...", Toast.LENGTH_SHORT).show();
            } else {
                super.onBackPressed();
                overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
            }
        }
    }
}
