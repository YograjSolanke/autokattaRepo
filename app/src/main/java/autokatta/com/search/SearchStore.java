package autokatta.com.search;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import autokatta.com.R;
import autokatta.com.adapter.BrowseStoreAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.networkreceiver.ConnectionDetector;
import autokatta.com.other.CustomToast;
import autokatta.com.response.BrowseStoreResponse;
import retrofit2.Response;

/**
 * Created by ak-001 on 19/4/17.
 */

public class SearchStore extends Fragment implements RequestNotifier {
    View mSearchStore;
    RecyclerView searchList;

    String searchString;
    private List<BrowseStoreResponse.Success> allSearchDataArrayList = new ArrayList<>();
    private List<BrowseStoreResponse.Success> allSearchDataArrayList_new = new ArrayList<>();
    HashSet<String> categoryHashSet;
    ImageView filterImg;
    BrowseStoreAdapter adapter;
    boolean hasViewCreated = false;
    TextView mNoData;
    CheckedCategoryAdapter categoryAdapter;
    CheckedLocationAdapter locationAdapter;
    List<String> categoryList = new ArrayList<>();
    List<String> LocationList = new ArrayList<>();
    HashSet<String> locationHashSet;
    List<String> finalcategory = new ArrayList<>();
    List<String> finallocation = new ArrayList<>();
    ConnectionDetector mConnectionDetector;
    private ProgressDialog dialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mSearchStore = inflater.inflate(R.layout.fragment_search_store_list, container, false);
        return mSearchStore;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mNoData = (TextView) mSearchStore.findViewById(R.id.no_category);
        //mNoData.setVisibility(View.GONE);

        searchList = (RecyclerView) mSearchStore.findViewById(R.id.searchlist);
        filterImg = (ImageView) mSearchStore.findViewById(R.id.filterimg);
        filterImg.setVisibility(View.GONE);

        searchList.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        searchList.setLayoutManager(mLayoutManager);

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    dialog = new ProgressDialog(getActivity());
                    dialog.setMessage("Loading...");

                    mConnectionDetector = new ConnectionDetector(getActivity());
                    Bundle bundle = getArguments();
                    if (bundle != null) {
                        searchString = bundle.getString("searchText1");
                        System.out.println("Store" + searchString);
                        getSearchResults(searchString);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        filterImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // filterData(categoryHashSet.toArray(new String[categoryHashSet.size()]));
                filterResult(categoryHashSet.toArray(new String[categoryHashSet.size()]),
                        locationHashSet.toArray(new String[locationHashSet.size()]));
            }
        });
    }

    /*
    get Store Search Results...
     */
    private void getSearchResults(String searchString) {

        if (mConnectionDetector.isConnectedToInternet()) {
            dialog.show();
            ApiCall mApiCall = new ApiCall(getActivity(), this);
            mApiCall.searchStore(searchString, getActivity().getSharedPreferences(getString(R.string.my_preference),
                    Context.MODE_PRIVATE).getString("loginContact", ""));
        } else {
            CustomToast.customToast(getActivity(), getString(R.string.no_internet));
        }
    }

    @Override
    public void notifySuccess(Response<?> response) {
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
        if (response != null) {
            if (response.isSuccessful()) {
                BrowseStoreResponse searchData = (BrowseStoreResponse) response.body();
                if (!searchData.getSuccess().isEmpty()) {
                    mNoData.setVisibility(View.GONE);
                    allSearchDataArrayList.clear();
                    filterImg.setVisibility(View.VISIBLE);
                    for (BrowseStoreResponse.Success success : searchData.getSuccess()) {
                        success.setStoreId(success.getStoreId());
                        success.setContactNo(success.getContactNo());
                        success.setStoreName(success.getStoreName());
                        success.setLocation(success.getLocation());
                        success.setStoreImage(success.getStoreImage());
                        success.setStoreType(success.getStoreType());
                        success.setWebsite(success.getWebsite());
                        success.setWorkingDays(success.getWorkingDays());
                        success.setCategory(success.getCategory());
                        success.setRating(success.getRating());
                        success.setLikecount(success.getLikecount());
                        success.setLikestatus(success.getLikestatus());
                        success.setFollowcount(success.getFollowcount());
                        success.setFollowstatus(success.getFollowstatus());

                        if (success.getCategory().trim().contains(",")) {
                            String arr[] = success.getCategory().trim().split(",");
                            for (int l = 0; l < arr.length; l++) {
                                String part = arr[l].trim();
                                if (!part.equals(" ") && !part.equals(""))
                                    categoryList.add(part);
                            }
                        } else {
                            categoryList.add(success.getCategory().trim());
                        }

                        if (!LocationList.contains(success.getLocation()))
                            LocationList.add(success.getLocation());
                        Log.i("location", "->" + success.getLocation());

                        allSearchDataArrayList.add(success);
                    }
                    categoryHashSet = new HashSet<>(categoryList);
                    locationHashSet = new HashSet<>(LocationList);
                    adapter = new BrowseStoreAdapter(getActivity(), allSearchDataArrayList);
                    searchList.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                } else {
                    mNoData.setVisibility(View.VISIBLE);
                    filterImg.setVisibility(View.GONE);
                }
            } else {
                CustomToast.customToast(getActivity(), getString(R.string._404));
            }
        } else {
            CustomToast.customToast(getActivity(), getString(R.string.no_response));
        }
    }

    @Override
    public void notifyError(Throwable error) {
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
        if (error instanceof SocketTimeoutException) {
            CustomToast.customToast(getActivity(), getString(R.string._404));
        } else if (error instanceof NullPointerException) {
            CustomToast.customToast(getActivity(), getString(R.string.no_response));
        } else if (error instanceof ClassCastException) {
            CustomToast.customToast(getActivity(), getString(R.string.no_response));
        } else if (error instanceof ConnectException) {
            CustomToast.customToast(getActivity(), getString(R.string.no_internet));
        } else if (error instanceof UnknownHostException) {
            CustomToast.customToast(getActivity(), getString(R.string.no_internet));
        } else {
            Log.i("Check Class-", "SearchStore Fragment");
        }


    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (this.isVisible()) {
            if (isVisibleToUser && !hasViewCreated) {
                Bundle bundle = getArguments();
                if (bundle != null) {
                    searchString = bundle.getString("searchText1");
                    System.out.println("Store" + searchString);
                    getSearchResults(searchString);
                }
                hasViewCreated = true;
            }
        }
    }


    @Override
    public void notifyString(String str) {

    }

    public void filterResult(final String[] incomingCategory, final String[] incomingLocation) {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View convertView = inflater.inflate(R.layout.custom_store_1, null);
        alertDialog.setView(convertView);
        final AlertDialog alert = alertDialog.show();
        alertDialog.setTitle("Select option to filter data");
        ListView lvcat = (ListView) convertView.findViewById(R.id.listview1);
        ListView lvlocation = (ListView) convertView.findViewById(R.id.listview2);
        Button Ok = (Button) convertView.findViewById(R.id.btnok);
        Button cancel = (Button) convertView.findViewById(R.id.btncancel);
        if (incomingCategory.length != 0) {
            categoryAdapter = new CheckedCategoryAdapter(getActivity(), incomingCategory);
            lvcat.setAdapter(categoryAdapter);
        }
        if (incomingLocation.length != 0) {
            locationAdapter = new CheckedLocationAdapter(getActivity(), incomingLocation);
            lvlocation.setAdapter(locationAdapter);
        }

        Ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchList.setAdapter(null);
                for (int i = 0; i < allSearchDataArrayList.size(); i++) {
                    if (allSearchDataArrayList.get(i).getCategory().trim().contains(",")) {
                        boolean flag = false;
                        String arr[] = allSearchDataArrayList.get(i).getCategory().trim().split(",");
                        for (int r = 0; r < arr.length; r++) {
                            if (finalcategory.contains(arr[r].trim()))
                                flag = true;
                        }

                        if (flag)
                            allSearchDataArrayList.get(i).setVisibility(true);
                        else {
                            if (finallocation.contains(allSearchDataArrayList.get(i).getLocation().trim()))
                                allSearchDataArrayList.get(i).setVisibility(true);
                            else
                                allSearchDataArrayList.get(i).setVisibility(false);
                        }
                    } else {
                        if (finalcategory.contains(allSearchDataArrayList.get(i).getCategory().trim()))
                            allSearchDataArrayList.get(i).setVisibility(true);
                        else {
                            if (finallocation.contains(allSearchDataArrayList.get(i).getLocation().trim()))
                                allSearchDataArrayList.get(i).setVisibility(true);
                            else
                                allSearchDataArrayList.get(i).setVisibility(false);
                        }
                    }
                }

                allSearchDataArrayList_new = new ArrayList<>();
                allSearchDataArrayList_new.clear();

                for (int w = 0; w < allSearchDataArrayList.size(); w++) {
                    if (allSearchDataArrayList.get(w).isVisibility()) {
                        allSearchDataArrayList_new.add(allSearchDataArrayList.get(w));
                    }
                }
                alert.dismiss();

                adapter = new BrowseStoreAdapter(getActivity(), allSearchDataArrayList_new);
                adapter.notifyDataSetChanged();
                searchList.setAdapter(adapter);
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();

            }
        });
        convertView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
    }

    static class ViewHolder {
        TextView text;
        CheckBox checkBox;
    }

    private class CheckedCategoryAdapter extends BaseAdapter {

        private LayoutInflater mInflater;
        Activity activity;
        List<String> titles = new ArrayList<>();

        CheckedCategoryAdapter(Activity a, String titles[]) {
            this.activity = a;

            this.titles = new ArrayList<>(Arrays.asList(titles));

            if (finalcategory.size() == 0) {
                for (int i = 0; i < this.titles.size(); i++) {

                    finalcategory.add(this.titles.get(i));
                }
            }
            mInflater = (LayoutInflater) activity.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public int getCount() {
            return titles.size();
        }

        public Object getItem(int position) {
            return titles.get(position);
        }

        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getViewTypeCount() {

            return getCount();
        }

        @Override
        public int getItemViewType(int position) {

            return position;
        }

        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;
            final View cv = convertView;

            if (convertView == null) {
                holder = new ViewHolder();

                convertView = mInflater.inflate(R.layout.checked_category_adapter, null);
                holder.text = (TextView) convertView.findViewById(R.id.txtname);
                holder.checkBox = (CheckBox) convertView.findViewById(R.id.checkbox);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.text.setText(titles.get(position));
            if (!finalcategory.get(position).equalsIgnoreCase("0"))
                holder.checkBox.setChecked(true);

            holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    if (isChecked) {
                        finalcategory.set(position, holder.text.getText().toString());
                    } else {
                        finalcategory.set(position, "0");
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

    private static class ViewHolder1 {
        TextView text;
        CheckBox checkBox;
    }

    private class CheckedLocationAdapter extends BaseAdapter {

        private LayoutInflater mInflater;
        Activity activity;
        List<String> titles = new ArrayList<>();

        CheckedLocationAdapter(Activity a, String titles[]) {
            this.activity = a;
            this.titles = new ArrayList<>(Arrays.asList(titles));
            if (finallocation.size() == 0) {
                for (int i = 0; i < this.titles.size(); i++) {
                    finallocation.add(this.titles.get(i));
                }
            }

            // mInflater = LayoutInflater.from(context);
            mInflater = (LayoutInflater) activity.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public int getCount() {
            return titles.size();
        }

        public Object getItem(int position) {
            return titles.get(position);
        }

        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getViewTypeCount() {
            return getCount();
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }

        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder1 holder;
            final View cv = convertView;
            if (convertView == null) {
                holder = new ViewHolder1();
                convertView = mInflater.inflate(R.layout.checked_category_adapter, null);
                holder.text = (TextView) convertView.findViewById(R.id.txtname);
                holder.checkBox = (CheckBox) convertView.findViewById(R.id.checkbox);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder1) convertView.getTag();
            }

            holder.text.setText(titles.get(position));
            if (!finallocation.get(position).equalsIgnoreCase("0"))
                holder.checkBox.setChecked(true);

            holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        finallocation.set(position, holder.text.getText().toString());
                    } else {
                        finallocation.set(position, "0");
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

    @Override
    public void onResume() {
        super.onResume();
        getSearchResults(searchString);
    }
}
