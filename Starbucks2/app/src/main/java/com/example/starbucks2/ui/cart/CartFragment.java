package com.example.starbucks2.ui.cart;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.starbucks2.ConfirmActivity;
import com.example.starbucks2.DBOpenHelper;
import com.example.starbucks2.DelActivity;
import com.example.starbucks2.MainActivity;
import com.example.starbucks2.MapsFragment;
import com.example.starbucks2.R;
import com.example.starbucks2.adapter.ProductAdapter;
import com.example.starbucks2.databinding.FragmentCartBinding;
import com.example.starbucks2.models.Product;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class CartFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private FragmentCartBinding binding;
    FloatingActionButton fabDel, fabConfirm;

    public static ArrayList<Product> listProduct = new ArrayList<>();
    RecyclerView rvCart;
    ProductAdapter adapter;

    MapsFragment mapsFragment;
    Spinner spinner;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCartBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mapsFragment = new MapsFragment(-6.1866416, 106.8232969);
        this.getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.mapFragment, mapsFragment).commit();

        Spinner spinner = getView().findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this.getContext(), R.array.locations, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(this);

        fabDel = getView().findViewById(R.id.fabDel);
        fabDel.setOnClickListener(x -> {
            if (listProduct.isEmpty()) {
                Toast.makeText(this.getContext(), "You currently have no item in your cart!", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent intent = new Intent(this.getActivity(), DelActivity.class);
            startActivity(intent);
        });

        fabConfirm = getView().findViewById(R.id.fabConfirm);
        fabConfirm.setOnClickListener(x -> {
            if (listProduct.isEmpty()) {
                Toast.makeText(this.getContext(), "You currently have no item in your cart!", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent intent = new Intent(this.getActivity(), ConfirmActivity.class);
            startActivity(intent);
        });

        getProductDataFromDB();

        rvCart = getView().findViewById(R.id.rvCart);
        rvCart.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false));
        adapter = new ProductAdapter(listProduct);
        rvCart.setAdapter(adapter);
    }

    void getProductDataFromDB() {
        listProduct.clear();
        SQLiteDatabase db = DBOpenHelper.getInstance(this.getContext()).getReadableDatabase();
        Cursor cursor = db.query(
                DBOpenHelper.PRODUCTS,
                new String[] {
                        DBOpenHelper.ID,
                        DBOpenHelper.NAME,
                        DBOpenHelper.PRICE,
                        DBOpenHelper.DESCRIPTION
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
                @SuppressLint("UseCompatLoadingForDrawables") Drawable ad = getResources().getDrawable(R.drawable.cold_brew);
                listProduct.add(new Product(
                        cursor.getInt(cursor.getColumnIndexOrThrow(DBOpenHelper.ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DBOpenHelper.NAME)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(DBOpenHelper.PRICE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DBOpenHelper.DESCRIPTION)),
                        ad,
                        R.drawable.cold_brew
                ));
            } while (cursor.moveToNext());
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
        String text = parent.getItemAtPosition(position).toString();

        if (text.equals("Starbucks Skyline Building")) {
            mapsFragment = new MapsFragment(-6.1866416, 106.8232969);
        }
        else if (text.equals("Starbucks Drive Thru hayam Wuruk")) {
            mapsFragment = new MapsFragment(-6.1646887, 106.8204573);
        }
        else if (text.equals("Starbucks Coffee Stasiun Gambir")) {
            mapsFragment = new MapsFragment(-6.1773367, 106.8282642);
//            mapsFragment = new MapsFragment(36.2422106, -113.7493378);
        }
        this.getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.mapFragment, mapsFragment).commit();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}