package autokatta.com.share;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import autokatta.com.R;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by ak-005 on 20/6/16.
 */

public class ShareWithCaptionAdapter extends BaseAdapter {

    private Activity activity;
    private String sharedata, contactnumber, profile_contact, keyword, contacttab;
    private int store_id, vehicle_id, product_id, search_id, status_id, auction_id, loan_id, exchange_id, service_id;
    private LayoutInflater mInflater;


    ShareWithCaptionAdapter(Activity activity, String contactnumber, String sharedata, int store_id, String keyword, int product_id,
                            int service_id, int vehicle_id, int search_id, int status_id, String profile_contact,
                            int auction_id, int loan_id, int exchange_id) {

        this.activity = activity;
        this.contactnumber = contactnumber;
        this.keyword = keyword;
        this.sharedata = sharedata;
        this.store_id = store_id;
        this.product_id = product_id;
        this.service_id = service_id;
        this.vehicle_id = vehicle_id;
        this.search_id = search_id;
        this.status_id = status_id;
        this.profile_contact = profile_contact;
        this.auction_id = auction_id;
        this.loan_id = loan_id;
        this.exchange_id = exchange_id;


        mInflater = (LayoutInflater) activity.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);


    }

    static class ViewHolder {
        TextView storename, storetype, location, website, time, workingday;
        RelativeLayout relative, relaprofilelike;
        LinearLayout relalike2, relatebutton;
        RatingBar storerating, productRating, serviceRating;
        ImageView dp, groupimage, storeimage, imgvehicle, imgproduct, imgservice, profileimage, storecallimg, Upimgvehicle;
        TextView profilefollowcnt, profilelikecnt, storefollowcnt, storelikecnt, vehiclelikecnt, Upvehiclelikecnt;
        TextView productname, producttype, productlikecnt;
        TextView servicename, servicetype, servicefollowcnt, servicelikecnt, servicesharecnt;
        TextView title, vprice, vbrand, vmodel, vyear, vkms, vregno, vrto, vlocation, vhrs;
        TextView category, brand, model, price, year, dateofsearch, searchleads;
        TextView poststatus;
        TextView aucnoofvehicles, aucenddate, auctitle, aucendtime, going, ignore, auctype;
        RelativeLayout relGoing, relIgnore;
        TextView username, profilelocation, profileworkat, profilewebsite;
        ImageView callimg, image;
        ImageView productrating1, productrating2, productrating3, productrating4, productrating5;
        ImageView servicerating1, servicerating2, servicerating3, servicerating4, servicerating5;


        TextView titleevent, start_date, start_time, end_date, end_time, eventlocation, auctioneer;
        RelativeLayout btnshare;
    }

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        View view = convertView;
        Log.i("Keyword", "->>>>>>>>>" + keyword);
        if (view == null) {
            if (keyword != null) {
                switch (keyword) {
                    case "profile":
                        view = mInflater.inflate(R.layout.adapter_share_profile_notification, null);
                        holder = new ViewHolder();
                        holder.username = (TextView) view.findViewById(R.id.username);
                        holder.profilelocation = (TextView) view.findViewById(R.id.profilelocation);
                        holder.profileworkat = (TextView) view.findViewById(R.id.profileworkat);
                        holder.profilewebsite = (TextView) view.findViewById(R.id.profilewebsite);
                        holder.profilefollowcnt = (TextView) view.findViewById(R.id.followcnt);
                        holder.profilelikecnt = (TextView) view.findViewById(R.id.like);
                        holder.profileimage = (ImageView) view.findViewById(R.id.imgnotilike);
                        holder.relaprofilelike = (RelativeLayout) view.findViewById(R.id.relaprofilelike);
                        holder.relalike2 = (LinearLayout) view.findViewById(R.id.relalike2);


                        break;

                    case "store":
                        view = mInflater.inflate(R.layout.adapter_share_store_notification, null);
                        holder = new ViewHolder();
                        holder.relative = (RelativeLayout) view.findViewById(R.id.relative);
                        holder.storeimage = (ImageView) view.findViewById(R.id.imgnotilike);
                        holder.storename = (TextView) view.findViewById(R.id.storename);
                        holder.storetype = (TextView) view.findViewById(R.id.storetype);
                        holder.location = (TextView) view.findViewById(R.id.location);
                        holder.website = (TextView) view.findViewById(R.id.website);
                        holder.time = (TextView) view.findViewById(R.id.timing);
                        holder.workingday = (TextView) view.findViewById(R.id.workday);
                        holder.storefollowcnt = (TextView) view.findViewById(R.id.followcnt);
                        holder.storelikecnt = (TextView) view.findViewById(R.id.like);
                        holder.storecallimg = (ImageView) view.findViewById(R.id.callimg);
                        holder.storerating = (RatingBar) view.findViewById(R.id.storerating);
                        holder.relalike2 = (LinearLayout) view.findViewById(R.id.relalike2);


                        break;

                    case "product":
                        view = mInflater.inflate(R.layout.adapter_share_product_notification, null);
                        holder = new ViewHolder();
                        holder.imgproduct = (ImageView) view.findViewById(R.id.imgnotilike);
                        holder.productname = (TextView) view.findViewById(R.id.productname);
                        holder.producttype = (TextView) view.findViewById(R.id.producttype);
                        holder.productlikecnt = (TextView) view.findViewById(R.id.likeprod);
                        holder.relative = (RelativeLayout) view.findViewById(R.id.relative);
                        holder.relalike2 = (LinearLayout) view.findViewById(R.id.relalike2);
                        holder.relalike2 = (LinearLayout) view.findViewById(R.id.relalike2);
                        holder.callimg = (ImageView) view.findViewById(R.id.callimg);
                        holder.productRating = (RatingBar) view.findViewById(R.id.product_rating);


                        break;

                    case "service":

                        view = mInflater.inflate(R.layout.adapter_share_service_notification, null);
                        holder = new ViewHolder();
                        holder.imgservice = (ImageView) view.findViewById(R.id.imgservice);
                        holder.servicename = (TextView) view.findViewById(R.id.servicename);
                        holder.servicetype = (TextView) view.findViewById(R.id.servicetype);
                        holder.servicefollowcnt = (TextView) view.findViewById(R.id.followcnt);
                        holder.servicelikecnt = (TextView) view.findViewById(R.id.likeservice);
                        holder.servicesharecnt = (TextView) view.findViewById(R.id.shareservice);
                        holder.relative = (RelativeLayout) view.findViewById(R.id.relative);
                        holder.relalike2 = (LinearLayout) view.findViewById(R.id.relalike2);
                        holder.callimg = (ImageView) view.findViewById(R.id.callimg);
                        holder.serviceRating = (RatingBar) view.findViewById(R.id.service_rating);
                        break;


                    case "vehicle":

                        view = mInflater.inflate(R.layout.adapter_share_vehicle_notification, null);
                        holder = new ViewHolder();
                        holder.imgvehicle = (ImageView) view.findViewById(R.id.imgvehicle);
                        holder.title = (TextView) view.findViewById(R.id.title);
                        holder.vprice = (TextView) view.findViewById(R.id.price);
                        holder.vbrand = (TextView) view.findViewById(R.id.brand);
                        holder.vmodel = (TextView) view.findViewById(R.id.model);
                        holder.vyear = (TextView) view.findViewById(R.id.year);
                        holder.vkms = (TextView) view.findViewById(R.id.km_hrs);
                        holder.vregno = (TextView) view.findViewById(R.id.registrationNo);
                        holder.vrto = (TextView) view.findViewById(R.id.RTO);
                        holder.vlocation = (TextView) view.findViewById(R.id.location);
                        holder.relative = (RelativeLayout) view.findViewById(R.id.relative);
                        holder.relalike2 = (LinearLayout) view.findViewById(R.id.relalike2);
                        holder.vehiclelikecnt = (TextView) view.findViewById(R.id.like);
                        holder.relative = (RelativeLayout) view.findViewById(R.id.relative);
                        holder.relalike2 = (LinearLayout) view.findViewById(R.id.relalike2);
                        holder.callimg = (ImageView) view.findViewById(R.id.callimg);

                        break;


                    case "mysearch":
                        view = mInflater.inflate(R.layout.adapter_share_search_notification, null);
                        holder = new ViewHolder();
                        holder.category = (TextView) view.findViewById(R.id.mysearch_category);
                        holder.brand = (TextView) view.findViewById(R.id.mysearch_brand);
                        holder.model = (TextView) view.findViewById(R.id.mysearch_model);
                        holder.price = (TextView) view.findViewById(R.id.mysearch_price);
                        holder.year = (TextView) view.findViewById(R.id.mysearch_year);
                        holder.dateofsearch = (TextView) view.findViewById(R.id.searchdate);
                        holder.searchleads = (TextView) view.findViewById(R.id.buyerleads);
                        holder.relaprofilelike = (RelativeLayout) view.findViewById(R.id.relaprofilelike);
                        holder.relalike2 = (LinearLayout) view.findViewById(R.id.relalike2);

                        break;

                    case "poststatus":

                        view = mInflater.inflate(R.layout.adapter_share_post_notification, null);
                        holder = new ViewHolder();
                        holder.poststatus = (TextView) view.findViewById(R.id.statustxt);
                        holder.relaprofilelike = (RelativeLayout) view.findViewById(R.id.relaprofilelike);
                        holder.relalike2 = (LinearLayout) view.findViewById(R.id.relalike2);

                        break;

                    case "uploadvehicle":

                        view = mInflater.inflate(R.layout.adapter_share_vehicle_notification, null);
                        holder = new ViewHolder();
                        holder.imgvehicle = (ImageView) view.findViewById(R.id.imgvehicle);
                        holder.title = (TextView) view.findViewById(R.id.title);
                        holder.vprice = (TextView) view.findViewById(R.id.price);
                        holder.vbrand = (TextView) view.findViewById(R.id.brand);
                        holder.vmodel = (TextView) view.findViewById(R.id.model);
                        holder.vyear = (TextView) view.findViewById(R.id.year);
                        holder.vkms = (TextView) view.findViewById(R.id.km_hrs);
                        holder.vregno = (TextView) view.findViewById(R.id.registrationNo);
                        holder.vrto = (TextView) view.findViewById(R.id.RTO);
                        holder.vlocation = (TextView) view.findViewById(R.id.location);
                        holder.relative = (RelativeLayout) view.findViewById(R.id.relative);
                        holder.relalike2 = (LinearLayout) view.findViewById(R.id.relalike2);
                        holder.callimg = (ImageView) view.findViewById(R.id.callimg);

                        holder.vehiclelikecnt = (TextView) view.findViewById(R.id.like);

                        break;


                    case "auction":
                        view = mInflater.inflate(R.layout.adapter_share_auction_notification, null);
                        holder = new ViewHolder();
                        holder.auctitle = (TextView) view.findViewById(R.id.auc_name);
                        holder.aucnoofvehicles = (TextView) view.findViewById(R.id.auc_noofvehicle);
                        holder.aucenddate = (TextView) view.findViewById(R.id.auc_enddate);
                        holder.aucendtime = (TextView) view.findViewById(R.id.auc_endtime);
                        holder.going = (TextView) view.findViewById(R.id.going_cnt);
                        holder.ignore = (TextView) view.findViewById(R.id.ignore_cnt);
                        holder.auctype = (TextView) view.findViewById(R.id.auc_type);
                        holder.relGoing = (RelativeLayout) view.findViewById(R.id.relgoing);
                        holder.relIgnore = (RelativeLayout) view.findViewById(R.id.relignore);
                        holder.relaprofilelike = (RelativeLayout) view.findViewById(R.id.relaprofilelike);
                        holder.relatebutton = (LinearLayout) view.findViewById(R.id.relatebutton);


                        break;

                    case "loan":
                        view = mInflater.inflate(R.layout.adapter_share_event_notification, null);
                        holder = new ViewHolder();
                        holder.titleevent = (TextView) view.findViewById(R.id.edittitle);
                        holder.start_date = (TextView) view.findViewById(R.id.start_date);
                        holder.start_time = (TextView) view.findViewById(R.id.start_time);
                        holder.end_date = (TextView) view.findViewById(R.id.datetime2);
                        holder.end_time = (TextView) view.findViewById(R.id.editText);
                        holder.eventlocation = (TextView) view.findViewById(R.id.editlocation);
                        holder.image = (ImageView) view.findViewById(R.id.image);
                        holder.auctioneer = (TextView) view.findViewById(R.id.ownertext);
                        holder.btnshare = (RelativeLayout) view.findViewById(R.id.btnrelative);

                        break;


                    case "exchange":
                        view = mInflater.inflate(R.layout.adapter_share_event_notification, null);
                        holder = new ViewHolder();
                        holder.titleevent = (TextView) view.findViewById(R.id.edittitle);
                        holder.start_date = (TextView) view.findViewById(R.id.start_date);
                        holder.start_time = (TextView) view.findViewById(R.id.start_time);
                        holder.end_date = (TextView) view.findViewById(R.id.datetime2);
                        holder.end_time = (TextView) view.findViewById(R.id.editText);
                        holder.eventlocation = (TextView) view.findViewById(R.id.editlocation);
                        holder.image = (ImageView) view.findViewById(R.id.image);
                        holder.auctioneer = (TextView) view.findViewById(R.id.ownertext);
                        holder.btnshare = (RelativeLayout) view.findViewById(R.id.btnrelative);

                        break;

                    default:
                        break;

                }
            }
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        System.out.println("sharedata============" + sharedata);

        switch (keyword) {

            case "profile":


                if (holder != null) {
                    String data10[] = sharedata.split("=");
                    holder.username.setText(data10[0]);
                    holder.profilelocation.setText(data10[3]);
                    holder.profileworkat.setText(data10[1]);
                    holder.profilewebsite.setText(data10[2]);

                    holder.profilefollowcnt.setText("Followers(" + data10[6] + ")");
                    holder.profilelikecnt.setText("Likes(" + data10[5] + ")");

                    holder.relaprofilelike.setVisibility(View.GONE);
                    holder.relalike2.setVisibility(View.GONE);


                    if (data10[4] == null || data10[4].equals(""))
                        holder.profileimage.setBackgroundResource(R.drawable.logo48x48);
                    else {
                        Glide.with(activity)
                                .load(activity.getString(R.string.base_image_url) + data10[4])
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .bitmapTransform(new CropCircleTransformation(activity))
                                .override(100, 100)
                                .into(holder.profileimage);
                    }
                }

                break;

            case "store":

                if (holder != null) {
                    String data[] = sharedata.split("=");
                    holder.storename.setText(data[0]);
                    holder.website.setText(data[1]);
                    holder.time.setText(data[2]);
                    holder.workingday.setText(data[3]);
                    holder.storetype.setText(data[4]);
                    holder.location.setText(data[5]);
                    holder.storefollowcnt.setText("Followers(" + data[9] + ")");
                    holder.storelikecnt.setText("Likes(" + data[8] + ")");
                    String rating = data[7];
                    holder.relative.setVisibility(View.GONE);
                    holder.relalike2.setVisibility(View.GONE);
                    holder.storecallimg.setVisibility(View.GONE);

                    if (rating != null && !rating.equals("null")) {
                        holder.storerating.setRating(Float.parseFloat(rating));
                    }

                    if (data[6] == null || data[6].equalsIgnoreCase("")) {
                        holder.storeimage.setBackgroundResource(R.drawable.logo48x48);

                    } else {
                        Glide.with(activity)
                                .load(activity.getString(R.string.base_image_url) + data[6])
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .bitmapTransform(new CropCircleTransformation(activity))
                                .override(100, 100)
                                .into(holder.storeimage);
                    }


                }

                break;


            case "product":

                if (holder != null) {
                    holder.relative.setVisibility(View.GONE);
                    holder.relalike2.setVisibility(View.GONE);
                    holder.callimg.setVisibility(View.INVISIBLE);

                    String data1[] = sharedata.split("=");

                    holder.productname.setText(data1[0]);
                    holder.producttype.setText(data1[1]);
                    holder.productlikecnt.setText("Like(" + data1[3] + ")");


                    if (data1[4] == null || data1[4].equals(""))
                        holder.imgproduct.setBackgroundResource(R.drawable.logo48x48);
                    else {
                        Glide.with(activity)
                                .load(activity.getString(R.string.base_image_url) + data1[4])
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .bitmapTransform(new CropCircleTransformation(activity))
                                .override(100, 100)
                                .into(holder.imgproduct);
                    }

                    if (data1[2] != null && !data1[2].equals("null")) {
                        holder.productRating.setRating(Float.parseFloat(data1[2]));
                    }
                }

                break;

            case "service":

                if (holder != null) {
                    holder.relative.setVisibility(View.GONE);
                    holder.relalike2.setVisibility(View.GONE);
                    holder.callimg.setVisibility(View.INVISIBLE);

                    String data2[] = sharedata.split("=");
                    holder.servicename.setText(data2[0]);
                    holder.servicetype.setText(data2[1]);
                    holder.servicelikecnt.setText("Like(" + data2[3] + ")");

                    if (data2[4] == null || data2[4].equals(""))
                        holder.imgservice.setBackgroundResource(R.drawable.logo48x48);
                    else {
                        Glide.with(activity)
                                .load(activity.getString(R.string.base_image_url) + data2[4])
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .bitmapTransform(new CropCircleTransformation(activity))
                                .override(100, 100)
                                .into(holder.imgservice);
                    }
                    if (data2[2] != null && !data2[2].equals("null")) {
                        holder.productRating.setRating(Float.parseFloat(data2[2]));
                    }

                }


                break;

            case "vehicle":

                if (holder != null) {
                    String data3[] = sharedata.split("=");

                    holder.title.setText(data3[0]);
                    holder.vprice.setText(data3[1]);
                    holder.vbrand.setText(data3[2]);
                    holder.vmodel.setText(data3[3]);
                    holder.vyear.setText(data3[4]);
                    holder.vehiclelikecnt.setText("Like(" + data3[10] + ")");
                    holder.vkms.setText(data3[5]);
                    holder.vregno.setText(data3[8]);
                    holder.vrto.setText(data3[6]);
                    holder.vlocation.setText(data3[7]);

                    holder.relative.setVisibility(View.GONE);
                    holder.relalike2.setVisibility(View.GONE);
                    holder.callimg.setVisibility(View.GONE);

                    System.out.println("imageeeee=========" + data3[9]);


                    if (data3[9] == null || data3[9].equals(""))
                        holder.imgvehicle.setBackgroundResource(R.drawable.logo48x48);

                    else {
                        Glide.with(activity)
                                .load(activity.getString(R.string.base_image_url) + data3[9])
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .bitmapTransform(new CropCircleTransformation(activity))
                                .override(100, 100)
                                .into(holder.imgvehicle);
                    }
                }

                break;


            case "mysearch":

                if (holder != null) {
                    String data4[] = sharedata.split("=");

                    holder.category.setText(data4[0]);
                    holder.brand.setText(data4[1]);
                    holder.model.setText(data4[2]);
                    holder.price.setText(data4[3]);
                    holder.year.setText(data4[4]);
                    holder.dateofsearch.setText(data4[5]);
                    holder.searchleads.setText(data4[6]);

                    holder.relaprofilelike.setVisibility(View.GONE);
                    holder.relalike2.setVisibility(View.GONE);
                }

                break;

            case "poststatus":

                if (holder != null) {
                    String data5[] = sharedata.split("=");

                    holder.poststatus.setText("\"" + data5[0] + "\"");
                    holder.relaprofilelike.setVisibility(View.GONE);
                    holder.relalike2.setVisibility(View.GONE);
                }

                break;

            case "uploadvehicle":
                if (holder != null) {
                    String data6[] = sharedata.split("=");

                    holder.title.setText(data6[0]);
                    holder.vprice.setText(data6[1]);
                    holder.vbrand.setText(data6[2]);
                    holder.vmodel.setText(data6[3]);
                    holder.vyear.setText(data6[4]);
                    holder.vehiclelikecnt.setText("Like(" + data6[10] + ")");

                    holder.vregno.setText(data6[8]);
                    holder.vrto.setText(data6[6]);
                    holder.vlocation.setText(data6[7]);
                    holder.vkms.setText(data6[5]);

                    holder.relative.setVisibility(View.GONE);
                    holder.relalike2.setVisibility(View.GONE);
                    holder.callimg.setVisibility(View.GONE);

                    if (data6[9] == null || data6[9].equals(""))
                        holder.imgvehicle.setBackgroundResource(R.drawable.logo48x48);
                    else {
                        Glide.with(activity)
                                .load(activity.getString(R.string.base_image_url) + data6[9])
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .bitmapTransform(new CropCircleTransformation(activity))
                                .override(100, 100)
                                .into(holder.imgvehicle);

                    }
                }

                break;

            case "auction":
                if (holder != null) {
                    String data7[] = sharedata.split("=");

                    holder.auctitle.setText(data7[0]);
                    holder.aucnoofvehicles.setText(data7[1]);
                    holder.aucenddate.setText(data7[2]);
                    holder.aucendtime.setText(data7[3]);
                    holder.auctype.setText(data7[4]);
                    holder.going.setText(data7[5]);
                    holder.ignore.setText(data7[6]);


                    holder.relaprofilelike.setVisibility(View.GONE);
                    holder.relatebutton.setVisibility(View.GONE);
                    holder.relGoing.setVisibility(View.GONE);
                    holder.relIgnore.setVisibility(View.GONE);


                    if (!data7[7].equals("myauction")) {
                        holder.relGoing.setVisibility(View.GONE);
                        holder.relIgnore.setVisibility(View.GONE);

                    }
                }

                break;


            case "loan":
                if (holder != null) {
                    String data8[] = sharedata.split("=");

                    holder.auctioneer.setText(data8[0]);
                    holder.titleevent.setText(data8[2]);
                    holder.eventlocation.setText(data8[3]);
                    holder.start_date.setText(data8[5]);
                    holder.start_time.setText(data8[6]);
                    holder.end_date.setText(data8[7]);
                    holder.end_time.setText(data8[8]);
                    holder.btnshare.setVisibility(View.GONE);


                    try {

                        if (!data8[9].equals("") || !data8[9].equals("null") || data8[9] != null) {

                            String imagename = activity.getString(R.string.base_image_url) + data8[9];

                            imagename = imagename.replaceAll(" ", "%20");

                            System.out.println("in list=======" + imagename);

                            try {

                                Glide.with(activity)
                                        .load(imagename)
                                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                                        .bitmapTransform(new CropCircleTransformation(activity))
                                        .into(holder.image);


                            } catch (Exception e) {
                                System.out.println("Error in uploading images");
                            }
                        } else
                            holder.image.setImageResource(R.drawable.logo48x48);

                    } catch (Exception e) {

                        e.printStackTrace();
                    }
                }

                break;


            case "exchange":

                if (holder != null) {
                    String data9[] = sharedata.split("=");

                    holder.auctioneer.setText(data9[0]);
                    holder.titleevent.setText(data9[2]);
                    holder.eventlocation.setText(data9[3]);
                    holder.start_date.setText(data9[5]);
                    holder.start_time.setText(data9[6]);
                    holder.end_date.setText(data9[7]);
                    holder.end_time.setText(data9[8]);


                    holder.btnshare.setVisibility(View.GONE);


                    try {
                        System.out.println(data9[9]);

                        if (!data9[9].equals("") || !data9[9].equals("null") || data9[9] != null) {

                            String imagename = activity.getString(R.string.base_image_url) + data9[9];

                            imagename = imagename.replaceAll(" ", "%20");

                            System.out.println("in list=======" + imagename);

                            try {

                                Glide.with(activity)
                                        .load(imagename)
                                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                                        .bitmapTransform(new CropCircleTransformation(activity))
                                        .into(holder.image);


                            } catch (Exception e) {
                                System.out.println("Error in uploading images");
                            }
                        } else if (data9[9].equals(""))

                        {
                            holder.image.setImageResource(R.drawable.exchangeimage);

                        }
                    } catch (Exception e) {

                        e.printStackTrace();
                    }
                }

                break;


            default:
                break;

        }

        return view;
    }
}
