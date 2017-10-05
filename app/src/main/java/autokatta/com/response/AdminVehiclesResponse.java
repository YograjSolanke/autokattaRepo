package autokatta.com.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ak-003 on 1/4/17.
 */

public class AdminVehiclesResponse {

    @SerializedName("Success")
    @Expose
    private List<Success> success = null;

    public List<Success> getSuccess() {
        return success;
    }

    public void setSuccess(List<Success> success) {
        this.success = success;
    }


    public class Success {

        @SerializedName("id")
        @Expose
        private int id;
        @SerializedName("userid")
        @Expose
        private String userid;
        @SerializedName("fileName")
        @Expose
        private String fileName;
        @SerializedName("categoryId")
        @Expose
        private String categoryId;
        @SerializedName("subCategoryId")
        @Expose
        private String subCategoryId;
        @SerializedName("brandId")
        @Expose
        private String brandId;
        @SerializedName("modelId")
        @Expose
        private String modelId;
        @SerializedName("lotNo")
        @Expose
        private String lotNo;
        @SerializedName("registrationNumber")
        @Expose
        private String registrationNumber;
        @SerializedName("mode")
        @Expose
        private String mode;
        @SerializedName("chassiNo")
        @Expose
        private String chassiNo;
        @SerializedName("yom")
        @Expose
        private String yom;
        @SerializedName("yardName")
        @Expose
        private String yardName;
        @SerializedName("contactNo")
        @Expose
        private String contactNo;
        @SerializedName("rcStatus")
        @Expose
        private String rcStatus;
        @SerializedName("engineNo")
        @Expose
        private String engineNo;
        @SerializedName("color")
        @Expose
        private String color;
        @SerializedName("winingBidAmmount")
        @Expose
        private String winingBidAmmount;
        @SerializedName("winnerName")
        @Expose
        private String winnerName;
        @SerializedName("winnerPhoneNumber")
        @Expose
        private String winnerPhoneNumber;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("state")
        @Expose
        private String state;
        @SerializedName("product")
        @Expose
        private String product;
        @SerializedName("areaOffice")
        @Expose
        private String areaOffice;
        @SerializedName("custName")
        @Expose
        private String custName;
        @SerializedName("insdt")
        @Expose
        private String insdt;
        @SerializedName("vehicleTax")
        @Expose
        private String vehicleTax;
        @SerializedName("reservedPrice")
        @Expose
        private String reservedPrice;
        @SerializedName("serviceCharge")
        @Expose
        private String serviceCharge;
        @SerializedName("serviceTax")
        @Expose
        private String serviceTax;
        @SerializedName("buyerFee")
        @Expose
        private String buyerFee;
        @SerializedName("branch")
        @Expose
        private String branch;
        @SerializedName("loanAccountNo")
        @Expose
        private String loanAccountNo;
        @SerializedName("milesOrKms")
        @Expose
        private String milesOrKms;
        @SerializedName("parkedAt")
        @Expose
        private String parkedAt;
        @SerializedName("specialClauses")
        @Expose
        private String specialClauses;
        @SerializedName("specialClause1")
        @Expose
        private String specialClause1;
        @SerializedName("specialClause2")
        @Expose
        private String specialClause2;
        @SerializedName("specialClause3")
        @Expose
        private String specialClause3;
        @SerializedName("specialClause4")
        @Expose
        private String specialClause4;
        @SerializedName("specialClause5")
        @Expose
        private String specialClause5;
        @SerializedName("specialClause6")
        @Expose
        private String specialClause6;
        @SerializedName("specialClause7")
        @Expose
        private String specialClause7;
        @SerializedName("insuranceStatus")
        @Expose
        private String insuranceStatus;
        @SerializedName("registrationYear")
        @Expose
        private String registrationYear;
        @SerializedName("startPrice")
        @Expose
        private String startPrice;
        @SerializedName("excelFileImages")
        @Expose
        private String excelFileImages;
        @SerializedName("uploadedImages")
        @Expose
        private String uploadedImages;
        @SerializedName("manufacturer")
        @Expose
        private String manufacturer;
        @SerializedName("productCategory")
        @Expose
        private String productCategory;
        @SerializedName("contactPerson")
        @Expose
        private String contactPerson;
        @SerializedName("version")
        @Expose
        private String version;
        @SerializedName("repoDate")
        @Expose
        private String repoDate;
        @SerializedName("yardRentPerDay")
        @Expose
        private String yardRentPerDay;
        @SerializedName("noOfHours")
        @Expose
        private String noOfHours;
        @SerializedName("ownerNo")
        @Expose
        private String ownerNo;
        @SerializedName("hypothecated")
        @Expose
        private String hypothecated;
        @SerializedName("fuelType")
        @Expose
        private String fuelType;
        @SerializedName("dealerName")
        @Expose
        private String dealerName;
        @SerializedName("status")
        @Expose
        private String status;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public String getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(String categoryId) {
            this.categoryId = categoryId;
        }

        public String getSubCategoryId() {
            return subCategoryId;
        }

        public void setSubCategoryId(String subCategoryId) {
            this.subCategoryId = subCategoryId;
        }

        public String getBrandId() {
            return brandId;
        }

        public void setBrandId(String brandId) {
            this.brandId = brandId;
        }

        public String getModelId() {
            return modelId;
        }

        public void setModelId(String modelId) {
            this.modelId = modelId;
        }

        public String getLotNo() {
            return lotNo;
        }

        public void setLotNo(String lotNo) {
            this.lotNo = lotNo;
        }

        public String getRegistrationNumber() {
            return registrationNumber;
        }

        public void setRegistrationNumber(String registrationNumber) {
            this.registrationNumber = registrationNumber;
        }

        public String getMode() {
            return mode;
        }

        public void setMode(String mode) {
            this.mode = mode;
        }

        public String getChassiNo() {
            return chassiNo;
        }

        public void setChassiNo(String chassiNo) {
            this.chassiNo = chassiNo;
        }

        public String getYom() {
            return yom;
        }

        public void setYom(String yom) {
            this.yom = yom;
        }

        public String getYardName() {
            return yardName;
        }

        public void setYardName(String yardName) {
            this.yardName = yardName;
        }

        public String getContactNo() {
            return contactNo;
        }

        public void setContactNo(String contactNo) {
            this.contactNo = contactNo;
        }

        public String getRcStatus() {
            return rcStatus;
        }

        public void setRcStatus(String rcStatus) {
            this.rcStatus = rcStatus;
        }

        public String getEngineNo() {
            return engineNo;
        }

        public void setEngineNo(String engineNo) {
            this.engineNo = engineNo;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public String getWiningBidAmmount() {
            return winingBidAmmount;
        }

        public void setWiningBidAmmount(String winingBidAmmount) {
            this.winingBidAmmount = winingBidAmmount;
        }

        public String getWinnerName() {
            return winnerName;
        }

        public void setWinnerName(String winnerName) {
            this.winnerName = winnerName;
        }

        public String getWinnerPhoneNumber() {
            return winnerPhoneNumber;
        }

        public void setWinnerPhoneNumber(String winnerPhoneNumber) {
            this.winnerPhoneNumber = winnerPhoneNumber;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getProduct() {
            return product;
        }

        public void setProduct(String product) {
            this.product = product;
        }

        public String getAreaOffice() {
            return areaOffice;
        }

        public void setAreaOffice(String areaOffice) {
            this.areaOffice = areaOffice;
        }

        public String getCustName() {
            return custName;
        }

        public void setCustName(String custName) {
            this.custName = custName;
        }

        public String getInsdt() {
            return insdt;
        }

        public void setInsdt(String insdt) {
            this.insdt = insdt;
        }

        public String getVehicleTax() {
            return vehicleTax;
        }

        public void setVehicleTax(String vehicleTax) {
            this.vehicleTax = vehicleTax;
        }

        public String getReservedPrice() {
            return reservedPrice;
        }

        public void setReservedPrice(String reservedPrice) {
            this.reservedPrice = reservedPrice;
        }

        public String getServiceCharge() {
            return serviceCharge;
        }

        public void setServiceCharge(String serviceCharge) {
            this.serviceCharge = serviceCharge;
        }

        public String getServiceTax() {
            return serviceTax;
        }

        public void setServiceTax(String serviceTax) {
            this.serviceTax = serviceTax;
        }

        public String getBuyerFee() {
            return buyerFee;
        }

        public void setBuyerFee(String buyerFee) {
            this.buyerFee = buyerFee;
        }

        public String getBranch() {
            return branch;
        }

        public void setBranch(String branch) {
            this.branch = branch;
        }

        public String getLoanAccountNo() {
            return loanAccountNo;
        }

        public void setLoanAccountNo(String loanAccountNo) {
            this.loanAccountNo = loanAccountNo;
        }

        public String getMilesOrKms() {
            return milesOrKms;
        }

        public void setMilesOrKms(String milesOrKms) {
            this.milesOrKms = milesOrKms;
        }

        public String getParkedAt() {
            return parkedAt;
        }

        public void setParkedAt(String parkedAt) {
            this.parkedAt = parkedAt;
        }

        public String getSpecialClauses() {
            return specialClauses;
        }

        public void setSpecialClauses(String specialClauses) {
            this.specialClauses = specialClauses;
        }

        public String getSpecialClause1() {
            return specialClause1;
        }

        public void setSpecialClause1(String specialClause1) {
            this.specialClause1 = specialClause1;
        }

        public String getSpecialClause2() {
            return specialClause2;
        }

        public void setSpecialClause2(String specialClause2) {
            this.specialClause2 = specialClause2;
        }

        public String getSpecialClause3() {
            return specialClause3;
        }

        public void setSpecialClause3(String specialClause3) {
            this.specialClause3 = specialClause3;
        }

        public String getSpecialClause4() {
            return specialClause4;
        }

        public void setSpecialClause4(String specialClause4) {
            this.specialClause4 = specialClause4;
        }

        public String getSpecialClause5() {
            return specialClause5;
        }

        public void setSpecialClause5(String specialClause5) {
            this.specialClause5 = specialClause5;
        }

        public String getSpecialClause6() {
            return specialClause6;
        }

        public void setSpecialClause6(String specialClause6) {
            this.specialClause6 = specialClause6;
        }

        public String getSpecialClause7() {
            return specialClause7;
        }

        public void setSpecialClause7(String specialClause7) {
            this.specialClause7 = specialClause7;
        }

        public String getInsuranceStatus() {
            return insuranceStatus;
        }

        public void setInsuranceStatus(String insuranceStatus) {
            this.insuranceStatus = insuranceStatus;
        }

        public String getRegistrationYear() {
            return registrationYear;
        }

        public void setRegistrationYear(String registrationYear) {
            this.registrationYear = registrationYear;
        }

        public String getStartPrice() {
            return startPrice;
        }

        public void setStartPrice(String startPrice) {
            this.startPrice = startPrice;
        }

        public String getExcelFileImages() {
            return excelFileImages;
        }

        public void setExcelFileImages(String excelFileImages) {
            this.excelFileImages = excelFileImages;
        }

        public String getUploadedImages() {
            return uploadedImages;
        }

        public void setUploadedImages(String uploadedImages) {
            this.uploadedImages = uploadedImages;
        }

        public String getManufacturer() {
            return manufacturer;
        }

        public void setManufacturer(String manufacturer) {
            this.manufacturer = manufacturer;
        }

        public String getProductCategory() {
            return productCategory;
        }

        public void setProductCategory(String productCategory) {
            this.productCategory = productCategory;
        }

        public String getContactPerson() {
            return contactPerson;
        }

        public void setContactPerson(String contactPerson) {
            this.contactPerson = contactPerson;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getRepoDate() {
            return repoDate;
        }

        public void setRepoDate(String repoDate) {
            this.repoDate = repoDate;
        }

        public String getYardRentPerDay() {
            return yardRentPerDay;
        }

        public void setYardRentPerDay(String yardRentPerDay) {
            this.yardRentPerDay = yardRentPerDay;
        }

        public String getNoOfHours() {
            return noOfHours;
        }

        public void setNoOfHours(String noOfHours) {
            this.noOfHours = noOfHours;
        }

        public String getOwnerNo() {
            return ownerNo;
        }

        public void setOwnerNo(String ownerNo) {
            this.ownerNo = ownerNo;
        }

        public String getHypothecated() {
            return hypothecated;
        }

        public void setHypothecated(String hypothecated) {
            this.hypothecated = hypothecated;
        }

        public String getFuelType() {
            return fuelType;
        }

        public void setFuelType(String fuelType) {
            this.fuelType = fuelType;
        }

        public String getDealerName() {
            return dealerName;
        }

        public void setDealerName(String dealerName) {
            this.dealerName = dealerName;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

    }
}