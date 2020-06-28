package com.kmr;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private SharedPreferences sharedPref;
    private String emailLogin;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        Button officeBtn = (Button)root.findViewById(R.id.officeButton);
        officeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMapActivity(getString(R.string.office));
            }
        });
        Button coworkBtn = (Button)root.findViewById(R.id.coworkButton);
        coworkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMapActivity(getString(R.string.cowork));
            }
        });
        Button meetingBtn = (Button)root.findViewById(R.id.meetingButton);
        meetingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMapActivity(getString(R.string.meeting));
            }
        });

        statusCheck();

        return root;
    }

    public void openMapActivity(String placeType){
        sharedPref = getActivity().getSharedPreferences(getString(R.string.cpworks_account), Context.MODE_PRIVATE);
        emailLogin = sharedPref.getString(getString(R.string.cpworks_account), null);

        if (Objects.nonNull(emailLogin)){
            Intent intent = new Intent(getActivity(), CustomInfoWindowMapsActivity.class);
            intent.putExtra("placeType", placeType);
            startActivity(intent);
        } else {
            Toast.makeText(getActivity(),
                "Silahkan Login untuk memesan" +
                        "", Toast.LENGTH_LONG)
                .show();
        }
    }

    public void statusCheck() {
        final LocationManager manager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();

        }
    }

    private void buildAlertMessageNoGps() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder
                .setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        builder.create().show();
    }

}