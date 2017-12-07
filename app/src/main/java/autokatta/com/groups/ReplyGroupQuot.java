package autokatta.com.groups;

import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.net.SocketTimeoutException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import autokatta.com.R;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.networkreceiver.ConnectionDetector;
import autokatta.com.other.CustomToast;
import autokatta.com.response.GetReviewQuotResponse;
import autokatta.com.view.OtherProfile;
import autokatta.com.view.UserProfile;
import retrofit2.Response;

public class ReplyGroupQuot extends AppCompatActivity implements RequestNotifier {

    ConnectionDetector mConnectionDetector;
    public List<GetReviewQuotResponse.Success.ReviewMessage> mainList = new ArrayList<>();
    public List<GetReviewQuotResponse.Success.ReplayMessage> childlist;
    private ProgressDialog dialog;
    int sendQuotID;
    private LinearLayout mLinearListView;
    boolean isFirstViewClick[];
    LinearLayout mLinearScrollSecond[];
    String myContact;
    AlertDialog alert;
    TextView addimagetext;
    ImageView uploadImage;
    EditText mReviewEnter;
    Button mSend;
    private int mQuotationOtherID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply_group_quot);
        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...");
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        mConnectionDetector = new ConnectionDetector(this);
        if (getIntent().getExtras() != null) {
            mQuotationOtherID = getIntent().getExtras().getInt("QuotationOtherid");
            getQuotReviewReply(mQuotationOtherID);
        }
        mLinearListView = (LinearLayout) findViewById(R.id.linear_ListView);
        mReviewEnter = (EditText) findViewById(R.id.review_enter);
        mSend = (Button) findViewById(R.id.send);
        myContact = getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE)
                .getString("loginContact", "");
        mSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postQuotReview(mQuotationOtherID, "Review", myContact, mReviewEnter.getText().toString(), 0);
            }
        });

    }

    public void getQuotReviewReply(int mQuotationOtherID) {
        if (mConnectionDetector.isConnectedToInternet()) {
            dialog.show();
            ApiCall apiCall = new ApiCall(this, this);
            apiCall.GetReviewQuot(mQuotationOtherID);
        } else {
            CustomToast.customToast(getApplicationContext(), getString(R.string.no_internet));
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
    }

    @Override
    public void notifySuccess(Response<?> response) {
        if (response != null) {
            if (response.isSuccessful()) {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
                if (response.body() instanceof GetReviewQuotResponse) {
                    GetReviewQuotResponse quotReviewReply = (GetReviewQuotResponse) response.body();
                    mainList.clear();
                    GetReviewQuotResponse.Success object = quotReviewReply.getSuccess();
                    for (GetReviewQuotResponse.Success.ReviewMessage message : object.getReviewMessage()) {
                        childlist = new ArrayList<>();
                        message.setReviewQuoteId(message.getReviewQuoteId());
                        message.setReviewString(message.getReviewString());
                        message.setSenderContact(message.getSenderContact());
                        message.setCreatedDate1(message.getCreatedDate1());
                        message.setCustomerName(message.getCustomerName());
                        message.setCustomerPic(message.getCustomerPic());

                        for (GetReviewQuotResponse.Success.ReplayMessage objectmatch : object.getReplayMessage()) {
                            if (message.getReviewQuoteId().equals(objectmatch.getReviewQuoteId())) {
                                objectmatch.setCreatedDate1(objectmatch.getCreatedDate1());
                                objectmatch.setReplyQuoteId(objectmatch.getReviewQuoteId());
                                objectmatch.setSenderContact(objectmatch.getSenderContact());
                                objectmatch.setReplayString(objectmatch.getReplayString());
                                objectmatch.setCustomerName(objectmatch.getCustomerName());
                                objectmatch.setCustomerPic(objectmatch.getCustomerPic());
                                childlist.add(objectmatch);
                            }
                        }
                        message.setReplayMessage(childlist);
                        mainList.add(message);
                    }

                    System.out.println("main list size=" + mainList.size());
                    mLinearScrollSecond = new LinearLayout[mainList.size()];
                    for (int i = 0; i < mainList.size(); i++) {
                        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        assert inflater != null;
                        View mLinearView = inflater.inflate(R.layout.review_layout, null);
                        final TextView msg = (TextView) mLinearView.findViewById(R.id.msgr);
                        final TextView dateNtime = (TextView) mLinearView.findViewById(R.id.dateNtime);
                        final RelativeLayout mLinearFirstArrow = (RelativeLayout) mLinearView.findViewById(R.id.linearFirst);
                        final ImageView replyImage = (ImageView) mLinearView.findViewById(R.id.reply);
                        final ImageView profile = (ImageView) mLinearView.findViewById(R.id.profile);
                        mLinearScrollSecond[i] = (LinearLayout) mLinearView.findViewById(R.id.linear_scroll);

                        msg.setText(mainList.get(i).getReviewString());
                        final int finalI2 = i;
                        profile.setOnClickListener(new View.OnClickListener() {
                            Bundle bundle = new Bundle();

                            @Override
                            public void onClick(View view) {
                                bundle.putString("contactOtherProfile", mainList.get(finalI2).getSenderContact());
                                if (myContact.equalsIgnoreCase(mainList.get(finalI2).getSenderContact())) {
                                    ActivityOptions options = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.ok_left_to_right, R.anim.ok_right_to_left);
                                    Intent i = new Intent(getApplicationContext(), UserProfile.class);
                                    i.putExtras(bundle);
                                    startActivity(i, options.toBundle());

                                } else {
                                    ActivityOptions options = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.ok_left_to_right, R.anim.ok_right_to_left);
                                    Intent i = new Intent(getApplicationContext(), OtherProfile.class);
                                    i.putExtras(bundle);
                                    startActivity(i, options.toBundle());
                                }
                            }
                        });
                        if (!mainList.get(i).getCustomerPic().equals("")) {
                            String dp_path = getString(R.string.base_image_url) + mainList.get(i).getCustomerPic();
                            Glide.with(getApplicationContext())
                                    .load(dp_path)
                                    .centerCrop()
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .placeholder(R.drawable.logo)
                                    .into(profile);
                        }

                        try {
                            TimeZone utc = TimeZone.getTimeZone("etc/UTC");
                            //format of date coming from services
                            DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault());
                            inputFormat.setTimeZone(utc);

                            //format of date which we want to show
                            DateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy hh:mm a", Locale.getDefault());
                            outputFormat.setTimeZone(utc);

                            Date date = inputFormat.parse(mainList.get(i).getCreatedDate1());
                            String output = outputFormat.format(date);
                            dateNtime.setText(mainList.get(i).getCustomerName() + " " + output);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        final int finalI = i;
                        sendQuotID = mainList.get(finalI).getReviewQuoteId();
                        replyImage.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                sendmessage("Reply", mainList.get(finalI).getReviewQuoteId());
                            }
                        });

                        //Adds data into second row
                        for (int j = 0; j < mainList.get(i).getReplayMessage().size(); j++) {
                            LayoutInflater inflater2 = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                            assert inflater2 != null;
                            View mLinearView2 = inflater2.inflate(R.layout.reply_layout, null);

                            TextView reply = (TextView) mLinearView2.findViewById(R.id.msgr);
                            ImageView profilePic = (ImageView) mLinearView2.findViewById(R.id.profile);

                            TextView repdateNtime = (TextView) mLinearView2.findViewById(R.id.dateNtime);
                            reply.setText(mainList.get(i).getReplayMessage().get(j).getReplayString());

                            if (!mainList.get(i).getReplayMessage().get(j).getCustomerPic().equals("")) {
                                String dp_path = getString(R.string.base_image_url) + mainList.get(i).getReplayMessage().get(j).getCustomerPic();
                                Glide.with(getApplicationContext())
                                        .load(dp_path)
                                        .centerCrop()
                                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                                        .placeholder(R.drawable.logo)
                                        .into(profilePic);
                            }

                            final int finalI1 = i;
                            final int finalJ = j;
                            profilePic.setOnClickListener(new View.OnClickListener() {
                                Bundle bundle = new Bundle();

                                @Override
                                public void onClick(View view) {

                                    bundle.putString("contactOtherProfile", mainList.get(finalI1).getReplayMessage().get(finalJ).getSenderContact());
                                    if (myContact.equalsIgnoreCase(mainList.get(finalI1).getReplayMessage().get(finalJ).getSenderContact())) {
                                        ActivityOptions options = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.ok_left_to_right, R.anim.ok_right_to_left);
                                        Intent i = new Intent(getApplicationContext(), UserProfile.class);
                                        i.putExtras(bundle);
                                        startActivity(i, options.toBundle());

                                    } else {
                                        ActivityOptions options = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.ok_left_to_right, R.anim.ok_right_to_left);
                                        Intent i = new Intent(getApplicationContext(), OtherProfile.class);
                                        i.putExtras(bundle);
                                        startActivity(i, options.toBundle());

                                    }
                                }
                            });

                            try {
                                TimeZone utc = TimeZone.getTimeZone("etc/UTC");
                                //format of date coming from services
                                DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault());
                                inputFormat.setTimeZone(utc);

                                //format of date which we want to show
                                DateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy hh:mm a", Locale.getDefault());
                                outputFormat.setTimeZone(utc);

                                Date date = inputFormat.parse(mainList.get(i).getReplayMessage().get(j).getCreatedDate1());
                                String output = outputFormat.format(date);
                                repdateNtime.setText(mainList.get(i).getReplayMessage().get(j).getCustomerName() + " replied " + output);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            mLinearScrollSecond[i].addView(mLinearView2);
                        }
                        mLinearListView.addView(mLinearView);
                    }
                } else {
                    if (dialog.isShowing()) {
                        dialog.dismiss();
                    }
                    CustomToast.customToast(getApplicationContext(), getString(R.string._404));
                }
            } else {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
                CustomToast.customToast(getApplicationContext(), getString(R.string.no_response));
            }
        }
    }

    /*
    send message
     */

    public void sendmessage(final String keyword, final int ReviewQuoteID) {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(ReplyGroupQuot.this);
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
        //alertDialog.setTitle("Send Message");
        message.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                wordCount.setText(String.valueOf(200 - s.length()));
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postQuotReview(0, keyword, myContact, message.getText().toString(), ReviewQuoteID);
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

    private void postQuotReview(int quotationID, String keyword, String myContact, String message, int ReviewQuoteID) {
        if (mConnectionDetector.isConnectedToInternet()) {
            dialog.show();
            // quotationReply
            ApiCall apiCall = new ApiCall(ReplyGroupQuot.this, this);
            apiCall.quotationReply(quotationID, keyword, myContact, message, ReviewQuoteID);
        } else {
            CustomToast.customToast(getApplicationContext(), getString(R.string.no_internet));
        }
    }

    @Override
    public void notifyError(Throwable error) {
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
        if (error instanceof SocketTimeoutException) {
            CustomToast.customToast(getApplicationContext(), getString(R.string._404));
        } else if (error instanceof NullPointerException) {
            CustomToast.customToast(getApplicationContext(), getString(R.string.no_response));
        } else if (error instanceof ClassCastException) {
            CustomToast.customToast(getApplicationContext(), getString(R.string.no_response));
        } else {
            Log.i("Check Class-", "ReplyGroupQuot");
            error.printStackTrace();
        }
    }

    @Override
    public void notifyString(String str) {
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
        if (str != null) {
            if (str.equals("sent_reply")) {
                Log.i("str", "->" + str);
            }
        }
    }
}
