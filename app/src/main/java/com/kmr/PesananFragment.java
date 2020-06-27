package com.kmr;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

public class PesananFragment extends Fragment{

    private PesananViewModel pesananViewModel;

    private Button btnPesananOrder;
    private Button btnPesananMingguan;
    private Button btnPesananBulanan;
    private TextView txtPesananBulanan;
    private TextView txtPesananMingguan;

    private SharedPreferences sharedPref;
    private String emailLogin;

    private TextView tv;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        pesananViewModel =
                ViewModelProviders.of(this).get(PesananViewModel.class);
        View root = inflater.inflate(R.layout.fragment_pesanan, container, false);

        btnPesananOrder = (Button)root.findViewById(R.id.btnPesananOrder);
        btnPesananMingguan = (Button)root.findViewById(R.id.btnPesananMingguan);
        btnPesananBulanan = (Button)root.findViewById(R.id.btnPesananBulanan);
        txtPesananBulanan = (TextView)root.findViewById(R.id.txtPesananBulanan);
        txtPesananMingguan = (TextView)root.findViewById(R.id.txtPesananMingguan);

        btnPesananOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openOrderActivity();
            }
        });
        btnPesananMingguan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMingguanActivity();
            }
        });
        btnPesananBulanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openBulananActivity();

            }
        });

        sharedPref = getActivity().getSharedPreferences(getString(R.string.cpworks_account), Context.MODE_PRIVATE);
        emailLogin = sharedPref.getString(getString(R.string.cpworks_account), null);

        if (Objects.nonNull(emailLogin)) { // kalo uda login
            if (emailLogin.equalsIgnoreCase("admin")){
                btnPesananMingguan.setVisibility(View.VISIBLE);
                btnPesananBulanan.setVisibility(View.VISIBLE);
                txtPesananMingguan.setVisibility(View.VISIBLE);
                txtPesananBulanan.setVisibility(View.VISIBLE);
            } else {
                btnPesananMingguan.setVisibility(View.INVISIBLE);
                btnPesananBulanan.setVisibility(View.INVISIBLE);
                txtPesananMingguan.setVisibility(View.INVISIBLE);
                txtPesananBulanan.setVisibility(View.INVISIBLE);
            }
        } else {
            btnPesananMingguan.setVisibility(View.INVISIBLE);
            btnPesananBulanan.setVisibility(View.INVISIBLE);
            txtPesananMingguan.setVisibility(View.INVISIBLE);
            txtPesananBulanan.setVisibility(View.INVISIBLE);
        }

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();

        sharedPref = getActivity().getSharedPreferences(getString(R.string.cpworks_account), Context.MODE_PRIVATE);
        emailLogin = sharedPref.getString(getString(R.string.cpworks_account), null);

        if (Objects.nonNull(emailLogin)) { // kalo uda login
            if (emailLogin.equalsIgnoreCase("admin")){
                btnPesananMingguan.setVisibility(View.VISIBLE);
                btnPesananBulanan.setVisibility(View.VISIBLE);
                txtPesananMingguan.setVisibility(View.VISIBLE);
                txtPesananBulanan.setVisibility(View.VISIBLE);
            } else {
                btnPesananMingguan.setVisibility(View.INVISIBLE);
                btnPesananBulanan.setVisibility(View.INVISIBLE);
                txtPesananMingguan.setVisibility(View.INVISIBLE);
                txtPesananBulanan.setVisibility(View.INVISIBLE);
            }
        } else {
            btnPesananMingguan.setVisibility(View.INVISIBLE);
            btnPesananBulanan.setVisibility(View.INVISIBLE);
            txtPesananMingguan.setVisibility(View.INVISIBLE);
            txtPesananBulanan.setVisibility(View.INVISIBLE);
        }

    }

    public void openOrderActivity(){
        Intent intent = new Intent(getActivity(), OrderActivity.class);
        startActivity(intent);
    }

    public void openMingguanActivity(){
        Intent intent = new Intent(getActivity(), MingguanActivity.class);
        startActivity(intent);
    }

    public void openBulananActivity(){
        Intent intent = new Intent(getActivity(), BulananActivity.class);
        startActivity(intent);
    }

}