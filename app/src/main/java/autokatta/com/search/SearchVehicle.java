package autokatta.com.search;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import autokatta.com.R;
import autokatta.com.adapter.VehicleSearchAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.response.SearchVehicleResponse;
import retrofit2.Response;

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mSearchVehicle = inflater.inflate(R.layout.fragment_search_product, container, false);

        return mSearchVehicle;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mNoData = (TextView) mSearchVehicle.findViewById(R.id.no_category);
        mNoData.setVisibility(View.GONE);

        searchList = (ListView) mSearchVehicle.findViewById(R.id.searchlist);
        filterImg = (ImageView) mSearchVehicle.findViewById(R.id.filterimg);
        advanceSearch = (Button) mSearchVehicle.findViewById(R.id.advBtn);
        filterImg.setVisibility(View.GONE);
        advanceSearch.setVisibility(View.VISIBLE);

        myContact = getActivity().getSharedPreferences(getString(R.string.my_preference), Context.MODE_PRIVATE)
                .getString("loginContact", "");

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    bundle = getArguments();
                    if (bundle != null) {
                        searchString = bundle.getString("searchText1");
                        System.out.println("Vehicle" + searchString);
                        getSearchResults(searchString);
                    }
                    advanceSearch.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            FilterFragment filterActivity = new FilterFragment();
                            Bundle b=new Bundle();
                            b.putString("action", "Main");
                            filterActivity.setArguments(b);
                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.search_product, filterActivity);
                            fragmentTransaction.addToBackStack("filteractivity");
                            fragmentTransaction.commit();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
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
        ApiCall mApiCall = new ApiCall(getActivity(), this);
        mApiCall.getVehicleSearchData(searchString, myContact);
    }

    @Override
    public void notifySuccess(Response<?> response) {
        if (response != null) {
            if (response.isSuccessful()) {
                SearchVehicleResponse vehicleResponse = (SearchVehicleResponse) response.body();
                if (!vehicleResponse.getSuccess().isEmpty()) {
                    mNoData.setVisibility(View.GONE);
                    allSearchDataArrayList.clear();
                    for (SearchVehicleResponse.Success success : vehicleResponse.getSuccess()) {
                        try {
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

                            String dates = success.getDate();
                            DateFormat f = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                            Date d = f.parse(dates);
                            success.setDate(d.toString());

                            allSearchDataArrayList.add(success);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    VehicleSearchAdapter adapter = new VehicleSearchAdapter(getActivity(), allSearchDataArrayList);
                    searchList.setAdapter(adapter);

                } else
                    mNoData.setVisibility(View.VISIBLE);
            } else {
                // Snackbar.make(getView(), getString(R.string._404), Snackbar.LENGTH_SHORT);
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
            Log.i("Check Class-", "SearchVehicle Fragment");
        }


    }

    @Override
    public void notifyString(String str) {

    }
}
