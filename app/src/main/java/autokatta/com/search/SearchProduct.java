package autokatta.com.search;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import autokatta.com.R;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
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
    List<String> categoryList = new ArrayList<>();
    List<String> tagsList = new ArrayList<>();
    List<String> BrandtagsList = new ArrayList<>();
    HashSet<String> categoryHashSet;
    HashSet<String> tagsHashSet;
    HashSet<String> BrandtagsHashSet;
    SearchProductAdapter adapter;
    Bundle bundle;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mSearchProduct = inflater.inflate(R.layout.fragment_search_product, container, false);
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
        return mSearchProduct;
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
                categoryHashSet = new HashSet<String>(categoryList);
                tagsHashSet = new HashSet<String>(tagsList);
                BrandtagsHashSet = new HashSet<String>(BrandtagsList);
                adapter = new SearchProductAdapter(getActivity(), mList);
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

    /*
    Adapter
     */

    private class SearchProductAdapter extends BaseAdapter {

        Activity activity;
        FragmentActivity ctx;
        List<GetSearchProductResponse.Success> allSearchData = new ArrayList<>();
        private LayoutInflater inflater;

        private SearchProductAdapter(Activity activity, List<GetSearchProductResponse.Success> allSearchDataArrayList) {
            this.activity = activity;
            this.allSearchData = allSearchDataArrayList;
            inflater = (LayoutInflater) activity.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return allSearchData.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        private class YoHolder {
            TextView productName, productPrice, productDetails, productTags, productType;
            Button ViewDetails;
            ImageView productImage, deleteproduct;
            RatingBar productrating;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final YoHolder yoHolder;
            if (convertView == null) {
                yoHolder = new YoHolder();
                convertView = inflater.inflate(R.layout.product_new, null);
                yoHolder.productName = (TextView) convertView.findViewById(R.id.edittxt);
                yoHolder.productPrice = (TextView) convertView.findViewById(R.id.priceedit);
                yoHolder.productDetails = (TextView) convertView.findViewById(R.id.editdetails);
                yoHolder.productTags = (TextView) convertView.findViewById(R.id.edittags);
                yoHolder.productType = (TextView) convertView.findViewById(R.id.editproducttype);
                yoHolder.ViewDetails = (Button) convertView.findViewById(R.id.btnviewdetails);
                yoHolder.productImage = (ImageView) convertView.findViewById(R.id.profile);
                yoHolder.productrating = (RatingBar) convertView.findViewById(R.id.productrating);
                yoHolder.deleteproduct = (ImageView) convertView.findViewById(R.id.deleteproduct);
                convertView.setTag(yoHolder);
            } else {
                yoHolder = (YoHolder) convertView.getTag();
            }
            final GetSearchProductResponse.Success obj = allSearchData.get(position);
            yoHolder.productrating.setVisibility(View.GONE);
            yoHolder.deleteproduct.setVisibility(View.GONE);
            yoHolder.productName.setText(obj.getProductName());
            yoHolder.productPrice.setText(obj.getPrice());
            yoHolder.productDetails.setText(obj.getProductDetails());
            yoHolder.productTags.setText(obj.getProductTags());
            yoHolder.productType.setText(obj.getType());

            if (obj.getImages().equals("") || obj.getImages().equals("null") || obj.getImages().equals("")) {
                yoHolder.productImage.setBackgroundResource(R.drawable.store);
            } else {
                try {
                    String dp_path = "http://autokatta.com/mobile/Product_pics/" + obj.getImages();
                    Glide.with(activity)
                            .load(dp_path)
                            .centerCrop()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(yoHolder.productImage);
                } catch (Exception e) {
                    System.out.println("Error in uploading images");
                }
            }

            yoHolder.ViewDetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle b = new Bundle();
                    b.putString("name", obj.getProductName());
                    b.putString("pid", obj.getProductId());
                    b.putString("price", obj.getPrice());
                    b.putString("details", obj.getProductDetails());
                    b.putString("tags", obj.getProductTags());
                    b.putString("type", obj.getType());
                    b.putString("likestatus", obj.getProductlikestatus());
                    b.putString("images", obj.getImages());
                    b.putString("category", obj.getCategory());
                    b.putString("plikecnt", obj.getProductlikecount());
                    b.putString("prating", obj.getProductrating());
                    b.putString("prate", obj.getPrate());
                    b.putString("prate1", obj.getPrate1());
                    b.putString("prate2", obj.getPrate2());
                    b.putString("prate3", obj.getPrate3());
                    b.putString("store_id", obj.getStoreId());
                    b.putString("storecontact", obj.getStorecontact());
                    b.putString("storename", obj.getStoreName());
                    b.putString("storewebsite", obj.getStorewebsite());
                    b.putString("storerating", obj.getStorerating());
                    b.putString("brandtags_list", obj.getBrandtags());


                   /* ProductView frag = new ProductView();
                    frag.setArguments(b);

                    FragmentManager fragmentManager = ctx.getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.containerView, frag);
                    fragmentTransaction.addToBackStack("product_view");
                    fragmentTransaction.commit();*/
                }
            });
            return convertView;
        }
    }
}
