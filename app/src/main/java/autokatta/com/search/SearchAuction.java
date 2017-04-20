package autokatta.com.search;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashSet;

import autokatta.com.R;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import retrofit2.Response;

/**
 * Created by ak-001 on 19/4/17.
 */

public class SearchAuction extends Fragment implements RequestNotifier {
    View mSearchAuction;
    private ListView searchList;
    //AllSearchEventCustomAdapter adapter;

    String searchString, firstWord;
    //private ArrayList<AllSearchData> allSearchDataArrayList;
    //private ArrayList<AllSearchData> allSearchDataArrayList_new;

    String myContact;
    ImageView filterImg;
    boolean[] checkedValues;
    HashSet<String> eventTypeSet;
    ArrayList<String> eventTypeList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mSearchAuction = inflater.inflate(R.layout.fragment_search_product, container, false);

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Bundle bundle = getArguments();
                searchString = bundle.getString("searchText");
                getSearchAuction(searchString);
            }
        });
        return mSearchAuction;
    }

    private void getSearchAuction(String searchString) {
        ApiCall mApiCall = new ApiCall(getActivity(), this);
        mApiCall.getSearchAuctionData(searchString);
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
