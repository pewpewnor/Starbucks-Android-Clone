package com.example.starbucks2.ui.menu;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.starbucks2.DetailActivity;
import com.example.starbucks2.R;
import com.example.starbucks2.adapter.ProductAdapter;
import com.example.starbucks2.databinding.FragmentMenuBinding;
import com.example.starbucks2.models.Product;

import java.util.ArrayList;

public class MenuFragment extends Fragment implements ProductAdapter.ProductClickListener {

    private FragmentMenuBinding binding;

    ArrayList<Product> products = new ArrayList<>();
    RecyclerView rvProducts;
    ProductAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMenuBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvProducts = getView().findViewById(R.id.rvProducts);
        rvProducts.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false));
        insertProduct();
        adapter = new ProductAdapter(products, this);
        rvProducts.setAdapter(adapter);
    }

    void insertProduct() {
        Drawable ad = getResources().getDrawable(R.drawable.caffe_latte);
        products.add(new Product(1, "Caffe Latte", 50, "Our dark, rich espresso balanced with steamed milk and a light layer of foam.", ad, R.drawable.caffe_latte));

        Drawable bd = getResources().getDrawable(R.drawable.cold_brew);
        products.add(new Product(2, "Cold Brew", 60, "Handcrafted in small batches daily, slow-steeped in cool water for 20 hours.", bd, R.drawable.cold_brew));

        Drawable cd = getResources().getDrawable(R.drawable.cortado);
        products.add(new Product(3, "Cortado", 30, "Two ristretto shots topped with warm, silky milk served in a 6oz cup.", cd, R.drawable.cortado));

        Drawable dd = getResources().getDrawable(R.drawable.english_breakfast);
        products.add(new Product(4, "English Tea", 40, "Each sip of this beloved morning black tea unfolds to reveal the complexity of the high-grown.", dd, R.drawable.english_breakfast));

        Drawable ed = getResources().getDrawable(R.drawable.flat_white);
        products.add(new Product(5, "Flat White", 0, "The Flat White combines steamed milk and rich, dark espresso for a creamy coffee-forward drink.", ed, R.drawable.flat_white));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onProductClicked(int position) {
        Intent intent = new Intent(this.getContext(), DetailActivity.class);
        Product p = products.get(position);
        intent.putExtra("name", p.getName());
        intent.putExtra("price", p.getPrice());
        intent.putExtra("desc", p.getDescription());
        intent.putExtra("imgid", p.getImgid());
//        intent.putExtra("product", products.get(position));
        startActivity(intent);
    }
}