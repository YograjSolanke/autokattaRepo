package autokatta.com.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ak-005 on 16/10/17.
 */

public class GetVehicleRepoInsuranceResponse {

        @SerializedName("Success")
        @Expose
        private List<Success> success = null;
        @SerializedName("Error")
        @Expose
        private Object error;

        public List<Success> getSuccess() {
            return success;
        }

        public void setSuccess(List<Success> success) {
            this.success = success;
        }

        public Object getError() {
            return error;
        }

        public void setError(Object error) {
            this.error = error;
        }

    public class Success {

        @SerializedName("VehicleRepoInsuranceID")
        @Expose
        private Integer vehicleRepoInsuranceID;
        @SerializedName("AccountNumber")
        @Expose
        private String accountNumber;
        @SerializedName("BorrowerName")
        @Expose
        private String borrowerName;
        @SerializedName("BorrowerContact")
        @Expose
        private String borrowerContact;
        @SerializedName("BranchCityName")
        @Expose
        private String branchCityName;
        @SerializedName("BrachMangerName")
        @Expose
        private String brachMangerName;
        @SerializedName("BranchContact")
        @Expose
        private String branchContact;
        @SerializedName("DealerName")
        @Expose
        private String dealerName;
        @SerializedName("StockYardName")
        @Expose
        private String stockYardName;
        @SerializedName("StockYardAddress")
        @Expose
        private String stockYardAddress;
        @SerializedName("InwardDate")
        @Expose
        private String inwardDate;
        @SerializedName("BranchMangerStatus")
        @Expose
        private String branchMangerStatus;
        @SerializedName("BorrowerStatus")
        @Expose
        private String borrowerStatus;
        @SerializedName("VehicleID")
        @Expose
        private Integer vehicleID;
  @SerializedName("ClientName")
        @Expose
        private String ClientName;

        public String getClientName() {
            return ClientName;
        }

        public void setClientName(String clientName) {
            ClientName = clientName;
        }

        public Integer getVehicleRepoInsuranceID() {
            return vehicleRepoInsuranceID;
        }

        public void setVehicleRepoInsuranceID(Integer vehicleRepoInsuranceID) {
            this.vehicleRepoInsuranceID = vehicleRepoInsuranceID;
        }

        public String getAccountNumber() {
            return accountNumber;
        }

        public void setAccountNumber(String accountNumber) {
            this.accountNumber = accountNumber;
        }

        public String getBorrowerName() {
            return borrowerName;
        }

        public void setBorrowerName(String borrowerName) {
            this.borrowerName = borrowerName;
        }

        public String getBorrowerContact() {
            return borrowerContact;
        }

        public void setBorrowerContact(String borrowerContact) {
            this.borrowerContact = borrowerContact;
        }

        public String getBranchCityName() {
            return branchCityName;
        }

        public void setBranchCityName(String branchCityName) {
            this.branchCityName = branchCityName;
        }

        public String getBrachMangerName() {
            return brachMangerName;
        }

        public void setBrachMangerName(String brachMangerName) {
            this.brachMangerName = brachMangerName;
        }

        public String getBranchContact() {
            return branchContact;
        }

        public void setBranchContact(String branchContact) {
            this.branchContact = branchContact;
        }

        public String getDealerName() {
            return dealerName;
        }

        public void setDealerName(String dealerName) {
            this.dealerName = dealerName;
        }

        public String getStockYardName() {
            return stockYardName;
        }

        public void setStockYardName(String stockYardName) {
            this.stockYardName = stockYardName;
        }

        public String getStockYardAddress() {
            return stockYardAddress;
        }

        public void setStockYardAddress(String stockYardAddress) {
            this.stockYardAddress = stockYardAddress;
        }

        public String getInwardDate() {
            return inwardDate;
        }

        public void setInwardDate(String inwardDate) {
            this.inwardDate = inwardDate;
        }

        public String getBranchMangerStatus() {
            return branchMangerStatus;
        }

        public void setBranchMangerStatus(String branchMangerStatus) {
            this.branchMangerStatus = branchMangerStatus;
        }

        public String getBorrowerStatus() {
            return borrowerStatus;
        }

        public void setBorrowerStatus(String borrowerStatus) {
            this.borrowerStatus = borrowerStatus;
        }

        public Integer getVehicleID() {
            return vehicleID;
        }

        public void setVehicleID(Integer vehicleID) {
            this.vehicleID = vehicleID;
        }

    }

}
