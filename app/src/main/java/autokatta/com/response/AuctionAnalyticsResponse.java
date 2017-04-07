package autokatta.com.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ak-003 on 7/4/17.
 */

public class AuctionAnalyticsResponse {
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

        @SerializedName("reached_count")
        @Expose
        private String reachedCount;
        @SerializedName("going_count")
        @Expose
        private String goingCount;
        @SerializedName("going_student")
        @Expose
        private String goingStudent;
        @SerializedName("going_self_student")
        @Expose
        private String goingSelfStudent;
        @SerializedName("going_employee")
        @Expose
        private String goingEmployee;
        @SerializedName("ignore_count")
        @Expose
        private String ignoreCount;
        @SerializedName("ignore_student")
        @Expose
        private String ignoreStudent;
        @SerializedName("ignore_self_student")
        @Expose
        private String ignoreSelfStudent;
        @SerializedName("ignore_employee")
        @Expose
        private String ignoreEmployee;
        @SerializedName("shared_count")
        @Expose
        private String sharedCount;

        public String getReachedCount() {
            return reachedCount;
        }

        public void setReachedCount(String reachedCount) {
            this.reachedCount = reachedCount;
        }

        public String getGoingCount() {
            return goingCount;
        }

        public void setGoingCount(String goingCount) {
            this.goingCount = goingCount;
        }

        public String getGoingStudent() {
            return goingStudent;
        }

        public void setGoingStudent(String goingStudent) {
            this.goingStudent = goingStudent;
        }

        public String getGoingSelfStudent() {
            return goingSelfStudent;
        }

        public void setGoingSelfStudent(String goingSelfStudent) {
            this.goingSelfStudent = goingSelfStudent;
        }

        public String getGoingEmployee() {
            return goingEmployee;
        }

        public void setGoingEmployee(String goingEmployee) {
            this.goingEmployee = goingEmployee;
        }

        public String getIgnoreCount() {
            return ignoreCount;
        }

        public void setIgnoreCount(String ignoreCount) {
            this.ignoreCount = ignoreCount;
        }

        public String getIgnoreStudent() {
            return ignoreStudent;
        }

        public void setIgnoreStudent(String ignoreStudent) {
            this.ignoreStudent = ignoreStudent;
        }

        public String getIgnoreSelfStudent() {
            return ignoreSelfStudent;
        }

        public void setIgnoreSelfStudent(String ignoreSelfStudent) {
            this.ignoreSelfStudent = ignoreSelfStudent;
        }

        public String getIgnoreEmployee() {
            return ignoreEmployee;
        }

        public void setIgnoreEmployee(String ignoreEmployee) {
            this.ignoreEmployee = ignoreEmployee;
        }

        public String getSharedCount() {
            return sharedCount;
        }

        public void setSharedCount(String sharedCount) {
            this.sharedCount = sharedCount;
        }

    }
}
