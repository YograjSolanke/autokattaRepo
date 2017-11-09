package autokatta.com.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import autokatta.com.R;

public class GroupLinkActivity extends AppCompatActivity implements View.OnClickListener {
    String mBundleContact;
    int mGroupID;
    Button mBtnJoion, mBtnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_link);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Group Link Requests");

        mBtnJoion = (Button) findViewById(R.id.btnJoin);
        mBtnCancel = (Button) findViewById(R.id.btnCancel);

        mBtnJoion.setOnClickListener(this);
        mBtnCancel.setOnClickListener(this);


        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        if (getIntent().getExtras() != null) {
            mGroupID = getIntent().getExtras().getInt("bundle_GroupId", 0);
            mBundleContact = getIntent().getExtras().getString("bundle_Contact", "");
        }
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnJoin:

                break;
            case R.id.btnCancel:
                finish();
                break;
        }
    }
}
