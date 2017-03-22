package autokatta.com.view;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import autokatta.com.response.BlacklistMemberResponse;
import retrofit2.Response;

public class BlackListedMemberActivity extends AppCompatActivity implements RequestNotifier, SwipeRefreshLayout.OnRefreshListener {

    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;
    ApiCall apiCall;
    SharedPreferences sharedPreferences;
    List<BlacklistMemberResponse.Success> blacklistMemberList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_black_listed_member);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayoutBlacklist);
        recyclerView = (RecyclerView) findViewById(R.id.rv_recycler_view);
        apiCall = new ApiCall(BlackListedMemberActivity.this, this);
        sharedPreferences = getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE);
        final String myContact = sharedPreferences.getString("loginContact", "7841023392");


        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);

        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                apiCall.getBlackListMembers(myContact);
            }
        });


    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void notifySuccess(Response<?> response) {

        if (response != null) {

            if (response.isSuccessful()) {

                BlacklistMemberResponse blacklistMemberResponse = (BlacklistMemberResponse) response.body();
                if (!blacklistMemberResponse.getSuccess().isEmpty()) {
                    for (BlacklistMemberResponse.Success success : blacklistMemberResponse.getSuccess()) {

                        success.setId(success.getId());
                        success.setBlacklistContact(success.getBlacklistContact());
                        success.setUsername(success.getUsername());
                        success.setUserimage(success.getUserimage());

                        blacklistMemberList.add(success);


                    }

                    Log.i("Ssize", String.valueOf(blacklistMemberList.size()));
                    swipeRefreshLayout.setRefreshing(false);
                }


            } else {
                CustomToast.customToast(getApplicationContext(), getString(R.string._404));
            }

        } else {
            CustomToast.customToast(getApplicationContext(), getString(R.string.no_response));
        }

    }

    @Override
    public void notifyError(Throwable error) {

    }

    @Override
    public void notifyString(String str) {

    }
}
