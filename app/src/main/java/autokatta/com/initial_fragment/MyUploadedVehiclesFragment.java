package autokatta.com.initial_fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.adapter.MyUploadedVehicleAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.OnLoadMoreListener;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.networkreceiver.ConnectionDetector;
import autokatta.com.other.CustomToast;
import autokatta.com.other.VerticalLineDecorator;
import autokatta.com.response.MyUploadedVehiclesResponse;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ak-004 on 1/4/17.
 */

public class MyUploadedVehiclesFragment extends Fragment implements RequestNotifier, SwipeRefreshLayout.OnRefreshListener {
    SwipeRefreshLayout mSwipeRefreshLayout;
    RecyclerView mRecyclerView;
    List<MyUploadedVehiclesResponse.Success> myUploadedVehiclesResponseList = new ArrayList<>();
    MyUploadedVehicleAdapter adapter;
    View myVehicles;
    String myContact;
    TextView mNoData;
    ConnectionDetector mTestConnection;
    boolean hasView = false;
    int index = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myVehicles = inflater.inflate(R.layout.fragment_my_uploaded_vehicles, container, false);
        return myVehicles;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {

                mTestConnection = new ConnectionDetector(getActivity());
                myContact = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE)
                        .getString("loginContact", "");

                mSwipeRefreshLayout = (SwipeRefreshLayout) myVehicles.findViewById(R.id.swipeRefreshLayoutMyUploadedVehicle);
                mRecyclerView = (RecyclerView) myVehicles.findViewById(R.id.recyclerMyUploadedVehicle);
                mNoData = (TextView) myVehicles.findViewById(R.id.no_category);
                mNoData.setVisibility(View.GONE);

                adapter = new MyUploadedVehicleAdapter(getActivity(), myUploadedVehiclesResponseList);
                adapter.setLoadMoreListener(new OnLoadMoreListener() {
                    @Override
                    public void onLoadMore() {
                        mRecyclerView.post(new Runnable() {
                            @Override
                            public void run() {
                                //int index = myUploadedVehiclesResponseList.size() - 1;
                                index++;
                                Log.i("index", "->" + index);
                                loadMore(index);
                            }
                        });
                        //Calling loadMore function in Runnable to fix the
                        // java.lang.IllegalStateException: Cannot call this method while RecyclerView is computing a layout or scrolling error
                    }
                });
                //adapter.notifyDataSetChanged();
                mRecyclerView.setHasFixedSize(true);
                LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getActivity());
                //mLinearLayoutManager.setReverseLayout(true);
                //mLinearLayoutManager.setStackFromEnd(true);
                mLinearLayoutManager.setSmoothScrollbarEnabled(true);
                mRecyclerView.setLayoutManager(mLinearLayoutManager);
                mRecyclerView.addItemDecoration(new VerticalLineDecorator(2));
                mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                mRecyclerView.setAdapter(adapter);

                mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                        android.R.color.holo_green_light,
                        android.R.color.holo_orange_light,
                        android.R.color.holo_red_light);
                mSwipeRefreshLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        mSwipeRefreshLayout.setRefreshing(true);
                        getMyUploadedVehicles(myContact, 1, 10);
                    }
                });
            }
        });
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (this.isVisible()) {
            if (isVisibleToUser && !hasView) {
                getMyUploadedVehicles(myContact, 1, 10);
                hasView = true;
            }
        }
    }

    @Override
    public void onRefresh() {
        getMyUploadedVehicles(myContact, 1, 10);
    }

    private void getMyUploadedVehicles(String myContact, int pageNo, int record) {
        if (mTestConnection.isConnectedToInternet()) {
            ApiCall apiCall = new ApiCall(getActivity(), this);
            apiCall.MyUploadedVehicles(myContact, pageNo, record);
        } else {
            mSwipeRefreshLayout.setRefreshing(false);
            mNoData.setVisibility(View.GONE);
            if (isAdded())
                CustomToast.customToast(getActivity(), getString(R.string.no_internet));
        }
    }


    @Override
    public void notifySuccess(Response<?> response) {
        if (response != null) {
            if (response.isSuccessful()) {
                /*if (index > 1) {
                    myUploadedVehiclesResponseList.remove(myUploadedVehiclesResponseList.size() - 1);
                }*/
                mSwipeRefreshLayout.setRefreshing(false);
                //myUploadedVehiclesResponseList.clear();
                MyUploadedVehiclesResponse myVehicleResponse = (MyUploadedVehiclesResponse) response.body();
                Log.i("myVehicleResponse", "->" + myVehicleResponse.getSuccess().size());
                if (myVehicleResponse.getSuccess().size() > 0) {
                    if (!myVehicleResponse.getSuccess().isEmpty()) {
                        mNoData.setVisibility(View.GONE);
                        for (MyUploadedVehiclesResponse.Success myVehicleSuccess : myVehicleResponse.getSuccess()) {
                            myVehicleSuccess.setVehicleId(myVehicleSuccess.getVehicleId());
                            myVehicleSuccess.setTitle(myVehicleSuccess.getTitle());
                            myVehicleSuccess.setPrice(myVehicleSuccess.getPrice());
                            myVehicleSuccess.setCategory(myVehicleSuccess.getCategory());
                            myVehicleSuccess.setModel(myVehicleSuccess.getModel());
                            myVehicleSuccess.setManufacturer(myVehicleSuccess.getManufacturer());
                            myVehicleSuccess.setBuyerLeads(myVehicleSuccess.getBuyerLeads());
                            myVehicleSuccess.setNotificationstatus(myVehicleSuccess.getNotificationstatus());
                            myVehicleSuccess.setImages(myVehicleSuccess.getImages());
                            myVehicleSuccess.setDate(myVehicleSuccess.getDate());
                            myVehicleSuccess.setYearOfManufacture(myVehicleSuccess.getYearOfManufacture());
                            myVehicleSuccess.setKmsRunning(myVehicleSuccess.getKmsRunning());
                            myVehicleSuccess.setHrsRunning(myVehicleSuccess.getHrsRunning());
                            myVehicleSuccess.setRtoCity(myVehicleSuccess.getRtoCity());
                            myVehicleSuccess.setLocationCity(myVehicleSuccess.getLocationCity());
                            myVehicleSuccess.setRegistrationNumber(myVehicleSuccess.getRegistrationNumber());
                            if (myVehicleSuccess.getRegistrationNumber().equals(""))
                                myVehicleSuccess.setRegistrationNumber("NA");
                            else
                                myVehicleSuccess.setRegistrationNumber(myVehicleSuccess.getRegistrationNumber());

                            myVehicleSuccess.setVersion(myVehicleSuccess.getVersion());
                            myVehicleSuccess.setRcAvailable(myVehicleSuccess.getRcAvailable());
                            myVehicleSuccess.setNoOfOwner(myVehicleSuccess.getNoOfOwner());

                            myVehicleSuccess.setGroupIDs(myVehicleSuccess.getGroupIDs());
                            myVehicleSuccess.setStoreIDs(myVehicleSuccess.getStoreIDs());
                            myVehicleSuccess.setStockType(myVehicleSuccess.getStockType());
                            myVehicleSuccess.setChatCount(myVehicleSuccess.getChatCount());
                            myVehicleSuccess.setEnquiryCount(myVehicleSuccess.getEnquiryCount());
                            myUploadedVehiclesResponseList.add(myVehicleSuccess);
                        }
                        Log.i("size", String.valueOf(myUploadedVehiclesResponseList.size()));
                        //adapter.notifyDataChanged();
                    } else {
                        //adapter.setMoreDataAvailable(false);
                        //Toast.makeText(getActivity(),"No More Data Available",Toast.LENGTH_LONG).show();
                        mSwipeRefreshLayout.setRefreshing(false);
                        mNoData.setVisibility(View.VISIBLE);
                    }
                } else {//result size 0 means there is no more data available at server
                    adapter.setMoreDataAvailable(false);
                    //telling adapter to stop calling load more as no more server data available
                    Toast.makeText(getActivity(), "No More Data Available", Toast.LENGTH_LONG).show();
                }
                //adapter.notifyDataSetChanged();
                adapter.notifyDataChanged();
            } else {
                mSwipeRefreshLayout.setRefreshing(false);
                if (isAdded())
                    CustomToast.customToast(getActivity(), getString(R.string._404));
            }

        } else {
            mSwipeRefreshLayout.setRefreshing(false);
            if (isAdded())
                CustomToast.customToast(getActivity(), getString(R.string.no_response));
        }
    }


    private void loadMore(int index) {
        //add loading progress view
        adapter.notifyItemInserted(myUploadedVehiclesResponseList.size());
        getMyUploadedVehicles(myContact, index, 10);

        /*try {
            if (mTestConnection.isConnectedToInternet()) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(ApiCall.initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<MyUploadedVehiclesResponse> myUploadedVehiclesResponseCall = serviceApi._autokattaGetMyUploadedVehicles(myContact, index, 10);
                myUploadedVehiclesResponseCall.enqueue(new Callback<MyUploadedVehiclesResponse>() {
                    @Override
                    public void onResponse(Call<MyUploadedVehiclesResponse> call, Response<MyUploadedVehiclesResponse> response) {
                        if (response.isSuccessful()) {
                            myUploadedVehiclesResponseList.remove(myUploadedVehiclesResponseList.size()-1);
                            mSwipeRefreshLayout.setRefreshing(false);
                            //myUploadedVehiclesResponseList.clear();
                            MyUploadedVehiclesResponse myVehicleResponse = (MyUploadedVehiclesResponse) response.body();
                            if (!myVehicleResponse.getSuccess().isEmpty()) {
                                mNoData.setVisibility(View.GONE);
                                for (MyUploadedVehiclesResponse.Success myVehicleSuccess : myVehicleResponse.getSuccess()) {
                                    myVehicleSuccess.setVehicleId(myVehicleSuccess.getVehicleId());
                                    myVehicleSuccess.setTitle(myVehicleSuccess.getTitle());
                                    myVehicleSuccess.setPrice(myVehicleSuccess.getPrice());
                                    myVehicleSuccess.setCategory(myVehicleSuccess.getCategory());
                                    myVehicleSuccess.setModel(myVehicleSuccess.getModel());
                                    myVehicleSuccess.setManufacturer(myVehicleSuccess.getManufacturer());
                                    myVehicleSuccess.setBuyerLeads(myVehicleSuccess.getBuyerLeads());
                                    myVehicleSuccess.setNotificationstatus(myVehicleSuccess.getNotificationstatus());
                                    myVehicleSuccess.setImages(myVehicleSuccess.getImages());
                                    myVehicleSuccess.setDate(myVehicleSuccess.getDate());
                                    myVehicleSuccess.setYearOfManufacture(myVehicleSuccess.getYearOfManufacture());
                                    myVehicleSuccess.setKmsRunning(myVehicleSuccess.getKmsRunning());
                                    myVehicleSuccess.setHrsRunning(myVehicleSuccess.getHrsRunning());
                                    myVehicleSuccess.setRtoCity(myVehicleSuccess.getRtoCity());
                                    myVehicleSuccess.setLocationCity(myVehicleSuccess.getLocationCity());
                                    myVehicleSuccess.setRegistrationNumber(myVehicleSuccess.getRegistrationNumber());
                                    if (myVehicleSuccess.getRegistrationNumber().equals(""))
                                        myVehicleSuccess.setRegistrationNumber("NA");
                                    else
                                        myVehicleSuccess.setRegistrationNumber(myVehicleSuccess.getRegistrationNumber());

                                    myVehicleSuccess.setVersion(myVehicleSuccess.getVersion());
                                    myVehicleSuccess.setRcAvailable(myVehicleSuccess.getRcAvailable());
                                    myVehicleSuccess.setNoOfOwner(myVehicleSuccess.getNoOfOwner());

                                    myVehicleSuccess.setGroupIDs(myVehicleSuccess.getGroupIDs());
                                    myVehicleSuccess.setStoreIDs(myVehicleSuccess.getStoreIDs());
                                    myVehicleSuccess.setStockType(myVehicleSuccess.getStockType());
                                    myVehicleSuccess.setChatCount(myVehicleSuccess.getChatCount());
                                    myVehicleSuccess.setEnquiryCount(myVehicleSuccess.getEnquiryCount());


                                    myUploadedVehiclesResponseList.add(myVehicleSuccess);
                                }
                                Log.i("size", String.valueOf(myUploadedVehiclesResponseList.size()));
                                mSwipeRefreshLayout.setRefreshing(false);
                            } else {
                                adapter.setMoreDataAvailable(false);
                                Toast.makeText(getActivity(),"No More Data Available",Toast.LENGTH_LONG).show();
                                mSwipeRefreshLayout.setRefreshing(false);
                                mNoData.setVisibility(View.VISIBLE);
                            }
                            adapter.notifyDataChanged();
                        } else {
                            mSwipeRefreshLayout.setRefreshing(false);
                            if (isAdded())
                                CustomToast.customToast(getActivity(), getString(R.string._404));
                        }
                    }

                    @Override
                    public void onFailure(Call<MyUploadedVehiclesResponse> call, Throwable t) {
                        //mNotifier.notifyError(t);
                    }
                });
            } else
                CustomToast.customToast(getActivity(), getString(R.string.no_internet));
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        /*Call<List<MovieModel>> call = api.getMovies(index);
        call.enqueue(new Callback<List<MovieModel>>() {
            @Override
            public void onResponse(Call<List<MovieModel>> call, Response<List<MovieModel>> response) {
                if(response.isSuccessful()){

                    //remove loading view
                    movies.remove(movies.size()-1);

                    List<MovieModel> result = response.body();
                    if(result.size()>0){
                        //add loaded data
                        movies.addAll(result);
                    }else{//result size 0 means there is no more data available at server
                        adapter.setMoreDataAvailable(false);
                        //telling adapter to stop calling load more as no more server data available
                        Toast.makeText(context,"No More Data Available",Toast.LENGTH_LONG).show();
                    }
                    adapter.notifyDataChanged();
                    //should call the custom method adapter.notifyDataChanged here to get the correct loading status
                }else{
                    Log.e(TAG," Load More Response Error "+String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(Call<List<MovieModel>> call, Throwable t) {
                Log.e(TAG," Load More Response Error "+t.getMessage());
            }
        });*/
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
                    , "MyUploadedVehiclesActivity");
            error.printStackTrace();
        }
    }

    @Override
    public void notifyString(String str) {

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.used_inv_filter, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.all:
                onRefresh();
                return true;

            case R.id.finance_repo:
                adapter.getFilter().filter("Finance/Repo");
                return true;

            case R.id.insurance:
                adapter.getFilter().filter("Insurance");
                return true;

            case R.id.scrap:
                adapter.getFilter().filter("Scrap");
                return true;

            case R.id.inventory:
                adapter.getFilter().filter("Inventory");
                return true;

            case R.id.market_place:
                adapter.getFilter().filter("Market Place");
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
