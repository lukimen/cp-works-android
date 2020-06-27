package com.kmr;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Response;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kmr.model.BaseResponse;

import java.io.IOException;

public class RegisterActivity extends AppCompatActivity {

    private APIInterface apiInterface;
    private Button btnRegister;
    private EditText txtEmail;
    private EditText txtPassword;
    private SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btnRegister = findViewById(R.id.btnRegister);
        txtEmail = findViewById(R.id.txtRegisterEmail);
        txtPassword = findViewById(R.id.txtRegisterPassword);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!txtEmail.getText().toString().trim().equalsIgnoreCase("") &&
                        !txtPassword.getText().toString().trim().equalsIgnoreCase("")){
                    new Register().execute(getApplicationContext());
                } else {
                    Toast.makeText(getApplicationContext(),   "'Silahkan Input Username & Password",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private class Register extends AsyncTask<Context, String, String> {

        @Override
        protected String doInBackground(Context... contexts) {
            publishProgress("Loading..."); // Calls onProgressUpdate()

            try {

                apiInterface = APIClient.getClient().create(APIInterface.class);
                Response<BaseResponse<Boolean>> result =
                        apiInterface.register(
                                txtEmail.getText().toString(),
                                txtPassword.getText().toString())
                                .execute();

                if (result.body().getData() == true){ // register sukses?

                    //update ui
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),   "'Register Sukses", Toast.LENGTH_LONG).show();
                        }
                    });

                    finish();
                } else {

                    //update ui
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),   "'Register Gagal", Toast.LENGTH_LONG).show();
                        }
                    });
                }


                System.out.println("result : " + result);

            } catch (IOException e) {
                e.printStackTrace();
            }

            return "finish";
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
}