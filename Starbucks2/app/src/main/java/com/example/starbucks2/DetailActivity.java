package com.example.starbucks2;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class DetailActivity extends AppCompatActivity {

    TextView tvName, tvPrice, tvDescription;
    ImageView picCoffee;
    Button btnGrab;

    @SuppressWarnings("deprecation")
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_detail);

        tvName = findViewById(R.id.tvName);
        tvPrice = findViewById(R.id.tvPrice);
        tvDescription = findViewById(R.id.tvDescription);
        picCoffee = (ImageView) findViewById(R.id.picCoffee);

//        Product product = (Product) getIntent().getSerializableExtra("product");

        String productName = getIntent().getStringExtra("name");
        int productPrice = getIntent().getIntExtra("price", 100);
        String productDesc = getIntent().getStringExtra("desc");
        int productImgId = getIntent().getIntExtra("imgid", R.drawable.cold_brew);

        tvName.setText(productName);
        tvPrice.setText("$" + productPrice);
        tvDescription.setText(productDesc);
        @SuppressLint("UseCompatLoadingForDrawables") Drawable ad = getResources().getDrawable(productImgId);
        picCoffee.setImageDrawable(ad);

        btnGrab = findViewById(R.id.btnGrab);

        btnGrab.setOnClickListener(x -> {
            SQLiteDatabase db = DBOpenHelper.getInstance(this).getWritableDatabase();

            ContentValues cv = new ContentValues();
            cv.put(DBOpenHelper.NAME, productName);
            cv.put(DBOpenHelper.PRICE, productPrice);
            cv.put(DBOpenHelper.DESCRIPTION, productDesc);
            cv.put(DBOpenHelper.IMGID, productImgId);

            Log.d("hey", "finish");

            db.insert(DBOpenHelper.PRODUCTS, null, cv);

            Toast.makeText(this, productName + " Added to cart!", Toast.LENGTH_SHORT).show();

            finish();
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onNavigateUp();
    }
}