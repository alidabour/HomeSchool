package com.almanara.homeschool.adapter;


import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.almanara.ali.homeschool.R;
import com.almanara.homeschool.data.CategoryInformation;

import java.util.List;

/**
 * Created by Ali on 1/22/2017.
 * This class is designed to create the course as an image and a text beneath it
 * and that is being used in the recycle view in the StudentHomeActivity Layout
 */

public class ChildrenAdapter extends RecyclerView.Adapter<ChildrenAdapter.CategoryViewHolder> {
    List<CategoryInformation> categoryInformationList ;
    public OnClickHandler onClickHandler;
    private Cursor mCursor;
    public interface OnClickHandler {
        void onClick(String test);
    }
    public ChildrenAdapter(List<CategoryInformation> categoryInformationList,OnClickHandler onClickHandler) {
        this.categoryInformationList = categoryInformationList;
        this.onClickHandler = onClickHandler;
//        Log.v("Test","Constr."+categoryInformationList.get(0).getCategoryName());
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
//        Log.v("Test","test");
//        Log.v("Test","Inf:"+categoryInformation.getCategoryName()+" "+categoryInformation.getCategoryImage());
        mCursor.moveToPosition(position);
//        holder.childrenName.setText(mCursor.getString(mCursor.getColumnIndex(ChildColumns.CHILD_NAME)));
        holder.childrenImage.setImageResource(R.drawable.photoid);
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
    public class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        protected TextView childrenName;
        protected ImageView childrenImage;
        public CategoryViewHolder(View itemView) {
            super(itemView);
            childrenImage = (ImageView) itemView.findViewById(R.id.children_imageView);
            childrenName = (TextView) itemView.findViewById(R.id.children_textView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onClickHandler.onClick("test");
        }
    }
}
