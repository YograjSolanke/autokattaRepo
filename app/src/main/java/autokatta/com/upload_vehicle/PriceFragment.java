package autokatta.com.upload_vehicle;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.File;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import autokatta.com.R;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.interfaces.ServiceApi;
import autokatta.com.other.CustomToast;
import autokatta.com.response.PriceSuggestionResponse;
import autokatta.com.response.UploadUsedVehicleResponse;
import autokatta.com.view.VehicleDetails;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ak-003 on 24/3/17.
 */

public class PriceFragment extends Fragment implements RequestNotifier, View.OnClickListener {
    View mPrice;
    EditText edtPrice;
    SeekBar priceseekbar;
    TextView txtSugestedPrice, minprice, maxprice;
    Button btnUpload;
    List<Integer> priceList = new ArrayList<>();
    ApiCall apiCall;

    public PriceFragment() {
        //empty comstructor
    }

    String vehicle_id = "", strCategoryId = "", strCategoryName = "", strSubcategoryId = "", strSubcategoryName = "", strBrandId = "", strModelId = "", strVersionId = "",
            strStearing = "", strBrakename = "", strPumpname = "", strTitle = "", strGroupprivacy = "", strGroupids = "", strGroupnames = "",
            strStoreprivacy = "", strStoreids = "", strStorenames = "", strFinancestatus = "", strExhangestatus = "", strPermit = "",
            strMakemonth = "", strMakeyear = "", strRegmonth = "", strRegyear = "", strHrs = "", strKms = "", strInvoice = "", strLocation = "",
            strRto = "", strRegno = "", strBodyMfg = "", strSeatMfg = "", strOwner = "", strEmission = "", strHypo = "", strRc = "", strInsurance = "",
            strTaxvalid = "", strPermitvalid = "", strFitnessvalid = "", strInsuranceIdv = "", strInsuDate = "", strTaxDate = "",
            strFitnessDate = "", strPermitDate = "", strChasis = "", strEngine = "", strDrive = "", strTrans = "", strBustype = "", strAir = "",
            strApp = "", strImplementStr = "", strSeatcap = "", strTyreContext = "", strFuel = "", strHp = "", strJib = "", strBoon = "",
            strImages = "", strColor = "", strBodytype = "", strImplement = "", strMfgYr = "", strBrandName = "",
            strModelName = "", strVersionName = "";

    String myContact;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mPrice = inflater.inflate(R.layout.fragment_price, container, false);

        edtPrice = (EditText) mPrice.findViewById(R.id.edtPrice);
        priceseekbar = (SeekBar) mPrice.findViewById(R.id.priceseekbar);
        txtSugestedPrice = (TextView) mPrice.findViewById(R.id.sugPrice);
        minprice = (TextView) mPrice.findViewById(R.id.minprice);
        maxprice = (TextView) mPrice.findViewById(R.id.maxprice);
        btnUpload = (Button) mPrice.findViewById(R.id.btnUpload);

        apiCall = new ApiCall(getActivity(), this);
        btnUpload.setOnClickListener(this);

        myContact = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", "7841023392");

        strCategoryId = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("upload_categoryId", "1");
        strCategoryName = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("upload_categoryName", null);
        strSubcategoryId = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("upload_subCatId", "66");
        strSubcategoryName = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("upload_subCatName", null);
        strStearing = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("upload_stearingName", null);
        strBrakename = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("upload_brakeName", null);
        strPumpname = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("upload_pumpName", null);
        strTitle = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("upload_Title", null);
        strGroupprivacy = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("upload_groupPrivacy", null);
        strGroupids = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("upload_GroupIds", null);
        strGroupnames = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("upload_GroupNames", null);
        strStoreprivacy = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("upload_storePrivacy", null);
        strStoreids = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("upload_StoreIds", null);
        strStorenames = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("upload_StoreNames", null);
        strFinancestatus = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("upload_financeStatus", null);
        strExhangestatus = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("upload_exchangeStatus", null);
        strPermit = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("upload_permit", null);
        strMakemonth = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("upload_makeMonth", null);
        strMakeyear = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("upload_makeYear", null);
        strRegmonth = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("upload_registerMonth", null);
        strRegyear = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("upload_registerYear", null);
        strHrs = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("upload_Hrs", null);
        strKms = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("upload_Kms", null);
        strInvoice = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("upload_invoice", null);
        strLocation = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("upload_Location", null);
        strRto = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("upload_Rto", null);
        strRegno = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("upload_RegistNo", null);
        strBodyMfg = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("upload_BodyManufacture", null);
        strSeatMfg = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("upload_SeatManufacture", null);
        strOwner = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("upload_owner", null);
        strEmission = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("upload_emission", null);
        strHypo = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("upload_hypo", null);
        strRc = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("upload_rc", null);
        strInsurance = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("upload_insurance", null);
        strTaxvalid = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("upload_taxValid", null);
        strPermitvalid = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("upload_permitValid", null);
        strFitnessvalid = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("upload_fitnessValid", null);
        strInsuranceIdv = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("upload_insuranceIdv", null);
        strInsuDate = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("upload_insuranceDate", null);
        strTaxDate = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("upload_taxDate", null);
        strFitnessDate = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("upload_fitnessDate", null);
        strPermitDate = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("upload_permitDate", null);
        strChasis = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("upload_chasisNo", null);
        strEngine = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("upload_engineNo", null);
        strDrive = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("upload_drive", null);
        strTrans = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("upload_transmission", null);
        strBustype = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("upload_busType", null);
        strAir = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("upload_airCondition", null);
        strApp = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("upload_application", null);
        strImplementStr = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("upload_implementStr", null);
        strSeatcap = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("upload_seatingCapacity", null);
        strTyreContext = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("upload_tyreContext", null);
        strFuel = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("upload_fuel", null);
        strHp = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("upload_hpCapacity", null);
        strJib = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("upload_Jib", null);
        strBoon = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("upload_Boon", null);

        strImages = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("images", null);
        strColor = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("upload_color", null);
        strBodytype = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("upload_bodyType", null);
        strImplement = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("upload_implement", null);

        strBrandName = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("upload_brandName", "");
        strBrandId = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("upload_brandId", "33");
        strModelName = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("upload_modelName", "");
        strModelId = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("upload_modelId", "11");
        strVersionName = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("upload_versionName", "");
        strVersionId = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("upload_versionId", "20");
        strMfgYr = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("upload_mfgYear", "20");

        System.out.println("incoming images=" + strImages);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {


                    getPrice(strCategoryId, strSubcategoryId, strBrandId, strModelId, strVersionId, strMfgYr, strRto);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return mPrice;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnUpload:
//                String pricestr = edtPrice.getText().toString();
//                getActivity().getSharedPreferences(getString(R.string.my_preference),MODE_PRIVATE).edit().putString("uploadPrice",pricestr).apply();
//
//
//                ConfirmationFragment fragment2 = new ConfirmationFragment();
//                fragment2.setArguments(b);
//                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.containerView, fragment2);
//                fragmentTransaction.commit();
                uploadVehicle();
                break;
        }


    }

    private void getPrice(String categoryId, String subCategoryId, String brandId, String modelId, String versionId, String mfgYear, String rtoCity) {

        apiCall.SuggestedPrice(categoryId, subCategoryId, brandId, modelId, versionId, mfgYear, rtoCity);
    }

    private void uploadVehicle() {
        apiCall.uploadUsedVehicle(strTitle, myContact, strCategoryName, strSubcategoryName, strModelName,
                strBrandName, strVersionName, strRto, strLocation,
                strRegmonth, strRegyear, strMakemonth,
                strMakeyear, strColor, strRegno,
                strRc, strInsurance, strInsuranceIdv, strTaxvalid,
                strTaxDate, strFitnessvalid, strPermitvalid,
                strPermit, strFitnessvalid, strFuel, strSeatcap,
                strPermitDate, strFinancestatus, strKms, strHrs,
                strOwner, strBodyMfg, strSeatMfg,
                strHypo, strEngine, strChasis, edtPrice.getText().toString(), strImages,
                strDrive, strTrans, strBodytype, "", "",
                strApp, strTyreContext, strBustype, strAir,
                strInvoice, strImplement, strGroupprivacy, strHp, strJib,
                strBoon, strBrakename, strPumpname, strInsuDate, strEmission,
                strFinancestatus, strExhangestatus, strStearing,
                strCategoryId, strSubcategoryId, strBrandId, strModelId, strVersionId);
    }

    @Override
    public void notifySuccess(Response<?> response) {

        if (response != null) {
            if (response.isSuccessful()) {

                if (response.body() instanceof PriceSuggestionResponse) {
                    PriceSuggestionResponse priceSuggestionResponse = (PriceSuggestionResponse) response.body();
                    Log.i("response", response.body().toString());
                    if (!priceSuggestionResponse.getSuccess().isEmpty()) {

                        for (PriceSuggestionResponse.Success success : priceSuggestionResponse.getSuccess()) {
                            success.setPriceSuggestion(success.getPriceSuggestion());
                            String i = success.getPriceSuggestion();
                            if (!i.equals(""))
                                priceList.add(Integer.valueOf(success.getPriceSuggestion()));
                        }

//                    System.out.println("priceSuggestion" + priceList);
//
//                    for (int i = 0; i < priceList.size() - 1; i++) {
//                        for (int j = i + 1; j < priceList.size(); j++) {
//                            if (priceList.get(i) > (priceList.get(j))) {
//                                int temp = priceList.get(i);
//                                priceList.set(i, priceList.get(j));
//                                priceList.set(j, temp);
//                            }
//                        }
//                    }
//
//                    System.out.println("priceSuggestion" + priceList);

                        if (priceList.size() != 0) {

                            int maxsize = priceList.size();
                            priceseekbar.setMax(priceList.get(maxsize - 1));

                            minprice.setText(String.valueOf(priceList.get(0)));
                            maxprice.setText(String.valueOf(priceList.get(maxsize - 1)));

                            priceseekbar.setProgress(priceList.get(0)); // or any other value

                            priceseekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

                                int seekBarProgress = 0;

                                @Override
                                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                                    seekBarProgress = progress;
                                    edtPrice.setText("" + seekBarProgress);
                                }


                                @Override
                                public void onStartTrackingTouch(SeekBar seekBar) {
                                    edtPrice.setText("" + seekBarProgress);
                                }

                                @Override
                                public void onStopTrackingTouch(SeekBar seekBar) {

                                    edtPrice.setText("" + seekBarProgress);
                                    CustomToast.customToast(getActivity(), "Progresss" + seekBarProgress);
                                }
                            });
                        } else {
                            txtSugestedPrice.setVisibility(View.GONE);
                            priceseekbar.setVisibility(View.GONE);
                            minprice.setVisibility(View.GONE);
                            maxprice.setVisibility(View.GONE);
                        }


                    } else
                        CustomToast.customToast(getActivity(), getActivity().getString(R.string.no_response));
                } else if (response.body() instanceof UploadUsedVehicleResponse) {
                    UploadUsedVehicleResponse vehicleResponse = (UploadUsedVehicleResponse) response.body();
                    if (vehicleResponse.getSuccess() != null) {
                        vehicle_id = vehicleResponse.getSuccess().getVehicleID().toString();

                        uploadImage(strImages);

                        if (!strGroupids.equals("") || !strStoreids.equals("")) {

                            Bundle b = new Bundle();
                            b.putString("vehicle_id", vehicle_id);
                            VehiclePrivacy frag = new VehiclePrivacy();
                            frag.setArguments(b);

                            FragmentManager mFragmentManager;
                            FragmentTransaction mFragmentTransaction;
                            mFragmentManager = getActivity().getSupportFragmentManager();
                            mFragmentTransaction = mFragmentManager.beginTransaction();
                            mFragmentTransaction.replace(R.id.vehicle_upload_container, frag, "vehiclePrivacy")
                                    .addToBackStack("vehiclePrivacy")
                                    .commit();


                        } else {
                            Intent intent = new Intent(getActivity(), VehicleDetails.class);
                            intent.putExtra("vehicle_id", vehicle_id);
                            getActivity().startActivity(intent);
                        }
                    } else
                        CustomToast.customToast(getActivity(), getActivity().getString(R.string.no_response));
                }

            } else
                CustomToast.customToast(getActivity(), getActivity().getString(R.string._404));

        } else
            CustomToast.customToast(getActivity(), getActivity().getString(R.string.no_response));
    }

    @Override
    public void notifyError(Throwable error) {
        if (error instanceof SocketTimeoutException) {
            CustomToast.customToast(getActivity(), getString(R.string._404));
        } else if (error instanceof NullPointerException) {
            CustomToast.customToast(getActivity(), getString(R.string.no_response));
        } else if (error instanceof ClassCastException) {
            CustomToast.customToast(getActivity(), getString(R.string.no_response));
        } else {
            Log.i("Check Class-", "Price Fragment");
            error.printStackTrace();
        }
    }

    @Override
    public void notifyString(String str) {

    }

    @Override
    public void onResume() {
        super.onResume();
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    onBackPressed();
                    return true;
                }
                return false;
            }
        });

    }

    public void onBackPressed() {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        Fragment f = fm.findFragmentById(R.id.vehicle_upload_container);

        if (fm.getBackStackEntryCount() > 0) {
            fm.popBackStack();
        }
    }


    private void uploadImage(String picturePath) {
        Log.i("PAth", "->" + picturePath);
        List<String> imgList = Arrays.asList(picturePath.split(","));
        int s = imgList.size();
        for (int i = 0; i < imgList.size(); i++) {


            File file = new File(imgList.get(i));
            // Parsing any Media type file
            RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
            MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
            RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());

            ServiceApi getResponse = ApiCall.getRetrofit().create(ServiceApi.class);
            Call<String> call = getResponse.uploadVehiclePic(fileToUpload, filename);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    Log.i("image", "->" + response.body());
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {

                }
            });
        }
    }
}
