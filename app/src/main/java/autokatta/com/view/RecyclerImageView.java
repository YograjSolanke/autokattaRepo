package autokatta.com.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.adapter.RecyclerImageViewAdapter;

public class RecyclerImageView extends AppCompatActivity {

    String myContact;

    RecyclerView recyclerView;
    LinearLayoutManager mLinearLayoutManager;
    String mBundleImages;
    RecyclerImageViewAdapter adapter;
    TextView mNoData;
    List<String> urlsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_image_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Images");

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        myContact = getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", null);
        recyclerView.setHasFixedSize(true);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setReverseLayout(true);
        mLinearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(mLinearLayoutManager);

        urlsList.add(getString(R.string.base_image_url) + "14970083868751.jpg");
        urlsList.add(getString(R.string.base_image_url) + "1502100947252.jpg");
        urlsList.add(getString(R.string.base_image_url) + "1503311495439.jpg");
        urlsList.add(getString(R.string.base_image_url) + "1505558860268.jpg");
        urlsList.add(getString(R.string.base_image_url) + "a-3.jpg");
        urlsList.add(getString(R.string.base_image_url) + "Avneet%20Singh%20FO%20Rahata%2020171009_140951.jpg");
        urlsList.add(getString(R.string.base_image_url) + "IMG-20170918-WA00045819.jpg");
        urlsList.add(getString(R.string.base_image_url) + "IMG-20170918-WA0025.jpg");
        urlsList.add(getString(R.string.base_image_url) + "IMG-20170922-WA0007.jpg");


        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                if (getSupportActionBar() != null) {
                    getSupportActionBar().setHomeButtonEnabled(true);
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                }

                if (getIntent().getExtras() != null) {
                    mBundleImages = getIntent().getExtras().getString("bundle_GroupId", "");
                    Log.i("groupId", mBundleImages);

                }

                adapter = new RecyclerImageViewAdapter(RecyclerImageView.this, urlsList);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();


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
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
    }

}
