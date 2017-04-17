package autokatta.com.search;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import autokatta.com.R;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import retrofit2.Response;

/**
 * Created by ak-001 on 17/4/17.
 */

public class SearchProduct extends Fragment implements RequestNotifier {
    View mSearchProduct;
    private ListView searchList;
    ImageView filterImg;
    String searchString, firstWord;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mSearchProduct = inflater.inflate(R.layout.fragment_search_product, container, false);
        searchList = (ListView) mSearchProduct.findViewById(R.id.searchlist);
        filterImg = (ImageView) mSearchProduct.findViewById(R.id.filterimg);

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Bundle bundle = getArguments();
                searchString = bundle.getString("searchText");
                System.out.println("SearchString" + searchString);
                getSearchResults(searchString);
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

    }

    @Override
    public void notifyError(Throwable error) {

    }

    @Override
    public void notifyString(String str) {

    }
}
