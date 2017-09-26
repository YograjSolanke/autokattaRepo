package autokatta.com.view;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.kaopiz.kprogresshud.KProgressHUD;

import autokatta.com.R;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.networkreceiver.ConnectionDetector;
import retrofit2.Response;


public class ReviewActivity extends AppCompatActivity implements RequestNotifier, View.OnClickListener {

    ConnectionDetector mConnectionDetector;
    ApiCall mApiCall;
    String contact;
    KProgressHUD hud;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Reviews");

        mApiCall = new ApiCall(ReviewActivity.this, this);
        contact = getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE)
                .getString("loginContact", "");


        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...");
        mConnectionDetector = new ConnectionDetector(this);


        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (getSupportActionBar() != null) {
                        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                        getSupportActionBar().setDisplayShowHomeEnabled(true);
                    }


                    hud = KProgressHUD.create(ReviewActivity.this)
                            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                            .setLabel("Please wait")
                            .setMaxProgress(100)
                            .show();
                    if (!mConnectionDetector.isConnectedToInternet()) {
                        Toast.makeText(ReviewActivity.this, "Please check your internet connection", Toast.LENGTH_SHORT).show();
                    } else {
                        //call webServiceHere
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab:

                //write review here
                break;
        }

    }

    @Override
    public void notifySuccess(Response<?> response) {

    }

    @Override
    public void notifyError(Throwable error) {

    }

    @Override
    public void notifyString(String str) {

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
        finish();
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
    }
}
