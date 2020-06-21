package com.kmr.ui.pesanan;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.kmr.BulananActivity;
import com.kmr.LoginActivity;
import com.kmr.MingguanActivity;
import com.kmr.OrderActivity;
import com.kmr.R;

public class PesananFragment extends Fragment{

    private PesananViewModel pesananViewModel;

    private Button btnPesananOrder;
    private Button btnPesananMingguan;
    private Button btnPesananBulanan;

    private TextView tv;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        pesananViewModel =
                ViewModelProviders.of(this).get(PesananViewModel.class);
        View root = inflater.inflate(R.layout.fragment_pesanan, container, false);

        btnPesananOrder = (Button)root.findViewById(R.id.btnPesananOrder);
        btnPesananOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openOrderActivity();
            }
        });
        btnPesananMingguan = (Button)root.findViewById(R.id.btnPesananMingguan);
        btnPesananMingguan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                show();
                openMingguanActivity();
            }
        });
        btnPesananBulanan = (Button)root.findViewById(R.id.btnPesananBulanan);
        btnPesananBulanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openBulananActivity();

            }
        });

        return root;
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