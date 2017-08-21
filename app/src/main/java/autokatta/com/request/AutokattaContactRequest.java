package autokatta.com.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ak-005 on 10/8/17.
 */

public class AutokattaContactRequest {

    @SerializedName("Contacts")
    @Expose
    private String contacts;


    @SerializedName("Names")
    @Expose
    private String names;
    @SerializedName("MyContact")
    @Expose
    private String myContact;

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getMyContact() {
        return myContact;
    }

    public void setMyContact(String myContact) {
        this.myContact = myContact;
    }

    public AutokattaContactRequest(String contacts, String names, String myContact) {
        this.contacts = contacts;
        this.names = names;
        this.myContact = myContact;
    }

}

