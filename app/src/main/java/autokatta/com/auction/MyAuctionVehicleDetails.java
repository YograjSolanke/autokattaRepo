package autokatta.com.auction;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.HashMap;

import autokatta.com.R;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import autokatta.com.response.GetVehicleForAuctionResponse;
import retrofit2.Response;

public class MyAuctionVehicleDetails extends AppCompatActivity implements RequestNotifier {

    String str1, contactnumber, auction_id, vehicle_id;

    private HashMap<TextView, CountDownTimer> counters = new HashMap<TextView, CountDownTimer>();
    Handler handler;
    CountDownTimer cdt;

    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

    //String aauction_id, action_title, auctioncontact, start_date, start_time, end_date, end_time, auction_type, location, special_clauses, no_of_vehicles, endDateTime, highbid;

    //String vvehicle_id, title, contact_no, category, sub_category, model, manufacturer, Version, rto_city, location_city, year_of_registration, year_of_manufacture, colorr, registration_number, rc_available, kms_running, Hrs_running, no_of_owners, fual_type, seating_capacity, hypothication, engine_no, chassis_no, price, image, body_type, boat_type, bus_type, implementsd, finance_req, hp_capacity, JIB, Boon, tyre_condition, insurance_valid, insurance_idv, fitness_validity, permit_validity, air_condition, rv_type, transmission, tax_validity, application, permit_yesno, drive, lotNostr;

    //String CurrentBid_price;


    TextView vehiclemodel, vehicletitle, vehiclebrand, vehicleyear, vehicle_km_hrs, vehiclerc_invoice, vehicle_rto_city, vehicle_location, current_bid, moredetail, startpricetxt, startprice, lotNo;

    TextView categorydetails, regyeardetails, insurencedetails, ownerdetails, rc_availabledetails, ins_validitydetails, tax_validitydetails, fitness_validitydetails, permit_validitydetails, fueldetails, seatingdetails, hypodetails, permitdetails, drivedetails, transmissiondetails, bodydetails, boatdetails, rvdetails, applicationdetails, colordetails, tyredetails, bustypedetails, airdetails, registrationdetails, enginedetails, implementationdetails, chassicdetails, hpcapacitydetails, jibdetails, boondetails;

    ImageView vehicleImage;


    View vhapcapacity, vimplement, vair, vbus, vapp, vboat, vbody, vtrans, vdrive, vseat, vboon, vjib, vrv, vfuel, vhypo, vtyre, vengine, vchassic;

    TextView hpcapacitytxt, jibtxt, boontxt, bustext, airtext, seatcaptxt, implimentstext, applicationtext, transmissiontext, drivetext, bodytext, boattext, rvtext;
    TextView cameracount;

    String sendImage = "", Allimages = "";
    ArrayList<String> iname = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_auction_vehicle_details);

        contactnumber = getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", "");

        vehicletitle = (TextView) findViewById(R.id.vehicletitle);
        vehiclemodel = (TextView) findViewById(R.id.vehiclemodel);
        vehiclebrand = (TextView) findViewById(R.id.vehiclebrand);
        vehicleyear = (TextView) findViewById(R.id.vehicleyear);
        vehicle_km_hrs = (TextView) findViewById(R.id.vehicle_km_hrs);
        vehiclerc_invoice = (TextView) findViewById(R.id.vehiclerc_invoice);
        vehicle_rto_city = (TextView) findViewById(R.id.vehicle_rto_city);
        vehicle_location = (TextView) findViewById(R.id.vehicle_location);
        current_bid = (TextView) findViewById(R.id.current_bid);
        moredetail = (TextView) findViewById(R.id.moredetail);
        startpricetxt = (TextView) findViewById(R.id.startpricetxt);
        startprice = (TextView) findViewById(R.id.startprice);
        vehicleImage = (ImageView) findViewById(R.id.auctionimage);
        lotNo = (TextView) findViewById(R.id.setlotno);

        categorydetails = (TextView) findViewById(R.id.categorydetails);
        regyeardetails = (TextView) findViewById(R.id.regyeardetails);
        insurencedetails = (TextView) findViewById(R.id.insurencedetails);
        ownerdetails = (TextView) findViewById(R.id.ownerdetails);
        rc_availabledetails = (TextView) findViewById(R.id.rc_availabledetails);
        ins_validitydetails = (TextView) findViewById(R.id.ins_validitydetails);
        tax_validitydetails = (TextView) findViewById(R.id.tax_validitydetails);
        fitness_validitydetails = (TextView) findViewById(R.id.fitness_validitydetails);
        permit_validitydetails = (TextView) findViewById(R.id.permit_validitydetails);
        fueldetails = (TextView) findViewById(R.id.fueldetails);
        seatingdetails = (TextView) findViewById(R.id.seatingdetails);
        hypodetails = (TextView) findViewById(R.id.hypodetails);
        permitdetails = (TextView) findViewById(R.id.permitdetails);
        drivedetails = (TextView) findViewById(R.id.drivedetails);
        transmissiondetails = (TextView) findViewById(R.id.transmissiondetails);
        bodydetails = (TextView) findViewById(R.id.bodydetails);
        rvdetails = (TextView) findViewById(R.id.rvdetails);
        applicationdetails = (TextView) findViewById(R.id.applicationdetails);
        colordetails = (TextView) findViewById(R.id.colordetails);
        tyredetails = (TextView) findViewById(R.id.tyredetails);
        bustypedetails = (TextView) findViewById(R.id.bustypedetails);
        airdetails = (TextView) findViewById(R.id.airdetails);
        registrationdetails = (TextView) findViewById(R.id.registrationdetails);
        enginedetails = (TextView) findViewById(R.id.enginedetails);
        implementationdetails = (TextView) findViewById(R.id.implementationdetails);
        chassicdetails = (TextView) findViewById(R.id.chassicdetails);
        hpcapacitydetails = (TextView) findViewById(R.id.hpcapacitydetails);
        jibdetails = (TextView) findViewById(R.id.jibdetails);
        boondetails = (TextView) findViewById(R.id.boondetails);
        boatdetails = (TextView) findViewById(R.id.boatdetails);

        hpcapacitytxt = (TextView) findViewById(R.id.hpcapacitytxt);
        jibtxt = (TextView) findViewById(R.id.jibtxt);
        boontxt = (TextView) findViewById(R.id.boontxt);
        bustext = (TextView) findViewById(R.id.bustext);
        airtext = (TextView) findViewById(R.id.airtext);
        seatcaptxt = (TextView) findViewById(R.id.seatcaptxt);
        implimentstext = (TextView) findViewById(R.id.implimentstext);
        applicationtext = (TextView) findViewById(R.id.applicationtext);
        transmissiontext = (TextView) findViewById(R.id.transmissiontext);
        drivetext = (TextView) findViewById(R.id.drivetext);
        bodytext = (TextView) findViewById(R.id.bodytext);
        boattext = (TextView) findViewById(R.id.boattext);

        vhapcapacity = (View) findViewById(R.id.vhapcapacity);
        vimplement = (View) findViewById(R.id.vimplement);
        vair = (View) findViewById(R.id.vair);
        vbus = (View) findViewById(R.id.vbus);
        vapp = (View) findViewById(R.id.vapp);
        vboat = (View) findViewById(R.id.vboat);
        vbody = (View) findViewById(R.id.vbody);
        vtrans = (View) findViewById(R.id.vtrans);
        vdrive = (View) findViewById(R.id.vdrive);
        vseat = (View) findViewById(R.id.vseat);
        vboon = (View) findViewById(R.id.vboon);
        vjib = (View) findViewById(R.id.vjib);
        vfuel = (View) findViewById(R.id.vfuel);
        vhypo = (View) findViewById(R.id.vhypo);
        vtyre = (View) findViewById(R.id.vtyre);
        vengine = (View) findViewById(R.id.vengine);
        vchassic = (View) findViewById(R.id.vchassic);
        vrv = (View) findViewById(R.id.vrv);

        vehicle_id = getIntent().getExtras().getString("vehicle_id");
        auction_id = getIntent().getExtras().getString("auction_id");

        startpricetxt.setVisibility(View.GONE);
        startprice.setVisibility(View.GONE);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                getvehicleByIdForAuction();
            }
        });
    }

    private void getvehicleByIdForAuction() {
        ApiCall mApiCall = new ApiCall(MyAuctionVehicleDetails.this, this);
        mApiCall.getVehicleAuction(auction_id, vehicle_id, getSharedPreferences(getString(R.string.my_preference),
                MODE_PRIVATE).getString("loginContact", ""));
    }

    @Override
    public void notifySuccess(Response<?> response) {
        if (response != null) {
            if (response.isSuccessful()) {
                GetVehicleForAuctionResponse auctionResponse = (GetVehicleForAuctionResponse) response.body();
                for (GetVehicleForAuctionResponse.Vehicle auction : auctionResponse.getSuccess().getVehicle()) {

                    lotNo.setText(auction.getLotNo());
                    chassicdetails.setText(auction.getChassisNo());
                    vehicle_km_hrs.setText(auction.getKmsRunning());
                    vehicle_rto_city.setText(auction.getRtoCity());
                    //image = jsonObject.getString("image");
                    colordetails.setText(auction.getColor());
                    vehicle_location.setText(auction.getLocationCity());
                    vehiclemodel.setText(auction.getModel());
                    vehiclebrand.setText(auction.getBrand());
                    categorydetails.setText(auction.getCategory());
                    vehicletitle.setText(auction.getTitle());
                    vehicleyear.setText(auction.getYear());
                    if ((auction.getImage() == null) || auction.getImage().equals("") || auction.getImage().equals("null")) {
                        vehicleImage.setBackgroundResource(R.drawable.vehiimg);
                        sendImage = "http://autokatta.com/mobile/uploads/amitkamble.jpg";
                    } else {
                        String[] imagenamecame = auction.getImage().split(",");
                        for (int z = 0; z < imagenamecame.length; z++) {
                            iname.add(imagenamecame[z].replaceAll(" ", "%20"));
                        }
                        sendImage = iname.get(0);
                        Glide.with(MyAuctionVehicleDetails.this)
                                .load("http://autokatta.com/mobile/uploads/" + sendImage)
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .override(100, 100)
                                .into(vehicleImage);

                    }

                    if (auction.getCategory().equalsIgnoreCase("bus")) {
                        bustypedetails.setVisibility(View.VISIBLE);
                        bustext.setVisibility(View.VISIBLE);
                        vtyre.setVisibility(View.VISIBLE);
                        airdetails.setVisibility(View.VISIBLE);
                        vbus.setVisibility(View.VISIBLE);
                        airtext.setVisibility(View.VISIBLE);
                        vfuel.setVisibility(View.VISIBLE);
                        seatingdetails.setVisibility(View.VISIBLE);
                        seatcaptxt.setVisibility(View.VISIBLE);
                    } else if (auction.getCategory().equalsIgnoreCase("3 Wheeler")) {
                        vfuel.setVisibility(View.VISIBLE);
                        seatingdetails.setVisibility(View.VISIBLE);
                        seatcaptxt.setVisibility(View.VISIBLE);
                        airdetails.setVisibility(View.VISIBLE);
                        airtext.setVisibility(View.VISIBLE);
                        vbus.setVisibility(View.VISIBLE);
                    } else if (auction.getCategory().equalsIgnoreCase("Tractor")) {
                        implementationdetails.setVisibility(View.VISIBLE);
                        vengine.setVisibility(View.VISIBLE);
                        implimentstext.setVisibility(View.VISIBLE);
                        applicationdetails.setVisibility(View.VISIBLE);
                        applicationtext.setVisibility(View.VISIBLE);
                        vrv.setVisibility(View.VISIBLE);
                        hpcapacitydetails.setVisibility(View.VISIBLE);
                        vchassic.setVisibility(View.VISIBLE);
                        hpcapacitytxt.setVisibility(View.VISIBLE);
                    } else if (auction.getCategory().equalsIgnoreCase("Car")) {
                        transmissiondetails.setVisibility(View.VISIBLE);
                        vdrive.setVisibility(View.VISIBLE);
                        transmissiontext.setVisibility(View.VISIBLE);
                        vfuel.setVisibility(View.VISIBLE);
                        seatingdetails.setVisibility(View.VISIBLE);
                        seatcaptxt.setVisibility(View.VISIBLE);
                        airtext.setVisibility(View.VISIBLE);
                        airdetails.setVisibility(View.VISIBLE);
                        vbus.setVisibility(View.VISIBLE);
                    } else if (auction.getCategory().equalsIgnoreCase("Commercial Vehicle")) {
                        bodydetails.setVisibility(View.VISIBLE);
                        vtrans.setVisibility(View.VISIBLE);
                        bodytext.setVisibility(View.VISIBLE);
                        airdetails.setVisibility(View.VISIBLE);
                        vbus.setVisibility(View.VISIBLE);
                        airtext.setVisibility(View.VISIBLE);
                    }

                    if (auction.getCategory().equalsIgnoreCase("Cranes")) {
                        hpcapacitydetails.setVisibility(View.VISIBLE);
                        hpcapacitytxt.setVisibility(View.VISIBLE);
                        vchassic.setVisibility(View.VISIBLE);
                        jibdetails.setVisibility(View.VISIBLE);
                        jibtxt.setVisibility(View.VISIBLE);
                        vhapcapacity.setVisibility(View.VISIBLE);
                        boondetails.setVisibility(View.VISIBLE);
                        boontxt.setVisibility(View.VISIBLE);
                        vjib.setVisibility(View.VISIBLE);
                    }
                }

                /*for (GetVehicleForAuctionResponse.Auction auction : auctionResponse.getSuccess().getAuction()){
                    //contact_no = jsonObject.getString("contact_no");
                    *//*Version = jsonObject.getString("Version");
                    year_of_registration = jsonObject.getString("year_of_registration");
                    year_of_manufacture = jsonObject.getString("year_of_manufacture");

                    registration_number = jsonObject.getString("registration_number");
                    rc_available = jsonObject.getString("rc_available");

                    Hrs_running = jsonObject.getString("Hrs_running");
                    no_of_owners = jsonObject.getString("no_of_owners");
                    fual_type = jsonObject.getString("fual_type");
                    seating_capacity = jsonObject.getString("seating_capacity");
                    hypothication = jsonObject.getString("hypothication");
                    engine_no = jsonObject.getString("engine_no");

                    price = jsonObject.getString("price");

                    body_type = jsonObject.getString("body_type");
                    boat_type = jsonObject.getString("boat_type");
                    bus_type = jsonObject.getString("bus_type");
                    implementsd = jsonObject.getString("implements");
                    finance_req = jsonObject.getString("finance_req");
                    hp_capacity = jsonObject.getString("hp_capacity");
                    JIB = jsonObject.getString("JIB");
                    Boon = jsonObject.getString("Boon");
                    CurrentBid_price = jsonObject.getString("CurrentBid_price");
                    tyre_condition = jsonObject.getString("tyre_condition");
                    insurance_valid = jsonObject.getString("insurance_valid");
                    insurance_idv = jsonObject.getString("insurance_idv");
                    fitness_validity = jsonObject.getString("fitness_validity");
                    permit_validity = jsonObject.getString("permit_validity");
                    air_condition = jsonObject.getString("air_condition");
                    rv_type = jsonObject.getString("rv_type");
                    transmission = jsonObject.getString("transmission");
                    tax_validity = jsonObject.getString("tax_validity");
                    application = jsonObject.getString("application");
                    permit_yesno = jsonObject.getString("permit_yesno");
                    drive = jsonObject.getString("drive");*//*

                    fitness_validitydetails.setText(fitness_validity);
                    permit_validitydetails.setText(permit_validity);
                    fueldetails.setText(fual_type);
                    seatingdetails.setText(seating_capacity);
                    hypodetails.setText(hypothication);
                    permitdetails.setText(permit_yesno);
                    drivedetails.setText(drive);
                    transmissiondetails.setText(transmission);

                    bodydetails.setText(body_type);
                    if (boat_type != null)
                        boatdetails.setText(boat_type);
                    rvdetails.setText(rv_type);
                    applicationdetails.setText(application);
                    tyredetails.setText(tyre_condition);
                    airdetails.setText(air_condition);
                    bustypedetails.setText(bus_type);
                    registrationdetails.setText(registration_number);
                    enginedetails.setText(engine_no);
                    implementationdetails.setText(implementsd);
                    hpcapacitydetails.setText(hp_capacity);
                }*/

            } else {
                CustomToast.customToast(getApplicationContext(), getString(R.string._404));
            }
        } else {
            CustomToast.customToast(getApplicationContext(), getString(R.string.no_response));
        }
    }

    @Override
    public void notifyError(Throwable error) {

    }

    @Override
    public void notifyString(String str) {

    }
}
