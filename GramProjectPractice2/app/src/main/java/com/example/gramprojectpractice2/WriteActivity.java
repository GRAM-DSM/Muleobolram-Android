package com.example.gramprojectpractice2;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.gramprojectpractice2.model.WriteRequest;
import com.example.gramprojectpractice2.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WriteActivity extends AppCompatActivity {

    private static final String TAG = "WriteActivity";
    public static String a = "";
    public static String b = "";
    EditText write_title,write_main;
    Button write_send,write_back;
    ImageButton write_login;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        write_title = findViewById(R.id.write_title);
        write_main = findViewById(R.id.write_main);
        write_send = findViewById(R.id.write_send);
        write_login = findViewById(R.id.write_login);
        write_back = findViewById(R.id.write_back);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        dialog = new Dialog(WriteActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_write_dialog);

        write_login.setOnClickListener(v -> finish());

        write_back.setOnClickListener(v -> finish());

        write_send.setOnClickListener(v -> {
            dialog.show();
            Button okBtn = dialog.findViewById(R.id.write_dialog_ok);
            Button cancelBtn = dialog.findViewById(R.id.write_dialog_cancel);

            okBtn.setOnClickListener(v1 -> {
                a = write_title.getText().toString();
                b = write_main.getText().toString();
                if(a.trim().isEmpty()||b.trim().isEmpty())
                {
                    //Toast toast = new Toast(WriteActivity.this);
                    Toast.makeText(WriteActivity.this,"텍스트를 모두 입력해 주세요",Toast.LENGTH_LONG).show();
                }
                else if(a.length()>20)
                {
                    Toast.makeText(WriteActivity.this,"제목은 20자이하로 작성해 주세요",Toast.LENGTH_LONG).show();
                }
                else if(b.length()>255)
                {
                    Toast.makeText(WriteActivity.this,"본문은 255자이하로 작성해 주세요",Toast.LENGTH_LONG).show();
                }
                else if(!a.trim().isEmpty()&&!b.trim().isEmpty()) {

                    WriteRequest writeRequest = new WriteRequest();
                    writeRequest.setTitle(a);

                    writeRequest.setContent(b);
                    RetrofitClient retrofitClient = new RetrofitClient();
                    retrofitClient.getRetrofit().write(MainActivity.token, writeRequest).enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if(response.code()==201){
                                Toast toast = new Toast(WriteActivity.this);
                                toast.makeText(WriteActivity.this, "글 쓰기 성공", Toast.LENGTH_SHORT).show();
                                Log.d(TAG, "onResponse: ");
                                a = "";
                                b = "";
                                dialog.dismiss();
                                finish();

                            }
                        }
                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Log.d(TAG, "onFailure: ");
                        }
                    });
                }
            });
            cancelBtn.setOnClickListener(v12 -> dialog.dismiss());
        });

    }
}