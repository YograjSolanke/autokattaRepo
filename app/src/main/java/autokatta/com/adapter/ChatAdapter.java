package autokatta.com.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
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

import autokatta.com.R;
import autokatta.com.response.BroadcastMessageResponse;


/**
 * Created by ak-003 on 16/5/16.
 */
public class ChatAdapter extends BaseAdapter implements View.OnClickListener {


    private Activity activity;
    private static LayoutInflater inflater = null;
    private TextView chattext, dateNtime, dateTextview, imagetimetext;
    private ImageView image;
    String contactnumber;
    LinearLayout imagelayout;
    DateFormat date, time;
    ArrayList<String> hashMapDate;
    ArrayList<String> hashMapposition;


    ArrayList<BroadcastMessageResponse.Success> locallist = new ArrayList<>();

    //new constuctr
    @SuppressLint("SimpleDateFormat")
    public ChatAdapter(Activity activity, String contactnumber, ArrayList<BroadcastMessageResponse.Success> locallist) {

        date = new SimpleDateFormat(" MMM dd yyyy");
        time = new SimpleDateFormat(" hh:mm aa");


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
        chattext = (TextView) row.findViewById(R.id.msgr);
        dateNtime = (TextView) row.findViewById(R.id.dateNtime);
        imagetimetext = (TextView) row.findViewById(R.id.imagetime);
        dateTextview = (TextView) row.findViewById(R.id.dateTextview);
        image = (ImageView) row.findViewById(R.id.image);
        imagelayout = (LinearLayout) row.findViewById(R.id.imagelayout);
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


//        if(chatMessageObj.image.equals("")||chatMessageObj.image.equals("null")||chatMessageObj.image.equals(""))
//        {
//            //yoHolder.productImage.setBackgroundResource(R.drawable.store);
//            imagelayout.setVisibility(View.GONE);
//        }
//        else
//        {
//            try {
//                Picasso.with(activity)
//                        .load("http://autokatta.com/mobile/uploaded_broadcast_images/"+chatMessageObj.image)
//                        .into(image);
//
//            } catch (Exception e) {
//                System.out.println("Error in uploading images");
//            }
//        }


        //Glide code for image downloading
        if (chatMessageObj.getImage().equals("") || chatMessageObj.getImage().equals("null") || chatMessageObj.getImage().equals("")) {
            //yoHolder.productImage.setBackgroundResource(R.drawable.store);
            imagelayout.setVisibility(View.GONE);
        } else {
            Glide.with(activity)
                    .load("http://autokatta.com/mobile/uploaded_broadcast_images/" + chatMessageObj.getImage())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.logo)
                    //.animate(R.anim.zoomin)
                    //.error(R.drawable.blocked)
                    .into(image);
        }


        imagetimetext.setText(time.format(chatMessageObj.getNewDate()));

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                b.putString("images", chatMessageObj.getImage());
                b.putString("imgpath", "uploaded_broadcast_images/");
//                ImageSlider fragment = new ImageSlider();
//                fragment.setArguments(b);
//
//                FragmentManager fragmentManager = ctx.getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.containerView, fragment);
//                fragmentTransaction.addToBackStack("imageslider");
//                fragmentTransaction.commit();
            }
        });

        return row;
    }

    @Override
    public void onClick(View v) {

    }


}
