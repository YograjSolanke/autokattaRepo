package autokatta.com.search;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import autokatta.com.R;
import autokatta.com.adapter.SearchProductAdapter;
import autokatta.com.response.GetSearchProductResponse;

/**
 * Created by ak-001 on 18/4/17.
 */

public class SearchService extends Fragment {
    View mSearchService;
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
    SearchProduct.CheckedTagsAdapter tagsadapter;
    SearchProduct.CheckedCategoryAdapter categoryAdapter;
    SearchProduct.CheckedBrandTagsAdapter brandTagsAdapter;
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
                    Log.i("String", "->" + searchString);
                    getSearchResults(searchString);
                }
            }
        });
        return mSearchService;
    }

    /*
    Search Results...
     */
    private void getSearchResults(String searchString) {

    }


}

