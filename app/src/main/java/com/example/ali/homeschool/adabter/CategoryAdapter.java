package com.example.ali.homeschool.adabter;


import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ali.homeschool.data.CategoryInformation;
import com.example.ali.homeschool.R;

import java.util.List;

/**
 * Created by Ali on 1/22/2017.
 * This class is designed to create the course as an image and a text beneath it
 * and that is being used in the recycle view in the StudentHomeActivity Layout
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    List<CategoryInformation> categoryInformationList ;

    public CategoryAdapter(List<CategoryInformation> categoryInformationList) {
        this.categoryInformationList = categoryInformationList;
        Log.v("Test","Constr."+categoryInformationList.get(0).getCategoryName());
    }

    @Override
    public CategoryAdapter.CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_item_card,parent,false);
        return new CategoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CategoryAdapter.CategoryViewHolder holder, int position) {
        CategoryInformation categoryInformation = categoryInformationList.get(position);
        Log.v("Test","test");
        Log.v("Test","Inf:"+categoryInformation.getCategoryName()+" "+categoryInformation.getCategoryImage());
        holder.categoryName.setText(categoryInformation.getCategoryName());
        holder.categoryImage.setImageResource(categoryInformation.getCategoryImage());
    }

    @Override
    public int getItemCount() {
        return categoryInformationList.size();
    }
    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
        protected TextView categoryName;
        protected ImageView categoryImage;
        public CategoryViewHolder(View itemView) {
            super(itemView);
            categoryImage = (ImageView) itemView.findViewById(R.id.category_imageView);
            categoryName = (TextView) itemView.findViewById(R.id.category_textView);
        }
    }
}
