package com.kmr;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.kmr.model.ListItem;

import java.util.ArrayList;
import java.util.List;

public class LaporanBulananActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laporan_bulanan);

        List<ListItem> userList = getListData();

        final TextView txtTotal = (TextView) findViewById(R.id.txtLaporanBulananTotal);
        txtTotal.setTypeface(txtTotal.getTypeface(), Typeface.BOLD);
        txtTotal.setText("Rp. 1.151.500.000");

        final ListView lv = (ListView) findViewById(R.id.laporan_bulanan_list);

        lv.setAdapter(new CustomListAdapter(this, userList));

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                ListItem user = (ListItem) lv.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(), "Selected :" + " " + user.getName() + ", " + user.getLocation(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private List<ListItem> getListData() {
        List<ListItem> results = new ArrayList<>();
        ListItem user1 = new ListItem();
        user1.setName("");
        user1.setDesignation("- Tahun 2020 Bulan Januari");
        user1.setLocation("Rp. 351.500.000");
        results.add(user1);
        ListItem user2 = new ListItem();
        user2.setName("");
        user2.setDesignation("- Tahun 2020 Bulan Februari");
        user2.setLocation("Rp. 279.500.000");
        results.add(user2);
        ListItem user3 = new ListItem();
        user3.setName("");
        user3.setDesignation("- Tahun 2020 Bulan Maret");
        user3.setLocation("Rp. 429.500.000");
        results.add(user3);
        return results;
    }
}