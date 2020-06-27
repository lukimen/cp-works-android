package com.kmr;

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

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private APIInterface apiInterface;
    private Button btnLogin;
    private EditText txtEmail;
    private EditText txtPassword;
    private SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = findViewById(R.id.btnLogin);
        txtEmail = findViewById(R.id.txtLoginEmail);
        txtPassword = findViewById(R.id.txtLoginPassword);

        sharedPref = getSharedPreferences(
                getString(R.string.cpworks_account), Context.MODE_PRIVATE);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!txtEmail.getText().toString().trim().equalsIgnoreCase("") &&
                        !txtPassword.getText().toString().trim().equalsIgnoreCase("")){
                    new Login().execute(getApplicationContext());
                } else {
                    Toast.makeText(getApplicationContext(),   "'Silahkan Input Username & Password",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private class Login extends AsyncTask<Context, String, String> {

        @Override
        protected String doInBackground(Context... contexts) {
            publishProgress("Loading..."); // Calls onProgressUpdate()

            try {

                apiInterface = APIClient.getClient().create(APIInterface.class);
                Response<BaseResponse<Boolean>> result =
                        apiInterface.login(
                                txtEmail.getText().toString(),
                                txtPassword.getText().toString())
                                .execute();

                if (result.body().getData() == true){ // login sukses?

                    SharedPreferences.Editor editor = sharedPref.edit();

                    editor.putString(getString(R.string.cpworks_account), txtEmail.getText().toString());
                    editor.commit();

                    //update ui
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                        Toast.makeText(getApplicationContext(),   "'Login Sukses", Toast.LENGTH_LONG).show();
                        }
                    });


                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            String emailLogin = sharedPref.getString(getString(R.string.cpworks_account), null);

                            Toast.makeText(getApplicationContext(),
                                    "emailLogin: " + emailLogin , Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                    Thread.sleep(3000);
                    finish();
                } else {
                    //update ui
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),   "'Login Failed", Toast.LENGTH_LONG).show();
                        }
                    });
                }


                System.out.println("result : " + result);

            } catch (IOException | InterruptedException e) {
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