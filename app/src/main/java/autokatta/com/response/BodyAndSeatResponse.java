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

    public Success getSuccess() {
        return success;
    }

    public void setSuccess(Success success) {
        this.success = success;
    }

    public class Success {

        @SerializedName("BodyManufacturers")
        @Expose
        private List<BodyManufacturer> bodyManufacturers = null;
        @SerializedName("SeatManufacturers")
        @Expose
        private List<SeatManufacturer> seatManufacturers = null;

        public List<BodyManufacturer> getBodyManufacturers() {
            return bodyManufacturers;
        }

        public void setBodyManufacturers(List<BodyManufacturer> bodyManufacturers) {
            this.bodyManufacturers = bodyManufacturers;
        }

        public List<SeatManufacturer> getSeatManufacturers() {
            return seatManufacturers;
        }

        public void setSeatManufacturers(List<SeatManufacturer> seatManufacturers) {
            this.seatManufacturers = seatManufacturers;
        }


        public class SeatManufacturer {

            @SerializedName("seatManufacturerId")
            @Expose
            private String seatManufacturerId;
            @SerializedName("seatManufacturerName")
            @Expose
            private String seatManufacturerName;

            public String getSeatManufacturerId() {
                return seatManufacturerId;
            }

            public void setSeatManufacturerId(String seatManufacturerId) {
                this.seatManufacturerId = seatManufacturerId;
            }

            public String getSeatManufacturerName() {
                return seatManufacturerName;
            }

            public void setSeatManufacturerName(String seatManufacturerName) {
                this.seatManufacturerName = seatManufacturerName;
            }

        }

        public class BodyManufacturer {

            @SerializedName("bodyManufacturerId")
            @Expose
            private String bodyManufacturerId;
            @SerializedName("bodyManufacturerName")
            @Expose
            private String bodyManufacturerName;

            public String getBodyManufacturerId() {
                return bodyManufacturerId;
            }

            public void setBodyManufacturerId(String bodyManufacturerId) {
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
