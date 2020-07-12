package com.kmr;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.kmr.model.BaseResponse;
import com.kmr.model.OrderDao;
import com.kmr.model.Place;
import com.kmr.model.PlaceDao;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import retrofit2.Response;
import retrofit2.http.Url;


public class CustomInfoWindowMapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener {

    private GoogleMap mMap;

    private MapWrapperLayout mapWrapperLayout;
    private ViewGroup infoWindow;
    private TextView title;
    private TextView price;
    private TextView jarak;
    private TextView address;
    private TextView address2;
    private Button btnPesan;
    private ImageView imageView;
    private OnInfoWindowElemTouchListener infoButtonListener;

    private Map<String, Place> placeMap = new HashMap<>();

    private String placeType;

    private APIInterface apiInterface;
    private LocationManager locationManager;

    private double latUser;
    private double lonUser;

    private Place placeClicked;
    private Bitmap iconClicked;
    private Drawable drawableImg;

    @Override
    public void onMapReady(GoogleMap googleMap) {

        placeType = getIntent().getStringExtra("placeType");

//        populatePlace();

        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        mMap.setMyLocationEnabled(true);

        mapWrapperLayout = (MapWrapperLayout) findViewById(R.id.map_relative_layout);

        // MapWrapperLayout initialization
        // 39 - default marker height
        // 20 - offset between the default InfoWindow bottom edge and it's content bottom edge
        mapWrapperLayout.init(mMap, getPixelsFromDp(this, 39 + 20));


        // We want to reuse the info window for all the markers,
        // so let's create only one class member instance
        this.infoWindow = (ViewGroup) getLayoutInflater().inflate(R.layout.custom_info_window, null);

        this.title = (TextView) infoWindow.findViewById(R.id.title);
        this.price = (TextView) infoWindow.findViewById(R.id.price);
        this.jarak = (TextView) infoWindow.findViewById(R.id.jarak);
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
                openPesanActivity(placeClicked);
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

                placeClicked = placeMap.get(marker.getTitle());

                Picasso.get().load(placeClicked.getImage())
                        .into(imageView);

                title.setText(placeClicked.getTitle());
                title.setTypeface(title.getTypeface(), Typeface.BOLD);

                address.setText(placeClicked.getAddress());
                address2.setText(placeClicked.getAddress2());
                price.setText("Harga: Rp. " +
                        String.format("%,d", (Double.valueOf(placeClicked.getPrice())).intValue()) + "/" +
                        placeClicked.getDurasi());

                float sdf = distFrom(latUser, lonUser, placeClicked.getLat(), placeClicked.getLon());

                jarak.setText("jarak: ~" + String.format("%.02f", sdf) + " km");

                infoButtonListener.setMarker(marker);

                // We must call this to set the current marker and infoWindow references
                // to the MapWrapperLayout
                mapWrapperLayout.setMarkerWithInfoWindow(marker, infoWindow);
                return infoWindow;
            }
        });

        locationManager = (LocationManager) getSystemService(
                Context.LOCATION_SERVICE);

        Location loc = getLastKnownLocation();

        if (Objects.nonNull(loc)) {

            IsiTempat isiTempat = new IsiTempat();
            isiTempat.execute(this);

            latUser = loc.getLatitude();
            lonUser = loc.getLongitude();

//        LatLng latLngCawang = new LatLng(-6.24737081, 106.87092874);
            LatLng latLngCawang = new LatLng(latUser, lonUser);

            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(latLngCawang)
                    .zoom(12)
                    .build();

            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

            Toast.makeText(getApplicationContext(),
                    "lat: " + loc.getLatitude(), Toast.LENGTH_LONG)
                    .show();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_info_window);

        mapWrapperLayout = (MapWrapperLayout) findViewById(R.id.map_relative_layout);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    public static int getPixelsFromDp(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    @Override
    public void onLocationChanged(Location location) {

        Toast.makeText(getApplicationContext(),
                "lat on change: " + location.getLatitude(), Toast.LENGTH_LONG)
                .show();

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
    }

    private class IsiTempat extends AsyncTask<Context, String, String> {

        @Override
        protected String doInBackground(Context... contexts) {
            publishProgress("Loading..."); // Calls onProgressUpdate()

            try {

                apiInterface = APIClient.getClient().create(APIInterface.class);
                final Response<BaseResponse<List<PlaceDao>>> result =
                        apiInterface.getTempat(placeType)
                                .execute();

                populatePlace(result.body().getData());

                //update ui
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "'Login Sukses", Toast.LENGTH_LONG).show();


                        for (Map.Entry<String, Place> entry : placeMap.entrySet()) {
                            Place place = entry.getValue();

                            LatLng latLng = new LatLng(place.getLat(), place.getLon());

                            mMap.addMarker(new MarkerOptions()
                                    .title(place.getTitle())
                                    .position(latLng));
                        }
                    }
                });

                System.out.println("result : " + result);

            } catch (IOException e) {
                e.printStackTrace();
            }

            return "finish";
        }

        @Override
        protected void onPostExecute(String result) {
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onProgressUpdate(String... text) {
        }
    }

    private void populatePlace(List<PlaceDao> placeDaoList) {

        for (PlaceDao placeDao : placeDaoList) {

            Place place = new Place();
            place.setId(placeDao.getId());
            place.setTitle(placeDao.getName());
            place.setLat(placeDao.getLatitude());
            place.setLon(placeDao.getLongitude());
            place.setAddress(placeDao.getAddress());
            place.setAddress2(placeDao.getAddress2());
            place.setDurasi(placeDao.getDurasi());
            place.setPrice(String.valueOf(placeDao.getPrice()));
            place.setImage(placeDao.getImage());

            placeMap.put(placeDao.getName(), place);

        }
    }

    public void openPesanActivity(Place place) {

        Intent intent = new Intent(getApplicationContext(), PesanActivity.class);
        intent.putExtra("placeId", place.getId());
        intent.putExtra("placeName", place.getTitle());
        intent.putExtra("placeImage", place.getImage());
        intent.putExtra("placeAddress", place.getAddress());
        intent.putExtra("placeAddress2", place.getAddress2());
        intent.putExtra("placeHarga", place.getPrice());
        intent.putExtra("placeDurasi", place.getDurasi());
        startActivity(intent);
    }

    public static float distFrom(double lat1, double lng1, double lat2, double lng2) {
        double earthRadius = 6371000; //meters
        double dLat = Math.toRadians(lat2 - lat1);
        double dLng = Math.toRadians(lng2 - lng1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLng / 2) * Math.sin(dLng / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        float dist = (float) (earthRadius * c);


        return dist / 1000;
    }

    private Location getLastKnownLocation() {
        locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
        List<String> providers = locationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return null;
            }
            Location l = locationManager.getLastKnownLocation(provider);
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                // Found best last known location: %s", l);
                bestLocation = l;
            }
        }
        return bestLocation;
    }

}

