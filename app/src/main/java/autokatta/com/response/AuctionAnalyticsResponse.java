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
        private int reachedCount;
        @SerializedName("going_count")
        @Expose
        private int goingCount;
        @SerializedName("going_student")
        @Expose
        private int goingStudent;
        @SerializedName("going_self_student")
        @Expose
        private int goingSelfStudent;
        @SerializedName("going_employee")
        @Expose
        private int goingEmployee;
        @SerializedName("ignore_count")
        @Expose
        private int ignoreCount;
        @SerializedName("ignore_student")
        @Expose
        private int ignoreStudent;
        @SerializedName("ignore_self_student")
        @Expose
        private int ignoreSelfStudent;
        @SerializedName("ignore_employee")
        @Expose
        private int ignoreEmployee;
        @SerializedName("shared_count")
        @Expose
        private int sharedCount;

        public int getReachedCount() {
            return reachedCount;
        }

        public void setReachedCount(int reachedCount) {
            this.reachedCount = reachedCount;
        }

        public int getGoingCount() {
            return goingCount;
        }

        public void setGoingCount(int goingCount) {
            this.goingCount = goingCount;
        }

        public int getGoingStudent() {
            return goingStudent;
        }

        public void setGoingStudent(int goingStudent) {
            this.goingStudent = goingStudent;
        }

        public int getGoingSelfStudent() {
            return goingSelfStudent;
        }

        public void setGoingSelfStudent(int goingSelfStudent) {
            this.goingSelfStudent = goingSelfStudent;
        }

        public int getGoingEmployee() {
            return goingEmployee;
        }

        public void setGoingEmployee(int goingEmployee) {
            this.goingEmployee = goingEmployee;
        }

        public int getIgnoreCount() {
            return ignoreCount;
        }

        public void setIgnoreCount(int ignoreCount) {
            this.ignoreCount = ignoreCount;
        }

        public int getIgnoreStudent() {
            return ignoreStudent;
        }

        public void setIgnoreStudent(int ignoreStudent) {
            this.ignoreStudent = ignoreStudent;
        }

        public int getIgnoreSelfStudent() {
            return ignoreSelfStudent;
        }

        public void setIgnoreSelfStudent(int ignoreSelfStudent) {
            this.ignoreSelfStudent = ignoreSelfStudent;
        }

        public int getIgnoreEmployee() {
            return ignoreEmployee;
        }

        public void setIgnoreEmployee(int ignoreEmployee) {
            this.ignoreEmployee = ignoreEmployee;
        }

        public int getSharedCount() {
            return sharedCount;
        }

        public void setSharedCount(int sharedCount) {
            this.sharedCount = sharedCount;
        }

    }
}
