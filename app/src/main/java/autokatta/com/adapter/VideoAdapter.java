package autokatta.com.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.allattentionhere.autoplayvideos.AAH_CustomViewHolder;
import com.allattentionhere.autoplayvideos.AAH_VideosAdapter;

import java.util.List;

import autokatta.com.R;

/**
 * Created by ak-004 on 10/10/17.
 */

public class VideoAdapter extends AAH_VideosAdapter {
    private List<String> list;
    //Picasso picasso;

    public class MyViewHolder extends AAH_CustomViewHolder {
        final TextView tv;
        final ImageView img_vol;
        Button img_playback;
        boolean isMuted; //to mute/un-mute video (optional)

        public MyViewHolder(View x) {
            super(x);
            tv = (TextView) x.findViewById(R.id.tv);
            img_vol = (ImageView) x.findViewById(R.id.img_vol);
            img_playback = (Button) x.findViewById(R.id.img_playback);
        }
    }

    public VideoAdapter(List<String> list_urls) {
        this.list = list_urls;
        //this.picasso = p;
    }

    @Override
    public AAH_CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_card, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AAH_CustomViewHolder holder, int position) {
        final MyViewHolder myViewHolder = (MyViewHolder) holder;
        myViewHolder.tv.setText("Rajashree");

        //todo
        //holder.setImageUrl(list.get(position).getImage_url());
        myViewHolder.setVideoUrl(list.get(position));
        //load image/thumbnail into imageview
        /*if (list.get(position).getImage_url() != null && !list.get(position).getImage_url().isEmpty())
            picasso.load(holder.getImageUrl()).config(Bitmap.Config.RGB_565).into(holder.getAAH_ImageView());*/

        myViewHolder.img_playback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myViewHolder.isPlaying()) {
                    myViewHolder.pauseVideo();
                    myViewHolder.setPaused(true);
                    myViewHolder.img_playback.setText("Play");
                    myViewHolder.img_playback.setVisibility(View.VISIBLE);
                } else {
                    myViewHolder.playVideo();
                    myViewHolder.setPaused(false);
                    myViewHolder.img_playback.setText("Pause");
                    myViewHolder.img_playback.setVisibility(View.VISIBLE);
                }
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

