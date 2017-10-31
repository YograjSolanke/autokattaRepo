package autokatta.com.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ak-003 on 30/10/17.
 */

public class GetMediaResponse {

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

        @SerializedName("Image")
        @Expose
        private List<Image> image = null;
        @SerializedName("Video")
        @Expose
        private List<Video> video = null;
        @SerializedName("Status")
        @Expose
        private List<Status> status = null;

        public List<Image> getImage() {
            return image;
        }

        public void setImage(List<Image> image) {
            this.image = image;
        }

        public List<Video> getVideo() {
            return video;
        }

        public void setVideo(List<Video> video) {
            this.video = video;
        }

        public List<Status> getStatus() {
            return status;
        }

        public void setStatus(List<Status> status) {
            this.status = status;
        }

        public class Image {

            @SerializedName("LiveStatusID")
            @Expose
            private Integer liveStatusID;
            @SerializedName("Status")
            @Expose
            private String status;
            @SerializedName("DateTime_1")
            @Expose
            private String dateTime1;
            @SerializedName("DateTime")
            @Expose
            private String dateTime;
            @SerializedName("UserID")
            @Expose
            private String userContact;
            @SerializedName("Interest")
            @Expose
            private String interest;
            @SerializedName("Type")
            @Expose
            private String type;
            @SerializedName("Image")
            @Expose
            private String image;
            @SerializedName("Video")
            @Expose
            private String video;

            public Integer getLiveStatusID() {
                return liveStatusID;
            }

            public void setLiveStatusID(Integer liveStatusID) {
                this.liveStatusID = liveStatusID;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getDateTime1() {
                return dateTime1;
            }

            public void setDateTime1(String dateTime1) {
                this.dateTime1 = dateTime1;
            }

            public String getDateTime() {
                return dateTime;
            }

            public void setDateTime(String dateTime) {
                this.dateTime = dateTime;
            }

            public String getUserContact() {
                return userContact;
            }

            public void setUserContact(String userID) {
                this.userContact = userID;
            }

            public String getInterest() {
                return interest;
            }

            public void setInterest(String interest) {
                this.interest = interest;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getVideo() {
                return video;
            }

            public void setVideo(String video) {
                this.video = video;
            }

        }

        public class Video {

            @SerializedName("LiveStatusID")
            @Expose
            private Integer liveStatusID;
            @SerializedName("Status")
            @Expose
            private String status;
            @SerializedName("DateTime_1")
            @Expose
            private String dateTime1;
            @SerializedName("DateTime")
            @Expose
            private String dateTime;
            @SerializedName("UserID")
            @Expose
            private String userContact;
            @SerializedName("Interest")
            @Expose
            private String interest;
            @SerializedName("Type")
            @Expose
            private String type;
            @SerializedName("Image")
            @Expose
            private String image;
            @SerializedName("Video")
            @Expose
            private String video;

            public Integer getLiveStatusID() {
                return liveStatusID;
            }

            public void setLiveStatusID(Integer liveStatusID) {
                this.liveStatusID = liveStatusID;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getDateTime1() {
                return dateTime1;
            }

            public void setDateTime1(String dateTime1) {
                this.dateTime1 = dateTime1;
            }

            public String getDateTime() {
                return dateTime;
            }

            public void setDateTime(String dateTime) {
                this.dateTime = dateTime;
            }

            public String getUserContact() {
                return userContact;
            }

            public void setUserContact(String userID) {
                this.userContact = userID;
            }

            public String getInterest() {
                return interest;
            }

            public void setInterest(String interest) {
                this.interest = interest;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getVideo() {
                return video;
            }

            public void setVideo(String video) {
                this.video = video;
            }

        }

        public class Status {

            @SerializedName("LiveStatusID")
            @Expose
            private Integer liveStatusID;
            @SerializedName("Status")
            @Expose
            private String status;
            @SerializedName("DateTime_1")
            @Expose
            private String dateTime1;
            @SerializedName("DateTime")
            @Expose
            private String dateTime;
            @SerializedName("UserID")
            @Expose
            private String userContact;
            @SerializedName("Interest")
            @Expose
            private String interest;
            @SerializedName("Type")
            @Expose
            private String type;
            @SerializedName("Image")
            @Expose
            private String image;
            @SerializedName("Video")
            @Expose
            private String video;

            public Integer getLiveStatusID() {
                return liveStatusID;
            }

            public void setLiveStatusID(Integer liveStatusID) {
                this.liveStatusID = liveStatusID;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getDateTime1() {
                return dateTime1;
            }

            public void setDateTime1(String dateTime1) {
                this.dateTime1 = dateTime1;
            }

            public String getDateTime() {
                return dateTime;
            }

            public void setDateTime(String dateTime) {
                this.dateTime = dateTime;
            }

            public String getUserContact() {
                return userContact;
            }

            public void setUserContact(String userID) {
                this.userContact = userID;
            }

            public String getInterest() {
                return interest;
            }

            public void setInterest(String interest) {
                this.interest = interest;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getVideo() {
                return video;
            }

            public void setVideo(String video) {
                this.video = video;
            }

        }

    }
}
