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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import autokatta.com.R;
import autokatta.com.adapter.SearchServiceAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import autokatta.com.response.GetServiceSearchResponse;
import retrofit2.Response;

/**
 * Created by ak-001 on 18/4/17.
 */

public class SearchService extends Fragment implements RequestNotifier {
    View mSearchService;
    private ListView searchList;
    ImageView filterImg;
    String searchString, firstWord;
    List<GetServiceSearchResponse.Success> mList = new ArrayList<>();
    List<GetServiceSearchResponse.Success> mList_new = new ArrayList<>();
    List<String> categoryList = new ArrayList<>();
    List<String> tagsList = new ArrayList<>();
    List<String> BrandtagsList = new ArrayList<>();
    HashSet<String> categoryHashSet;
    HashSet<String> tagsHashSet;
    HashSet<String> BrandtagsHashSet;
    SearchServiceAdapter adapter;
    Bundle bundle;
    CheckedTagsAdapter tagsadapter;
    CheckedCategoryAdapter categoryAdapter;
    CheckedBrandTagsAdapter brandTagsAdapter;
    ArrayList<String> finalcategory = new ArrayList<>();
    ArrayList<String> finalTags = new ArrayList<>();
    ArrayList<String> finalBrandTags = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mSearchService = inflater.inflate(R.layout.fragment_search_product, container, false);
        searchList = (ListView) mSearchService.findViewById(R.id.searchlist);
        filterImg = (ImageView) mSearchService.findViewById(R.id.filterimg);

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                bundle = getArguments();
                if (bundle != null) {
                    searchString = bundle.getString("searchText1");
                    Log.i("Service", "->" + searchString);
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
        return mSearchService;
    }

    /*
    Search Results...
     */
    private void getSearchResults(String searchString) {
        ApiCall mApiCall = new ApiCall(getActivity(), this);
        mApiCall.searchService(searchString, getActivity().getSharedPreferences(getString(R.string.my_preference),
                Context.MODE_PRIVATE).getString("loginContact", ""));
    }


    @Override
    public void notifySuccess(Response<?> response) {
        if (response != null) {
            if (response.isSuccessful()) {
                GetServiceSearchResponse productResponse = (GetServiceSearchResponse) response.body();
                for (GetServiceSearchResponse.Success success : productResponse.getSuccess()) {
                    success.setStoreId(success.getStoreId());
                    success.setStoreId(success.getId());
                    success.setStoreId(success.getName());
                    success.setStoreId(success.getImages());
                    success.setStoreId(success.getType());
                    success.setStoreId(success.getPrice());
                    success.setStoreId(success.getCategory());
                    success.setStoreId(success.getBrandtags());
                    success.setStoreId(success.getDetails());
                    success.setStoreId(success.getStorecontact());
                    success.setStoreId(success.getStoreName());
                    success.setStoreId(success.getStorewebsite());
                    success.setStoreId(success.getStorerating());
                    success.setStoreId(success.getServicelikecount());
                    success.setStoreId(success.getServicelikestatus());
                    success.setStoreId(success.getSrate());
                    success.setStoreId(success.getSrate1());
                    success.setStoreId(success.getSrate2());
                    success.setStoreId(success.getSrate3());
                    success.setStoreId(success.getServicerating());
                    success.setStoreId(success.getServiceTags());

                    //category specific code
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
                    if (success.getServiceTags().trim().contains(",")) {
                        String arr[] = success.getServiceTags().trim().split(",");
                        for (int l = 0; l < arr.length; l++) {
                            String part = arr[l].trim();
                            if (!part.equals(" ") || !part.equals(""))
                                tagsList.add(part);
                        }
                    } else {
                        if (!success.getServiceTags().trim().equals(" ") && !success.getServiceTags().trim().equals(""))
                            tagsList.add(success.getServiceTags().trim());
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
                adapter = new SearchServiceAdapter(getActivity(), mList);
                searchList.setAdapter(adapter);
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
        if (incomingTags.length != 0) {
            tagsadapter = new CheckedTagsAdapter(getActivity(), incomingTags);
            lvtags.setAdapter(tagsadapter);
        }
        if (incomingCategory.length != 0) {
            categoryAdapter = new CheckedCategoryAdapter(getActivity(), incomingCategory);
            lvcat.setAdapter(categoryAdapter);
        }
        if (incomingBtags.length != 0) {
            brandTagsAdapter = new CheckedBrandTagsAdapter(getActivity(), incomingBtags);
            lvbrandtags.setAdapter(brandTagsAdapter);
        }

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchList.setAdapter(null);
                for (int i = 0; i < mList.size(); i++) {
                    //If Category contains ","
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
                            if (mList.get(i).getServiceTags().trim().contains(",")) {
                                boolean flagtag = false;

                                String aartag[] = mList.get(i).getServiceTags().trim().split(",");
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

                                if (finalTags.contains(mList.get(i).getBrandtags().trim()))
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
                    //If there is no "," in Category
                    else {
                        if (finalcategory.contains(mList.get(i).getCategory().trim()))
                            mList.get(i).visibility = true;
                        else {


                            if (mList.get(i).getServiceTags().trim().contains(",")) {

                                boolean flagtag = false;

                                String aartag[] = mList.get(i).getServiceTags().trim().split(",");
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

                                if (finalTags.contains(mList.get(i).getServiceTags().trim()))
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
                adapter = new SearchServiceAdapter(getActivity(), mList_new);
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
        Button remove;
        CheckBox checkBox;
    }

    public class CheckedTagsAdapter extends BaseAdapter {

        private LayoutInflater mInflater;
        Activity activity;

        ArrayList<String> titles = new ArrayList<>();

        public CheckedTagsAdapter(Activity a, String titles[]) {
//
            this.activity = a;

            this.titles = new ArrayList<>(Arrays.asList(titles));
//

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
                //holder.remove=(Button)convertView.findViewById(R.id.remove);
                holder.checkBox = (CheckBox) convertView.findViewById(R.id.checkbox);
//                if(filterflag==false)
//                    holder.checkBox.setChecked(true);
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
            final ViewHolder2 holder;
            final View cv = convertView;


            if (convertView == null) {
                holder = new ViewHolder2();

                convertView = mInflater.inflate(R.layout.checked_category_adapter, null);
                holder.text = (TextView) convertView.findViewById(R.id.txtname);
                //holder.remove=(Button)convertView.findViewById(R.id.remove);
                holder.checkBox = (CheckBox) convertView.findViewById(R.id.checkbox);
//                if(filterflag==false)
//                    holder.checkBox.setChecked(true);
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

