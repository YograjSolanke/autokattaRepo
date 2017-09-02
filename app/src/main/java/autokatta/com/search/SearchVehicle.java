package autokatta.com.search;

import android.content.Context;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.adapter.VehicleSearchAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.networkreceiver.ConnectionDetector;
import autokatta.com.other.CustomToast;
import autokatta.com.response.SearchVehicleResponse;
import retrofit2.Response;

import static autokatta.com.R.id.search;

/**
 * Created by ak-001 on 19/4/17.
 */

public class SearchVehicle extends Fragment implements RequestNotifier {
    View mSearchVehicle;
    private ListView searchList;
    String searchString;
    private List<SearchVehicleResponse.Success> allSearchDataArrayList = new ArrayList<>();
    String myContact;
    ImageView filterImg;
    Button advanceSearch;
    Bundle bundle;
    boolean hasViewCreated = false;
    TextView mNoData;
    ConnectionDetector mConnectionDetector;
    //private ProgressDialog dialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mSearchVehicle = inflater.inflate(R.layout.fragment_search_product, container, false);
        setHasOptionsMenu(true);
        return mSearchVehicle;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mNoData = (TextView) mSearchVehicle.findViewById(R.id.no_category);
        //mNoData.setVisibility(View.GONE);
        searchList = (ListView) mSearchVehicle.findViewById(R.id.searchlist);
        filterImg = (ImageView) mSearchVehicle.findViewById(R.id.filterimg);
        advanceSearch = (Button) mSearchVehicle.findViewById(R.id.advBtn);
        filterImg.setVisibility(View.GONE);
        advanceSearch.setVisibility(View.GONE);

        myContact = getActivity().getSharedPreferences(getString(R.string.my_preference), Context.MODE_PRIVATE)
                .getString("loginContact", "");

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    /*dialog = new ProgressDialog(getActivity());
                    dialog.setMessage("Loading...");*/

                    mConnectionDetector = new ConnectionDetector(getActivity());
                    bundle = getArguments();
                    if (bundle != null) {
                        searchString = bundle.getString("searchText1");
                        System.out.println("Vehicle" + searchString);
                        getSearchResults(searchString);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        advanceSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FilterFragment filterActivity = new FilterFragment();
                Bundle b = new Bundle();
                b.putString("action", "Main");
                filterActivity.setArguments(b);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.search_product, filterActivity, "filteractivity")
                        .addToBackStack("filteractivity")
                        .commit();
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
                    System.out.println("Vehicle" + searchString);
                    getSearchResults(searchString);
                }
                hasViewCreated = true;
            }
        }
    }
    /*
    search Results...
     */
    private void getSearchResults(String searchString) {

        if (mConnectionDetector.isConnectedToInternet()) {
            //dialog.show();
            ApiCall mApiCall = new ApiCall(getActivity(), this);
            mApiCall.getVehicleSearchData(searchString, myContact);
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
                SearchVehicleResponse vehicleResponse = (SearchVehicleResponse) response.body();
                if (!vehicleResponse.getSuccess().isEmpty()) {
                    mNoData.setVisibility(View.GONE);
                    allSearchDataArrayList.clear();
                    advanceSearch.setVisibility(View.VISIBLE);
                    for (SearchVehicleResponse.Success success : vehicleResponse.getSuccess()) {
                      //  try {
                            success.setVehicleId(success.getVehicleId());
                            success.setTitle(success.getTitle());
                            success.setCategory(success.getCategory());
                            success.setModel(success.getModel());
                            success.setManufacturer(success.getManufacturer());
                            success.setRTOCity(success.getRTOCity());
                            success.setLocationCity(success.getLocationCity());
                            success.setLocationState(success.getLocationState());
                            success.setLocationCountry(success.getLocationCountry());
                            success.setLocationAddress(success.getLocationAddress());
                            success.setYearOfRegistration(success.getYearOfRegistration());
                            success.setYearOfManufacture(success.getYearOfManufacture());
                            success.setContact(success.getContact());
                            success.setColor(success.getColor());
                            success.setRegistrationNumber(success.getRegistrationNumber());
                            success.setRcAvailable(success.getRcAvailable());
                            success.setInsuranceValid(success.getInsuranceValid());
                            success.setInsuranceIdv(success.getInsuranceIdv());
                            success.setTaxValidity(success.getTaxValidity());
                            success.setFitnessValidity(success.getFitnessValidity());
                            success.setPermitValidity(success.getPermitValidity());
                            success.setFualType(success.getFualType());
                            success.setSeatingCapacity(success.getSeatingCapacity());
                            success.setPermit(success.getPermit());
                            success.setKmsRunning(success.getKmsRunning());
                            success.setNoOfOwners(success.getNoOfOwners());
                            success.setHypothication(success.getHypothication());
                            success.setEngineNo(success.getEngineNo());
                            success.setChassisNo(success.getChassisNo());
                            success.setPermit(success.getPrice());
                            success.setImage(success.getImage());
                            success.setDrive(success.getDrive());
                            success.setTransmission(success.getTransmission());
                            success.setBodyType(success.getBodyType());
                            success.setBoatType(success.getBoatType());
                            success.setRvType(success.getRvType());
                            success.setApplication(success.getApplication());
                            success.setStatus(success.getStatus());
                            success.setVehiclelikestatus(success.getVehiclelikestatus());
                            success.setUsername(success.getUsername());
                            success.setBuyerLeads(success.getBuyerLeads());

                           // String dates = success.getDate();
                          //  DateFormat f = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                           // Date d = f.parse(dates);
                            success.setDate(success.getDate());

                            allSearchDataArrayList.add(success);

                      /*  } catch (Exception e) {
                            e.printStackTrace();
                        }*/
                    }
                    Log.e("----------------", String.valueOf(allSearchDataArrayList.size()));
                    VehicleSearchAdapter adapter = new VehicleSearchAdapter(getActivity(), allSearchDataArrayList);
                    searchList.setAdapter(adapter);


                } else {
                    mNoData.setVisibility(View.VISIBLE);
                    advanceSearch.setVisibility(View.GONE);
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
            Log.i("Check Class-", "SearchVehicle Fragment");
        }


    }

    @Override
    public void notifyString(String str) {

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        //getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem searchMenuItem = menu.findItem(R.id.action_search);
        /*mSearchView = (SearchView) searchMenuItem.getActionView();
        searchMenuItem.expandActionView();
        setupSearchView();*/

        final EditText editText = (EditText) searchMenuItem.getActionView().findViewById(search);
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
