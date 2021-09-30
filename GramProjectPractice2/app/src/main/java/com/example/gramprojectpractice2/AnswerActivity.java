package com.example.gramprojectpractice2;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gramprojectpractice2.model.AnswerData;
import com.example.gramprojectpractice2.model.CommentSeeResponse;
import com.example.gramprojectpractice2.model.CommentWriteResponse;
import com.example.gramprojectpractice2.network.RetrofitClient;
import com.example.gramprojectpractice2.view.AnswerAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class
AnswerActivity extends AppCompatActivity {

    private static final String TAG = "AnswerActivity";
    private ArrayList<AnswerData> arrayList;;
    private String recyclerviewGetTitle,recyclerviewGetMain, recyclerviewGetName;
    private String recyclerviewGetid_pk;
    private AnswerAdapter answerAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private Button send,answer_community;
    private TextView a_title,delete,a_main;
    private EditText answer;
    private ImageButton back;
    private Dialog dialog;
    private int check = 0;
    String SeeName, SeeContent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);

        back = (ImageButton)findViewById(R.id.back);
        send = findViewById(R.id.send);
        answer = findViewById(R.id.answer);
        delete = findViewById(R.id.delete);
        a_title = findViewById(R.id.a_title);
        a_main = findViewById(R.id.a_main);
        answer_community = findViewById(R.id.answer_community);
        recyclerView = findViewById(R.id.rV_answer);

        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        arrayList = new ArrayList<>();
        answerAdapter = new AnswerAdapter(this, arrayList);
        recyclerView.setAdapter(answerAdapter);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        dialog = new Dialog(AnswerActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_answer_dialog);

        //MainActiviy에서 아이템뷰를 클릭시 화면이 넘어오면서 아이템뷰 안에 제목 데이터가 넘어오고 까지
        //받아온 데이터를 AnswerActivity제목란에 넣어준다
        Intent intent = getIntent();
        recyclerviewGetTitle = intent.getStringExtra("itemTitle");
        recyclerviewGetMain = intent.getStringExtra("itemmain");
        recyclerviewGetid_pk = intent.getStringExtra("item_id_pk");
        recyclerviewGetName = intent.getStringExtra("itemName");

        if(recyclerviewGetTitle.trim().isEmpty()==false&&recyclerviewGetMain.trim().isEmpty()==false)
        {
            a_title.setText(recyclerviewGetTitle);
            a_main.setText(recyclerviewGetMain);
        }

        back.setOnClickListener(v -> {
             finish();
        });


        send.setOnClickListener(v -> {
            String an = answer.getText().toString();
            if(an.length()>255)
            {
                Toast.makeText(AnswerActivity.this,"댓글은 255자이하로 입력해 주세요",Toast.LENGTH_LONG);
            }
            else if(!an.trim().isEmpty())
            {
                CommentWriteResponse commentWriteResponse = new CommentWriteResponse();
                commentWriteResponse.setContent(an);
                int int_id_pk = Integer.parseInt(recyclerviewGetid_pk);
                RetrofitClient retrofitClient = new RetrofitClient();
                retrofitClient.getRetrofit().writeComment(MainActivity.token,commentWriteResponse,int_id_pk).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if(response.code()==200){
                            Log.d(TAG, "onResponse: 글쓰기성공");
                            answer.setText(null);

                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                });

                RetrofitClient retrofitClient1 = new RetrofitClient();
                retrofitClient1.getRetrofit().seeComment(MainActivity.token,int_id_pk).enqueue(new Callback<CommentSeeResponse>() {
                    @Override
                    public void onResponse(Call<CommentSeeResponse> call, Response<CommentSeeResponse> response) {
                        if(response.code()==201){
                            arrayList.clear();
                            Log.d(TAG, "onResponse: "+"댓글 보기 성공");
                            showitem("{\n" +
                                    "    \"comment_join\": "+ response.body().getComment_join() +
                                    "}");
                        }
                    }
                    @Override
                    public void onFailure(Call<CommentSeeResponse> call, Throwable t) {

                    }
                });

            }
            else if(an.trim().isEmpty())
            {
                Toast toast = new Toast(AnswerActivity.this);
                toast.makeText(AnswerActivity.this,"텍스트를 입력해 주세요",Toast.LENGTH_LONG).show();
            }

        });

        if(check == 0)
        {
            RetrofitClient retrofitClient = new RetrofitClient();
            int int_id_pk = Integer.parseInt(recyclerviewGetid_pk);
            retrofitClient.getRetrofit().seeComment(MainActivity.token,int_id_pk).enqueue(new Callback<CommentSeeResponse>() {
                @Override
                public void onResponse(Call<CommentSeeResponse> call, Response<CommentSeeResponse> response) {
                    if(response.code()==201){
                        Log.d(TAG, "onResponse: "+"댓글 보기 성공");
                        showitem("{\n" +
                                "    \"comment_join\": "+ response.body().getComment_join() +
                                "}");
                    }
                }
                @Override
                public void onFailure(Call<CommentSeeResponse> call, Throwable t) {

                }
            });
        }


        delete.setOnClickListener(v -> {
            dialog.show();
            Button okBtn = dialog.findViewById(R.id.answer_dialog_ok);
            Button cancelBtn = dialog.findViewById(R.id.answer_dialog_cancel);
            okBtn.setOnClickListener(v1 -> {

                RetrofitClient retrofitClient1 = new RetrofitClient();
                int int_id_pk = Integer.parseInt(recyclerviewGetid_pk);
                retrofitClient1.getRetrofit().deletePost(MainActivity.token, int_id_pk).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Log.d(TAG, "onResponse: "+response.code());
                        if(response.code()==200){
                            dialog.dismiss();
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.d(TAG, "DeleteOnFailure: ");
                    }
                });
            });
            cancelBtn.setOnClickListener(v12 -> {
                dialog.dismiss();
            });
        });

        answer_community.setOnClickListener(v -> finish());

    }

    @Override
    protected void onResume() {
        super.onResume();


    }
    private void showitem(String json)
    {
        try {
            JSONObject jsonObject = new JSONObject(json);
            String comment_join = jsonObject.getString("comment_join");
            JSONArray jsonArray = new JSONArray(comment_join);
            if(jsonArray.length()==0){
                return;
            }
            for(int i = 0; i<jsonArray.length();i++)
            {
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                SeeName = jsonObject1.getString("name");
                SeeContent = jsonObject1.getString("content");
                AnswerData answerData = new AnswerData(SeeContent, SeeName);
                arrayList.add(answerData);
                answerAdapter.notifyDataSetChanged();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}