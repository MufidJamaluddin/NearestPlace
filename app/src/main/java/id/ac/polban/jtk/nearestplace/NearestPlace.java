package id.ac.polban.jtk.nearestplace;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import id.ac.polban.jtk.cursoradapter.PlaceCursorAdapter;
import id.ac.polban.jtk.database.PlaceDatabaseHelper;
import id.ac.polban.jtk.location.GpsLocationListener;

public class NearestPlace extends AppCompatActivity
{

    private static final int MINIMUM_TIME_BETWEEN_UPDATES = 30000; // Dalam Satuan Milisecond
    private static final int MINIMUM_DISTANCE_CHANGE_FOR_UPDATES = 1; // Dalam Satuan Meter

    private ListView listPlace;

    private LocationListener locationListener;

    private LocationManager locationManager;

    private String limitItem;

    protected void startGps()
    {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Toast.makeText(this,"Please Activate Your GPS !",Toast.LENGTH_LONG).show();
        }
        else {
            this.locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    MINIMUM_TIME_BETWEEN_UPDATES,
                    MINIMUM_DISTANCE_CHANGE_FOR_UPDATES,
                    this.locationListener
            );
        }
    }

    protected void stopGps()
    {
        this.locationManager.removeUpdates(this.locationListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_nearest_place);

        try
        {
            this.limitItem = getIntent().getExtras().getString("limit");
        }
        catch (Exception e)
        {
            Toast.makeText(this, e.getMessage() + ". Althought, limit will change to 10",Toast.LENGTH_SHORT).show();
            this.limitItem = "10";
        }

        this.locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        this.listPlace = findViewById(R.id.place_list_view);

        this.locationListener = new GpsLocationListener(){

            @Override
            public void onLocationChanged(Location location)
            {
                super.onLocationChanged(location);

                Double curLat = location.getLatitude();
                Double curLong = location.getLongitude();

                SQLiteOpenHelper sqLiteOpenHelper = new PlaceDatabaseHelper(NearestPlace.this);

                try
                {
                    PlaceCursorAdapter placeCursorAdapter;
                    SQLiteDatabase db;

                    db = sqLiteOpenHelper.getReadableDatabase();

                    // Tetapkan Cursor
                    Cursor dbcursor = db.query("PLACE",
                            new String[]{"_id","NAME", "DESCRIPTION", "LAT", "LONG"},
                            null, null, null, null,
                            "ABS(" + curLat + " - LAT) + ABS(" + curLong + " - LONG) ASC",
                            limitItem);

                    placeCursorAdapter = new PlaceCursorAdapter(NearestPlace.this, dbcursor) {

                        @Override
                        public void bindView(View view, Context context, Cursor cursor)
                        {
                            /*
                              Ambil komponen View item_place.xml
                             */
                            TextView textViewTitle = view.findViewById(R.id.title);
                            TextView textViewDesc = view.findViewById(R.id.desc);
                            TextView textViewDist = view.findViewById(R.id.distance);

                            /*
                              Ambil data dari database
                             */
                            String title = cursor.getString(cursor.getColumnIndexOrThrow("NAME"));
                            String description = cursor.getString(cursor.getColumnIndexOrThrow("DESCRIPTION"));
                            double fromLat = cursor.getDouble(cursor.getColumnIndexOrThrow("LAT"));
                            double fromLng = cursor.getDouble(cursor.getColumnIndexOrThrow("LONG"));

                            Float distance = GpsLocationListener.getDistance(fromLat, fromLng, title);

                            /*
                              Tampilkan Data di View item_place.xml
                             */
                            textViewTitle.setText(title);
                            textViewDesc.setText(description);
                            textViewDist.setText(String.format("%s meter", distance));
                        }

                    };

                    /*
                      Set Adapter ke List View activity_nearest_place.xml
                     */
                    listPlace.setAdapter(placeCursorAdapter);


                    // Tutup Koneksi Database
                    if(dbcursor != null && dbcursor.isClosed())
                        dbcursor.close();

                    if(db != null && !db.isOpen())
                        db.close();

                }
                catch(Exception e)
                {
                    Toast.makeText(NearestPlace.this, e.getMessage() , Toast.LENGTH_LONG).show();
                }
            }

        };
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        this.startGps();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        this.stopGps();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();

        if(this.locationListener != null)
            this.locationListener = null;


        if(this.locationManager != null)
            this.locationManager = null;

    }

}