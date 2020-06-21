package com.kmr;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.kmr.model.ListItem;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class OrderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        List<ListItem> userList = getListData();

        final ListView lv = (ListView) findViewById(R.id.order_list);

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
        user1.setName("Menara Cawang");
        user1.setDesignation("start: 2020-10-10 x3");
        user1.setLocation("Rp. 1.500.000");
        results.add(user1);
        ListItem user2 = new ListItem();
        user2.setName("Pancoran Mas");
        user2.setDesignation("Start 2020-10-15 x5");
        user2.setLocation("Rp. 7.500.000");
        results.add(user2);
        return results;
    }
}