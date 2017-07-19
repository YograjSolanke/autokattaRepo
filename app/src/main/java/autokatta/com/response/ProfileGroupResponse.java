package autokatta.com.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ak-003 on 19/3/17.
 */

public class ProfileGroupResponse {


        @SerializedName("Success")
        @Expose
        private Success success;

        public Success getSuccess() {
            return success;
        }

        public void setSuccess(Success success) {
            this.success = success;
        }


    public class JoinedGroup {

        @SerializedName("id")
        @Expose
        private int id;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("modified_date")
        @Expose
        private String modifiedDate;
        @SerializedName("groupcount")
        @Expose
        private Integer groupcount;
        @SerializedName("vehiclecount")
        @Expose
        private Integer vehiclecount;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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

        public String getModifiedDate() {
            return modifiedDate;
        }

        public void setModifiedDate(String modifiedDate) {
            this.modifiedDate = modifiedDate;
        }

        public Integer getGroupcount() {
            return groupcount;
        }

        public void setGroupcount(Integer groupcount) {
            this.groupcount = groupcount;
        }

        public Integer getVehiclecount() {
            return vehiclecount;
        }

        public void setVehiclecount(Integer vehiclecount) {
            this.vehiclecount = vehiclecount;
        }

    }

    public class MyGroup {

        @SerializedName("id")
        @Expose
        private int id;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("modified_date")
        @Expose
        private String modifiedDate;
        @SerializedName("groupcount")
        @Expose
        private Integer groupcount;
        @SerializedName("vehiclecount")
        @Expose
        private Integer vehiclecount;
        @SerializedName("query")
        @Expose
        private String query;
        @SerializedName("adminVehicleCount")
        @Expose
        private String adminVehicleCount;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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

        public String getModifiedDate() {
            return modifiedDate;
        }

        public void setModifiedDate(String modifiedDate) {
            this.modifiedDate = modifiedDate;
        }

        public Integer getGroupcount() {
            return groupcount;
        }

        public void setGroupcount(Integer groupcount) {
            this.groupcount = groupcount;
        }

        public Integer getVehiclecount() {
            return vehiclecount;
        }

        public void setVehiclecount(Integer vehiclecount) {
            this.vehiclecount = vehiclecount;
        }

        public String getQuery() {
            return query;
        }

        public void setQuery(String query) {
            this.query = query;
        }

        public String getAdminVehicleCount() {
            return adminVehicleCount;
        }

        public void setAdminVehicleCount(String adminVehicleCount) {
            this.adminVehicleCount = adminVehicleCount;
        }

    }

    public class Success {

        @SerializedName("My_groups")
        @Expose
        private List<MyGroup> myGroups = null;
        @SerializedName("Joined_groups")
        @Expose
        private List<JoinedGroup> joinedGroups = null;

        public List<MyGroup> getMyGroups() {
            return myGroups;
        }

        public void setMyGroups(List<MyGroup> myGroups) {
            this.myGroups = myGroups;
        }

        public List<JoinedGroup> getJoinedGroups() {
            return joinedGroups;
        }

        public void setJoinedGroups(List<JoinedGroup> joinedGroups) {
            this.joinedGroups = joinedGroups;
        }

    }
}
