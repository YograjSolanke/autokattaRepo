package autokatta.com.search;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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
import java.util.Set;

import autokatta.com.R;
import autokatta.com.adapter.AllSearchEventCustomAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.networkreceiver.ConnectionDetector;
import autokatta.com.other.CustomToast;
import autokatta.com.response.GetSearchAuctionResponse;
import autokatta.com.response.ModelSearchAuction;
import retrofit2.Response;

/**
 * Created by ak-001 on 19/4/17.
 */

public class SearchAuction extends Fragment implements RequestNotifier {
    View mSearchAuction;
    private ListView searchList;
    String searchString;
    private List<ModelSearchAuction> allSearchDataArrayList = new ArrayList<>();
    private List<ModelSearchAuction> allSearchDataArrayList_new = new ArrayList<>();
    AllSearchEventCustomAdapter adapter;
    String myContact;
    ImageView filterImg;
    boolean[] checkedValues;
    HashSet<String> eventTypeSet;
    List<String> eventTypeList = new ArrayList<>();
    Bundle bundle;
    boolean hasViewCreated = false;
    TextView mNoData;
    ConnectionDetector mConnectionDetector;
    //private ProgressDialog dialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mSearchAuction = inflater.inflate(R.layout.fragment_search_product, container, false);
        setHasOptionsMenu(true);
        return mSearchAuction;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mNoData = (TextView) mSearchAuction.findViewById(R.id.no_category);
        //mNoData.setVisibility(View.GONE);

        searchList = (ListView) mSearchAuction.findViewById(R.id.searchlist);
        filterImg = (ImageView) mSearchAuction.findViewById(R.id.filterimg);
        filterImg.setVisibility(View.GONE);

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {

                try {
                    /*dialog = new ProgressDialog(getActivity());
                    dialog.setMessage("Loading...");*/

                    myContact = getActivity().getSharedPreferences(getString(R.string.my_preference), Context.MODE_PRIVATE)
                            .getString("loginContact", "");

                    mConnectionDetector = new ConnectionDetector(getActivity());

                    Bundle bundle = getArguments();
                    if (bundle != null) {
                        searchString = bundle.getString("searchText1");
                        System.out.println("Auction" + searchString);
                        getSearchAuction(searchString);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });
        filterImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterData(eventTypeSet.toArray(new String[eventTypeSet.size()]));
            }
        });
    }

    private void getSearchAuction(String searchString) {
        if (mConnectionDetector.isConnectedToInternet()) {
            //dialog.show();
            ApiCall mApiCall = new ApiCall(getActivity(), this);
            mApiCall.getSearchAuctionData(searchString);
        } else {
            CustomToast.customToast(getActivity(), getString(R.string.no_internet));
        }
    }

    @Override
    public void notifySuccess(Response<?> response) {
        /*if (dialog.isShowing()) {
            dialog.dismiss();
        }*/
        if (response != null) {
            if (response.isSuccessful()) {
                mNoData.setVisibility(View.GONE);
                filterImg.setVisibility(View.VISIBLE);
                GetSearchAuctionResponse auctionResponse = (GetSearchAuctionResponse) response.body();
                allSearchDataArrayList.clear();
                eventTypeList.clear();
                //Auction Live

                if (!auctionResponse.getSuccess().getAuctionLive().isEmpty()) {
                    for (GetSearchAuctionResponse.AuctionLive auctionLive : auctionResponse.getSuccess().getAuctionLive()) {
                        ModelSearchAuction modelAuction = new ModelSearchAuction();

                        modelAuction.setAuctionId(auctionLive.getAuctionId());
                        modelAuction.setActionTitle(auctionLive.getActionTitle());
                        modelAuction.setStartDate(auctionLive.getStartDate());
                        modelAuction.setStartTime(auctionLive.getStartTime());
                        modelAuction.setEndDate(auctionLive.getEndDate());
                        modelAuction.setEndTime(auctionLive.getEndTime());
                        modelAuction.setAuctionType(auctionLive.getAuctionType());
                        modelAuction.setLocation(auctionLive.getLocation());
                        modelAuction.setProductCategory(auctionLive.getProductCategory());
                        modelAuction.setSpecialClauses(auctionLive.getSpecialClauses());
                        modelAuction.setNoOfVehicle(auctionLive.getNoOfVehicle());
                        modelAuction.setStartDateTime(auctionLive.getStartDateTime());
                        modelAuction.setEndDateTime(auctionLive.getEndDateTime());
                        modelAuction.setGoingcount(auctionLive.getGoingcount());
                        modelAuction.setKey(auctionLive.getKey());
                        modelAuction.setEventType(auctionLive.getEventType());
                        Log.e("Auction", "Live");
                        eventTypeList.add(auctionLive.getEventType());
                        allSearchDataArrayList.add(modelAuction);
                    }
                }
                //Auction Up
                if (!auctionResponse.getSuccess().getAuctionUp().isEmpty()) {
                    for (GetSearchAuctionResponse.AuctionUp auctionUp : auctionResponse.getSuccess().getAuctionUp()) {
                        ModelSearchAuction modelAuction = new ModelSearchAuction();

                        modelAuction.setAuctionId(auctionUp.getAuctionId());
                        modelAuction.setActionTitle(auctionUp.getActionTitle());
                        modelAuction.setStartDate(auctionUp.getStartDate());
                        modelAuction.setStartTime(auctionUp.getStartTime());
                        modelAuction.setEndDate(auctionUp.getEndDate());
                        modelAuction.setEndTime(auctionUp.getEndTime());
                        modelAuction.setAuctionType(auctionUp.getAuctionType());
                        modelAuction.setLocation(auctionUp.getLocation());
                        modelAuction.setProductCategory(auctionUp.getProductCategory());
                        modelAuction.setSpecialClauses(auctionUp.getSpecialClauses());
                        modelAuction.setNoOfVehicle(auctionUp.getNoOfVehicle());
                        modelAuction.setStartDateTime(auctionUp.getStartDateTime());
                        modelAuction.setEndDateTime(auctionUp.getEndDateTime());
                        modelAuction.setGoingcount(auctionUp.getGoingcount());
                        modelAuction.setKey(auctionUp.getKey());
                        modelAuction.setEventType(auctionUp.getEventType());
                        Log.e("Auction", "Up");
                        eventTypeList.add(auctionUp.getEventType());
                        allSearchDataArrayList.add(modelAuction);
                    }
                }

                //
                if (!auctionResponse.getSuccess().getExchangeMelaLive().isEmpty()) {
                    for (GetSearchAuctionResponse.ExchangeMelaLive exchangeMelaLive : auctionResponse.getSuccess().getExchangeMelaLive()) {
                        ModelSearchAuction modelAuction = new ModelSearchAuction();

                        modelAuction.setId(exchangeMelaLive.getId());
                        modelAuction.setContact(exchangeMelaLive.getContact());
                        modelAuction.setName(exchangeMelaLive.getName());
                        modelAuction.setStartDate(exchangeMelaLive.getStartDate());
                        modelAuction.setStartTime(exchangeMelaLive.getStartTime());
                        modelAuction.setEndDate(exchangeMelaLive.getEndDate());
                        modelAuction.setEndTime(exchangeMelaLive.getEndTime());
                        modelAuction.setLocation(exchangeMelaLive.getLocation());
                        modelAuction.setAddress(exchangeMelaLive.getAddress());
                        modelAuction.setImage(exchangeMelaLive.getImage());
                        modelAuction.setStartDateTime(exchangeMelaLive.getStartDateTime());
                        modelAuction.setEndDateTime(exchangeMelaLive.getEndDateTime());
                        modelAuction.setCreateDate(exchangeMelaLive.getCreateDate());
                        modelAuction.setDetails(exchangeMelaLive.getDetails());
                        modelAuction.setKey(exchangeMelaLive.getKey());
                        modelAuction.setEventType(exchangeMelaLive.getEventType());
                        Log.e("Exchange", "Live");
                        eventTypeList.add(exchangeMelaLive.getEventType());
                        allSearchDataArrayList.add(modelAuction);
                    }
                }
                //Exchange Mela Up
                if (!auctionResponse.getSuccess().getExchangeMelaUp().isEmpty()) {
                    for (GetSearchAuctionResponse.ExchangeMelaUp exchangeMelaUp : auctionResponse.getSuccess().getExchangeMelaUp()) {
                        ModelSearchAuction modelAuction = new ModelSearchAuction();

                        modelAuction.setId(exchangeMelaUp.getId());
                        modelAuction.setContact(exchangeMelaUp.getContact());
                        modelAuction.setName(exchangeMelaUp.getName());
                        modelAuction.setStartDate(exchangeMelaUp.getStartDate());
                        modelAuction.setStartTime(exchangeMelaUp.getStartTime());
                        modelAuction.setEndDate(exchangeMelaUp.getEndDate());
                        modelAuction.setEndTime(exchangeMelaUp.getEndTime());
                        modelAuction.setLocation(exchangeMelaUp.getLocation());
                        modelAuction.setAddress(exchangeMelaUp.getAddress());
                        modelAuction.setImage(exchangeMelaUp.getImage());
                        modelAuction.setStartDateTime(exchangeMelaUp.getStartDateTime());
                        modelAuction.setEndDateTime(exchangeMelaUp.getEndDateTime());
                        modelAuction.setCreateDate(exchangeMelaUp.getCreateDate());
                        modelAuction.setDetails(exchangeMelaUp.getDetails());
                        modelAuction.setKey(exchangeMelaUp.getKey());
                        modelAuction.setEventType(exchangeMelaUp.getEventType());
                        Log.e("Exchange", "Up");
                        eventTypeList.add(exchangeMelaUp.getEventType());
                        allSearchDataArrayList.add(modelAuction);
                    }
                }
                //Loan Mela Live
                if (!auctionResponse.getSuccess().getLoanMelaLive().isEmpty()) {
                    for (GetSearchAuctionResponse.LoanMelaLive loanMelaLive : auctionResponse.getSuccess().getLoanMelaLive()) {
                        ModelSearchAuction modelAuction = new ModelSearchAuction();

                        modelAuction.setId(loanMelaLive.getId());
                        modelAuction.setContact(loanMelaLive.getContact());
                        modelAuction.setName(loanMelaLive.getName());
                        modelAuction.setStartDate(loanMelaLive.getStartDate());
                        modelAuction.setStartTime(loanMelaLive.getStartTime());
                        modelAuction.setEndDate(loanMelaLive.getEndDate());
                        modelAuction.setEndTime(loanMelaLive.getEndTime());
                        modelAuction.setLocation(loanMelaLive.getLocation());
                        modelAuction.setAddress(loanMelaLive.getAddress());
                        modelAuction.setImage(loanMelaLive.getImage());
                        modelAuction.setStartDateTime(loanMelaLive.getStartDateTime());
                        modelAuction.setEndDateTime(loanMelaLive.getEndDateTime());
                        modelAuction.setCreateDate(loanMelaLive.getCreateDate());
                        modelAuction.setDetails(loanMelaLive.getDetails());
                        modelAuction.setKey(loanMelaLive.getKey());
                        modelAuction.setEventType(loanMelaLive.getEventType());
                        Log.e("Loan", "Live");
                        eventTypeList.add(loanMelaLive.getEventType());
                        allSearchDataArrayList.add(modelAuction);
                    }
                }

                //Loan Mela Up
                if (!auctionResponse.getSuccess().getLoanMelaUp().isEmpty()) {
                    for (GetSearchAuctionResponse.LoanMelaUp loanMelaUp : auctionResponse.getSuccess().getLoanMelaUp()) {
                        ModelSearchAuction modelAuction = new ModelSearchAuction();

                        modelAuction.setId(loanMelaUp.getId());
                        modelAuction.setContact(loanMelaUp.getContact());
                        modelAuction.setName(loanMelaUp.getName());
                        modelAuction.setStartDate(loanMelaUp.getStartDate());
                        modelAuction.setStartTime(loanMelaUp.getStartTime());
                        modelAuction.setEndDate(loanMelaUp.getEndDate());
                        modelAuction.setEndTime(loanMelaUp.getEndTime());
                        modelAuction.setLocation(loanMelaUp.getLocation());
                        modelAuction.setAddress(loanMelaUp.getAddress());
                        modelAuction.setImage(loanMelaUp.getImage());
                        modelAuction.setStartDateTime(loanMelaUp.getStartDateTime());
                        modelAuction.setEndDateTime(loanMelaUp.getEndDateTime());
                        modelAuction.setCreateDate(loanMelaUp.getCreateDate());
                        modelAuction.setDetails(loanMelaUp.getDetails());
                        modelAuction.setKey(loanMelaUp.getKey());
                        modelAuction.setEventType(loanMelaUp.getEventType());
                        Log.e("Loan", "Up");
                        eventTypeList.add(loanMelaUp.getEventType());
                        allSearchDataArrayList.add(modelAuction);
                    }
                }

                /*//Vehicle Live
                if (!auctionResponse.getSuccess().getVehiclesLive().isEmpty()) {
                    for (GetSearchAuctionResponse.VehiclesLive vehiclesLive : auctionResponse.getSuccess().getVehiclesLive()) {
                    ModelSearchAuction modelAuction = new ModelSearchAuction();

                        modelAuction.setAuctionId(vehiclesLive.getAuctionId());
                        modelAuction.setVehicleId(vehiclesLive.getVehicleId());
                        modelAuction.setTitle(vehiclesLive.getTitle());
                        modelAuction.setModel(vehiclesLive.getModel());
                        modelAuction.setColor(vehiclesLive.getColor());
                        modelAuction.setImage(vehiclesLive.getImage());
                        modelAuction.setStartPrice(vehiclesLive.getStartPrice());
                        modelAuction.setKey(6);
                        modelAuction.setReservePrice(vehiclesLive.getReservePrice());
                        Log.e("Vehicle","Live");
                        allSearchDataArrayList.add(modelAuction);
                    }
                }
                //Vehicle Up
                if (!auctionResponse.getSuccess().getVehiclesUp().isEmpty()) {
                    for (GetSearchAuctionResponse.VehiclesUp vehiclesUp : auctionResponse.getSuccess().getVehiclesUp()) {
                    ModelSearchAuction modelAuction = new ModelSearchAuction();

                        modelAuction.setAuctionId(vehiclesUp.getAuctionId());
                        modelAuction.setVehicleId(vehiclesUp.getVehicleId());
                        modelAuction.setTitle(vehiclesUp.getTitle());
                        modelAuction.setModel(vehiclesUp.getModel());
                        modelAuction.setColor(vehiclesUp.getColor());
                        modelAuction.setImage(vehiclesUp.getImage());
                        modelAuction.setStartPrice(vehiclesUp.getStartPrice());
                        modelAuction.setKey(7);
                        modelAuction.setReservePrice(vehiclesUp.getReservePrice());
                        Log.e("Vehicle","Up");
                        allSearchDataArrayList.add(modelAuction);
                    }
                }*/
                eventTypeSet = new HashSet<>(eventTypeList);
                checkedValues = new boolean[eventTypeSet.size()];
                Arrays.fill(checkedValues, Boolean.TRUE);

                if (allSearchDataArrayList.size() != 0) {
                    adapter = new AllSearchEventCustomAdapter(getActivity(), allSearchDataArrayList);
                    searchList.setAdapter(adapter);
                } else {
                    mNoData.setVisibility(View.VISIBLE);
                    filterImg.setVisibility(View.GONE);
                }
            }
        } else {
            CustomToast.customToast(getActivity(), getString(R.string.no_response));
        }
    }

    @Override
    public void notifyError(Throwable error) {
        /*if (dialog.isShowing()) {
            dialog.dismiss();
        }*/
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
            Log.i("Check Class-", "SearchAuction Fragment");
        }


    }

    @Override
    public void notifyString(String str) {

    }

    //alert box to filter by category
    public void filterData(final String[] incomingData) {
        final ArrayList<String> mSelectedItems = new ArrayList<>();
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
        // set the dialog title
        builder.setTitle("Please Select City/Location to filter!!")
                .setCancelable(true)
                .setMultiChoiceItems(incomingData, checkedValues, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if (isChecked) {
                            // mSelectedItems.add(incomingCategory[which]);
                            checkedValues[which] = true;
                        } else if (mSelectedItems.contains(incomingData[which])) {
                            // mSelectedItems.remove(incomingCategory[which]);
                            checkedValues[which] = false;
                        }
                    }

                })
                // Set the action buttons
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        mSelectedItems.clear();
                        //for preselected checkboxes.
                        for (int y = 0; y < checkedValues.length; y++) {
                            if (checkedValues[y])
                                mSelectedItems.add(incomingData[y]);
                        }
                        Set<String> selectedItemSet = new HashSet<>(mSelectedItems);
                        //all checkboxes selected

                        searchList.setAdapter(null);
                        for (int i = 0; i < allSearchDataArrayList.size(); i++) {
                            //simplify code
                            allSearchDataArrayList.get(i).visibility = selectedItemSet.contains(allSearchDataArrayList.get(i).getEventType());
                        }

                        for (int w = 0; w < allSearchDataArrayList.size(); w++) {
                            if (allSearchDataArrayList.get(w).visibility) {
                                allSearchDataArrayList_new.add(allSearchDataArrayList.get(w));
                            }

                        }

                        System.out.println("main class list size after:" + allSearchDataArrayList.size());

                        adapter = new AllSearchEventCustomAdapter(getActivity(), allSearchDataArrayList_new);
                        adapter.notifyDataSetChanged();
                        searchList.setAdapter(adapter);
                        //  filterImg.setVisibility(View.GONE);
                    }

                })

                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // removes the AlertDialog in the screen
                    }
                })

                .show();

    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (this.isVisible()) {
            if (isVisibleToUser && !hasViewCreated) {
                bundle = getArguments();
                if (bundle != null) {
                    searchString = bundle.getString("searchText1");
                    System.out.println("Auction" + searchString);
                    getSearchAuction(searchString);
                }
                hasViewCreated = true;
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        //getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem searchMenuItem = menu.findItem(R.id.action_search);
        /*mSearchView = (SearchView) searchMenuItem.getActionView();
        searchMenuItem.expandActionView();
        setupSearchView();*/

        final EditText editText = (EditText) searchMenuItem.getActionView().findViewById(R.id.search);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //fillter(s.toString());
                //getSearchResults(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                //fillter(s.toString());
                Log.i("Strings", "-->" + s.toString());
                getSearchAuction(s.toString());
            }
        });

        MenuItemCompat.setOnActionExpandListener(searchMenuItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                if (!TextUtils.isEmpty(editText.getText())) {
                    editText.getText().clear();
                    // fillter(editText.getText().toString().trim());
                }
                //hideSoftKeyboard(getActivity());
                return true;
            }
        });
    }
}
