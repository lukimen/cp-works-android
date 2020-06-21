package com.kmr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class BulananActivity extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener {

    private Spinner spinBulananAwalTahun;
    private Spinner spinBulananAwalBulan;
    private Spinner spinBulananAkhirTahun;
    private Spinner spinBulananAkhirBulan;

    private List<String> tahunList = new ArrayList<>();
    private List<String> bulanList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bulanan);

        insertBulanTahun();

        spinBulananAwalTahun = (Spinner) findViewById(R.id.spinBulananAwalTahun);
        spinBulananAwalBulan = (Spinner) findViewById(R.id.spinBulananAwalBulan);
        spinBulananAkhirTahun = (Spinner) findViewById(R.id.spinBulananAkhirTahun);
        spinBulananAkhirBulan = (Spinner) findViewById(R.id.spinBulananAkhirBulan);

        spinBulananAwalTahun.setOnItemSelectedListener(this);
        ArrayAdapter adapterSpinBulananAwalTahun =
                new ArrayAdapter(this,android.R.layout.simple_spinner_item, tahunList);
        adapterSpinBulananAwalTahun.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinBulananAwalTahun.setAdapter(adapterSpinBulananAwalTahun);

        spinBulananAwalBulan.setOnItemSelectedListener(this);
        ArrayAdapter adapterSpinBulananAwalBulan =
                new ArrayAdapter(this,android.R.layout.simple_spinner_item, bulanList);
        adapterSpinBulananAwalBulan.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinBulananAwalBulan.setAdapter(adapterSpinBulananAwalBulan);

        spinBulananAkhirTahun.setOnItemSelectedListener(this);
        ArrayAdapter adapterSpinBulananAkhirTahun=
                new ArrayAdapter(this,android.R.layout.simple_spinner_item, tahunList);
        adapterSpinBulananAkhirTahun.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinBulananAkhirTahun.setAdapter(adapterSpinBulananAkhirTahun);

        spinBulananAkhirBulan.setOnItemSelectedListener(this);
        ArrayAdapter adapterSpinBulananAkhirBulan =
                new ArrayAdapter(this,android.R.layout.simple_spinner_item, bulanList);
        adapterSpinBulananAkhirBulan.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinBulananAkhirBulan.setAdapter(adapterSpinBulananAkhirBulan);

        findViewById(R.id.btnBulananLaporan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLaporanActivity();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {

//        Toast.makeText(getApplicationContext(),tahunList.get(position) , Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }


    public void openLaporanActivity(){
        Intent intent = new Intent(getApplicationContext(), LaporanBulananActivity.class);
        startActivity(intent);
    }

    private void insertBulanTahun(){
        for (int i = 2020; i < 2031; i++) {
            tahunList.add(String.valueOf(i));
        }

        bulanList.add("Januari");
        bulanList.add("Februari");
        bulanList.add("Mare");
        bulanList.add("April");
        bulanList.add("Mei");
        bulanList.add("Juni");
        bulanList.add("Juli");
        bulanList.add("Agustus");
        bulanList.add("September");
        bulanList.add("Oktober");
        bulanList.add("November");
        bulanList.add("Desember");

    }
}