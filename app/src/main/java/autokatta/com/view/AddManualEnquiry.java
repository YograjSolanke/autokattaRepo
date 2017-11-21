package autokatta.com.view;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.IdRes;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import autokatta.com.R;
import autokatta.com.adapter.GooglePlacesAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.generic.SetMyDateAndTime;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.interfaces.ServiceApi;
import autokatta.com.networkreceiver.ConnectionDetector;
import autokatta.com.other.CustomToast;
import autokatta.com.response.AddManualEnquiryResponse;
import autokatta.com.response.GetFinancerNameResponse;
import autokatta.com.response.GetSourceOfEnquiryResponse;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddManualEnquiry extends AppCompatActivity implements RequestNotifier, View.OnTouchListener {

    EditText edtName, edtContact, edtAddress, edtDiscussion, edtDate, edtTime;
    AutoCompleteTextView autoAddress;
    Spinner spnInventory, spnStatus, spnSource;
    String myContact, mSource;
    LinearLayout txtUser, txtInvite, mInventory;
    ImageView imgContact;
    private ConnectionDetector mConnectionDetector;
    RelativeLayout mRelative;
    NestedScrollView scrollView;
    TextView mNoData;
    TextView Title, Category, Brand, Model, Keyword, price;
    RelativeLayout relCategory, relBrand, relModel, relPrice, mDetails;
    ImageView Image;
    Menu menu;
    String strFinancername;
    int strLoanAmt;
    float strLoanPer;
    String fullpath = "";
    Dialog mBottomSheetDialog;
    String mId, mKeyword, mClassname, mImage;
    private final int REQUEST_CODE = 99;
    Button mSubmit;
    String mFinancestatus = "";
    EditText othersource;
    TextInputLayout othersourcelayout;
    String SOURCE[] = null;
    final List<String> mFinancerList = new ArrayList<>();
    final HashMap<String, Integer> mFinancerList1 = new HashMap<>();
    List<String> parsedDataFinancer = new ArrayList<>();
    RadioButton financeyes, financeno, exchangeyes, exchangeno;


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
                spnSource = (Spinner) findViewById(R.id.spnsource);
                othersource = (EditText) findViewById(R.id.editothersource);
                othersourcelayout = (TextInputLayout) findViewById(R.id.othersourcelayout);
                edtDate = (EditText) findViewById(R.id.edtDate);
                edtTime = (EditText) findViewById(R.id.edtTime);
                txtUser = (LinearLayout) findViewById(R.id.txtUser);
                // mListView = (ListView) findViewById(R.id.vehicle_list);
                scrollView = (NestedScrollView) findViewById(R.id.scroll_view);
                imgContact = (ImageView) findViewById(R.id.contact_list);
                txtInvite = (LinearLayout) findViewById(R.id.txtInvite);
                mInventory = (LinearLayout) findViewById(R.id.selctinventory);
                mSubmit = (Button) findViewById(R.id.sub);

                financeyes = (RadioButton) findViewById(R.id.financeyes);
                financeno = (RadioButton) findViewById(R.id.financeno);
                exchangeyes = (RadioButton) findViewById(R.id.exchangeYes);
                exchangeno = (RadioButton) findViewById(R.id.exchangeNo);

                mConnectionDetector = new ConnectionDetector(AddManualEnquiry.this);

                getSourceofenquiry();
                RadioGroup mRadioGroup = (RadioGroup) findViewById(R.id.exchange);
                RadioGroup mFinanceRadioGroup = (RadioGroup) findViewById(R.id.financegrp);
                mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                        if (checkedId == R.id.exchangeYes) {
                        }
                    }
                });
                mFinanceRadioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                        if (checkedId == R.id.financeyes) {
                            mBottomSheetDialog = new Dialog(AddManualEnquiry.this, R.style.MaterialDialogSheet);
                            LayoutInflater layoutInflater = (LayoutInflater) AddManualEnquiry.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                            assert layoutInflater != null;
                            final View view = layoutInflater.inflate(R.layout.custom_enquiry_finance_info, null);
                            ImageView mClose = (ImageView) view.findViewById(R.id.close);
                            Button mAdd = (Button) view.findViewById(R.id.submit);
                            final EditText mLoanAmount = (EditText) view.findViewById(R.id.loanamt);
                            final EditText mLoanPer = (EditText) view.findViewById(R.id.loanpercent);
                            final AutoCompleteTextView mFinancerName = (AutoCompleteTextView) view.findViewById(R.id.financername);

                            mFinancestatus = "yes";
                            mClose.setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mBottomSheetDialog.dismiss();
                                    financeno.setChecked(true);
                                }
                            });

                            try {
                                if (mConnectionDetector.isConnectedToInternet()) {
                                    Retrofit retrofit = new Retrofit.Builder()
                                            .baseUrl(getString(R.string.base_url))
                                            .addConverterFactory(GsonConverterFactory.create())
                                            .client(initLog().build())
                                            .build();

                                    ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                                    Call<GetFinancerNameResponse> add = serviceApi._autokattagetFinancername();
                                    add.enqueue(new Callback<GetFinancerNameResponse>() {
                                        @Override
                                        public void onResponse(Call<GetFinancerNameResponse> call, Response<GetFinancerNameResponse> response) {
                                            if (response.isSuccessful()) {
                                                mFinancerList.clear();
                                                GetFinancerNameResponse mRepo = (GetFinancerNameResponse) response.body();
                                                for (GetFinancerNameResponse.Success success : mRepo.getSuccess()) {
                                                    success.setFinancierID(success.getFinancierID());
                                                    success.setFinancierName(success.getFinancierName());
                                                    mFinancerList.add(success.getFinancierName());
                                                }
                                                ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(mBottomSheetDialog.getContext(),
                                                        R.layout.registration_spinner, mFinancerList);
                                                mFinancerName.setAdapter(dataAdapter);


                                            } else {
                                                CustomToast.customToast(getApplicationContext(), getString(R.string._404));
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<GetFinancerNameResponse> call, Throwable t) {

                                        }
                                    });
                                } else
                                    CustomToast.customToast(getApplicationContext(), getString(R.string.no_internet));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                            mAdd.setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    strFinancername = mFinancerName.getText().toString();
                                    if (!mFinancerList.contains(strFinancername) && !strFinancername.equalsIgnoreCase("")) {
                                        addFinancername(strFinancername);
                                    }
                                    if (mLoanAmount.getText().toString().equalsIgnoreCase("")) {
                                        mLoanAmount.setError("Please add Loan Amount");
                                    } else if (mLoanPer.getText().toString().equalsIgnoreCase("")) {
                                        mLoanPer.setError("Please add Loan Percentage");
                                    } else if (mFinancerName.getText().toString().equalsIgnoreCase("")) {
                                        mFinancerName.setError("Please add Financer Name");
                                    } else {
                                        strLoanAmt = Integer.parseInt(mLoanAmount.getText().toString());
                                        strLoanPer = Float.parseFloat(mLoanPer.getText().toString());
                                        strFinancername = mFinancerName.getText().toString();
                                        mBottomSheetDialog.dismiss();
                                    }

                                }
                            });

                            mBottomSheetDialog.setContentView(view);
                            mBottomSheetDialog.setCancelable(true);
                            mBottomSheetDialog.getWindow().setTitle("Finance Info");
                            mBottomSheetDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                            mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            mBottomSheetDialog.getWindow().setGravity(Gravity.TOP);
                            mBottomSheetDialog.show();
                        }
                    }


                });
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


                    mKeyword = getIntent().getExtras().getString("keyword");
                    Keyword.setText(mKeyword);
                    Category.setText(getIntent().getExtras().getString("category"));
                    Title.setText(getIntent().getExtras().getString("title"));
                    Brand.setText(getIntent().getExtras().getString("brand"));
                    Model.setText(getIntent().getExtras().getString("model"));
                    price.setText(getIntent().getExtras().getString("price"));
                    mImage = getIntent().getExtras().getString("image", "");
                    mId = String.valueOf(getIntent().getExtras().getInt("id"));
                    mClassname = getIntent().getExtras().getString("classname");

                    if (getIntent().getExtras().getString("keyword", "").equalsIgnoreCase("Products")) {
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
                    } else if (getIntent().getExtras().getString("keyword", "").equalsIgnoreCase("Services")) {
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
                    } else if (getIntent().getExtras().getString("keyword", "").equalsIgnoreCase("Used Vehicle")) {
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
                    } else if (getIntent().getExtras().getString("keyword", "").equalsIgnoreCase("New Vehicle")) {
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
                            //  int strPos = spnInventory.getSelectedItemPosition();
                            int strPos1 = spnStatus.getSelectedItemPosition();
                            String custName = edtName.getText().toString();
                            String custContact = edtContact.getText().toString();
                            String custAddress = autoAddress.getText().toString();
                            String custFullAddress = edtAddress.getText().toString();
                            custInventoryType = mKeyword;

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
                                        CustomToast.customToast(AddManualEnquiry.this, "Please Select Valid Address ");
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
                            } else if (flag) {
                                autoAddress.setError("Please provide proper address");
                                autoAddress.requestFocus();
                            } else if (custFullAddress.equals("")) {
                                edtAddress.setError("Enter Detailed address");
                                edtAddress.requestFocus();
                            } else if (spnStatus.getSelectedItem().toString().equalsIgnoreCase("select enquiry status")) {
                                CustomToast.customToast(getApplicationContext(), "Please provide status");
                                spnStatus.requestFocus();
                            } else if (spnStatus.getSelectedItem().toString().equalsIgnoreCase("other") && othersource.getText().toString().equalsIgnoreCase("")) {
                                othersource.setError("Please enter status");
                                othersource.setFocusable(true);
                            } else if (spnSource.getSelectedItem().toString().equalsIgnoreCase("select source of enquiry")) {
                                CustomToast.customToast(getApplicationContext(), "Please provide source");
                                spnStatus.requestFocus();
                            } else if (!spnSource.getSelectedItem().toString().equalsIgnoreCase("select source of enquiry") && !spnSource.getSelectedItem().toString().equalsIgnoreCase("other")) {
                                mSource = spnSource.getSelectedItem().toString();
                            } else if (spnSource.getSelectedItem().toString().equalsIgnoreCase("other") && othersource.getText().toString().equalsIgnoreCase("")) {
                                othersource.setError("Please enter source");
                                othersource.setFocusable(true);
                            } else if (spnSource.getSelectedItem().toString().equalsIgnoreCase("other")) {
                                addsource(othersource.getText().toString());
                                mSource = othersource.getText().toString();
                            } else if (nextFollowupDate.equals("") || nextFollowupDate.startsWith(" ")) {
                                edtDate.setError("Enter Date");
                                edtDate.requestFocus();
                            } else {
                                AddEnquiryData(custName, custContact, custAddress, custFullAddress, custInventoryType, custEnquiryStatus,
                                        discussion, nextFollowupDate, mId);
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
                        sendSMSMessage(edtContact.getText().toString());
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

    private void addsource(String othersource) {
        ApiCall mApiCall = new ApiCall(this, this);
        mApiCall.addOthersource(othersource);
    }

    private void getSourceofenquiry() {
        ApiCall mApiCall = new ApiCall(this, this);
        mApiCall.getSourceOfEnquiry();
    }

    private void sendSMSMessage(String con) {
        Log.i("Send SMS", "");

        try {
            android.telephony.SmsManager smsManager = android.telephony.SmsManager.getDefault();
            smsManager.sendTextMessage(con, null, "Hi I am using Autokatta\n Please click below link to download and connect with me for more https://play.google.com/store/apps/details?id=autokatta.com", null, null);
            CustomToast.customToast(this, "SMS sent.");
        } catch (Exception e) {
            CustomToast.customToast(this, "SMS failed, please try again.");
            e.printStackTrace();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_manual_enquiry, menu);
        this.menu = menu;
        MenuItem item = menu.findItem(R.id.ok_manual);
        if (mClassname != null) {
            item.setVisible(false);
        }
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
                } else if (nextFollowupDate.equals("") || nextFollowupDate.startsWith(" ")) {
                    edtDate.setError("Enter Date");
                    edtDate.requestFocus();
                } else if (spnSource.getSelectedItem().toString().equalsIgnoreCase("select source of enquiry")) {
                    CustomToast.customToast(getApplicationContext(), "Please provide source");
                    spnStatus.requestFocus();
                } else if (spnSource.getSelectedItem().toString().equalsIgnoreCase("other") && othersource.getText().toString().equalsIgnoreCase("")) {
                    othersource.setError("Please enter source");
                    othersource.setFocusable(true);
                } else {
                    if (!spnSource.getSelectedItem().toString().equalsIgnoreCase("select source of enquiry") && !spnSource.getSelectedItem().toString().equalsIgnoreCase("other")) {
                        mSource = spnSource.getSelectedItem().toString();
                    } else if (spnSource.getSelectedItem().toString().equalsIgnoreCase("other")) {
                        addsource(othersource.getText().toString());
                        mSource = othersource.getText().toString();
                    }

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
                    bundle.putString("nextFollowupDate", nextFollowupDate);
                    bundle.putString("source", mSource);
                    bundle.putFloat("loanpercent", strLoanPer);
                    bundle.putInt("loanamt", strLoanAmt);
                    bundle.putString("financername", strFinancername);
                    bundle.putString("financerstatus", mFinancestatus);

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
                custEnquiryStatus, discussion, nextFollowupDate, addArray, mSource, strFinancername, strLoanAmt, strLoanPer, mFinancestatus);
    }

    private void addFinancername(String fName) {
        ApiCall mApiCall = new ApiCall(this, this);
        mApiCall.addFinancerName(fName);
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
    }

    @Override
    public void notifySuccess(Response<?> response) {
        if (response != null) {
            if (response.isSuccessful()) {
                if (response.body() instanceof AddManualEnquiryResponse) {
                    AddManualEnquiryResponse enquiryResponse = (AddManualEnquiryResponse) response.body();
                    if (enquiryResponse.getSuccess() != null) {
                        if (enquiryResponse.getSuccess().getSuccess().equalsIgnoreCase("Data successfully Inserted.")) {
                            CustomToast.customToast(AddManualEnquiry.this, "Data Inserted Successfully");
                            finish();
                        }
                    } else {
                        CustomToast.customToast(getApplicationContext(), getString(R.string.no_internet));
                    }
                } else if (response.body() instanceof GetSourceOfEnquiryResponse) {
                    GetSourceOfEnquiryResponse moduleResponse = (GetSourceOfEnquiryResponse) response.body();
                    final List<String> source = new ArrayList<>();
                    if (!moduleResponse.getSuccess().isEmpty()) {
                        source.add("select source of enquiry");
                        for (GetSourceOfEnquiryResponse.Success message : moduleResponse.getSuccess()) {
                            source.add(message.getSource());
                        }
                        source.add("Other");
                        SOURCE = new String[source.size()];
                        SOURCE = (String[]) source.toArray(SOURCE);
                        ArrayAdapter<String> dataadapter = new ArrayAdapter<>(getApplicationContext(), R.layout.registration_spinner, SOURCE);
                        spnSource.setAdapter(dataadapter);

                        spnSource.setOnItemSelectedListener(new OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                if (spnSource.getSelectedItem().toString().equalsIgnoreCase("other")) {
                                    othersourcelayout.setVisibility(View.VISIBLE);
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                    } else
                        CustomToast.customToast(getApplicationContext(), getString(R.string.no_response));
                }
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
            } else if (str.equalsIgnoreCase("success_source_added")) {
                CustomToast.customToast(this, "New Source Added");
            } else if (str.equalsIgnoreCase("success_financier_added")) {
                CustomToast.customToast(this, "New Financer Added");
                mBottomSheetDialog.dismiss();
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
                        assert contactData != null;
                        Cursor c = getContentResolver().query(contactData, null, null, null, null);
                        assert c != null;
                        if (c.moveToFirst()) {
                            String contactId = c.getString(c.getColumnIndex(ContactsContract.Contacts._ID));
                            String hasNumber = c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                            String num = "";
                            if (Integer.valueOf(hasNumber) == 1) {
                                Cursor numbers = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null);
                                assert numbers != null;
                                while (numbers.moveToNext()) {
                                    num = numbers.getString(numbers.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                                    num = num.replaceAll("-", "");
                                    num = num.replace("(", "").replace(")", "").replaceAll(" ", "").replaceAll("[\\D]", "");

                                    if (num.length() > 10)
                                        num = num.substring(num.length() - 10);
                                    Log.i("Number", "AddManualEnquiry" + num);
                                    edtContact.setText(num);
                                }
                                numbers.close();
                            }
                        }
                        c.close();
                        break;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
        }
    }

    /***
     * Retrofit Logs
     ***/
    private OkHttpClient.Builder initLog() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging).readTimeout(90, TimeUnit.SECONDS);
        return httpClient;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mBottomSheetDialog != null) {
            mBottomSheetDialog.dismiss();
            mBottomSheetDialog = null;
        }
    }
}
