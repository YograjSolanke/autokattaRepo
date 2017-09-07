package autokatta.com.search;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import autokatta.com.adapter.SearchPersonAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.networkreceiver.ConnectionDetector;
import autokatta.com.other.CustomToast;
import autokatta.com.response.SearchPersonResponse;
import retrofit2.Response;

/**
 * Created by ak-001 on 19/4/17.
 */

public class SearchPerson extends Fragment implements RequestNotifier {
    View mSearchPerson;
    private RecyclerView searchList;

    String searchString;
    private List<SearchPersonResponse.Success> allSearchDataArrayList = new ArrayList<>();
    private List<SearchPersonResponse.Success> allSearchDataArrayList_new;
    boolean[] checkedValues;
    boolean hasViewCreated = false;
    TextView mNoData;
    String myContact;
    ImageView filterImg;
    ArrayList<String> cityList = new ArrayList<>();
    HashSet<String> citySet;
    SearchPersonAdapter adapter;
    ConnectionDetector mConnectionDetector;
    //private ProgressDialog dialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mSearchPerson = inflater.inflate(R.layout.fragment_search_store_list, container, false);
        setHasOptionsMenu(true);
        return mSearchPerson;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mNoData = (TextView) mSearchPerson.findViewById(R.id.no_category);
        //mNoData.setVisibility(View.GONE);
        searchList = (RecyclerView) mSearchPerson.findViewById(R.id.searchlist);
        filterImg = (ImageView) mSearchPerson.findViewById(R.id.filterimg);
        filterImg.setVisibility(View.GONE);

        myContact = getActivity().getSharedPreferences(getString(R.string.my_preference), Context.MODE_PRIVATE)
                .getString("loginContact", "");

        searchList.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        searchList.setLayoutManager(mLayoutManager);

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    /*dialog = new ProgressDialog(getActivity());
                    dialog.setMessage("Loading...");*/

                    mConnectionDetector = new ConnectionDetector(getActivity());
                    Bundle bundle = getArguments();
                    if (bundle != null) {
                        searchString = bundle.getString("searchText1");
                        System.out.println("Person" + searchString);
                        getSearchResults(searchString);
                    }
                    filterImg.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            filterData(citySet.toArray(new String[citySet.size()]));
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void getSearchResults(String searchString) {
        if (mConnectionDetector.isConnectedToInternet()) {
            //dialog.show();
            ApiCall mApiCall = new ApiCall(getActivity(), this);
            mApiCall.getPersonSearchData(searchString, myContact);
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
                SearchPersonResponse contactResponse = (SearchPersonResponse) response.body();
                if (contactResponse != null) {
                    if (!contactResponse.getSuccess().isEmpty()) {
                        mNoData.setVisibility(View.GONE);
                        allSearchDataArrayList.clear();
                        filterImg.setVisibility(View.VISIBLE);
                        for (SearchPersonResponse.Success success : contactResponse.getSuccess()) {
                            success.setUsername(success.getUsername());
                            success.setCity(success.getCity());
                            success.setContact(success.getContact());
                            success.setMystatus(success.getMystatus());
                            success.setProfilePhoto(success.getProfilePhoto());
                            success.setStatus(success.getStatus());

                            if (!cityList.contains(success.getCity()))
                                cityList.add(success.getCity());

                            allSearchDataArrayList.add(success);
                        }
                        //For unique data
                        citySet = new HashSet<>(cityList);
                        checkedValues = new boolean[citySet.size()];
                        Arrays.fill(checkedValues, Boolean.TRUE);

                        adapter = new SearchPersonAdapter(getActivity(), allSearchDataArrayList);
                        searchList.setAdapter(adapter);
                    } else {
                        mNoData.setVisibility(View.VISIBLE);
                        filterImg.setVisibility(View.GONE);
                    }
                } else {
                    mNoData.setVisibility(View.VISIBLE);
                    filterImg.setVisibility(View.GONE);
                }
            } else {
                if (isAdded())
                CustomToast.customToast(getActivity(), getString(R.string._404));
            }
        } else {
            if (isAdded())
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
            Log.i("Check Class-", "SearchPerson Fragment");
        }

    }

    @Override
    public void notifyString(String str) {

    }

    /*
    Filter Data...
     */
    public void filterData(final String[] incomingCity) {
        final List<String> mSelectedItems = new ArrayList<>();
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
        // set the dialog title
        builder.setTitle("Please Select City/Location to filter!!")
                .setCancelable(true)
                .setMultiChoiceItems(incomingCity, checkedValues, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if (isChecked) {
                            // mSelectedItems.add(incomingCategory[which]);
                            checkedValues[which] = true;
                        } else if (mSelectedItems.contains(incomingCity[which])) {
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
                                mSelectedItems.add(incomingCity[y]);
                        }
                        Set<String> selectedItemSet = new HashSet<>(mSelectedItems);
                        //all checkboxes selected
                        searchList.setAdapter(null);
                        for (int i = 0; i < allSearchDataArrayList.size(); i++) {
                            //simplify code
                            allSearchDataArrayList.get(i).contactVisibility = selectedItemSet.contains(allSearchDataArrayList.get(i).getCity().trim());
                        }

                        allSearchDataArrayList_new = new ArrayList<>();
                        for (int w = 0; w < allSearchDataArrayList.size(); w++) {
                            if (allSearchDataArrayList.get(w).contactVisibility) {
                                allSearchDataArrayList_new.add(allSearchDataArrayList.get(w));
                            }
                        }

                        adapter = new SearchPersonAdapter(getActivity(), allSearchDataArrayList_new);
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
                Bundle bundle = getArguments();
                if (bundle != null) {
                    searchString = bundle.getString("searchText1");
                    System.out.println("Person" + searchString);
                    getSearchResults(searchString);
                }
                hasViewCreated = true;
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getSearchResults(searchString);
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
                getSearchResults(s.toString());
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
