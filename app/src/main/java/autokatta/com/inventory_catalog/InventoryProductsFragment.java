package autokatta.com.inventory_catalog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import autokatta.com.R;
import autokatta.com.adapter.StoreProductAdapter;
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

public class InventoryProductsFragment extends Fragment implements RequestNotifier, SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {

    View mProduct;
    ImageView filterImg;
    RelativeLayout relativeFilter;
    String Sharedcontact, storeContact;
    SwipeRefreshLayout mSwipeRefreshLayout;
    RecyclerView mRecyclerView;
    List<StoreInventoryResponse.Success.Product> productList;
    List<StoreInventoryResponse.Success.Product> productList_new;
    LinearLayoutManager mLayoutManager;
    StoreProductAdapter adapter;
    boolean hasView = false;
    ConnectionDetector mTestConnection;
    ArrayList<String> categoryList = new ArrayList<>();
    HashSet<String> categoryHashSet;
    CheckedCategoryAdapter categoryAdapter;
    ArrayList<String> finalcategory = new ArrayList<>();
    TextView mNoData;
    int counter = 0;

    public InventoryProductsFragment() {
        //empty constructor...
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mProduct = inflater.inflate(R.layout.store_product_fragment, container, false);
        return mProduct;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTestConnection = new ConnectionDetector(getActivity());
                Sharedcontact = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", null);
                mNoData = (TextView) mProduct.findViewById(R.id.no_category);
                filterImg = (ImageView) mProduct.findViewById(R.id.filterimg);
                relativeFilter = (RelativeLayout) mProduct.findViewById(R.id.rel);
                relativeFilter.setVisibility(View.GONE);
                mSwipeRefreshLayout = (SwipeRefreshLayout) mProduct.findViewById(R.id.swipeRefreshLayout);
                mRecyclerView = (RecyclerView) mProduct.findViewById(R.id.recycler_view);
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
                        getInventoryProducts(Sharedcontact);
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
                getInventoryProducts(Sharedcontact);
                hasView = true;
            }
        }
    }

    private void getInventoryProducts(String sharedcontact) {
        if (mTestConnection.isConnectedToInternet()) {
            ApiCall apiCall = new ApiCall(getActivity(), this);
            apiCall.getMyInventory_Catalog(sharedcontact);
        } else {
            mSwipeRefreshLayout.setRefreshing(false);
            mNoData.setVisibility(View.GONE);
            if (isAdded())
                CustomToast.customToast(getActivity(), getString(R.string.no_internet));
        }
    }

    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(false);

//        productList.clear();
        getInventoryProducts(Sharedcontact);
    }

    @Override
    public void notifySuccess(Response<?> response) {
        if (response != null) {
            if (response.isSuccessful()) {
                mSwipeRefreshLayout.setRefreshing(false);
                productList = new ArrayList<>();
                StoreInventoryResponse storeResponse = (StoreInventoryResponse) response.body();
                if (!storeResponse.getSuccess().getProduct().isEmpty()) {
                    mNoData.setVisibility(View.GONE);
                    for (StoreInventoryResponse.Success.Product success : storeResponse.getSuccess().getProduct()) {
                        success.setProductId(success.getProductId());
                        success.setName(success.getName());
                        success.setBrandtags(success.getBrandtags());
                        success.setPrice(success.getPrice());
                        success.setProductType(success.getProductType());
                        success.setProductDetails(success.getProductDetails());
                        success.setProductTags(success.getProductTags());
                        success.setProductImage(success.getProductImage());
                        success.setCategory(success.getCategory());
                        success.setProductlikestatus(success.getProductlikestatus());
                        success.setProductlikecount(success.getProductlikecount());
                        success.setProductrating(success.getProductrating());
                        success.setPrate(success.getPrate());
                        success.setPrate1(success.getPrate1());
                        success.setPrate2(success.getPrate2());
                        success.setPrate3(success.getPrate3());
                        storeContact = success.getStorecontact();
                        productList.add(success);
//filter code commented

//                        if (success.getCategory().trim().contains(",")) {
//                            String arr[] = success.getCategory().trim().split(",");
//                            for (int l = 0; l < arr.length; l++) {
//                                String part = arr[l].trim();
//                                if (!part.equals(" ") && !part.equals(""))
//                                    categoryList.add(part);
//                            }
//                        } else {
//                            categoryList.add(success.getCategory().trim());
//                        }
//
                    }
//                    categoryHashSet = new HashSet<>(categoryList);
//                    filterResult(categoryHashSet.toArray(new String[categoryHashSet.size()]));
                    mSwipeRefreshLayout.setRefreshing(false);


                    adapter = new StoreProductAdapter(getActivity(), productList, Sharedcontact, storeContact);
                    mRecyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                } else {
                    mSwipeRefreshLayout.setRefreshing(false);
                    mNoData.setVisibility(View.VISIBLE);
                }
            } else {

                mSwipeRefreshLayout.setRefreshing(false);
                if (isAdded())
                    CustomToast.customToast(getActivity(), getString(R.string._404_));
            }
        } else {
            mSwipeRefreshLayout.setRefreshing(false);
            if (isAdded())
                CustomToast.customToast(getActivity(), getString(R.string.no_response));
        }
    }

    @Override
    public void notifyError(Throwable error) {
        mSwipeRefreshLayout.setRefreshing(false);
        if (error instanceof SocketTimeoutException) {
            if (isAdded())
                CustomToast.customToast(getActivity(), getString(R.string._404_));
        } else if (error instanceof NullPointerException) {
            if (isAdded())
                CustomToast.customToast(getActivity(), getString(R.string.no_response));
        } else if (error instanceof ClassCastException) {
            if (isAdded())
                CustomToast.customToast(getActivity(), getString(R.string.no_response));
        } else if (error instanceof ConnectException) {
            if (isAdded())
                CustomToast.customToast(getActivity(), getString(R.string.no_internet));
        } else if (error instanceof UnknownHostException) {
            if (isAdded())
                CustomToast.customToast(getActivity(), getString(R.string.no_internet));
        } else {
            Log.i("Check Class-"
                    , "Inventory Product Fragment");
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
        openDialog.setCancelable(false);
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
                    for (int i = 0; i < productList.size(); i++) {
                        //If Category contains ","
                        if (productList.get(i).getCategory().trim().contains(",")) {
                            boolean flag = false;
                            String arr[] = productList.get(i).getCategory().trim().split(",");
                            for (int r = 0; r < arr.length; r++) {
                                if (finalcategory.contains(arr[r].trim()))
                                    flag = true;
                            }

                            if (flag)
                                productList.get(i).setVisibility(true);
                            else {
                                productList.get(i).setVisibility(false);
                            }
                        } else {
                            if (finalcategory.contains(productList.get(i).getCategory().trim()))
                                productList.get(i).setVisibility(true);
                            else {
                                productList.get(i).setVisibility(false);
                            }
                        }
                    }


                    productList_new = new ArrayList<>();
                    productList_new.clear();
                    for (int w = 0; w < productList.size(); w++) {
                        if (productList.get(w).isVisibility()) {
                            productList_new.add(productList.get(w));
                        }
                    }
                    openDialog.dismiss();
                    adapter = new StoreProductAdapter(getActivity(), productList_new, Sharedcontact, storeContact);
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
