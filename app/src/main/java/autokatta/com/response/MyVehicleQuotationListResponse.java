package autokatta.com.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ak-004 on 20/9/17.
 */

public class MyVehicleQuotationListResponse {
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

        @SerializedName("CustContact")
        @Expose
        private String custContact;
        @SerializedName("ReservePrice")
        @Expose
        private Double reservePrice;
        @SerializedName("CreatedDate_1")
        @Expose
        private String createdDate1;
        @SerializedName("CreatedDate")
        @Expose
        private String createdDate;
        @SerializedName("CustomerName")
        @Expose
        private String customerName;
        @SerializedName("Query")
        @Expose
        private String query;
        private int vehicleID;

        public String getCustContact() {
            return custContact;
        }

        public void setCustContact(String custContact) {
            this.custContact = custContact;
        }

        public Double getReservePrice() {
            return reservePrice;
        }

        public void setReservePrice(Double reservePrice) {
            this.reservePrice = reservePrice;
        }

        public String getCreatedDate1() {
            return createdDate1;
        }

        public void setCreatedDate1(String createdDate1) {
            this.createdDate1 = createdDate1;
        }

        public String getCreatedDate() {
            return createdDate;
        }

        public void setCreatedDate(String createdDate) {
            this.createdDate = createdDate;
        }

        public String getCustomerName() {
            return customerName;
        }

        public void setCustomerName(String customerName) {
            this.customerName = customerName;
        }

        public String getQuery() {
            return query;
        }

        public void setQuery(String query) {
            this.query = query;
        }

        public int getVehicleID() {
            return vehicleID;
        }

        public void setVehicleID(int vehicleID) {
            this.vehicleID = vehicleID;
        }
    }
}
