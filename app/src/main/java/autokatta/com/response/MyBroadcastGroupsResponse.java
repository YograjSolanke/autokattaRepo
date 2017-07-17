package autokatta.com.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ak-003 on 23/3/17.
 */

public class MyBroadcastGroupsResponse {
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

        @SerializedName("Group_id")
        @Expose
        private int groupId;
        @SerializedName("Group_title")
        @Expose
        private String groupTitle;
        @SerializedName("Group_Owner")
        @Expose
        private String groupOwner;
        @SerializedName("Group_MemberContacts")
        @Expose
        private String groupMemberContacts;
        @SerializedName("Group_status")
        @Expose
        private String groupStatus;
        @SerializedName("Grp_MemberCount")
        @Expose
        private String grpMemberCount;
        @SerializedName("Grp_created_date")
        @Expose
        private String grpCreatedDate;
        @SerializedName("grp_update_date")
        @Expose
        private String grpUpdateDate;
        @SerializedName("delete_date")
        @Expose
        private String deleteDate;

        public int getGroupId() {
            return groupId;
        }

        public void setGroupId(int groupId) {
            this.groupId = groupId;
        }

        public String getGroupTitle() {
            return groupTitle;
        }

        public void setGroupTitle(String groupTitle) {
            this.groupTitle = groupTitle;
        }

        public String getGroupOwner() {
            return groupOwner;
        }

        public void setGroupOwner(String groupOwner) {
            this.groupOwner = groupOwner;
        }

        public String getGroupMemberContacts() {
            return groupMemberContacts;
        }

        public void setGroupMemberContacts(String groupMemberContacts) {
            this.groupMemberContacts = groupMemberContacts;
        }

        public String getGroupStatus() {
            return groupStatus;
        }

        public void setGroupStatus(String groupStatus) {
            this.groupStatus = groupStatus;
        }

        public String getGrpMemberCount() {
            return grpMemberCount;
        }

        public void setGrpMemberCount(String grpMemberCount) {
            this.grpMemberCount = grpMemberCount;
        }

        public String getGrpCreatedDate() {
            return grpCreatedDate;
        }

        public void setGrpCreatedDate(String grpCreatedDate) {
            this.grpCreatedDate = grpCreatedDate;
        }

        public String getGrpUpdateDate() {
            return grpUpdateDate;
        }

        public void setGrpUpdateDate(String grpUpdateDate) {
            this.grpUpdateDate = grpUpdateDate;
        }

        public String getDeleteDate() {
            return deleteDate;
        }

        public void setDeleteDate(String deleteDate) {
            this.deleteDate = deleteDate;
        }

    }
}
