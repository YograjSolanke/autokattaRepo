package autokatta.com.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ak-005 on 11/7/17.
 */

public class AddOwnVehicleRequest {

        @SerializedName("Contact")
        @Expose
        private String contact;
        @SerializedName("VehicleNo")
        @Expose
        private String vehicleNo;
        @SerializedName("VehicleType")
        @Expose
        private String vehicleType;
        @SerializedName("SubCategory")
        @Expose
        private String subCategory;
        @SerializedName("ModelNo")
        @Expose
        private String modelNo;
        @SerializedName("Brand")
        @Expose
        private String brand;
        @SerializedName("Version")
        @Expose
        private String version;
        @SerializedName("Year")
        @Expose
        private String year;
        @SerializedName("TaxValidity")
        @Expose
        private String taxValidity;
        @SerializedName("FitnessValidity")
        @Expose
        private String fitnessValidity;
        @SerializedName("PermitValidity")
        @Expose
        private String permitValidity;
        @SerializedName("Insurance")
        @Expose
        private String insurance;
        @SerializedName("PUC")
        @Expose
        private String pUC;
        @SerializedName("LastServiceDate")
        @Expose
        private String lastServiceDate;
        @SerializedName("NextServiceDate")
        @Expose
        private String nextServiceDate;
        @SerializedName("CreatedDate")
        @Expose
        private String createdDate;

        public String getContact() {
            return contact;
        }

        public void setContact(String contact) {
            this.contact = contact;
        }

        public String getVehicleNo() {
            return vehicleNo;
        }

        public void setVehicleNo(String vehicleNo) {
            this.vehicleNo = vehicleNo;
        }

        public String getVehicleType() {
            return vehicleType;
        }

        public void setVehicleType(String vehicleType) {
            this.vehicleType = vehicleType;
        }

        public String getSubCategory() {
            return subCategory;
        }

        public void setSubCategory(String subCategory) {
            this.subCategory = subCategory;
        }

        public String getModelNo() {
            return modelNo;
        }

        public void setModelNo(String modelNo) {
            this.modelNo = modelNo;
        }

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getYear() {
            return year;
        }

        public void setYear(String year) {
            this.year = year;
        }

        public String getTaxValidity() {
            return taxValidity;
        }

        public void setTaxValidity(String taxValidity) {
            this.taxValidity = taxValidity;
        }

        public String getFitnessValidity() {
            return fitnessValidity;
        }

        public void setFitnessValidity(String fitnessValidity) {
            this.fitnessValidity = fitnessValidity;
        }

        public String getPermitValidity() {
            return permitValidity;
        }

        public void setPermitValidity(String permitValidity) {
            this.permitValidity = permitValidity;
        }

        public String getInsurance() {
            return insurance;
        }

        public void setInsurance(String insurance) {
            this.insurance = insurance;
        }

        public String getPUC() {
            return pUC;
        }

        public void setPUC(String pUC) {
            this.pUC = pUC;
        }

        public String getLastServiceDate() {
            return lastServiceDate;
        }

        public void setLastServiceDate(String lastServiceDate) {
            this.lastServiceDate = lastServiceDate;
        }

        public String getNextServiceDate() {
            return nextServiceDate;
        }

        public void setNextServiceDate(String nextServiceDate) {
            this.nextServiceDate = nextServiceDate;
        }

        public String getCreatedDate() {
            return createdDate;
        }

        public void setCreatedDate(String createdDate) {
            this.createdDate = createdDate;
        }
    }

