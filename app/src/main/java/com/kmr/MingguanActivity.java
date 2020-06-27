package com.kmr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MingguanActivity extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener {

    private Spinner spinMingguanAwalTahun;
    private Spinner spinMingguanAwalMinggu;
    private Spinner spinMingguanAkhirTahun;
    private Spinner spinMingguanAkhirMinggu;

    private List<String> tahunList = new ArrayList<>();
    private List<String> mingguList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mingguan);

        insertMingguTahun();

        spinMingguanAwalTahun = (Spinner) findViewById(R.id.spinMingguanAwalTahun);
        spinMingguanAwalMinggu = (Spinner) findViewById(R.id.spinMingguanAwalMinggu);
        spinMingguanAkhirTahun = (Spinner) findViewById(R.id.spinMingguanAkhirTahun);
        spinMingguanAkhirMinggu = (Spinner) findViewById(R.id.spinMingguanAkhirMinggu);

        spinMingguanAwalTahun.setOnItemSelectedListener(this);
        ArrayAdapter adapterSpinMingguanAwalTahun =
                new ArrayAdapter(this,android.R.layout.simple_spinner_item, tahunList);
        adapterSpinMingguanAwalTahun.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinMingguanAwalTahun.setAdapter(adapterSpinMingguanAwalTahun);

        spinMingguanAwalMinggu.setOnItemSelectedListener(this);
        ArrayAdapter adapterSpinMingguanAwalMinggu =
                new ArrayAdapter(this,android.R.layout.simple_spinner_item, mingguList);
        adapterSpinMingguanAwalMinggu.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinMingguanAwalMinggu.setAdapter(adapterSpinMingguanAwalMinggu);

        spinMingguanAkhirTahun.setOnItemSelectedListener(this);
        ArrayAdapter adapterSpinMingguanAkhirTahun=
                new ArrayAdapter(this,android.R.layout.simple_spinner_item, tahunList);
        adapterSpinMingguanAkhirTahun.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinMingguanAkhirTahun.setAdapter(adapterSpinMingguanAkhirTahun);

        spinMingguanAkhirMinggu.setOnItemSelectedListener(this);
        ArrayAdapter adapterSpinMingguanAkhirMinggu =
                new ArrayAdapter(this,android.R.layout.simple_spinner_item, mingguList);
        adapterSpinMingguanAkhirMinggu.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinMingguanAkhirMinggu.setAdapter(adapterSpinMingguanAkhirMinggu);

        findViewById(R.id.btnMingguanLaporan).setOnClickListener(new View.OnClickListener() {
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

    private void insertMingguTahun(){
        for (int i = 1; i < 53; i++) {
            mingguList.add(String.valueOf(i));
        }
        for (int i = 2020; i < 2031; i++) {
            tahunList.add(String.valueOf(i));
        }

    }

    public void openLaporanActivity(){
        Intent intent = new Intent(getApplicationContext(), LaporanMingguanActivity.class);
        intent.putExtra("tahunAwal", spinMingguanAwalTahun.getSelectedItem().toString());
        intent.putExtra("mingguAwal", String.valueOf(spinMingguanAwalMinggu.getSelectedItemPosition()));
        intent.putExtra("tahunAkhir", spinMingguanAkhirTahun.getSelectedItem().toString());
        intent.putExtra("mingguAkhir", String.valueOf(spinMingguanAkhirMinggu.getSelectedItemPosition()));
        startActivity(intent);
    }

}