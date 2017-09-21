package autokatta.com.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
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
    public long addMyAutokattaContact(String name, String profilePic, String contact, String followStatus,
                                      String myStatus, String groupIds, String groupNames) {
        long result = -1;
        try {
            Cursor cursor = null;
            DbOperation operation = new DbOperation(mContext);
            operation.OPEN();
            /*cursor = db.rawQuery(DbQuery.checkPresentNumber + contact + "'", null);
            Log.i("Query", "->" + DbQuery.checkPresentNumber + contact + "'");
            if (cursor.getCount() > 0) {
                Log.e("Present", "->");
            } else {*/
            ContentValues values = new ContentValues();
            values.put(DbConstants.userName, name);
            values.put(DbConstants.contact, contact);
            values.put(DbConstants.followStatus, followStatus);
            values.put(DbConstants.myStatus, myStatus);
            values.put(DbConstants.groupIds, groupIds);
            values.put(DbConstants.groupNames, groupNames);

            if (profilePic == null) {
                values.put(DbConstants.profilePic, R.mipmap.ic_launcher);
            } else {
                values.put(DbConstants.profilePic, profilePic);
            }
            result = db.insert(DbConstants.tableMyAutokattaContacts, null, values);
            // }
            operation.CLOSE();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /*
    Get My Autokatta Contact...
     */
    public Cursor getAutokattaContact() {
        Cursor cursor = null;
        try {
            cursor = db.rawQuery(DbQuery.getAutokattaContact, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cursor;
    }


    /*
   Delete My Autokatta Contact...
    */
    public void deleteAutokattaContacts() {
        /*Cursor cursor = null;
        try {
            cursor = db.rawQuery(DbQuery.dropAutokattaContact, null);
            cur
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cursor;*/
        db.execSQL(DbQuery.dropAutokattaContact);
    }

    /*
   Delete My Autokatta Contact...
    */
    public void createAutokattaContactTable() {
        db.execSQL(DbQuery.create_table_AutokattaContact);
    }

    /*
        Update My Autokatta Contact follow status...
    */
    public int updateAutoContacts(String contact, String status) {
        int result = 0;
        try {

            //String query = "update table " + DbConstants.table_site + " set " + DbConstants.SiteSimNo + " = '" + no + "' where " + DbConstants.id + "='" + sid + "'";
            ContentValues values = new ContentValues();
            values.put(DbConstants.followStatus, status);
            result = db.update(DbConstants.tableMyAutokattaContacts, values, DbConstants.contact + "='" + contact + "'", null);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    /*
    Get Enquiry Count...
     */
    public void createEnquiryCount() {
        db.execSQL(DbQuery.create_table_Enq_Count);
    }

    /*
    update count
     */
    public long updateEnquiryCount(int count) {
        long result = -1;
        try {
            Cursor cursor = null;
            DbOperation operation = new DbOperation(mContext);
            operation.OPEN();
            ContentValues values = new ContentValues();
            values.put(DbConstants.enq_val, count);
            result = db.insert(DbConstants.tableEnquiryCount, null, values);
            operation.CLOSE();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /*
    Get My Autokatta Contact...
     */
    public Cursor getEnquiryCount() {
        Cursor cursor = null;
        try {
            cursor = db.rawQuery(DbQuery.getEnquiryCount, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cursor;
    }

    /*
   Delete Enquiry Count...
    */
    public void deleteEnquiryCount() {
        db.execSQL(DbQuery.dropEnquiryCount);
    }
}
