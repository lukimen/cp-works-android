package com.kmr.ui.account;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.kmr.CustomInfoWindowMapsActivity;
import com.kmr.LoginActivity;
import com.kmr.R;
import com.kmr.RegisterActivity;

public class AccountFragment extends Fragment {

    private AccountViewModel accountViewModel;

    private Button btnAccountLogin;
    private Button btnAccountRegister;
    private Button btnAccountLogout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        accountViewModel =
                ViewModelProviders.of(this).get(AccountViewModel.class);
        View root = inflater.inflate(R.layout.fragment_account, container, false);

        btnAccountLogin = (Button)root.findViewById(R.id.btnAccountLogin);
        btnAccountLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLoginActivity();
            }
        });
        btnAccountRegister = (Button)root.findViewById(R.id.btnAccountRegister);
        btnAccountRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openRegisterActivity();
            }
        });
        btnAccountLogout = (Button)root.findViewById(R.id.btnAccountLogout);
        btnAccountLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),   "User Logout", Toast.LENGTH_LONG).show();

            }
        });
        return root;
    }

    public void openLoginActivity(){
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
    }

    public void openRegisterActivity(){
        Intent intent = new Intent(getActivity(), RegisterActivity.class);
        startActivity(intent);
    }
}