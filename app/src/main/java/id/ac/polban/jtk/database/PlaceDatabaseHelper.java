package id.ac.polban.jtk.database;

/*
  Referensi :
       https://developer.android.com/reference/android/database/sqlite/SQLiteOpenHelper.html
       Buku Head First Android halaman 628

  @author Mufid Jamaluddin
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PlaceDatabaseHelper extends SQLiteOpenHelper
{
    /**
     * Nama Database dan Versi Database
     */
    private static final String DB_NAME = "TourAppI";
    private static final int DB_VERSION = 1;

    /**
     * Konstruktor
     * @param context
     */
    public PlaceDatabaseHelper(Context context)
    {
        // factory null : materi cursor
        super(context, PlaceDatabaseHelper.DB_NAME, null, PlaceDatabaseHelper.DB_VERSION);
    }

    /**
     * Insert Data Place
     *
     * @param db
     * @param name
     * @param description
     * @param latitude
     * @param longitude
     */
    private static void insertPlace(SQLiteDatabase db, String name, String description, double latitude, double longitude)
    {
        ContentValues placeValues = new ContentValues();

        placeValues.put("NAME",name);
        placeValues.put("DESCRIPTION",description);
        placeValues.put("LAT",latitude);
        placeValues.put("LONG",longitude);

        db.insert("PLACE", null, placeValues);
    }

    /**
     * Method untuk Update Database
     *
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    private void updateDatabase(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        if(oldVersion < 1)
        {
            db.execSQL("CREATE TABLE PLACE (" +
                    "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "NAME TEXT," +
                    "DESCRIPTION TEXT," +
                    "LAT REAL," +
                    "LONG REAL)");

            insertPlace(db,"Politeknik Negeri Bandung","Kampus Polban",-6.872250,107.572989);
            insertPlace(db,"Kosan Mufid","Kosan Mufid di Kamar 3",-6.866412,107.573381);
            insertPlace(db,"Masjid LH", "Masjid Lukmanul Hakim Kampus Polban", -6.872864,107.574550);
            insertPlace(db, "Dago Pakar","Tempat Rekreasi", -6.862989, 107.626516);

        }
    }

    /**
     * Method untuk Membuat DB Pertamakali jika Database ini Tidak Ada
     *
     * @param sqLiteDatabase
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        this.updateDatabase(sqLiteDatabase,0, DB_VERSION);
    }

    /**
     * Method untuk Upgrade Database jika Aplikasi Diupgrade
     *
     * @param sqLiteDatabase
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion)
    {
        this.updateDatabase(sqLiteDatabase,oldVersion,newVersion);
    }

    /**
     * Method untuk Downgrade Database jika Aplikasi Didowngrade
     *
     * @param sqLiteDatabase
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onDowngrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion)
    {
        this.updateDatabase(sqLiteDatabase, oldVersion, newVersion);
    }

}