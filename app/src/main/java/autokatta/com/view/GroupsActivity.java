package autokatta.com.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import autokatta.com.R;
import autokatta.com.apicall.ApiCall;
import autokatta.com.generic.Base64;
import autokatta.com.groups_container.CommunicationContainer;
import autokatta.com.groups_container.GroupProductContainer;
import autokatta.com.groups_container.GroupServiceContainer;
import autokatta.com.groups_container.MemberContainer;
import autokatta.com.groups_container.VehicleContainer;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import retrofit2.Response;

public class GroupsActivity extends AppCompatActivity implements RequestNotifier {
    Bundle b = new Bundle();
    String groupPrivacy, groupType;
    GridView androidGridView;
    ImageView mImageView;
    Button mShare;
    int mGroupID;
    private String mLoginContact;
    private ProgressDialog pDialog;
    String[] gridViewString = {
            "Communication", "Members", "Vehicle", "Product", "Service", "Video's",
            "Image's",};

    int[] gridViewImageId = {
            R.mipmap.communication, R.mipmap.members, R.mipmap.group_vehicles, R.mipmap.product, R.mipmap.services,
            R.mipmap.videos, R.mipmap.images,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");

        mLoginContact = getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE)
                .getString("loginContact", "");

        androidGridView = (GridView) findViewById(R.id.group_grid_view);
        mImageView = (ImageView) findViewById(R.id.groupImage);
        mShare = (Button) findViewById(R.id.shares);

        Intent i = getIntent();
        if (i.getExtras() != null) {
            b.putString("grouptype", i.getStringExtra("grouptype"));
            b.putString("className", i.getStringExtra("className"));
            b.putInt("bundle_GroupId", i.getIntExtra("bundle_GroupId", 0));
            b.putString("bundle_GroupName", i.getStringExtra("bundle_GroupName"));
            b.putString("tabIndex", i.getStringExtra("tabIndex"));
            b.putString("bundle_Contact", i.getStringExtra("bundle_Contact"));
            b.putString("bundle_groupPrivacy", i.getStringExtra("bundle_groupPrivacy"));
            setTitle(i.getStringExtra("bundle_GroupName"));

            groupPrivacy = i.getStringExtra("bundle_groupPrivacy");
            mGroupID = i.getIntExtra("bundle_GroupId", 0);
            groupType = i.getStringExtra("grouptype");

            getGroupData(mGroupID);


            /*Glide.with(getApplicationContext())
                    .load(getString(R.string.base_image_url)+)*/
        }

        GroupsActivity.CustomGridViewActivity adapterViewAndroid = new GroupsActivity.CustomGridViewActivity(GroupsActivity.this, gridViewString, gridViewImageId);
        androidGridView.setAdapter(adapterViewAndroid);

        androidGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int i, long id) {
                switch (gridViewString[+i]) {

                    case "Communication":
                        Intent communication = new Intent(getApplicationContext(), CommunicationContainer.class);
                        communication.putExtras(b);
                        startActivity(communication);
                        break;
                    case "Members":
                        Intent member = new Intent(getApplicationContext(), MemberContainer.class);
                        member.putExtras(b);
                        startActivity(member);
                        break;
                    case "Vehicle":
                        Intent vehicle = new Intent(getApplicationContext(), VehicleContainer.class);
                        vehicle.putExtras(b);
                        startActivity(vehicle);
                        break;
                    case "Product":
                        Intent product = new Intent(getApplicationContext(), GroupProductContainer.class);
                        product.putExtras(b);
                        startActivity(product);
                        break;
                    case "Service":
                        Intent service = new Intent(getApplicationContext(), GroupServiceContainer.class);
                        service.putExtras(b);
                        startActivity(service);
                        break;
                    case "Video's":
                        Intent videos = new Intent(getApplicationContext(), VideosViewActivity.class);
                        videos.putExtras(b);
                        startActivity(videos);
                        break;
                    case "Image's":
                        Intent images = new Intent(getApplicationContext(), ImagesViewActivity.class);
                        images.putExtras(b);
                        startActivity(images);
                        break;
                }
            }
        });

        mShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareOther();
            }
        });
    }

    private void getGroupData(int mGroupID) {
        ApiCall mApiCall = new ApiCall(this, this);
        pDialog.show();
    }

/*
    private void sharePopUp() {

        PopupMenu mPopupMenu = new PopupMenu(GroupsActivity.this, mShare);
        mPopupMenu.getMenuInflater().inflate(R.menu.more_menu, mPopupMenu.getMenu());
        mPopupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                switch (item.getItemId()) {
                    case R.id.autokatta:
*/
/*
                        String allPostDetails = decodedString + "=" +
                                notificationList.get(mPostHolder.getAdapterPosition()).getStatusType() + "=" +
                                notificationList.get(mPostHolder.getAdapterPosition()).getStatusImages() + "=" +
                                notificationList.get(mPostHolder.getAdapterPosition()).getStatusVideos();

                        mActivity.getSharedPreferences(mActivity.getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
                                putString("Share_sharedata", allPostDetails).apply();
                        mActivity.getSharedPreferences(mActivity.getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
                                putInt("Share_status_id", notificationList.get(mPostHolder.getAdapterPosition()).getStatusID()).apply();
                        mActivity.getSharedPreferences(mActivity.getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
                                putString("Share_keyword", "poststatus").apply();


                        Intent i = new Intent(getApplicationContext(), ShareWithinAppActivity.class);
                        startActivity(i);*//*

                        break;
                    case R.id.other:
                        */
/*String linkDetails = "Search Category : " + mSearchHolder.mSearchCategory.getText().toString() + "\n" +
                                "Search Brand : " + mSearchHolder.mSearchBrand.getText().toString() + "\n" +
                                "Search Model : " + mSearchHolder.mSearchModel.getText().toString() + "\n" +
                                "Year Of Mfg : " + mSearchHolder.mSearchYear.getText().toString() + "\n" +
                                "Price : " + mSearchHolder.mSearchPrice.getText().toString() + "\n" +
                                "Leads : " + mSearchHolder.mSearchLeads.getText().toString() + "\n" +
                                "Date : " + mSearchHolder.mSearchDate.getText().toString();*//*


                        //code to encode the status string(posting)
                        byte[] data = new byte[0], data1 = data = new byte[0];
                        try {
                            data = String.valueOf(mGroupID).getBytes("UTF-8");
                            data1 = mLoginContact.getBytes("UTF-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        String encodedGroupId = Base64.encodeBytes(data);
                        String encodedContact = Base64.encodeBytes(data1);

                        intent.setType("text/plain");
                        intent.putExtra(Intent.EXTRA_TEXT, "Please visit the link to join my group in Autokatta"
                                        + "\n" + "http://autokatta.com/group/main/" + encodedGroupId + "/" + encodedContact
                                */
/*+ "\n" + "\n" + allSearchDetails*//*
);
                        //intent.putExtra(Intent.EXTRA_TEXT, allSearchDetailss);
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        intent.putExtra(Intent.EXTRA_SUBJECT, "Group Invitation from Autokatta User");
                        startActivity(Intent.createChooser(intent, "Autokatta"));
                        break;
                }
                return false;
            }
        });
        mPopupMenu.show();
    }
*/

    private void shareOther() {

        Intent intent = new Intent(Intent.ACTION_SEND);
        byte[] data = new byte[0], data1 = data = new byte[0];
        try {
            data = String.valueOf(mGroupID).getBytes("UTF-8");
            data1 = mLoginContact.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String encodedGroupId = Base64.encodeBytes(data);
        String encodedContact = Base64.encodeBytes(data1);

        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, "Please visit the link to join my group in Autokatta"
                        + "\n" + "http://autokatta.com/group/main/" + encodedGroupId + "/" + encodedContact
                                /*+ "\n" + "\n" + allSearchDetails*/);
        //intent.putExtra(Intent.EXTRA_TEXT, allSearchDetailss);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.putExtra(Intent.EXTRA_SUBJECT, "Group Invitation from Autokatta User");
        startActivity(Intent.createChooser(intent, "Autokatta"));
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
        int fragments = getSupportFragmentManager().getBackStackEntryCount();
        if (fragments == 1) {
            finish();
            overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
        } else {
            if (getFragmentManager().getBackStackEntryCount() > 1) {
                getFragmentManager().popBackStack();
            } else {
                super.onBackPressed();
            }
        }
    }

    @Override
    public void notifySuccess(Response<?> response) {

    }

    @Override
    public void notifyError(Throwable error) {
        if (pDialog.isShowing()) {
            pDialog.dismiss();
        }
        if (error instanceof SocketTimeoutException) {
            CustomToast.customToast(getApplicationContext(), getString(R.string._404));
        } else if (error instanceof NullPointerException) {
            // CustomToast.customToast(getApplicationContext(), getString(R.string.no_response));
        } else if (error instanceof ClassCastException) {
            //CustomToast.customToast(getApplicationContext(), getString(R.string.no_response));
        } else if (error instanceof ConnectException) {
            CustomToast.customToast(getApplicationContext(), getString(R.string.no_internet));
        } else if (error instanceof UnknownHostException) {
            CustomToast.customToast(getApplicationContext(), getString(R.string.no_internet));
        } else {
            Log.i("Check Class-"
                    , "GroupsActivity");
            error.printStackTrace();
        }
    }

    @Override
    public void notifyString(String str) {

    }

    private class CustomGridViewActivity extends BaseAdapter {
        private Context mContext;
        private final String[] gridViewString;
        private final int[] gridViewImageId;

        private CustomGridViewActivity(Context context, String[] gridViewString, int[] gridViewImageId) {
            mContext = context;
            this.gridViewImageId = gridViewImageId;
            this.gridViewString = gridViewString;
        }

        @Override
        public int getCount() {
            return gridViewString.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup parent) {
            View gridViewAndroid;
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (convertView == null) {
                gridViewAndroid = new View(mContext);
                if (inflater != null) {
                    gridViewAndroid = inflater.inflate(R.layout.gridview_layout, null);
                }
                TextView textViewAndroid = (TextView) gridViewAndroid.findViewById(R.id.android_gridview_text);
                ImageView imageViewAndroid = (ImageView) gridViewAndroid.findViewById(R.id.android_gridview_image);
                textViewAndroid.setText(gridViewString[i]);
                imageViewAndroid.setImageResource(gridViewImageId[i]);
            } else {
                gridViewAndroid = (View) convertView;
            }
            return gridViewAndroid;
        }
    }
}