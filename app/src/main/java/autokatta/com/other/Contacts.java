package autokatta.com.other;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ak-001 on 30/8/17.
 */

public class Contacts {
    @SerializedName("Contacts")
    @Expose
    private List<Cont> contacts = null;

    public List<Cont> getContacts() {
        return contacts;
    }

    public void setContacts(List<Cont> contacts) {
        this.contacts = contacts;
    }
}
