package autokatta.com.adapter;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

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
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import retrofit2.Response;

/**
 * Created by ak-003 on 24/4/17.
 */

public class GroupProductsAdapter extends RecyclerView.Adapter<GroupProductsAdapter.ProductHolder> implements RequestNotifier {

    Activity activity;
    private List<StoreInventoryResponse.Success.Product> mMainList = new ArrayList<>();
    String myContact, storeContact;
    ApiCall apiCall;
    private String pimagename = "";
    private ConnectionDetector connectionDetector;

    public GroupProductsAdapter(Activity activity, List<StoreInventoryResponse.Success.Product> productList, String myContact) {
        this.activity = activity;
        this.mMainList = productList;
        this.myContact = myContact;
        //this.storeContact = storeContact;
        connectionDetector = new ConnectionDetector(activity);
        apiCall = new ApiCall(activity, this);


    }

    @Override
    public GroupProductsAdapter.ProductHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.store_product_adapter, parent, false);
        return new ProductHolder(view);
    }

    @Override
    public void onBindViewHolder(final GroupProductsAdapter.ProductHolder holder, int position) {

        ArrayList<String> images = new ArrayList<String>();

        final StoreInventoryResponse.Success.Product product = mMainList.get(position);

        holder.pname.setText(product.getName());

        holder.pprice.setText(product.getPrice());

        holder.pdetails.setText(product.getProductDetails());

        holder.ptags.setText(product.getProductTags());

        holder.ptype.setText(product.getProductType());
        holder.productrating.setEnabled(false);

        /*if (myContact.equals(storeContact)) {
            holder.deleteproduct.setVisibility(View.VISIBLE);
        }*/


        holder.pname.setEnabled(false);
        holder.pprice.setEnabled(false);
        holder.pdetails.setEnabled(false);
        holder.ptags.setEnabled(false);
        holder.ptype.setEnabled(false);


        try {

            if (product.getProductImage().equals("") || product.getProductImage().equals("null") ||
                    product.getProductImage().equals("")) {

                holder.image.setBackgroundResource(R.drawable.store);
            } else {
                String[] parts = product.getProductImage().split(",");

                for (int l = 0; l < parts.length; l++) {
                    images.add(parts[l]);
                    System.out.println(parts[l]);
                }
                System.out.println("http://autokatta.com/mobile/Product_pics/" + images.get(0));

                pimagename = "http://autokatta.com/mobile/Product_pics/" + images.get(0);
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
        if (!product.getProductrating().equals("null")) {

            holder.productrating.setRating(Float.parseFloat(product.getProductrating()));
        } else {

        }


        holder.viewdetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Bundle b = new Bundle();
//                b.putString("name", name.get(position).toString());
//                b.putString("pid", id.get(position).toString());
//
//                b.putString("price", price.get(position).toString());
//                b.putString("details", details.get(position).toString());
//                b.putString("tags", tags.get(position).toString());
//                b.putString("type", type.get(position).toString());
//                b.putString("likestatus", plikestatus.get(position).toString());
//                b.putString("images", image.get(position).toString());
//                b.putString("category", category.get(position).toString());
//                b.putString("plikecnt", plike.get(position).toString());
//                b.putString("prating", prating.get(position).toString());
//                b.putString("prate", prate.get(position).toString());
//                b.putString("prate1", prate1.get(position).toString());
//                b.putString("prate2", prate2.get(position).toString());
//                b.putString("prate3", prate3.get(position).toString());
//                b.putString("store_id", store_id);
//                b.putString("storecontact", storecontact);
//                b.putString("storename", storename);
//                b.putString("storewebsite", storewebsite);
//                b.putString("storerating", storerating);
//                b.putString("brandtags_list", brandtags_list.get(position).toString());
//
//
//                ProductViewActivity frag = new ProductViewActivity();
//                frag.setArguments(b);
//
//                FragmentManager fragmentManager = ctx.getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.containerView, frag);
//                fragmentTransaction.addToBackStack("product_view");
//                fragmentTransaction.commit();

            }
        });

        holder.deleteproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String product_id = product.getProductId();


                if (!connectionDetector.isConnectedToInternet()) {
                    Toast.makeText(activity, "Please try later", Toast.LENGTH_SHORT).show();

                } else {

                    new android.support.v7.app.AlertDialog.Builder(activity)
                            .setTitle("Delete?")
                            .setMessage("Are You Sure You Want To Delete This Product?")

                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                    apiCall.deleteProduct(product_id, "delete");
                                    mMainList.remove(holder.getAdapterPosition());
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
        TextView pname, pprice, pdetails, ptype, ptags;
        ImageView image, deleteproduct;
        Button viewdetails, sviewdetails, vehidetails;
        RatingBar productrating;

        ProductHolder(View itemView) {
            super(itemView);


            pname = (TextView) itemView.findViewById(R.id.edittxt);
            pprice = (TextView) itemView.findViewById(R.id.priceedit);
            pdetails = (TextView) itemView.findViewById(R.id.editdetails);
            ptags = (TextView) itemView.findViewById(R.id.edittags);
            ptype = (TextView) itemView.findViewById(R.id.editproducttype);
            viewdetails = (Button) itemView.findViewById(R.id.btnviewdetails);
            image = (ImageView) itemView.findViewById(R.id.profile);
            productrating = (RatingBar) itemView.findViewById(R.id.productrating);
            deleteproduct = (ImageView) itemView.findViewById(R.id.deleteproduct);
        }
    }
}
