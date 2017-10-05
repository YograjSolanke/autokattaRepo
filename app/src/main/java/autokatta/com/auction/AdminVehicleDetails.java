package autokatta.com.auction;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.net.SocketTimeoutException;
import java.util.ArrayList;

import autokatta.com.R;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import autokatta.com.response.GetAdminVehicleResponse;
import retrofit2.Response;

public class AdminVehicleDetails extends AppCompatActivity implements RequestNotifier {

    String contactnumber, auction_id;
    int vehicle_id = 0;
    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
    String vvehicle_id, title, contact_no, category, sub_category, model, manufacturer, Version, rto_city, location_city, year_of_registration, year_of_manufacture, colorr, registration_number, rc_available, kms_running = "", Hrs_running, no_of_owners, fual_type, seating_capacity, hypothication, engine_no, chassis_no, price, image, body_type, boat_type, bus_type, implementsd, finance_req, hp_capacity, JIB, Boon, tyre_condition, insurance_valid, insurance_idv, fitness_validity, permit_validity, air_condition, rv_type, transmission, tax_validity, application, permit_yesno, drive, lotNostr, bundle_lotNo, Repodate, Yardrent;
    TextView vehiclemodel, vehicletitle, vehiclebrand, vehicleyear, vehicle_km_hrs, vehiclerc_invoice, vehicle_rto_city, vehicle_location, current_bid, moredetail, startpricetxt, startprice, lotNo;
    TextView categorydetails, regyeardetails, insurencedetails, ownerdetails, rc_availabledetails, ins_validitydetails, tax_validitydetails, fitness_validitydetails, permit_validitydetails, fueldetails, seatingdetails, hypodetails, permitdetails, drivedetails, transmissiondetails, bodydetails, boatdetails, rvdetails, applicationdetails, colordetails, tyredetails, bustypedetails, airdetails, registrationdetails, enginedetails, implementationdetails, chassicdetails, hpcapacitydetails, jibdetails,
            boondetails;
    ImageView vehicleImage;
    ImageButton callImage;
    View vhapcapacity, vimplement, vair, vbus, vapp, vboat, vbody, vtrans, vdrive, vseat, vboon, vjib, vrv, vfuel, vhypo, vtyre, vengine,
            vchassic, vrepo, vyard;
    TextView hpcapacitytxt, jibtxt, boontxt, bustext, airtext, seatcaptxt, implimentstext, applicationtext, transmissiontext, drivetext, bodytext, boattext, rvtext;
    TextView cameracount, repodetails, yardrentdetails;
    String sendImage = "", Allimages = "";
    ArrayList<String> iname = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_vehicle_details);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
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
        callImage = (ImageButton) findViewById(R.id.callimg);

        //cameracount = (TextView) findViewById(R.id.cameracount);
        repodetails = (TextView) findViewById(R.id.repodetails);
        yardrentdetails = (TextView) findViewById(R.id.yardrentdetails);

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
        vjib = (View) findViewById(R.id.vjib);
        vboon = (View) findViewById(R.id.vboon);
        vfuel = (View) findViewById(R.id.vfuel);
        vhypo = (View) findViewById(R.id.vhypo);
        vtyre = (View) findViewById(R.id.vtyre);
        vengine = (View) findViewById(R.id.vengine);
        vchassic = (View) findViewById(R.id.vchassic);
        vrv = (View) findViewById(R.id.vrv);
        vrepo = (View) findViewById(R.id.vrepo);
        vyard = (View) findViewById(R.id.vyard);

        vehicle_id = getIntent().getExtras().getInt("vehicle_id");
        bundle_lotNo = getIntent().getExtras().getString("lotNo");

        //Async call
        getadminVehicle();
        // getAdminVehicle();


        startpricetxt.setVisibility(View.GONE);
        startprice.setVisibility(View.GONE);


        //Image clicked for sliding
        /*vehicleImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewImages(root);
            }
        });*/

        //to call Vehicle Owner
        callImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                call();
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse(contact_no));
            }
        });
    }

    private void getadminVehicle() {
        ApiCall mApiCall = new ApiCall(AdminVehicleDetails.this, this);
        mApiCall.getAdminAuction(vehicle_id, getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE)
                .getString("loginContact", ""));
    }

    private void call() {
        Intent in = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + contact_no));
        System.out.println("calling started");
        try {
            startActivity(in);
        } catch (android.content.ActivityNotFoundException ex) {
            System.out.println("No Activity Found For Call in Service View Fragment\n");
        }
    }

    @Override
    public void notifySuccess(Response<?> response) {
        if (response != null) {
            if (response.isSuccessful()) {
                GetAdminVehicleResponse adminVehicleResponse = (GetAdminVehicleResponse) response.body();
                for (GetAdminVehicleResponse.Success success : adminVehicleResponse.getSuccess()) {
                    vvehicle_id = success.getId();
                    title = "NA";
                    contact_no = success.getContactNo();
                    category = success.getCategory();
                    sub_category = success.getSubcategory();
                    model = success.getModel();
                    manufacturer = success.getBrand();
                    Version = success.getVersion();
                    rto_city = "NA";
                    location_city = success.getParkedAt();
                    year_of_registration = success.getRegistrationYear();
                    year_of_manufacture = success.getYom();
                    colorr = success.getColor();
                    registration_number = success.getRegistrationNumber();
                    rc_available = success.getRcStatus();
                    kms_running = success.getMilesOrKms();
                    Hrs_running = success.getNoOfHours();
                    no_of_owners = success.getOwnerNo();
                    fual_type = success.getFuelType();
                    seating_capacity = "NA";
                    hypothication = success.getHypothecated();
                    engine_no = success.getEngineNo();
                    chassis_no = success.getChassiNo();
                    price = "NA";
                    image = success.getUploadedImages();
                    body_type = "NA";
                    boat_type = "NA";
                    bus_type = "NA";
                    implementsd = "NA";
                    finance_req = "NA";
                    hp_capacity = "NA";
                    JIB = "NA";
                    Boon = "NA";

                    tyre_condition = "NA";
                    insurance_valid = success.getInsdt();
                    insurance_idv = success.getInsuranceStatus();
                    fitness_validity = "NA";
                    permit_validity = "NA";
                    air_condition = "NA";
                    rv_type = "NA";
                    transmission = "NA";
                    tax_validity = success.getServiceTax();
                    application = "NA";
                    permit_yesno = "NA";
                    drive = "NA";
                    lotNostr = success.getLotNo();
                    Repodate = success.getRepoDate();
                    Yardrent = success.getYardRentPerDay();
                }
                if ((image == null) || image.equals("") || image.equals("null")) {
                    vehicleImage.setBackgroundResource(R.drawable.vehiimg);
                    sendImage = getApplicationContext().getString(R.string.base_image_url) + "logo48x48";
                } else {
                    String[] imagenamecame = image.split(",");
                    for (int z = 0; z < imagenamecame.length; z++) {
                        iname.add(imagenamecame[z].replaceAll(" ", "%20"));
                    }
                    sendImage = iname.get(0);
                    Glide.with(getApplicationContext())
                            .load(getApplicationContext().getString(R.string.base_image_url) + sendImage)
                            .override(100, 100)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(vehicleImage);
                }
                for (int i = 0; i < iname.size(); i++) {
                    Allimages = Allimages + "," + iname.get(i);
                }
                //cameracount.setText(String.valueOf(iname.size()));

                vehicletitle.setText(title);
                vehiclemodel.setText(model);
                vehiclebrand.setText(manufacturer);
                vehicle_rto_city.setText(rto_city);
                vehiclerc_invoice.setText(rc_available);
                vehicle_location.setText(location_city);
                vehicleyear.setText(year_of_manufacture);
                lotNo.setText(bundle_lotNo);

                categorydetails.setText(category);
                regyeardetails.setText(year_of_registration);
                insurencedetails.setText(insurance_idv);
                ownerdetails.setText(no_of_owners);
                rc_availabledetails.setText(rc_available);
                ins_validitydetails.setText(insurance_valid);
                tax_validitydetails.setText(tax_validity);
                fitness_validitydetails.setText(fitness_validity);
                permit_validitydetails.setText(permit_validity);
                fueldetails.setText(fual_type);
                seatingdetails.setText(seating_capacity);
                hypodetails.setText(hypothication);
                permitdetails.setText(permit_yesno);
                drivedetails.setText(drive);
                transmissiondetails.setText(transmission);
                colordetails.setText(colorr);
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
                chassicdetails.setText(chassis_no);
                hpcapacitydetails.setText(hp_capacity);
                jibdetails.setText(JIB);
                boondetails.setText(Boon);

                repodetails.setText(Repodate);
                yardrentdetails.setText("" + Yardrent + "Rs.");

                if (category.equalsIgnoreCase("bus")) {
                    bustypedetails.setVisibility(View.VISIBLE);
                    bustext.setVisibility(View.VISIBLE);
                    vtyre.setVisibility(View.VISIBLE);
                    airdetails.setVisibility(View.VISIBLE);
                    vbus.setVisibility(View.VISIBLE);
                    airtext.setVisibility(View.VISIBLE);
                    vfuel.setVisibility(View.VISIBLE);
                    seatingdetails.setVisibility(View.VISIBLE);
                    seatcaptxt.setVisibility(View.VISIBLE);
                } else if (category.equalsIgnoreCase("3 Wheeler")) {
                    vfuel.setVisibility(View.VISIBLE);
                    seatingdetails.setVisibility(View.VISIBLE);
                    seatcaptxt.setVisibility(View.VISIBLE);
                    airdetails.setVisibility(View.VISIBLE);
                    airtext.setVisibility(View.VISIBLE);
                    vbus.setVisibility(View.VISIBLE);
                } else if (category.equalsIgnoreCase("Tractor")) {
                    implementationdetails.setVisibility(View.VISIBLE);
                    vengine.setVisibility(View.VISIBLE);
                    implimentstext.setVisibility(View.VISIBLE);
                    applicationdetails.setVisibility(View.VISIBLE);
                    applicationtext.setVisibility(View.VISIBLE);
                    vrv.setVisibility(View.VISIBLE);
                    hpcapacitydetails.setVisibility(View.VISIBLE);
                    vchassic.setVisibility(View.VISIBLE);
                    hpcapacitytxt.setVisibility(View.VISIBLE);
                } else if (category.equalsIgnoreCase("Car")) {
                    transmissiondetails.setVisibility(View.VISIBLE);
                    vdrive.setVisibility(View.VISIBLE);
                    transmissiontext.setVisibility(View.VISIBLE);
                    vfuel.setVisibility(View.VISIBLE);
                    seatingdetails.setVisibility(View.VISIBLE);
                    seatcaptxt.setVisibility(View.VISIBLE);
                    airtext.setVisibility(View.VISIBLE);
                    airdetails.setVisibility(View.VISIBLE);
                    vbus.setVisibility(View.VISIBLE);
                } else if (category.equalsIgnoreCase("Commercial Vehicle")) {
                    bodydetails.setVisibility(View.VISIBLE);
                    vtrans.setVisibility(View.VISIBLE);
                    bodytext.setVisibility(View.VISIBLE);
                    airdetails.setVisibility(View.VISIBLE);
                    vbus.setVisibility(View.VISIBLE);
                    airtext.setVisibility(View.VISIBLE);
                }
                if (category.equalsIgnoreCase("Cranes")) {
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
                if (!kms_running.equals("") || kms_running != null)
                    vehicle_km_hrs.setText("" + kms_running + "Kms");
                else
                    vehicle_km_hrs.setText("" + Hrs_running + "Hrs");
            } else {
                // CustomToast.customToast(getApplicationContext(), getString(R.string._404));
            }
        } else {

            CustomToast.customToast(getApplicationContext(), getString(R.string.no_response));
        }
    }

    @Override
    public void notifyError(Throwable error) {
        if (error instanceof SocketTimeoutException) {
            CustomToast.customToast(getApplicationContext(), getString(R.string._404));
        } else if (error instanceof NullPointerException) {
            // CustomToast.customToast(getApplicationContext(), getString(R.string.no_response));
        } else if (error instanceof ClassCastException) {
            // CustomToast.customToast(getApplicationContext(), getString(R.string.no_response));
        } else {
            Log.i("Check Class-", "AdminVehicleDetails Activity");
        }
    }

    @Override
    public void notifyString(String str) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        int fragments = getSupportFragmentManager().getBackStackEntryCount();
        if (fragments == 1) {
            finish();
        } else {
            if (getFragmentManager().getBackStackEntryCount() > 1) {
                getFragmentManager().popBackStack();
            } else {
                super.onBackPressed();
            }
        }
    }
}
