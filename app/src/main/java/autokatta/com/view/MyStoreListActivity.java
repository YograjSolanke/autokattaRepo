package autokatta.com.view;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.net.SocketTimeoutException;
import java.util.ArrayList;

import autokatta.com.R;
import autokatta.com.apicall.ApiCall;
import autokatta.com.fragment_profile.About;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import autokatta.com.response.MyStoreResponse;
import retrofit2.Response;

public class MyStoreListActivity extends AppCompatActivity implements RequestNotifier, View.OnClickListener {

    RecyclerView mRecyclerView;
    SharedPreferences mSharedPreferences;
    FloatingActionButton fabCreateStore;
    ArrayList<MyStoreResponse.Success> storeResponseArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_store_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fabCreateStore = (FloatingActionButton) findViewById(R.id.fabCreateStore);
        fabCreateStore.setOnClickListener(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getApplicationContext());
        mLinearLayoutManager.setReverseLayout(true);
        mLinearLayoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        mSharedPreferences = getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE);
        String myContact = mSharedPreferences.getString("autokattaContact", "7841023392");
        ApiCall apiCall = new ApiCall(MyStoreListActivity.this, this);
        apiCall.MyStoreList(myContact);
    }

    @Override
    public void notifySuccess(Response<?> response) {

        if (response.isSuccessful()) {
            storeResponseArrayList = new ArrayList<>();
            storeResponseArrayList.clear();

            MyStoreResponse myStoreResponse = (MyStoreResponse) response.body();


            if (!myStoreResponse.getSuccess().isEmpty()) {
                for (MyStoreResponse.Success Sresponse : myStoreResponse.getSuccess()) {

                    Sresponse.setId(Sresponse.getId());
                    Sresponse.setName(Sresponse.getName());
                    Sresponse.setLocation(Sresponse.getLocation());
                    Sresponse.setWebsite(Sresponse.getWebsite());
                    Sresponse.setStoreOpenTime(Sresponse.getStoreOpenTime());
                    Sresponse.setStoreCloseTime(Sresponse.getStoreCloseTime());
                    Sresponse.setStoreImage(Sresponse.getStoreImage());
                    Sresponse.setCoverImage(Sresponse.getCoverImage());
                    Sresponse.setWorkingDays(Sresponse.getWorkingDays());
                    Sresponse.setRating(Sresponse.getRating());
                    Sresponse.setLikecount(Sresponse.getLikecount());
                    Sresponse.setFollowcount(Sresponse.getFollowcount());
                    Sresponse.setStoreType(Sresponse.getStoreType());

                    storeResponseArrayList.add(Sresponse);
                }

                Log.i("Store list size=>", String.valueOf(storeResponseArrayList.size()));
            } else
                CustomToast.customToast(getApplicationContext(), getString(R.string.no_response));
        }

    }

    @Override
    public void notifyError(Throwable error) {
        if (error instanceof SocketTimeoutException) {
            Toast.makeText(getApplicationContext(), getString(R.string._404), Toast.LENGTH_SHORT).show();
        } else if (error instanceof NullPointerException) {
            Toast.makeText(getApplicationContext(), getString(R.string.no_response), Toast.LENGTH_SHORT).show();
        } else if (error instanceof ClassCastException) {
            Toast.makeText(getApplicationContext(), getString(R.string.no_response), Toast.LENGTH_SHORT).show();
        } else {
            Log.i("Check Class-"
                    , "MyStoreListActivity");
        }
    }

    @Override
    public void notifyString(String str) {

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.fabCreateStore:
                About about = new About();
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.myStoreFrame, about).commit();

        }
    }
}
