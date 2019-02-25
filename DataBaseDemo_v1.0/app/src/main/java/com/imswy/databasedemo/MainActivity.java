package com.imswy.databasedemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.imswy.databasedemo.litepal.LitepalActivity;
import com.imswy.databasedemo.sqlite.SQLiteActivity;

public class MainActivity extends AppCompatActivity{

    private Button btnSQLite,btnLitepal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSQLite = findViewById(R.id.btn_sqlite);
        btnSQLite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,SQLiteActivity.class));
            }
        });

        btnLitepal = findViewById(R.id.btn_litepal);
        btnLitepal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,LitepalActivity.class));
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
