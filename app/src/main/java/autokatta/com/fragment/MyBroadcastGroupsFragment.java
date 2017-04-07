package autokatta.com.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import autokatta.com.response.MyBroadcastGroupsResponse;
import autokatta.com.response.MyBroadcastGroupsResponse.Success;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ak-005 on 5/4/17.
 */

public class MyBroadcastGroupsFragment extends Fragment implements View.OnClickListener, RequestNotifier, SwipeRefreshLayout.OnRefreshListener  {
    View mMyBroadcast;
    RecyclerView mRecyclerView;
    SwipeRefreshLayout mSwipeRefreshLayout;
    ApiCall apiCall;
    List<Success> broadcastGroupsResponseList = new ArrayList<>();
    MyBroadcastGroupsAdapter adapter;
    FragmentActivity ctx;
    Button btnSendMessage;
    ImageView imgDeleteGroup;
    String finalgrpids;
    ArrayList<String>incominggrpids = new ArrayList<>();



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mMyBroadcast = inflater.inflate(R.layout.fragment_mybroadcast_groups, container, false);

    mSwipeRefreshLayout =(SwipeRefreshLayout)mMyBroadcast.findViewById(R.id.swipeRefreshLayoutBGroup);

    mRecyclerView =(RecyclerView) mMyBroadcast.findViewById(R.id.recyclerBGroup);

     btnSendMessage = (Button) mMyBroadcast.findViewById(R.id.btnSendMsg);
     imgDeleteGroup = (ImageView) mMyBroadcast.findViewById(R.id.deletegroup);
    FloatingActionButton createGroup = (FloatingActionButton) mMyBroadcast.findViewById(R.id.fabCreateBroadcastGroup);


        btnSendMessage.setOnClickListener(this);
        imgDeleteGroup.setOnClickListener(this);
        createGroup.setOnClickListener(this);

    apiCall =new  ApiCall(getActivity(),this);

        mRecyclerView.setHasFixedSize(true);

    LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mLinearLayoutManager.setReverseLayout(true);
        mLinearLayoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
    android.R.color.holo_green_light,
    android.R.color.holo_orange_light,
    android.R.color.holo_red_light);
        mSwipeRefreshLayout.post(new

    Runnable() {
        @Override
        public void run () {
            mSwipeRefreshLayout.setRefreshing(true);
            apiCall.MyBroadcastGroups(getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", ""));
        }
    });
        return mMyBroadcast;
    }

    @Override
    public void onAttach(Context activity) {
        ctx = (FragmentActivity) activity;

        super.onAttach(activity);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnSendMsg:
                sendMessage();
                break;
            case R.id.deletegroup:
                deleteGroups();
                break;
            case R.id.fabCreateBroadcastGroup:
                Bundle b=new Bundle();
                b.putString("calltype","create");
                b.putString("groupname","");
                b.putString("groupmembers","");
                b.putString("group_id","");
                Log.e("hiiiii","->");
                getActivity().finish();
                CreateBroadcastGroupFragment about = new CreateBroadcastGroupFragment();
                about.setArguments(b);
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.broadcast_groups_container, about).commit();

                break;
        }
    }

    private void sendMessage() {
        CustomToast.customToast(getActivity(), "Sendmessage");
    }

    private void deleteGroups() {

        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle("Delete");
        alert.setMessage("Are you sure you want to delete this group?");
        alert.setIconAttribute(android.R.attr.alertDialogIcon);

        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {



                finalgrpids = "";
                incominggrpids.clear();

                incominggrpids = adapter.checkboxselect();
                System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!Checked grp id====" + incominggrpids);

                for (int i = 0; i < incominggrpids.size(); i++) {
                    if (!incominggrpids.get(i).equals("0")) {
                        if (finalgrpids.equals(""))
                            finalgrpids = incominggrpids.get(i);
                        else
                            finalgrpids = finalgrpids + "," + incominggrpids.get(i);

                    }

                }

                System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!going grp ids====" + finalgrpids);

                apiCall.deleteBroadcastgroup("delete",finalgrpids);
               /* try {
                    //deletegroup(finalgrpids);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
*/
                dialog.dismiss();

            }
        });

        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }

        });
        alert.create();
        alert.show();

    }

    @Override
    public void notifySuccess(Response<?> response) {

        if (response != null) {

            if (response.isSuccessful()) {

                broadcastGroupsResponseList.clear();
                MyBroadcastGroupsResponse myBroadcastGroupsResponse = (MyBroadcastGroupsResponse) response.body();

                if (!myBroadcastGroupsResponse.getSuccess().isEmpty()) {

                    for (MyBroadcastGroupsResponse.Success success : myBroadcastGroupsResponse.getSuccess()) {

                        success.setGroupId(success.getGroupId());
                        success.setGroupTitle(success.getGroupTitle());
                        success.setGroupOwner(success.getGroupOwner());
                        success.setGroupMemberContacts(success.getGroupMemberContacts());
                        success.setGroupStatus(success.getGroupStatus());
                        success.setGrpMemberCount(success.getGrpMemberCount());
                        success.setGrpCreatedDate(success.getGrpCreatedDate());

                        broadcastGroupsResponseList.add(success);
                    }
                    mSwipeRefreshLayout.setRefreshing(false);
                    adapter = new MyBroadcastGroupsAdapter(getActivity(), ctx, broadcastGroupsResponseList);
                  mRecyclerView.setAdapter(adapter);

                    Log.i("size broadcast group", String.valueOf(broadcastGroupsResponseList.size()));
                } else
                    CustomToast.customToast(getActivity(), this.getString(R.string.no_response));

            } else
                CustomToast.customToast(getActivity(), this.getString(R.string._404));

        } else
            CustomToast.customToast(getActivity(), this.getString(R.string.no_response));
    }

    @Override
    public void notifyError(Throwable error) {

    }

    @Override
    public void notifyString(String str) {
        if (str != null) {
            CustomToast.customToast(getActivity(), "Group Deleted Successfully");
            MyBroadcastGroupsFragment about = new MyBroadcastGroupsFragment();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.broadcast_groups_container, about).commit();


        }
    }

    @Override
    public void onRefresh() {

    }

     /*Adapter for broadcast group*/

    public class MyBroadcastGroupsAdapter extends RecyclerView.Adapter<MyBroadcastGroupsAdapter.MyViewHolder> {
        //  private LayoutInflater inflater = null;
        FragmentActivity ctx;
        String contact;
        Activity activity;
        ArrayList<String> grpidslist = new ArrayList<>();
        ArrayList<Boolean> positionArray = new ArrayList<>();

        private List<MyBroadcastGroupsResponse.Success> mItemList = new ArrayList<>();

        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView membercount;
            CheckedTextView group_title;
            ImageView editgroup;

            public MyViewHolder(View itemView) {
                super(itemView);
                group_title = (CheckedTextView) itemView.findViewById(R.id.txtname);
                membercount = (TextView) itemView.findViewById(R.id.txtcount);
                editgroup = (ImageView) itemView.findViewById(R.id.editgroup);

            }
        }


        public MyBroadcastGroupsAdapter(Activity activity, FragmentActivity fragmentActivity, List<MyBroadcastGroupsResponse.Success> broadcastlist) {
            ctx = fragmentActivity;
            this.mItemList = broadcastlist;
            System.out.println("rutu------------------itemlist size"+mItemList.size());
            this.activity = activity;
            contact = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", "");

            for (int i = 0; i < mItemList.size(); i++) {
                grpidslist.add("0");
                positionArray.add(false);
            }

        }


        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.my_broadcast_group_adapter, parent, false);
            MyViewHolder vh = new MyViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {
            holder.group_title.setText(mItemList.get(position).getGroupTitle());
            holder.membercount.setText("Members(" + mItemList.get(position).getGrpMemberCount() + ")");
            holder.itemView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder.group_title.setChecked(true);

                }
            });
            holder.editgroup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Bundle b=new Bundle();
                    b.putString("calltype","update");
                    b.putString("groupname",mItemList.get(position).getGroupTitle());
                    b.putString("groupmembers",mItemList.get(position).getGroupMemberContacts());
                    b.putString("group_id",mItemList.get(position).getGroupId());
                    CreateBroadcastGroupFragment broadcastGroup = new CreateBroadcastGroupFragment();
                    broadcastGroup.setArguments(b);
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.broadcast_groups_container, broadcastGroup);
                    fragmentTransaction.addToBackStack("rrrrr");
                    fragmentTransaction.commit();
                }
            });

            holder.group_title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (holder.group_title.isChecked()) {
                        holder.group_title.setChecked(false);
                        grpidslist.set(position, "0");
                        positionArray.set(position, false);
                    } else {
                        holder.group_title.setChecked(true);
                        positionArray.set(position, true);
                        grpidslist.set(position, mItemList.get(position).getGroupId());

                    }

                    if (positionArray.contains(true)) {
                        btnSendMessage.setEnabled(true);
                        imgDeleteGroup.setVisibility(View.VISIBLE);
                    }
                    else {
                        btnSendMessage.setEnabled(false);
                        imgDeleteGroup.setVisibility(View.GONE);
                    }


                }


            });
        }

        @Override
        public int getItemCount() {
            return mItemList.size();
        }

        @Override
        public int getItemViewType(int position) {

            return position;
        }
        private ArrayList checkboxselect() {
            // TODO Auto-generated method stub
            return grpidslist;
        }
    }
}
