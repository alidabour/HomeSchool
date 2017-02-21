package com.example.ali.homeschool.adapter;


import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ali.homeschool.R;
import com.example.ali.homeschool.data.CategoryInformation;

import java.util.List;

/**
 * Created by Ali on 1/22/2017.
 * This class is designed to create the course as an image and a text beneath it
 * and that is being used in the recycle view in the StudentHomeActivity Layout
 */

public class ChildrenAdapter extends RecyclerView.Adapter<ChildrenAdapter.CategoryViewHolder> {
    List<CategoryInformation> categoryInformationList ;

    public ChildrenAdapter(List<CategoryInformation> categoryInformationList) {
        this.categoryInformationList = categoryInformationList;
        Log.v("Test","Constr."+categoryInformationList.get(0).getCategoryName());
    }

    @Override
    public ChildrenAdapter.CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.children_item_card,parent,false);
        return new CategoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CategoryViewHolder holder, int position) {
        CategoryInformation categoryInformation = categoryInformationList.get(position);
        Log.v("Test","test");
        Log.v("Test","Inf:"+categoryInformation.getCategoryName()+" "+categoryInformation.getCategoryImage());
        holder.childrenName.setText(categoryInformation.getCategoryName());
        holder.childrenImage.setImageResource(categoryInformation.getCategoryImage());
    }

    @Override
    public int getItemCount() {
        return categoryInformationList.size();
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
        protected TextView childrenName;
        protected ImageView childrenImage;
        public CategoryViewHolder(View itemView) {
            super(itemView);
            childrenImage = (ImageView) itemView.findViewById(R.id.children_imageView);
            childrenName = (TextView) itemView.findViewById(R.id.children_textView);
        }
    }
}
