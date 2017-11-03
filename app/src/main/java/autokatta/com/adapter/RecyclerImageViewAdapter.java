package autokatta.com.adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import autokatta.com.other.FullImageActivity;

/**
 * Created by ak-004 on 3/11/17.
 */

public class RecyclerImageViewAdapter extends RecyclerView.Adapter<RecyclerImageViewAdapter.MyViewHolder> {

    Activity activity;
    List<String> list_urls = new ArrayList<>();
    int coount = 0;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        ImageView imageView;
        TextView textView;

        public MyViewHolder(View x) {
            super(x);
            cardView = (CardView) x.findViewById(R.id.card_view);
            imageView = (ImageView) x.findViewById(R.id.imageView);
            textView = (TextView) x.findViewById(R.id.textView);
        }
    }

    public RecyclerImageViewAdapter(Activity applicationContext, List<String> list_urls) {
        this.list_urls = list_urls;
        activity = applicationContext;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.image_card_layout, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        holder.setIsRecyclable(false);
        holder.textView.setText("Iamge " + coount++);
        try {

            Glide.with(activity)
                    .load(list_urls.get(holder.getAdapterPosition()))
                    //  .load(bitmap)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.imageView);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityOptions options = ActivityOptions.makeCustomAnimation(activity, R.anim.ok_left_to_right, R.anim.ok_right_to_left);
                Bundle b = new Bundle();
                b.putString("image", list_urls.get(holder.getAdapterPosition()));
                Intent intentnewvehicle = new Intent(activity, FullImageActivity.class);
                intentnewvehicle.putExtras(b);
                //intentnewvehicle.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                activity.startActivity(intentnewvehicle, options.toBundle());
            }
        });


    }

    @Override
    public int getItemCount() {
        return list_urls.size();
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }


}


