package com.example.sergio.kolestrol_1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.sergio.kolestrol_1.Model.ServerRequest;
import com.example.sergio.kolestrol_1.Model.ServerResponse;
import com.example.sergio.kolestrol_1.Model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Login extends AppCompatActivity {


    private Button btn_login, btn_register;
    private EditText edt_username, edt_password;
    private SharedPreferences preff;
    private RelativeLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        preff = this.getPreferences(0);
        btn_register = (Button) findViewById(R.id.btn_Register);
        btn_login = (Button) findViewById(R.id.btn_Login);
        edt_username = (EditText) findViewById(R.id.edt_userName);
        edt_password = (EditText) findViewById(R.id.edt_Password);
        btn_login.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
            String username = edt_username.getText().toString();
            String password = edt_password.getText().toString();

            if (!username.isEmpty() && !password.isEmpty()) {

                loginProcess(username, password);

            } else {
                Toast.makeText(Login.this,"Username atau Password Kosong !", Toast.LENGTH_LONG).show();
            }
        }
        });
    }
    private void loginProcess(final String username, final String password) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestInterface requestInterface = retrofit.create(RequestInterface.class);

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        ServerRequest request = new ServerRequest();
        request.setOperation(Constants.LOGIN_OPERATION);
        request.setUser(user);
        Call<ServerResponse> response = requestInterface.operation(request);

        response.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, retrofit2.Response<ServerResponse> response) {

                ServerResponse resp = response.body();
                Toast.makeText(Login.this, resp.getMessage(), Toast.LENGTH_LONG).show();

                if (resp.getResult().equals(Constants.SUCCESS)) {
                    SharedPreferences preff = getSharedPreferences("log", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preff.edit();

                    editor.putBoolean(Constants.IS_LOGGED_IN, true);
                    editor.putString(Constants.id_user, resp.getUser().getId_user());
                    editor.putString(Constants.password,resp.getUser().getPassword());
                    editor.putString(Constants.username,resp.getUser().getUsername());
                    editor.putString(Constants.nama,resp.getUser().getNama());
                    editor.putString(Constants.tanggal_lahir,resp.getUser().getTanggal_lahir());
                    editor.putString(Constants.umur,resp.getUser().getUmur());
                    editor.putString(Constants.berat_badan,resp.getUser().getBerat_badan());
                    editor.putString(Constants.jenis_kelamin,resp.getUser().getJenis_kelamin());
                    editor.apply();
//                        profil(username, password);
                    home();
                    Toast.makeText(Login.this, "Berhasil Login", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Login.this, "Username atau Password Salah !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Log.d(Constants.TAG, "failed");
                Toast.makeText(Login.this, t.getLocalizedMessage(), Toast.LENGTH_LONG).show();

            }
        });
    }

    private void home() {
        Intent intent = new Intent(this, kolestrol.class);
        startActivity(intent);
    }
    }
