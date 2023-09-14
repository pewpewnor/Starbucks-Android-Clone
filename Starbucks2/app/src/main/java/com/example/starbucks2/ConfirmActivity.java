package com.example.starbucks2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.starbucks2.models.Product;
import com.example.starbucks2.ui.cart.CartFragment;

import java.util.ArrayList;

public class ConfirmActivity extends AppCompatActivity {
    Button btnYes, btnNo;
    TextView tvTotal;
    ArrayList<Product> listProduct = CartFragment.listProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);

        int totalPrice = 0;
        for (int i=0; i < listProduct.size()-1; ++i) {
            totalPrice += listProduct.get(i).getPrice();
        }
        totalPrice += listProduct.get(listProduct.size()-1).getPrice();

        tvTotal = findViewById(R.id.tvTotal);
        tvTotal.setText("$" + totalPrice);

        btnYes = findViewById(R.id.yesConf);
        btnNo = findViewById(R.id.noConf);

        btnYes.setOnClickListener(x -> {
            addOrder();
            sms();
            DBOpenHelper.getInstance(this).del();

            Intent intent = new Intent(ConfirmActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });

        btnNo.setOnClickListener(x -> {
            Intent intent = new Intent(ConfirmActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }

    public boolean check(String permission) {
        int check = ContextCompat.checkSelfPermission(this, permission);
        return check == PackageManager.PERMISSION_GRANTED;
    }


    public void sms() {
        if (check(Manifest.permission.SEND_SMS)) {
            SmsManager smsManager = SmsManager.getDefault();
            String phone = "+15555215554";
            String msg = "Your order has been sent!";
            smsManager.sendTextMessage(phone, null, msg, null, null);
            Log.d("sms", "yes");
        }
        else {
            Log.d("test", "SMS permission denied!");
        }
    }

    public void addOrder() {
        StringBuilder items = new StringBuilder();
        int totalPrice = 0;
        for (int i=0; i < listProduct.size()-1; ++i) {
            items.append(listProduct.get(i).getName());
            items.append(", ");
            totalPrice += listProduct.get(i).getPrice();
        }
        items.append(listProduct.get(listProduct.size()-1).getName());
        totalPrice += listProduct.get(listProduct.size()-1).getPrice();

        SQLiteDatabase db = DBOpenHelper.getInstance(this).getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(DBOpenHelper.ITEMS, items.toString());
        cv.put(DBOpenHelper.TOTALPRICE, totalPrice);

        db.insert(DBOpenHelper.ORDERS, null, cv);
    }
}