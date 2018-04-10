package id.ac.polban.jtk.location;

import android.location.Location;
import android.os.Bundle;

/**
 * Interface untuk mendapatkan lokasi GPS
 *
 * @author Mufid Jamaluddin
 */

public interface IGpsLocationListener
{

    /**
     * Dipanggil jika lokasi berubah
     * @param location
     */
    void onLocationChanged(Location location);

    /**
     * Dipanggil jika provider status berubah
     * @param provider
     * @param status
     * @param bundle
     */
    void onStatusChanged(String provider, int status, Bundle bundle);

    /**
     * Dipanggil jika provider telah di-enabled oleh user
     *
     * @param provider
     */
    void onProviderEnabled(String provider);

    /**
     * Dipanggil jika provider telah di-disabled oleh user
     *
     * @param provider
     */
    void onProviderDisabled(String provider);

}