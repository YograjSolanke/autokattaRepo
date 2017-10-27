package autokatta.com.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import autokatta.com.response.MyStoreResponse;
import autokatta.com.response.ProfileGroupResponse;
import retrofit2.Response;

public class UploadToGroupStoreActivity extends AppCompatActivity implements RequestNotifier, View.OnClickListener {

    List<String> groupIdList = new ArrayList<>();
    List<String> groupTitleList = new ArrayList<>();
    List<String> storeIdList = new ArrayList<>();
    List<String> storeTitleList = new ArrayList<>();
    ListView storelistView, grouplistView;
    CheckedGroupsAdapter mCheckedGroupsAdapter;
    CheckedStoresAdapter mCheckedStoresAdapter;
    Button ok, cancel;
    RelativeLayout groupLayout, storeLayout;
    List<String> finalGroupList = new ArrayList<>();
    List<String> finalStoreList = new ArrayList<>();
    private ProgressDialog dialog;
    int statusId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_to_group_store);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading data, please wait...");

        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                statusId = getIntent().getExtras().getInt("statusId", 0);
                storelistView = (ListView) findViewById(R.id.storelist);
                grouplistView = (ListView) findViewById(R.id.grouplist);
                groupLayout = (RelativeLayout) findViewById(R.id.relativegrouplist);
                storeLayout = (RelativeLayout) findViewById(R.id.relativestorelist);
                ok = (Button) findViewById(R.id.ok);
                cancel = (Button) findViewById(R.id.cancel);

                dialog.show();
                getGroup();
                getStore();
            }
        });
        ok.setOnClickListener(this);
        cancel.setOnClickListener(this);


    }

    /* Get group Data...*/
    private void getGroup() {
        ApiCall mApiCall = new ApiCall(this, this);
        mApiCall.Groups(getApplicationContext().getSharedPreferences(getString(R.string.my_preference),
                MODE_PRIVATE).getString("loginContact", ""), 1, 10);
    }

    /* Get store Data...*/
    private void getStore() {
        ApiCall mApiCall = new ApiCall(this, this);
        mApiCall.MyStoreList(getApplicationContext().getSharedPreferences(getString(R.string.my_preference),
                MODE_PRIVATE).getString("loginContact", ""), 1, 10);
    }

    @Override
    public void notifySuccess(Response<?> response) {
        if (response != null) {
            if (response.isSuccessful()) {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
                if (response.body() instanceof ProfileGroupResponse) {
                    Log.e("ProfileGroupResponse", "->");
                    groupIdList.clear();
                    ProfileGroupResponse mProfileGroupResponse = (ProfileGroupResponse) response.body();
                    for (ProfileGroupResponse.MyGroup success : mProfileGroupResponse.getSuccess().getMyGroups()) {
                        groupIdList.add(String.valueOf(success.getId()));
                        groupTitleList.add(success.getTitle());
                    }
                    for (ProfileGroupResponse.JoinedGroup success : mProfileGroupResponse.getSuccess().getJoinedGroups()) {
                        groupIdList.add(String.valueOf(success.getId()));
                        groupTitleList.add(success.getTitle());
                    }

                    if (groupIdList.size() != 0) {

                        mCheckedGroupsAdapter = new CheckedGroupsAdapter(this, groupIdList, groupTitleList);
                        grouplistView.setAdapter(mCheckedGroupsAdapter);
                    } else {
                        groupLayout.setVisibility(View.GONE);
                    }

                } else if (response.body() instanceof MyStoreResponse) {
                    Log.e("MyStoreResponse", "->");
                    storeIdList.clear();
                    MyStoreResponse myStoreResponse = (MyStoreResponse) response.body();
                    for (MyStoreResponse.Success success : myStoreResponse.getSuccess()) {
                        storeIdList.add(String.valueOf(success.getId()));
                        storeTitleList.add(success.getName());
                    }
                    Log.i("Data", "storIds -" + storeIdList);
                    if (storeIdList.size() != 0) {
                        mCheckedStoresAdapter = new CheckedStoresAdapter(this, storeIdList, storeTitleList);
                        storelistView.setAdapter(mCheckedStoresAdapter);
                    } else {
                        storeLayout.setVisibility(View.GONE);
                    }

                }
            } else {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }

                CustomToast.customToast(getApplicationContext(), getString(R.string.no_response));
            }
        } else {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            CustomToast.customToast(getApplicationContext(), getString(R.string.no_response));
        }
    }

    @Override
    public void notifyError(Throwable error) {
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
        if (error instanceof SocketTimeoutException) {
            //CustomToast.customToast(getApplicationContext(), getString(R.string._404));
        } else if (error instanceof NullPointerException) {
            CustomToast.customToast(getApplicationContext(), getString(R.string.no_response));
        } else if (error instanceof ClassCastException) {
            //CustomToast.customToast(getApplicationContext(), getString(R.string.no_response));
        } else if (error instanceof ConnectException) {
            CustomToast.customToast(getApplicationContext(), getString(R.string.no_response));
        } else if (error instanceof UnknownHostException) {
            CustomToast.customToast(getApplicationContext(), getString(R.string.no_internet));
        } else {
            Log.i("Check Class-", "UploadToGroupStoreActivity");
            error.printStackTrace();
        }
    }

    @Override
    public void notifyString(String str) {
        if (str != null) {
            if (str.equalsIgnoreCase("uploadPostToGroupStore")) {
                CustomToast.customToast(getApplicationContext(), "Uploaded successfully");
                finish();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.ok:

                String GroupIds = "", StoreIds = "";
                if (!(finalGroupList.size() == 0)) {
                    for (int i = 0; i < finalGroupList.size(); i++) {

                        if (!finalGroupList.get(i).equals("0")) {
                            if (GroupIds.equals(""))
                                GroupIds = finalGroupList.get(i);
                            else
                                GroupIds = GroupIds + "," + finalGroupList.get(i);
                        }
                    }
                }
                if (!(finalStoreList.size() == 0)) {

                    for (int i = 0; i < finalStoreList.size(); i++) {
                        if (!finalStoreList.get(i).equals("0")) {
                            if (StoreIds.equals(""))
                                StoreIds = finalStoreList.get(i);
                            else
                                StoreIds = StoreIds + "," + finalStoreList.get(i);
                        }
                    }
                }

                if (GroupIds.equals("") && StoreIds.equals(""))
                    CustomToast.customToast(getApplicationContext(), "Please select atleast group or store to upload");
                else
                    uploadToGroupStore(GroupIds, StoreIds);
                break;

            case R.id.cancel:
                finish();
                break;
        }
    }

    private void uploadToGroupStore(String groupIds, String storeIds) {
        ApiCall mApiCall = new ApiCall(this, this);
        mApiCall.uploadToGroupStore(statusId, groupIds, storeIds);
        Log.i("Groups", groupIds);
        Log.i("Stores", storeIds);
        Log.i("Status", String.valueOf(statusId));
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


    private class CheckedGroupsAdapter extends BaseAdapter {

        private LayoutInflater mInflater;
        Activity activity;
        List<String> idList = new ArrayList<>();
        List<String> titles = new ArrayList<>();
        private List<Boolean> positionArray;

        CheckedGroupsAdapter(Activity a, List<String> id, List<String> titles) {

            this.activity = a;
            this.idList = id;
            this.titles = titles;

            finalGroupList = new ArrayList<>(id.size());
            positionArray = new ArrayList<>(id.size());

            for (int i = 0; i < id.size(); i++) {
                positionArray.add(false);
                finalGroupList.add("0");
            }

            mInflater = (LayoutInflater) activity.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public int getCount() {
            return idList.size();
        }

        public Object getItem(int position) {
            return idList.get(position);
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;
            final View cv = convertView;

            if (convertView == null) {
                holder = new ViewHolder();

                convertView = mInflater.inflate(R.layout.adapter_checked_contact, null);
                holder.text = (TextView) convertView.findViewById(R.id.txtname);
                holder.remove = (Button) convertView.findViewById(R.id.remove);
                holder.remove.setVisibility(View.GONE);
                holder.checkbox = (CheckBox) convertView.findViewById(R.id.checkbox);
                holder.checkbox.setVisibility(View.VISIBLE);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();

            }

            holder.text.setText(titles.get(position));

            holder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {

                        positionArray.set(position, true);
                        finalGroupList.set(position, idList.get(position));
                    } else {
                        positionArray.set(position, false);
                        finalGroupList.set(position, "0");
                    }
                }
            });


            convertView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    v.getParent().requestDisallowInterceptTouchEvent(true);
                    return false;
                }
            });

            return convertView;
        }

    }

    private class CheckedStoresAdapter extends BaseAdapter {

        private LayoutInflater mInflater;
        Activity activity;
        List<String> idList = new ArrayList<>();
        List<String> titles = new ArrayList<>();
        private List<Boolean> positionArray;

        CheckedStoresAdapter(Activity a, List<String> id, List<String> titles) {

            this.activity = a;
            this.idList = id;
            this.titles = titles;

            finalStoreList = new ArrayList<>(id.size());
            positionArray = new ArrayList<>(id.size());

            for (int i = 0; i < id.size(); i++) {
                positionArray.add(false);
                finalStoreList.add("0");
            }

            mInflater = (LayoutInflater) activity.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public int getCount() {
            return idList.size();
        }

        public Object getItem(int position) {
            return idList.get(position);
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;
            final View cv = convertView;


            if (convertView == null) {
                holder = new ViewHolder();

                convertView = mInflater.inflate(R.layout.adapter_checked_contact, null);
                holder.text = (TextView) convertView.findViewById(R.id.txtname);
                holder.remove = (Button) convertView.findViewById(R.id.remove);
                holder.remove.setVisibility(View.GONE);
                holder.checkbox = (CheckBox) convertView.findViewById(R.id.checkbox);
                holder.checkbox.setVisibility(View.VISIBLE);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();

            }

            holder.text.setText(titles.get(position));

            holder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {

                        positionArray.set(position, true);
                        finalStoreList.set(position, idList.get(position));
                    } else {
                        positionArray.set(position, false);
                        finalStoreList.set(position, "0");
                    }
                }
            });


            convertView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    v.getParent().requestDisallowInterceptTouchEvent(true);
                    return false;
                }
            });

            return convertView;
        }

    }

    private class ViewHolder {
        TextView text;
        Button remove;
        CheckBox checkbox;
    }
}
