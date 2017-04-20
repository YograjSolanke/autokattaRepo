package autokatta.com.search;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import autokatta.com.R;
import autokatta.com.adapter.MyStoreListAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import autokatta.com.response.MyStoreResponse;
import retrofit2.Response;

/**
 * Created by ak-001 on 19/4/17.
 */

public class SearchStore extends Fragment implements RequestNotifier {
    View mSearchStore;
    RecyclerView searchList;

    String searchString;
    private List<MyStoreResponse.Success> allSearchDataArrayList = new ArrayList<>();
    private List<MyStoreResponse.Success> allSearchDataArrayList_new = new ArrayList<>();
    HashSet<String> categoryHashSet;
    String myContact;
    ImageView filterImg;
    MyStoreListAdapter adapter;
    CheckedCategoryAdapter categoryAdapter;
    CheckedLocationAdapter locationAdapter;
    ArrayList<String> categoryList = new ArrayList<>();
    ArrayList<String> LocationList = new ArrayList<>();
    HashSet<String> locationHashSet;
    ArrayList<String> finalcategory = new ArrayList<>();
    ArrayList<String> finallocation = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mSearchStore = inflater.inflate(R.layout.fragment_search_store_list, container, false);
        searchList = (RecyclerView) mSearchStore.findViewById(R.id.searchlist);
        filterImg = (ImageView) mSearchStore.findViewById(R.id.filterimg);

        searchList.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        searchList.setLayoutManager(mLayoutManager);

        myContact = getActivity().getSharedPreferences(getString(R.string.my_preference), Context.MODE_PRIVATE)
                .getString("loginContact", "");

        try {
            Bundle bundle = getArguments();
            searchString = bundle.getString("searchText");
            System.out.println("SearchString" + searchString);
            getSearchResults(searchString);
        } catch (Exception e) {
            e.printStackTrace();
        }

        filterImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // filterData(categoryHashSet.toArray(new String[categoryHashSet.size()]));
                filterResult(categoryHashSet.toArray(new String[categoryHashSet.size()]),
                        locationHashSet.toArray(new String[locationHashSet.size()]));
            }
        });
        return mSearchStore;
    }

    /*
    get Store Search Results...
     */
    private void getSearchResults(String searchString) {
        ApiCall mApiCall = new ApiCall(getActivity(), this);
        mApiCall.searchStore(searchString, getActivity().getSharedPreferences(getString(R.string.my_preference),
                Context.MODE_PRIVATE).getString("loginContact", ""));
    }

    @Override
    public void notifySuccess(Response<?> response) {
        if (response != null) {
            if (response.isSuccessful()) {
                MyStoreResponse searchData = (MyStoreResponse) response.body();
                if (!searchData.getSuccess().isEmpty()) {
                    for (MyStoreResponse.Success success : searchData.getSuccess()) {
                        success.setId(success.getId());
                        success.setId(success.getContact());
                        success.setId(success.getName());
                        success.setId(success.getLocation());
                        success.setId(success.getStoreImage());
                        success.setId(success.getStoreType());
                        success.setId(success.getWebsite());
                        success.setId(success.getStoreOpenTime());
                        success.setId(success.getStoreCloseTime());
                        success.setId(success.getWorkingDays());
                        success.setId(success.getCategory());
                        success.setId(success.getCoverImage());
                        success.setId(success.getRating());
                        success.setId(success.getLikecount());
                        success.setId(success.getFollowcount());

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

                        allSearchDataArrayList.add(success);
                    }
                    categoryHashSet = new HashSet<>(categoryList);
                    locationHashSet = new HashSet<>(LocationList);
                    adapter = new MyStoreListAdapter(getActivity(), allSearchDataArrayList);
                    searchList.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

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

    }

    @Override
    public void notifyString(String str) {

    }

    public void filterResult(final String[] incomingCategory, final String[] incomingLocation) {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View convertView = inflater.inflate(R.layout.custom_store, null);
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
                            allSearchDataArrayList.get(i).visibility = true;
                        else {
                            if (finallocation.contains(allSearchDataArrayList.get(i).getLocation().trim()))
                                allSearchDataArrayList.get(i).visibility = true;
                            else
                                allSearchDataArrayList.get(i).visibility = false;
                        }
                    } else {
                        if (finalcategory.contains(allSearchDataArrayList.get(i).getCategory().trim()))
                            allSearchDataArrayList.get(i).visibility = true;
                        else {
                            if (finallocation.contains(allSearchDataArrayList.get(i).getLocation().trim()))
                                allSearchDataArrayList.get(i).visibility = true;
                            else
                                allSearchDataArrayList.get(i).visibility = false;
                        }
                    }
                }

                allSearchDataArrayList_new = new ArrayList<>();
                allSearchDataArrayList_new.clear();

                for (int w = 0; w < allSearchDataArrayList.size(); w++) {
                    if (allSearchDataArrayList.get(w).visibility) {
                        allSearchDataArrayList_new.add(allSearchDataArrayList.get(w));
                    }
                }
                alert.dismiss();

                adapter = new MyStoreListAdapter(getActivity(), allSearchDataArrayList_new);
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

    public class CheckedCategoryAdapter extends BaseAdapter {

        private LayoutInflater mInflater;
        Activity activity;

        ArrayList<String> titles = new ArrayList<>();


        public CheckedCategoryAdapter(Activity a, String titles[]) {
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

    static class ViewHolder1 {
        TextView text;
        CheckBox checkBox;
    }

    public class CheckedLocationAdapter extends BaseAdapter {

        private LayoutInflater mInflater;
        Activity activity;
        ArrayList<String> titles = new ArrayList<>();

        public CheckedLocationAdapter(Activity a, String titles[]) {
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
}
