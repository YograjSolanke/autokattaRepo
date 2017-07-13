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
    private Object status;
    @SerializedName("vehiclecount")
    @Expose
    private Integer vehiclecount;
    @SerializedName("productcount")
    @Expose
    private Integer productcount;
    @SerializedName("servicecount")
    @Expose
    private Integer servicecount;

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

    public Object getStatus() {
        return status;
    }

    public void setStatus(Object status) {
        this.status = status;
    }

    public Integer getVehiclecount() {
        return vehiclecount;
    }

    public void setVehiclecount(Integer vehiclecount) {
        this.vehiclecount = vehiclecount;
    }

    public Integer getProductcount() {
        return productcount;
    }

    public void setProductcount(Integer productcount) {
        this.productcount = productcount;
    }

    public Integer getServicecount() {
        return servicecount;
    }

    public void setServicecount(Integer servicecount) {
        this.servicecount = servicecount;
    }
}
}
