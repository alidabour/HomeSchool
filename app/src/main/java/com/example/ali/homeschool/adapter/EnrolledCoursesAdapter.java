package com.example.ali.homeschool.adapter;

/**
 * Created by Ali on 2/23/2017.
 */

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ali.homeschool.R;
import com.example.ali.homeschool.data.CategoryInformation;
import com.example.ali.homeschool.data.Entry.CourseColumns;

import java.util.List;



import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ali.homeschool.data.CategoryInformation;
import com.example.ali.homeschool.R;
import com.example.ali.homeschool.data.DataProvider;
import com.example.ali.homeschool.data.Entry.CourseColumns;

import java.util.List;

/**
 * Created by Ali on 1/22/2017.
 * This class is designed to create the course as an image and a text beneath it
 * and that is being used in the recycle view in the StudentHomeActivity Layout
 */

public class EnrolledCoursesAdapter extends RecyclerView.Adapter<EnrolledCoursesAdapter.CategoryViewHolder> {
    List<CategoryInformation> categoryInformationList ;
    private Cursor mCursor;

    public EnrolledCoursesAdapter() {
    }

    @Override
    public EnrolledCoursesAdapter.CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.enrolled_course_item,parent,false);
        return new CategoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(EnrolledCoursesAdapter.CategoryViewHolder holder, int position) {
        mCursor.moveToPosition(position);
//        CategoryInformation categoryInformation = categoryInformationList.get(position);
//        Log.v("Test","test");
//        Log.v("Test","Inf:"+categoryInformation.getCategoryName()+" "+categoryInformation.getCategoryImage());
//        holder.categoryName.setText(categoryInformation.getCategoryName());
//        holder.categoryImage.setImageResource(categoryInformation.getCategoryImage());
        holder.categoryName.setText(mCursor.getString(mCursor.getColumnIndex(CourseColumns.COURSE_NAME)));
        Log.v("Test","Enrolled :"+mCursor.getColumnIndex(CourseColumns.COURSE_NAME));
        holder.categoryImage.setImageResource(R.drawable.earlymath);

    }

    @Override
    public int getItemCount() {
        if (mCursor == null) {
            return 0;
        }
        return mCursor.getCount();
    }
    public Cursor swapCursor(Cursor c) {
        // check if this cursor is the same as the previous cursor (mCursor)
        if (mCursor == c) {
            return null; // bc nothing has changed
        }
        Cursor temp = mCursor;
        this.mCursor = c; // new cursor value assigned

        //check if this is a valid cursor, then update the cursor
        if (c != null) {
            this.notifyDataSetChanged();
        }
        return temp;
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
