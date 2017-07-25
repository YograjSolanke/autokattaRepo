package autokatta.com.adapter;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.networkreceiver.ConnectionDetector;
import autokatta.com.other.CustomToast;
import autokatta.com.response.StoreInventoryResponse;
import autokatta.com.view.ServiceViewActivity;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import retrofit2.Response;

/**
 * Created by ak-004 on 17/4/17.
 */


public class StoreServiceAdapter extends RecyclerView.Adapter<StoreServiceAdapter.ServiceHolder> implements RequestNotifier {
    Activity activity;
    List<StoreInventoryResponse.Success.Service> mMainList = new ArrayList<>();
    private String myContact, storeContact;
    ApiCall apiCall;
    private String pimagename = "";
    private ConnectionDetector connectionDetector;

    public StoreServiceAdapter(Activity activity, List<StoreInventoryResponse.Success.Service> serviceList, String myContact,
                               String storeContact) {
        this.activity = activity;
        this.mMainList = serviceList;
        this.myContact = myContact;
        this.storeContact = storeContact;
        connectionDetector = new ConnectionDetector(activity);
        apiCall = new ApiCall(activity, this);
    }

    @Override
    public StoreServiceAdapter.ServiceHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.store_product_adapter, parent, false);
        return new ServiceHolder(view);
    }

    @Override
    public void onBindViewHolder(final StoreServiceAdapter.ServiceHolder holder, final int position) {
        ArrayList<String> images = new ArrayList<String>();
        final StoreInventoryResponse.Success.Service service = mMainList.get(position);
        holder.pname.setText(service.getServiceName());
        holder.pprice.setText(service.getServicePrice());
        holder.pdetails.setText(service.getServiceDetails());
        holder.ptags.setText(service.getServicetags());
        holder.ptype.setText(service.getServiceType());
        holder.pCategory.setText(service.getServicecategory());
        holder.productrating.setEnabled(false);

        if (myContact.equals(service.getStorecontact())) {
            holder.deleteproduct.setVisibility(View.VISIBLE);
        }

        holder.pname.setEnabled(false);
        holder.pprice.setEnabled(false);
        holder.pdetails.setEnabled(false);
        holder.ptags.setEnabled(false);
        holder.ptype.setEnabled(false);
        holder.pCategory.setEnabled(false);

        try {
            if (service.getServiceImages() == null) {
                holder.image.setBackgroundResource(R.drawable.logo);
            } else {
                String[] parts = service.getServiceImages().split(",");
                for (int l = 0; l < parts.length; l++) {
                    images.add(parts[l]);
                    System.out.println(parts[l]);
                }
                System.out.println("http://autokatta.com/mobile/Service_pics/" + images.get(0));

                pimagename = "http://autokatta.com/mobile/Service_pics/" + images.get(0);
                pimagename = pimagename.replaceAll(" ", "%20");
                try {
                    Glide.with(activity)
                            .load(pimagename)
                            .bitmapTransform(new CropCircleTransformation(activity))
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .placeholder(R.drawable.logo)
                            .into(holder.image);
                } catch (Exception e) {
                    System.out.println("Error in uploading images");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (service.getServicerating() != null) {
            holder.productrating.setRating(Float.parseFloat(service.getServicerating()));
        }

        holder.viewdetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int serviceId = service.getServiceId();
                Intent intent = new Intent(activity, ServiceViewActivity.class);
                intent.putExtra("service_id", serviceId);
                activity.startActivity(intent);
            }
        });

        holder.deleteproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int serviceId = service.getServiceId();
                if (!connectionDetector.isConnectedToInternet()) {
                    CustomToast.customToast(activity, "Please try later");
                   // Toast.makeText(activity, "Please try later", Toast.LENGTH_SHORT).show();
                } else {
                    new android.support.v7.app.AlertDialog.Builder(activity)
                            .setTitle("Delete?")
                            .setMessage("Are You Sure You Want To Delete This Service?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    apiCall.deleteService(serviceId, "delete");
                                    mMainList.remove(position);
                                    notifyItemRemoved(position);
                                    notifyItemRangeChanged(position, mMainList.size());
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


    }

    @Override
    public int getItemCount() {
        return mMainList.size();
    }

    @Override
    public void notifySuccess(Response<?> response) {

    }

    @Override
    public void notifyError(Throwable error) {
        if (error instanceof SocketTimeoutException) {
            CustomToast.customToast(activity, activity.getString(R.string._404));
        } else if (error instanceof NullPointerException) {
            CustomToast.customToast(activity, activity.getString(R.string.no_response));
        } else if (error instanceof ClassCastException) {
            CustomToast.customToast(activity, activity.getString(R.string.no_response));
        } else {
            Log.i("Check Class-"
                    , "StoreServiceAdaper");
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

    class ServiceHolder extends RecyclerView.ViewHolder {
        TextView pname, pprice, pdetails, ptype, ptags, pCategory;
        ImageView image, deleteproduct;
        Button  sviewdetails, vehidetails;
        RatingBar productrating;
        CardView viewdetails;

        ServiceHolder(View itemView) {
            super(itemView);
            pname = (TextView) itemView.findViewById(R.id.edittxt);
            pprice = (TextView) itemView.findViewById(R.id.priceedit);
            pdetails = (TextView) itemView.findViewById(R.id.editdetails);
            ptags = (TextView) itemView.findViewById(R.id.edittags);
            ptype = (TextView) itemView.findViewById(R.id.editproducttype);
            pCategory = (TextView) itemView.findViewById(R.id.editCategory);
            viewdetails= (CardView) itemView.findViewById(R.id.card_view);
          //  viewdetails = (Button) itemView.findViewById(R.id.btnviewdetails);
            image = (ImageView) itemView.findViewById(R.id.profile);
            productrating = (RatingBar) itemView.findViewById(R.id.productrating);
            deleteproduct = (ImageView) itemView.findViewById(R.id.deleteproduct);
        }
    }
}