package autokatta.com.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ak-005 on 16/10/17.
 */

public class GetUserCategoryResponse {


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

        @SerializedName("UserCategoryID")
        @Expose
        private Integer userCategoryID;
        @SerializedName("ModifiedDate")
        @Expose
        private String modifiedDate;
        @SerializedName("Name")
        @Expose
        private String name;

        public Integer getUserCategoryID() {
            return userCategoryID;
        }

        public void setUserCategoryID(Integer userCategoryID) {
            this.userCategoryID = userCategoryID;
        }

        public String getModifiedDate() {
            return modifiedDate;
        }

        public void setModifiedDate(String modifiedDate) {
            this.modifiedDate = modifiedDate;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }
}
