package autokatta.com.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ak-004 on 10/4/17.
 */

public class BroadcastMessageResponse {


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

        @SerializedName("Msg_id")
        @Expose
        private String msgId;
        @SerializedName("Broadcast_Msg_id")
        @Expose
        private String broadcastMsgId;
        @SerializedName("sender")
        @Expose
        private String sender;
        @SerializedName("receiver")
        @Expose
        private String receiver;
        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("product_id")
        @Expose
        private String productId;
        @SerializedName("service_id")
        @Expose
        private String serviceId;
        @SerializedName("vehicle_id")
        @Expose
        private String vehicleId;
        @SerializedName("date")
        @Expose
        private String date;

        public String getMsgId() {
            return msgId;
        }

        public void setMsgId(String msgId) {
            this.msgId = msgId;
        }

        public String getBroadcastMsgId() {
            return broadcastMsgId;
        }

        public void setBroadcastMsgId(String broadcastMsgId) {
            this.broadcastMsgId = broadcastMsgId;
        }

        public String getSender() {
            return sender;
        }

        public void setSender(String sender) {
            this.sender = sender;
        }

        public String getReceiver() {
            return receiver;
        }

        public void setReceiver(String receiver) {
            this.receiver = receiver;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public String getServiceId() {
            return serviceId;
        }

        public void setServiceId(String serviceId) {
            this.serviceId = serviceId;
        }

        public String getVehicleId() {
            return vehicleId;
        }

        public void setVehicleId(String vehicleId) {
            this.vehicleId = vehicleId;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

    }
}
