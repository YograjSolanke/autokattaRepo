package autokatta.com.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ak-005 on 17/11/17.
 */

public class GetTransferEnquiryRequestResponse {

    @SerializedName("Success")
    @Expose
    private Success success;
    @SerializedName("Error")
    @Expose
    private Object error;

    public Success getSuccess() {
        return success;
    }

    public void setSuccess(Success success) {
        this.success = success;
    }

    public Object getError() {
        return error;
    }

    public void setError(Object error) {
        this.error = error;
    }


    public class Success {

        @SerializedName("Employee")
        @Expose
        private List<Employee> employee = null;

        public List<Employee> getEmployee() {
            return employee;
        }

        public void setEmployee(List<Employee> employee) {
            this.employee = employee;
        }


        public class Employee {

            @SerializedName("TransferEnquiryID")
            @Expose
            private Integer transferEnquiryID;
            @SerializedName("EnquiryID")
            @Expose
            private Integer enquiryID;
            @SerializedName("TransferContact")
            @Expose
            private String transferContact;
            @SerializedName("EnquiryContact")
            @Expose
            private String enquiryContact;
            @SerializedName("MyContact")
            @Expose
            private String myContact;
            @SerializedName("TransferToName")
            @Expose
            private String transferToName;
            @SerializedName("Description")
            @Expose
            private String description;
            @SerializedName("ReasonForTransfer")
            @Expose
            private String reasonForTransfer;
            @SerializedName("MonitorStatus1")
            @Expose
            private String monitorStatus1;
            @SerializedName("Date_1")
            @Expose
            private String date1;
            @SerializedName("Date")
            @Expose
            private String date;
            @SerializedName("PSVN_ID")
            @Expose
            private String pSVNID;
            @SerializedName("Keyword")
            @Expose
            private String keyword;
            @SerializedName("AcceptStatus")
            @Expose
            private String acceptStatus;

            public Integer getTransferEnquiryID() {
                return transferEnquiryID;
            }

            public void setTransferEnquiryID(Integer transferEnquiryID) {
                this.transferEnquiryID = transferEnquiryID;
            }

            public Integer getEnquiryID() {
                return enquiryID;
            }

            public void setEnquiryID(Integer enquiryID) {
                this.enquiryID = enquiryID;
            }

            public String getTransferContact() {
                return transferContact;
            }

            public void setTransferContact(String transferContact) {
                this.transferContact = transferContact;
            }

            public String getEnquiryContact() {
                return enquiryContact;
            }

            public void setEnquiryContact(String enquiryContact) {
                this.enquiryContact = enquiryContact;
            }

            public String getMyContact() {
                return myContact;
            }

            public void setMyContact(String myContact) {
                this.myContact = myContact;
            }

            public String getTransferToName() {
                return transferToName;
            }

            public void setTransferToName(String transferToName) {
                this.transferToName = transferToName;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getReasonForTransfer() {
                return reasonForTransfer;
            }

            public void setReasonForTransfer(String reasonForTransfer) {
                this.reasonForTransfer = reasonForTransfer;
            }

            public String getMonitorStatus1() {
                return monitorStatus1;
            }

            public void setMonitorStatus1(String monitorStatus1) {
                this.monitorStatus1 = monitorStatus1;
            }

            public String getDate1() {
                return date1;
            }

            public void setDate1(String date1) {
                this.date1 = date1;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getPSVNID() {
                return pSVNID;
            }

            public void setPSVNID(String pSVNID) {
                this.pSVNID = pSVNID;
            }

            public String getKeyword() {
                return keyword;
            }

            public void setKeyword(String keyword) {
                this.keyword = keyword;
            }

            public String getAcceptStatus() {
                return acceptStatus;
            }

            public void setAcceptStatus(String acceptStatus) {
                this.acceptStatus = acceptStatus;
            }

        }
    }

}
