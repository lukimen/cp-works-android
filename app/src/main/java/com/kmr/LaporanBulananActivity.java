package com.kmr;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import jxl.Workbook;
import jxl.demo.CSV;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import retrofit2.Response;
import retrofit2.http.Query;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.kmr.model.BaseResponse;
import com.kmr.model.Laporan;
import com.kmr.model.LaporanDetail;
import com.kmr.model.ListItem;
import com.kmr.model.OrderDao;
import com.kmr.util.DateFormatterHelper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LaporanBulananActivity extends AppCompatActivity {

    private APIInterface apiInterface;
    private ListView listView;
    private TextView txtTotal;
    private Laporan laporan;
    private SharedPreferences sharedPref;
    private String emailLogin;
    private Button btnLaporanBulananDownload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laporan_bulanan);

        txtTotal = (TextView) findViewById(R.id.txtLaporanBulananTotal);
        txtTotal.setTypeface(txtTotal.getTypeface(), Typeface.BOLD);
        listView = (ListView) findViewById(R.id.laporan_bulanan_list);
        btnLaporanBulananDownload = findViewById(R.id.btnLaporanBulananDownload);

        laporan = getEmptyLaporan();
        List<ListItem> userList = new ArrayList<>();

        txtTotal.setText("Rp. " + String.format("%,d", ((Double) laporan.getTotal()).intValue()));

        listView.setAdapter(new CustomListAdapter(this, userList));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                ListItem user = (ListItem) listView.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(), "Selected :" + " " + user.getName() +
                        ", " + user.getLocation(), Toast.LENGTH_SHORT).show();
            }
        });

        IsiLaporanBulanan isiLaporanBulanan = new IsiLaporanBulanan();
        isiLaporanBulanan.execute(this);

        btnLaporanBulananDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buatLaporanBulanan();
            }
        });
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

    private class IsiLaporanBulanan extends AsyncTask<Context, String, String> {

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
                    String bulanAwal = getIntent().getStringExtra("bulanAwal");
                    String tahunAkhir = getIntent().getStringExtra("tahunAkhir");
                    String bulanAkhir = getIntent().getStringExtra("bulanAkhir");

                    apiInterface = APIClient.getClient().create(APIInterface.class);
                    Response<BaseResponse<Laporan>> result =
                            apiInterface.laporanBulanan(
                                    bulanAkhir, bulanAwal, tahunAkhir, tahunAwal).execute();


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

    private void buatLaporanBulanan(){


        //1. Create an Excel file
        WritableWorkbook myFirstWbook = null;
        try {

            String content = "Separe here integers by semi-colon";
            String content2 = "Separe, here, integers, by, semi-colon";

            File file =
                    new File(Environment.getExternalStoragePublicDirectory(
                            Environment.DIRECTORY_DCIM).getAbsolutePath() + "/laporan-bulanan.xls");

            // if file doesnt exists, then create it`
            if (!file.exists()) {
                file.createNewFile();
            }

            myFirstWbook = Workbook.createWorkbook(file);

            // create an Excel sheet
            WritableSheet excelSheet = myFirstWbook.createSheet("Laporan Bulanan", 0);

            // add something into the Excel sheet
            Label label = new Label(0, 0, "Total laporan Mingguan");
            excelSheet.addCell(label);

            label = new Label(3, 0, txtTotal.getText().toString());
            excelSheet.addCell(label);

            int i = 1;
            for (LaporanDetail laporanDetail: laporan.getLaporanDetails()){

                label = new Label(0, i, laporanDetail.getDeskripsi());
                excelSheet.addCell(label);

                label = new Label(3, i,
                        "Rp. " + String.format("%,d", ((Double) laporanDetail.getTotal()).intValue()));
                excelSheet.addCell(label);

                i++;
            }

            myFirstWbook.write();

            Intent intent = new Intent(Intent.ACTION_VIEW);
            Uri csvUri =
                    FileProvider.getUriForFile(
                            getApplicationContext(), getApplicationContext().getPackageName() +
                                    ".provider", file);

            intent.setDataAndType(csvUri, "application/xls");
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(intent);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (WriteException e) {
            e.printStackTrace();
        } finally {

            if (myFirstWbook != null) {
                try {
                    myFirstWbook.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (WriteException e) {
                    e.printStackTrace();
                }
            }
        }

        Toast.makeText(getApplicationContext(),
                "download laporan bulanan", Toast.LENGTH_LONG).show();
    }

}