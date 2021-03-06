package autokatta.com.view;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import autokatta.com.AutokattaMainActivity;
import autokatta.com.R;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import autokatta.com.response.GetVehicleBrandResponse;
import autokatta.com.response.GetVehicleListResponse;
import autokatta.com.response.GetVehicleModelResponse;
import autokatta.com.response.GetVehicleSubTypeResponse;
import autokatta.com.response.GetVehicleVersionResponse;
import retrofit2.Response;

public class SearchNewVehicleActivity extends AppCompatActivity implements RequestNotifier {
    String myContact = "";
    ApiCall mApiCall;
    Spinner brandSpinner, modelSpinner, allcategorySpinner, subcategorySpinner, versionSpinner;
    String action = "", subCategory;
    int vehicle_id = 0, sub_category_id = 0, position_brand_id = 0, position_model_id = 0, position_version_id = 0;
    Button btnSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_new_vehicle);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btnSearch = (Button) findViewById(R.id.btnSearch);

        setTitle("Search New Vehicle");

        myContact = getSharedPreferences(getString(R.string.my_preference), Context.MODE_PRIVATE).getString("loginContact", "");
        mApiCall = new ApiCall(this, this);


        allcategorySpinner = (Spinner) findViewById(R.id.allCategory1);
        subcategorySpinner = (Spinner) findViewById(R.id.subCategory);
        brandSpinner = (Spinner) findViewById(R.id.BrandEdit1);
        modelSpinner = (Spinner) findViewById(R.id.ModelEdit1);
        versionSpinner = (Spinner) findViewById(R.id.VersionEdit1);


        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                if (getSupportActionBar() != null) {
                    getSupportActionBar().setDisplayShowHomeEnabled(true);
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                }

                getVehicleCategory();
            }
        });


        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (vehicle_id == 0) {
                    CustomToast.customToast(getApplicationContext(), "Please Select Category");
                } else if (sub_category_id == 0) {
                    CustomToast.customToast(getApplicationContext(), "Please Select Sub Category");
                } else if (position_brand_id == 0) {
                    CustomToast.customToast(getApplicationContext(), "Please Select Brand");
                } else if (position_model_id == 0) {
                    CustomToast.customToast(getApplicationContext(), "Please Select Model");
                } /*else if (position_version_id == 0) {
                    CustomToast.customToast(getApplicationContext(), "Please Select Version");
                } */ else {

                    Bundle b = new Bundle();
                    b.putInt("categoryId", vehicle_id);
                    b.putInt("subCategoryId", sub_category_id);
                    b.putInt("brandId", position_brand_id);
                    b.putInt("modelId", position_model_id);
                    b.putInt("versionId", position_version_id);

                    ActivityOptions options = ActivityOptions.makeCustomAnimation(SearchNewVehicleActivity.this, R.anim.ok_left_to_right, R.anim.ok_right_to_left);
                    Intent intentnewvehicle = new Intent(SearchNewVehicleActivity.this, SearchedNewVehicleResultActivity.class);
                    intentnewvehicle.putExtras(b);
                    startActivity(intentnewvehicle, options.toBundle());

                }
            }

        });
    }

    private void getVehicleCategory() {
        mApiCall.getVehicleList();
    }

    /*
    Sub Category...
     */
    private void getSubCategoryTask(int vehicle_id) {
        mApiCall.getVehicleSubtype(vehicle_id);
    }

    /*
  Get brandSpinner
   */
    private void getBrand(int categoryId, int subcategoryId) {
        mApiCall.getBrand(categoryId, subcategoryId);
    }

    /*
 Get modelSpinner...
  */
    private void getModel(int categoryId, int subCategoryId, int brandId) {
        mApiCall.getModel(categoryId, subCategoryId, brandId);
    }

    /*
   Get Version...
    */
    private void getVersion(int categoryId, int subCategoryId, int brandId, int modelId) {
        mApiCall.getVersion(categoryId, subCategoryId, brandId, modelId);
    }


    private int getIndex(Spinner spinner, String myString) {

        int index = 0;

        System.out.println("Spinner Count:" + spinner.getCount());
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).equals(myString)) {
                index = i;
            }
        }
        return index;
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
    public void notifySuccess(Response<?> response) {
        if (response != null) {
            if (response.isSuccessful()) {

                //Vehicle type
                if (response.body() instanceof GetVehicleListResponse) {
                    GetVehicleListResponse mGetVehicleListResponse = (GetVehicleListResponse) response.body();
                    //Category
                    List<String> mCategoryId = new ArrayList<>();
                    final List<String> parsedData = new ArrayList<>();
                    final HashMap<String, Integer> mCategoryMap = new HashMap<>();

                    mCategoryId.add("Select Category");
                    if (!mGetVehicleListResponse.getSuccess().isEmpty()) {

                        for (GetVehicleListResponse.Success mSuccess : mGetVehicleListResponse.getSuccess()) {
                            mSuccess.setId(mSuccess.getId());
                            mSuccess.setName(mSuccess.getName());

                            mCategoryId.add(mSuccess.getName());
                            mCategoryMap.put(mSuccess.getName(), mSuccess.getId());

                        }

                        parsedData.addAll(mCategoryId);
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.registration_spinner, parsedData);
                        allcategorySpinner.setAdapter(adapter);

                        subcategorySpinner.setAdapter(null);
                        brandSpinner.setAdapter(null);
                        modelSpinner.setAdapter(null);
                        versionSpinner.setAdapter(null);
                        allcategorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                if (position != 0) {
                                    vehicle_id = mCategoryMap.get(parsedData.get(position));
                                    String Category = parsedData.get(position);

                                    getSubCategoryTask(vehicle_id);

                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });

                    }
                }
                //Vehicle Sub type
                else if (response.body() instanceof GetVehicleSubTypeResponse) {
                    Log.e("GetVehicleTypes", "->");
                    List<String> mSubTypeList = new ArrayList<>();
                    final List<String> parsedData = new ArrayList<>();
                    final HashMap<String, Integer> mSubTypeMap = new HashMap<>();

                    mSubTypeList.add("Select Subcategory");
                    GetVehicleSubTypeResponse mGetVehicleSubTypeResponse = (GetVehicleSubTypeResponse) response.body();
                    for (GetVehicleSubTypeResponse.Success subTypeResponse : mGetVehicleSubTypeResponse.getSuccess()) {
                        subTypeResponse.setId(subTypeResponse.getId());
                        subTypeResponse.setName(subTypeResponse.getName());
                        mSubTypeList.add(subTypeResponse.getName());
                        mSubTypeMap.put(subTypeResponse.getName(), subTypeResponse.getId());
                    }
                    parsedData.addAll(mSubTypeList);
                    ArrayAdapter<String> adapter =
                            new ArrayAdapter<>(getApplicationContext(), R.layout.registration_spinner, parsedData);
                    subcategorySpinner.setAdapter(adapter);

                    brandSpinner.setAdapter(null);
                    modelSpinner.setAdapter(null);
                    versionSpinner.setAdapter(null);
                    subcategorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if (position != 0) {
                                sub_category_id = mSubTypeMap.get(parsedData.get(position));
                                subCategory = parsedData.get(position);

                                System.out.println("Sub cat is::" + sub_category_id);
                                System.out.println("Sub cat name::" + subCategory);

                                getBrand(vehicle_id, sub_category_id);

                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }
                //Vehicle brandSpinner
                else if (response.body() instanceof GetVehicleBrandResponse) {
                    Log.e("GetVehicleBrands", "->");
                    List<String> mBrandList = new ArrayList<>();
                    final List<String> brandData = new ArrayList<>();
                    final HashMap<String, Integer> mBrandMap = new HashMap<>();

                    mBrandList.add("Select Brand");
                    GetVehicleBrandResponse getVehicleBrandResponse = (GetVehicleBrandResponse) response.body();
                    for (GetVehicleBrandResponse.Success brandResponse : getVehicleBrandResponse.getSuccess()) {
                        brandResponse.setBrandId(brandResponse.getBrandId());
                        brandResponse.setBrandTitle(brandResponse.getBrandTitle());
                        mBrandList.add(brandResponse.getBrandTitle());
                        mBrandMap.put(brandResponse.getBrandTitle(), brandResponse.getBrandId());
                    }
                    //mBrandList.add("other");
                    brandData.addAll(mBrandList);
                    Log.i("ListBrand", "->" + mBrandList);
                    ArrayAdapter<String> adapter =
                            new ArrayAdapter<>(getApplicationContext(), R.layout.registration_spinner, brandData);

                    brandSpinner.setAdapter(adapter);

                    modelSpinner.setAdapter(null);
                    versionSpinner.setAdapter(null);
                    brandSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if (position != 0) {
                                position_brand_id = mBrandMap.get(brandData.get(position));
                                String brandName = brandData.get(position);

                                System.out.println("brandSpinner id is::" + position_brand_id);
                                System.out.println("brandSpinner name::" + brandName);

                                getModel(vehicle_id, sub_category_id, position_brand_id);
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }
                //Vehicle modelSpinner
                else if (response.body() instanceof GetVehicleModelResponse) {
                    Log.e("GetVehicleModel", "->");
                    List<String> mModelList = new ArrayList<>();
                    final List<String> modelData = new ArrayList<>();
                    final HashMap<String, Integer> mModelMap = new HashMap<>();

                    mModelList.add("Select Model");
                    GetVehicleModelResponse getVehicleModelResponse = (GetVehicleModelResponse) response.body();
                    for (GetVehicleModelResponse.Success modelResponse : getVehicleModelResponse.getSuccess()) {
                        modelResponse.setModelId(modelResponse.getModelId());
                        modelResponse.setModelTitle(modelResponse.getModelTitle());
                        mModelList.add(modelResponse.getModelTitle());
                        mModelMap.put(modelResponse.getModelTitle(), modelResponse.getModelId());
                    }
                    //mModelList.add("other");
                    modelData.addAll(mModelList);
                    Log.i("ListModel", "->" + mModelList);
                    ArrayAdapter<String> adapter =
                            new ArrayAdapter<>(getApplicationContext(), R.layout.registration_spinner, modelData);
                    modelSpinner.setAdapter(adapter);

                    versionSpinner.setAdapter(null);
                    modelSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if (position != 0) {
                                position_model_id = mModelMap.get(modelData.get(position));
                                String modelName = modelData.get(position);

                                System.out.println("modelSpinner id is::" + position_model_id);
                                System.out.println("modelSpinner name::" + modelName);

                                getVersion(vehicle_id, sub_category_id, position_brand_id, position_model_id);
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }

                //Vehicle Version
                else if (response.body() instanceof GetVehicleVersionResponse) {
                    Log.e("GetVehicleVersion", "->");
                    List<String> mVersionIdList = new ArrayList<>();
                    final List<String> versionData = new ArrayList<>();
                    final HashMap<String, Integer> mVersionMap = new HashMap<>();

                    mVersionIdList.add("Select Version");
                    GetVehicleVersionResponse getVehicleVersionResponse = (GetVehicleVersionResponse) response.body();
                    for (GetVehicleVersionResponse.Success versionResponse : getVehicleVersionResponse.getSuccess()) {
                        versionResponse.setVersionId(versionResponse.getVersionId());
                        versionResponse.setVersion(versionResponse.getVersion());
                        mVersionIdList.add(versionResponse.getVersion());
                        mVersionMap.put(versionResponse.getVersion(), versionResponse.getVersionId());
                    }
                    //mVersionIdList.add("other");
                    versionData.addAll(mVersionIdList);
                    Log.i("ListVersion", "->" + mVersionIdList);
                    ArrayAdapter<String> adapter =
                            new ArrayAdapter<>(getApplicationContext(), R.layout.registration_spinner, versionData);
                    versionSpinner.setAdapter(adapter);
                    versionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if (position != 0) {
                                position_version_id = mVersionMap.get(versionData.get(position));
                                String versionName = versionData.get(position);

                                System.out.println("Version id is::" + position_version_id);
                                System.out.println("Version name::" + versionName);
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }


            } else
                CustomToast.customToast(getApplicationContext(), getString(R.string._404));

        } else
            CustomToast.customToast(getApplicationContext(), getString(R.string.no_response));


    }


    @Override
    public void notifyError(Throwable error) {
        if (error instanceof SocketTimeoutException) {
            CustomToast.customToast(getApplicationContext(), getString(R.string._404));
        } else if (error instanceof NullPointerException) {
            CustomToast.customToast(getApplicationContext(), getString(R.string.no_response));
        } else if (error instanceof ClassCastException) {
            CustomToast.customToast(getApplicationContext(), getString(R.string.no_response));
        } else {
            Log.i("Check Class-", "Search New Vehicle Activity");
            error.printStackTrace();
        }
    }

    @Override
    public void notifyString(String str) {
        if (str != null) {

            if (str.startsWith("success")) {
                finish();
                startActivity(new Intent(SearchNewVehicleActivity.this, AutokattaMainActivity.class));
            }

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
    }
}
