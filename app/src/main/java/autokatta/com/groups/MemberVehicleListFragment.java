package autokatta.com.groups;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
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
import android.widget.Toast;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.adapter.GroupVehicleRefreshAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.OnLoadMoreListener;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.networkreceiver.ConnectionDetector;
import autokatta.com.other.CustomToast;
import autokatta.com.other.VerticalLineDecorator;
import autokatta.com.response.GetGroupVehiclesResponse;
import autokatta.com.response.GetGroupVehiclesResponse.Success;
import autokatta.com.response.GetRTOCityResponse;
import retrofit2.Response;

/**
 * Created by ak-005 on 22/5/17.
 */

public class MemberVehicleListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, RequestNotifier {
    View mGroupVehicleList;
    String brand = "", model = "", version = "", city = "", RTOcity = "", reg_year = "",
            mgf_year = "", price = "", kmsrunning = "";
    EditText editbrand, editmodel, editversion, editcity, editregyr, editmgfyr, editprice, editkms, editowner;
    AutoCompleteTextView editRTOcity;
    ImageView filterimg;
    RelativeLayout relativefilter;
    List<Success> mSuccesses = new ArrayList<>();
    SwipeRefreshLayout mSwipeRefreshLayout;
    RecyclerView mRecyclerView;
    GroupVehicleRefreshAdapter mGroupVehicleRefreshAdapter;
    LinearLayoutManager mLayoutManager;
    // on scroll
    ApiCall mApiCall;
    int mGroupId = 0,no_of_owner = 0;
    String className = "";
    Button goSearch;
    Bundle getBundle;
    String Rcontact;
    ConnectionDetector mTestConnection;
    Activity activity;
    boolean hasVisible = false;
    TextView mNoData;
    int index = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mGroupVehicleList = inflater.inflate(R.layout.member_vehicle_list_fragment, container, false);
        return mGroupVehicleList;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mApiCall = new ApiCall(getActivity(), this);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                getRtoCity();
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

                mGroupVehicleRefreshAdapter = new GroupVehicleRefreshAdapter(getActivity(), mSuccesses, mGroupId);
                mGroupVehicleRefreshAdapter.setLoadMoreListener(new OnLoadMoreListener() {
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
                mRecyclerView.setHasFixedSize(true);
                mLayoutManager = new LinearLayoutManager(getActivity());
                //mLayoutManager.setReverseLayout(true);
                //mLayoutManager.setStackFromEnd(true);
                mRecyclerView.setLayoutManager(mLayoutManager);
                mRecyclerView.addItemDecoration(new VerticalLineDecorator(2));
                mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                mRecyclerView.setAdapter(mGroupVehicleRefreshAdapter);
                //getData();//Get Api...
                mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                        android.R.color.holo_green_light,
                        android.R.color.holo_orange_light,
                        android.R.color.holo_red_light);
                mSwipeRefreshLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        mSwipeRefreshLayout.setRefreshing(true);
                        mTestConnection = new ConnectionDetector(getActivity());
                        //getGroupVehicles();
                        if (className != null && !className.equalsIgnoreCase("MemberListRefreshAdapter")) {
                            getGroupVehicles("", 1, 10);
                        } else if (className != null && className.equalsIgnoreCase("MemberListRefreshAdapter")) {
                            Rcontact = getBundle.getString("Rcontact");
                            getGroupVehicles(Rcontact, 1, 10);
                        }
                    }
                });
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            getBundle = getArguments();
                            className = getBundle.getString("className");
                            mGroupId = getBundle.getInt("bundle_GroupId");
                            if (className != null && !className.equalsIgnoreCase("MemberListRefreshAdapter")) {
                                getGroupVehicles("", 1, 10);
                            } else if (className != null && className.equalsIgnoreCase("MemberListRefreshAdapter")) {
                                String Rcontact = getBundle.getString("Rcontact");
                                getGroupVehicles(Rcontact, 1, 10);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

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
                            no_of_owner = Integer.parseInt(editowner.getText().toString().trim());

                        if (brand.equals("") && model.equals("") && version.equals("") && city.equals("") && RTOcity.equals("")
                                && price.equals("") && reg_year.equals("") && mgf_year.equals("") && kmsrunning.equals("")
                                && no_of_owner==0) {
                            Snackbar.make(v, "Enter value to search", Snackbar.LENGTH_SHORT).show();
                        } else {
                            getGroupVehicles("", 1, 10);
                        }
                    }
                });
            }
        });
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    private void loadMore(int index) {
        //add loading progress view
        mGroupVehicleRefreshAdapter.notifyItemInserted(mSuccesses.size());
        if (className != null && !className.equalsIgnoreCase("MemberListRefreshAdapter")) {
            getGroupVehicles("", index, 10);
        } else if (className != null && className.equalsIgnoreCase("MemberListRefreshAdapter")) {
            Rcontact = getBundle.getString("Rcontact");
            getGroupVehicles(Rcontact, index, 10);
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (this.isVisible()) {
            if (isVisibleToUser && !hasVisible) {
                if (className != null && !className.equalsIgnoreCase("MemberListRefreshAdapter")) {
                    getGroupVehicles("", 1, 10);
                } else if (className != null && className.equalsIgnoreCase("MemberListRefreshAdapter")) {
                    Rcontact = getBundle.getString("Rcontact");
                    getGroupVehicles(Rcontact, 1, 10);
                }
            }
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
    rcontact is used for geting vehicles of rcontact in group
    if rcontact is empty group vehicles comes
     */
    private void getGroupVehicles(String rcontact, int pageNo, int viewRecords) {
        if (mTestConnection.isConnectedToInternet()) {
            mApiCall.getGroupVehicles(mGroupId, brand, model, version, city, RTOcity, price, reg_year, mgf_year, kmsrunning,
                    no_of_owner, rcontact, pageNo, viewRecords);
        } else {
            if (isAdded())
            CustomToast.customToast(getActivity(), getString(R.string.no_internet));
        }

    }

    @Override
    public void notifySuccess(Response<?> response) {
        if (response != null) {
            if (response.isSuccessful()) {
                mSwipeRefreshLayout.setRefreshing(false);
                if (response.body() instanceof GetGroupVehiclesResponse) {
                    //mSuccesses.clear();
                    GetGroupVehiclesResponse mGetGroupVehiclesResponse = (GetGroupVehiclesResponse) response.body();
                    if (mGetGroupVehiclesResponse.getSuccess().size() > 0) {
                        if (!mGetGroupVehiclesResponse.getSuccess().isEmpty()) {
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

                        /*if (mSuccesses.size() != 0) {
                            //mGroupVehicleRefreshAdapter.notifyDataSetChanged();
                        }
                        mSwipeRefreshLayout.setVisibility(View.VISIBLE);
                        relativefilter.setVisibility(View.GONE);*/
                        } else {
                            mNoData.setVisibility(View.VISIBLE);
                            mSwipeRefreshLayout.setRefreshing(false);
                            mSwipeRefreshLayout.setVisibility(View.VISIBLE);
                            relativefilter.setVisibility(View.GONE);
                        }
                    } else {//result size 0 means there is no more data available at server
                        mGroupVehicleRefreshAdapter.setMoreDataAvailable(false);
                        //telling adapter to stop calling load more as no more server data available
                        if (isAdded())
                            Toast.makeText(getActivity(), "No More Data Available", Toast.LENGTH_LONG).show();
                    }
                    mGroupVehicleRefreshAdapter.notifyDataChanged();
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
                    if (getActivity() != null) {
                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getActivity(),
                                R.layout.registration_spinner, mRtoCity);
                        editRTOcity.setAdapter(dataAdapter);
                    }
                }
            } else {
                mSwipeRefreshLayout.setRefreshing(false);
                // CustomToast.customToast(getActivity(), getString(R.string._404_));
            }
        } else {
            mSwipeRefreshLayout.setRefreshing(false);
            if (isAdded())
            CustomToast.customToast(getActivity(), getString(R.string.no_response));
        }
    }

    @Override
    public void notifyError(Throwable error) {
        mSwipeRefreshLayout.setRefreshing(false);
        if (error instanceof SocketTimeoutException) {
            if (isAdded())
            CustomToast.customToast(getActivity(), getString(R.string._404_));
        } else if (error instanceof NullPointerException) {
            //CustomToast.customToast(getActivity(), getString(R.string.no_response));
        } else if (error instanceof ClassCastException) {
            //  CustomToast.customToast(getActivity(), getString(R.string.no_response));
        } else if (error instanceof ConnectException) {
            if (isAdded())
            CustomToast.customToast(getActivity(), getString(R.string.no_internet));
        } else if (error instanceof UnknownHostException) {
            if (isAdded())
            CustomToast.customToast(getActivity(), getString(R.string.no_internet));
        } else {
            Log.i("Check Class-"
                    , "membervehiclelistfragment");
            error.printStackTrace();
        }
    }

    @Override
    public void notifyString(String str) {

    }

    @Override
    public void onRefresh() {
        if (className != null && !className.equalsIgnoreCase("MemberListRefreshAdapter")) {
            getGroupVehicles("", 1, 10);
        } else if (className != null && className.equalsIgnoreCase("MemberListRefreshAdapter")) {
            String Rcontact = getBundle.getString("Rcontact");
            getGroupVehicles(Rcontact, 1, 10);
        }
    }

}
