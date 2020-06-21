package com.kmr;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class MarkerInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {
    private Context context;
    public MarkerInfoWindowAdapter(Context context) {
        this.context = context.getApplicationContext();
    }

    @Override
    public View getInfoWindow(Marker arg0) {
        return null;
    }

    @Override
    public View getInfoContents(Marker arg0) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v =  inflater.inflate(R.layout.map_marker_info_window, null);

        ImageView imageView = (ImageView) v.findViewById(R.id.imagePlace);
        TextView nameTxt = (TextView) v.findViewById(R.id.namePlace);
        TextView addTxt = (TextView) v.findViewById(R.id.addressPlace);
        TextView add2Txt = (TextView) v.findViewById(R.id.address2Place);
        Button orderButton = (Button) v.findViewById(R.id.btnPesananOrder);

        imageView.setImageResource(R.drawable.menarabca);

        nameTxt.setText("Menara BCA Office:");
        nameTxt.setTypeface(nameTxt.getTypeface(), Typeface.BOLD);

        addTxt.setText("Jl. MH Thamrin No 1");
        add2Txt.setText("Tanah Abang, Jakarta Pusat");

        // Setting custom OnTouchListener which deals with the pressed state
        // so it shows up
        // Setting custom OnTouchListener which deals with the pressed state
        // so it shows up
        OnInfoWindowElemTouchListener infoButtonListener = new OnInfoWindowElemTouchListener(orderButton){
            @Override
            protected void onClickConfirmed(View v, Marker marker) {
                // Here we can perform some action triggered after clicking the button
                Toast.makeText(context, marker.getTitle() + "'s button clicked!", Toast.LENGTH_LONG).show();
            }
        };
        orderButton.setOnTouchListener(infoButtonListener);

        return v;
    }
}
