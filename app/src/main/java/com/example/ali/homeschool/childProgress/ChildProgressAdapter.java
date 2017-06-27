package com.example.ali.homeschool.childProgress;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.ali.homeschool.R;

/**
 * Created by Ali on 3/1/2017.
 */

public class ChildProgressAdapter extends RecyclerView.Adapter<ChildProgressAdapter.ChildProgressViewHolder> {
    private Cursor mCursor;
    @Override
    public ChildProgressViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.child_progress_item,parent,false);
        return new ChildProgressViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ChildProgressViewHolder holder, int position) {
        mCursor.moveToPosition(position);
//        holder.courseName.setText(mCursor.getString(mCursor.getColumnIndex(ChildProgressColumns.COURSE_NAME)));
//        holder.progressBar.setProgress(mCursor.getInt(mCursor.getColumnIndex(ChildProgressColumns.COURSE_PROGRESS)));
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

    public class ChildProgressViewHolder extends RecyclerView.ViewHolder {
        TextView courseName;
        ProgressBar progressBar;
        public ChildProgressViewHolder(View itemView) {
            super(itemView);
            courseName = (TextView) itemView.findViewById(R.id.courseName);
            progressBar = (ProgressBar) itemView.findViewById(R.id.courseProgress);
        }
    }
}
