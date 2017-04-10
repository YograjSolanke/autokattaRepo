package autokatta.com.adapter;

import android.app.Activity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.response.BroadcastReceivedResponse;

/**
 * Created by ak-005 on 10/4/17.
 */

    public class BussinessMsgSendersAdapter extends RecyclerView.Adapter<BussinessMsgSendersAdapter.MyViewHolder>
    {

        private Activity mActivity;
        private List<BroadcastReceivedResponse.Success> mItemList = new ArrayList<>();

        String msender,msendername;
        class MyViewHolder extends RecyclerView.ViewHolder{
            TextView msgFrom,msgFromCnt;
            CardView cardView;


            public MyViewHolder(View itemView) {
                super(itemView);
                msgFrom = (TextView)itemView.findViewById(R.id.msgFrom);
                msgFromCnt = (TextView)itemView.findViewById(R.id.msgFromCnt);
                cardView= (CardView) itemView.findViewById(R.id.msgsendercv);

      /*  cardView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ChatActivity object = new ChatActivity();
                Bundle b = new Bundle();
                b.putString("sender", msender);
                b.putString("sendername", msendername);
                b.putString("product_id",product_id);
                b.putString("service_id",service_id);
                b.putString("vehicle_id",vehicle_id);

                object.setArguments(b);
                FragmentManager fragmentManager = fragmentActivity.getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.bussines_chat_container, object);
                fragmentTransaction.addToBackStack("chatactivity");
                fragmentTransaction.commit();

            }
        });*/

            }
        }
        public BussinessMsgSendersAdapter(Activity mActivity, List<BroadcastReceivedResponse.Success> mItemList) {
            this.mActivity = mActivity;
            this.mItemList = mItemList;
        }

        @Override
        public BussinessMsgSendersAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.bussiness_msg_senders, parent, false);
            MyViewHolder vh = new MyViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(BussinessMsgSendersAdapter.MyViewHolder holder, int position) {

            msender=mItemList.get(position).getSender();
            msendername=mItemList.get(position).getSendername();
            holder.msgFromCnt.setText(msender);
            holder.msgFrom.setText(msendername);


        }

        @Override
        public int getItemCount() {
            return mItemList.size();
        }
    }

