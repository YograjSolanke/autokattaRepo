package autokatta.com.adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.networkreceiver.ConnectionDetector;
import autokatta.com.other.CustomToast;
import autokatta.com.response.GetServiceSearchResponse;
import autokatta.com.view.ServiceViewActivity;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ak-001 on 18/4/17.
 */

public class SearchServiceAdapter extends BaseAdapter implements RequestNotifier {

    Activity activity;
    private List<GetServiceSearchResponse.Success> allSearchDataArrayList = new ArrayList<>();
    private LayoutInflater inflater;
    ApiCall apiCall;
    private ConnectionDetector connectionDetector;
    private String myContact;

    public SearchServiceAdapter(Activity activity1, List<GetServiceSearchResponse.Success> allSearchDataArrayList) {
        this.activity = activity1;
        this.allSearchDataArrayList = allSearchDataArrayList;
        connectionDetector = new ConnectionDetector(activity);
        apiCall = new ApiCall(activity, this);
        myContact = activity.getSharedPreferences(activity.getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", "");
    }

    @Override
    public int getCount() {
        return allSearchDataArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return allSearchDataArrayList.get(position).hashCode();
    }

    private class YoHolder {
        TextView pname, pprice, pdetails, ptype, ptags, pCategoey;
        ImageView image, deleteproduct;
        Button viewdetails;
        RatingBar productrating;
        CardView mCardView;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final YoHolder yoHolder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.store_product_adapter, null);
            yoHolder = new YoHolder();

            yoHolder.pname = (TextView) convertView.findViewById(R.id.edittxt);
            yoHolder.pprice = (TextView) convertView.findViewById(R.id.priceedit);
            yoHolder.pdetails = (TextView) convertView.findViewById(R.id.editdetails);
            yoHolder.ptags = (TextView) convertView.findViewById(R.id.edittags);
            yoHolder.pCategoey = (TextView) convertView.findViewById(R.id.editCategory);
            yoHolder.ptype = (TextView) convertView.findViewById(R.id.editproducttype);
            yoHolder.viewdetails = (Button) convertView.findViewById(R.id.btnviewdetails);
            yoHolder.image = (ImageView) convertView.findViewById(R.id.profile);
            yoHolder.productrating = (RatingBar) convertView.findViewById(R.id.productrating);
            yoHolder.deleteproduct = (ImageView) convertView.findViewById(R.id.deleteproduct);
            yoHolder.mCardView = (CardView) convertView.findViewById(R.id.card_view);
            convertView.setTag(yoHolder);
        } else {
            yoHolder = (YoHolder) convertView.getTag();
        }

        final GetServiceSearchResponse.Success service = allSearchDataArrayList.get(position);

        List<String> images = new ArrayList<String>();
        yoHolder.pname.setText(service.getName());
        yoHolder.pprice.setText(service.getPrice());
        yoHolder.pdetails.setText(service.getDetails());
        yoHolder.ptags.setText(service.getServiceTags());
        yoHolder.ptype.setText(service.getType());
        yoHolder.pCategoey.setText(service.getCategory());

        yoHolder.productrating.setEnabled(false);

        if (myContact.equals(service.getStorecontact())) {
            yoHolder.deleteproduct.setVisibility(View.VISIBLE);
        }

        yoHolder.pname.setEnabled(false);
        yoHolder.pprice.setEnabled(false);
        yoHolder.pdetails.setEnabled(false);
        yoHolder.ptags.setEnabled(false);
        yoHolder.ptype.setEnabled(false);
        yoHolder.pCategoey.setEnabled(false);

        try {

            if (service.getImages().equals("") || service.getImages().equals("null") ||
                    service.getImages().equals("")) {

                yoHolder.image.setBackgroundResource(R.drawable.logo);
            } else {
                images.clear();
                String[] parts = service.getImages().split(",");

                for (int l = 0; l < parts.length; l++) {
                    images.add(parts[l]);
                    System.out.println(parts[l]);
                }
                System.out.println("http://autokatta.com/mobile/Service_pics/" + images.get(0));
                String pimagename = "http://autokatta.com/mobile/Service_pics/" + images.get(0);
                pimagename = pimagename.replaceAll(" ", "%20");
                try {

                    Glide.with(activity)
                            .load(pimagename)
                            .bitmapTransform(new CropCircleTransformation(activity))
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .placeholder(R.drawable.logo)
                            .into(yoHolder.image);


                } catch (Exception e) {
                    System.out.println("Error in uploading images");
                }
            }

        } catch (Exception e) {

            e.printStackTrace();
        }
        if (!service.getServicerating().equals("null")) {
            yoHolder.productrating.setRating(Float.parseFloat(service.getServicerating()));
        } else {

        }

        yoHolder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityOptions options = ActivityOptions.makeCustomAnimation(activity, R.anim.ok_left_to_right, R.anim.ok_right_to_left);
                String proId = service.getId();
                Intent intent = new Intent(activity, ServiceViewActivity.class);
                intent.putExtra("service_id", proId);
                activity.startActivity(intent, options.toBundle());
            }
        });

        yoHolder.deleteproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String product_id = service.getId();
                if (!connectionDetector.isConnectedToInternet()) {
                    CustomToast.customToast(activity, activity.getString(R.string.no_internet));
                } else {
                    new android.support.v7.app.AlertDialog.Builder(activity)
                            .setTitle("Delete?")
                            .setMessage("Are You Sure You Want To Delete This Product?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    apiCall.deleteProduct(product_id, "delete");
                                    allSearchDataArrayList.remove(position);
                                    notifyDataSetChanged();
                                }
                            })

                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
            }
        });

        return convertView;
    }

    @Override
    public void notifySuccess(Response<?> response) {

    }

    @Override
    public void notifyError(Throwable error) {
        if (error instanceof SocketTimeoutException) {
            CustomToast.customToast(activity, activity.getString(R.string._404_));
        } else if (error instanceof NullPointerException) {
            CustomToast.customToast(activity, activity.getString(R.string.no_response));
        } else if (error instanceof ClassCastException) {
            CustomToast.customToast(activity, activity.getString(R.string.no_response));
        } else if (error instanceof ConnectException) {
            CustomToast.customToast(activity, activity.getString(R.string.no_internet));
        } else if (error instanceof UnknownHostException) {
            CustomToast.customToast(activity, activity.getString(R.string.no_internet));
        } else {
            Log.i("Check Class-"
                    , "SearchServiceAdaper");
            error.printStackTrace();
        }
    }

    @Override
    public void notifyString(String str) {
        if (str != null) {
            if (str.equals("success")) {
                CustomToast.customToast(activity, "Service Deleted");

            }
        }
    }
}
