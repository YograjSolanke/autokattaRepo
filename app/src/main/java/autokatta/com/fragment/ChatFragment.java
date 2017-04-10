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

import autokatta.com.R;
import autokatta.com.generic.GenericFunctions;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.view.OtherProfile;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ak-004 on 10/4/17.
 */

public class ChatFragment extends Fragment implements RequestNotifier, View.OnClickListener {

    public ChatFragment() {


    }

    private ListView listView;
    ImageView uploadImage;
    TextView addimagetext;
    AlertDialog alert;
    TextView BrdmessageText, msgFrom, dateNtime;
    String service_id = "", product_id = "", vehicle_id = "";
    ImageView imageView;


    //  MultipartEntity entity;
    String lastWord = "";

    String Sendercontact, sendername;
    private Button buttonRep;
    // ChatAdapter adapter;

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


        buttonRep = (Button) root.findViewById(R.id.replay);
        listView = (ListView) root.findViewById(R.id.msgview);
        msgFrom = (TextView) root.findViewById(R.id.msgFrom);
        chatwithtext = (TextView) root.findViewById(R.id.chatwithtext);

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


        try {
            Bundle b = getArguments();
            Sendercontact = b.getString("sender");
            sendername = b.getString("sendername");
            product_id = b.getString("product_id");
            service_id = b.getString("service_id");
            vehicle_id = b.getString("vehicle_id");

            chatwithtext.setText(sendername);

            //  getMyChat();

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

    }

    @Override
    public void notifyError(Throwable error) {

    }

    @Override
    public void notifyString(String str) {

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

                //  sendMyChat(message.getText().toString());
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
}
