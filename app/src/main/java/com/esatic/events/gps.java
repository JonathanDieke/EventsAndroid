package com.esatic.events;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

public class gps extends AppCompatActivity implements LocationListener {

    private static final String[] A = {"invalid", "n/a", "fine", "coarse"};
    private static final String[] P = {"invalid", "n/a", "low", "medium", "high"};
    private static final String[] S = {"out of service", "temporarilyunavailable", "available"};
    private TextView output;
    private LocationManager mgr;
    private String best;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps);

        //LocationManager gps = (LocationManager) getSystemService(LOCATION_SERVICE);

        mgr = (LocationManager) getSystemService(LOCATION_SERVICE);
        output = (TextView) findViewById(R.id.output);

        log("Location providers:");

        dumpProviders();

        Criteria criteria = new Criteria();
        best = mgr.getBestProvider(criteria, true);

        log("\nBestprovider is: " + best);
        log("\nLocations(starting with last known):");

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = mgr.getLastKnownLocation(best);
        dumpLocation(location);


    }


    private void log(String string) {
        output.append(string + "\n");
    }

    private void dumpProvider(String provider) {
        LocationProvider info = mgr.getProvider(provider);
        StringBuilder builder = new StringBuilder();
        builder.append("\nLocationProvider[")
                .append("name= ")
                .append(info.getName())
                .append(", enabled= ")
                .append(mgr.isProviderEnabled(provider))
                .append(", getAccuracy= ")
                .append(A[info.getAccuracy() + 1])
                .append(", getPowerRequirement")
                .append(P[info.getPowerRequirement() + 1])
                .append("name= ")
                .append(",hasMonetaryCost=")
                .append(info.hasMonetaryCost())
                .append(",requiresCell=")
                .append(info.requiresCell())
                .append(",requiresNetwork=")
                .append(info.requiresNetwork())
                .append(",requiresSatellite=")
                .append(info.requiresSatellite())
                .append(",supportsAltitude=")
                .append(info.supportsAltitude())
                .append(",supportsBearing=")
                .append(info.supportsBearing())
                .append(",supportsSpeed=")
                .append(info.supportsSpeed())
                .append("]");
        log(builder.toString());
    }

    private void dumpProviders() {
        List<String> providers = mgr.getAllProviders();
        for (String provider : providers) {
            dumpProvider(provider);
        }
    }

    private void dumpLocation(Location location) {
        if (location == null) {
            log("\nLocation[unknown]");
        } else {
            log("\n" + location.toString());
            log("\nLatitude= " + location.getLatitude());
            log("\nLongitude= " + location.getLongitude());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();// Débute les mises à jour (la documentation recommande un délai >=60 000 ms)
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mgr.requestLocationUpdates(best, 15000, 1, this);
    }

    @Override
    protected void onPause() {
        super.onPause();// Arrête les mises à jour pour économiser la batterie quand l’appli est en pause
        mgr.removeUpdates(this);
    }
    public void onLocationChanged(Location location) {
        dumpLocation(location);
    }

    @Override
    public void onProviderDisabled(String provider) {
        log("\nProviderdisabled:"+ provider);
    }

    @Override
    public void onProviderEnabled(String provider) {
        log("\nProviderenabled:"+ provider);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        log("\nProviderstatuschanged:"+ provider + ", status="+S[status] + ", extras =" + extras);
    }
}