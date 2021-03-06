package autokatta.com.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import autokatta.com.R;
import autokatta.com.adapter.FavouriteNotificationAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import autokatta.com.response.FavouriteAllResponse;
import autokatta.com.response.FavouriteResponse;
import retrofit2.Response;

/**
 * Created by ak-003 on 20/4/17.
 */

public class FavoriteNotificationFragment extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, RequestNotifier {
    RecyclerView mRecyclerView;
    SwipeRefreshLayout mSwipeRefreshLayout;
    TextView mNoData;
    private String mLoginContact = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_simple_listview);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        setTitle("My Favourite");
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mLoginContact = getSharedPreferences(getString(R.string.my_preference), Context.MODE_PRIVATE).
                        getString("loginContact", "");
                mNoData = (TextView) findViewById(R.id.no_category);
                mNoData.setVisibility(View.GONE);

                mRecyclerView = (RecyclerView) findViewById(R.id.recyclerMain);
                mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayoutMain);
                mRecyclerView.setHasFixedSize(true);
                LinearLayoutManager mLayoutManager = new LinearLayoutManager(FavoriteNotificationFragment.this);
                mLayoutManager.setReverseLayout(true);
                mLayoutManager.setStackFromEnd(true);
                mRecyclerView.setLayoutManager(mLayoutManager);

                mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                        android.R.color.holo_green_light,
                        android.R.color.holo_orange_light,
                        android.R.color.holo_red_light);
                mSwipeRefreshLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        mSwipeRefreshLayout.setRefreshing(true);
                        getFavouriteData();
                    }
                });

            }
        });
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    private void getFavouriteData() {
        ApiCall mApiCall = new ApiCall(this, this);
        mApiCall.FavouriteNotification(mLoginContact);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onRefresh() {
        getFavouriteData();
    }

    @Override
    public void notifySuccess(Response<?> response) {
        if (response != null) {
            if (response.isSuccessful()) {
                mNoData.setVisibility(View.GONE);
                List<FavouriteAllResponse> mainList = new ArrayList<>();
                mainList.clear();
                FavouriteResponse favouriteResponse = (FavouriteResponse) response.body();

                //Wall Notification
                if (!favouriteResponse.getSuccess().getNotification().isEmpty()) {
                    for (FavouriteResponse.Success.Notification successNotification : favouriteResponse.getSuccess().getNotification()) {
                        FavouriteAllResponse favouriteAllResponse = new FavouriteAllResponse();
                        favouriteAllResponse.setId(successNotification.getId());
                        favouriteAllResponse.setLayoutNo(successNotification.getLayout());
                        favouriteAllResponse.setSender(successNotification.getSender());
                        favouriteAllResponse.setAction(successNotification.getAction());
                        favouriteAllResponse.setReceiver(successNotification.getReceiver());
                        favouriteAllResponse.setVehicleId(successNotification.getVehicleId());
                        favouriteAllResponse.setProductId(successNotification.getProductId());
                        favouriteAllResponse.setServiceId(successNotification.getServiceId());
                        favouriteAllResponse.setStoreId(successNotification.getStoreId());
                        favouriteAllResponse.setGroupId(successNotification.getGroupId());
                        favouriteAllResponse.setSendername(successNotification.getSendername());
                        favouriteAllResponse.setSenderprofession(successNotification.getSenderprofession());
                        favouriteAllResponse.setSenderwebsite(successNotification.getSenderwebsite());
                        favouriteAllResponse.setSendercity(successNotification.getSendercity());
                        favouriteAllResponse.setSenderlikecount(successNotification.getSenderlikecount());
                        favouriteAllResponse.setSenderfollowcount(successNotification.getSenderfollowcount());
                        favouriteAllResponse.setSenderlikestatus(successNotification.getSenderlikestatus());
                        favouriteAllResponse.setSenderfollowstatus(successNotification.getSenderfollowstatus());
                        favouriteAllResponse.setReceivername(successNotification.getReceivername());
                        favouriteAllResponse.setReceiverPic(successNotification.getReceiverPic());
                        favouriteAllResponse.setReceiverprofession(successNotification.getReceiverprofession());
                        favouriteAllResponse.setReceiverwebsite(successNotification.getReceiverwebsite());
                        favouriteAllResponse.setReceivercity(successNotification.getReceivercity());
                        favouriteAllResponse.setReceiverlikecount(successNotification.getReceiverlikecount());
                        favouriteAllResponse.setReceiverfollowcount(successNotification.getReceiverfollowcount());
                        favouriteAllResponse.setReceiverlikestatus(successNotification.getReceiverlikestatus());
                        favouriteAllResponse.setReceiverfollowstatus(successNotification.getReceiverfollowstatus());
                        favouriteAllResponse.setStorelikestatus(successNotification.getStorelikestatus());
                        favouriteAllResponse.setStorefollowstatus(successNotification.getStorefollowstatus());
                        favouriteAllResponse.setStorerating(successNotification.getStorerating());
                        favouriteAllResponse.setStorelikecount(successNotification.getStorelikecount());
                        favouriteAllResponse.setStorefollowcount(successNotification.getStorefollowcount());
                        favouriteAllResponse.setStoretiming(successNotification.getStoretiming());
                        favouriteAllResponse.setStoreContact(successNotification.getStoreContact());
                        favouriteAllResponse.setStoreName(successNotification.getStoreName());
                        favouriteAllResponse.setStoreImage(successNotification.getStoreImage());
                        favouriteAllResponse.setStoreType(successNotification.getStoreType());
                        favouriteAllResponse.setStoreWebsite(successNotification.getStoreWebsite());
                        favouriteAllResponse.setWorkingDays(successNotification.getWorkingDays());
                        favouriteAllResponse.setStoreLocation(successNotification.getStoreLocation());
                        favouriteAllResponse.setStoreShareCount(successNotification.getStoreShareCount());
                        favouriteAllResponse.setStoreCategory(successNotification.getStoreCategory());
                        favouriteAllResponse.setGroupVehicles(successNotification.getGroupVehicles());
                        favouriteAllResponse.setGroupName(successNotification.getGroupName());
                        favouriteAllResponse.setGroupMembers(successNotification.getGroupMembers());
                        favouriteAllResponse.setGroupImage(successNotification.getGroupImage());
                        favouriteAllResponse.setGroupProductCount(successNotification.getGroupProductCount());
                        favouriteAllResponse.setGroupServiceCount(successNotification.getGroupServiceCount());
                        favouriteAllResponse.setProductlikestatus(successNotification.getProductlikestatus());
                        favouriteAllResponse.setProductfollowstatus(successNotification.getProductfollowstatus());
                        favouriteAllResponse.setProductlikecount(successNotification.getProductlikecount());
                        favouriteAllResponse.setProductfollowcount(successNotification.getProductfollowcount());
                        favouriteAllResponse.setProductName(successNotification.getProductName());
                        favouriteAllResponse.setProductType(successNotification.getProductType());
                        favouriteAllResponse.setProductRating(successNotification.getProductRating());
                        favouriteAllResponse.setProductShareCount(successNotification.getProductShareCount());
                        //favouriteAllResponse.setProductimages(successNotification.getProductimages());
                        String proImage = successNotification.getProductimages();
                        if (proImage.contains(",")) {
                            String[] items = proImage.split(",");
                            favouriteAllResponse.setProductimages(items[0]);
                            /*for (String item : items) {
                                notification.setProductImage(item);
                            }*/
                        } else {
                            favouriteAllResponse.setProductimages(proImage);
                        }
                        favouriteAllResponse.setServicelikestatus(successNotification.getServicelikestatus());
                        favouriteAllResponse.setServicefollowstatus(successNotification.getServicefollowstatus());
                        favouriteAllResponse.setServicelikecount(successNotification.getServicelikecount());
                        favouriteAllResponse.setServicefollowcount(successNotification.getServicefollowcount());
                        favouriteAllResponse.setServiceName(successNotification.getSeriveName());
                        favouriteAllResponse.setServiceType(successNotification.getServiceType());
                        favouriteAllResponse.setServiceRating(successNotification.getServiceRating());
                        favouriteAllResponse.setServiceShareCount(successNotification.getServiceShareCount());
                        //favouriteAllResponse.setServiceimages(successNotification.getServiceimages());
                        String serviceImage = successNotification.getServiceimages();
                        if (serviceImage.contains(",")) {
                            String[] items = serviceImage.split(",");
                            favouriteAllResponse.setServiceimages(items[0]);
                            /*for (String item : items) {
                                notification.setServiceImage(item);
                            }*/
                        } else {
                            favouriteAllResponse.setServiceimages(serviceImage);
                        }

                        favouriteAllResponse.setUpVehicleLikeStatus(successNotification.getUpVehicleLikeStatus());
                        favouriteAllResponse.setUpVehicleFollowStatus(successNotification.getUpVehicleFollowStatus());
                        favouriteAllResponse.setUpVehicleLikeCount(successNotification.getUpVehicleLikeCount());
                        favouriteAllResponse.setUpVehicleFollowCount(successNotification.getUpVehicleFollowCount());
                        favouriteAllResponse.setUpVehicleContact(successNotification.getUpVehicleContact());
                        favouriteAllResponse.setUpVehicleTitle(successNotification.getUpVehicleTitle());
                        favouriteAllResponse.setUpVehicleShareCount(successNotification.getUpVehicleShareCount());
                        favouriteAllResponse.setUpVehiclePrice(successNotification.getUpVehiclePrice());
                        favouriteAllResponse.setUpVehicleModel(successNotification.getUpVehicleModel());
                        favouriteAllResponse.setUpVehicleBrand(successNotification.getUpVehicleBrand());
                        favouriteAllResponse.setUpVehicleManfYear(successNotification.getUpVehicleManfYear());
                        favouriteAllResponse.setUpVehicleRegNo(successNotification.getUpVehicleRegNo());
                        favouriteAllResponse.setUpVehicleKmsRun(successNotification.getUpVehicleKmsRun());
                        favouriteAllResponse.setUpVehicleHrsRun(successNotification.getUpVehicleHrsRun());
                        favouriteAllResponse.setUpVehicleRtoCity(successNotification.getUpVehicleRtoCity());
                        favouriteAllResponse.setUpVehicleLocationCity(successNotification.getUpVehicleLocationCity());
                        String vehicleImage = successNotification.getUpVehicleImage();
                        if (vehicleImage.contains(",")) {
                            String[] items = vehicleImage.split(",");
                            favouriteAllResponse.setUpVehicleImage(items[0]);
                            /*for (String item : items) {
                                notification.setUpVehicleImage(item);
                            }*/
                        } else {
                            favouriteAllResponse.setUpVehicleImage(vehicleImage);
                        }

                        favouriteAllResponse.setSearchId(successNotification.getSearchID());
                        favouriteAllResponse.setSearchLikeStatus(successNotification.getSearchLikeStatus());
                        favouriteAllResponse.setSearchCategory(successNotification.getSearchCategory());
                        favouriteAllResponse.setSearchBrand(successNotification.getSearchBrand());
                        favouriteAllResponse.setSearchModel(successNotification.getSearchModel());
                        favouriteAllResponse.setSearchRtoCity(successNotification.getSearchRtoCity());
                        favouriteAllResponse.setSearchLocationCity(successNotification.getSearchLocationCity());
                        favouriteAllResponse.setSearchColor(successNotification.getSearchColor());
                        favouriteAllResponse.setSearchPrice(successNotification.getSearchPrice());
                        favouriteAllResponse.setSearchManfYear(successNotification.getSearchManfYear());
                        favouriteAllResponse.setSearchDate(successNotification.getSearchDate());
                        favouriteAllResponse.setSearchLeads(successNotification.getSearchLeads());
                        favouriteAllResponse.setFavid(successNotification.getFavid());
                        //favouriteAllResponse.setDate(successNotification.getDate());

                        try {
                            TimeZone utc = TimeZone.getTimeZone("etc/UTC");
                            //format of date coming from services
                            DateFormat inputFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a", Locale.getDefault());
                        /*DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
                                Locale.getDefault());*/
                            inputFormat.setTimeZone(utc);

                            //format of date which we want to show
                            DateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy hh:mm a", Locale.getDefault());
                        /*DateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy hh:mm aa",
                                Locale.getDefault());*/
                            outputFormat.setTimeZone(utc);

                            Date date = inputFormat.parse(successNotification.getDate());
                            //System.out.println("jjj"+date);
                            String output = outputFormat.format(date);
                            //System.out.println(mainList.get(i).getDate()+" jjj " + output);
                            favouriteAllResponse.setDate(output);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        mainList.add(favouriteAllResponse);
                    }
                }

                //BuyerSearch
                if (!favouriteResponse.getSuccess().getBuyerSearch().isEmpty()) {
                    for (FavouriteResponse.Success.BuyerSearch successBuyerSearch : favouriteResponse.getSuccess().getBuyerSearch()) {
                        FavouriteAllResponse favouriteAllResponse = new FavouriteAllResponse();
                        favouriteAllResponse.setLayoutNo("111");
                        favouriteAllResponse.setFavid(successBuyerSearch.getFavid());
                        favouriteAllResponse.setVvehicleId(successBuyerSearch.getVvehicleId());
                        favouriteAllResponse.setVtitle(successBuyerSearch.getVtitle());
                        favouriteAllResponse.setVcontactNo(successBuyerSearch.getVcontactNo());
                        favouriteAllResponse.setVcategory(successBuyerSearch.getVcategory());
                        favouriteAllResponse.setVmodel(successBuyerSearch.getVmodel());
                        favouriteAllResponse.setVmanufacturer(successBuyerSearch.getVmanufacturer());
                        favouriteAllResponse.setVVersion(successBuyerSearch.getVVersion());
                        favouriteAllResponse.setVrtoCity(successBuyerSearch.getVrtoCity());
                        favouriteAllResponse.setVlocationCity(successBuyerSearch.getVlocationCity());
                        favouriteAllResponse.setVlocationState(successBuyerSearch.getVlocationState());
                        favouriteAllResponse.setVlocationCountry(successBuyerSearch.getVlocationCountry());
                        favouriteAllResponse.setVmonthOfRegistration(successBuyerSearch.getVmonthOfRegistration());
                        favouriteAllResponse.setVyearOfRegistration(successBuyerSearch.getVyearOfRegistration());
                        favouriteAllResponse.setVmonthOfManufacture(successBuyerSearch.getVmonthOfManufacture());
                        favouriteAllResponse.setVyearOfManufacture(successBuyerSearch.getVyearOfManufacture());
                        favouriteAllResponse.setVcolor(successBuyerSearch.getVcolor());
                        favouriteAllResponse.setVregistrationNumber(successBuyerSearch.getVregistrationNumber());
                        favouriteAllResponse.setVrcAvailable(successBuyerSearch.getVrcAvailable());
                        favouriteAllResponse.setVinsuranceValid(successBuyerSearch.getVinsuranceValid());
                        favouriteAllResponse.setVinsuranceIdv(successBuyerSearch.getVinsuranceIdv());
                        favouriteAllResponse.setVtaxValidity(successBuyerSearch.getVtaxValidity());
                        favouriteAllResponse.setVtaxPaidUpto(successBuyerSearch.getVtaxPaidUpto());
                        favouriteAllResponse.setVfitnessValidity(successBuyerSearch.getVfitnessValidity());
                        favouriteAllResponse.setVpermitValidity(successBuyerSearch.getVpermitValidity());
                        favouriteAllResponse.setVpermitYesno(successBuyerSearch.getVpermitYesno());
                        favouriteAllResponse.setVfitnessYesno(successBuyerSearch.getVfitnessYesno());
                        favouriteAllResponse.setVfualType(successBuyerSearch.getVfualType());
                        favouriteAllResponse.setVseatingCapacity(successBuyerSearch.getVseatingCapacity());
                        favouriteAllResponse.setVpermit(successBuyerSearch.getVpermit());
                        favouriteAllResponse.setVkmsRunning(successBuyerSearch.getVkmsRunning());
                        favouriteAllResponse.setvHrsRunning(successBuyerSearch.getVHrsRunning());
                        favouriteAllResponse.setVnoOfOwners(successBuyerSearch.getVnoOfOwners());
                        favouriteAllResponse.setVhypothication(successBuyerSearch.getVhypothication());
                        favouriteAllResponse.setVengineNo(successBuyerSearch.getVengineNo());
                        favouriteAllResponse.setVchassisNo(successBuyerSearch.getVchassisNo());
                        favouriteAllResponse.setVprice(successBuyerSearch.getVprice());
                        favouriteAllResponse.setVimage(successBuyerSearch.getVimage());
                        favouriteAllResponse.setVdrive(successBuyerSearch.getVdrive());
                        favouriteAllResponse.setVtransmission(successBuyerSearch.getVtransmission());
                        favouriteAllResponse.setVbodyType(successBuyerSearch.getVbodyType());
                        favouriteAllResponse.setVboatType(successBuyerSearch.getVboatType());
                        favouriteAllResponse.setVrvType(successBuyerSearch.getVrvType());
                        favouriteAllResponse.setVapplication(successBuyerSearch.getVapplication());
                        favouriteAllResponse.setVtyreCondition(successBuyerSearch.getVtyreCondition());
                        favouriteAllResponse.setVbusType(successBuyerSearch.getVbusType());
                        favouriteAllResponse.setVairCondition(successBuyerSearch.getVairCondition());
                        favouriteAllResponse.setVinvoice(successBuyerSearch.getVinvoice());
                        favouriteAllResponse.setVimplements(successBuyerSearch.getVimplements());
                        favouriteAllResponse.setVfinanceReq(successBuyerSearch.getVfinanceReq());
                        favouriteAllResponse.setVprivacy(successBuyerSearch.getVprivacy());
                        favouriteAllResponse.setVviewcount(successBuyerSearch.getVviewcount());
                        favouriteAllResponse.setVcallcount(successBuyerSearch.getVcallcount());
                        favouriteAllResponse.setVdate(successBuyerSearch.getVdate());
                        favouriteAllResponse.setVstartPrice(successBuyerSearch.getVstartPrice());
                        favouriteAllResponse.setVreservePrice(successBuyerSearch.getVreservePrice());
                        favouriteAllResponse.setVstatus(successBuyerSearch.getVstatus());
                        favouriteAllResponse.setSendername(successBuyerSearch.getSendername());
                        favouriteAllResponse.setSenderPic(successBuyerSearch.getSenderPic());
                        favouriteAllResponse.setVhpcapacity(successBuyerSearch.getVhpCapacity());
                        favouriteAllResponse.setSsearchId(successBuyerSearch.getSsearchId());
                        favouriteAllResponse.setScontactNo(successBuyerSearch.getScontactNo());
                        favouriteAllResponse.setSstatus(successBuyerSearch.getSstatus());
                        favouriteAllResponse.setScategory(successBuyerSearch.getScategory());
                        favouriteAllResponse.setSmanufacturer(successBuyerSearch.getSmanufacturer());
                        favouriteAllResponse.setSmodel(successBuyerSearch.getSmodel());
                        favouriteAllResponse.setSversion(successBuyerSearch.getSversion());
                        favouriteAllResponse.setSrtoCity(successBuyerSearch.getSrtoCity());
                        favouriteAllResponse.setSrtoCity2(successBuyerSearch.getSrtoCity2());
                        favouriteAllResponse.setSrtoCity3(successBuyerSearch.getSrtoCity3());
                        favouriteAllResponse.setSrtoCity4(successBuyerSearch.getSrtoCity4());
                        favouriteAllResponse.setSrtoCity5(successBuyerSearch.getSrtoCity5());
                        favouriteAllResponse.setSlocationCity(successBuyerSearch.getSlocationCity());
                        favouriteAllResponse.setSlocationCity2(successBuyerSearch.getSlocationCity2());
                        favouriteAllResponse.setSlocationCity3(successBuyerSearch.getSlocationCity3());
                        favouriteAllResponse.setSlocationCity4(successBuyerSearch.getSlocationCity4());
                        favouriteAllResponse.setSlocationCity5(successBuyerSearch.getSlocationCity5());
                        favouriteAllResponse.setSlocationState(successBuyerSearch.getSlocationState());
                        favouriteAllResponse.setSyearOfRegistration(successBuyerSearch.getSyearOfRegistration());
                        favouriteAllResponse.setScolor(successBuyerSearch.getScolor());
                        favouriteAllResponse.setSrcAvailable(successBuyerSearch.getSrcAvailable());
                        favouriteAllResponse.setSyearOfManufacture(successBuyerSearch.getSyearOfManufacture());
                        favouriteAllResponse.setSinsuranceValid(successBuyerSearch.getSinsuranceValid());
                        favouriteAllResponse.setStaxValidity(successBuyerSearch.getStaxValidity());
                        favouriteAllResponse.setSfitnessValidity(successBuyerSearch.getSfitnessValidity());
                        favouriteAllResponse.setSpermitValidity(successBuyerSearch.getSpermitValidity());
                        favouriteAllResponse.setSfualType(successBuyerSearch.getSfualType());
                        favouriteAllResponse.setSseatingCapacity(successBuyerSearch.getSseatingCapacity());
                        favouriteAllResponse.setSpermit(successBuyerSearch.getSpermit());
                        favouriteAllResponse.setSkmsRunning(successBuyerSearch.getSkmsRunning());
                        favouriteAllResponse.setSnoOfOwners(successBuyerSearch.getSnoOfOwners());
                        favouriteAllResponse.setShypothication(successBuyerSearch.getShypothication());
                        favouriteAllResponse.setSprice(successBuyerSearch.getSprice());
                        favouriteAllResponse.setSdrive(successBuyerSearch.getSdrive());
                        favouriteAllResponse.setStransmission(successBuyerSearch.getStransmission());
                        favouriteAllResponse.setSbodyType(successBuyerSearch.getSbodyType());
                        favouriteAllResponse.setSboatType(successBuyerSearch.getSboatType());
                        favouriteAllResponse.setSrvType(successBuyerSearch.getSrvType());
                        favouriteAllResponse.setSapplication(successBuyerSearch.getSapplication());
                        favouriteAllResponse.setSdate(successBuyerSearch.getSdate());
                        favouriteAllResponse.setScalldate(successBuyerSearch.getScalldate());
                        favouriteAllResponse.setSstopdate(successBuyerSearch.getSstopdate());
                        favouriteAllResponse.setStyreCondition(successBuyerSearch.getStyreCondition());
                        favouriteAllResponse.setSimplements(successBuyerSearch.getSimplements());
                        favouriteAllResponse.setSbusType(successBuyerSearch.getSbusType());
                        favouriteAllResponse.setSairCondition(successBuyerSearch.getSairCondition());
                        favouriteAllResponse.setSimplements(successBuyerSearch.getSimplements());
                        favouriteAllResponse.setSbusType(successBuyerSearch.getSbusType());
                        favouriteAllResponse.setSairCondition(successBuyerSearch.getSairCondition());
                        favouriteAllResponse.setSfinanceReq(successBuyerSearch.getSfinanceReq());
                        favouriteAllResponse.setSinvoice(successBuyerSearch.getSinvoice());
                        favouriteAllResponse.setShpcapacity(successBuyerSearch.getShpCapacity());
                        mainList.add(favouriteAllResponse);
                    }
                }

                //Seller
                if (!favouriteResponse.getSuccess().getSellerVehicle().isEmpty()) {
                    for (FavouriteResponse.Success.SellerVehicle successSellerVehicle : favouriteResponse.getSuccess().getSellerVehicle()) {
                        FavouriteAllResponse favouriteAllResponse = new FavouriteAllResponse();
                        favouriteAllResponse.setLayoutNo("112");
                        favouriteAllResponse.setFavid(successSellerVehicle.getFavid());
                        favouriteAllResponse.setVvehicleId(successSellerVehicle.getVvehicleId());
                        favouriteAllResponse.setVtitle(successSellerVehicle.getVtitle());
                        favouriteAllResponse.setVcontactNo(successSellerVehicle.getVcontactNo());
                        favouriteAllResponse.setVcategory(successSellerVehicle.getVcategory());
                        favouriteAllResponse.setVmodel(successSellerVehicle.getVmodel());
                        favouriteAllResponse.setVmanufacturer(successSellerVehicle.getVmanufacturer());
                        favouriteAllResponse.setVVersion(successSellerVehicle.getVVersion());
                        favouriteAllResponse.setVrtoCity(successSellerVehicle.getVrtoCity());
                        favouriteAllResponse.setVlocationCity(successSellerVehicle.getVlocationCity());
                        favouriteAllResponse.setVlocationState(successSellerVehicle.getVlocationState());
                        favouriteAllResponse.setVlocationCountry(successSellerVehicle.getVlocationCountry());
                        favouriteAllResponse.setVmonthOfRegistration(successSellerVehicle.getVmonthOfRegistration());
                        favouriteAllResponse.setVyearOfRegistration(successSellerVehicle.getVyearOfRegistration());
                        favouriteAllResponse.setVmonthOfManufacture(successSellerVehicle.getVmonthOfManufacture());
                        favouriteAllResponse.setVyearOfManufacture(successSellerVehicle.getVyearOfManufacture());
                        favouriteAllResponse.setVcolor(successSellerVehicle.getVcolor());
                        favouriteAllResponse.setVregistrationNumber(successSellerVehicle.getVregistrationNumber());
                        favouriteAllResponse.setVrcAvailable(successSellerVehicle.getVrcAvailable());
                        favouriteAllResponse.setVinsuranceValid(successSellerVehicle.getVinsuranceValid());
                        favouriteAllResponse.setVinsuranceIdv(successSellerVehicle.getVinsuranceIdv());
                        favouriteAllResponse.setVtaxValidity(successSellerVehicle.getVtaxValidity());
                        favouriteAllResponse.setVtaxPaidUpto(successSellerVehicle.getVtaxPaidUpto());
                        favouriteAllResponse.setVfitnessValidity(successSellerVehicle.getVfitnessValidity());
                        favouriteAllResponse.setVpermitValidity(successSellerVehicle.getVpermitValidity());
                        favouriteAllResponse.setVpermitYesno(successSellerVehicle.getVpermitYesno());
                        favouriteAllResponse.setVfitnessYesno(successSellerVehicle.getVfitnessYesno());
                        favouriteAllResponse.setVfualType(successSellerVehicle.getVfualType());
                        favouriteAllResponse.setVseatingCapacity(successSellerVehicle.getVseatingCapacity());
                        favouriteAllResponse.setVpermit(successSellerVehicle.getVpermit());
                        favouriteAllResponse.setVkmsRunning(successSellerVehicle.getVkmsRunning());
                        favouriteAllResponse.setvHrsRunning(successSellerVehicle.getVHrsRunning());
                        favouriteAllResponse.setVnoOfOwners(successSellerVehicle.getVnoOfOwners());
                        favouriteAllResponse.setVhypothication(successSellerVehicle.getVhypothication());
                        favouriteAllResponse.setVengineNo(successSellerVehicle.getVengineNo());
                        favouriteAllResponse.setVchassisNo(successSellerVehicle.getVchassisNo());
                        favouriteAllResponse.setVprice(successSellerVehicle.getVprice());
                        favouriteAllResponse.setVimage(successSellerVehicle.getVimage());
                        favouriteAllResponse.setVdrive(successSellerVehicle.getVdrive());
                        favouriteAllResponse.setVtransmission(successSellerVehicle.getVtransmission());
                        favouriteAllResponse.setVbodyType(successSellerVehicle.getVbodyType());
                        favouriteAllResponse.setVboatType(successSellerVehicle.getVboatType());
                        favouriteAllResponse.setVrvType(successSellerVehicle.getVrvType());
                        favouriteAllResponse.setVapplication(successSellerVehicle.getVapplication());
                        favouriteAllResponse.setVtyreCondition(successSellerVehicle.getVtyreCondition());
                        favouriteAllResponse.setVbusType(successSellerVehicle.getVbusType());
                        favouriteAllResponse.setVairCondition(successSellerVehicle.getVairCondition());
                        favouriteAllResponse.setVinvoice(successSellerVehicle.getVinvoice());
                        favouriteAllResponse.setVimplements(successSellerVehicle.getVimplements());
                        favouriteAllResponse.setVfinanceReq(successSellerVehicle.getVfinanceReq());
                        favouriteAllResponse.setVprivacy(successSellerVehicle.getVprivacy());
                        favouriteAllResponse.setVviewcount(successSellerVehicle.getVviewcount());
                        favouriteAllResponse.setVcallcount(successSellerVehicle.getVcallcount());
                        favouriteAllResponse.setVdate(successSellerVehicle.getVdate());
                        favouriteAllResponse.setVstartPrice(successSellerVehicle.getVstartPrice());
                        favouriteAllResponse.setVreservePrice(successSellerVehicle.getVreservePrice());
                        favouriteAllResponse.setVstatus(successSellerVehicle.getVstatus());
                        favouriteAllResponse.setSendername(successSellerVehicle.getSendername());
                        favouriteAllResponse.setSenderPic(successSellerVehicle.getSenderPic());
                        favouriteAllResponse.setVhpcapacity(successSellerVehicle.getVhpCapacity());
                        favouriteAllResponse.setSsearchId(successSellerVehicle.getSsearchId());
                        favouriteAllResponse.setScontactNo(successSellerVehicle.getScontactNo());
                        favouriteAllResponse.setSstatus(successSellerVehicle.getSstatus());
                        favouriteAllResponse.setScategory(successSellerVehicle.getScategory());
                        favouriteAllResponse.setSmanufacturer(successSellerVehicle.getSmanufacturer());
                        favouriteAllResponse.setSmodel(successSellerVehicle.getSmodel());
                        favouriteAllResponse.setSversion(successSellerVehicle.getSversion());
                        favouriteAllResponse.setSrtoCity(successSellerVehicle.getSrtoCity());
                        favouriteAllResponse.setSrtoCity2(successSellerVehicle.getSrtoCity2());
                        favouriteAllResponse.setSrtoCity3(successSellerVehicle.getSrtoCity3());
                        favouriteAllResponse.setSrtoCity4(successSellerVehicle.getSrtoCity4());
                        favouriteAllResponse.setSrtoCity5(successSellerVehicle.getSrtoCity5());
                        favouriteAllResponse.setSlocationCity(successSellerVehicle.getSlocationCity());
                        favouriteAllResponse.setSlocationCity2(successSellerVehicle.getSlocationCity2());
                        favouriteAllResponse.setSlocationCity3(successSellerVehicle.getSlocationCity3());
                        favouriteAllResponse.setSlocationCity4(successSellerVehicle.getSlocationCity4());
                        favouriteAllResponse.setSlocationCity5(successSellerVehicle.getSlocationCity5());
                        favouriteAllResponse.setSlocationState(successSellerVehicle.getSlocationState());
                        favouriteAllResponse.setSyearOfRegistration(successSellerVehicle.getSyearOfRegistration());
                        favouriteAllResponse.setScolor(successSellerVehicle.getScolor());
                        favouriteAllResponse.setSrcAvailable(successSellerVehicle.getSrcAvailable());
                        favouriteAllResponse.setSyearOfManufacture(successSellerVehicle.getSyearOfManufacture());
                        favouriteAllResponse.setSinsuranceValid(successSellerVehicle.getSinsuranceValid());
                        favouriteAllResponse.setStaxValidity(successSellerVehicle.getStaxValidity());
                        favouriteAllResponse.setSfitnessValidity(successSellerVehicle.getSfitnessValidity());
                        favouriteAllResponse.setSpermitValidity(successSellerVehicle.getSpermitValidity());
                        favouriteAllResponse.setSfualType(successSellerVehicle.getSfualType());
                        favouriteAllResponse.setSseatingCapacity(successSellerVehicle.getSseatingCapacity());
                        favouriteAllResponse.setSpermit(successSellerVehicle.getSpermit());
                        favouriteAllResponse.setSkmsRunning(successSellerVehicle.getSkmsRunning());
                        favouriteAllResponse.setSnoOfOwners(successSellerVehicle.getSnoOfOwners());
                        favouriteAllResponse.setShypothication(successSellerVehicle.getShypothication());
                        favouriteAllResponse.setSprice(successSellerVehicle.getSprice());
                        favouriteAllResponse.setSdrive(successSellerVehicle.getSdrive());
                        favouriteAllResponse.setStransmission(successSellerVehicle.getStransmission());
                        favouriteAllResponse.setSbodyType(successSellerVehicle.getSbodyType());
                        favouriteAllResponse.setSboatType(successSellerVehicle.getSboatType());
                        favouriteAllResponse.setSrvType(successSellerVehicle.getSrvType());
                        favouriteAllResponse.setSapplication(successSellerVehicle.getSapplication());
                        favouriteAllResponse.setSdate(successSellerVehicle.getSdate());
                        favouriteAllResponse.setScalldate(successSellerVehicle.getScalldate());
                        favouriteAllResponse.setSstopdate(successSellerVehicle.getSstopdate());
                        favouriteAllResponse.setStyreCondition(successSellerVehicle.getStyreCondition());
                        favouriteAllResponse.setSimplements(successSellerVehicle.getSimplements());
                        favouriteAllResponse.setSbusType(successSellerVehicle.getSbusType());
                        favouriteAllResponse.setSairCondition(successSellerVehicle.getSairCondition());
                        favouriteAllResponse.setSimplements(successSellerVehicle.getSimplements());
                        favouriteAllResponse.setSbusType(successSellerVehicle.getSbusType());
                        favouriteAllResponse.setSairCondition(successSellerVehicle.getSairCondition());
                        favouriteAllResponse.setSfinanceReq(successSellerVehicle.getSfinanceReq());
                        favouriteAllResponse.setSinvoice(successSellerVehicle.getSinvoice());
                        favouriteAllResponse.setShpcapacity(successSellerVehicle.getShpCapacity());
                        mainList.add(favouriteAllResponse);
                    }
                }

                //Search
                if (!favouriteResponse.getSuccess().getSearch().isEmpty()) {
                    for (FavouriteResponse.Success.Search successSearch : favouriteResponse.getSuccess().getSearch()) {
                        FavouriteAllResponse favouriteAllResponse = new FavouriteAllResponse();
                        favouriteAllResponse.setLayoutNo("113");
                        favouriteAllResponse.setSearchId(successSearch.getSearchID());
                        favouriteAllResponse.setSearchLikeStatus(successSearch.getSearchLikeStatus());
                        favouriteAllResponse.setSearchCategory(successSearch.getSearchCategory());
                        favouriteAllResponse.setSearchBrand(successSearch.getSearchBrand());
                        favouriteAllResponse.setSearchModel(successSearch.getSearchModel());
                        favouriteAllResponse.setSearchRtoCity(successSearch.getSearchRtoCity());
                        favouriteAllResponse.setSearchLocationCity(successSearch.getSearchLocationCity());
                        favouriteAllResponse.setSearchColor(successSearch.getSearchColor());
                        favouriteAllResponse.setSearchPrice(successSearch.getSearchPrice());
                        favouriteAllResponse.setSearchManfYear(successSearch.getSearchManfYear());
                        favouriteAllResponse.setSearchDate(successSearch.getSearchDate());
                        favouriteAllResponse.setSearchLeads(successSearch.getSearchLeads());
                        favouriteAllResponse.setFavid(successSearch.getFavid());
                        favouriteAllResponse.setDate(successSearch.getDate());
                        mainList.add(favouriteAllResponse);
                    }
                }
                Log.i("FavList", "Count->" + mainList.size());
                mSwipeRefreshLayout.setRefreshing(false);
                FavouriteNotificationAdapter adapter = new FavouriteNotificationAdapter(FavoriteNotificationFragment.this, mainList);
                mRecyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            } else {
                mSwipeRefreshLayout.setRefreshing(false);
                mNoData.setVisibility(View.VISIBLE);
                //CustomToast.customToast(getActivity(), getString(R.string._404));
            }
        } else {
            CustomToast.customToast(FavoriteNotificationFragment.this, getString(R.string.no_response));
        }
    }

    @Override
    public void notifyError(Throwable error) {
        mSwipeRefreshLayout.setRefreshing(false);
        if (error instanceof SocketTimeoutException) {
            CustomToast.customToast(FavoriteNotificationFragment.this, getString(R.string._404_));
        } else if (error instanceof NullPointerException) {
            CustomToast.customToast(FavoriteNotificationFragment.this, getString(R.string.no_response));
        } else if (error instanceof ClassCastException) {
            CustomToast.customToast(FavoriteNotificationFragment.this, getString(R.string.no_response));
        } else if (error instanceof ConnectException) {
            CustomToast.customToast(FavoriteNotificationFragment.this, getString(R.string.no_internet));
        } else if (error instanceof UnknownHostException) {
            CustomToast.customToast(FavoriteNotificationFragment.this, getString(R.string.no_internet));
        } else {
            Log.i("Check Class", "Favourite Notification");
            error.printStackTrace();
        }
    }

    @Override
    public void notifyString(String str) {

    }
}

