package autokatta.com.adapter;

import android.app.Activity;
import android.app.ActivityOptions;
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
import autokatta.com.interfaces.OnLoadMoreListener;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.networkreceiver.ConnectionDetector;
import autokatta.com.other.CustomToast;
import autokatta.com.response.StoreInventoryResponse;
import autokatta.com.view.ProductViewActivity;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import retrofit2.Response;

/**
 * Created by ak-003 on 24/4/17.
 */

public class GroupServiceAdpater extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements RequestNotifier {
    Activity activity;
    List<StoreInventoryResponse.Success.Service> mMainList = new ArrayList<>();
    private String myContact, storeContact, mGroupType;
    ApiCall apiCall;
    private ConnectionDetector connectionDetector;

    private OnLoadMoreListener loadMoreListener;
    final int TYPE_DATA = 0;
    private final int TYPE_LOAD = 1;
    private boolean isLoading = false, isMoreDataAvailable = true;

    public GroupServiceAdpater(Activity activity, List<StoreInventoryResponse.Success.Service> serviceList, String myContact,
                               String storeContact, String mGroupType) {
        this.activity = activity;
        this.mMainList = serviceList;
        this.myContact = myContact;
        this.storeContact = storeContact;
        this.mGroupType = mGroupType;
        connectionDetector = new ConnectionDetector(activity);
        apiCall = new ApiCall(activity, this);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(activity);
        if (viewType == TYPE_DATA) {
            return new ServiceHolder(inflater.inflate(R.layout.store_product_adapter, parent, false));
        } else {
            return new LoadHolder(inflater.inflate(R.layout.row_load, parent, false));
        }
        /*View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.store_product_adapter, parent, false);
        return new ServiceHolder(view);*/
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder1, final int position) {
        if (position >= getItemCount() - 1 && isMoreDataAvailable && !isLoading && loadMoreListener != null) {
            isLoading = true;
            loadMoreListener.onLoadMore();
        }

        if (getItemViewType(position) == TYPE_DATA) {
            final ServiceHolder holder = (ServiceHolder) holder1;
            holder.mEnquiry.setVisibility(View.GONE);

            List<String> images = new ArrayList<String>();
            final StoreInventoryResponse.Success.Service service = mMainList.get(position);
            holder.pname.setText(service.getServiceName());
            holder.pprice.setText(service.getServicePrice());
            holder.pdetails.setText(service.getServiceDetails());
            holder.ptags.setText(service.getServicetags());
            holder.ptype.setText(service.getServiceType());
            holder.pCategory.setText(service.getServicecategory());
            holder.productrating.setEnabled(false);

       /*if other group edit buton and view button visiblity*/
            if (myContact.equals(service.getStorecontact()) && mGroupType.startsWith("MyGroup")) {
                holder.deleteproduct.setVisibility(View.VISIBLE);
            } else {
                holder.mEdit.setVisibility(View.GONE);
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
                    System.out.println(activity.getString(R.string.base_image_url) + images.get(0));

                    String pimagename = activity.getString(R.string.base_image_url) + images.get(0);
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

            holder.mDetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ActivityOptions options = ActivityOptions.makeCustomAnimation(activity, R.anim.ok_left_to_right, R.anim.ok_right_to_left);
                    int proId = service.getServiceId();
                    Intent intent = new Intent(activity, ProductViewActivity.class);
                    intent.putExtra("product_id", proId);
                    activity.startActivity(intent, options.toBundle());
                }
            });

            holder.mEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ActivityOptions options = ActivityOptions.makeCustomAnimation(activity, R.anim.ok_left_to_right, R.anim.ok_right_to_left);
                    int proId = service.getServiceId();
                    Intent intent = new Intent(activity, ProductViewActivity.class);
                    intent.putExtra("product_id", proId);
                    intent.putExtra("editmode", "yes");
                    activity.startActivity(intent, options.toBundle());
                }
            });

      /*  holder.viewdetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int serviceId = service.getServiceId();
                Intent intent = new Intent(activity, ServiceViewActivity.class);
                intent.putExtra("service_id", serviceId);
                activity.startActivity(intent);

            }
        });*/

            holder.deleteproduct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final int serviceId = service.getServiceId();
                    if (!connectionDetector.isConnectedToInternet()) {
                        CustomToast.customToast(activity, "Please try later");
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

    }

    @Override
    public int getItemCount() {
        return mMainList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mMainList.get(position).getServiceId() != 0) {
            return TYPE_DATA;
        } else {
            return TYPE_LOAD;
        }
    }

    public void setMoreDataAvailable(boolean moreDataAvailable) {
        isMoreDataAvailable = moreDataAvailable;
    }

    /* notifyDataSetChanged is final method so we can't override it
         call adapter.notifyDataChanged(); after update the list
         */
    public void notifyDataChanged() {
        notifyDataSetChanged();
        isLoading = false;
    }

    public void setLoadMoreListener(OnLoadMoreListener loadMoreListener) {
        this.loadMoreListener = loadMoreListener;
    }

    /*
    For loading progress bar...
     */
    static class LoadHolder extends RecyclerView.ViewHolder {
        LoadHolder(View itemView) {
            super(itemView);
        }
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
        ImageView image, deleteproduct,mEnquiry;
        Button sviewdetails, vehidetails,mDetails,mEdit;
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
            viewdetails = (CardView) itemView.findViewById(R.id.card_view);
            //  viewdetails = (Button) itemView.findViewById(R.id.btnviewdetails);
            image = (ImageView) itemView.findViewById(R.id.profile);
            productrating = (RatingBar) itemView.findViewById(R.id.productrating);
            deleteproduct = (ImageView) itemView.findViewById(R.id.deleteproduct);
            mDetails = (Button) itemView.findViewById(R.id.btnView);
            mEdit = (Button) itemView.findViewById(R.id.btnEdit);
            mEnquiry = (ImageView) itemView.findViewById(R.id.enquiry);

        }
    }
}
