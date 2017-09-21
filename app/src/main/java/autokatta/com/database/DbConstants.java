package autokatta.com.database;

/**
 * Created by ak-001 on 18/3/17.
 */

public class DbConstants {
    public static final String TAG = "AUTOKATTA";
    static final String DataBase_Name = "autokatta_db";
    static final int DB_version = 1;

    /*
    Table Names...
     */
    static final String tableMyAutokattaContacts = "table_autokatta_contact";
    static final String tableEnquiryCount = "table_enquiry_count";


    //tableMyAutokattaContacts
    public static final String id = "id";
    public static final String userName = "user_name";
    public static final String profilePic = "profile_pic";
    public static final String contact = "contact";
    public static final String followStatus = "follow_status";
    public static final String myStatus = "my_status";
    public static final String groupIds = "groupIds";
    public static final String groupNames = "groupNames";

    //table enquiry count...
    public static final String enq_id = "id";
    public static final String enq_val = "value";

}
