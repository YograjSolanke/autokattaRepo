package autokatta.com.adapter;

import android.app.Activity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import autokatta.com.R;

/**
 * Created by ak-003 on 28/3/17.
 */

public class AutokattaContactAdapter extends RecyclerView.Adapter<AutokattaContactAdapter.YoHolder> {

    private Activity mActivity;


    static class YoHolder extends RecyclerView.ViewHolder {

        CardView mCardView;
        ImageView imgProfile, imgCall;
        TextView mTextName, mTextNumber, mTextStatus;
        Button btnFollow, btnUnfollow;

        public YoHolder(View itemView) {
            super(itemView);
            mCardView = (CardView) itemView.findViewById(R.id.adapter_autokatta_contactCard_view);
            imgProfile = (ImageView) itemView.findViewById(R.id.profileImg);
            imgCall = (ImageView) itemView.findViewById(R.id.callImg);
            mTextName = (TextView) itemView.findViewById(R.id.txtname);
            mTextNumber = (TextView) itemView.findViewById(R.id.txtnumber);
            mTextStatus = (TextView) itemView.findViewById(R.id.txtstatus);
            btnFollow = (Button) itemView.findViewById(R.id.btnfollow);
            btnUnfollow = (Button) itemView.findViewById(R.id.btnunfollow);

        }
    }

    public AutokattaContactAdapter(Activity mActivity) {
        try {
            this.mActivity = mActivity;
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }

    @Override
    public AutokattaContactAdapter.YoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_autokatta_contact, parent, false);
        YoHolder yoHolder = new YoHolder(v);
        return yoHolder;
    }

    @Override
    public void onBindViewHolder(AutokattaContactAdapter.YoHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
