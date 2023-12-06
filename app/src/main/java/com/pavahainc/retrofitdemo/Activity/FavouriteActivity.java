package com.pavahainc.retrofitdemo.Activity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pavahainc.retrofitdemo.Database.DBHelper1;
import com.pavahainc.retrofitdemo.Model.Data;
import com.pavahainc.retrofitdemo.R;
import com.pavahainc.retrofitdemo.Adpter.FavouriteAdapter;

import java.util.ArrayList;

public class FavouriteActivity extends AppCompatActivity {

    LinearLayout nodata;
    private RecyclerView rec1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_favorite );

        nodata = findViewById( R.id.nodata );

        rec1 = findViewById( R.id.rec1 );


        favdata();



    }

    void favdata() {
        DBHelper1 dbHelper1 = new DBHelper1( FavouriteActivity.this );
        Cursor cursor = dbHelper1.readdata();

        ArrayList<Data> arrayList1 = new ArrayList<>();
        while (cursor.moveToNext()) {
            String id = cursor.getString( 1 );
            String title = cursor.getString( 2 );
            String category = cursor.getString( 3 );
            int views = cursor.getInt( 4 );
            int likes = cursor.getInt( 5 );

            LinearLayoutManager manager = new LinearLayoutManager( FavouriteActivity.this );
            manager.setReverseLayout(true);
            manager.setStackFromEnd(true);

            rec1.setLayoutManager( manager );
            Data dat = new Data( id, title, category, views, likes );
            arrayList1.add( dat );

        }
        if (arrayList1.size() > 0) {
            FavouriteAdapter recyclerViewAdapter2 = new FavouriteAdapter( arrayList1, FavouriteActivity.this );
            rec1.setAdapter( recyclerViewAdapter2 );
        } else {
            nodata.setVisibility( View.VISIBLE );
        }

    }

}