package autokatta.com.upload_vehicle;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import autokatta.com.R;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.view.VehicleDetails;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ak-003 on 27/3/17.
 */

public class VehiclePrivacy extends Fragment implements View.OnClickListener, RequestNotifier {

    String myContact, vehicle_id, GroupIds = "", StoreIds = "", GroupName = "", StoreName = "";
    View mVehiclePrivacy;
    ListView storelistView, grouplistView;
    Button ok, cancel;
    String[] arrGroupIds, arrGroupTitle, arrStoreIds, arrStoreTitle;
    ArrayList<String> groupIdlist, groupTitlelist, storeIdlist, storeTitlelist;

    CheckedGrouptAdapter adpgroupAdapter;
    CheckedStoreAdapter adpstoreAdapter;

    public VehiclePrivacy() {
        //empty constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mVehiclePrivacy = inflater.inflate(R.layout.fragment_vehicle_privacy, container, false);

        myContact = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", "7841023392");

        Bundle b = getArguments();
        vehicle_id = b.getString("vehicle_id");


        GroupIds = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("upload_GroupIds", null);
        StoreIds = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("upload_StoreIds", null);
        GroupName = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("upload_GroupNames", null);
        StoreName = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("upload_StoreNames", null);


        storelistView = (ListView) mVehiclePrivacy.findViewById(R.id.storelist);
        grouplistView = (ListView) mVehiclePrivacy.findViewById(R.id.grouplist);
        ok = (Button) mVehiclePrivacy.findViewById(R.id.ok);
        cancel = (Button) mVehiclePrivacy.findViewById(R.id.cancel);
        groupIdlist = new ArrayList<>();
        groupTitlelist = new ArrayList<>();
        storeIdlist = new ArrayList<>();
        storeTitlelist = new ArrayList<>();


        arrGroupIds = GroupIds.split(",");
        arrGroupTitle = GroupName.split(",");
        arrStoreIds = StoreIds.split(",");
        arrStoreTitle = StoreName.split(",");


        groupIdlist.clear();
        groupTitlelist.clear();
        storeIdlist.clear();
        storeTitlelist.clear();

        for (int i = 0; i < arrGroupIds.length; i++) {
            groupIdlist.add(arrGroupIds[i]);
        }
        for (int j = 0; j < arrGroupTitle.length; j++) {
            groupTitlelist.add(arrGroupTitle[j]);
        }
        for (int k = 0; k < arrStoreIds.length; k++) {
            storeIdlist.add(arrStoreIds[k]);
        }
        for (int l = 0; l < arrStoreTitle.length; l++) {
            storeTitlelist.add(arrStoreTitle[l]);
        }


        adpgroupAdapter = new CheckedGrouptAdapter(getActivity(), groupIdlist, groupTitlelist);
        grouplistView.setAdapter(adpgroupAdapter);

        adpstoreAdapter = new CheckedStoreAdapter(getActivity(), storeIdlist, storeTitlelist);
        storelistView.setAdapter(adpstoreAdapter);

        ok.setOnClickListener(this);
        cancel.setOnClickListener(this);


        return mVehiclePrivacy;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.ok:
                if (!(groupIdlist.size() == 0) || !(storeIdlist.size() == 0)) {

                    GroupIds = "";
                    StoreIds = "";
                    if (!(groupIdlist.size() == 0)) {
                        for (int i = 0; i < groupIdlist.size(); i++) {

                            if (GroupIds.equals(""))
                                GroupIds = groupIdlist.get(i);
                            else
                                GroupIds = GroupIds + "," + groupIdlist.get(i);
                        }
                    }
                    if (!(storeIdlist.size() == 0)) {

                        for (int i = 0; i < storeIdlist.size(); i++) {
                            if (StoreIds.equals(""))
                                StoreIds = storeIdlist.get(i);
                            else
                                StoreIds = StoreIds + "," + storeIdlist.get(i);

                        }

                    }
                    setPrivacy(GroupIds, StoreIds);

                } else {
                    Intent intent = new Intent(getActivity(), VehicleDetails.class);
                    intent.putExtra("vehicle_id", vehicle_id);
                    getActivity().startActivity(intent);

                }
                break;

            case R.id.cancel:
                Intent intent = new Intent(getActivity(), VehicleDetails.class);
                intent.putExtra("vehicle_id", vehicle_id);
                getActivity().startActivity(intent);
                break;
        }
    }

    private void setPrivacy(String groupIds, String storeIds) {
        ApiCall apiCall = new ApiCall(getActivity(), this);
        apiCall.VehiclePrivacy(myContact, vehicle_id, groupIds, storeIds);
    }

    @Override
    public void notifySuccess(Response<?> response) {

    }

    @Override
    public void notifyError(Throwable error) {

    }

    @Override
    public void notifyString(String str) {
        if (str != null) {
            if (str.equals("success")) {
                Intent intent = new Intent(getActivity(), VehicleDetails.class);
                intent.putExtra("vehicle_id", vehicle_id);
                getActivity().startActivity(intent);
            }
        }

    }

    private class CheckedGrouptAdapter extends BaseAdapter {

        private LayoutInflater mInflater;
        Activity activity;
        ArrayList<String> id = new ArrayList<>();
        ArrayList<String> titles = new ArrayList<>();


        public CheckedGrouptAdapter(Activity a, ArrayList<String> id, ArrayList<String> titles) {
//

            this.activity = a;
            this.id = id;
            this.titles = titles;
//


            // mInflater = LayoutInflater.from(context);
            mInflater = (LayoutInflater) activity.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public int getCount() {
            return id.size();
        }

        public Object getItem(int position) {
            return id.get(position);
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
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();

            }


            holder.text.setText(titles.get(position));

            holder.remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    titles.remove(position);
                    id.remove(position);
                    adpgroupAdapter.notifyDataSetChanged();


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


    }

    private class CheckedStoreAdapter extends BaseAdapter {

        private LayoutInflater mInflater;
        Activity activity;
        ArrayList<String> id = new ArrayList<>();
        ArrayList<String> titles = new ArrayList<>();


        public CheckedStoreAdapter(Activity a, ArrayList<String> id, ArrayList<String> titles) {
//

            this.activity = a;
            this.id = id;
            this.titles = titles;
//


            // mInflater = LayoutInflater.from(context);
            mInflater = (LayoutInflater) activity.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public int getCount() {
            return id.size();
        }

        public Object getItem(int position) {
            return id.get(position);
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
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();

            }


            holder.text.setText(titles.get(position));

            holder.remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    titles.remove(position);
                    id.remove(position);
                    adpstoreAdapter.notifyDataSetChanged();


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
}
