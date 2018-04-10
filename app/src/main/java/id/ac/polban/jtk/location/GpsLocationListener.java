package id.ac.polban.jtk.location;

/*
  Referensi :
       https://developer.android.com/reference/android/location/LocationListener.html

  @author Mufid Jamaluddin
 */

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

/**
 * Mendapatkan lokasi dari Gps
 */
public class GpsLocationListener implements LocationListener
{
    /**
     * Posisi pada GPS
     */
    private static Location currentLoc;


    /**
     * Menghitung Jarak dalam Meter
     *
     * @param fromLat
     * @param fromLong
     * @return
     */
    public static Float getDistance(Double fromLat, Double fromLong, String fromProvider)
    {
        Location fromLoc = new Location(fromProvider);
        fromLoc.setLatitude(fromLat);
        fromLoc.setLongitude(fromLong);

        Float distance = currentLoc.distanceTo(fromLoc);

        return distance;
    }

    /**
     * Dipanggil jika lokasi berubah
     * @param location
     */
    @Override
    public void onLocationChanged(Location location)
    {
        currentLoc = location;

    }

    /**
     * Dipanggil jika provider status berubah
     *
     * @param provider
     * @param status
     * @param bundle
     */
    @Override
    public void onStatusChanged(String provider, int status, Bundle bundle)
    {

    }

    /**
     * Dipanggil jika provider telah di-enabled oleh user
     *
     * @param provider
     */
    @Override
    public void onProviderEnabled(String provider)
    {

    }

    /**
     * Dipanggil jika provider telah di-disabled oleh user
     *
     * @param provider
     */
    @Override
    public void onProviderDisabled(String provider)
    {

    }

}