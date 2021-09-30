package com.example.gramprojectpractice2;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.gramprojectpractice2.model.LoginRequest;
import com.example.gramprojectpractice2.model.LoginResponse;
import com.example.gramprojectpractice2.network.LoginInterface;
import com.example.gramprojectpractice2.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {

    TextView error_tv, register;
    EditText id, password;
    Button login;
    Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        error_tv = findViewById(R.id.error_tv);
        register = findViewById(R.id.textGram);
        login = findViewById(R.id.btn_login);
        id = findViewById(R.id.id_et);
        password = findViewById(R.id.password_et);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        retrofit = new RetrofitClient().getRetrofit1();

        login.setOnClickListener(v -> {
            if (id.getText() != null && password.getText() != null) {
                LoginRequest body = new LoginRequest();
                body.setId(id.getText().toString());
                body.setPassword(password.getText().toString());

                retrofit.create(LoginInterface.class).login(body).enqueue(new Callback<LoginResponse>() {
                    @Override //성공시
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (response.code() == 201) {
                            error_tv.setText("");
                            MainActivity.token = "Bearer " + response.body().getAccess_token();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                        if (response.code() == 409) {
                            error_tv.setText("아이디나 비밀번호가 잘못됐습니다.");
                        }
                    }

                    @Override //실패시
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                    }
                });
            } else {
                error_tv.setText("아이디나 비밀번호가 잘못됐습니다.");
            }

        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SubRegisterActivity.class);
                startActivity(intent);
            }
        });

    }
}