package autokatta.com.fragment_profile;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;

import autokatta.com.R;

public class EditAboutAddress extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener  {
    Menu menu;
SwipeRefreshLayout mSwipeRefreshLayout;
    RecyclerView mRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_about_address);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayoutBGroup);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerBGroup);



        mSwipeRefreshLayout.setOnRefreshListener(EditAboutAddress.this);
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);

               // mApiCall.MyBroadcastGroups(getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", ""));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_address_menu, menu);
        this.menu = menu;

        return true;
    }

    @Override
    public void onRefresh() {
        // mApiCall.MyBroadcastGroups(getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", ""));

    }
}
