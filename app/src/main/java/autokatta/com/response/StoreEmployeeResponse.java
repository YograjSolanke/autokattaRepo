package autokatta.com.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ak-004 on 14/11/17.
 */

public class StoreEmployeeResponse {

    @SerializedName("Success")
    @Expose
    private Success success = null;
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

            @SerializedName("StoreEmplyeeID")
            @Expose
            private Integer storeEmplyeeID;
            @SerializedName("Name")
            @Expose
            private String name;
            @SerializedName("ContactNo")
            @Expose
            private String contactNo;
            @SerializedName("Designation")
            @Expose
            private String designation;
            @SerializedName("StoreID")
            @Expose
            private Integer storeID;
            @SerializedName("Description")
            @Expose
            private String description;
            @SerializedName("Status")
            @Expose
            private String status;
            @SerializedName("Permission")
            @Expose
            private String permission;

            public String getDeleteStatus() {
                return DeleteStatus;
            }

            public void setDeleteStatus(String deleteStatus) {
                DeleteStatus = deleteStatus;
            }

            @SerializedName("DeleteStatus")

            @Expose
            private String DeleteStatus;




            public Integer getStoreEmplyeeID() {
                return storeEmplyeeID;
            }

            public void setStoreEmplyeeID(Integer storeEmplyeeID) {
                this.storeEmplyeeID = storeEmplyeeID;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getContactNo() {
                return contactNo;
            }

            public void setContactNo(String contactNo) {
                this.contactNo = contactNo;
            }

            public String getDesignation() {
                return designation;
            }

            public void setDesignation(String designation) {
                this.designation = designation;
            }

            public Integer getStoreID() {
                return storeID;
            }

            public void setStoreID(Integer storeID) {
                this.storeID = storeID;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getPermission() {
                return permission;
            }

            public void setPermission(String permission) {
                this.permission = permission;
            }

        }

    }


}
