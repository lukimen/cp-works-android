package com.kmr;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.kmr.model.Place;

import java.util.HashMap;
import java.util.Map;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;


public class CustomInfoWindowMapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private MapWrapperLayout mapWrapperLayout;
    private ViewGroup infoWindow;
    private TextView title;
    private TextView price;
    private TextView address;
    private TextView address2;
    private Button btnPesan;
    private ImageView imageView;
    private OnInfoWindowElemTouchListener infoButtonListener;

    private Map<String, Place> placeMap = new HashMap<>();

    @Override
    public void onMapReady(GoogleMap googleMap) {

        populatePlace();
        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        }
        mMap.setMyLocationEnabled(true);

        mapWrapperLayout = (MapWrapperLayout)findViewById(R.id.map_relative_layout);

        // MapWrapperLayout initialization
        // 39 - default marker height
        // 20 - offset between the default InfoWindow bottom edge and it's content bottom edge
        mapWrapperLayout.init(mMap, getPixelsFromDp(this, 39 + 20));


        // We want to reuse the info window for all the markers,
        // so let's create only one class member instance
        this.infoWindow = (ViewGroup)getLayoutInflater().inflate(R.layout.custom_info_window, null);

        this.title = (TextView) infoWindow.findViewById(R.id.title);
        this.price = (TextView) infoWindow.findViewById(R.id.price);
        this.address = (TextView) infoWindow.findViewById(R.id.address);
        this.address2 = (TextView) infoWindow.findViewById(R.id.address2);
        this.btnPesan = (Button) infoWindow.findViewById(R.id.btnPesan);

        this.imageView = (ImageView) infoWindow.findViewById(R.id.imageView);

        // Setting custom OnTouchListener which deals with the pressed state
        // so it shows up
        this.infoButtonListener = new OnInfoWindowElemTouchListener(btnPesan) {
            @Override
            protected void onClickConfirmed(View v, Marker marker) {
                // Here we can perform some action triggered after clicking the button
//                Toast.makeText(CustomInfoWindowMapsActivity.this, marker.getTitle() + "'s button clicked!", Toast.LENGTH_SHORT).show();
                openPesanActivity();
            }
        };
        this.btnPesan.setOnTouchListener(infoButtonListener);


        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {

                Place placeClicked = placeMap.get(marker.getTitle());

                // Setting up the infoWindow with current's marker info
                imageView.setImageResource(R.drawable.pancoranmas50);

                title.setText(placeClicked.getTitle());
                title.setTypeface(title.getTypeface(), Typeface.BOLD);

                address.setText(placeClicked.getAddress());
                address2.setText(placeClicked.getAddress2());
                price.setText(placeClicked.getPrice());

                infoButtonListener.setMarker(marker);

                // We must call this to set the current marker and infoWindow references
                // to the MapWrapperLayout
                mapWrapperLayout.setMarkerWithInfoWindow(marker, infoWindow);
                return infoWindow;
            }
        });

        LatLng latLngCawang = new LatLng(-6.24737081, 106.87092874);

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(latLngCawang)
                .zoom(12)
                .build();

        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        for (Map.Entry<String, Place> entry: placeMap.entrySet()){
            Place place = entry.getValue();

            LatLng latLng = new LatLng(place.getLat(), place.getLon());

            mMap.addMarker(new MarkerOptions()
                    .title(place.getTitle())
                    .position(latLng));
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_info_window);

        mapWrapperLayout = (MapWrapperLayout)findViewById(R.id.map_relative_layout);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    public static int getPixelsFromDp(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dp * scale + 0.5f);
    }

    private void populatePlace(){
        Place place1 = new Place();
        place1.setTitle("Menara Cawang");
        place1.setLat(-6.24737081);
        place1.setLon(106.87092874);
        place1.setAddress("Jl. Dewi Sartika No 2");
        place1.setAddress2("Jatinegara, Jakarta Timur");
        place1.setPrice("Rp 500.000/hr");

        Place place2 = new Place();
        place2.setTitle("Pancoran Mas Office");
        place2.setLat(-6.24428225);
        place2.setLon(106.84113711);
        place2.setAddress("Jl. Pancoran Barat No 7");
        place2.setAddress2("Pancoran, Jakarta Selatan");
        place2.setPrice("Rp 750.000/hr");

        placeMap.put(place1.getTitle(), place1);
        placeMap.put(place2.getTitle(), place2);
    }

    public void openPesanActivity(){
        Intent intent = new Intent(getApplicationContext(), PesanActivity.class);
        startActivity(intent);
    }
}

