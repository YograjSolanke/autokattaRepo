package autokatta.com.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ak-001 on 22/11/17.
 */

public class GetReviewQuotResponse {
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

        @SerializedName("ReviewMessage")
        @Expose
        private List<ReviewMessage> reviewMessage = null;
        @SerializedName("ReplayMessage")
        @Expose
        private List<ReplayMessage> replayMessage = null;

        public List<ReviewMessage> getReviewMessage() {
            return reviewMessage;
        }

        public void setReviewMessage(List<ReviewMessage> reviewMessage) {
            this.reviewMessage = reviewMessage;
        }

        public List<ReplayMessage> getReplayMessage() {
            return replayMessage;
        }

        public void setReplayMessage(List<ReplayMessage> replayMessage) {
            this.replayMessage = replayMessage;
        }

        public class ReviewMessage {

            @SerializedName("ReplayMessage")
            @Expose
            private List<ReplayMessage> replayMessage = null;

            public void setReplayMessage(List<ReplayMessage> replayMessage) {
                this.replayMessage = replayMessage;
            }

            public List<ReplayMessage> getReplayMessage() {
                return replayMessage;
            }

            @SerializedName("ReviewString")
            @Expose
            private String reviewString;
            @SerializedName("ReviewQuoteID")
            @Expose
            private Integer reviewQuoteId;
            @SerializedName("SenderContact")
            @Expose
            private String senderContact;
            @SerializedName("CreatedDate")
            @Expose
            private String createdDate;
            @SerializedName("CustomerName")
            @Expose
            private String customerName;
            @SerializedName("CustomerPic")
            @Expose
            private String customerPic;

            public String getReviewString() {
                return reviewString;
            }

            public void setReviewString(String reviewString) {
                this.reviewString = reviewString;
            }

            public Integer getReviewQuoteId() {
                return reviewQuoteId;
            }

            public void setReviewQuoteId(Integer reviewId) {
                this.reviewQuoteId = reviewId;
            }

            public String getSenderContact() {
                return senderContact;
            }

            public void setSenderContact(String senderContact) {
                this.senderContact = senderContact;
            }

            public String getCreatedDate() {
                return createdDate;
            }

            public void setCreatedDate(String createdDate1) {
                this.createdDate = createdDate;
            }

            public String getCustomerName() {
                return customerName;
            }

            public void setCustomerName(String customerName) {
                this.customerName = customerName;
            }

            public String getCustomerPic() {
                return customerPic;
            }

            public void setCustomerPic(String customerPic) {
                this.customerPic = customerPic;
            }
        }

        public class ReplayMessage {

            @SerializedName("ReplayString")
            @Expose
            private String replayString;
            @SerializedName("ReplyQuoteId")
            @Expose
            private Integer replyQuoteId;
            @SerializedName("ReviewQuoteId")
            @Expose
            private Integer reviewQuoteId;
            @SerializedName("SenderContact")
            @Expose
            private String senderContact;
            @SerializedName("CreatedDate")
            @Expose
            private String createdDate;
            @SerializedName("CustomerName")
            @Expose
            private String customerName;
            @SerializedName("CustomerPic")
            @Expose
            private String customerPic;

            public String getReplayString() {
                return replayString;
            }

            public void setReplayString(String replayString) {
                this.replayString = replayString;
            }

            public Integer getReplyQuoteId() {
                return replyQuoteId;
            }

            public void setReplyQuoteId(Integer replayId) {
                this.replyQuoteId = replayId;
            }

            public Integer getReviewQuoteId() {
                return reviewQuoteId;
            }

            public void setReviewQuoteId(Integer reviewId) {
                this.reviewQuoteId = reviewId;
            }

            public String getSenderContact() {
                return senderContact;
            }

            public void setSenderContact(String senderContact) {
                this.senderContact = senderContact;
            }

            public String getCreatedDate() {
                return createdDate;
            }

            public void setCreatedDate(String createdDate1) {
                this.createdDate = createdDate1;
            }

            public String getCustomerName() {
                return customerName;
            }

            public void setCustomerName(String customerName) {
                this.customerName = customerName;
            }

            public String getCustomerPic() {
                return customerPic;
            }

            public void setCustomerPic(String customerPic) {
                this.customerPic = customerPic;
            }
        }
    }
}
