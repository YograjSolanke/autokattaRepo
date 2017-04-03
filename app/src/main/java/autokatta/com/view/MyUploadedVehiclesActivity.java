package autokatta.com.view;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import autokatta.com.R;
import autokatta.com.initial_fragment.MyUploadedVehiclesFragment;

public class MyUploadedVehiclesActivity extends AppCompatActivity {

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_uploaded_vehicles);
        toolbar = (Toolbar) findViewById(R.id.toolbar);


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
        fragmentTransaction.replace(R.id.myUploadedVehicleFrame, new MyUploadedVehiclesFragment()).commit();

    }


}
