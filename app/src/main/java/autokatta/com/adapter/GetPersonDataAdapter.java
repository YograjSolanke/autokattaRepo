package autokatta.com.adapter;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.telephony.gsm.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.other.CustomToast;
import autokatta.com.response.GetPersonDataResponse;
import autokatta.com.view.EnquiredPersonsListActivity;

/**
 * Created by ak-001 on 11/5/17.
 */

public class GetPersonDataAdapter extends RecyclerView.Adapter<GetPersonDataAdapter.PersonData> {
    Activity mActivity;
    List<GetPersonDataResponse.Success> list = new ArrayList<>();
    private String strId, strKeyword, strTitle;
    int mEnquiryID;

    public GetPersonDataAdapter(Activity mActivity, List<GetPersonDataResponse.Success> list, String strId, String strKeyword, String strTitle,int enquiryid) {
        this.mActivity = mActivity;
        this.list = list;
        this.strId = strId;
        this.strKeyword = strKeyword;
        this.strTitle = strTitle;
        this.mEnquiryID = enquiryid;
    }


    static class PersonData extends RecyclerView.ViewHolder {
        TextView mPersonName, mContact, mAddress, mFollowUpDate, mDiscussion, mInvite, mLastEnquiry, mEnquiryStatus;
        ImageView mProfilePic, mCallImg, mMailImage, mIsAuto;
        CardView mCardView;

        PersonData(View itemView) {
            super(itemView);
            mPersonName = (TextView) itemView.findViewById(R.id.name);
            mContact = (TextView) itemView.findViewById(R.id.contact);
            mAddress = (TextView) itemView.findViewById(R.id.address);
            mFollowUpDate = (TextView) itemView.findViewById(R.id.follow_up);
            mDiscussion = (TextView) itemView.findViewById(R.id.discussion);
            mProfilePic = (ImageView) itemView.findViewById(R.id.user_image);
            mCallImg = (ImageView) itemView.findViewById(R.id.call_image);
            mMailImage = (ImageView) itemView.findViewById(R.id.mail_image);
            mIsAuto = (ImageView) itemView.findViewById(R.id.is_auto);
            mInvite = (TextView) itemView.findViewById(R.id.txtInvite);
            mLastEnquiry = (TextView) itemView.findViewById(R.id.last_enquiry);
            mEnquiryStatus = (TextView) itemView.findViewById(R.id.enquiryStatus);
            mCardView = (CardView) itemView.findViewById(R.id.person_card_view);
        }
    }

    @Override
    public PersonData onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_person_data, parent, false);
        // set the view's size, margins, paddings and layout parameters
        return new PersonData(v);
    }

    @Override
    public void onBindViewHolder(final PersonData holder, final int position) {
        holder.mPersonName.setText(list.get(position).getUsername());
        holder.mContact.setText(list.get(position).getContactNo());
        holder.mAddress.setText(list.get(position).getCity());
        holder.mFollowUpDate.setText(list.get(position).getNextFollowupDate());
        holder.mLastEnquiry.setText(list.get(position).getLastEnquiryDate());
        holder.mEnquiryStatus.setText(list.get(position).getCustEnquiryStatus());
        holder.mDiscussion.setText(list.get(position).getLastDiscussion());

        if (list.get(position).getProfilePic().equals("") || list.get(position).getProfilePic().equals("null")
                || list.get(position).getProfilePic().equals(null)) {
            holder.mProfilePic.setBackgroundResource(R.drawable.logo48x48);
        } else {
            String used_pic = mActivity.getString(R.string.base_image_url) + list.get(position).getProfilePic();
            Glide.with(mActivity)
                    .load(used_pic)
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.mProfilePic);
        }

        holder.mCallImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                call(holder.mContact.getText().toString());
            }
        });

        holder.mMailImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmail();
            }
        });
        holder.mInvite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendSMSMessage(holder.mContact.getText().toString(), "hhhh");
            }
        });

        if (list.get(position).getIsPresent().equals("yes")) {
            holder.mIsAuto.setVisibility(View.VISIBLE);
            holder.mInvite.setVisibility(View.GONE);
            holder.mContact.setTypeface(Typeface.DEFAULT_BOLD);
        } else {
            holder.mIsAuto.setVisibility(View.GONE);
            holder.mInvite.setVisibility(View.VISIBLE);
        }

        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mActivity, EnquiredPersonsListActivity.class);
                intent.putExtra("id", strId);
                intent.putExtra("keyword", strKeyword);
                intent.putExtra("name", strTitle);
                intent.putExtra("contact", holder.mContact.getText().toString());
                intent.putExtra("custname", holder.mPersonName.getText().toString());
                intent.putExtra("address", holder.mAddress.getText().toString());
                intent.putExtra("enquiryid", mEnquiryID);

                mActivity.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    //Calling Functionality
    private void call(String rcontact) {
        Intent in = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + rcontact));
        try {
            if (ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            mActivity.startActivity(in);
        } catch (android.content.ActivityNotFoundException ex) {
            System.out.println("No Activity Found For Call in get person adapter\n");
        }
    }

    private void sendEmail() {
     /*   Log.i("Send email", "");
        String[] TO = {""};
        String[] CC = {""};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Your subject");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message goes here");

        try {
            mActivity.startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            //mActivity.finish();
            Log.i("Finished sending email", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(mActivity, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }*/
        /*final Intent emailLauncher = new Intent(Intent.ACTION_SEND_MULTIPLE);
        emailLauncher.setType("message/rfc822");
        emailLauncher.putExtra(Intent.EXTRA_EMAIL, "abc@gmail.com,cv@gmail.com");
        emailLauncher.putExtra(Intent.EXTRA_CC, "cv@gmail.com");
        emailLauncher.putExtra(Intent.EXTRA_SUBJECT, "check this subject line");
        emailLauncher.putExtra(Intent.EXTRA_TEXT, "hey check this message body!");*/

        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", "abc@mail.com", null));
        emailIntent.putExtra(Intent.EXTRA_EMAIL, "abc@gmail.com");
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Body");
        mActivity.startActivity(Intent.createChooser(emailIntent, "Send Email..."));
        /*try {
            mActivity.startActivity(emailLauncher);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }*/
    }

    private void sendSMSMessage(String con, String msg) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(con, null, "hi..." + msg, null, null);
            CustomToast.customToast(mActivity, "SMS sent.");
        } catch (Exception e) {
            CustomToast.customToast(mActivity, "SMS failed, please try again.");
            e.printStackTrace();
        }
    }
}
