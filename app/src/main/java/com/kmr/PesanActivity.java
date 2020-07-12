package com.kmr;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Response;
import retrofit2.http.Query;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.kmr.model.BaseResponse;
import com.kmr.model.Place;
import com.kmr.model.PlaceDao;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class PesanActivity extends AppCompatActivity {

    private APIInterface apiInterface;
    private DatePickerDialog picker;
    private EditText tglPesan;
    private EditText lamaPesan;
    private ImageView imgPesan;
    private Button btnPesan;
    private TextView txtPesanPlaceName;
    private TextView txtPesanAddress;
    private TextView txtPesanAddress2;
    private TextView txtPesanHarga;
    private TextView txtPesanTotalHarga;

    private String placeId;
    private String placeName;
    private String email;
    private String tanggalAwalSewa;
    private int durasiSewa;
    private double totalBayar;


    private String placeImage;
    private String placeAddress;
    private String placeAddress2;
    private String placeHarga;
    private String placeDurasi;

    private SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesan);

        //get data dari map yg di klik
        placeId = getIntent().getStringExtra("placeId");
        placeName = getIntent().getStringExtra("placeName");
        placeImage = getIntent().getStringExtra("placeImage");
        placeAddress = getIntent().getStringExtra("placeAddress");
        placeAddress2 = getIntent().getStringExtra("placeAddress2");
        placeHarga = getIntent().getStringExtra("placeHarga");
        placeDurasi = getIntent().getStringExtra("placeDurasi");

        //ambil email dr login
        sharedPref = getApplicationContext()
                .getSharedPreferences(getString(R.string.cpworks_account), Context.MODE_PRIVATE);
        email = sharedPref.getString(getString(R.string.cpworks_account), null);

        imgPesan = (ImageView) findViewById(R.id.imgPesan);

        Picasso.get().load(placeImage)
                .into(imgPesan);

        btnPesan = findViewById(R.id.btnPesan);
        btnPesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                IsiOrder isiOrder = new IsiOrder();
                isiOrder.execute(getApplicationContext());

                Toast.makeText(getApplicationContext(),
                        "Berhasil Melakukan Pesanan", Toast.LENGTH_LONG).show();
                finish();
            }
        });

        tglPesan=(EditText) findViewById(R.id.tglPesan);
        tglPesan.setInputType(InputType.TYPE_NULL);
        tglPesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(PesanActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                tglPesan.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                                tanggalAwalSewa = tglPesan.getText().toString();
                            }
                        }, year, month, day);
                picker.show();
            }
        });

        txtPesanPlaceName = findViewById(R.id.txtPesanPlaceName);
        txtPesanAddress = findViewById(R.id.txtPesanAddress);
        txtPesanAddress2 = findViewById(R.id.txtPesanAddress2);
        txtPesanHarga = findViewById(R.id.txtPesanHarga);
        txtPesanTotalHarga = findViewById(R.id.txtPesanTotalHarga);
        lamaPesan = findViewById(R.id.lamaPesan);

        txtPesanPlaceName.setText(placeName);
        txtPesanAddress.setText(placeAddress);
        txtPesanAddress2.setText(placeAddress2);
        txtPesanHarga.setText(placeHarga);
        txtPesanHarga.setText("Harga: Rp. " +
                String.format("%,d", (Double.valueOf(placeHarga)).intValue()) + "/" + placeDurasi);

        lamaPesan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }
            @Override
            public void onTextChanged(final CharSequence s, int start, int before,
                                      int count) {
            }
            @Override
            public void afterTextChanged(final Editable s) {
                //avoid triggering event when text is too short
                if (s.length() >= 1) {
                    totalBayar = Double.valueOf(s.toString()) * Double.valueOf(placeHarga);
                    txtPesanTotalHarga.setText("Total Harga: Rp. " +
                            String.format("%,d", (Double.valueOf(totalBayar)).intValue()));
                    durasiSewa = Integer.valueOf(s.toString());
                } else {
                    txtPesanTotalHarga.setText("Total Harga: Rp. " +
                            String.format("%,d", (Double.valueOf(placeHarga)).intValue()));
                    totalBayar = Double.valueOf(placeHarga);
                    durasiSewa = Integer.valueOf(s.toString());
                }
            }
        });
    }

    private class IsiOrder extends AsyncTask<Context, String, String> {

        @Override
        protected String doInBackground(Context... contexts) {
            publishProgress("Loading..."); // Calls onProgressUpdate()

            try {

                apiInterface = APIClient.getClient().create(APIInterface.class);
                final Response<BaseResponse<Boolean>> result =
                        apiInterface.orderPesan(
                                email,
                                durasiSewa,
                                placeId,
                                placeName,
                                tanggalAwalSewa,
                                totalBayar)
                                .execute();

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
}