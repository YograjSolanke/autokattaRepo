package autokatta.com.adapter;

import android.app.Activity;
import android.content.Context;
import android.telephony.gsm.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.other.CustomToast;
import autokatta.com.response.GetContactByCompanyResponse;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by ak-005 on 29/3/17.
 */

public class CompanyBasedInvitationAdapter extends BaseAdapter {
    private Activity mContext;
    private List<GetContactByCompanyResponse.Success> mList;
    private List<GetContactByCompanyResponse.Success> mListCopy;
    private ItemFilter mFilter = new ItemFilter();

    public CompanyBasedInvitationAdapter(Activity mContext, List<GetContactByCompanyResponse.Success> mList) {
        this.mContext = mContext;
        this.mList = mList;
        this.mListCopy = mList;//for search filter
    }

    private class ContactListHolder {
        TextView mName;
        TextView mContact;
        ImageView mPro_pic;
        Button mbtnInvite;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ContactListHolder contactListHolder = null;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.companyinvitation_adapter, null);

            contactListHolder = new ContactListHolder();
            contactListHolder.mName = (TextView) convertView.findViewById(R.id.names);
            contactListHolder.mContact = (TextView) convertView.findViewById(R.id.contact);
            contactListHolder.mPro_pic = (ImageView) convertView.findViewById(R.id.pro_pic);
            contactListHolder.mbtnInvite = (Button) convertView.findViewById(R.id.invite);
            convertView.setTag(contactListHolder);
        } else {
            contactListHolder = (ContactListHolder) convertView.getTag();
        }

        GetContactByCompanyResponse.Success success = mList.get(position);
        contactListHolder.mName.setText(success.getUsername());
        contactListHolder.mContact.setText(success.getContact());

        final ContactListHolder ContactListHolder = contactListHolder;
        contactListHolder.mbtnInvite.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                sendSMSMessage(ContactListHolder.mContact.toString(), ContactListHolder.mName.toString());
            }
        });

        //Set Profile Photo
        if (success.getProfilePic().equals("") || success.getProfilePic().equals(null) || success.getProfilePic().equals("null"))
            contactListHolder.mPro_pic.setBackgroundResource(R.drawable.profile);

        else {
            Glide.with(mContext)
                    .load(mContext.getString(R.string.base_image_url) + success.getProfilePic())
                    .bitmapTransform(new CropCircleTransformation(mContext)) //To display image in Circular form.
                    .diskCacheStrategy(DiskCacheStrategy.ALL) //For caching diff versions of image.
                    .override(110, 100)
                    .into(contactListHolder.mPro_pic);

        }
        return convertView;
    }

    public Filter getFilter() {
        return mFilter;
    }

    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            String filterString = constraint.toString().toLowerCase();
            FilterResults results = new FilterResults();
            final List<GetContactByCompanyResponse.Success> list = mListCopy;
            int count = list.size();
            final ArrayList<GetContactByCompanyResponse.Success> nlist = new ArrayList<>(count);
            GetContactByCompanyResponse.Success filterableString;
            for (int i = 0; i < count; i++) {
                filterableString = list.get(i);
                if (filterString.equals("")) {
                    nlist.add(filterableString);
                } else if (filterableString.getUsername().toLowerCase().contains(filterString)) {
                    nlist.add(filterableString);
                }
            }

            results.values = nlist;
            results.count = nlist.size();
            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mList = (ArrayList<GetContactByCompanyResponse.Success>) results.values;
            notifyDataSetChanged();
        }

    }

    private void sendSMSMessage(String con, String msg) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(con, null, "hi..." + msg, null, null);
        } catch (Exception e) {
            CustomToast.customToast(mContext, "SMS failed, please try again.");
            e.printStackTrace();
        }
    }


    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return mList.get(position).hashCode();
    }


}
