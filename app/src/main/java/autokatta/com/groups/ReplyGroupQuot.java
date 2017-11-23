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
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import autokatta.com.response.QuotReviewReply;
import autokatta.com.view.OtherProfile;
import autokatta.com.view.UserProfile;
import retrofit2.Response;

public class ReplyGroupQuot extends AppCompatActivity implements RequestNotifier {

    ConnectionDetector mConnectionDetector;
    public List<QuotReviewReply.Success.ReviewMessage> mainList = new ArrayList<>();
    public List<QuotReviewReply.Success.ReplayMessage> childlist;
    private ProgressDialog dialog;
    int vehicleId;
    private LinearLayout mLinearListView;
    boolean isFirstViewClick[];
    LinearLayout mLinearScrollSecond[];
    String myContact;
    AlertDialog alert;
    TextView addimagetext;
    ImageView uploadImage;

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
            vehicleId = getIntent().getExtras().getInt("VehicleId");
            getQuotReviewReply(vehicleId);
        }
        mLinearListView = (LinearLayout) findViewById(R.id.linear_ListView);
        myContact = getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE)
                .getString("loginContact", "");
    }

    public void getQuotReviewReply(int vehicleId) {
        if (mConnectionDetector.isConnectedToInternet()) {
            dialog.show();
            ApiCall apiCall = new ApiCall(this, this);
            apiCall.QuotReviewReply(vehicleId);
        } else {
            Toast.makeText(getApplicationContext(), "No Internet connection", Toast.LENGTH_SHORT).show();
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
                if (response.body() instanceof QuotReviewReply) {
                    QuotReviewReply quotReviewReply = (QuotReviewReply) response.body();
                    mainList.clear();
                    QuotReviewReply.Success object = quotReviewReply.getSuccess();
                    for (QuotReviewReply.Success.ReviewMessage message : object.getReviewMessage()) {
                        childlist = new ArrayList<>();
                        message.setReviewId(message.getReviewId());
                        message.setReviewString(message.getReviewString());
                        message.setSenderContact(message.getSenderContact());
                        message.setCreatedDate1(message.getCreatedDate1());

                        for (QuotReviewReply.Success.ReplayMessage objectmatch : object.getReplayMessage()) {
                            if (message.getReviewId().equals(objectmatch.getReviewId())) {
                                objectmatch.setCreatedDate1(objectmatch.getCreatedDate1());
                                objectmatch.setReplayId(objectmatch.getReplayId());
                                objectmatch.setSenderContact(objectmatch.getSenderContact());
                                objectmatch.setReplayString(objectmatch.getReplayString());
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

                        try {
                            TimeZone utc = TimeZone.getTimeZone("etc/UTC");
                            //format of date coming from services
                            DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault());
                        /*DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
                                Locale.getDefault());*/
                            inputFormat.setTimeZone(utc);

                            //format of date which we want to show
                            DateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy hh:mm a", Locale.getDefault());
                        /*DateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy hh:mm aa",
                                Locale.getDefault());*/
                            outputFormat.setTimeZone(utc);

                            Date date = inputFormat.parse(mainList.get(i).getCreatedDate1());
                            //System.out.println("jjj"+date);
                            String output = outputFormat.format(date);
                            //System.out.println(mainList.get(i).getDate()+" jjj " + output);
                            dateNtime.setText(mainList.get(i).getSenderContact() + " " + output);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        final int finalI = i;
                        replyImage.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                sendmessage(mainList.get(finalI).getReviewId(), "Reply");
                            }
                        });

                        //Adds data into second row
                        for (int j = 0; j < mainList.get(i).getReplayMessage().size(); j++) {
                            LayoutInflater inflater2 = null;
                            int showcheckboc = 0;
                            inflater2 = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                            View mLinearView2 = inflater2.inflate(R.layout.reply_layout, null);

                            TextView reply = (TextView) mLinearView2.findViewById(R.id.msgr);
                            ImageView profilePic = (ImageView) mLinearView2.findViewById(R.id.profile);

                            TextView repdateNtime = (TextView) mLinearView2.findViewById(R.id.dateNtime);
                            reply.setText(mainList.get(i).getReplayMessage().get(j).getReplayString());

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
                        /*DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
                                Locale.getDefault());*/
                                inputFormat.setTimeZone(utc);

                                //format of date which we want to show
                                DateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy hh:mm a", Locale.getDefault());
                        /*DateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy hh:mm aa",
                                Locale.getDefault());*/
                                outputFormat.setTimeZone(utc);

                                Date date = inputFormat.parse(mainList.get(i).getReplayMessage().get(j).getCreatedDate1());
                                //System.out.println("jjj"+date);
                                String output = outputFormat.format(date);
                                //System.out.println(mainList.get(i).getDate()+" jjj " + output);
                                repdateNtime.setText(mainList.get(i).getReplayMessage().get(j).getSenderContact() + " replied " + output);
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

    public void sendmessage(final int QuotationID, final String keyword) {
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
                ///postQuotReview(QuotationID, keyword, myContact, message.getText().toString(), vehicleId, GroupID, type);
                alert.dismiss();
                //Start with this please compete tomorrow
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();
            }
        });
    }

    private void postQuotReview() {
        // quotationReply
    }

    @Override
    public void notifyError(Throwable error) {

    }

    @Override
    public void notifyString(String str) {

    }
}
