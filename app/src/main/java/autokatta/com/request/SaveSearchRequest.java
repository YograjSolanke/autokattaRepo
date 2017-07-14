package autokatta.com.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ak-005 on 14/7/17.
 */

public class SaveSearchRequest {


    @SerializedName("Keyword")
    @Expose
    private String keyword;
    @SerializedName("SearchID")
    @Expose
    private Integer searchID;
    @SerializedName("Contact")
    @Expose
    private String contact;
    @SerializedName("Category")
    @Expose
    private String category;
    @SerializedName("Brand")
    @Expose
    private String brand;
    @SerializedName("Model")
    @Expose
    private String model;
    @SerializedName("RTOCity")
    @Expose
    private String rTOCity;
    @SerializedName("City")
    @Expose
    private String city;
    @SerializedName("LocationState")
    @Expose
    private String locationState;
    @SerializedName("RegistrationYear")
    @Expose
    private String registrationYear;
    @SerializedName("ManufacturerYear")
    @Expose
    private String manufacturerYear;
    @SerializedName("Color")
    @Expose
    private String color;
    @SerializedName("RCAvailable")
    @Expose
    private String rCAvailable;
    @SerializedName("InsuranceValid")
    @Expose
    private String insuranceValid;
    @SerializedName("TaxValidity")
    @Expose
    private String taxValidity;
    @SerializedName("FitnessValidity")
    @Expose
    private String fitnessValidity;
    @SerializedName("PermitValidity")
    @Expose
    private String permitValidity;
    @SerializedName("Fuel")
    @Expose
    private String fuel;
    @SerializedName("SeatCap")
    @Expose
    private String seatCap;
    @SerializedName("Permit")
    @Expose
    private String permit;
    @SerializedName("KmsRunning")
    @Expose
    private String kmsRunning;
    @SerializedName("HrsRunning")
    @Expose
    private String hrsRunning;
    @SerializedName("HpCapacity")
    @Expose
    private String hpCapacity;
    @SerializedName("Owners")
    @Expose
    private Integer owners;
    @SerializedName("Hypothetication")
    @Expose
    private String hypothetication;
    @SerializedName("Price")
    @Expose
    private String price;
    @SerializedName("Drive")
    @Expose
    private String drive;
    @SerializedName("Transmission")
    @Expose
    private String transmission;
    @SerializedName("BodyType")
    @Expose
    private String bodyType;
    @SerializedName("BoatType")
    @Expose
    private String boatType;
    @SerializedName("RvType")
    @Expose
    private String rvType;
    @SerializedName("Application")
    @Expose
    private String application;
    @SerializedName("City1")
    @Expose
    private String city1;
    @SerializedName("City2")
    @Expose
    private String city2;
    @SerializedName("City3")
    @Expose
    private String city3;
    @SerializedName("City4")
    @Expose
    private String city4;
    @SerializedName("RTOcity1")
    @Expose
    private String rTOcity1;
    @SerializedName("RTOcity2")
    @Expose
    private String rTOcity2;
    @SerializedName("RTOcity3")
    @Expose
    private String rTOcity3;
    @SerializedName("RTOcity4")
    @Expose
    private String rTOcity4;
    @SerializedName("TyreCondition")
    @Expose
    private String tyreCondition;
    @SerializedName("Implements")
    @Expose
    private String _implements;
    @SerializedName("BusType")
    @Expose
    private String busType;
    @SerializedName("AirCondition")
    @Expose
    private String airCondition;
    @SerializedName("Invoice")
    @Expose
    private String invoice;
    @SerializedName("Finance")
    @Expose
    private String finance;
    @SerializedName("Version")
    @Expose
    private String version;
    @SerializedName("CallPermission")
    @Expose
    private String callPermission;

    public SaveSearchRequest(String keyword, Integer searchID, String contact, String category, String brand, String model,
                             String rTOCity, String city, String locationState, String registrationYear,
                             String manufacturerYear, String color, String rCAvailable, String insuranceValid,
                             String taxValidity, String fitnessValidity, String permitValidity, String fuel,
                             String seatCap, String permit, String kmsRunning, String hrsRunning, String hpCapacity,
                             Integer owners, String hypothetication, String price, String drive, String transmission,
                             String bodyType, String boatType, String rvType, String application, String city1, String city2,
                             String city3, String city4, String rTOcity1, String rTOcity2, String rTOcity3, String rTOcity4,
                             String tyreCondition, String _implements, String busType, String airCondition, String invoice,
                             String finance, String version, String callPermission) {
        this.keyword = keyword;
        this.searchID = searchID;
        this.contact = contact;
        this.category = category;
        this.brand = brand;
        this.model = model;
        this.rTOCity = rTOCity;
        this.city = city;
        this.locationState = locationState;
        this.registrationYear = registrationYear;
        this.manufacturerYear = manufacturerYear;
        this.color = color;
        this.rCAvailable = rCAvailable;
        this.insuranceValid = insuranceValid;
        this.taxValidity = taxValidity;
        this.fitnessValidity = fitnessValidity;
        this.permitValidity = permitValidity;
        this.fuel = fuel;
        this.seatCap = seatCap;
        this.permit = permit;
        this.kmsRunning = kmsRunning;
        this.hrsRunning = hrsRunning;
        this.hpCapacity = hpCapacity;
        this.owners = owners;
        this.hypothetication = hypothetication;
        this.price = price;
        this.drive = drive;
        this.transmission = transmission;
        this.bodyType = bodyType;
        this.boatType = boatType;
        this.rvType = rvType;
        this.application = application;
        this.city1 = city1;
        this.city2 = city2;
        this.city3 = city3;
        this.city4 = city4;
        this.rTOcity1 = rTOcity1;
        this.rTOcity2 = rTOcity2;
        this.rTOcity3 = rTOcity3;
        this.rTOcity4 = rTOcity4;
        this.tyreCondition = tyreCondition;
        this._implements = _implements;
        this.busType = busType;
        this.airCondition = airCondition;
        this.invoice = invoice;
        this.finance = finance;
        this.version = version;
        this.callPermission = callPermission;
    }
}
