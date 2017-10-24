package autokatta.com.adapter;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.allattentionhere.autoplayvideos.AAH_CustomViewHolder;
import com.allattentionhere.autoplayvideos.AAH_VideosAdapter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import autokatta.com.R;
import autokatta.com.view.SingleVideoActivity;
import jp.wasabeef.glide.transformations.CropSquareTransformation;

/**
 * Created by ak-004 on 10/10/17.
 */

public class VideoAdapter extends AAH_VideosAdapter {
    private List<String> list;
    Context activity;
    //Picasso picasso;

    public class MyViewHolder extends AAH_CustomViewHolder {
        final TextView tv;
        final ImageView img_vol;
        Button img_playback;
        //   ProgressBar progressBar = null;
        CardView card_view;
        boolean isMuted; //to mute/un-mute video (optional)

        public MyViewHolder(View x) {
            super(x);
            tv = (TextView) x.findViewById(R.id.tv);
            img_vol = (ImageView) x.findViewById(R.id.img_vol);
            img_playback = (Button) x.findViewById(R.id.img_playback);
            //  progressBar = (ProgressBar) x.findViewById(R.id.progressbar);
            card_view = (CardView) x.findViewById(R.id.card_view);
        }
    }

    public VideoAdapter(Context applicationContext, List<String> list_urls) {
        this.list = list_urls;
        activity = applicationContext;
        //this.picasso = p;
    }

    @Override
    public AAH_CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_card, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AAH_CustomViewHolder holder, final int position) {
        final MyViewHolder myViewHolder = (MyViewHolder) holder;
        myViewHolder.tv.setText("Rajashree");

        //todo
        holder.setImageUrl(activity.getString(R.string.base_image_url) + "logo48x48.png");
        // myViewHolder.setVideoUrl(list.get(position));
        // myViewHolder.playVideo();
//        myViewHolder.progressBar.setVisibility(View.VISIBLE);
//        myViewHolder.videoStarted();
//        myViewHolder.progressBar.setVisibility(View.GONE);

        //load image/thumbnail into imageview
//        if (list.get(position).getImage_url() != null && !list.get(position).getImage_url().isEmpty())
        // picasso.load(holder.getImageUrl()).config(Bitmap.Config.RGB_565).into(holder.getAAH_ImageView());


        String dppath = activity.getString(R.string.base_image_url) + "logo48x48.png";
        Glide.with(activity)
                .load(dppath)
                .bitmapTransform(new CropSquareTransformation(activity)) //To display image in Circular form.
                .diskCacheStrategy(DiskCacheStrategy.ALL) //For caching diff versions of image.
                //.placeholder(R.drawable.logo) //To show image before loading an original image.
                //.error(R.drawable.blocked) //To show error image if problem in loading.
                .into(holder.getAAH_ImageView());

        myViewHolder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityOptions options = ActivityOptions.makeCustomAnimation(activity, R.anim.ok_left_to_right, R.anim.ok_right_to_left);
                Bundle b = new Bundle();
                b.putString("url", list.get(position));
                Intent intentnewvehicle = new Intent(activity, SingleVideoActivity.class);
                intentnewvehicle.putExtras(b);
                intentnewvehicle.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                activity.startActivity(intentnewvehicle, options.toBundle());

            }
        });


        myViewHolder.img_playback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                ActivityOptions options = ActivityOptions.makeCustomAnimation(activity, R.anim.ok_left_to_right, R.anim.ok_right_to_left);
                Bundle b = new Bundle();
                b.putString("url", list.get(position));
                Intent intentnewvehicle = new Intent(activity, SingleVideoActivity.class);
                intentnewvehicle.putExtras(b);
                intentnewvehicle.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                activity.startActivity(intentnewvehicle, options.toBundle());
//                if (myViewHolder.isPlaying()) {
//                    myViewHolder.pauseVideo();
//                    myViewHolder.setPaused(true);
//                    myViewHolder.img_playback.setText("Play");
//                    myViewHolder.img_playback.setVisibility(View.VISIBLE);
//                } else {
//                    myViewHolder.playVideo();
//                    myViewHolder.setPaused(false);
//                    myViewHolder.img_playback.setText("Pause");
//                    myViewHolder.img_playback.setVisibility(View.VISIBLE);
//                }
            }
        });


        myViewHolder.getAah_vi().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myViewHolder.isMuted) {
                    myViewHolder.unmuteVideo();
                    myViewHolder.img_vol.setImageResource(R.mipmap.manual_enquiry);
                } else {
                    myViewHolder.muteVideo();
                    myViewHolder.img_vol.setImageResource(R.drawable.store);
                }
                myViewHolder.isMuted = !myViewHolder.isMuted;
            }
        });

        // myViewHolder.setLooping(true); //optional - true by default
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

}

