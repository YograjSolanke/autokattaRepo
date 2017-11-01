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

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
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
import jp.wasabeef.glide.transformations.CropSquareTransformation;
import retrofit2.Response;

/**
 * Created by ak-003 on 10/7/17.
 */

public class GroupProductAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements RequestNotifier {
    Activity activity;
    List<StoreInventoryResponse.Success.Product> mMainList = new ArrayList<>();
    private String myContact, storeContact;
    ApiCall apiCall;
    private ConnectionDetector connectionDetector;
    GroupProductAdapter.ProductHolder mView;
    private String mGroupType;

    private OnLoadMoreListener loadMoreListener;
    final int TYPE_DATA = 0;
    private final int TYPE_LOAD = 1;
    private boolean isLoading = false, isMoreDataAvailable = true;

    public GroupProductAdapter(Activity activity, List<StoreInventoryResponse.Success.Product> productList, String myContact,
                               String storeContact, String mGroupType) {
        this.activity = activity;
        this.mMainList = productList;
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
            return new ProductHolder(inflater.inflate(R.layout.store_product_adapter, parent, false));
        } else {
            return new LoadHolder(inflater.inflate(R.layout.row_load, parent, false));
        }
        /*View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.store_product_adapter, parent, false);
        return new ProductHolder(view);*/
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder1, final int position) {
        if (position >= getItemCount() - 1 && isMoreDataAvailable && !isLoading && loadMoreListener != null) {
            isLoading = true;
            loadMoreListener.onLoadMore();
        }

        if (getItemViewType(position) == TYPE_DATA) {
            final ProductHolder holder = (ProductHolder) holder1;
            mView = holder;
            holder.mEnquiry.setVisibility(View.GONE);

            List<String> images = new ArrayList<String>();
            final StoreInventoryResponse.Success.Product product = mMainList.get(position);
            holder.pname.setText(product.getName());
            holder.pprice.setText(product.getPrice());
            holder.pdetails.setText(product.getProductDetails());
            holder.ptags.setText(product.getProductTags());
            holder.ptype.setText(product.getProductType());
            holder.pCategoey.setText(product.getCategory());
            holder.productrating.setEnabled(false);

            if (myContact.equals(product.getStorecontact()) && mGroupType.startsWith("MyGroup")) {
                holder.deleteproduct.setVisibility(View.VISIBLE);
            } else {
                holder.mEdit.setVisibility(View.GONE);
            }

            holder.pname.setEnabled(false);
            holder.pprice.setEnabled(false);
            holder.pdetails.setEnabled(false);
            holder.ptags.setEnabled(false);
            holder.ptype.setEnabled(false);
            holder.pCategoey.setEnabled(false);

            try {
                if (product.getProductImage() == null) {
                    holder.image.setBackgroundResource(R.drawable.logo);
                } else {
                    String[] parts = product.getProductImage().split(",");
                    for (int l = 0; l < parts.length; l++) {
                        images.add(parts[l]);
                        System.out.println(parts[l]);
                    }
                    String pimagename = activity.getString(R.string.base_image_url) + images.get(0);
                    pimagename = pimagename.replaceAll(" ", "%20");
                    try {
                        Glide.with(activity)
                                .load(pimagename)
                                .bitmapTransform(new CropSquareTransformation(activity))
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
            if (product.getProductrating() != null) {
                holder.productrating.setRating(Float.parseFloat(product.getProductrating()));
            } else {

            }

            holder.mDetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ActivityOptions options = ActivityOptions.makeCustomAnimation(activity, R.anim.ok_left_to_right, R.anim.ok_right_to_left);
                    int proId = product.getProductId();
                    Intent intent = new Intent(activity, ProductViewActivity.class);
                    intent.putExtra("product_id", proId);
                    activity.startActivity(intent, options.toBundle());
                }
            });

            holder.mEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ActivityOptions options = ActivityOptions.makeCustomAnimation(activity, R.anim.ok_left_to_right, R.anim.ok_right_to_left);
                    int proId = product.getProductId();
                    Intent intent = new Intent(activity, ProductViewActivity.class);
                    intent.putExtra("product_id", proId);
                    intent.putExtra("editmode", "yes");
                    activity.startActivity(intent, options.toBundle());
                }
            });
        /*
        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityOptions options = ActivityOptions.makeCustomAnimation(activity, R.anim.ok_left_to_right, R.anim.ok_right_to_left);
                int proId = product.getProductId();
                Intent intent = new Intent(activity, ProductViewActivity.class);
                intent.putExtra("product_id", proId);
                activity.startActivity(intent, options.toBundle());
            }
        });
*/
            holder.deleteproduct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final int product_id = product.getProductId();
                    if (!connectionDetector.isConnectedToInternet()) {
                        CustomToast.customToast(activity, "Please try later");
                        // Toast.makeText(activity, "Please try later", Toast.LENGTH_SHORT).show();
                    } else {
                        new android.support.v7.app.AlertDialog.Builder(activity)
                                .setTitle("Delete?")
                                .setMessage("Are You Sure You Want To Delete This Product?")
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        apiCall.deleteProduct(product_id, "delete");
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
        if (mMainList.get(position).getProductId() != 0) {
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
        } else if (error instanceof ConnectException) {
            CustomToast.customToast(activity, activity.getString(R.string.no_internet));
        } else if (error instanceof UnknownHostException) {
            CustomToast.customToast(activity, activity.getString(R.string.no_internet));
        } else {
            Log.i("Check Class-"
                    , "StoreProductAdaper");
            error.printStackTrace();
        }
    }

    @Override
    public void notifyString(String str) {
        if (str != null) {
            if (str.equals("success")) {
                CustomToast.customToast(activity, "Product Deleted");

            }
        }
    }

    class ProductHolder extends RecyclerView.ViewHolder {
        TextView pname, pprice, pdetails, ptype, ptags, pCategoey;
        ImageView image, deleteproduct,mEnquiry;
        Button viewdetails, sviewdetails, vehidetails,mDetails,mEdit;
        RatingBar productrating;
        CardView mCardView;

        ProductHolder(View itemView) {
            super(itemView);
            pname = (TextView) itemView.findViewById(R.id.edittxt);
            pprice = (TextView) itemView.findViewById(R.id.priceedit);
            pdetails = (TextView) itemView.findViewById(R.id.editdetails);
            ptags = (TextView) itemView.findViewById(R.id.edittags);
            pCategoey = (TextView) itemView.findViewById(R.id.editCategory);
            ptype = (TextView) itemView.findViewById(R.id.editproducttype);
            //viewdetails = (Button) itemView.findViewById(R.id.btnviewdetails);
            mDetails = (Button) itemView.findViewById(R.id.btnView);
            mEdit = (Button) itemView.findViewById(R.id.btnEdit);
            image = (ImageView) itemView.findViewById(R.id.profile);
            productrating = (RatingBar) itemView.findViewById(R.id.productrating);
            deleteproduct = (ImageView) itemView.findViewById(R.id.deleteproduct);
            mEnquiry = (ImageView) itemView.findViewById(R.id.enquiry);
            mCardView = (CardView) itemView.findViewById(R.id.card_view);
        }
    }
}
