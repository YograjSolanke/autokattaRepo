package autokatta.com.view;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.apicall.ApiCall;
import autokatta.com.fragment_profile.About;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import autokatta.com.response.MyBroadcastGroupsResponse;
import retrofit2.Response;

public class MyBroadcastGroupsActivity extends AppCompatActivity implements View.OnClickListener, RequestNotifier, SwipeRefreshLayout.OnRefreshListener {

    RecyclerView mRecyclerView;
    SwipeRefreshLayout mSwipeRefreshLayout;
    ApiCall apiCall;
    List<MyBroadcastGroupsResponse.Success> broadcastGroupsResponseList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_broadcast_groups);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            //getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        }

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayoutBGroup);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerBGroup);
        Button btnSendMessage = (Button) findViewById(R.id.btnSendMsg);
        ImageView imgDeleteGroup = (ImageView) findViewById(R.id.deletegroup);
        FloatingActionButton createGroup = (FloatingActionButton) findViewById(R.id.fabCreateBroadcastGroup);


        btnSendMessage.setOnClickListener(this);
        imgDeleteGroup.setOnClickListener(this);
        createGroup.setOnClickListener(this);

        apiCall = new ApiCall(this, this);

        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setReverseLayout(true);
        mLinearLayoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
                apiCall.MyBroadcastGroups(getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", "7841023392"));
            }
        });

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnSendMsg:
                sendMessage();
                break;
            case R.id.deletegroup:
                deleteGroups();
                break;
            case R.id.fabCreateBroadcastGroup:

                About about = new About();
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.broadcast_groupsFrame, about).commit();

                break;
        }
    }

    private void sendMessage() {
        CustomToast.customToast(this, "Sendmessage");
    }

    private void deleteGroups() {

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Delete");
        alert.setMessage("Are you sure you want to delete this group?");
        alert.setIconAttribute(android.R.attr.alertDialogIcon);

        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                /*
                finalgrpids = "";
                incominggrpids.clear();

                incominggrpids = adapter.checkboxselect();
                System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!Checked grp id====" + incominggrpids);

                for (int i = 0; i < incominggrpids.size(); i++) {
                    if (!incominggrpids.get(i).equals("0")) {
                        if (finalgrpids.equals(""))
                            finalgrpids = incominggrpids.get(i);
                        else
                            finalgrpids = finalgrpids + "," + incominggrpids.get(i);

                    }

                }

                System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!going grp ids====" + finalgrpids);

                try {
                    deletegroup(finalgrpids);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                */
                dialog.dismiss();

            }
        });

        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }

        });
        alert.create();
        alert.show();

    }


    @Override
    public void notifySuccess(Response<?> response) {

        if (response != null) {

            if (response.isSuccessful()) {

                MyBroadcastGroupsResponse myBroadcastGroupsResponse = (MyBroadcastGroupsResponse) response.body();

                if (!myBroadcastGroupsResponse.getSuccess().isEmpty()) {

                    for (MyBroadcastGroupsResponse.Success success : myBroadcastGroupsResponse.getSuccess()) {

                        success.setGroupId(success.getGroupId());
                        success.setGroupTitle(success.getGroupTitle());
                        success.setGroupOwner(success.getGroupOwner());
                        success.setGroupMemberContacts(success.getGroupMemberContacts());
                        success.setGroupStatus(success.getGroupStatus());
                        success.setGrpMemberCount(success.getGrpMemberCount());
                        success.setGrpCreatedDate(success.getGrpCreatedDate());

                        broadcastGroupsResponseList.add(success);
                    }
                    mSwipeRefreshLayout.setRefreshing(false);
                    Log.i("size broadcast group", String.valueOf(broadcastGroupsResponseList.size()));
                } else
                    CustomToast.customToast(this, this.getString(R.string.no_response));

            } else
                CustomToast.customToast(this, this.getString(R.string._404));

        } else
            CustomToast.customToast(this, this.getString(R.string.no_response));
    }

    @Override
    public void notifyError(Throwable error) {

    }

    @Override
    public void notifyString(String str) {

    }

    @Override
    public void onRefresh() {

    }
}
