package com.kmr;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Response;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.kmr.model.BaseResponse;
import com.kmr.model.Laporan;
import com.kmr.model.LaporanDetail;
import com.kmr.model.ListItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LaporanMingguanActivity extends AppCompatActivity {

    private APIInterface apiInterface;
    private ListView listView;
    private TextView txtTotal;
    private Laporan laporan;
    private SharedPreferences sharedPref;
    private String emailLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laporan_mingguan);

        txtTotal = (TextView) findViewById(R.id.txtLaporanMingguanTotal);
        txtTotal.setTypeface(txtTotal.getTypeface(), Typeface.BOLD);
        listView = (ListView) findViewById(R.id.laporan_mingguan_list);

        laporan = getEmptyLaporan();
        List<ListItem> userList = new ArrayList<>();

        txtTotal.setText("Rp. " + String.format("%,d", ((Double) laporan.getTotal()).intValue()));

        listView.setAdapter(new CustomListAdapter(this, userList));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                ListItem user = (ListItem) listView.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(), "Selected :" + " " +
                        user.getName() + ", " + user.getLocation(), Toast.LENGTH_SHORT).show();
            }
        });

        IsiLaporanMingguan isiLaporanMingguan = new IsiLaporanMingguan();
        isiLaporanMingguan.execute(this);
    }


    private Laporan getEmptyLaporan(){
        Laporan emptyLaporan = new Laporan();
        emptyLaporan.setLaporanDetails(new ArrayList<LaporanDetail>());
        return emptyLaporan;
    }

    private List<ListItem> converKeItem(Laporan laporan) {
        List<ListItem> results = new ArrayList<>();

        for (LaporanDetail laporanDetail: laporan.getLaporanDetails()){

            ListItem item = new ListItem();
            item.setName("");
            item.setDesignation(laporanDetail.getDeskripsi());
            item.setLocation("Rp. " + String.format("%,d", ((Double) laporanDetail.getTotal()).intValue()));
            results.add(item);
        }

        return results;
    }

    private class IsiLaporanMingguan extends AsyncTask<Context, String, String> {

        private String resp;

        @Override
        protected String doInBackground(Context... contexts) {
            publishProgress("Loading..."); // Calls onProgressUpdate()

            try {

                sharedPref = getApplicationContext()
                        .getSharedPreferences(getString(R.string.cpworks_account), Context.MODE_PRIVATE);
                emailLogin = sharedPref.getString(getString(R.string.cpworks_account), null);

                if (Objects.nonNull(emailLogin)){

                    String tahunAwal = getIntent().getStringExtra("tahunAwal");
                    String mingguAwal = getIntent().getStringExtra("mingguAwal");
                    String tahunAkhir = getIntent().getStringExtra("tahunAkhir");
                    String mingguAkhir = getIntent().getStringExtra("mingguAkhir");

                    apiInterface = APIClient.getClient().create(APIInterface.class);
                    Response<BaseResponse<Laporan>> result =
                            apiInterface.laporanMingguan(
                                    mingguAkhir, mingguAwal, tahunAkhir, tahunAwal).execute();


                    laporan = result.body().getData();

                    //update ui
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            listView.setAdapter(new CustomListAdapter(
                                    getApplicationContext(),
                                    converKeItem(laporan)));

                            txtTotal.setText("Rp. " + String.format("%,d", ((Double) laporan.getTotal()).intValue()));

                        }
                    });
                    System.out.println("result : " + result);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            return resp;
        }

        @Override
        protected void onPostExecute(String result) {}

        @Override
        protected void onPreExecute() {}

        @Override
        protected void onProgressUpdate(String... text) {}
    }

    private List<ListItem> getListData() {
        List<ListItem> results = new ArrayList<>();
        ListItem user1 = new ListItem();
        user1.setName("");
        user1.setDesignation("- Tahun 2020 Minggu ke 1");
        user1.setLocation("Rp. 51.500.000");
        results.add(user1);
        ListItem user2 = new ListItem();
        user2.setName("");
        user2.setDesignation("- Tahun 2020 Minggu ke 2");
        user2.setLocation("Rp. 79.500.000");
        results.add(user2);
        ListItem user3 = new ListItem();
        user3.setName("");
        user3.setDesignation("- Tahun 2020 Minggu ke 3");
        user3.setLocation("Rp. 29.500.000");
        results.add(user3);
        return results;
    }
}