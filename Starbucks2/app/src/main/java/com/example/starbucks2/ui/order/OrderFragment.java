package com.example.starbucks2.ui.order;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.starbucks2.DBOpenHelper;
import com.example.starbucks2.R;
import com.example.starbucks2.adapter.OrderAdapter;
import com.example.starbucks2.adapter.ProductAdapter;
import com.example.starbucks2.databinding.FragmentOrderBinding;
import com.example.starbucks2.models.Order;
import com.example.starbucks2.models.Product;

import java.util.ArrayList;

public class OrderFragment extends Fragment {
    private FragmentOrderBinding binding;

    RecyclerView rvOrder;
    OrderAdapter adapter;

    ArrayList<Order> orders = new ArrayList<Order>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentOrderBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getOrderDataFromDB();

        rvOrder = getView().findViewById(R.id.rvOrder);
        rvOrder.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false));
        adapter = new OrderAdapter(orders);
        rvOrder.setAdapter(adapter);
    }

    void getOrderDataFromDB() {
        orders.clear();
        SQLiteDatabase db = DBOpenHelper.getInstance(this.getContext()).getReadableDatabase();
        Cursor cursor = db.query(
                DBOpenHelper.ORDERS,
                new String[] {
                        DBOpenHelper.ID,
                        DBOpenHelper.ITEMS,
                        DBOpenHelper.TOTALPRICE,
                },
                null,
                null,
                null,
                null,
                null
        );

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {
                orders.add(new Order(
                        cursor.getInt(cursor.getColumnIndexOrThrow(DBOpenHelper.ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DBOpenHelper.ITEMS)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(DBOpenHelper.TOTALPRICE))
                ));
            } while (cursor.moveToNext());
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}