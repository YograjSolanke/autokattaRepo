package autokatta.com.search;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import autokatta.com.adapter.SearchProductAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.networkreceiver.ConnectionDetector;
import autokatta.com.other.CustomToast;
import autokatta.com.response.GetSearchProductResponse;
import retrofit2.Response;

/**
 * Created by ak-001 on 17/4/17.
 */

public class SearchProduct extends Fragment implements RequestNotifier {
    View mSearchProduct;
    private ListView searchList;
    ImageView filterImg;
    String searchString, firstWord;
    List<GetSearchProductResponse.Success> mList = new ArrayList<>();
    List<GetSearchProductResponse.Success> mList_new = new ArrayList<>();
    List<String> categoryList = new ArrayList<>();
    List<String> tagsList = new ArrayList<>();
    List<String> BrandtagsList = new ArrayList<>();
    HashSet<String> categoryHashSet;
    HashSet<String> tagsHashSet;
    HashSet<String> BrandtagsHashSet;
    SearchProductAdapter adapter;
    Bundle bundle;
    boolean hasViewCreated = false;
    TextView mNoData;
    CheckedTagsAdapter tagsadapter;
    CheckedCategoryAdapter categoryAdapter;
    CheckedBrandTagsAdapter brandTagsAdapter;
    List<String> finalcategory = new ArrayList<>();
    List<String> finalTags = new ArrayList<>();
    List<String> finalBrandTags = new ArrayList<>();
    ConnectionDetector mConnectionDetector;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mSearchProduct = inflater.inflate(R.layout.fragment_search_product, container, false);
        return mSearchProduct;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mNoData = (TextView) mSearchProduct.findViewById(R.id.no_category);
        //mNoData.setVisibility(View.GONE);
        searchList = (ListView) mSearchProduct.findViewById(R.id.searchlist);
        filterImg = (ImageView) mSearchProduct.findViewById(R.id.filterimg);
        filterImg.setVisibility(View.GONE);

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mConnectionDetector = new ConnectionDetector(getActivity());
                bundle = getArguments();
                if (bundle != null) {
                    searchString = bundle.getString("searchText1");
                    Log.i("String", "->" + searchString);
                    getSearchResults(searchString);
                }
            }
        });

        filterImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testingbox(categoryHashSet.toArray(new String[categoryHashSet.size()]),
                        tagsHashSet.toArray(new String[tagsHashSet.size()]), BrandtagsHashSet.toArray(new String[BrandtagsHashSet.size()]));
            }
        });
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (this.isVisible()) {
            if (isVisibleToUser && !hasViewCreated) {
                bundle = getArguments();
                if (bundle != null) {
                    searchString = bundle.getString("searchText1");
                    Log.i("String", "->" + searchString);
                    getSearchResults(searchString);
                }
                hasViewCreated = true;
            }
        }
    }

    private void getSearchResults(String searchString) {

        if (mConnectionDetector.isConnectedToInternet()) {
            ApiCall mApiCall = new ApiCall(getActivity(), this);
            mApiCall.searchProduct(searchString, getActivity().getSharedPreferences(getString(R.string.my_preference),
                    Context.MODE_PRIVATE).getString("loginContact", ""));
        } else
            CustomToast.customToast(getActivity(), getString(R.string.no_internet));
    }

    @Override
    public void notifySuccess(Response<?> response) {
        if (response != null) {
            if (response.isSuccessful()) {
                GetSearchProductResponse productResponse = (GetSearchProductResponse) response.body();
                if (!productResponse.getSuccess().isEmpty()) {
                    mNoData.setVisibility(View.GONE);
                    filterImg.setVisibility(View.VISIBLE);
                    mList.clear();
                    for (GetSearchProductResponse.Success success : productResponse.getSuccess()) {
                        success.setStoreId(success.getStoreId());
                        success.setProductId(success.getProductId());
                        success.setProductType(success.getProductType());
                        success.setProductName(success.getProductName());
                        success.setImages(success.getImages());
                        success.setPrice(success.getPrice());
                        success.setCategory(success.getCategory());
                        success.setBrandtags(success.getBrandtags());
                        success.setProductDetails(success.getProductDetails());
                        success.setStorecontact(success.getStorecontact());
                        success.setStoreName(success.getStoreName());
                        success.setStorewebsite(success.getStorewebsite());
                        success.setStorerating(success.getStorerating());
                        success.setProductlikecount(success.getProductlikecount());
                        success.setProductlikestatus(success.getProductlikestatus());
                        success.setPrate(success.getPrate());
                        success.setPrate1(success.getPrate1());
                        success.setPrate2(success.getPrate2());
                        success.setPrate3(success.getPrate3());
                        success.setProductrating(success.getProductrating());
                        success.setProductTags(success.getProductTags());
                        success.visibility = true;

                        if (success.getCategory().trim().contains(",")) {
                            String arr[] = success.getCategory().trim().split(",");
                            for (int l = 0; l < arr.length; l++) {
                                String part = arr[l].trim();
                                if (!part.equals(" ") || !part.equals(""))
                                    categoryList.add(part);
                            }
                        } else {
                            categoryList.add(success.getCategory().trim());
                        }


                        //Tags specific code
                        if (success.getProductTags().trim().contains(",")) {
                            String arr[] = success.getProductTags().trim().split(",");
                            for (int l = 0; l < arr.length; l++) {
                                String part = arr[l].trim();
                                if (!part.equals(" ") || !part.equals(""))
                                    tagsList.add(part);
                            }
                        } else {
                            if (!success.getProductTags().trim().equals(" ") && !success.getProductTags().trim().equals(""))
                                tagsList.add(success.getProductTags().trim());
                        }

                        // Brand Tags specific code
                        if (success.getBrandtags().trim().contains(",")) {
                            String arr[] = success.getBrandtags().trim().split(",");
                            for (int l = 0; l < arr.length; l++) {
                                String part = arr[l].trim();
                                if (!part.equals(" ") || !part.equals(""))
                                    BrandtagsList.add(part);
                            }
                        } else {
                            if (!success.getBrandtags().trim().equals(" ") && !success.getBrandtags().trim().equals(""))
                                BrandtagsList.add(success.getBrandtags().trim());
                        }


                        String img = success.getImages();
                        if (img.contains(",")) {
                            String arr[] = img.split(",", 2);
                            firstWord = arr[0];
                            System.out.println(firstWord);
                            success.setImages(firstWord);
                            System.out.println("firstword imaggggg=========" + firstWord);
                            String all = img.replace(",", "/ ");
                            System.out.println("All images are::" + all);
                            success.setImages(all);
                        } else {
                            System.out.println("otherrr imaggggg=========" + img);
                            success.setImages(img);
                            success.setImages(img);
                        }
                        mList.add(success);
                    }
                    categoryHashSet = new HashSet<>(categoryList);
                    tagsHashSet = new HashSet<>(tagsList);
                    BrandtagsHashSet = new HashSet<>(BrandtagsList);
                    adapter = new SearchProductAdapter(getActivity(), mList);
                    searchList.setAdapter(adapter);
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
            Log.i("Check Class-", "SearchProduct Fragment");
        }


    }

    @Override
    public void notifyString(String str) {

    }

    public void testingbox(final String[] incomingCategory, final String[] incomingTags, final String[] incomingBtags) {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View convertView = inflater.inflate(R.layout.custom, null);
        alertDialog.setView(convertView);
        final AlertDialog alert = alertDialog.show();
        alertDialog.setTitle("Select option to filter data");
        ListView lvcat = (ListView) convertView.findViewById(R.id.listview1);
        ListView lvtags = (ListView) convertView.findViewById(R.id.listview2);
        ListView lvbrandtags = (ListView) convertView.findViewById(R.id.listview3);
        Button ok = (Button) convertView.findViewById(R.id.btnok);
        Button cancel = (Button) convertView.findViewById(R.id.btncancel);
        tagsadapter = new CheckedTagsAdapter(getActivity(), incomingTags);
        categoryAdapter = new CheckedCategoryAdapter(getActivity(), incomingCategory);
        brandTagsAdapter = new CheckedBrandTagsAdapter(getActivity(), incomingBtags);
        lvtags.setAdapter(tagsadapter);
        lvcat.setAdapter(categoryAdapter);
        lvbrandtags.setAdapter(brandTagsAdapter);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchList.setAdapter(null);

                for (int i = 0; i < mList.size(); i++) {
                    if (mList.get(i).getCategory().trim().contains(",")) {
                        boolean flag = false;

                        String arr[] = mList.get(i).getCategory().trim().split(",");
                        for (int r = 0; r < arr.length; r++) {
                            if (finalcategory.contains(arr[r].trim()))
                                flag = true;
                        }

                        if (flag)
                            mList.get(i).visibility = true;
                        else {
                            //If Tags contains ","
                            if (mList.get(i).getProductTags().trim().contains(",")) {
                                boolean flagtag = false;
                                String aartag[] = mList.get(i).getProductTags().trim().split(",");
                                for (int t = 0; t < aartag.length; t++) {
                                    if (finalTags.contains(aartag[t].trim()))
                                        flagtag = true;
                                }

                                if (flagtag)
                                    mList.get(i).visibility = true;
                                else {
                                    //mList.get(i).visibility = false;
                                    //If BrandTags contains ","
                                    if (mList.get(i).getBrandtags().trim().contains(",")) {
                                        boolean flagbrandtag = false;

                                        String arrbrandtag[] = mList.get(i).getBrandtags().trim().split(",");
                                        for (int t = 0; t < arrbrandtag.length; t++) {
                                            if (finalBrandTags.contains(arrbrandtag[t].trim()))
                                                flagbrandtag = true;
                                        }

                                        if (flagbrandtag)
                                            mList.get(i).visibility = true;
                                        else
                                            mList.get(i).visibility = false;

                                    } else {
                                        if (finalBrandTags.contains(mList.get(i).getBrandtags().trim()))
                                            mList.get(i).visibility = true;
                                        else
                                            mList.get(i).visibility = false;
                                    }
                                }

                            }

                            //If there is no "," in Tags
                            else {

                                if (finalTags.contains(mList.get(i).getProductTags().trim()))
                                    mList.get(i).visibility = true;
                                else {
                                    //If BrandTags contains ","
                                    if (mList.get(i).getProductTags().trim().contains(",")) {

                                        boolean flagbrandtag = false;

                                        String arrbrandtag[] = mList.get(i).getProductTags().trim().split(",");
                                        for (int t = 0; t < arrbrandtag.length; t++) {
                                            if (finalBrandTags.contains(arrbrandtag[t].trim()))
                                                flagbrandtag = true;
                                        }

                                        if (flagbrandtag)
                                            mList.get(i).visibility = true;
                                        else
                                            mList.get(i).visibility = false;

                                    } else {
                                        if (finalBrandTags.contains(mList.get(i).getBrandtags().trim()))
                                            mList.get(i).visibility = true;
                                        else
                                            mList.get(i).visibility = false;
                                    }
                                }
                                //mList.get(i).visibility = false;
                            }


                        }


                    }
                    //If there is no "," in Category
                    else {
                        if (finalcategory.contains(mList.get(i).getCategory().trim()))
                            mList.get(i).visibility = true;
                        else {
                            if (mList.get(i).getProductTags().trim().contains(",")) {

                                boolean flagtag = false;

                                String aartag[] = mList.get(i).getProductTags().trim().split(",");
                                for (int t = 0; t < aartag.length; t++) {
                                    if (finalTags.contains(aartag[t].trim()))
                                        flagtag = true;
                                }

                                if (flagtag)
                                    mList.get(i).visibility = true;
                                else {
                                    //If BrandTags contains ","
                                    if (mList.get(i).getBrandtags().trim().contains(",")) {

                                        boolean flagbrandtag = false;

                                        String arrbrandtag[] = mList.get(i).getBrandtags().trim().split(",");
                                        for (int t = 0; t < arrbrandtag.length; t++) {
                                            if (finalBrandTags.contains(arrbrandtag[t].trim()))
                                                flagbrandtag = true;
                                        }

                                        if (flagbrandtag)
                                            mList.get(i).visibility = true;
                                        else
                                            mList.get(i).visibility = false;

                                    } else {
                                        if (finalBrandTags.contains(mList.get(i).getBrandtags().trim()))
                                            mList.get(i).visibility = true;
                                        else
                                            mList.get(i).visibility = false;
                                    }
                                }

                            } else {

                                if (finalTags.contains(mList.get(i).getProductTags().trim()))
                                    mList.get(i).visibility = true;
                                else {
                                    //If BrandTags contains ","
                                    if (mList.get(i).getBrandtags().trim().contains(",")) {

                                        boolean flagbrandtag = false;

                                        String arrbrandtag[] = mList.get(i).getBrandtags().trim().split(",");
                                        for (int t = 0; t < arrbrandtag.length; t++) {
                                            if (finalBrandTags.contains(arrbrandtag[t].trim()))
                                                flagbrandtag = true;
                                        }

                                        if (flagbrandtag)
                                            mList.get(i).visibility = true;
                                        else
                                            mList.get(i).visibility = false;

                                    } else {
                                        if (finalBrandTags.contains(mList.get(i).getBrandtags().trim()))
                                            mList.get(i).visibility = true;
                                        else
                                            mList.get(i).visibility = false;
                                    }
                                }
                                //mList.get(i).visibility = false;
                            }
                        }
                    }
                }

                mList_new = new ArrayList<>();
                mList_new.clear();

                for (int w = 0; w < mList.size(); w++) {
                    if (mList.get(w).visibility) {
                        mList_new.add(mList.get(w));
                    }
                }

                alert.dismiss();
                adapter = new SearchProductAdapter(getActivity(), mList_new);
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

    /*
    Adapters....
     */

    static class ViewHolder {
        TextView text;
        Button remove;
        CheckBox checkBox;
    }

    private class CheckedTagsAdapter extends BaseAdapter {
        private LayoutInflater mInflater;
        Activity activity;

        List<String> titles = new ArrayList<>();

        CheckedTagsAdapter(Activity a, String titles[]) {
            this.activity = a;
            this.titles = new ArrayList<>(Arrays.asList(titles));

            if (finalTags.size() == 0) {
                for (int i = 0; i < this.titles.size(); i++) {
                    finalTags.add(this.titles.get(i));
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
            if (!finalTags.get(position).equalsIgnoreCase("0"))
                holder.checkBox.setChecked(true);
            holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        finalTags.set(position, holder.text.getText().toString());
                    } else {
                        finalTags.set(position, "0");
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

    /*
    Adpters...
     */
    private static class ViewHolder1 {
        TextView text;
        Button remove;
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

    private static class ViewHolder2 {
        TextView text;
        Button remove;
        CheckBox checkBox;
    }

    private class CheckedBrandTagsAdapter extends BaseAdapter {
        private LayoutInflater mInflater;
        Activity activity;
        List<String> titles = new ArrayList<>();

        CheckedBrandTagsAdapter(Activity a, String titles[]) {
            this.activity = a;
            this.titles = new ArrayList<>(Arrays.asList(titles));
            if (finalBrandTags.size() == 0) {
                for (int i = 0; i < this.titles.size(); i++) {
                    finalBrandTags.add(this.titles.get(i));
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
            final ViewHolder2 holder;
            final View cv = convertView;
            if (convertView == null) {
                holder = new ViewHolder2();
                convertView = mInflater.inflate(R.layout.checked_category_adapter, null);
                holder.text = (TextView) convertView.findViewById(R.id.txtname);
                holder.checkBox = (CheckBox) convertView.findViewById(R.id.checkbox);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder2) convertView.getTag();
            }
            holder.text.setText(titles.get(position));
            if (!finalBrandTags.get(position).equalsIgnoreCase("0"))
                holder.checkBox.setChecked(true);
            holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        finalBrandTags.set(position, holder.text.getText().toString());
                    } else {
                        finalBrandTags.set(position, "0");
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
