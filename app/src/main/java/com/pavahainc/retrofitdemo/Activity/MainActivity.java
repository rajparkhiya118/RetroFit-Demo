package com.pavahainc.retrofitdemo.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pavahainc.retrofitdemo.Utils.EndlessRecyclerViewScrollListener;
import com.pavahainc.retrofitdemo.R;
import com.pavahainc.retrofitdemo.Api.RetrofitAPI;
import com.pavahainc.retrofitdemo.Adpter.RecyclerViewAdapter;
import com.pavahainc.retrofitdemo.Model.Data;
import com.pavahainc.retrofitdemo.Model.RecyclerData;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Data> recyclerDataArrayList;
    private RecyclerViewAdapter recyclerViewAdapter;
    private ProgressBar progressBar;
    RecyclerView idRVCourse;
    int totalpages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        idRVCourse = findViewById(R.id.idRVCourse);
        progressBar = findViewById(R.id.idPBLoading);

        recyclerDataArrayList = new ArrayList<>();
        LinearLayoutManager manager = new LinearLayoutManager(MainActivity.this);

        idRVCourse.setLayoutManager(manager);
        recyclerViewAdapter = new RecyclerViewAdapter(recyclerDataArrayList, MainActivity.this);
        idRVCourse.setAdapter(recyclerViewAdapter);

        idRVCourse.addOnScrollListener(new EndlessRecyclerViewScrollListener(manager) {
            @Override
            public void onFirstVisibleItem(int i) {
            }

            @Override
            public void onLoadMore(int i) {

                Log.e("Akash", "i: " + i);
                Log.e("Akash", "totalpages: " + totalpages);

                if (totalpages >= i) {
                    getAllCourses(i);
                }
            }
        });

        getAllCourses(1);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_xml, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.watchletter:
                Intent i = new Intent(MainActivity.this, WatchLetterActivity.class);
                startActivity(i);
                return true;

            case R.id.favourite:
                Intent intent = new Intent(MainActivity.this, FavouriteActivity.class);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void getAllCourses(int i) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://159.65.146.129/api/videos/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        Call<RecyclerData> call = retrofitAPI.getAllCourses(i);

        call.enqueue(new Callback<RecyclerData>() {
            @Override
            public void onResponse(@NonNull Call<RecyclerData> call, @NonNull Response<RecyclerData> response) {

                Log.e("Akash", "onResponse: " + response);

                if (response.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);
                    assert response.body() != null;
                    ArrayList<Data> templist = (ArrayList<Data>) response.body().getDataArray();
                    recyclerDataArrayList.addAll(templist);
                    recyclerViewAdapter.notifyDataSetChanged();
                    totalpages = response.body().getTotalpages();
                }
            }

            @Override
            public void onFailure(@NonNull Call<RecyclerData> call, @NonNull Throwable t) {
                Toast.makeText(MainActivity.this, "Fail to get data", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        recyclerViewAdapter.notifyDataSetChanged();
    }
}