package autokatta.com.database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

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

}
