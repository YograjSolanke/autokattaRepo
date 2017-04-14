package autokatta.com.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ak-005 on 14/4/17.
 */

public class GetOwnVehiclesResponse {

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
        private String id;
        @SerializedName("vehicle_type")
        @Expose
        private String vehicleType;
        @SerializedName("brand")
        @Expose
        private String brand;
        @SerializedName("year")
        @Expose
        private String year;
        @SerializedName("version")
        @Expose
        private String version;
        @SerializedName("model_no")
        @Expose
        private String modelNo;
        @SerializedName("subcategory")
        @Expose
        private String subcategory;
        @SerializedName("vehi_no")
        @Expose
        private String vehiNo;
        @SerializedName("tax_validity")
        @Expose
        private String taxValidity;
        @SerializedName("fitness_validity")
        @Expose
        private String fitnessValidity;
        @SerializedName("permit_validity")
        @Expose
        private String permitValidity;
        @SerializedName("insurance")
        @Expose
        private String insurance;
        @SerializedName("puc")
        @Expose
        private String puc;
        @SerializedName("last_service_date")
        @Expose
        private String lastServiceDate;
        @SerializedName("next_service_date")
        @Expose
        private String nextServiceDate;
        @SerializedName("uploaddate")
        @Expose
        private String uploaddate;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getVehicleType() {
            return vehicleType;
        }

        public void setVehicleType(String vehicleType) {
            this.vehicleType = vehicleType;
        }

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public String getYear() {
            return year;
        }

        public void setYear(String year) {
            this.year = year;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getModelNo() {
            return modelNo;
        }

        public void setModelNo(String modelNo) {
            this.modelNo = modelNo;
        }

        public String getSubcategory() {
            return subcategory;
        }

        public void setSubcategory(String subcategory) {
            this.subcategory = subcategory;
        }

        public String getVehiNo() {
            return vehiNo;
        }

        public void setVehiNo(String vehiNo) {
            this.vehiNo = vehiNo;
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

        public String getPuc() {
            return puc;
        }

        public void setPuc(String puc) {
            this.puc = puc;
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

        public String getUploaddate() {
            return uploaddate;
        }

        public void setUploaddate(String uploaddate) {
            this.uploaddate = uploaddate;
        }

    }

}
