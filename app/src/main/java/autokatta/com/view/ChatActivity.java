package autokatta.com.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.File;
import java.io.FileOutputStream;
import java.net.SocketTimeoutException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import autokatta.com.R;
import autokatta.com.adapter.ChatAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.interfaces.ServiceApi;
import autokatta.com.other.CustomToast;
import autokatta.com.response.BroadcastMessageResponse;
import autokatta.com.response.ChatElementDetails;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ak-004 on 10/4/17.
 */

public class ChatActivity extends AppCompatActivity implements RequestNotifier, View.OnClickListener {

    private ListView listView;
    ImageView uploadImage;
    TextView addimagetext;
    AlertDialog alert;
    TextView msgFrom;
    int service_id = 0, product_id = 0, vehicle_id = 0;
    Uri selectedImage = null;
    Bitmap bitmapRotate;
    String fname;
    File file;
    String mediaPath = "";
    ApiCall apiCall;
    ArrayList<BroadcastMessageResponse.Success> chatlist = new ArrayList<>();
    //  MultipartEntity entity;
    String lastWord = "";
    String Sendercontact, sendername, senderLocation;
    ChatAdapter adapter;
    Bitmap bitmap;
    String myContact;
    TextView Title, Category, Brand, Model, Keyword, price, chatwithtext, chatWithLocation;
    ImageView Image;
    RelativeLayout relCategory, relBrand, relModel, relPrice, MainRel, relativeprofile;
   /* String vehi_img_url = getString(R.string.base_image_url);
    String prduct_img_url = getString(R.string.base_image_url);
    String service_img_url = getString(R.string.base_image_url);*/
    String fullpath = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        myContact = getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", "");

        Button buttonRep = (Button) findViewById(R.id.replay);
        listView = (ListView) findViewById(R.id.msgview);
        //msgFrom = (TextView) findViewById(R.id.msgFrom);
        chatwithtext = (TextView) findViewById(R.id.chatwithtext);
        chatWithLocation = (TextView) findViewById(R.id.chatwithLocation);
        apiCall = new ApiCall(this, this);
        Keyword = (TextView) findViewById(R.id.keyword);
        Title = (TextView) findViewById(R.id.settitle);
        Category = (TextView) findViewById(R.id.setcategory);
        Brand = (TextView) findViewById(R.id.setbrand);
        Model = (TextView) findViewById(R.id.setmodel);
        Image = (ImageView) findViewById(R.id.image);
        price = (TextView) findViewById(R.id.setprice);
        relCategory = (RelativeLayout) findViewById(R.id.relative2);
        relBrand = (RelativeLayout) findViewById(R.id.relative3);
        relModel = (RelativeLayout) findViewById(R.id.relative4);
        relPrice = (RelativeLayout) findViewById(R.id.relative5);
        MainRel = (RelativeLayout) findViewById(R.id.MainRel);
        relativeprofile = (RelativeLayout) findViewById(R.id.relativeprofile);

        relativeprofile.setOnClickListener(this);
        MainRel.setOnClickListener(this);
        buttonRep.setOnClickListener(this);

        try {

            Sendercontact = getIntent().getExtras().getString("sender");
            sendername = getIntent().getExtras().getString("sendername");
            product_id = getIntent().getExtras().getInt("product_id");
            service_id = getIntent().getExtras().getInt("service_id");
            vehicle_id = getIntent().getExtras().getInt("vehicle_id");
            // senderLocation = getIntent().getExtras().getString("senderLocation");

            setTitle(sendername);
            chatwithtext.setText(sendername);
            apiCall.getChatMessageData(Sendercontact, myContact, product_id, service_id, vehicle_id);
            if (product_id==0 && service_id==0 && vehicle_id==0)
                MainRel.setVisibility(View.GONE);
            else {
                apiCall.getChatElementData(product_id, service_id, vehicle_id);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case (R.id.relativeprofile):
                Bundle b = new Bundle();
                b.putString("action", "other");
                b.putString("contactOtherProfile", Sendercontact);
                Intent intent = new Intent(ChatActivity.this, OtherProfile.class);
                intent.putExtras(b);
                startActivity(intent);
                break;
            case (R.id.replay):
                sendmessage();
                break;
            case (R.id.MainRel):
                if (Keyword.getText().toString().equalsIgnoreCase("Vehicle")) {
                    Intent intent1 = new Intent(this, VehicleDetails.class);
                    intent1.putExtra("vehicle_id", vehicle_id);
                    startActivity(intent1);
                }else if (Keyword.getText().toString().equalsIgnoreCase("Product"))
            {
                Intent intent2 = new Intent(this, ProductViewActivity.class);
                intent2.putExtra("product_id", product_id);
                startActivity(intent2);
            }else
            {
                Intent intent3 = new Intent(this, ServiceViewActivity.class);
                intent3.putExtra("service_id", service_id);
                startActivity(intent3);
            }
                break;
        }

    }

    @Override
    public void notifySuccess(Response<?> response) {
        DateFormat f = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
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
                            message.setSenderProfileImage(message.getSenderProfileImage());
                            senderLocation = message.getSenderLocation();
                            chatWithLocation.setText(senderLocation);
                            Date d = null;
                            try {

                                d = f.parse(message.getDate());
                                Log.i("dateeeeeeeeeeeee", message.getDate1());

                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            message.setNewDate(d);
                            chatlist.add(message);
                        }
                        adapter = new ChatAdapter(ChatActivity.this, myContact, chatlist);
                        listView.setAdapter(adapter);
                        listView.setSelection(chatlist.size() - 1);
                    } else
                        CustomToast.customToast(ChatActivity.this, getString(R.string.no_response));
                }
                 /*
                      Chat Element  Response
                 */
                if (response.body() instanceof ChatElementDetails) {
                    ChatElementDetails chatElementDetails = (ChatElementDetails) response.body();
                    if (chatElementDetails.getSuccess() != null) {
                        if (!chatElementDetails.getSuccess().getProduct().isEmpty()) {
                            for (ChatElementDetails.Success.Product product : chatElementDetails.getSuccess().getProduct()) {
                                Keyword.setText(product.getKeyword());
                                Title.setText(product.getProductName());
                                price.setText(product.getPrice());
                                fullpath =  getString(R.string.base_image_url) + product.getImages();
                                fullpath = fullpath.replaceAll(" ", "%20");
                                Glide.with(ChatActivity.this)
                                        .load(fullpath)
                                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                                        .bitmapTransform(new CropCircleTransformation(ChatActivity.this))
                                        .placeholder(R.drawable.logo)
                                        .into(Image);
                                relCategory.setVisibility(View.GONE);
                                relBrand.setVisibility(View.GONE);
                                relModel.setVisibility(View.GONE);
                            }
                        } else if (!chatElementDetails.getSuccess().getService().isEmpty()) {
                            for (ChatElementDetails.Success.Service service : chatElementDetails.getSuccess().getService()) {
                                Keyword.setText(service.getKeyword());
                                Title.setText(service.getName());
                                price.setText(service.getPrice());
                                fullpath =  getString(R.string.base_image_url) + service.getImages();
                                fullpath = fullpath.replaceAll(" ", "%20");
                                Glide.with(ChatActivity.this)
                                        .load(fullpath)
                                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                                        .bitmapTransform(new CropCircleTransformation(ChatActivity.this))
                                        .placeholder(R.drawable.logo)
                                        .into(Image);
                                relCategory.setVisibility(View.GONE);
                                relBrand.setVisibility(View.GONE);
                                relModel.setVisibility(View.GONE);
                            }
                        } else if (!chatElementDetails.getSuccess().getVehicle().isEmpty()) {
                            for (ChatElementDetails.Success.Vehicle vehicle : chatElementDetails.getSuccess().getVehicle()) {
                                Keyword.setText(vehicle.getKeyword());
                                Title.setText(vehicle.getTitle());
                                price.setText(vehicle.getPrice());
                                Category.setText(vehicle.getCategory());
                                Brand.setText(vehicle.getManufacturer());
                                Model.setText(vehicle.getModel());
                                fullpath =  getString(R.string.base_image_url) + vehicle.getImage();
                                fullpath = fullpath.replaceAll(" ", "%20");
                                Glide.with(ChatActivity.this)
                                        .load(fullpath)
                                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                                        .bitmapTransform(new CropCircleTransformation(ChatActivity.this))
                                        .placeholder(R.drawable.logo)
                                        .into(Image);
                            }
                        }
                    } else
                        CustomToast.customToast(ChatActivity.this, getString(R.string.no_response));
                }
            } else {
                CustomToast.customToast(ChatActivity.this, getString(R.string._404));
            }
        } else {
            CustomToast.customToast(getApplicationContext(), getString(R.string.no_response));
        }
    }

    @Override
    public void notifyError(Throwable error) {
        if (error instanceof SocketTimeoutException) {
            CustomToast.customToast(getApplicationContext(), getString(R.string._404));
        } else if (error instanceof NullPointerException) {
            CustomToast.customToast(getApplicationContext(), getString(R.string.no_response));
        } else if (error instanceof ClassCastException) {
            CustomToast.customToast(getApplicationContext(), getString(R.string.no_response));
        } else {
            Log.i("Check Class-", "ChatActivity");
            error.printStackTrace();
        }

    }

    @Override
    public void notifyString(String str) {
        if (str != null) {
            if (str.equalsIgnoreCase("success_message_saved")) {
                uploadImage(mediaPath);
                listView.setAdapter(null);
                apiCall.getChatMessageData(Sendercontact, myContact, product_id, service_id, vehicle_id);
            } else {
                CustomToast.customToast(getApplicationContext(), getString(R.string._404));
            }
        } else {
            CustomToast.customToast(getApplicationContext(), getString(R.string.no_response));
        }
    }
    //alert dialog box method to show pop up to send message and image in broadcast group

    public void sendmessage() {
        lastWord = "";
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(ChatActivity.this);
        LayoutInflater inflater = getLayoutInflater();
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


    public void onPickImage(View view) {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(ChatActivity.this);
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
                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
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
                            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
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
                        String root = getApplicationContext().getFilesDir().toString();
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
        Cursor cursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
    }
}
