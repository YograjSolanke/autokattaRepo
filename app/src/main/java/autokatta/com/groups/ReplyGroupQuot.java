package autokatta.com.groups;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import autokatta.com.R;

public class ReplyGroupQuot extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply_group_quot);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }
}
