package autokatta.com.groups;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.adapter.GroupVehicleRefreshAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.networkreceiver.ConnectionDetector;
import autokatta.com.other.CustomToast;
import autokatta.com.other.EndlessRecyclerOnScrollListener;
import autokatta.com.response.GetGroupVehiclesResponse;
import autokatta.com.response.GetRTOCityResponse;
import retrofit2.Response;

/**
 * Created by ak-001 on 24/3/17.
 */

public class GroupVehicleList extends Fragment implements SwipeRefreshLayout.OnRefreshListener, RequestNotifier {
    View mGroupVehicleList;
    String brand = "", model = "", version = "", city = "", RTOcity = "", reg_year = "",
            mgf_year = "", price = "", kmsrunning = "";
    int no_of_owner = 0;
    EditText editbrand, editmodel, editversion, editcity, editregyr, editmgfyr, editprice, editkms, editowner;
    AutoCompleteTextView editRTOcity;
    ImageView filterimg;
    RelativeLayout relativefilter;
    List<GetGroupVehiclesResponse.Success> mSuccesses = new ArrayList<>();
    SwipeRefreshLayout mSwipeRefreshLayout;
    RecyclerView mRecyclerView;
    GroupVehicleRefreshAdapter mGroupVehicleRefreshAdapter;
    LinearLayoutManager mLayoutManager;
    ApiCall mApiCall;
    int mGroupId = 0;
    String className = "";
    Button goSearch;
    TextView mNoData;
    Bundle getBundle;
    ConnectionDetector mTestConnection;
    boolean _hasLoadedOnce = false;
    Activity activity;

    public GroupVehicleList() {
        //empty constructor...
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mGroupVehicleList = inflater.inflate(R.layout.fragment_group_vehicle_list, container, false);
        return mGroupVehicleList;
    }

    /*
    get Owned vehicles
     */
    private void getMyVehicles(String rcontact) {
        if (mTestConnection.isConnectedToInternet()) {
            mApiCall.getMyVehicles(rcontact);
        } else {
            if (isAdded())
            CustomToast.customToast(getActivity(), getString(R.string.no_internet));
        }
    }

    /*
    Rto city
     */
    private void getRtoCity() {
        mApiCall.getVehicleRTOCity();
    }

    /*
    Group Vehicle List...
    rcontact is used to get user vehicles in group
     */
    private void getGroupVehicles(String rcontact) {
        try {
            if (mTestConnection.isConnectedToInternet()) {
                mApiCall.getGroupVehicles(mGroupId, brand, model, version, city, RTOcity, price, reg_year, mgf_year, kmsrunning, no_of_owner, rcontact);
            } else {
                if (isAdded())
                    CustomToast.customToast(getActivity(), getString(R.string.no_internet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void notifySuccess(Response<?> response) {
        if (response != null) {
            if (response.isSuccessful()) {
                mSwipeRefreshLayout.setRefreshing(false);
                if (response.body() instanceof GetGroupVehiclesResponse) {
                    mSuccesses.clear();
                    GetGroupVehiclesResponse mGetGroupVehiclesResponse = (GetGroupVehiclesResponse) response.body();
                    if (mGetGroupVehiclesResponse.getSuccess() != null || !mGetGroupVehiclesResponse.getSuccess().isEmpty()) {
                        mNoData.setVisibility(View.GONE);
                        for (GetGroupVehiclesResponse.Success success : mGetGroupVehiclesResponse.getSuccess()) {
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
                            success.setPrice(success.getPrice());
                            success.setImage(success.getImage());
                            success.setDrive(success.getDrive());
                            success.setTransmission(success.getTransmission());
                            success.setBodyType(success.getBodyType());
                            success.setBoatType(success.getBoatType());
                            success.setRvType(success.getRvType());
                            success.setApplication(success.getApplication());
                            success.setStatus(success.getStatus());
                            success.setDate(success.getDate());
                            success.setVehiclelikestatus(success.getVehiclelikestatus());
                            success.setUsername(success.getUsername());

                            String img = success.getImage();
                            String firstWord = "", all = "";
                            if (img.contains(",")) {
                                String arr[] = img.split(",", 2);
                                firstWord = arr[0].replaceAll(" ", "");
                                success.setSingleImage(firstWord);
                                all = img.replace(",", "/ ");
                                success.setAllImage(all);
                            } else {
                                success.setSingleImage(img);
                                success.setAllImage(all);
                            }
                            mSuccesses.add(success);
                        }
                        mGroupVehicleRefreshAdapter = new GroupVehicleRefreshAdapter(getActivity(), mSuccesses);
                        if (mSuccesses.size() != 0) {
                            mRecyclerView.setAdapter(mGroupVehicleRefreshAdapter);
                            mGroupVehicleRefreshAdapter.notifyDataSetChanged();
                        }
                        mSwipeRefreshLayout.setVisibility(View.VISIBLE);
                        relativefilter.setVisibility(View.GONE);

                    } else {
                        mNoData.setVisibility(View.VISIBLE);
                        mSwipeRefreshLayout.setRefreshing(false);
                        mSwipeRefreshLayout.setVisibility(View.VISIBLE);
                        relativefilter.setVisibility(View.GONE);
                    }


                }
                //Rto city
                else if (response.body() instanceof GetRTOCityResponse) {
                    final List<String> mRtoCity = new ArrayList<String>();
                    mRtoCity.add("-Select RTO city-");
                    GetRTOCityResponse mGetRTOCityResponse = (GetRTOCityResponse) response.body();
                    for (GetRTOCityResponse.Success success : mGetRTOCityResponse.getSuccess()) {
                        /*success.setRtoCityId(success.getRtoCityId());
                        success.setRtoCityName(success.getRtoCityName());
                        mRtoCity.add(success.getRtoCode());*/

                        mRtoCity.add(success.getRtoCode() + " " +
                                success.getRtoCityName());
                    }
                    if (isAdded()) {
                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getActivity(),
                                R.layout.registration_spinner, mRtoCity);
                        editRTOcity.setAdapter(dataAdapter);
                    }
                }
            } else {
                mSwipeRefreshLayout.setRefreshing(false);
//                if (isAdded())
//                    CustomToast.customToast(getActivity(), getString(R.string._404_));
            }
        } else {
            mSwipeRefreshLayout.setRefreshing(false);
            if (isAdded())
                CustomToast.customToast(getActivity(), getString(R.string.no_response));
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            if (activity != null) {
                activity = getActivity();
            }
        }
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
                    , "GroupVehicleList");
            error.printStackTrace();
        }
    }

    @Override
    public void notifyString(String str) {

    }

    @Override
    public void onRefresh() {
        mSuccesses.clear();
        if (className != null && !className.equalsIgnoreCase("MemberListRefreshAdapter")) {
            getGroupVehicles("");
        } else if (className != null && className.equalsIgnoreCase("MemberListRefreshAdapter")) {
            String Rcontact = getBundle.getString("Rcontact");
            getGroupVehicles(Rcontact);
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (this.isVisible()) {
            if (isVisibleToUser && !_hasLoadedOnce) {
                if (className != null && !className.equalsIgnoreCase("MemberListRefreshAdapter")) {
                    getGroupVehicles("");
                } else if (className != null && className.equalsIgnoreCase("MemberListRefreshAdapter")) {
                    String Rcontact = getBundle.getString("Rcontact");
                    getGroupVehicles(Rcontact);
                }
                _hasLoadedOnce = true;
            }
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTestConnection = new ConnectionDetector(getActivity());
                mRecyclerView = (RecyclerView) mGroupVehicleList.findViewById(R.id.rv_recycler_view);
                editbrand = (EditText) mGroupVehicleList.findViewById(R.id.editbrand);
                editmodel = (EditText) mGroupVehicleList.findViewById(R.id.editmodel);
                editversion = (EditText) mGroupVehicleList.findViewById(R.id.editversion);
                editcity = (EditText) mGroupVehicleList.findViewById(R.id.editcity);
                editRTOcity = (AutoCompleteTextView) mGroupVehicleList.findViewById(R.id.editrtocity);
                editregyr = (EditText) mGroupVehicleList.findViewById(R.id.editregyear);
                editmgfyr = (EditText) mGroupVehicleList.findViewById(R.id.editmgfyear);
                editprice = (EditText) mGroupVehicleList.findViewById(R.id.editprice);
                editkms = (EditText) mGroupVehicleList.findViewById(R.id.editkmsrun);
                editowner = (EditText) mGroupVehicleList.findViewById(R.id.editowners);
                filterimg = (ImageView) mGroupVehicleList.findViewById(R.id.filterimg);
                relativefilter = (RelativeLayout) mGroupVehicleList.findViewById(R.id.relative_filter);
                goSearch = (Button) mGroupVehicleList.findViewById(R.id.goSearch);

                mNoData = (TextView) mGroupVehicleList.findViewById(R.id.no_category);
                mNoData.setVisibility(View.GONE);
                mSwipeRefreshLayout = (SwipeRefreshLayout) mGroupVehicleList.findViewById(R.id.swipeRefreshLayout);
                mRecyclerView.setHasFixedSize(true);
                mLayoutManager = new LinearLayoutManager(getActivity());
                mLayoutManager.setReverseLayout(true);
                mLayoutManager.setStackFromEnd(true);
                mRecyclerView.setLayoutManager(mLayoutManager);
                //getData();//Get Api...
                mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                        android.R.color.holo_green_light,
                        android.R.color.holo_orange_light,
                        android.R.color.holo_red_light);
                mSwipeRefreshLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        mSwipeRefreshLayout.setRefreshing(true);
                        //getGroupVehicles();
                        if (className != null && !className.equalsIgnoreCase("MemberListRefreshAdapter")) {
                            getGroupVehicles("");
                        } else if (className != null && className.equalsIgnoreCase("MemberListRefreshAdapter")) {
                            String Rcontact = getBundle.getString("Rcontact");
                            getGroupVehicles(Rcontact);
                        }
                    }
                });

                try {
                    getBundle = getArguments();
                    className = getBundle.getString("className");
                    mGroupId = getBundle.getInt("bundle_GroupId");
                    if (className != null && !className.equalsIgnoreCase("MemberListRefreshAdapter")) {
                        getGroupVehicles("");
                    } else if (className != null && className.equalsIgnoreCase("MemberListRefreshAdapter")) {
                        String Rcontact = getBundle.getString("Rcontact");
                        getGroupVehicles(Rcontact);
                    }
                    getRtoCity();

                /*
                Recycler View OnScrollChanged Listener...
                 */
                    mRecyclerView.setOnScrollListener(new EndlessRecyclerOnScrollListener(mLayoutManager) {
                        @Override
                        public void onLoadMore(int current_page) {
                            Log.i("Loading", "->");
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }


                filterimg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (mSwipeRefreshLayout.getVisibility() == View.VISIBLE) {
                            mSwipeRefreshLayout.setVisibility(View.GONE);
                            relativefilter.setVisibility(View.VISIBLE);
                            mNoData.setVisibility(View.GONE);
                        } else {
                            mSwipeRefreshLayout.setVisibility(View.VISIBLE);
                            relativefilter.setVisibility(View.GONE);
                            mNoData.setVisibility(View.GONE);
                        }
                    }
                });

                /*
                get Relative layout data...
                 */

                goSearch.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub

                        brand = editbrand.getText().toString().trim();
                        model = editmodel.getText().toString().trim();
                        version = editversion.getText().toString().trim();
                        city = editcity.getText().toString().trim();
                        RTOcity = editRTOcity.getText().toString().trim();
                        price = editprice.getText().toString().trim();
                        reg_year = editregyr.getText().toString().trim();
                        mgf_year = editmgfyr.getText().toString().trim();
                        kmsrunning = editkms.getText().toString().trim();
                        if (editowner.getText().toString().equals(""))
                            no_of_owner = 0;
                        else
                            no_of_owner = Integer.parseInt(editowner.getText().toString());

                        if (brand.equals("") && model.equals("") && version.equals("") && city.equals("") && RTOcity.equals("")
                                && price.equals("") && reg_year.equals("") && mgf_year.equals("") && kmsrunning.equals("")
                                && no_of_owner == 0) {
                            CustomToast.customToast(getActivity(), "Enter value to search");
                        } else {
                            getGroupVehicles("");
                        }

                    }
                });
            }
        });
        mApiCall = new ApiCall(getActivity(), this);
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

}
