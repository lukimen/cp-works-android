package com.kmr;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.kmr.model.BaseResponse;
import com.kmr.model.ListItem;
import com.kmr.model.OrderDao;
import com.kmr.util.DateFormatterHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Response;

public class OrderActivity extends AppCompatActivity {

    private APIInterface apiInterface;
    private ListView listView;
    private List<OrderDao> orderDaoList;

    private SharedPreferences sharedPref;
    private String emailLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        List<ListItem> userList = new ArrayList<>();

        listView = (ListView) findViewById(R.id.order_list);

        listView.setAdapter(new CustomListAdapter(this, userList));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                ListItem user = (ListItem) listView.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(), user.getName() + ", " + user.getLocation(), Toast.LENGTH_LONG).show();
            }
        });

        IsiOrder isiOrder = new IsiOrder();
        isiOrder.execute(this);

    }

    private class IsiOrder extends AsyncTask<Context, String, String> {

        private String resp;

        @Override
        protected String doInBackground(Context... contexts) {
            publishProgress("Loading..."); // Calls onProgressUpdate()

            try {

                sharedPref = getApplicationContext()
                        .getSharedPreferences(getString(R.string.cpworks_account), Context.MODE_PRIVATE);
                emailLogin = sharedPref.getString(getString(R.string.cpworks_account), null);

                if (Objects.nonNull(emailLogin)){

                    apiInterface = APIClient.getClient().create(APIInterface.class);
                    Response<BaseResponse<List<OrderDao>>> result =
                            apiInterface.getOrderByEmail(emailLogin).execute();

                    orderDaoList = result.body().getData();

                    //update ui
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            listView.setAdapter(new CustomListAdapter(
                                    getApplicationContext(),
                                    converKeItem(orderDaoList)));

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
        protected void onPostExecute(String result) {
        }


        @Override
        protected void onPreExecute() {
        }


        @Override
        protected void onProgressUpdate(String... text) {
        }
    }

    private List<ListItem> converKeItem(List<OrderDao> orderDaos) {
        List<ListItem> results = new ArrayList<>();

        for (OrderDao orderDao: orderDaos){

            String tglSewa = "Tgl Sewa: " + DateFormatterHelper.dateToString(orderDao.getTanggalAwalSewa());

            ListItem item = new ListItem();
            item.setName(orderDao.getPlaceName());
            item.setDesignation(tglSewa + " x " + Integer.valueOf(orderDao.getDurasiSewa()));
            item.setLocation("Rp. " + String.format("%,d", ((Double) orderDao.getTotalBayar()).intValue()));
            results.add(item);
        }

        return results;
    }
}