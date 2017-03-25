package autokatta.com.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ak-001 on 25/3/17.
 */

public class GetGroupContactsResponse {
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

        @SerializedName("member")
        @Expose
        private String member;
        @SerializedName("contact")
        @Expose
        private String contact;
        @SerializedName("username")
        @Expose
        private String username;
        @SerializedName("dp")
        @Expose
        private String dp;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("vehiclecount")
        @Expose
        private String vehiclecount;

        public String getMember() {
            return member;
        }

        public void setMember(String member) {
            this.member = member;
        }

        public String getContact() {
            return contact;
        }

        public void setContact(String contact) {
            this.contact = contact;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getDp() {
            return dp;
        }

        public void setDp(String dp) {
            this.dp = dp;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getVehiclecount() {
            return vehiclecount;
        }

        public void setVehiclecount(String vehiclecount) {
            this.vehiclecount = vehiclecount;
        }

    }
}
