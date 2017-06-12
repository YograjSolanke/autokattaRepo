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
    ArrayList<String> finalcategory = new ArrayList<>();
    ArrayList<String> finalTags = new ArrayList<>();
    ArrayList<String> finalBrandTags = new ArrayList<>();

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
        mNoData.setVisibility(View.GONE);
        searchList = (ListView) mSearchProduct.findViewById(R.id.searchlist);
        filterImg = (ImageView) mSearchProduct.findViewById(R.id.filterimg);

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
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
        ApiCall mApiCall = new ApiCall(getActivity(), this);
        mApiCall.searchProduct(searchString, getActivity().getSharedPreferences(getString(R.string.my_preference),
                Context.MODE_PRIVATE).getString("loginContact", ""));
    }

    @Override
    public void notifySuccess(Response<?> response) {
        if (response != null) {
            if (response.isSuccessful()) {
                GetSearchProductResponse productResponse = (GetSearchProductResponse) response.body();
                if (!productResponse.getSuccess().isEmpty()) {
                    mNoData.setVisibility(View.GONE);
                    mList.clear();
                    for (GetSearchProductResponse.Success success : productResponse.getSuccess()) {
                        success.setStoreId(success.getStoreId());
                        success.setStoreId(success.getProductId());
                        success.setStoreId(success.getType());
                        success.setStoreId(success.getProductName());
                        success.setStoreId(success.getImages());
                        success.setStoreId(success.getProductType());
                        success.setStoreId(success.getPrice());
                        success.setStoreId(success.getCategory());
                        success.setStoreId(success.getBrandtags());
                        success.setStoreId(success.getProductDetails());
                        success.setStoreId(success.getStorecontact());
                        success.setStoreId(success.getStoreName());
                        success.setStoreId(success.getStorewebsite());
                        success.setStoreId(success.getStorerating());
                        success.setStoreId(success.getProductlikecount());
                        success.setStoreId(success.getProductlikestatus());
                        success.setStoreId(success.getPrate());
                        success.setStoreId(success.getPrate1());
                        success.setStoreId(success.getPrate2());
                        success.setStoreId(success.getPrate3());
                        success.setStoreId(success.getProductrating());
                        success.setStoreId(success.getProductTags());
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
                }

            } else {
                //Snackbar.make(getView(), getString(R.string._404), Snackbar.LENGTH_SHORT);
            }
        } else {
            //Snackbar.make(getView(), getString(R.string.no_response), Snackbar.LENGTH_SHORT);
        }
    }

    @Override
    public void notifyError(Throwable error) {
        if (error instanceof SocketTimeoutException) {
            //Snackbar.make(getView(), getString(R.string._404_), Snackbar.LENGTH_SHORT).show();
        } else if (error instanceof NullPointerException) {
            //Snackbar.make(getView(), getString(R.string.no_response), Snackbar.LENGTH_SHORT).show();
        } else if (error instanceof ClassCastException) {
            //Snackbar.make(getView(), getString(R.string.no_response), Snackbar.LENGTH_SHORT).show();
        } else if (error instanceof ConnectException) {
            //mNoInternetIcon.setVisibility(View.VISIBLE);
            /*Snackbar snackbar = Snackbar.make(getView(), getString(R.string.no_internet), Snackbar.LENGTH_INDEFINITE)
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
            snackbar.show();*/
        } else if (error instanceof UnknownHostException) {
            //mNoInternetIcon.setVisibility(View.VISIBLE);
            /*Snackbar snackbar = Snackbar.make(getView(), getString(R.string.no_internet), Snackbar.LENGTH_INDEFINITE)
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
            snackbar.show();*/
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

    public class CheckedTagsAdapter extends BaseAdapter {
        private LayoutInflater mInflater;
        Activity activity;

        ArrayList<String> titles = new ArrayList<>();

        public CheckedTagsAdapter(Activity a, String titles[]) {
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
    static class ViewHolder1 {
        TextView text;
        Button remove;
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

    static class ViewHolder2 {
        TextView text;
        Button remove;
        CheckBox checkBox;
    }

    public class CheckedBrandTagsAdapter extends BaseAdapter {
        private LayoutInflater mInflater;
        Activity activity;
        ArrayList<String> titles = new ArrayList<>();

        public CheckedBrandTagsAdapter(Activity a, String titles[]) {
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
