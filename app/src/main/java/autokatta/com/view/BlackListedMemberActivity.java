package autokatta.com.view;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import autokatta.com.R;
import autokatta.com.initial_fragment.MyBlacklistedMemberFragment;

public class BlackListedMemberActivity extends AppCompatActivity {
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_black_listed_member);
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
        fragmentTransaction.replace(R.id.blacklistFrame, new MyBlacklistedMemberFragment()).commit();





    }


}
