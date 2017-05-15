package autokatta.com.browseStore;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
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

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import autokatta.com.R;
import autokatta.com.adapter.BrowseStoreAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import autokatta.com.response.BrowseStoreResponse;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ak-003 on 20/3/17.
 */

public class ProductBasedStore extends Fragment implements RequestNotifier, SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {

    View mProductBased;
    RecyclerView mRecyclerView;
    SwipeRefreshLayout mSwipeRefreshLayout;
    ImageView filterImg;
    List<BrowseStoreResponse.Success> mSuccesses;
    List<BrowseStoreResponse.Success> mSuccesses_new;
    ArrayList<String> categoryList = new ArrayList<>();
    HashSet<String> categoryHashSet;
    CheckedCategoryAdapter categoryAdapter;
    ArrayList<String> finalcategory = new ArrayList<>();
    BrowseStoreAdapter adapter;
    public ProductBasedStore() {
        //Empty Constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mProductBased = inflater.inflate(R.layout.fragment_product_based_store, container, false);
        mRecyclerView = (RecyclerView) mProductBased.findViewById(R.id.BrowseStore_recycler_view);
        mSwipeRefreshLayout = (SwipeRefreshLayout) mProductBased.findViewById(R.id.swipeRefreshLayoutBrowseStore);
        filterImg = (ImageView) mProductBased.findViewById(R.id.filterimg);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        //getData();//Get Api...
        mSwipeRefreshLayout.setOnRefreshListener(this);
        filterImg.setOnClickListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
                getStoreData(getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE)
                        .getString("loginContact", ""));
            }
        });

        return mProductBased;
    }

    private void getStoreData(String contact) {

        ApiCall apiCall = new ApiCall(getActivity(), this);
        apiCall.getBrowseStores(contact, "Product");
    }

    @Override
    public void notifySuccess(Response<?> response) {

        if (response != null) {
            if (response.isSuccessful()) {
                mSwipeRefreshLayout.setRefreshing(false);
                mSuccesses = new ArrayList<>();
                BrowseStoreResponse browseStoreResponse = (BrowseStoreResponse) response.body();
                for (BrowseStoreResponse.Success success : browseStoreResponse.getSuccess()) {
                    success.setStoreId(success.getStoreId());
                    success.setContactNo(success.getContactNo());
                    success.setStoreName(success.getStoreName());
                    success.setStoreImage(success.getStoreImage());
                    success.setLocation(success.getLocation());
                    success.setCategory(success.getCategory());
                    success.setWebsite(success.getWebsite());
                    success.setStoreType(success.getStoreType());
                    success.setWorkingDays(success.getWorkingDays());
                    success.setModifiedDate(success.getModifiedDate());
                    success.setLikestatus(success.getLikestatus());
                    success.setFollowstatus(success.getFollowstatus());
                    success.setLikecount(success.getLikecount());
                    success.setFollowcount(success.getFollowcount());
                    success.setRating(success.getRating());
                    success.setVisibility(true);
                    mSuccesses.add(success);


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

                }


                System.out.println("List !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! before hashset" + categoryList);
                categoryHashSet = new HashSet<>(categoryList);
                System.out.println("List !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!  after hashset" + categoryHashSet);
                filterResult(categoryHashSet.toArray(new String[categoryHashSet.size()]));


                mSwipeRefreshLayout.setRefreshing(false);
//                adapter = new BrowseStoreAdapter(getActivity(), mSuccesses);
//                mRecyclerView.setAdapter(adapter);
//                adapter.notifyDataSetChanged();
            } else {
                mSwipeRefreshLayout.setRefreshing(false);
                CustomToast.customToast(getActivity(), getString(R.string._404));
            }
        } else {
            CustomToast.customToast(getActivity(), getString(R.string.no_response));
        }

    }

    @Override
    public void notifyError(Throwable error) {
        if (error instanceof SocketTimeoutException) {
            CustomToast.customToast(getActivity(), getString(R.string._404));
        } else if (error instanceof NullPointerException) {
            CustomToast.customToast(getActivity(), getString(R.string.no_response));
        } else if (error instanceof ClassCastException) {
            CustomToast.customToast(getActivity(), getString(R.string.no_response));
        } else {
            Log.i("Check Class-", "ProductBasedStore Fragment");
        }

    }

    @Override
    public void notifyString(String str) {

    }

    @Override
    public void onRefresh() {

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


        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View convertView = inflater.inflate(R.layout.custom_store, null);
        alertDialog.setView(convertView);
        final AlertDialog alert = alertDialog.show();
        alertDialog.setTitle("Select option to filter data");
        ListView lvcat = (ListView) convertView.findViewById(R.id.listview1);

        Button Ok = (Button) convertView.findViewById(R.id.btnok);
        Button cancel = (Button) convertView.findViewById(R.id.btncancel);


        categoryAdapter = new CheckedCategoryAdapter(getActivity(), incomingCategory);


        lvcat.setAdapter(categoryAdapter);

        Ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                System.out.println("Final categories=" + finalcategory);

                mRecyclerView.setAdapter(null);
                for (int i = 0; i < mSuccesses.size(); i++) {
                    //If Category contains ","
                    if (mSuccesses.get(i).getCategory().trim().contains(",")) {
                        boolean flag = false;

                        String arr[] = mSuccesses.get(i).getCategory().trim().split(",");
                        for (int r = 0; r < arr.length; r++) {

                            if (finalcategory.contains(arr[r].trim()))
                                flag = true;

                        }

                        if (flag)
                            mSuccesses.get(i).setVisibility(true);

                            //Location checking code
                        else {
                            mSuccesses.get(i).setVisibility(false);

                        }

                    }
                    //If Category not contains ","
                    else {
                        if (finalcategory.contains(mSuccesses.get(i).getCategory().trim()))
                            mSuccesses.get(i).setVisibility(true);


                            //Location checking code
                        else {

                            mSuccesses.get(i).setVisibility(false);
                        }
                    }
                    //allSearchDataArrayList.get(i).visibility = false;
                }


                mSuccesses_new = new ArrayList<>();
                mSuccesses_new.clear();

                for (int w = 0; w < mSuccesses.size(); w++) {
                    if (mSuccesses.get(w).isVisibility()) {

                        mSuccesses_new.add(mSuccesses.get(w));
                    }

                }

                alert.dismiss();

                adapter = new BrowseStoreAdapter(getActivity(), mSuccesses_new);
                mRecyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();


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


        //   alertDialog.show();
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
//
            this.activity = a;

            this.titles = new ArrayList<>(Arrays.asList(titles));
//

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
}
