package autokatta.com.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ak-001 on 22/11/17.
 */

public class QuotReviewReply {
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
            @SerializedName("ReviewId")
            @Expose
            private Integer reviewId;
            @SerializedName("SenderContact")
            @Expose
            private String senderContact;
            @SerializedName("CreatedDate_1")
            @Expose
            private String createdDate1;

            public String getReviewString() {
                return reviewString;
            }

            public void setReviewString(String reviewString) {
                this.reviewString = reviewString;
            }

            public Integer getReviewId() {
                return reviewId;
            }

            public void setReviewId(Integer reviewId) {
                this.reviewId = reviewId;
            }

            public String getSenderContact() {
                return senderContact;
            }

            public void setSenderContact(String senderContact) {
                this.senderContact = senderContact;
            }

            public String getCreatedDate1() {
                return createdDate1;
            }

            public void setCreatedDate1(String createdDate1) {
                this.createdDate1 = createdDate1;
            }

        }

        public class ReplayMessage {

            @SerializedName("ReplayString")
            @Expose
            private String replayString;
            @SerializedName("ReplayId")
            @Expose
            private Integer replayId;
            @SerializedName("ReviewId")
            @Expose
            private Integer reviewId;
            @SerializedName("SenderContact")
            @Expose
            private String senderContact;
            @SerializedName("CreatedDate_1")
            @Expose
            private String createdDate1;

            public String getReplayString() {
                return replayString;
            }

            public void setReplayString(String replayString) {
                this.replayString = replayString;
            }

            public Integer getReplayId() {
                return replayId;
            }

            public void setReplayId(Integer replayId) {
                this.replayId = replayId;
            }

            public Integer getReviewId() {
                return reviewId;
            }

            public void setReviewId(Integer reviewId) {
                this.reviewId = reviewId;
            }

            public String getSenderContact() {
                return senderContact;
            }

            public void setSenderContact(String senderContact) {
                this.senderContact = senderContact;
            }

            public String getCreatedDate1() {
                return createdDate1;
            }

            public void setCreatedDate1(String createdDate1) {
                this.createdDate1 = createdDate1;
            }

        }
    }
}
