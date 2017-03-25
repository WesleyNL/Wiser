package br.com.wiser.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

import java.util.List;

/**
 * Created by Jefferson on 30/07/2016.
 */
public class UtilsLocation {
    private static LocationManager locationManager = null;
    public static Location locationPorListener = null;

    private static double latitude = 0;
    private static double longitude = 0;

    public static double getLatitude() {
        return latitude;
    }

    public static double getLongitude() {
        return longitude;
    }

    public static void atualizarLocalizacao(Context context) {
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        if (locationManager != null) {
            if (ActivityCompat.checkSelfPermission(context,
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(context,
                            Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }

            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    UtilsLocation.locationPorListener = location;
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            });
        }
    }

    public static void atualizarCoordenadas(Context context) {

        int status = context.getPackageManager().checkPermission(Manifest.permission.ACCESS_COARSE_LOCATION, context.getPackageName());

        if (status == PackageManager.PERMISSION_GRANTED) {

            LocationManager locMng = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            List<String> providers = locMng.getAllProviders();

            if (providers != null &&
                    providers.contains(LocationManager.NETWORK_PROVIDER) ||
                    providers.contains(LocationManager.PASSIVE_PROVIDER) ||
                    providers.contains(LocationManager.GPS_PROVIDER)) {

                if(locationPorListener == null) {
                    Location loc = locMng.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                    if (loc == null) {
                        loc = locMng.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
                    }

                    if (loc == null) {
                        loc = locMng.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    }

                    if (loc != null) {
                        latitude = loc.getLatitude();
                        longitude = loc.getLongitude();
                    }
                }
            }
        }
    }
}
