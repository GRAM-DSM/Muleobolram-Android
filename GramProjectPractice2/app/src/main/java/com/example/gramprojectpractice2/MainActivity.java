package com.example.gramprojectpractice2;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import com.example.gramprojectpractice2.model.MainData;
import com.example.gramprojectpractice2.model.SeeResponse;
import com.example.gramprojectpractice2.network.RetrofitClient;
import com.example.gramprojectpractice2.view.MainAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private ArrayList<MainData> arrayList;
    private MainAdapter mainAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    public static int sharePosition = 0;
    public static int shareOR = 0;
    int id_pk;
    String title;
    String name;
    String content;
    public static String token = "";

    ImageButton main_Add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate: "+token);

        recyclerView = findViewById(R.id.rV);
        main_Add = findViewById(R.id.main_Add);

        arrayList = new ArrayList<>();
        mainAdapter = new MainAdapter(this,arrayList);
        linearLayoutManager = new LinearLayoutManager(this);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(mainAdapter);

        main_Add.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), WriteActivity.class);
            startActivity(intent);
        });



    }

    @Override
    protected void onResume() {
        super.onResume();

        RetrofitClient retrofitClient = new RetrofitClient();
        retrofitClient.getRetrofit().seePost(token).enqueue(new Callback<SeeResponse >() {
            @Override
            public void onResponse(Call<SeeResponse> call, Response<SeeResponse> response) {
                if (response.code() == 200) {
                    Log.d(TAG, "onResponse: "+"MainActivity 성공");
                    showitem("{\n" +
                            "    \"posts\": "+ response.body().getGetPost() +
                            "}");
                }
            }

            @Override
            public void onFailure(Call<SeeResponse> call, Throwable t) {
                System.out.println("MainActivity 실패");
            }
        });

    }



    private void showitem(String json) {
        arrayList.clear();

        try {
            JSONObject jsonObject = new JSONObject(json);
            String posts = jsonObject.getString("posts");
            JSONArray jsonArray = new JSONArray(posts);
            if(jsonArray.length()==0) {
                return;
            }
            for(int i = 0; i<jsonArray.length();i++)
            {
                JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                id_pk = jsonObject2.getInt("id_pk");
                title = jsonObject2.getString("title");
                name = jsonObject2.getString("name");
                content = jsonObject2.getString("content");
                String stringId_pk = Integer.toString(id_pk);
                MainData mainData = new MainData(stringId_pk,title,name,content);
                arrayList.add(mainData);
                mainAdapter.notifyDataSetChanged();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
