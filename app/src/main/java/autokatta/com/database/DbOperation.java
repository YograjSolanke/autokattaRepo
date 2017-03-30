package autokatta.com.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import autokatta.com.R;

/**
 * Created by ak-001 on 18/3/17.
 */

public class DbOperation {

    private Context mContext;
    private SQLiteDatabase db = null;

    public DbOperation(Context context) {
        this.mContext = context;
    }


    //open the database
    public SQLiteDatabase OPEN() {
        try {

            db = new DbHelper(mContext).getWritableDatabase();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return db;
    }

    //close the database connection
    public void CLOSE() {
        try {
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    My Autokatta Contact...
     */
    public long addMyAutokattaContact(String name, String profilePic, String contact, String followStatus, String myStatus) {
        long result = -1;
        try {
            ContentValues values = new ContentValues();
            values.put(DbConstants.userName, name);
            values.put(DbConstants.contact, contact);
            values.put(DbConstants.followStatus, followStatus);
            values.put(DbConstants.myStatus, myStatus);

            if (profilePic == null) {
                values.put(DbConstants.profilePic, R.mipmap.ic_launcher);
            } else {
                values.put(DbConstants.profilePic, profilePic);
            }

            result = db.insert(DbConstants.tableMyAutokattaContacts, null, values);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
