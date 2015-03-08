package com.jam.ksm.cupworthy;

/**
 * DB helper
 * Created by kimberlystrauch on 2/19/15.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;


public class MySQLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_BAC = "bac_table";
    //public static final String COLUMN_ID = "_id";
    public static final String COLUMN_DRINKS = "drinks";
    public static final String COLUMN_GENDER = "gender";
    public static final String COLUMN_WEIGHT = "weight";
    public static final String COLUMN_BAC = "bac";
    private String[] ALL_COLUMNS = {COLUMN_DRINKS,
            COLUMN_GENDER, COLUMN_WEIGHT, COLUMN_BAC };


    //public static final String COLUMN_COMMENT = "comment";

    private static final String DATABASE_NAME = "bac.db";
    private static final int DATABASE_VERSION = 1;


    // Database creation sql statement

    private static final String DATABASE_CREATE = "create table if not exists "
            + TABLE_BAC + "(" + COLUMN_DRINKS
            + " INTEGER NOT NULL, " + COLUMN_GENDER
            + " TEXT NOT NULL, " + COLUMN_WEIGHT + " INTEGER NOT NULL, " + COLUMN_BAC + " FLOAT NOT NULL );";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        //database.execSQL("DROP TABLE IF EXISTS " + TABLE_BAC);
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BAC);
        onCreate(db);
    }


    private BACEntry cursorToEntry(Cursor cursor) {
        BACEntry entry = new BACEntry();

        if (cursor != null && cursor.moveToFirst()) {
            Log.d("CS69", "cursor not null");
            //entry.setId(cursor.getLong(cursor.getColumnIndex(COLUMN_ID)));
            entry.setDrinks(cursor.getInt(cursor
                    .getColumnIndex(COLUMN_DRINKS)));
            entry.setGender(cursor.getString(cursor
                    .getColumnIndex(COLUMN_GENDER)));
            entry.setWeight(cursor.getInt(cursor.getColumnIndex(COLUMN_WEIGHT)));
            entry.setBAC(cursor.getDouble(cursor.getColumnIndex(COLUMN_BAC)));


        }
        cursor.close();
        return entry;
    }

    // query a specific entry using its row index
    public BACEntry fetchEntry(String gender, int weight, int drinks) throws SQLException {
        SQLiteDatabase database = getWritableDatabase();


        /*Cursor cursor = database.query(TABLE_BAC, ALL_COLUMNS,
                COLUMN_DRINKS + "=?" + " and "+ COLUMN_WEIGHT+ "=?" + weight + " and " + COLUMN_GENDER + "=?", new String[]{Integer.toString(drinks), Integer.toString(weight),gender},null, null, null,null);

      */



        Cursor cursor = database.query(TABLE_BAC, ALL_COLUMNS,
                COLUMN_DRINKS + " = ? AND " + COLUMN_GENDER +" = ? AND " +  COLUMN_WEIGHT + " = ?", new String[] {"" + drinks, gender, "" + weight },null, null, null,null);

        BACEntry entry = new BACEntry();
        if (cursor != null) {
            //cursor.moveToFirst();
            Log.d("CS69", "moved to first cursor");
            // get entry from cursor and return it
            entry = cursorToEntry(cursor);

            // close what we opened...
        }

        cursor.close();
        database.close();
        return entry;
    }


    public int insertEntry(String line) {
        Log.d("CS69", "INSERTING ENTRY");
        ContentValues values = new ContentValues();

        String[] entry_arr = line.split(",");

        BACEntry entry = new BACEntry();
        entry.setDrinks(Integer.parseInt(entry_arr[0]));
        entry.setGender(entry_arr[1]);
        entry.setWeight(Integer.parseInt(entry_arr[2]));
        entry.setBAC(Double.parseDouble(entry_arr[3]));

        // put all the info into content values with appropriate keys
        values.put(COLUMN_DRINKS, entry.getDrinks());
        values.put(COLUMN_GENDER, entry.getGender());
        values.put(COLUMN_WEIGHT, entry.getWeight());
        values.put(COLUMN_BAC, entry.getBAC());

        SQLiteDatabase database = getWritableDatabase();
        int insertId = (int) database.insert(TABLE_BAC, null, values);

        //entry.setId(insertId);
        // Log the comment stored
        Log.d("CS69", "BAC entry with insert ID = " + insertId + "drinks = " + entry.getDrinks()
                + " stored!");


        database.close();
        return insertId;
    }
}