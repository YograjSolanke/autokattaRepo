package autokatta.com.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ak-003 on 10/11/17.
 */

public class GroupDetailedResponse {

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

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("GroupMember")
        @Expose
        private String groupMember;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("modified_date_1")
        @Expose
        private String modifiedDate1;
        @SerializedName("PrivacyStatus")
        @Expose
        private String privacyStatus;
        @SerializedName("groupcount")
        @Expose
        private String groupcount;
        @SerializedName("vehiclecount")
        @Expose
        private Integer vehiclecount;
        @SerializedName("productcount")
        @Expose
        private Integer productcount;
        @SerializedName("servicecount")
        @Expose
        private Integer servicecount;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getGroupMember() {
            return groupMember;
        }

        public void setGroupMember(String groupMember) {
            this.groupMember = groupMember;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getModifiedDate1() {
            return modifiedDate1;
        }

        public void setModifiedDate1(String modifiedDate1) {
            this.modifiedDate1 = modifiedDate1;
        }

        public String getPrivacyStatus() {
            return privacyStatus;
        }

        public void setPrivacyStatus(String privacyStatus) {
            this.privacyStatus = privacyStatus;
        }

        public String getGroupcount() {
            return groupcount;
        }

        public void setGroupcount(String groupcount) {
            this.groupcount = groupcount;
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
