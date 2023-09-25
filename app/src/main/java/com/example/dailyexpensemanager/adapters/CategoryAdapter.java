package com.example.dailyexpensemanager.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dailyexpensemanager.R;
import com.example.dailyexpensemanager.databinding.CategoryItemSampleLayoutBinding;
import com.example.dailyexpensemanager.models.CategoryModel;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    Context context;
    ArrayList<CategoryModel> categoryModelArrayList;

    public interface CategoryClickListener {
        void onCategoryClicked(CategoryModel categoryModel);
    }

    CategoryClickListener categoryClickListener;

    public CategoryAdapter(Context context, ArrayList<CategoryModel> categoryModelArrayList, CategoryClickListener categoryClickListener) {
        this.context = context;
        this.categoryModelArrayList = categoryModelArrayList;
        this.categoryClickListener = categoryClickListener;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryViewHolder(LayoutInflater.from(context).inflate(R.layout.category_item_sample_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        CategoryModel categoryModel = categoryModelArrayList.get(position);
        holder.binding.categoryIconImageView.setImageResource(categoryModel.getCategoryImage());
        holder.binding.categoryNameTextView.setText("" + categoryModel.getCategoryName());
        holder.binding.categoryIconImageView.setBackgroundTintList(context.getColorStateList(categoryModel.getCategoryColor()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoryClickListener.onCategoryClicked(categoryModel);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryModelArrayList.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        CategoryItemSampleLayoutBinding binding;
        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = CategoryItemSampleLayoutBinding.bind(itemView);
        }
    }
}
