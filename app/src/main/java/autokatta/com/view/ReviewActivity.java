package autokatta.com.view;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.kaopiz.kprogresshud.KProgressHUD;

import java.net.SocketTimeoutException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import autokatta.com.R;
import autokatta.com.adapter.ReviewAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.networkreceiver.ConnectionDetector;
import autokatta.com.other.CustomToast;
import autokatta.com.response.ReviewAndReplyResponse;
import retrofit2.Response;


public class ReviewActivity extends AppCompatActivity implements RequestNotifier, View.OnClickListener {

    ConnectionDetector mConnectionDetector;
    ApiCall mApiCall;
    String contact;
    KProgressHUD hud;
    private ProgressDialog dialog;
    public List<ReviewAndReplyResponse.Success.ReviewMessage> topList = new ArrayList<>();
    public List<ReviewAndReplyResponse.Success.ReviewMessage> mainList = new ArrayList<>();
    public List<ReviewAndReplyResponse.Success.ReplayMessage> childlist;
    AlertDialog alert;
    ListView listView;
    ReviewAdapter adapter;
    int store_id, product_id, service_id, vehicle_id;
    ImageView uploadImage;
    TextView addimagetext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Reviews");

        mApiCall = new ApiCall(ReviewActivity.this, this);
        contact = getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE)
                .getString("loginContact", "");


        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...");
        mConnectionDetector = new ConnectionDetector(this);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        listView = (ListView) findViewById(R.id.msgview);
        fab.setOnClickListener(this);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (getSupportActionBar() != null) {
                        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                        getSupportActionBar().setDisplayShowHomeEnabled(true);
                    }

                    if (getIntent().getExtras() != null) {
                        store_id = getIntent().getExtras().getInt("store_id", 0);
                        product_id = getIntent().getExtras().getInt("product_id", 0);
                        service_id = getIntent().getExtras().getInt("service_id", 0);
                        vehicle_id = getIntent().getExtras().getInt("vehicle_id", 0);

                    }



                    hud = KProgressHUD.create(ReviewActivity.this)
                            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                            .setLabel("Please wait")
                            .setMaxProgress(100)
                            .show();
                    if (!mConnectionDetector.isConnectedToInternet()) {
                        Toast.makeText(ReviewActivity.this, "Please check your internet connection", Toast.LENGTH_SHORT).show();
                    } else {
                        mApiCall.getReviewOrReply(store_id, product_id, service_id, vehicle_id);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });



    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab:
                hud.dismiss();
                //write review here
                sendmessage();
                break;

        }

    }

    //alert dialog box method to show pop up to send message and image in broadcast group

    public void sendmessage() {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(ReviewActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View convertView = inflater.inflate(R.layout.custom_broadmessage_layout, null);
        final EditText message = (EditText) convertView.findViewById(R.id.statustext);
        Button send = (Button) convertView.findViewById(R.id.btnsend);
        Button cancel = (Button) convertView.findViewById(R.id.btncancel);
        addimagetext = (TextView) convertView.findViewById(R.id.addimagetext);
        uploadImage = (ImageView) convertView.findViewById(R.id.upadateimg);
        addimagetext.setVisibility(View.GONE);
        uploadImage.setVisibility(View.GONE);
        final TextView wordCount = (TextView) convertView.findViewById(R.id.counttxt);
        alertDialog.setView(convertView);
        alert = alertDialog.show();
        alertDialog.setTitle("Send Message");
        message.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                wordCount.setText(String.valueOf(200 - s.length()));
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }
        });


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mApiCall.postReviewOrReply(0, "Review", contact, message.getText().toString(), store_id, product_id, service_id, vehicle_id);
                alert.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();

            }
        });
    }


    @Override
    public void notifySuccess(Response<?> response) {

        DateFormat f = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        if (response != null) {
            if (response.isSuccessful()) {
                hud.dismiss();
                /*
                        Response to get review message
                 */
                if (response.body() instanceof ReviewAndReplyResponse) {
                    ReviewAndReplyResponse moduleResponse = (ReviewAndReplyResponse) response.body();
                    childlist = new ArrayList<>();
                    ReviewAndReplyResponse.Success object = moduleResponse.getSuccess();
                    for (ReviewAndReplyResponse.Success.ReviewMessage obj : object.getReviewMessage()) {
                        obj.setSenderContact(obj.getSenderContact());
                        obj.setReviewString(obj.getReviewString());
                        obj.setCreatedDate(obj.getCreatedDate());
                        obj.setReviewId(obj.getReviewId());

                        obj.setReplayMessage(childlist);
                        mainList.add(obj);

                    }

                    for (ReviewAndReplyResponse.Success.ReplayMessage objectmatch : object.getReplayMessage()) {
                        objectmatch.setCreatedDate(objectmatch.getCreatedDate());
                        objectmatch.setReplayId(objectmatch.getReplayId());
                        objectmatch.setSenderContact(objectmatch.getSenderContact());
                        objectmatch.setReplayString(objectmatch.getReplayString());
                        childlist.add(objectmatch);

                    }


                    adapter = new ReviewAdapter(ReviewActivity.this, mainList, contact);
                    listView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                } else {
                    hud.dismiss();
                    CustomToast.customToast(getApplicationContext(), getString(R.string._404));
                }
            } else {
                hud.dismiss();
                CustomToast.customToast(getApplicationContext(), getString(R.string.no_response));
            }

        }
    }

    @Override
    public void notifyError(Throwable error) {
        hud.dismiss();
        if (error instanceof SocketTimeoutException) {
            CustomToast.customToast(getApplicationContext(), getString(R.string._404));
        } else if (error instanceof NullPointerException) {
            CustomToast.customToast(getApplicationContext(), getString(R.string.no_response));
        } else if (error instanceof ClassCastException) {
            CustomToast.customToast(getApplicationContext(), getString(R.string.no_response));
        } else {
            Log.i("Check Class-", "ChatActivity");
            error.printStackTrace();
        }
    }

    @Override
    public void notifyString(String str) {
        if (str != null) {
            if (str.equals("sent_review")) {
                hud.dismiss();
                CustomToast.customToast(ReviewActivity.this, "Review Posted");
                mApiCall.getReviewOrReply(store_id, product_id, service_id, vehicle_id);
            } else if (str.equals("sent_reply")) {
                hud.dismiss();
                CustomToast.customToast(ReviewActivity.this, "Reply Sent");
                mApiCall.getReviewOrReply(store_id, product_id, service_id, vehicle_id);
            }
        }

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
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
    }
}
