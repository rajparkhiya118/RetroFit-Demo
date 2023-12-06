package com.pavahainc.retrofitdemo.Activity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pavahainc.retrofitdemo.Database.DBHelper;
import com.pavahainc.retrofitdemo.Model.Data;
import com.pavahainc.retrofitdemo.R;
import com.pavahainc.retrofitdemo.Adpter.WatchLetterAdapter;

import java.util.ArrayList;

public class WatchLetterActivity extends AppCompatActivity {

    LinearLayout nodata;
    private RecyclerView rec;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_watch_letter );

        nodata = findViewById( R.id.nodata );

        rec = findViewById( R.id.rec );

        watchletterdata();


    }

    void watchletterdata() {
        DBHelper dbHelper = new DBHelper( WatchLetterActivity.this );
        Cursor cursor = dbHelper.readdata();

        ArrayList<Data> arrayList = new ArrayList<>();
        while (cursor.moveToNext()) {
            String id = cursor.getString( 1 );
            String title = cursor.getString( 2 );
            String category = cursor.getString( 3 );
            int views = cursor.getInt( 4 );
            int likes = cursor.getInt( 5 );

            LinearLayoutManager manager = new LinearLayoutManager( WatchLetterActivity.this );

            rec.setLayoutManager( manager );
            Data data = new Data( id, title, category, views, likes );
            arrayList.add( data );

        }
        if (arrayList.size() > 0) {
            WatchLetterAdapter recyclerViewAdapter1 = new WatchLetterAdapter( arrayList, WatchLetterActivity.this );
            rec.setAdapter( recyclerViewAdapter1 );

        } else {
            nodata.setVisibility( View.VISIBLE );
        }

    }
}