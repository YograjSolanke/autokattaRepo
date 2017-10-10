package autokatta.com.adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.VideoView;

import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;

/**
 * Created by ak-004 on 10/10/17.
 */

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.MyVideoHolder> {
    private Activity mActivity;
    private Context mContext;
    List<String> videosList = new ArrayList<>();
    private MyVideoHolder view;
    ProgressDialog pDialog;


    static class MyVideoHolder extends RecyclerView.ViewHolder {
        CardView mCardView;
        VideoView videoview;

        MyVideoHolder(View itemView) {
            super(itemView);
            mCardView = (CardView) itemView.findViewById(R.id.card_view);
            videoview = (VideoView) itemView.findViewById(R.id.VideoView);

        }
    }

    public VideoAdapter(Activity mActivity, List<String> videosList) {
        try {
            this.mActivity = mActivity;
            this.videosList = videosList;
            this.mContext = mActivity;


            // Create a progressbar
            pDialog = new ProgressDialog(this.mActivity);
            // Set progressbar title
            pDialog.setTitle("Android Video Streaming Tutorial");
            // Set progressbar message
            pDialog.setMessage("Buffering...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            // Show progressbar
            pDialog.show();

        } catch (ClassCastException c) {
            c.printStackTrace();
        }
    }

    @Override
    public MyVideoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_video_adapter, parent, false);
        return new MyVideoHolder(v);
    }

    @Override
    public void onBindViewHolder(final MyVideoHolder holder, int position) {
        final int safePosition = holder.getAdapterPosition();
        view = holder;


        try {
            // Start the MediaController
            MediaController mediacontroller = new MediaController(
                    mActivity);
            mediacontroller.setAnchorView(holder.videoview);
            // Get the URL from String VideoURL
            Uri video = Uri.parse(videosList.get(safePosition));
            holder.videoview.setMediaController(mediacontroller);
            holder.videoview.setVideoURI(video);

        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }

        holder.videoview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.videoview.start();
            }
        });

//       // holder.videoview.requestFocus();
//        holder.videoview.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//            // Close the progress bar and play the video
//            public void onPrepared(MediaPlayer mp) {
//
//                holder.videoview.start();
//            }
//        });

        pDialog.dismiss();

//        /***Card Click Listener***/
//        holder.mCardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });

    }

    public int getCount() {
        return videosList.size();
    }

//

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {

        return position;
    }

    @Override
    public int getItemCount() {
        return videosList.size();
    }


}

