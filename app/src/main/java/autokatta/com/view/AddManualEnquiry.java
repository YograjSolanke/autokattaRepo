package autokatta.com.view;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.telephony.gsm.SmsManager;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import autokatta.com.R;
import autokatta.com.Registration.InviteFriends;
import autokatta.com.adapter.GooglePlacesAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.generic.SetMyDateAndTime;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import autokatta.com.response.AddManualEnquiryResponse;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import retrofit2.Response;

public class AddManualEnquiry extends AppCompatActivity implements RequestNotifier, View.OnTouchListener {

    EditText edtName, edtContact, edtAddress, edtDiscussion, edtDate, edtTime;
    AutoCompleteTextView autoAddress;
    Spinner spnInventory, spnStatus;
    String myContact;
    LinearLayout txtUser, txtInvite,mInventory;
    ImageView imgContact;
    RelativeLayout mRelative;
    android.support.v4.widget.NestedScrollView scrollView;
    TextView mNoData;
    TextView Title, Category, Brand, Model, Keyword, price;
    RelativeLayout relCategory, relBrand, relModel, relPrice, mDetails;
    ImageView Image;
    Menu menu;
    String fullpath = "";
    String mVehicleId;
    String mKeyword,mTitle,mPrice,mCategory,mBrand,mModel,mClassname,mImage;
    private final int REQUEST_CODE = 99;
    Button mSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_manual_enquiry);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        setTitle("Add Enquiry");
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mRelative = (RelativeLayout) findViewById(R.id.add_manual);
                myContact = getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", "");
                edtName = (EditText) findViewById(R.id.edtName);
                edtContact = (EditText) findViewById(R.id.edtContact);
                edtAddress = (EditText) findViewById(R.id.edtAddress);
                edtDiscussion = (EditText) findViewById(R.id.edtDiscussion);
                autoAddress = (AutoCompleteTextView) findViewById(R.id.autoAddress);
                spnInventory = (Spinner) findViewById(R.id.spnInventory);
                spnStatus = (Spinner) findViewById(R.id.spnStatus);
                edtDate = (EditText) findViewById(R.id.edtDate);
                edtTime = (EditText) findViewById(R.id.edtTime);
                txtUser = (LinearLayout) findViewById(R.id.txtUser);
                // mListView = (ListView) findViewById(R.id.vehicle_list);
                scrollView = (NestedScrollView) findViewById(R.id.scroll_view);
                imgContact = (ImageView) findViewById(R.id.contact_list);
                txtInvite = (LinearLayout) findViewById(R.id.txtInvite);
                mInventory = (LinearLayout) findViewById(R.id.selctinventory);
                mSubmit= (Button) findViewById(R.id.sub);


                Keyword = (TextView) findViewById(R.id.keyword);
                Title = (TextView) findViewById(R.id.settitle);
                Category = (TextView) findViewById(R.id.setcategory);
                Brand = (TextView) findViewById(R.id.setbrand);
                Model = (TextView) findViewById(R.id.setmodel);
                Image = (ImageView) findViewById(R.id.image);
                price = (TextView) findViewById(R.id.setprice);
                relCategory = (RelativeLayout) findViewById(R.id.relative2);
                relBrand = (RelativeLayout) findViewById(R.id.relative3);
                relModel = (RelativeLayout) findViewById(R.id.relative4);
                relPrice = (RelativeLayout) findViewById(R.id.relative5);
                mDetails = (RelativeLayout) findViewById(R.id.details);

                mNoData = (TextView) findViewById(R.id.no_category);
                mNoData.setVisibility(View.GONE);

                edtDate.setOnTouchListener(AddManualEnquiry.this);
                edtTime.setOnTouchListener(AddManualEnquiry.this);
                autoAddress.setAdapter(new GooglePlacesAdapter(AddManualEnquiry.this, R.layout.registration_spinner));

                edtContact.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        txtUser.setVisibility(View.GONE);
                        txtInvite.setVisibility(View.GONE);

                        if (s.length() == 10) {
                            if (!myContact.equalsIgnoreCase(s.toString()))
                                checkUser(s.toString());
                            else {
                                edtContact.requestFocus();
                                edtContact.setError("admin not allowed");
                            }
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        txtUser.setVisibility(View.GONE);
                        txtInvite.setVisibility(View.GONE);
                    }
                });

                /*This works when user comes from bussiness chat*/
                if (getIntent().getExtras() != null) {

                    mInventory.setVisibility(View.GONE);
                    mDetails.setVisibility(View.VISIBLE);
                    mSubmit.setVisibility(View.VISIBLE);

                    edtName.setText(getIntent().getExtras().getString("sendername"));
                    edtContact.setText(getIntent().getExtras().getString("sender"));
                    edtName.setSelection(edtName.getText().length());
                    edtContact.setSelection(edtContact.getText().length());

                    Keyword.setText(getIntent().getExtras().getString("keyword"));
                    Category.setText(getIntent().getExtras().getString("category"));
                    Title.setText(getIntent().getExtras().getString("title"));
                    Brand.setText(getIntent().getExtras().getString("brand"));
                    Model.setText(getIntent().getExtras().getString("model"));
                    price.setText(getIntent().getExtras().getString("price"));
                    mImage=getIntent().getExtras().getString("image","");
                    mVehicleId= String.valueOf(getIntent().getExtras().getInt("vehicleid"));
                    mClassname=getIntent().getExtras().getString("classname");



                    if (getIntent().getExtras().getString("keyword","").equalsIgnoreCase("Product")) {
                        relBrand.setVisibility(View.GONE);
                        relModel.setVisibility(View.GONE);
                        if (!mImage.equals("") && !mImage.equals("null")) {
                            fullpath = getString(R.string.base_image_url) + mImage;
                            fullpath = fullpath.replaceAll(" ", "%20");
                            Glide.with(AddManualEnquiry.this)
                                    .load(fullpath)
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .bitmapTransform(new CropCircleTransformation(AddManualEnquiry.this))
                                    .placeholder(R.drawable.logo)
                                    .into(Image);
                        } else {
                            Image.setImageResource(R.drawable.logo);
                        }
                    } else if (getIntent().getExtras().getString("keyword","").equalsIgnoreCase("Service")) {
                        relCategory.setVisibility(View.GONE);
                        relBrand.setVisibility(View.GONE);
                        relModel.setVisibility(View.GONE);
                        if (!mImage.equals("") && !mImage.equals("null")) {
                            fullpath = getString(R.string.base_image_url) + mImage;
                            fullpath = fullpath.replaceAll(" ", "%20");
                            Glide.with(AddManualEnquiry.this)
                                    .load(fullpath)
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .bitmapTransform(new CropCircleTransformation(AddManualEnquiry.this))
                                    .placeholder(R.drawable.logo)
                                    .into(Image);
                        } else {
                            Image.setImageResource(R.drawable.logo);
                        }
                    } else if (getIntent().getExtras().getString("keyword","").equalsIgnoreCase("Vehicle")) {
                        if (!mImage.equals("") && !mImage.equals("null")) {
                            fullpath = getString(R.string.base_image_url) + mImage;
                            fullpath = fullpath.replaceAll(" ", "%20");
                            Glide.with(AddManualEnquiry.this)
                                    .load(fullpath)
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .bitmapTransform(new CropCircleTransformation(AddManualEnquiry.this))
                                    .placeholder(R.drawable.logo)
                                    .into(Image);
                        } else {
                            Image.setImageResource(R.drawable.logo);
                        }
                    }
                    mSubmit.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String custInventoryType = "", custEnquiryStatus = "";
                            Boolean flag = false;
                           // int strPos = spnInventory.getSelectedItemPosition();
                            int strPos1 = spnStatus.getSelectedItemPosition();
                            String custName = edtName.getText().toString();
                            String custContact = edtContact.getText().toString();
                            String custAddress = autoAddress.getText().toString();
                            String custFullAddress = edtAddress.getText().toString();

                           /* if (strPos != 0) {
                                custInventoryType = spnInventory.getSelectedItem().toString();
                            }*/
                            if (strPos1 != 0)
                                custEnquiryStatus = spnStatus.getSelectedItem().toString();

                            String discussion = edtDiscussion.getText().toString();
                            String nextFollowupDate = edtDate.getText().toString() + " " + edtTime.getText().toString();

                            if (!custAddress.isEmpty()) {
                                List<String> resultList = GooglePlacesAdapter.getResultList();
                                for (int i = 0; i < resultList.size(); i++) {
                                    if (custAddress.equalsIgnoreCase(resultList.get(i))) {
                                        flag = true;
                                        break;
                                    } else {
                                        CustomToast.customToast(AddManualEnquiry.this,"Please Select Valid Address ");
                                        flag = false;
                                    }
                                }
                            }

                            if (custName.equalsIgnoreCase("") || custName.startsWith(" ") && custName.startsWith(" ")) {
                                edtName.setError("Please provide customer name");
                                edtName.requestFocus();
                            } else if (custContact.isEmpty() || custContact.startsWith(" ") && custContact.startsWith(" ")) {
                                edtContact.setError("Please provide customer contact");
                                edtContact.requestFocus();
                            } else if (custAddress.equals("") || custAddress.startsWith(" ") && custAddress.startsWith(" ")) {
                                autoAddress.setError("Enter Address");
                                autoAddress.requestFocus();
                            } else if (!flag) {
                                autoAddress.setError("Please provide proper address");
                                autoAddress.requestFocus();
                            } else if (custFullAddress.equals("")) {
                                edtAddress.setError("Enter Detailed address");
                                edtAddress.requestFocus();
                            }/* else if (spnInventory.getSelectedItemPosition() == 0) {
                                CustomToast.customToast(getApplicationContext(), "Please provide inventory");
                                spnInventory.requestFocus();
                            } */else if (spnStatus.getSelectedItemPosition() == 0) {
                                CustomToast.customToast(getApplicationContext(), "Please provide status");
                                spnStatus.requestFocus();
                            } /*else if (discussion.equals("")) {
                    edtDiscussion.setError("Enter discussion data");
                    edtDiscussion.requestFocus();
                }*/ else if (nextFollowupDate.equals("") || nextFollowupDate.startsWith(" ")) {
                                edtDate.setError("Enter Date");
                                edtDate.requestFocus();
                            } else {
                                    AddEnquiryData(custName, custContact, custAddress, custFullAddress, custInventoryType, custEnquiryStatus,
                                            discussion, nextFollowupDate, mVehicleId);
                                }

                            }
                    });
                }


                txtUser.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ActivityOptions options = ActivityOptions.makeCustomAnimation(AddManualEnquiry.this, R.anim.left_to_right, R.anim.right_to_left);
                        Bundle bundle = new Bundle();
                        bundle.putString("contactOtherProfile", edtContact.getText().toString());
                        Intent intent = new Intent(AddManualEnquiry.this, OtherProfile.class);
                        intent.putExtras(bundle);
                        startActivity(intent, options.toBundle());
                    }
                });

                txtInvite.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //sendSMSMessage(edtContact.getText().toString(),"hhhh");
                    }
                });


                spnInventory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if (position != 0) {
                            String str = spnInventory.getSelectedItem().toString();
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                imgContact.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                        startActivityForResult(intent, REQUEST_CODE);
                    }
                });
            }
        });
    }

    private void checkUser(String contact) {
        ApiCall mApiCall = new ApiCall(this, this);
        mApiCall.registrationContactValidation(contact);
    }

    private void sendSMSMessage(String con, String msg) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(con, null, "hi..." + msg, null, null);
            Toast.makeText(getApplicationContext(), "SMS sent.", Toast.LENGTH_LONG).show();
            Intent i = new Intent(getApplicationContext(), InviteFriends.class);
            startActivity(i);
            finish();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "SMS failed, please try again.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_manual_enquiry, menu);
        this.menu = menu;
        return true;
    }
/*
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

        if (mNoData.getVisibility()==View.VISIBLE)
            menu.findItem(R.id.ok_manual).setVisible(false);
        return true;
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {

            case android.R.id.home:
                /*if (mListView.getVisibility() == View.VISIBLE) {
                    mListView.setVisibility(View.GONE);
                    scrollView.setVisibility(View.VISIBLE);
                } else {*/
                onBackPressed();

                // }
                mNoData.setVisibility(View.GONE);
                break;

            case R.id.ok_manual:
                String custInventoryType = "", custEnquiryStatus = "";
                Boolean flag = false;
                int strPos = spnInventory.getSelectedItemPosition();
                int strPos1 = spnStatus.getSelectedItemPosition();
                String custName = edtName.getText().toString();
                String custContact = edtContact.getText().toString();
                String custAddress = autoAddress.getText().toString();
                String custFullAddress = edtAddress.getText().toString();

                if (strPos != 0) {
                    custInventoryType = spnInventory.getSelectedItem().toString();
                }
                if (strPos1 != 0)
                    custEnquiryStatus = spnStatus.getSelectedItem().toString();

                String discussion = edtDiscussion.getText().toString();
                String nextFollowupDate = edtDate.getText().toString() + " " + edtTime.getText().toString();

                if (!custAddress.isEmpty()) {
                    List<String> resultList = GooglePlacesAdapter.getResultList();
                    for (int i = 0; i < resultList.size(); i++) {
                        if (custAddress.equalsIgnoreCase(resultList.get(i))) {
                            flag = true;
                            break;
                        } else {
                            flag = false;
                        }
                    }
                }
/*
                if (arrayList != null) {
                    arrayList = adapter.getInventoryList();
                    for (int i = 0; i < arrayList.size(); i++) {
                        if (!arrayList.get(i).equals("0")) {
                            if (addArray.equals("")) {
                                addArray = arrayList.get(i);
                            } else {
                                addArray = addArray + "," + arrayList.get(i);
                                Log.i("addArray", "->" + addArray);
                            }
                        }
                    }
                }*/

                if (custName.equalsIgnoreCase("") || custName.startsWith(" ") && custName.startsWith(" ")) {
                    edtName.setError("Please provide customer name");
                    edtName.requestFocus();
                } else if (custContact.isEmpty() || custContact.startsWith(" ") && custContact.startsWith(" ")) {
                    edtContact.setError("Please provide customer contact");
                    edtContact.requestFocus();
                } else if (custAddress.equals("") || custAddress.startsWith(" ") && custAddress.startsWith(" ")) {
                    autoAddress.setError("Enter Address");
                    autoAddress.requestFocus();
                } else if (!flag) {
                    autoAddress.setError("Please provide proper address");
                    autoAddress.requestFocus();
                } else if (custFullAddress.equals("")) {
                    edtAddress.setError("Enter detailed address");
                    edtAddress.requestFocus();
                } else if (spnInventory.getSelectedItemPosition() == 0) {
                    CustomToast.customToast(getApplicationContext(), "Please provide inventory");
                    spnInventory.requestFocus();
                } else if (spnStatus.getSelectedItemPosition() == 0) {
                    CustomToast.customToast(getApplicationContext(), "Please provide status");
                    spnStatus.requestFocus();
                } /*else if (discussion.equals("")) {
                    edtDiscussion.setError("Enter discussion data");
                    edtDiscussion.requestFocus();
                }*/ else if (nextFollowupDate.equals("") || nextFollowupDate.startsWith(" ")) {
                    edtDate.setError("Enter Date");
                    edtDate.requestFocus();
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putString("spinnerValue", spnInventory.getSelectedItem().toString());
                    bundle.putString("custName", custName);
                    bundle.putString("custContact", custContact);
                    bundle.putString("custAddress", custAddress);
                    bundle.putString("custFullAddress", custFullAddress);
                    bundle.putString("custInventoryType", custInventoryType);
                    bundle.putString("custEnquiryStatus", custEnquiryStatus);
                    bundle.putString("discussion", discussion);
                    bundle.putString("nextFollowupDate", nextFollowupDate);

                        ActivityOptions options = ActivityOptions.makeCustomAnimation(AddManualEnquiry.this, R.anim.ok_left_to_right, R.anim.ok_right_to_left);
                        Intent intent = new Intent(getApplicationContext(), ManualEnquiryVehicleList.class);
                        intent.putExtras(bundle);
                        startActivity(intent, options.toBundle());


                }
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void AddEnquiryData(String custName, String custContact, String custAddress, String custFullAddress,
                                String custInventoryType, String custEnquiryStatus, String discussion,
                                String nextFollowupDate, String addArray) {
        ApiCall mApiCall = new ApiCall(this, this);
        mApiCall.addManualEnquiryData(myContact, custName, custContact, custAddress, custFullAddress, custInventoryType,
                custEnquiryStatus, discussion, nextFollowupDate, addArray);
    }


    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        switch (view.getId()) {

            case (R.id.edtDate):
                if (action == MotionEvent.ACTION_DOWN) {
                    edtDate.setInputType(InputType.TYPE_NULL);
                    edtDate.setError(null);
                    new SetMyDateAndTime("date", edtDate, this);
                }
                break;

            case (R.id.edtTime):

                if (action == MotionEvent.ACTION_DOWN) {
                    edtTime.setInputType(InputType.TYPE_NULL);
                    edtTime.setError(null);
                    new SetMyDateAndTime("time", edtTime, this);
                }
                break;
        }

        return false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            ActivityOptions options = ActivityOptions.makeCustomAnimation(AddManualEnquiry.this, R.anim.left_to_right, R.anim.right_to_left);
            startActivity(new Intent(getApplicationContext(), ManualEnquiry.class), options.toBundle());
            finish();
        } else {
            finish();
            startActivity(new Intent(getApplicationContext(), ManualEnquiry.class));
        }*/
    }

    @Override
    public void notifySuccess(Response<?> response) {
        if (response != null) {
            if (response.isSuccessful()) {
                if (response.body() instanceof AddManualEnquiryResponse) {
                    AddManualEnquiryResponse enquiryResponse = (AddManualEnquiryResponse) response.body();
                    if (enquiryResponse.getSuccess() != null) {
                        if (enquiryResponse.getSuccess().getSuccess().equalsIgnoreCase("Data successfully Inserted.")) {
                            //Snackbar.make(mRelative, enquiryResponse.getSuccess().getSuccess(), Snackbar.LENGTH_SHORT).show();
                         /*   Bundle bundle = new Bundle();
                            bundle.putString("spinnerValue",spnInventory.getSelectedItem().toString());
                            ActivityOptions options = ActivityOptions.makeCustomAnimation(AddManualEnquiry.this, R.anim.ok_left_to_right, R.anim.ok_right_to_left);
                            Intent intent = new Intent(getApplicationContext(), ManualEnquiryVehicleList.class);
                            intent.putExtras(bundle);
                            startActivity(intent, options.toBundle());*/
                            CustomToast.customToast(AddManualEnquiry.this, "Data Inserted Successfully");
                            finish();
                        }
                    } else {
                        CustomToast.customToast(getApplicationContext(), getString(R.string.no_internet));
                    }
                } /*else if (response.body() instanceof GetInventoryResponse) {
                    GetInventoryResponse mInventoryResponse = (GetInventoryResponse) response.body();
                    if (mInventoryResponse.getSuccess() != null) {
                        mItemList.clear();
                        scrollView.setVisibility(View.GONE);
                       // mListView.setVisibility(View.VISIBLE);
                        for (GetInventoryResponse.Success success : mInventoryResponse.getSuccess()) {
                            mNoData.setVisibility(View.GONE);
                            if (success.getInventoryType().equals("UsedVehicle")) {
                                success.setVehicleId(success.getVehicleId());
                                success.setTitle(success.getTitle());
                                success.setCategory(success.getCategory());
                                success.setSubCategory(success.getSubCategory());
                                success.setModel(success.getModel());
                                mInventoryType = success.getInventoryType();
                                Log.i("used", "->" + mInventoryType);
                                mItemList.add(success);
                            } else if (success.getInventoryType().equals("New Vehicle")) {
                            } else if (success.getInventoryType().equals("Products")) {
                                success.setId(success.getId());
                                success.setProductName(success.getProductName());
                                success.setProductType(success.getProductType());
                                success.setCategory(success.getCategory());
                                mInventoryType = success.getInventoryType();
                                Log.i("product", "->" + mInventoryType);
                                mItemList.add(success);
                            } else if (success.getInventoryType().equals("Services")) {
                                success.setId(success.getId());
                                success.setName(success.getName());
                                success.setType(success.getType());
                                success.setCategory(success.getCategory());
                                mInventoryType = success.getInventoryType();
                                Log.i("service", "->" + mInventoryType);
                                mItemList.add(success);
                            }
                        }
                        Log.i("Inventory", "->" + mInventoryType);

                        if (mItemList.size() != 0) {
                            mNoData.setVisibility(View.GONE);
                            adapter = new InventoryAdapter(AddManualEnquiry.this, mItemList, mInventoryType);
                           // mListView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                            menu.findItem(R.id.ok_manual).setVisible(true);

                        } else {
                            mNoData.setVisibility(View.VISIBLE);
                            menu.findItem(R.id.ok_manual).setVisible(false);
                        }
                    } else {
                        Snackbar.make(mRelative, getString(R.string.no_response), Snackbar.LENGTH_SHORT).show();
                    }
                }*//*
            } else {
                Snackbar.make(mRelative, getString(R.string._404_), Snackbar.LENGTH_SHORT).show();
            }
        } else {
            Snackbar.make(mRelative, getString(R.string.no_response), Snackbar.LENGTH_SHORT).show();
        }*/
            }
            {
                Snackbar.make(mRelative, getString(R.string._404_), Snackbar.LENGTH_SHORT).show();
            }
        } else {
            Snackbar.make(mRelative, getString(R.string.no_response), Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void notifyError(Throwable error) {
        /*if (error instanceof SocketTimeoutException) {
            Snackbar.make(mRelative, getString(R.string._404_), Snackbar.LENGTH_SHORT).show();
        } else if (error instanceof NullPointerException) {
            Snackbar.make(mRelative, getString(R.string.no_response), Snackbar.LENGTH_SHORT).show();
        } else if (error instanceof ClassCastException) {
            Snackbar.make(mRelative, getString(R.string.no_response), Snackbar.LENGTH_SHORT).show();
        } else if (error instanceof ConnectException) {
            Snackbar.make(mRelative, getString(R.string.no_internet), Snackbar.LENGTH_SHORT).show();
        } else {
            Log.i("Check Class-"
                    , "Add Manual Enquiry");
            error.printStackTrace();
        }*/
    }

    @Override
    public void notifyString(String str) {
        if (str != null) {
            if (str.equalsIgnoreCase("Success")) {
                txtUser.setVisibility(View.VISIBLE);
                txtInvite.setVisibility(View.GONE);
            } else {
                txtInvite.setVisibility(View.VISIBLE);
                txtUser.setVisibility(View.GONE);
            }
        } else
            CustomToast.customToast(getApplicationContext(), getString(R.string.no_response));
    }

    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        switch (reqCode) {
            case (REQUEST_CODE):
                if (resultCode == Activity.RESULT_OK) {
                    try {
                        Uri contactData = data.getData();
                        Cursor c = getContentResolver().query(contactData, null, null, null, null);
                        if (c.moveToFirst()) {
                            String contactId = c.getString(c.getColumnIndex(ContactsContract.Contacts._ID));
                            String hasNumber = c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                            String num = "";
                            if (Integer.valueOf(hasNumber) == 1) {
                                Cursor numbers = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null);
                                while (numbers.moveToNext()) {
                                    num = numbers.getString(numbers.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                                    num = num.replaceAll("-", "");
                                    num = num.replace("(", "").replace(")", "").replaceAll(" ", "").replaceAll("[\\D]", "");

                                    if (num.length() > 10)
                                        num = num.substring(num.length() - 10);
                                    Toast.makeText(AddManualEnquiry.this, "Number=" + num, Toast.LENGTH_LONG).show();
                                    edtContact.setText(num);
                                }
                            }
                        }
                        break;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
        }
    }
}
