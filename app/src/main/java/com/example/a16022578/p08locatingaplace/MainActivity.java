package com.example.a16022578.p08locatingaplace;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity {
    Spinner spn;

    private GoogleMap map;


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch(requestCode) {
            case 0: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    Toast.makeText(MainActivity.this, "Not Granted", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spn = (Spinner) findViewById(R.id.spinner);

        FragmentManager fm = getSupportFragmentManager();
        SupportMapFragment mapFragment = (SupportMapFragment)
                fm.findFragmentById(R.id.map);


        mapFragment.getMapAsync(new OnMapReadyCallback(){
            @Override
            public void onMapReady(GoogleMap googleMap) {
                map = googleMap;
                //Compass and Zoom
                UiSettings ui = map.getUiSettings();
                ui.setCompassEnabled(true);
                ui.setZoomControlsEnabled(true);

                //Spinner lat lang
                LatLng a = new LatLng(1.436065, 103.786263); //CWP
                LatLng b = new LatLng(1.294460, 103.848070); //Plaza Sing
                LatLng c = new LatLng(1.305480, 103.926338); //Parkway


                Marker markA = map.addMarker(new
                        MarkerOptions()
                        .position(a)
                        .title("HQ - Causeway Point")
                        .snippet("1 Woodlands Square,Singapore 738099")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher)));

                Marker markB = map.addMarker(new
                        MarkerOptions()
                        .position(b)
                        .title("CENTRAL - Plaza Singapura")
                        .snippet("68 Orchard Rd, Singapore 238839")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

                Marker markC = map.addMarker(new
                        MarkerOptions()
                        .position(c)
                        .title("EAST - Parkway Parade")
                        .snippet("80 Marine Parade Rd, Singapore 449269")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));

                map.setMapType(GoogleMap.MAP_TYPE_NORMAL);

                //SHOW CURRENT LOCATION
                int permissionCheck = ContextCompat.checkSelfPermission(MainActivity.this,
                        android.Manifest.permission.ACCESS_FINE_LOCATION);

                if (permissionCheck == PermissionChecker.PERMISSION_GRANTED) {
                    map.setMyLocationEnabled(true);
                } else {
                    Log.e("GMap - Permission", "GPS access has not been granted");
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
                }

                spn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        switch (i) {
                            case 0:
                                if (map != null){
                                    LatLng a = new LatLng(1.436065, 103.786263);
                                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(a,
                                            15));
                                }
                                break;
                            case 1:
                                if (map != null){
                                    LatLng b = new LatLng(1.294460, 103.848070);
                                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(b,
                                            15));
                                }
                                break;
                            case 2:
                                if (map != null){
                                    LatLng c = new LatLng(1.305480, 103.926338);
                                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(c,
                                            15));
                                }
                                break;
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                //Click on Map
                map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        Toast.makeText(MainActivity.this, marker.getTitle(), Toast.LENGTH_LONG).show();
                        return false;
                    }
                });

            }
        });

    }
}
