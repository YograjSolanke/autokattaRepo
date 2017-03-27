package autokatta.com.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ak-005 on 27/3/17.
 */

public class GetSkillsResponse {
    @SerializedName("skill_Id")
    @Expose
    private String skillId;
    @SerializedName("skillNames")
    @Expose
    private String skillNames;

    public String getSkillId() {
        return skillId;
    }

    public void setSkillId(String skillId) {
        this.skillId = skillId;
    }

    public String getSkillNames() {
        return skillNames;
    }

    public void setSkillNames(String skillNames) {
        this.skillNames = skillNames;
    }

}
