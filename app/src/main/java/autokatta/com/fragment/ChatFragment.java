package autokatta.com.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.SocketTimeoutException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import autokatta.com.R;
import autokatta.com.adapter.ChatAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.generic.GenericFunctions;
import autokatta.com.interfaces.ImageUpload;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import autokatta.com.response.BroadcastMessageResponse;
import autokatta.com.view.OtherProfile;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ak-004 on 10/4/17.
 */

public class ChatFragment extends Fragment implements RequestNotifier, View.OnClickListener {

    public ChatFragment() {


    }

    private ListView listView;
    ImageView uploadImage;
    ImageUpload mImageUpload;
    TextView addimagetext;
    AlertDialog alert;
    TextView BrdmessageText, msgFrom, dateNtime;
    String service_id = "", product_id = "", vehicle_id = "";
    ImageView imageView;
    ApiCall apiCall;
    ArrayList<BroadcastMessageResponse.Success> chatlist = new ArrayList<>();


    //  MultipartEntity entity;
    String lastWord = "";

    String Sendercontact, sendername;
    private Button buttonRep;
    ChatAdapter adapter;

    String picturePath = "";
    Bitmap bitmap;
    String userSelected = "";
    String myContact;
    TextView Title, Category, Brand, Model, Keyword, price, chatwithtext;
    ImageView Image;
    RelativeLayout relCategory, relBrand, relModel, relPrice, MainRel, relativeprofile;
    String vehi_img_url = "http://autokatta.com/mobile/uploads/";
    String prduct_img_url = "http://autokatta.com/mobile/Product_pics/";
    String service_img_url = "http://autokatta.com/mobile/Service_pics/";
    String fullpath = "";

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View root = inflater.inflate(R.layout.chat_fragment, null);
        myContact = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", "");

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();


        // Change base URL to your upload server URL.
        mImageUpload = new Retrofit.Builder().baseUrl(getString(R.string.base_url)).client(client).build().create(ImageUpload.class);

        buttonRep = (Button) root.findViewById(R.id.replay);
        listView = (ListView) root.findViewById(R.id.msgview);
        msgFrom = (TextView) root.findViewById(R.id.msgFrom);
        chatwithtext = (TextView) root.findViewById(R.id.chatwithtext);
        apiCall = new ApiCall(getActivity(), this);

        Keyword = (TextView) root.findViewById(R.id.keyword);
        Title = (TextView) root.findViewById(R.id.settitle);
        Category = (TextView) root.findViewById(R.id.setcategory);
        Brand = (TextView) root.findViewById(R.id.setbrand);
        Model = (TextView) root.findViewById(R.id.setmodel);
        Image = (ImageView) root.findViewById(R.id.image);
        price = (TextView) root.findViewById(R.id.setprice);
        relCategory = (RelativeLayout) root.findViewById(R.id.relative2);
        relBrand = (RelativeLayout) root.findViewById(R.id.relative3);
        relModel = (RelativeLayout) root.findViewById(R.id.relative4);
        relPrice = (RelativeLayout) root.findViewById(R.id.relative5);
        MainRel = (RelativeLayout) root.findViewById(R.id.MainRel);
        relativeprofile = (RelativeLayout) root.findViewById(R.id.relativeprofile);

        relativeprofile.setOnClickListener(this);
        buttonRep.setOnClickListener(this);


        try {
            Bundle b = getArguments();
            Sendercontact = b.getString("sender");
            sendername = b.getString("sendername");
            product_id = b.getString("product_id");
            service_id = b.getString("service_id");
            vehicle_id = b.getString("vehicle_id");

            chatwithtext.setText(sendername);

            apiCall.getChatMessageData(Sendercontact, myContact, product_id, service_id, vehicle_id);

            if (product_id.equals("") && service_id.equals("") && vehicle_id.equals(""))
                MainRel.setVisibility(View.GONE);
            else {
            }
            // getChatAllData();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return root;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case (R.id.relativeprofile):

                Bundle b = new Bundle();
                b.putString("action", "other");
                b.putString("contact", Sendercontact);
                Intent intent = new Intent(getActivity(), OtherProfile.class);
                intent.putExtras(b);
                getActivity().startActivity(intent);
                break;
            case (R.id.replay):

                sendmessage();
                break;


        }

    }

    @Override
    public void notifySuccess(Response<?> response) {
        DateFormat f = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        if (response != null) {
            if (response.isSuccessful()) {

                /*
                        Response to get chat message
                 */
                if (response.body() instanceof BroadcastMessageResponse) {
                    BroadcastMessageResponse moduleResponse = (BroadcastMessageResponse) response.body();
                    chatlist.clear();

                    if (!moduleResponse.getSuccess().isEmpty()) {

                        for (BroadcastMessageResponse.Success message : moduleResponse.getSuccess()) {
                            message.setSender(message.getSender());
                            message.setReceiver(message.getReceiver());
                            message.setMessage(message.getMessage());
                            message.setImage(message.getImage());

                            Date d = null;
                            try {
                                d = f.parse(message.getDate());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            message.setNewDate(d);

                            chatlist.add(message);

                        }

                        adapter = new ChatAdapter(getActivity(), myContact, chatlist);
                        listView.setAdapter(adapter);
                        listView.setSelection(chatlist.size() - 1);


                    } else
                        CustomToast.customToast(getActivity(), getString(R.string.no_response));
                }

                 /*
                        Response after creating store
                 */
//                if (response.body() instanceof CreateStoreResponse) {
//                    CreateStoreResponse createStoreResponse = (CreateStoreResponse) response.body();
//
//                    if (createStoreResponse.getSuccess() != null) {
//                        String id = createStoreResponse.getSuccess().getStoreID().toString();
//                        Log.i("StoreId", "->" + id);
//                        CustomToast.customToast(getActivity(), "Store Created Successfully");
//
//                        upload(picturePath);
//                        upload(coverpicturePath);
//
//                        bundle = new Bundle();
//                        bundle.putString("store_id", id);
//                        bundle.putString("call", callFrom);
//                        bundle.putString("storetype", storetype);
//
//                        AddMoreAdminsForStoreFrag addAdmin = new AddMoreAdminsForStoreFrag();
//                        addAdmin.setArguments(bundle);
//                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                        fragmentTransaction.replace(R.id.myStoreListFrame, addAdmin).addToBackStack("mystorelist").commit();
//
//
//                    } else
//                        CustomToast.customToast(getActivity(), getString(R.string.no_response));
//                }

            } else {
                CustomToast.customToast(getActivity(), getString(R.string._404));
            }
        } else {
            CustomToast.customToast(getActivity(), getString(R.string.no_response));
        }


    }

    @Override
    public void notifyError(Throwable error) {

        if (error instanceof SocketTimeoutException) {
            CustomToast.customToast(getActivity(), getString(R.string._404));
        } else if (error instanceof NullPointerException) {
            CustomToast.customToast(getActivity(), getString(R.string.no_response));
        } else if (error instanceof ClassCastException) {
            CustomToast.customToast(getActivity(), getString(R.string.no_response));
        } else {
            Log.i("Check Class-", "ChatFragment");
            error.printStackTrace();
        }

    }

    @Override
    public void notifyString(String str) {
        if (str != null) {
            if (str.equalsIgnoreCase("success")) {
                upload(picturePath);
                listView.setAdapter(null);

                apiCall.getChatMessageData(Sendercontact, myContact, product_id, service_id, vehicle_id);

            } else {
                CustomToast.customToast(getActivity(), getString(R.string._404));
            }
        } else {
            CustomToast.customToast(getActivity(), getString(R.string.no_response));
        }

    }


    //alert dialog box method to show pop up to send message and image in broadcast group

    public void sendmessage() {

        lastWord = "";

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View convertView = inflater.inflate(R.layout.custom_broadmessage_layout, null);

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

                selectImage();

            }
        });


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                apiCall.sendChatMessage(myContact, Sendercontact, message.getText().toString(), lastWord, product_id, service_id, vehicle_id);
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


    private void selectImage() {


        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override

            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Take Photo")) {

                    userSelected = "camera";
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                    startActivityForResult(intent, 1);

                } else if (options[item].equals("Choose from Gallery")) {

                    userSelected = "gallery";
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                    startActivityForResult(intent, 2);

                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }

            }

        });

        builder.show();
    }


    //new activity???????????????????????????????????????????///
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {

            ////image upload from camera
            if (userSelected == "camera") {
                Bundle b = data.getExtras();

                bitmap = (Bitmap) b.get("data");
                uploadImage.setImageBitmap(bitmap);
                getImageUri(getActivity(), bitmap);
                System.out.println("rutu----------------" + picturePath);

                lastWord = picturePath.substring(picturePath.lastIndexOf("/") + 1);
                System.out.println(lastWord);
            }
            //Image Upload from gallery
            else if (userSelected == "gallery") {
                System.out.println("rutu= userselected in gallery==========:" + userSelected);

                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                picturePath = cursor.getString(columnIndex);
                cursor.close();

                GenericFunctions obj = new GenericFunctions();
                Bitmap rotatedBitmap = obj.decodeFile(picturePath);

                uploadImage.setImageBitmap(rotatedBitmap);

                System.out.println(picturePath);
                lastWord = picturePath.substring(picturePath.lastIndexOf("/") + 1);
                System.out.println(lastWord);
            }
        }
    }

    //Code for getting uri from bitmap image only if image is set in ImageView
    public Uri getImageUri(Context inContext, Bitmap bitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        picturePath = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), bitmap, "Title", null);
        return Uri.parse(picturePath);
    }


    //upload image to server

    public void upload(String picturePath) {

        System.out.println("picturePath while upload image:" + picturePath);
        System.out.println("rutu= userselected in upload==========:" + userSelected);

        try {

            // HttpPost httpPost = new HttpPost("http://www.autokatta.com/mobile/upload_profile_profile_pics.php"); // serverupdate_profile.php
            lastWord = picturePath.substring(picturePath.lastIndexOf("/") + 1);
            System.out.println(lastWord);


            File file = new File(picturePath);
            RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file.getPath());
            MultipartBody.Part body = MultipartBody.Part.createFormData("club_image", file.getName(), reqFile);
            RequestBody name = RequestBody.create(MediaType.parse("text/plain"), "upload_test");

            retrofit2.Call<okhttp3.ResponseBody> req = mImageUpload.postBroadcastMessageImage(body, name);
            req.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    CustomToast.customToast(getActivity(), "Image Upladed");
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    t.printStackTrace();
                }
            });

        } catch (Exception e) {

            Log.e(e.getClass().getName(), e.getMessage(), e);

        }


    }
}
