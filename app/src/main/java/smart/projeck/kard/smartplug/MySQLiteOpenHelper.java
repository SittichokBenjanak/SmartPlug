package smart.projeck.kard.smartplug;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Fourchok on 4/2/2561.
 */

public class MySQLiteOpenHelper extends SQLiteOpenHelper{
    // Explicit
    private static final String DATABASE_NAME = "Smartplug.db";
    private static final int DATABASE_VERSION = 1;
    private static final String CREATE_USER_TABLE = "create table usertable" +
            "(_id integer primary key," +
            "User text," +
            "Password text" +
            "Email text);";




    public MySQLiteOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }   // Constructor

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}   // Main Class
