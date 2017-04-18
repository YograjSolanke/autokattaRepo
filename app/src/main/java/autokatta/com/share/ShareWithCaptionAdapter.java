package autokatta.com.share;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by ak-005 on 20/6/16.
 */

public class ShareWithCaptionAdapter extends BaseAdapter {

    private Activity activity;
    private String sharedata, store_id, contactnumber, vehicle_id, product_id,
            service_id, profile_contact, search_id, status_id, auction_id, loan_id, exchange_id, keyword, contacttab;

    private List<String> iname;
    private LayoutInflater mInflater;


    public ShareWithCaptionAdapter(Activity activity, String contactnumber, String sharedata, String store_id, String keyword, String product_id,
                                   String service_id, String vehicle_id, String search_id, String status_id, String profile_contact,
                                   String auction_id, String loan_id, String exchange_id) {

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
        ImageView starrating1, starrating2, starrating3, starrating4, starrating5;
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

        final ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();


            switch (keyword) {

                case "profile":
                    convertView = mInflater.inflate(R.layout.adapter_share_profile_notification, null);

                    holder.username = (TextView) convertView.findViewById(R.id.username);
                    holder.profilelocation = (TextView) convertView.findViewById(R.id.profilelocation);
                    holder.profileworkat = (TextView) convertView.findViewById(R.id.profileworkat);
                    holder.profilewebsite = (TextView) convertView.findViewById(R.id.profilewebsite);
                    holder.profilefollowcnt = (TextView) convertView.findViewById(R.id.followcnt);
                    holder.profilelikecnt = (TextView) convertView.findViewById(R.id.like);
                    holder.profileimage = (ImageView) convertView.findViewById(R.id.imgnotilike);
                    holder.relaprofilelike = (RelativeLayout) convertView.findViewById(R.id.relaprofilelike);
                    holder.relalike2 = (LinearLayout) convertView.findViewById(R.id.relalike2);


                    break;

                case "store":
                    convertView = mInflater.inflate(R.layout.adapter_share_store_notification, null);

                    holder.relative = (RelativeLayout) convertView.findViewById(R.id.relative);
                    holder.storeimage = (ImageView) convertView.findViewById(R.id.imgnotilike);
                    holder.storename = (TextView) convertView.findViewById(R.id.storename);
                    holder.storetype = (TextView) convertView.findViewById(R.id.storetype);
                    holder.location = (TextView) convertView.findViewById(R.id.location);
                    holder.website = (TextView) convertView.findViewById(R.id.website);
                    holder.time = (TextView) convertView.findViewById(R.id.timing);
                    holder.workingday = (TextView) convertView.findViewById(R.id.workday);
                    holder.storefollowcnt = (TextView) convertView.findViewById(R.id.followcnt);
                    holder.storelikecnt = (TextView) convertView.findViewById(R.id.like);
                    holder.storecallimg = (ImageView) convertView.findViewById(R.id.callimg);
                    holder.starrating1 = (ImageView) convertView.findViewById(R.id.starlike4);
                    holder.starrating2 = (ImageView) convertView.findViewById(R.id.starlike3);
                    holder.starrating3 = (ImageView) convertView.findViewById(R.id.starlike2);
                    holder.starrating4 = (ImageView) convertView.findViewById(R.id.starlike1);
                    holder.starrating5 = (ImageView) convertView.findViewById(R.id.starlike);
                    holder.relalike2 = (LinearLayout) convertView.findViewById(R.id.relalike2);


                    break;

                case "product":
                    convertView = mInflater.inflate(R.layout.adapter_share_product_notification, null);

                    holder.imgproduct = (ImageView) convertView.findViewById(R.id.imgnotilike);
                    holder.productname = (TextView) convertView.findViewById(R.id.productname);
                    holder.producttype = (TextView) convertView.findViewById(R.id.producttype);
                    holder.productlikecnt = (TextView) convertView.findViewById(R.id.likeprod);
                    holder.relative = (RelativeLayout) convertView.findViewById(R.id.relative);
                    holder.relalike2 = (LinearLayout) convertView.findViewById(R.id.relalike2);
                    holder.productrating1 = (ImageView) convertView.findViewById(R.id.starprod1);
                    holder.productrating2 = (ImageView) convertView.findViewById(R.id.starprod2);
                    holder.productrating3 = (ImageView) convertView.findViewById(R.id.starprod3);
                    holder.productrating4 = (ImageView) convertView.findViewById(R.id.starprod4);
                    holder.productrating5 = (ImageView) convertView.findViewById(R.id.starprod5);
                    holder.relalike2 = (LinearLayout) convertView.findViewById(R.id.relalike2);
                    holder.callimg = (ImageView) convertView.findViewById(R.id.callimg);


                    break;

                case "service":

                    convertView = mInflater.inflate(R.layout.adapter_share_service_notification, null);

                    holder.imgservice = (ImageView) convertView.findViewById(R.id.imgservice);
                    holder.servicename = (TextView) convertView.findViewById(R.id.servicename);
                    holder.servicetype = (TextView) convertView.findViewById(R.id.servicetype);
                    holder.servicefollowcnt = (TextView) convertView.findViewById(R.id.followcnt);
                    holder.servicelikecnt = (TextView) convertView.findViewById(R.id.likeservice);
                    holder.servicesharecnt = (TextView) convertView.findViewById(R.id.shareservice);
                    holder.relative = (RelativeLayout) convertView.findViewById(R.id.relative);
                    holder.relalike2 = (LinearLayout) convertView.findViewById(R.id.relalike2);
                    holder.servicerating1 = (ImageView) convertView.findViewById(R.id.starservice1);
                    holder.servicerating2 = (ImageView) convertView.findViewById(R.id.starservice2);
                    holder.servicerating3 = (ImageView) convertView.findViewById(R.id.starservice3);
                    holder.servicerating4 = (ImageView) convertView.findViewById(R.id.starservice4);
                    holder.servicerating5 = (ImageView) convertView.findViewById(R.id.starservice5);

                    holder.callimg = (ImageView) convertView.findViewById(R.id.callimg);
                    break;


                case "vehicle":

                    convertView = mInflater.inflate(R.layout.adapter_share_vehicle_notification, null);

                    holder.imgvehicle = (ImageView) convertView.findViewById(R.id.imgvehicle);
                    holder.title = (TextView) convertView.findViewById(R.id.title);
                    holder.vprice = (TextView) convertView.findViewById(R.id.price);
                    holder.vbrand = (TextView) convertView.findViewById(R.id.brand);
                    holder.vmodel = (TextView) convertView.findViewById(R.id.model);
                    holder.vyear = (TextView) convertView.findViewById(R.id.year);
                    holder.vkms = (TextView) convertView.findViewById(R.id.km_hrs);
                    holder.vregno = (TextView) convertView.findViewById(R.id.registrationNo);
                    holder.vrto = (TextView) convertView.findViewById(R.id.RTO);
                    holder.vlocation = (TextView) convertView.findViewById(R.id.location);
                    holder.relative = (RelativeLayout) convertView.findViewById(R.id.relative);
                    holder.relalike2 = (LinearLayout) convertView.findViewById(R.id.relalike2);
                    holder.vehiclelikecnt = (TextView) convertView.findViewById(R.id.like);
                    holder.relative = (RelativeLayout) convertView.findViewById(R.id.relative);
                    holder.relalike2 = (LinearLayout) convertView.findViewById(R.id.relalike2);
                    holder.callimg = (ImageView) convertView.findViewById(R.id.callimg);

                    break;


                case "mysearch":
                    convertView = mInflater.inflate(R.layout.searchnotification, null);

                    holder.category = (TextView) convertView.findViewById(R.id.mysearch_category);
                    holder.brand = (TextView) convertView.findViewById(R.id.mysearch_brand);
                    holder.model = (TextView) convertView.findViewById(R.id.mysearch_model);
                    holder.price = (TextView) convertView.findViewById(R.id.mysearch_price);

                    holder.year = (TextView) convertView.findViewById(R.id.mysearch_year);
                    holder.dateofsearch = (TextView) convertView.findViewById(R.id.searchdate);
                    holder.searchleads = (TextView) convertView.findViewById(R.id.buyerleads);
                    holder.relaprofilelike = (RelativeLayout) convertView.findViewById(R.id.relaprofilelike);
                    holder.relalike2 = (LinearLayout) convertView.findViewById(R.id.relalike2);

                    break;

                case "poststatus":

                    convertView = mInflater.inflate(R.layout.postnotification, null);

                    holder.poststatus = (TextView) convertView.findViewById(R.id.statustxt);
                    holder.relaprofilelike = (RelativeLayout) convertView.findViewById(R.id.relaprofilelike);
                    holder.relalike2 = (LinearLayout) convertView.findViewById(R.id.relalike2);

                    break;

                case "uploadvehicle":

                    convertView = mInflater.inflate(R.layout.adapter_share_vehicle_notification, null);

                    holder.imgvehicle = (ImageView) convertView.findViewById(R.id.imgvehicle);
                    holder.title = (TextView) convertView.findViewById(R.id.title);
                    holder.vprice = (TextView) convertView.findViewById(R.id.price);
                    holder.vbrand = (TextView) convertView.findViewById(R.id.brand);
                    holder.vmodel = (TextView) convertView.findViewById(R.id.model);
                    holder.vyear = (TextView) convertView.findViewById(R.id.year);
                    holder.vkms = (TextView) convertView.findViewById(R.id.km_hrs);
                    holder.vregno = (TextView) convertView.findViewById(R.id.registrationNo);
                    holder.vrto = (TextView) convertView.findViewById(R.id.RTO);
                    holder.vlocation = (TextView) convertView.findViewById(R.id.location);
                    holder.relative = (RelativeLayout) convertView.findViewById(R.id.relative);
                    holder.relalike2 = (LinearLayout) convertView.findViewById(R.id.relalike2);
                    holder.callimg = (ImageView) convertView.findViewById(R.id.callimg);

                    holder.vehiclelikecnt = (TextView) convertView.findViewById(R.id.like);

                    break;


                case "auction":
                    convertView = mInflater.inflate(R.layout.activenotification, null);

                    holder.auctitle = (TextView) convertView.findViewById(R.id.auc_name);
                    holder.aucnoofvehicles = (TextView) convertView.findViewById(R.id.auc_noofvehicle);
                    holder.aucenddate = (TextView) convertView.findViewById(R.id.auc_enddate);
                    holder.aucendtime = (TextView) convertView.findViewById(R.id.auc_endtime);
                    holder.going = (TextView) convertView.findViewById(R.id.going_cnt);
                    holder.ignore = (TextView) convertView.findViewById(R.id.ignore_cnt);
                    holder.auctype = (TextView) convertView.findViewById(R.id.auc_type);
                    holder.relGoing = (RelativeLayout) convertView.findViewById(R.id.relgoing);
                    holder.relIgnore = (RelativeLayout) convertView.findViewById(R.id.relignore);
                    holder.relaprofilelike = (RelativeLayout) convertView.findViewById(R.id.relaprofilelike);
                    holder.relatebutton = (LinearLayout) convertView.findViewById(R.id.relatebutton);


                    break;

                case "loan":
                    convertView = mInflater.inflate(R.layout.loan_hori_adapter, null);

                    holder.titleevent = (TextView) convertView.findViewById(R.id.edittitle);
                    holder.start_date = (TextView) convertView.findViewById(R.id.start_date);
                    holder.start_time = (TextView) convertView.findViewById(R.id.start_time);
                    holder.end_date = (TextView) convertView.findViewById(R.id.datetime2);
                    holder.end_time = (TextView) convertView.findViewById(R.id.editText);
                    holder.eventlocation = (TextView) convertView.findViewById(R.id.editlocation);
                    holder.image = (ImageView) convertView.findViewById(R.id.image);
                    holder.auctioneer = (TextView) convertView.findViewById(R.id.ownertext);
                    holder.btnshare = (RelativeLayout) convertView.findViewById(R.id.btnrelative);

                    break;


                case "exchange":
                    convertView = mInflater.inflate(R.layout.loan_hori_adapter, null);

                    holder.titleevent = (TextView) convertView.findViewById(R.id.edittitle);
                    holder.start_date = (TextView) convertView.findViewById(R.id.start_date);
                    holder.start_time = (TextView) convertView.findViewById(R.id.start_time);
                    holder.end_date = (TextView) convertView.findViewById(R.id.datetime2);
                    holder.end_time = (TextView) convertView.findViewById(R.id.editText);
                    holder.eventlocation = (TextView) convertView.findViewById(R.id.editlocation);
                    holder.image = (ImageView) convertView.findViewById(R.id.image);
                    holder.auctioneer = (TextView) convertView.findViewById(R.id.ownertext);
                    holder.btnshare = (RelativeLayout) convertView.findViewById(R.id.btnrelative);

                    break;

                default:
                    break;

            }

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();

        }
        iname = new ArrayList<String>();

        System.out.println("sharedata============" + sharedata);

        switch (keyword) {

            case "profile":

                String data10[] = sharedata.split("=");

                holder.username.setText(data10[0]);
                holder.profilelocation.setText(data10[3]);
                holder.profileworkat.setText(data10[1]);
                holder.profilewebsite.setText(data10[2]);

                holder.profilefollowcnt.setText("Followers(" + data10[6] + ")");
                holder.profilelikecnt.setText("Likes(" + data10[5] + ")");

                holder.relaprofilelike.setVisibility(View.GONE);
                holder.relalike2.setVisibility(View.GONE);


                if (data10[4] == null || data10[4].equals("")) {
                    holder.profileimage.setBackgroundResource(R.drawable.profile);
                }
                if (data10[4] != null || !data10[4].equals("")) {

                    Glide.with(activity)
                            .load("http://autokatta.com/mobile/profile_profile_pics/" + data10[4])
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .bitmapTransform(new CropCircleTransformation(activity))
                            .override(100, 100)
                            .into(holder.profileimage);
                }


                break;

            case "store":

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

                if (rating.equals("null")) {
                } else if (!rating.equals("null")) {
                    // System.out.println("storeratingcount_list.get(position).toString()"+obj.storeratingld.toString()+obj.storeratingld);

                    //  Float floatrate= Float.parseFloat(rating.toString());// Float.valueOf(storeratingcount_list.get(position).toString());

                    if (Float.parseFloat(rating) >= 4.5) {
                        holder.starrating1.setBackgroundResource(R.drawable.ratestar1);
                        holder.starrating2.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.ratestar1));
                        holder.starrating3.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.ratestar1));
                        holder.starrating4.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.ratestar1));
                        holder.starrating5.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.ratestar1));
                    }
                    if (Float.parseFloat(rating) <= 4.5 && Float.parseFloat(rating) >= 3.5) {
                        holder.starrating2.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.ratestar1));
                        holder.starrating3.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.ratestar1));
                        holder.starrating4.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.ratestar1));
                        holder.starrating5.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.ratestar1));
                    }
                    if (Float.parseFloat(rating) <= 3.5 && Float.parseFloat(rating) >= 2.5) {
                        holder.starrating3.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.ratestar1));
                        holder.starrating4.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.ratestar1));
                        holder.starrating5.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.ratestar1));
                    }
                    if (Float.parseFloat(rating) <= 2.5 && Float.parseFloat(rating) >= 1.5) {
                        holder.starrating4.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.ratestar1));
                        holder.starrating5.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.ratestar1));
                    }
                    if (Float.parseFloat(rating) <= 1.5 && Float.parseFloat(rating) >= 0.5) {
                        holder.starrating5.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.ratestar1));
                    }

                }

                try {
                    Glide.with(activity)
                            .load("http://autokatta.com/mobile/store_profiles/" + data[6])
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .bitmapTransform(new CropCircleTransformation(activity))
                            .override(100, 100)
                            .into(holder.storeimage);
                } catch (Exception e) {
                    System.out.println("Error in uploading images");
                }


                break;


            case "product":

                holder.relative.setVisibility(View.GONE);
                holder.relalike2.setVisibility(View.GONE);
                holder.callimg.setVisibility(View.INVISIBLE);

                String data1[] = sharedata.split("=");

                holder.productname.setText(data1[0]);
                holder.producttype.setText(data1[1]);
                holder.productlikecnt.setText("Like(" + data1[3] + ")");


                try {
                    Glide.with(activity)
                            .load("http://autokatta.com/mobile/Product_pics/" + data1[4])
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .bitmapTransform(new CropCircleTransformation(activity))
                            .override(100, 100)
                            .into(holder.imgproduct);

                } catch (Exception e) {
                    System.out.println("Error in uploading images");
                }

                if (!data1[2].equals("null")) {

                    Float f = Float.parseFloat(data1[2]);
                    if (f > 4.5) {
                        holder.productrating1.setImageResource(R.drawable.ratestar1);
                        holder.productrating2.setImageResource(R.drawable.ratestar1);
                        holder.productrating3.setImageResource(R.drawable.ratestar1);
                        holder.productrating4.setImageResource(R.drawable.ratestar1);
                        holder.productrating5.setImageResource(R.drawable.ratestar1);
                    } else if (f > 3.5 && f <= 4.5) {
                        holder.productrating1.setImageResource(R.drawable.ratestar1);
                        holder.productrating2.setImageResource(R.drawable.ratestar1);
                        holder.productrating3.setImageResource(R.drawable.ratestar1);
                        holder.productrating4.setImageResource(R.drawable.ratestar1);
                    } else if (f > 2.5 && f <= 3.5) {
                        holder.productrating1.setImageResource(R.drawable.ratestar1);
                        holder.productrating2.setImageResource(R.drawable.ratestar1);
                        holder.productrating3.setImageResource(R.drawable.ratestar1);
                    } else if (f > 1.5 && f <= 2.5) {
                        holder.productrating1.setImageResource(R.drawable.ratestar1);
                        holder.productrating2.setImageResource(R.drawable.ratestar1);
                    } else if (f <= 1.5 && f > 0.5) {
                        holder.productrating1.setImageResource(R.drawable.ratestar1);

                    } else if (f <= 0.5) {

                    }

                } else {

                }


                break;

            case "service":

                holder.relative.setVisibility(View.GONE);
                holder.relalike2.setVisibility(View.GONE);
                holder.callimg.setVisibility(View.INVISIBLE);

                String data2[] = sharedata.split("=");
                holder.servicename.setText(data2[0]);
                holder.servicetype.setText(data2[1]);
                holder.servicelikecnt.setText("Like(" + data2[3] + ")");
                try {
                    Glide.with(activity)
                            .load("http://autokatta.com/mobile/Service_pics/" + data2[4])
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .bitmapTransform(new CropCircleTransformation(activity))
                            .override(100, 100)
                            .into(holder.imgservice);

                } catch (Exception e) {
                    System.out.println("Error in uploading images");
                }

                if (!data2[2].equals("null")) {

                    Float f = Float.parseFloat(data2[2]);
                    if (f > 4.5) {
                        holder.servicerating1.setImageResource(R.drawable.ratestar1);
                        holder.servicerating2.setImageResource(R.drawable.ratestar1);
                        holder.servicerating3.setImageResource(R.drawable.ratestar1);
                        holder.servicerating4.setImageResource(R.drawable.ratestar1);
                        holder.servicerating5.setImageResource(R.drawable.ratestar1);
                    } else if (f > 3.5 && f <= 4.5) {
                        holder.servicerating1.setImageResource(R.drawable.ratestar1);
                        holder.servicerating2.setImageResource(R.drawable.ratestar1);
                        holder.servicerating3.setImageResource(R.drawable.ratestar1);
                        holder.servicerating4.setImageResource(R.drawable.ratestar1);
                    } else if (f > 2.5 && f <= 3.5) {
                        holder.servicerating1.setImageResource(R.drawable.ratestar1);
                        holder.servicerating2.setImageResource(R.drawable.ratestar1);
                        holder.servicerating3.setImageResource(R.drawable.ratestar1);
                    } else if (f > 1.5 && f <= 2.5) {
                        holder.servicerating1.setImageResource(R.drawable.ratestar1);
                        holder.servicerating2.setImageResource(R.drawable.ratestar1);
                    } else if (f <= 1.5 && f > 0.5) {
                        holder.servicerating1.setImageResource(R.drawable.ratestar1);

                    } else if (f <= 0.5) {

                    }

                } else {

                }


                break;

            case "vehicle":

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


                if (data3[9] == null || data3[9].equals("")) {
                    holder.imgvehicle.setBackgroundResource(R.drawable.vehiimg);

                }
                if (data3[9] != null || !data3[9].equals(""))
                    try {

                        Glide.with(activity)
                                .load("http://autokatta.com/mobile/uploads/" + data3[9])
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .bitmapTransform(new CropCircleTransformation(activity))
                                .override(100, 100)
                                .into(holder.imgvehicle);

                    } catch (Exception e) {
                        System.out.println("Error in uploading images");
                    }

                break;


            case "mysearch":

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

                break;

            case "poststatus":

                String data5[] = sharedata.split("=");

                holder.poststatus.setText("\"" + data5[0] + "\"");
                holder.relaprofilelike.setVisibility(View.GONE);
                holder.relalike2.setVisibility(View.GONE);

                break;

            case "uploadvehicle":

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

                if (data6[9] == null || data6[9].equals("")) {
                    holder.imgvehicle.setBackgroundResource(R.drawable.store);

                }
                if (data6[9] != null || !data6[9].equals(""))
                    try {
                        Glide.with(activity)
                                .load("http://autokatta.com/mobile/uploads/" + data6[9])
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .bitmapTransform(new CropCircleTransformation(activity))
                                .override(100, 100)
                                .into(holder.imgvehicle);

                    } catch (Exception e) {
                        System.out.println("Error in uploading images");
                    }

                break;

            case "auction":

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

                break;


            case "loan":

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

                    if (!data8[9].equals("") || !data8[9].equals("null") || !data8[9].equals(null)) {

                        String imagename = "http://autokatta.com/mobile/loan_exchange_events_pics/" + data8[9];

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
                    } else if (data8[9].equals(""))

                    {
                        holder.image.setImageResource(R.drawable.lonemelaimage);

                    }
                } catch (Exception e) {

                    e.printStackTrace();
                }

                break;


            case "exchange":

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

                    if (!data9[9].equals("") || !data9[9].equals("null") || !data9[9].equals(null)) {

                        String imagename = "http://autokatta.com/mobile/loan_exchange_events_pics/" + data9[9];

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

                break;


            default:
                break;

        }

        return convertView;
    }
}
