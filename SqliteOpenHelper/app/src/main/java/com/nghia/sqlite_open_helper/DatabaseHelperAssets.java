package com.nghia.sqlite_open_helper;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by nghia.vuong on 04,May,2021
 */
class DatabaseHelperAssets extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    public static String DATABASE_NAME = "DATA.DB";
    private static File DATABASE_FILE;
    private static DatabaseHelperAssets mInstance;
    // This is an indicator if we need to copy the
    // database file.
    private boolean mNotCopyFileSqlLite = false;
    private boolean mIsUpgraded = false;
    private Context mContext;
    /**
     * number of users of the database connection.
     */
    private int mOpenConnections = 0;

    // --- singleton database
    private DatabaseHelperAssets(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        this.mContext = context;

        SQLiteDatabase database = null;
        try {
            database = getReadableDatabase(); // Read only data

            if (database != null)
                database.close();

            DATABASE_FILE = context.getDatabasePath(DATABASE_NAME);

            if (mNotCopyFileSqlLite) {
                CopyDatabase();
            }

            if (mIsUpgraded) {
                DoUpgrade();
            }

        } catch (SQLiteException ignored) {
        }
        finally {
            if (database != null && database.isOpen()) {
                database.close();
            }
        }
    }

    synchronized static public DatabaseHelperAssets GetInstance(Context context) {

        if (mInstance == null) {
            mInstance = new DatabaseHelperAssets(context);
        }

        return mInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        mNotCopyFileSqlLite = true;
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int old_version, int new_version) {
        mNotCopyFileSqlLite = true;
        mIsUpgraded = true;
    }

    /**
     * called if a database upgrade is needed
     */
    private void DoUpgrade() {
        // implement the database upgrade here.
    }


    @Override
    public synchronized void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        // increment the number of users of the database connection.
        mOpenConnections++;
        if (!db.isReadOnly()) {
            // Enable foreign key constraints
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }

    /**
     * implementation to avoid closing the database connection while it is in
     * use by others.
     */
    @Override
    public synchronized void close() {
        mOpenConnections--;
        if (mOpenConnections == 0) {
            super.close();
        }
    }

    /**
     * Now copy database
     */
    private void CopyDatabase() {
        AssetManager assetManager = mContext.getResources().getAssets();
        InputStream in = null;
        OutputStream out = null;
        try {
            in = assetManager.open(DATABASE_NAME);
            out = new FileOutputStream(DATABASE_FILE);
            byte[] buffer = new byte[1024];
            int read = 0;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
        } catch (IOException e) {
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                }
            }
        }
        SetDatabaseVersion();
        mNotCopyFileSqlLite = false;
    }

    private void SetDatabaseVersion() {
        SQLiteDatabase db = null;
        try {
            db = SQLiteDatabase.openDatabase(DATABASE_FILE.getAbsolutePath(), null, SQLiteDatabase.OPEN_READWRITE);
            db.execSQL("PRAGMA user_version = " + VERSION);
        } catch (SQLiteException e) {
            e.printStackTrace();
        } finally {
            if (db != null && db.isOpen()) {
                db.close();
            }
        }
    }

    /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     *
     * @return true if it exists, false if it doesn't
     */
    public static boolean checkDataBase(String DB_PATH, String DB_NAME) {
        SQLiteDatabase checkDB;
        try {
            checkDB = SQLiteDatabase.openDatabase(DB_PATH, null, SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {
            //database does't exist yet.
            return false;
        }

        if (checkDB != null) {
            checkDB.close();
            return true;
        } else {
            return false;
        }


    }

    private void ClearData() {
        if (checkDataBase(DATABASE_FILE.getPath(), DATABASE_NAME)) {
            mContext.deleteDatabase(DATABASE_NAME);
        }
    }
}
