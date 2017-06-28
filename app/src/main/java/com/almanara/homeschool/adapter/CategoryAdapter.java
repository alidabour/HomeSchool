package com.almanara.homeschool.adapter;


import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.almanara.homeschool.data.firebase.CourseCreated;
import com.bumptech.glide.Glide;
import com.almanara.ali.homeschool.R;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by Ali on 1/22/2017.
 * This class is designed to create the course as an image and a text beneath it
 * and that is being used in the recycle view in the StudentHomeActivity Layout
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    List<CourseCreated> CourseList ;
    private Cursor mCursor;
    public  OnClickHandler onClickHandler;
    Context context;
    public interface OnClickHandler {
        void onClick(CourseCreated test);
    }

    public CategoryAdapter(Context context,List<CourseCreated> CourseList , OnClickHandler onClickHandler) {
        Log.v("Test","----------------------------------CategoryAdapter");
        this.context=context;
        this.onClickHandler = onClickHandler;
        this.CourseList  = CourseList ;
//        Log.v("Test","Constr."+categoryInformationList.get(0).getCategoryName());
    }

    @Override
    public CategoryAdapter.CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_item_card,parent,false);
        return new CategoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CategoryAdapter.CategoryViewHolder holder, int position) {
//        Log.v("Test","----------------------------------CategoryAdapter : "+ CourseList.get(position).toString());
        CourseCreated Course = CourseList.get(position);

        Glide.with(context).load(Course.getPhoto_url()).fitCenter().bitmapTransform(new RoundedCornersTransformation(context,20,0, RoundedCornersTransformation.CornerType.TOP)).into(holder.categoryImage);
        Log.v("Trailers101",holder.categoryImage.getScaleType()+"");
        holder.categoryName.setText(Course.getName());
        Log.v("Trailers10111111111111",Course.getName()+"");
//        mCursor.moveToPosition(position);
//        CategoryInformation categoryInformation = categoryInformationList.get(position);
//        Log.v("Test","test");
//        Log.v("Test","Inf:"+categoryInformation.getCategoryName()+" "+categoryInformation.getCategoryImage());
//        holder.categoryName.setText(categoryInformation.getCategoryName());
//        holder.categoryImage.setImageResource(categoryInformation.getCategoryImage());

        //test
//        holder.categoryName.setText(mCursor.getString(mCursor.getColumnIndex(CourseColumns.COURSE_NAME)));
//        holder.categoryImage.setImageResource(R.drawable.earlymath);

    }

    @Override
    public int getItemCount() {
//        if (mCursor == null) {
//            return 0;
//        }
//        return mCursor.getCount();
        return CourseList.size();
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

    public  class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        protected TextView categoryName;
        protected ImageView categoryImage;
        protected CardView cardView;
        public CategoryViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.cardView);
            categoryImage = (ImageView) itemView.findViewById(R.id.category_imageView);
            categoryName = (TextView) itemView.findViewById(R.id.category_textView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            cardView.setBackgroundColor(Color.parseColor("#04bf4f"));
//            cardView.setBackground(context.getResources().getDrawable(R.drawable.student_icon));
//            Cursor cursor = mCursor;
//            cursor.moveToPosition(position);
          //  String id = mCursor.getString(mCursor.getColumnIndex(CourseColumns.GLOBAL_ID));
          //  onClickHandler.onClick(id);
            onClickHandler.onClick(CourseList.get(position));
        }
    }
}
