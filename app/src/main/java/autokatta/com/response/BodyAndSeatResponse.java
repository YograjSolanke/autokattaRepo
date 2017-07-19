package autokatta.com.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ak-001 on 23/3/17.
 */

public class BodyAndSeatResponse {

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

        @SerializedName("bodyManufac")
        @Expose
        private List<BodyManufac> bodyManufac = null;
        @SerializedName("seatManufac")
        @Expose
        private List<SeatManufac> seatManufac = null;

        public List<BodyManufac> getBodyManufac() {
            return bodyManufac;
        }

        public void setBodyManufac(List<BodyManufac> bodyManufac) {
            this.bodyManufac = bodyManufac;
        }

        public List<SeatManufac> getSeatManufac() {
            return seatManufac;
        }

        public void setSeatManufac(List<SeatManufac> seatManufac) {
            this.seatManufac = seatManufac;
        }


        public class SeatManufac {

            @SerializedName("seatManufacturerId")
            @Expose
            private Integer seatManufacturerId;
            @SerializedName("seatManufacturerName")
            @Expose
            private String seatManufacturerName;

            public Integer getSeatManufacturerId() {
                return seatManufacturerId;
            }

            public void setSeatManufacturerId(Integer seatManufacturerId) {
                this.seatManufacturerId = seatManufacturerId;
            }

            public String getSeatManufacturerName() {
                return seatManufacturerName;
            }

            public void setSeatManufacturerName(String seatManufacturerName) {
                this.seatManufacturerName = seatManufacturerName;
            }

        }

        public class BodyManufac {

            @SerializedName("bodyManufacturerId")
            @Expose
            private Integer bodyManufacturerId;
            @SerializedName("bodyManufacturerName")
            @Expose
            private String bodyManufacturerName;

            public Integer getBodyManufacturerId() {
                return bodyManufacturerId;
            }

            public void setBodyManufacturerId(Integer bodyManufacturerId) {
                this.bodyManufacturerId = bodyManufacturerId;
            }

            public String getBodyManufacturerName() {
                return bodyManufacturerName;
            }

            public void setBodyManufacturerName(String bodyManufacturerName) {
                this.bodyManufacturerName = bodyManufacturerName;
            }

        }
    }
}
