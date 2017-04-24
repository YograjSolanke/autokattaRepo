package autokatta.com.fragment;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import autokatta.com.R;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.interfaces.ServiceApi;
import autokatta.com.other.CustomToast;
import autokatta.com.response.MyBroadcastGroupsResponse;
import autokatta.com.response.MyBroadcastGroupsResponse.Success;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ak-005 on 5/4/17.
 */

public class MyBroadcastGroupsFragment extends Fragment implements View.OnClickListener, RequestNotifier, SwipeRefreshLayout.OnRefreshListener {
    View mMyBroadcast;
    RecyclerView mRecyclerView;
    SwipeRefreshLayout mSwipeRefreshLayout;
    ApiCall mApiCall;
    List<Success> broadcastGroupsResponseList = new ArrayList<>();
    MyBroadcastGroupsAdapter adapter;
    FragmentActivity ctx;
    Button btnSendMessage;
    ImageView imgDeleteGroup;
    String finalgrpids;
    String mediaPath = "", mContact;
    Uri selectedImage = null;
    Bitmap  bitmapRotate;
    String fname;
    File file;
    ArrayList<String> incominggrpids = new ArrayList<>();

    Bitmap bitmap;
    String lastWord = "";
    ImageView uploadImage;
    AlertDialog alert;
    TextView addimagetext;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mMyBroadcast = inflater.inflate(R.layout.fragment_mybroadcast_groups, container, false);

        mSwipeRefreshLayout = (SwipeRefreshLayout) mMyBroadcast.findViewById(R.id.swipeRefreshLayoutBGroup);

        mRecyclerView = (RecyclerView) mMyBroadcast.findViewById(R.id.recyclerBGroup);

        btnSendMessage = (Button) mMyBroadcast.findViewById(R.id.btnSendMsg);
        imgDeleteGroup = (ImageView) mMyBroadcast.findViewById(R.id.deletegroup);
        FloatingActionButton createGroup = (FloatingActionButton) mMyBroadcast.findViewById(R.id.fabCreateBroadcastGroup);


        btnSendMessage.setOnClickListener(this);
        imgDeleteGroup.setOnClickListener(this);
        createGroup.setOnClickListener(this);

        mApiCall = new ApiCall(getActivity(), this);

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
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
                mApiCall.MyBroadcastGroups(getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", ""));
            }
        });
        return mMyBroadcast;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnSendMsg:
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
                if ( finalgrpids.equals("")) {
                    CustomToast.customToast(getActivity(), "Please Select Group to Send Message");
                } else {
                    sendmessage(finalgrpids);
                }
                break;
            case R.id.deletegroup:
                deleteGroups();
                break;
            case R.id.fabCreateBroadcastGroup:
                Bundle b = new Bundle();
                b.putString("calltype", "create");
                b.putString("groupname", "");
                b.putString("groupmembers", "");
                b.putString("group_id", "");

                CreateBroadcastGroupFragment about = new CreateBroadcastGroupFragment();
                about.setArguments(b);
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.broadcast_groups_container, about).commit();

                break;
        }
    }


    private void deleteGroups() {

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

        if ( finalgrpids.equals("")) {
            CustomToast.customToast(getActivity(), "Please Select Group to Delete");
        } else {
            AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
            alert.setTitle("Delete");
            alert.setMessage("Are you sure you want to delete this group?");
            alert.setIconAttribute(android.R.attr.alertDialogIcon);

            alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {


                    System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!going grp ids====" + finalgrpids);

                    mApiCall.deleteBroadcastgroup("delete", finalgrpids);

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
    }

    @Override
    public void notifySuccess(Response<?> response) {

        if (response != null) {

            if (response.isSuccessful()) {

                broadcastGroupsResponseList.clear();
                mSwipeRefreshLayout.setRefreshing(false);
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
                    adapter = new MyBroadcastGroupsAdapter(getActivity(), broadcastGroupsResponseList);
                    mRecyclerView.setAdapter(adapter);
                    adapter.notifyItemRangeChanged(0, adapter.getItemCount());

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
        if (error instanceof SocketTimeoutException) {
            CustomToast.customToast(ctx, getString(R.string._404));
        } else if (error instanceof NullPointerException) {
            CustomToast.customToast(ctx, getString(R.string.no_response));
        } else if (error instanceof ClassCastException) {
            CustomToast.customToast(ctx, getString(R.string.no_response));
        } else {
            Log.i("Check Class-", "My BroadcastGroup");
        }
    }

    @Override
    public void notifyString(String str) {
        if (str != null) {
            if (str.equals("success_deletion")) {
                CustomToast.customToast(getActivity(), "BroadCast Group Deleted Successfully");
                MyBroadcastGroupsFragment about = new MyBroadcastGroupsFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.broadcast_groups_container, about).commit();
            } else {
                if (str.equalsIgnoreCase("success")) {
                    CustomToast.customToast(getActivity(), "BroadCast Message Send Successfully");
                    MyBroadcastGroupsFragment about = new MyBroadcastGroupsFragment();
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.broadcast_groups_container, about).commit();
                }
            }
        }else
        {
            CustomToast.customToast(getActivity(), "Somthing went Wrong Please try Again");
        }
    }

    @Override
    public void onRefresh() {
        mApiCall.MyBroadcastGroups(getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", ""));

    }

    /***********************Adapter for broadcast group*********************************/

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


        public MyBroadcastGroupsAdapter(Activity activity, List<MyBroadcastGroupsResponse.Success> broadcastlist) {
            this.mItemList = broadcastlist;
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

                    Bundle b = new Bundle();
                    b.putString("calltype", "update");
                    b.putString("groupname", mItemList.get(position).getGroupTitle());
                    b.putString("groupmembers", mItemList.get(position).getGroupMemberContacts());
                    b.putString("group_id", mItemList.get(position).getGroupId());
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
                    } else {
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

    //alert dialog box method to show pop up to send message and image in broadcast group

    public void sendmessage(final String groupids) {


        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View convertView = inflater.inflate(R.layout.custom_broadcast_message_layout, null);

        final EditText message = (EditText) convertView.findViewById(R.id.statustext);
        Button send = (Button) convertView.findViewById(R.id.btnsend);
        Button cancel = (Button) convertView.findViewById(R.id.btncancel);
        addimagetext = (TextView) convertView.findViewById(R.id.addimagetext);
        uploadImage = (ImageView) convertView.findViewById(R.id.upadateimg);
        final TextView wordCount = (TextView) convertView.findViewById(R.id.counttxt);
        alertDialog.setView(convertView);
        alert = alertDialog.show();
        alertDialog.setTitle("Send Message");


        message.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //wordCount.setText("200");
//				wordCount.setText(s.length());
                wordCount.setText(String.valueOf(200 - s.length()));
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {

                // TODO Auto-generated method stub
            }
        });

        SpannableString spanString = new SpannableString("Add Image");
        spanString.setSpan(new UnderlineSpan(), 0, spanString.length(), 0);
        addimagetext.setText(spanString);
        addimagetext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPickImage(v);
              //  selectImage();

            }
        });


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mApiCall.broadcastGroupMessage(groupids, message.getText().toString(), lastWord);
                uploadImage(mediaPath);
                //sendDataToWeb(message.getText().toString(), groupids);
                alert.dismiss();
            }
        });


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();

            }
        });

    }


    public void onPickImage(View view) {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {
                    Intent cameraintent = new Intent(
                            android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraintent, 101);
                } else if (options[item].equals("Choose from Gallery")) {
                    Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(galleryIntent, 0);
                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == 0 && resultCode == RESULT_OK && null != data) {
                // Get the Image from data
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                assert cursor != null;
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                mediaPath = cursor.getString(columnIndex);
                // Set the Image in ImageView for Previewing the Media
                uploadImage.setImageBitmap(BitmapFactory.decodeFile(mediaPath));
                cursor.close();
                ///storage/emulated/0/DCIM/Camera/20170411_124425.jpg
                lastWord = mediaPath.substring(mediaPath.lastIndexOf("/") + 1);
                Log.i("Media", "path" + lastWord);


            } else if (requestCode == 101) {
                if (resultCode == RESULT_OK) {
                    if (data != null) {
                        selectedImage = data.getData(); // the uri of the image taken
                        if (String.valueOf((Bitmap) data.getExtras().get("data")).equals("null")) {
                            bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
                        } else {
                            bitmap = (Bitmap) data.getExtras().get("data");
                        }
                        if (Float.valueOf(getImageOrientation()) >= 0) {
                            bitmapRotate = rotateImage(bitmap, Float.valueOf(getImageOrientation()));
                        } else {
                            bitmapRotate = bitmap;
                            bitmap.recycle();
                        }
                        uploadImage.setImageBitmap(bitmapRotate);

//                            Saving image to mobile internal memory for sometime
                        String root = getActivity().getApplicationContext().getFilesDir().toString();
                        File myDir = new File(root + "/androidlift");
                        myDir.mkdirs();

                        Random generator = new Random();
                        int n = 10000;
                        n = generator.nextInt(n);

//                            Give the file name that u want
                        fname = "Autokatta" + n + ".jpg";

                        mediaPath = root + "/androidlift/" + fname;
                        file = new File(myDir, fname);
                        saveFile(bitmapRotate, file);

                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //    Saving file to the mobile internal memory
    private void saveFile(Bitmap sourceUri, File destination) {
        if (destination.exists()) destination.delete();
        try {
            FileOutputStream out = new FileOutputStream(destination);
            sourceUri.compress(Bitmap.CompressFormat.JPEG, 60, out);
            out.flush();
            out.close();
            /*if (cd.isConnectingToInternet()) {*/
            lastWord = mediaPath.substring(mediaPath.lastIndexOf("/") + 1);
            //uploadImage(mediaPath);
            Log.i("image", "path" + lastWord);
            //      /data/data/autokatta.com/files/androidlift/Autokatta9460.jpg
            /*} else {
                Toast.makeText(MainActivity.this, "No Internet Connection..", Toast.LENGTH_LONG).show();
            }*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Bitmap rotateImage(Bitmap source, float angle) {
        Bitmap retVal;

        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        retVal = Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);

        return retVal;
    }

    private void uploadImage(String picturePath) {
        Log.i("PAth", "->" + picturePath);
        //    /storage/emulated/0/DCIM/Camera/20170411_124425.jpg
        //    /data/data/autokatta.com/files/androidlift/Autokatta9460.jpg
        File file = new File(picturePath);
        // Parsing any Media type file
        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());

        ServiceApi getResponse = ApiCall.getRetrofit().create(ServiceApi.class);
        Call<String> call = getResponse.uploadImageBroadcast(fileToUpload, filename);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.i("image", "->" + response.body());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    //    In some mobiles image will get rotate so to correting that this code will help us
    private int getImageOrientation() {
        final String[] imageColumns = {MediaStore.Images.Media._ID, MediaStore.Images.ImageColumns.ORIENTATION};
        final String imageOrderBy = MediaStore.Images.Media._ID + " DESC";
        Cursor cursor = getActivity().getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                imageColumns, null, null, imageOrderBy);

        if (cursor.moveToFirst()) {
            int orientation = cursor.getInt(cursor.getColumnIndex(MediaStore.Images.ImageColumns.ORIENTATION));
            System.out.println("orientation===" + orientation);
            cursor.close();
            return orientation;
        } else {
            return 0;
        }
    }

}
