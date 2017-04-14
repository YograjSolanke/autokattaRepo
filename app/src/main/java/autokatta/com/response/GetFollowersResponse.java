package autokatta.com.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ak-005 on 14/4/17.
 */

public class GetFollowersResponse {

    @SerializedName("Success")
    @Expose
    private Success success;

    public Success getSuccess() {
        return success;
    }

    public void setSuccess(Success success) {
        this.success = success;
    }


    public class Follower {

        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("contact")
        @Expose
        private String contact;
        @SerializedName("profile_pic")
        @Expose
        private String profilePic;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getContact() {
            return contact;
        }

        public void setContact(String contact) {
            this.contact = contact;
        }

        public String getProfilePic() {
            return profilePic;
        }

        public void setProfilePic(String profilePic) {
            this.profilePic = profilePic;
        }

    }
        public class Following {

            @SerializedName("name")
            @Expose
            private String name;
            @SerializedName("contact")
            @Expose
            private String contact;
            @SerializedName("profile_pic")
            @Expose
            private String profilePic;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getContact() {
                return contact;
            }

            public void setContact(String contact) {
                this.contact = contact;
            }

            public String getProfilePic() {
                return profilePic;
            }

            public void setProfilePic(String profilePic) {
                this.profilePic = profilePic;
            }

        }
            public class Success {

                @SerializedName("Following")
                @Expose
                private List<Following> following = null;
                @SerializedName("Followers")
                @Expose
                private List<Follower> followers = null;

                public List<Following> getFollowing() {
                    return following;
                }

                public void setFollowing(List<Following> following) {
                    this.following = following;
                }

                public List<Follower> getFollowers() {
                    return followers;
                }

                public void setFollowers(List<Follower> followers) {
                    this.followers = followers;
                }
            }
    }


