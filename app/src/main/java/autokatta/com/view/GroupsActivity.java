package autokatta.com.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import autokatta.com.R;
import autokatta.com.StoreVideosActivity;
import autokatta.com.groups_container.CommunicationContainer;
import autokatta.com.groups_container.GroupProductContainer;
import autokatta.com.groups_container.GroupServiceContainer;
import autokatta.com.groups_container.MemberContainer;
import autokatta.com.groups_container.VehicleContainer;

public class GroupsActivity extends AppCompatActivity {
    Bundle b = new Bundle();
    String className;
    GridView androidGridView;

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
        setTitle("My Group");
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        Intent i = getIntent();
        String grouptype = i.getStringExtra("grouptype");
        className = i.getStringExtra("className");
        if (grouptype != null) {
            b.putString("grouptype", i.getStringExtra("grouptype"));
            b.putString("className", i.getStringExtra("className"));
            b.putInt("bundle_GroupId", i.getIntExtra("bundle_GroupId", 0));
            b.putString("bundle_GroupName", i.getStringExtra("bundle_GroupName"));
            b.putString("tabIndex", i.getStringExtra("tabIndex"));
            b.putString("bundle_Contact", i.getStringExtra("bundle_Contact"));
        }

        GroupsActivity.CustomGridViewActivity adapterViewAndroid = new GroupsActivity.CustomGridViewActivity(GroupsActivity.this, gridViewString, gridViewImageId);
        androidGridView = (GridView) findViewById(R.id.group_grid_view);
        androidGridView.setAdapter(adapterViewAndroid);

        androidGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int i, long id) {
                if (gridViewString[+i].equals("Communication")) {
                    Intent communication = new Intent(getApplicationContext(), CommunicationContainer.class);
                    communication.putExtras(b);
                    startActivity(communication);
                } else if (gridViewString[+i].equals("Members")) {
                    Intent member = new Intent(getApplicationContext(), MemberContainer.class);
                    member.putExtras(b);
                    startActivity(member);
                } else if (gridViewString[+i].equals("Vehicle")) {
                    Intent vehicle = new Intent(getApplicationContext(), VehicleContainer.class);
                    vehicle.putExtras(b);
                    startActivity(vehicle);
                } else if (gridViewString[+i].equals("Product")) {
                    Intent product = new Intent(getApplicationContext(), GroupProductContainer.class);
                    product.putExtras(b);
                    startActivity(product);
                } else if (gridViewString[+i].equals("Service")) {
                    Intent service = new Intent(getApplicationContext(), GroupServiceContainer.class);
                    service.putExtras(b);
                    startActivity(service);
                } else if (gridViewString[+i].equals("Video's")) {
                    Intent videos = new Intent(getApplicationContext(), StoreVideosActivity.class);
                    videos.putExtras(b);
                    startActivity(videos);
                } else if (gridViewString[+i].equals("Image's")) {
                    Intent images = new Intent(getApplicationContext(), AndroidGridViewDisplayImages.class);
                    startActivity(images);
                }
            }
        });

        /*GroupDetailTabs groupDetailTabs = new GroupDetailTabs();
        Intent i = getIntent();
        String grouptype = i.getStringExtra("grouptype");
        className = i.getStringExtra("className");
        if (grouptype != null) {
            b.putString("grouptype", i.getStringExtra("grouptype"));
            b.putString("className", i.getStringExtra("className"));
            b.putInt("bundle_GroupId", i.getIntExtra("bundle_GroupId", 0));
            b.putString("bundle_GroupName", i.getStringExtra("bundle_GroupName"));
            b.putString("tabIndex", i.getStringExtra("tabIndex"));
            b.putString("bundle_Contact", i.getStringExtra("bundle_Contact"));
            groupDetailTabs.setArguments(b);
        }
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.profile_groups_container, groupDetailTabs, "GroupActivity")
                .addToBackStack("GroupActivity")
                .commit();*/
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
                gridViewAndroid = inflater.inflate(R.layout.gridview_layout, null);
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