package ie.dit.msd_assignment;

/**
 * Created by chris-ubuntu on 21/11/16.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class JournalDBManager {
    public static final String KEY_ROWID 	= "_id";
    public static final String KEY_ARROWCOUNT	= "arrow_count";
    public static final String KEY_DATE 	= "date";
    public static final String KEY_VENUE = "venue";
    public static final String KEY_JOURNALDETAILS 	= "journal_details";
    public static final String KEY_IMAGE = "image";


    private static final String DATABASE_NAME 	= "Journal";
    private static final String DATABASE_TABLE 	= "Entries";
    private static final int DATABASE_VERSION 	= 2;

    //
    private static final String DATABASE_CREATE =
            "create table Entries (_id integer primary key autoincrement, " +
                    "arrow_count number not null," +
                    "date text not null, "  +
                    "venue text not null," +
                    "journal_details text not null,"+
                    "image blob);";

    private final Context context;
    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    //
    public JournalDBManager(Context ctx)
    {
        //
        this.context 	= ctx;
        DBHelper 		= new DatabaseHelper(context);
    }


    private static class DatabaseHelper extends SQLiteOpenHelper
    {
        //
        DatabaseHelper(Context context)
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }


        @Override
        //
        public void onCreate(SQLiteDatabase db)
        {

            db.execSQL(DATABASE_CREATE);
        }

        @Override
        //
        public void onUpgrade(SQLiteDatabase db, int oldVersion,
                              int newVersion)
        {
            String upgradeQuery = "ALTER TABLE "+DATABASE_TABLE+" ADD COLUMN image BLOB";
            if(newVersion == 2 && oldVersion == 1) {
                db.execSQL(upgradeQuery);
            }
        }
    }   //

    public JournalDBManager open() throws SQLException
    {
        db     = DBHelper.getWritableDatabase();
        return this;
    }

    public void close()
    {
        DBHelper.close();
    }

    public long insertEntry(int arrowCount, String date, String venue, String details, byte[] image)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_ARROWCOUNT, arrowCount);
        initialValues.put(KEY_DATE, date);
        initialValues.put(KEY_VENUE, venue);
        initialValues.put(KEY_JOURNALDETAILS, details);
        initialValues.put(KEY_IMAGE, image);
        return db.insert(DATABASE_TABLE, null, initialValues);
    }

    public boolean deleteEntry(long rowId)
    {
        //
        return db.delete(DATABASE_TABLE, KEY_ROWID +
                "=" + rowId, null) > 0;
    }

    public Cursor getAllEntries()
    {
        return db.query(DATABASE_TABLE, new String[]
                        {
                                KEY_ROWID,
                                KEY_ARROWCOUNT,
                                KEY_DATE,
                                KEY_VENUE,
                                KEY_JOURNALDETAILS,
                                KEY_IMAGE
                        },
                null, null, null, null, null);
    }

    public Cursor getEntry(long rowId) throws SQLException
    {
        Cursor mCursor =
                db.query(true, DATABASE_TABLE, new String[]
                                {
                                        KEY_ROWID,
                                        KEY_ARROWCOUNT,
                                        KEY_DATE,
                                        KEY_VENUE,
                                        KEY_JOURNALDETAILS,
                                        KEY_IMAGE
                                },
                        KEY_ROWID + "=" + rowId,  null, null, null, null, null);

        if (mCursor != null)
        {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    //
    public boolean updateEntry(long rowId, int arrowCount, String date, String venue, String entry)
    {
        ContentValues args = new ContentValues();
        args.put(KEY_ARROWCOUNT, arrowCount);
        args.put(KEY_DATE, date);
        args.put(KEY_VENUE, venue);
        args.put(KEY_JOURNALDETAILS, entry);
        return db.update(DATABASE_TABLE, args,
                KEY_ROWID + "=" + rowId, null) > 0;
    }
}
