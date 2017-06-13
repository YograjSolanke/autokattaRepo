package autokatta.com.database;

/**
 * Created by ak-001 on 18/3/17.
 */

public class DbQuery {

    /*
    Table My Autokatta Contacts...
     */
    protected static final String create_table_AutokattaContact = "create table " + DbConstants.tableMyAutokattaContacts +
            "(" + DbConstants.id + " integer primary key autoincrement," + DbConstants.userName + " text," +
            DbConstants.profilePic + " text," + DbConstants.contact + " text," + DbConstants.followStatus + " text,"
            + DbConstants.myStatus + " text)";

    protected static final String checkPresentNumber = "Select * from " + DbConstants.tableMyAutokattaContacts +
            " where " + DbConstants.contact + "='";

    protected static final String getAutokattaContact = "select * from " + DbConstants.tableMyAutokattaContacts;

    protected static final String dropAutokattaContact = "drop table if exists '" + DbConstants.tableMyAutokattaContacts + "'";

}
