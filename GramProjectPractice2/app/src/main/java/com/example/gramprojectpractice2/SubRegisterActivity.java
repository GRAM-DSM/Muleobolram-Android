package com.example.gramprojectpractice2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gramprojectpractice2.model.RegisterInterface;
import com.example.gramprojectpractice2.model.RegisterRequest;
import com.example.gramprojectpractice2.model.ValidateRequest;
import com.example.gramprojectpractice2.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SubRegisterActivity extends AppCompatActivity {

    EditText id, password, name;
    Button btn_register, btn_check;
    TextView check_tv;
    Retrofit retrofit;
    private final String TAG = "SubRegister";
    private Boolean isOverlap = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_register);

        btn_register = findViewById(R.id.btn_register);
        btn_check = findViewById(R.id.btn_check);
        id = findViewById(R.id.id);
        password = findViewById(R.id.password);
        name = findViewById(R.id.name);
        check_tv = findViewById(R.id.check_tv);
        retrofit = new RetrofitClient().getRetrofit1();

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        btn_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (id.length() >= 5)
                {
                    ValidateRequest body = new ValidateRequest();
                    body.setId(id.getText().toString());
                    retrofit.create(RegisterInterface.class).validate(body).enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            Log.d(TAG, "onResponse: "+response.code());

                            if (response.code() == 200)
                            {
                                check_tv.setText("확인 완료");
                                isOverlap = true;
                            }
                            if (response.code() == 409)
                            {
                                check_tv.setText("");
                                Toast.makeText(getApplicationContext(),"아이디 겹침", Toast.LENGTH_LONG).show();
                                isOverlap = false;
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                        }
                    });
                }
                else
                {
                    Toast.makeText(getApplicationContext(),
                            "아이디 형식이 알맞지 않습니다.", Toast.LENGTH_LONG).show();
                }
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOverlap)
                {
                    if (password.length() < 5) {
                        new AlertDialog.Builder(SubRegisterActivity.this)
                                .setMessage("아이디나 비밀번호의 형식이 알맞지 않습니다.")
                                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                }).show();
                    }

                    else if (id.getText() != null && password.getText() != null && name.getText() != null) {
                        RegisterRequest body = new RegisterRequest();
                        body.setId(id.getText().toString());
                        body.setName(name.getText().toString());
                        body.setPassword(password.getText().toString());

                        retrofit.create(RegisterInterface.class).register(body).enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                if (response.code() == 201) {
                                    AlertDialog.Builder dialog = new AlertDialog.Builder(SubRegisterActivity.this)
                                            .setMessage("회원가입에 성공했습니다!")
                                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                   /* Intent intent = new Intent(SubRegisterActivity.this, LoginActivity.class);
                                                    startActivity(intent);*/
                                                    finish();
                                                }
                                            });
                                    dialog.show();
                                }

                                if (response.code() == 409) {
                                    Toast.makeText(getApplicationContext(),
                                            "이미 존재하는 아이디입니다.", Toast.LENGTH_SHORT).show();
                                }

                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Log.d(TAG, t.toString());
                            }
                        });
                    }

                    else{
                        Toast.makeText(getApplicationContext(),"아이디 중복 확인을 해주세요.", Toast.LENGTH_LONG).show();
                    }

                }
            }
        });

    }
}

