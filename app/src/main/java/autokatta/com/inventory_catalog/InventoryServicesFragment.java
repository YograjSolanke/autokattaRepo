package autokatta.com.inventory_catalog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
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
import autokatta.com.adapter.StoreServiceAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.networkreceiver.ConnectionDetector;
import autokatta.com.other.CustomToast;
import autokatta.com.response.StoreInventoryResponse;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ak-003 on 7/6/17.
 */

public class InventoryServicesFragment extends Fragment implements RequestNotifier, SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {

    View mService;
    String Sharedcontact, storeContact;
    SwipeRefreshLayout mSwipeRefreshLayout;
    RecyclerView mRecyclerView;
    ImageView filterImg;
    TextView titleText, mNoData;
    List<StoreInventoryResponse.Success.Service> serviceList;
    List<StoreInventoryResponse.Success.Service> serviceList_new;
    LinearLayoutManager mLayoutManager;
    StoreServiceAdapter adapter;
    boolean hasView = false;
    ConnectionDetector mTestConnection;
    ArrayList<String> categoryList = new ArrayList<>();
    HashSet<String> categoryHashSet;
    CheckedCategoryAdapter categoryAdapter;
    ArrayList<String> finalcategory = new ArrayList<>();

    int counter = 0;


    public InventoryServicesFragment() {
        //empty constructor...
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mService = inflater.inflate(R.layout.store_product_fragment, container, false);
        return mService;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTestConnection = new ConnectionDetector(getActivity());
                Sharedcontact = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", "");
                mSwipeRefreshLayout = (SwipeRefreshLayout) mService.findViewById(R.id.swipeRefreshLayout);
                mRecyclerView = (RecyclerView) mService.findViewById(R.id.recycler_view);
                //titleText = (TextView) mService.findViewById(R.id.titleText);
                mNoData = (TextView) mService.findViewById(R.id.no_category);
                filterImg = (ImageView) mService.findViewById(R.id.filterimg);
                mNoData.setVisibility(View.GONE);
                //titleText.setText("Services");
                mRecyclerView.setHasFixedSize(true);
                mLayoutManager = new LinearLayoutManager(getActivity());
                mLayoutManager.setReverseLayout(true);
                mLayoutManager.setStackFromEnd(true);
                mRecyclerView.setLayoutManager(mLayoutManager);

                mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                        android.R.color.holo_green_light,
                        android.R.color.holo_orange_light,
                        android.R.color.holo_red_light);
                mSwipeRefreshLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        mSwipeRefreshLayout.setRefreshing(true);
//                        getInventoryService(Sharedcontact);
                    }
                });
            }
        });
        mSwipeRefreshLayout.setOnRefreshListener(this);
        filterImg.setOnClickListener(this);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (this.isVisible()) {
            if (isVisibleToUser && !hasView) {
                getInventoryService(Sharedcontact);
                hasView = true;
            }
        }
    }

    private void getInventoryService(String sharedcontact) {
        if (mTestConnection.isConnectedToInternet()) {
            ApiCall apiCall = new ApiCall(getActivity(), this);
            apiCall.getMyInventory_Catalog(sharedcontact);
        } else {
            mSwipeRefreshLayout.setRefreshing(false);
            mNoData.setVisibility(View.GONE);
            Snackbar snackbar = Snackbar.make(getView(), getString(R.string.no_internet), Snackbar.LENGTH_INDEFINITE)
                    .setAction("Go Online", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
                        }
                    });
            // Changing message text color
            snackbar.setActionTextColor(Color.RED);
            // Changing action button text color
            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.YELLOW);
            snackbar.show();
        }
    }

    @Override
    public void onRefresh() {
//        serviceList.clear();
//        getInventoryService(Sharedcontact);
    }


    @Override
    public void notifySuccess(Response<?> response) {
        if (response != null) {
            if (response.isSuccessful()) {
                mSwipeRefreshLayout.setRefreshing(false);
                serviceList = new ArrayList<>();
                StoreInventoryResponse storeResponse = (StoreInventoryResponse) response.body();
                if (!storeResponse.getSuccess().getService().isEmpty()) {
                    mNoData.setVisibility(View.GONE);
                    for (StoreInventoryResponse.Success.Service success : storeResponse.getSuccess().getService()) {
                        success.setServiceId(success.getServiceId());
                        success.setServiceName(success.getServiceName());
                        success.setBrandtags(success.getBrandtags());
                        success.setServicePrice(success.getServicePrice());
                        success.setServiceType(success.getServiceType());
                        success.setServiceDetails(success.getServiceDetails());
                        success.setServicetags(success.getServicetags());
                        success.setServiceImages(success.getServiceImages());
                        success.setServicecategory(success.getServicecategory());
                        success.setServicelikestatus(success.getServicelikestatus());
                        success.setServicelikecount(success.getServicelikecount());
                        success.setServicerating(success.getServicerating());
                        success.setSrate(success.getSrate());
                        success.setSrate1(success.getSrate1());
                        success.setSrate2(success.getSrate2());
                        success.setSrate3(success.getSrate3());
                        serviceList.add(success);


                        if (success.getServicecategory().trim().contains(",")) {
                            String arr[] = success.getServicecategory().trim().split(",");
                            for (int l = 0; l < arr.length; l++) {
                                String part = arr[l].trim();
                                if (!part.equals(" ") && !part.equals(""))
                                    categoryList.add(part);
                            }
                        } else {
                            categoryList.add(success.getServicecategory().trim());
                        }
                    }


                    categoryHashSet = new HashSet<>(categoryList);
                    filterResult(categoryHashSet.toArray(new String[categoryHashSet.size()]));
                    mSwipeRefreshLayout.setRefreshing(false);
//                    adapter = new StoreServiceAdapter(getActivity(), serviceList, Sharedcontact, storeContact);
//                    mRecyclerView.setAdapter(adapter);
//                    adapter.notifyDataSetChanged();
                } else {
                    mSwipeRefreshLayout.setRefreshing(false);
                    mNoData.setVisibility(View.VISIBLE);
                }
            } else {
                mSwipeRefreshLayout.setRefreshing(false);
                Snackbar.make(getView(), getString(R.string._404_), Snackbar.LENGTH_SHORT).show();
            }
        } else {
            mSwipeRefreshLayout.setRefreshing(false);
            Snackbar.make(getView(), getString(R.string.no_response), Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void notifyError(Throwable error) {
        mSwipeRefreshLayout.setRefreshing(false);
        if (error instanceof SocketTimeoutException) {
            Snackbar.make(getView(), getString(R.string._404_), Snackbar.LENGTH_SHORT).show();
        } else if (error instanceof NullPointerException) {
            Snackbar.make(getView(), getString(R.string.no_response), Snackbar.LENGTH_SHORT).show();
        } else if (error instanceof ClassCastException) {
            Snackbar.make(getView(), getString(R.string.no_response), Snackbar.LENGTH_SHORT).show();
        } else if (error instanceof ConnectException) {
            //mNoInternetIcon.setVisibility(View.VISIBLE);
            Snackbar snackbar = Snackbar.make(getView(), getString(R.string.no_internet), Snackbar.LENGTH_INDEFINITE)
                    .setAction("Go Online", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
                        }
                    });
            // Changing message text color
            snackbar.setActionTextColor(Color.RED);
            // Changing action button text color
            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.YELLOW);
            snackbar.show();
        } else if (error instanceof UnknownHostException) {
            //mNoInternetIcon.setVisibility(View.VISIBLE);
            Snackbar snackbar = Snackbar.make(getView(), getString(R.string.no_internet), Snackbar.LENGTH_INDEFINITE)
                    .setAction("Go Online", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
                        }
                    });
            // Changing message text color
            snackbar.setActionTextColor(Color.RED);
            // Changing action button text color
            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.YELLOW);
            snackbar.show();
        } else {
            Log.i("Check Class-"
                    , "StoreServices");
            error.printStackTrace();
        }
    }

    @Override
    public void notifyString(String str) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case (R.id.filterimg):
                filterResult(categoryHashSet.toArray(new String[categoryHashSet.size()]));
                break;
        }
    }

    public void filterResult(final String[] incomingCategory) {
//        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
//        LayoutInflater inflater = getActivity().getLayoutInflater();
//
//        View convertView = inflater.inflate(R.layout.custom_store, null);
//        alertDialog.setView(convertView);


        final Dialog openDialog = new Dialog(getActivity());
        openDialog.setContentView(R.layout.custom_store);
        openDialog.setTitle("Custom Dialog Box");

        ListView lvcat = (ListView) openDialog.findViewById(R.id.listview1);

        Button Ok = (Button) openDialog.findViewById(R.id.btnok);
        Button cancel = (Button) openDialog.findViewById(R.id.btncancel);
        cancel.setVisibility(View.GONE);

        categoryAdapter = new CheckedCategoryAdapter(getActivity(), incomingCategory);
        lvcat.setAdapter(categoryAdapter);

        Ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRecyclerView.setAdapter(null);
                if (counter != 0 && counter <= 5) {
                    for (int i = 0; i < serviceList.size(); i++) {
                        //If Category contains ","
                        if (serviceList.get(i).getServicecategory().trim().contains(",")) {
                            boolean flag = false;
                            String arr[] = serviceList.get(i).getServicecategory().trim().split(",");
                            for (int r = 0; r < arr.length; r++) {
                                if (finalcategory.contains(arr[r].trim()))
                                    flag = true;
                            }

                            if (flag)
                                serviceList.get(i).setVisibility(true);
                            else {
                                serviceList.get(i).setVisibility(false);
                            }
                        } else {
                            if (finalcategory.contains(serviceList.get(i).getServicecategory().trim()))
                                serviceList.get(i).setVisibility(true);
                            else {
                                serviceList.get(i).setVisibility(false);
                            }
                        }
                    }


                    serviceList_new = new ArrayList<>();
                    serviceList_new.clear();
                    for (int w = 0; w < serviceList.size(); w++) {
                        if (serviceList.get(w).isVisibility()) {
                            serviceList_new.add(serviceList.get(w));
                        }
                    }
                    openDialog.dismiss();
                    adapter = new StoreServiceAdapter(getActivity(), serviceList_new, Sharedcontact, storeContact);
                    mRecyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                } else {
                    if (counter == 0) {
                        CustomToast.customToast(getActivity(), "Please Select Atleast One Category");
//                        AlertDialog alert = alertDialog.create();
//                        alert.show();
                    }
                    if (counter > 5) {
                        CustomToast.customToast(getActivity(), "Please Select Only 5 Category");
//                        AlertDialog alert = alertDialog.create();
//                        alert.show();
                    }

                }

            }
        });
        openDialog.show();

//        convertView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                v.getParent().requestDisallowInterceptTouchEvent(true);
//                return false;
//            }
//        });
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
            counter = 0;
            if (finalcategory.size() == 0) {
                for (int i = 0; i < this.titles.size(); i++) {
                    finalcategory.add("0");
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
                        counter++;
                        finalcategory.set(position, holder.text.getText().toString());
                    } else {
                        counter--;
                        finalcategory.set(position, "0");
                    }

                    System.out.println("finalcatery=" + finalcategory.get(position));
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
