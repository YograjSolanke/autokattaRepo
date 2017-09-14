package autokatta.com.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import autokatta.com.R;
import autokatta.com.other.FullImageActivity;
import autokatta.com.response.BroadcastMessageResponse;


/**
 * Created by ak-003 on 16/5/16.
 */
public class ChatAdapter extends BaseAdapter implements View.OnClickListener {


    private Activity activity;
    private LayoutInflater inflater = null;
    private String contactnumber;
    DateFormat date, time;
    private List<String> hashMapDate;
    private List<String> hashMapposition;


    private List<BroadcastMessageResponse.Success> locallist = new ArrayList<>();

    public ChatAdapter(Activity activity, String contactnumber, List<BroadcastMessageResponse.Success> locallist) {

        date = new SimpleDateFormat(" MMM dd yyyy", Locale.getDefault());
        time = new SimpleDateFormat(" hh:mm aa", Locale.getDefault());


        this.activity = activity;
        this.contactnumber = contactnumber;
        this.locallist = locallist;
        hashMapDate = new ArrayList<>();
        hashMapposition = new ArrayList<>();

        for (int i = 0; i < this.locallist.size(); i++) {
            if (!hashMapDate.contains(date.format(this.locallist.get(i).getNewDate()))) {
                hashMapDate.add(date.format(this.locallist.get(i).getNewDate()));
                hashMapposition.add(String.valueOf(i));
            }
        }

        /***********  Layout inflator to call external xml layout () ***********/
        inflater = (LayoutInflater) this.activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return locallist.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return getCount();
    }

    /*********
     * Create a holder Class to contain inflated xml file elements
     *********/
    public static class ViewHolder {

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final BroadcastMessageResponse.Success chatMessageObj = locallist.get(position);
        View row = convertView;

        if (chatMessageObj.getSender().equalsIgnoreCase(contactnumber)) {
            row = inflater.inflate(R.layout.right, parent, false);
        } else {
            row = inflater.inflate(R.layout.left, parent, false);
        }
        TextView chattext = (TextView) row.findViewById(R.id.msgr);
        TextView dateNtime = (TextView) row.findViewById(R.id.dateNtime);
        TextView imagetimetext = (TextView) row.findViewById(R.id.imagetime);
        TextView dateTextview = (TextView) row.findViewById(R.id.dateTextview);
        ImageView image = (ImageView) row.findViewById(R.id.image);
        LinearLayout imagelayout = (LinearLayout) row.findViewById(R.id.imagelayout);
        chattext.setText(chatMessageObj.getMessage());
        dateNtime.setText(time.format(chatMessageObj.getNewDate()));
        if (chatMessageObj.getMessage().equals("") &&
                (!chatMessageObj.getImage().equals("") || !chatMessageObj.getImage().equals("null") || !chatMessageObj.getImage().equals(""))) {
            chattext.setVisibility(View.GONE);
            dateNtime.setVisibility(View.GONE);
        }
        //dateTextview.setText(date.format(hashMapDate.get(position)));


        if (hashMapDate.contains(date.format(chatMessageObj.getNewDate()))) {
            if (hashMapposition.contains(String.valueOf(position))) {
                dateTextview.setText(date.format(chatMessageObj.getNewDate()));
            } else {
                dateTextview.setVisibility(View.GONE);
            }
        } else {
            dateTextview.setVisibility(View.GONE);
        }

        //Glide code for image downloading
        if (chatMessageObj.getImage()==null || chatMessageObj.getImage().isEmpty()) {
            imagelayout.setVisibility(View.GONE);
        } else {
            Glide.with(activity)
                    .load(activity.getString(R.string.base_image_url) + chatMessageObj.getImage())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.logo)
                    .into(image);
        }


        imagetimetext.setText(time.format(chatMessageObj.getNewDate()));

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String image;
                if (chatMessageObj.getImage().equals(""))
                    image = activity.getString(R.string.base_image_url) + "logo48x48.png";
                else
                    image = activity.getString(R.string.base_image_url) + chatMessageObj.getImage();
                Bundle b = new Bundle();
                b.putString("image", image);
                Intent intent = new Intent(activity, FullImageActivity.class);
                intent.putExtras(b);
                activity.startActivity(intent);
            }
        });

        return row;
    }

    @Override
    public void onClick(View v) {

    }


}
