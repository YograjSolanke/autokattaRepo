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
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.networkreceiver.ConnectionDetector;
import autokatta.com.other.CustomToast;
import autokatta.com.response.StoreInventoryResponse;
import autokatta.com.view.ProductViewActivity;
import jp.wasabeef.glide.transformations.CropSquareTransformation;
import retrofit2.Response;

//import autokatta.com.view.ProductViewActivity;

/**
 * Created by ak-004 on 17/4/17.
 */

public class StoreProductAdapter extends RecyclerView.Adapter<StoreProductAdapter.ProductHolder> implements RequestNotifier {
    Activity activity;
    List<StoreInventoryResponse.Success.Product> mMainList = new ArrayList<>();
    private String myContact, storeContact;
    ApiCall apiCall;
    private ConnectionDetector connectionDetector;
    StoreProductAdapter.ProductHolder mView;

    public StoreProductAdapter(Activity activity, List<StoreInventoryResponse.Success.Product> productList, String myContact,
                               String storeContact) {
        this.activity = activity;
        this.mMainList = productList;
        this.myContact = myContact;
        this.storeContact = storeContact;
        connectionDetector = new ConnectionDetector(activity);
        apiCall = new ApiCall(activity, this);
    }

    @Override
    public StoreProductAdapter.ProductHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.store_product_adapter, parent, false);
        return new ProductHolder(view);
    }

    @Override
    public void onBindViewHolder(final StoreProductAdapter.ProductHolder holder, int position) {
        mView = holder;
        List<String> images = new ArrayList<String>();
        final StoreInventoryResponse.Success.Product product = mMainList.get(position);
        holder.pname.setText(product.getName());
        holder.pprice.setText(product.getPrice());
        holder.pdetails.setText(product.getProductDetails());
        holder.ptags.setText(product.getProductTags());
        holder.ptype.setText(product.getProductType());
        holder.pCategoey.setText(product.getCategory());
        holder.productrating.setEnabled(false);


        if (myContact.equals(product.getStorecontact())) {
            holder.deleteproduct.setVisibility(View.VISIBLE);
        } else {
            holder.editdetails.setVisibility(View.GONE);
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
                System.out.println(activity.getString(R.string.base_image_url) + images.get(0));
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

        holder.viewdetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityOptions options = ActivityOptions.makeCustomAnimation(activity, R.anim.ok_left_to_right, R.anim.ok_right_to_left);
                int proId = product.getProductId();
                Intent intent = new Intent(activity, ProductViewActivity.class);
                intent.putExtra("product_id", proId);
                activity.startActivity(intent, options.toBundle());
            }
        });

        holder.editdetails.setOnClickListener(new View.OnClickListener() {
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
                                    mMainList.remove(holder.getAdapterPosition());
                                    notifyItemRemoved(holder.getAdapterPosition());
                                    notifyItemRangeChanged(holder.getAdapterPosition(), mMainList.size());
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
        ImageView image, deleteproduct;
        Button viewdetails, editdetails;
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
            viewdetails = (Button) itemView.findViewById(R.id.btnView);
            editdetails = (Button) itemView.findViewById(R.id.btnEdit);
            image = (ImageView) itemView.findViewById(R.id.profile);
            productrating = (RatingBar) itemView.findViewById(R.id.productrating);
            deleteproduct = (ImageView) itemView.findViewById(R.id.deleteproduct);
            mCardView = (CardView) itemView.findViewById(R.id.card_view);
        }
    }
}
