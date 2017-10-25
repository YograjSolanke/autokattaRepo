package autokatta.com.adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.VideoView;

import java.util.List;

import autokatta.com.R;
import autokatta.com.view.SingleVideoActivity;

/**
 * Created by ak-004 on 10/10/17.
 */

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.MyViewHolder> {
    private List<String> list;
    Activity activity;
    //Picasso picasso;
    private MyViewHolder view;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        VideoView videoView;
        CardView cardView;

        public MyViewHolder(View x) {
            super(x);
            videoView = (VideoView) x.findViewById(R.id.VideoView);
            cardView = (CardView) x.findViewById(R.id.card_view);

        }
    }

    public VideoAdapter(Activity applicationContext, List<String> list_urls) {
        this.list = list_urls;
        activity = applicationContext;
        //this.picasso = p;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_card, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final int safePosition = holder.getAdapterPosition();
        view = holder;

        try {
            // Start the MediaController
            MediaController mediacontroller = new MediaController(
                    activity);
            mediacontroller.setAnchorView(view.videoView);
            // Get the URL from String VideoURL
            Uri video = Uri.parse(list.get(position));

            Bitmap thumb = ThumbnailUtils.createVideoThumbnail(list.get(position),
                    MediaStore.Images.Thumbnails.MINI_KIND);

            BitmapDrawable bitmapDrawable = new BitmapDrawable(thumb);
            view.videoView.setBackground(bitmapDrawable);


        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }


        view.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityOptions options = ActivityOptions.makeCustomAnimation(activity, R.anim.ok_left_to_right, R.anim.ok_right_to_left);
                Bundle b = new Bundle();
                b.putString("url", list.get(position));
                Intent intentnewvehicle = new Intent(activity, SingleVideoActivity.class);
                intentnewvehicle.putExtras(b);
                //intentnewvehicle.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                activity.startActivity(intentnewvehicle, options.toBundle());
            }
        });



    }
//
//    @Override
//    public void onBindViewHolder(AAH_CustomViewHolder holder, final int position) {
//        final MyViewHolder myViewHolder = (MyViewHolder) holder;
//        myViewHolder.tv.setText("Rajashree");
//
//        //todo
//        holder.setImageUrl(activity.getString(R.string.base_image_url) + "logo48x48.png");
//        // myViewHolder.setVideoUrl(list.get(position));
//        // myViewHolder.playVideo();
////        myViewHolder.progressBar.setVisibility(View.VISIBLE);
////        myViewHolder.videoStarted();
////        myViewHolder.progressBar.setVisibility(View.GONE);
//
//        //load image/thumbnail into imageview
////        if (list.get(position).getImage_url() != null && !list.get(position).getImage_url().isEmpty())
//        // picasso.load(holder.getImageUrl()).config(Bitmap.Config.RGB_565).into(holder.getAAH_ImageView());
//
//
//        String dppath = activity.getString(R.string.base_image_url) + "logo48x48.png";
//        Glide.with(activity)
//                .load(dppath)
//                .bitmapTransform(new CropSquareTransformation(activity)) //To display image in Circular form.
//                .diskCacheStrategy(DiskCacheStrategy.ALL) //For caching diff versions of image.
//                //.placeholder(R.drawable.logo) //To show image before loading an original image.
//                //.error(R.drawable.blocked) //To show error image if problem in loading.
//                .into(holder.getAAH_ImageView());
//
//        myViewHolder.card_view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ActivityOptions options = ActivityOptions.makeCustomAnimation(activity, R.anim.ok_left_to_right, R.anim.ok_right_to_left);
//                Bundle b = new Bundle();
//                b.putString("url", list.get(position));
//                Intent intentnewvehicle = new Intent(activity, SingleVideoActivity.class);
//                intentnewvehicle.putExtras(b);
//                intentnewvehicle.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                activity.startActivity(intentnewvehicle, options.toBundle());
//
//            }
//        });
//
//
//        myViewHolder.img_playback.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//                ActivityOptions options = ActivityOptions.makeCustomAnimation(activity, R.anim.ok_left_to_right, R.anim.ok_right_to_left);
//                Bundle b = new Bundle();
//                b.putString("url", list.get(position));
//                Intent intentnewvehicle = new Intent(activity, SingleVideoActivity.class);
//                intentnewvehicle.putExtras(b);
//                intentnewvehicle.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                activity.startActivity(intentnewvehicle, options.toBundle());
////                if (myViewHolder.isPlaying()) {
////                    myViewHolder.pauseVideo();
////                    myViewHolder.setPaused(true);
////                    myViewHolder.img_playback.setText("Play");
////                    myViewHolder.img_playback.setVisibility(View.VISIBLE);
////                } else {
////                    myViewHolder.playVideo();
////                    myViewHolder.setPaused(false);
////                    myViewHolder.img_playback.setText("Pause");
////                    myViewHolder.img_playback.setVisibility(View.VISIBLE);
////                }
//            }
//        });
//
//
//    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

}

