package com.kmr;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class AccountFragment extends Fragment {

    private AccountViewModel accountViewModel;

    private Button btnAccountLoginLogout;
    private Button btnAccountRegister;
    private SharedPreferences sharedPref;
    private String emailLogin;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        accountViewModel =
                ViewModelProviders.of(this).get(AccountViewModel.class);
        View root = inflater.inflate(R.layout.fragment_account, container, false);

        btnAccountLoginLogout = (Button)root.findViewById(R.id.btnAccountLoginLogout);
        btnAccountRegister = (Button)root.findViewById(R.id.btnAccountRegister);

        sharedPref = getActivity().getSharedPreferences(getString(R.string.cpworks_account), Context.MODE_PRIVATE);

        emailLogin = sharedPref.getString(getString(R.string.cpworks_account), null);

//        Toast.makeText(getActivity(),
//                "emailLogin: " + emailLogin + "emailLogin 2: " + emailLogin2, Toast.LENGTH_LONG)
//                .show();

        if (Objects.nonNull(emailLogin)) { // kalo uda login
            btnAccountRegister.setVisibility(View.INVISIBLE);
            btnAccountLoginLogout.setText("Logout");
        } else {
            btnAccountRegister.setVisibility(View.VISIBLE);
            btnAccountLoginLogout.setText("Login");
        }

        btnAccountLoginLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                emailLogin = sharedPref.getString(getString(R.string.cpworks_account), null);

                if (Objects.nonNull(emailLogin)) { // kalo logout
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.clear();
                    editor.commit();
                    btnAccountRegister.setVisibility(View.VISIBLE);
                    btnAccountLoginLogout.setText("Login");
                } else {
                    openLoginActivity();
                }
            }
        });

        btnAccountRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openRegisterActivity();
            }
        });
        return root;
    }

    @Override
    public void onResume() {

        super.onResume();

        emailLogin = sharedPref.getString(getString(R.string.cpworks_account), null);

        Toast.makeText(getActivity(),
                "ressss emailLogin: " + emailLogin, Toast.LENGTH_LONG)
                .show();

        if (Objects.nonNull(emailLogin)) { // kalo uda login
            btnAccountRegister.setVisibility(View.INVISIBLE);
            btnAccountLoginLogout.setText("Logout");
        } else {
            btnAccountRegister.setVisibility(View.VISIBLE);
            btnAccountLoginLogout.setText("Login");
        }
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