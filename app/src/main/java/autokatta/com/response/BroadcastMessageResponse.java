package autokatta.com.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

/**
 * Created by ak-004 on 10/4/17.
 */

public class BroadcastMessageResponse {


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


        public Date getNewDate() {
            return newDate;
        }

        public void setNewDate(Date newDate) {
            this.newDate = newDate;
        }

        private Date newDate;


        @SerializedName("Msg_id")
        @Expose
        private Integer msgId;
        @SerializedName("Broadcast_Msg_id")
        @Expose
        private Integer broadcastMsgId;
        @SerializedName("date_1")
        @Expose
        private String date1;
        @SerializedName("date")
        @Expose
        private String date;
        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("product_id")
        @Expose
        private Integer productId;
        @SerializedName("receiver")
        @Expose
        private String receiver;
        @SerializedName("sender")
        @Expose
        private String sender;
        @SerializedName("senderLocation")
        @Expose
        private String senderLocation;
        @SerializedName("senderProfileImage")
        @Expose
        private String senderProfileImage;
        @SerializedName("service_id")
        @Expose
        private Integer serviceId;
        @SerializedName("vehicle_id")
        @Expose
        private Integer vehicleId;

        public Integer getMsgId() {
            return msgId;
        }

        public void setMsgId(Integer msgId) {
            this.msgId = msgId;
        }

        public Integer getBroadcastMsgId() {
            return broadcastMsgId;
        }

        public void setBroadcastMsgId(Integer broadcastMsgId) {
            this.broadcastMsgId = broadcastMsgId;
        }

        public String getDate1() {
            return date1;
        }

        public void setDate1(String date1) {
            this.date1 = date1;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public Integer getProductId() {
            return productId;
        }

        public void setProductId(Integer productId) {
            this.productId = productId;
        }

        public String getReceiver() {
            return receiver;
        }

        public void setReceiver(String receiver) {
            this.receiver = receiver;
    }

        public String getSender() {
            return sender;
        }

        public void setSender(String sender) {
            this.sender = sender;
        }

        public String getSenderLocation() {
            return senderLocation;
        }

        public void setSenderLocation(String senderLocation) {
            this.senderLocation = senderLocation;
        }

        public String getSenderProfileImage() {
            return senderProfileImage;
        }

        public void setSenderProfileImage(String senderProfileImage) {
            this.senderProfileImage = senderProfileImage;
        }

        public Integer getServiceId() {
            return serviceId;
        }

        public void setServiceId(Integer serviceId) {
            this.serviceId = serviceId;
        }

        public Integer getVehicleId() {
            return vehicleId;
        }

        public void setVehicleId(Integer vehicleId) {
            this.vehicleId = vehicleId;
        }

    }
}
