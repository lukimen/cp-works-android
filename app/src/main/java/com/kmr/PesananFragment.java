package com.kmr;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
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

//        ImageView imgTes = (ImageView)root.findViewById(R.id.imgTest);

//        imgTes.setImageResource(R.drawable.meeting120);

//        imgTes.setImageDrawable(
//                getImageDrawable("https://i.ibb.co/rfkQCzv/rtdx-office-120.png"));
//        Picasso.get().load("https://i.ibb.co/rfkQCzv/rtdx-office-120.png")
//                .into(imgTes);


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

    private Drawable getImageDrawable(String url) {
        Drawable image = null;
        URL imageUrl;

        try {
            // Create a Drawable by decoding a stream from a remote URL
            imageUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream stream = conn.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(stream);
            image = new BitmapDrawable(getResources(), bitmap);
        } catch (Exception e) {
//            Log.e(TAG, "Decoding Bitmap stream failed");
//            image = getResources().getDrawable(R.drawable.noquestion);
        }
        return image;
    }

}