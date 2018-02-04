package smart.projeck.kard.smartplug;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Fourchok on 4/2/2561.
 */

public class UserTABLE {
    //Explicit
    private MySQLiteOpenHelper objMySQLiteOpenHelper;
    private SQLiteDatabase writeSqLiteDatabase, readSqLiteDatabase;

    public UserTABLE(Context context) {
        objMySQLiteOpenHelper = new MySQLiteOpenHelper(context);
        writeSqLiteDatabase = objMySQLiteOpenHelper.getWritableDatabase();
        readSqLiteDatabase = objMySQLiteOpenHelper.getReadableDatabase();

    }   // Constructor
}   // Main Class
