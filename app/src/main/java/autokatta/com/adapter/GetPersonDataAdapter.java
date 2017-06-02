package autokatta.com.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.telephony.gsm.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.Registration.InviteFriends;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import autokatta.com.response.GetPersonDataResponse;
import retrofit2.Response;

/**
 * Created by ak-001 on 11/5/17.
 */

public class GetPersonDataAdapter extends RecyclerView.Adapter<GetPersonDataAdapter.PersonData> implements RequestNotifier {

    Activity mActivity;
    List<GetPersonDataResponse.Success> list = new ArrayList<>();
    private PersonData personDataHolder;
    private List<String> contactList = new ArrayList<>();


    public GetPersonDataAdapter(Activity mActivity, List<GetPersonDataResponse.Success> list) {
        this.mActivity = mActivity;
        this.list = list;

        for (int i = 0; i < list.size(); i++) {
            contactList.add(list.get(i).getContactNo());
            //checkUser(contactList.get(i));
            Log.i("List", "-" + contactList.get(i));
        }


    }


    static class PersonData extends RecyclerView.ViewHolder {

        TextView mPersonName, mContact, mAddress, mFollowUpDate, mDiscussion, mInvite;
        ImageView mProfilePic, mCallImg, mMailImage;

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
            mInvite = (TextView) itemView.findViewById(R.id.txtInvite);
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
    public void onBindViewHolder(final PersonData holder, int position) {
        personDataHolder = holder;
        holder.mPersonName.setText(list.get(position).getUsername());
        holder.mContact.setText(list.get(position).getContactNo());
        holder.mAddress.setText(list.get(position).getCity());
        holder.mFollowUpDate.setText(list.get(position).getNextFollowupDate());


        for (int i = 0; i < contactList.size(); i++) {
            checkUser(contactList.get(i));

            Log.i("list", "===" + contactList.get(i));
        }
        //checkUser(list.get(position).getContactNo());

        if (list.get(position).getProfilePic().equals("") || list.get(position).getProfilePic().equals("null")
                || list.get(position).getProfilePic().equals(null)) {
            holder.mProfilePic.setBackgroundResource(R.drawable.hdlogo);
        } else {
            String used_pic = "http://autokatta.com/mobile/profile_profile_pics/" + list.get(position).getProfilePic();
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
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    //Calling Functionality
    private void call(String rcontact) {
        Intent in = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + rcontact));
        try {
            mActivity.startActivity(in);
        } catch (android.content.ActivityNotFoundException ex) {
            System.out.println("No Activity Found For Call in Car Details Fragment\n");
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
    }

    private void sendSMSMessage(String con, String msg) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(con, null, "hi..." + msg, null, null);
            Toast.makeText(mActivity, "SMS sent.", Toast.LENGTH_LONG).show();
            Intent i = new Intent(mActivity, InviteFriends.class);
            mActivity.startActivity(i);
            mActivity.finish();
        } catch (Exception e) {
            Toast.makeText(mActivity, "SMS failed, please try again.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    private void checkUser(String contact) {
        ApiCall mApiCall = new ApiCall(mActivity, this);
        mApiCall.registrationContactValidation(contact);
    }

    @Override
    public void notifySuccess(Response<?> response) {

    }

    @Override
    public void notifyError(Throwable error) {

    }

    @Override
    public void notifyString(String str) {
        if (str != null) {
            if (str.equalsIgnoreCase("Success")) {
                //CustomToast.customToast(mActivity,"Autokatta user");
                personDataHolder.mInvite.setVisibility(View.GONE);
                personDataHolder.mContact.setTypeface(personDataHolder.mContact.getTypeface(), Typeface.BOLD);
            } else {
                //CustomToast.customToast(mActivity,"No Autokatta user");
                personDataHolder.mInvite.setVisibility(View.VISIBLE);

            }
        } else
            CustomToast.customToast(mActivity, mActivity.getString(R.string.no_response));
    }
}
