/*
 * Copyright (C) 2015 Paul Burke
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.ali.homeschool.instructor;

import android.graphics.Color;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.ali.homeschool.instructor.create.ordering.DoneOrderInterface;
import com.example.ali.homeschool.instructor.create.ordering.ItemTouchHelperAdapter;
import com.example.ali.homeschool.instructor.create.ordering.ItemTouchHelperViewHolder;
import com.example.ali.homeschool.instructor.create.ordering.OnStartDragListener;
import com.example.ali.homeschool.R;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



/**
 * Simple RecyclerView.Adapter that implements {@link ItemTouchHelperAdapter} to respond to move and
 * dismiss events from a {@link android.support.v7.widget.helper.ItemTouchHelper}.
 *
 * @author Paul Burke (ipaulpro)
 */
public class RecyclerListAdapter extends RecyclerView.Adapter<RecyclerListAdapter.ItemViewHolder>
        implements ItemTouchHelperAdapter {

    private List<String> mItems;
    private final OnStartDragListener mDragStartListener;
    DoneOrderInterface doneOrderInterface;
    ParseXMLInstructor parseXMLInstructor;

    public RecyclerListAdapter(ParseXMLInstructor parseXMLInstructor,
                               OnStartDragListener dragStartListener,
                               ArrayList<String> layouts,
                               final DoneOrderInterface doneOrderInterface) {
        this.parseXMLInstructor = parseXMLInstructor;
        mDragStartListener = dragStartListener;
        mItems = new ArrayList<>();
        mItems.addAll(layouts);
        this.doneOrderInterface = doneOrderInterface;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext()).inflate(R.layout.holder_ordering_layout, parent, false);
        ItemViewHolder itemViewHolder = new ItemViewHolder(view);
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        String layout = mItems.get(position);
        try {
            holder.view.addView(parseXMLInstructor.parse(layout));
        } catch (XmlPullParserException e) {
            Log.v("Adapter", "Error +++" + e.getMessage());
        } catch (IOException e) {
            Log.v("Adapter", "Error +--+" + e.getMessage());
        }
        // Start a drag whenever the handle view it touched
        holder.handleView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                    mDragStartListener.onStartDrag(holder);
                }
                return false;
            }
        });
    }

    @Override
    public void onItemDismiss(int position) {
        Log.v("O-O", "adapter before remove:" + mItems.size());
        mItems.remove(position);
        doneOrderInterface.onReorder(mItems);
        Log.v("O-O", "adapter after remove:" + mItems.size());

        notifyItemRemoved(position);
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Log.v("O-O", "adapter before Swap:" + mItems.size());
        Collections.swap(mItems, fromPosition, toPosition);
        Log.v("O-O", "adapter after Swap:" + mItems.size());
        notifyItemMoved(fromPosition, toPosition);
        doneOrderInterface.onReorder(mItems);
        return true;
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void clearItems() {
        Log.v("O-O", "adapter before clearItems :" + mItems.size());
        mItems.clear();
        Log.v("O-O", "adapter after clearItems :" + mItems.size());

    }

    public void setArrayItems(ArrayList<String> layouts) {
        Log.v("O-O", "adapter before setArrayItems :" + mItems.size());
        mItems.addAll(layouts);
        notifyDataSetChanged();
        Log.v("O-O", "adapter after setArrayItems :" + mItems.size());

    }

    /**
     * Simple example of a view holder that implements {@link ItemTouchHelperViewHolder} and has a
     * "handle" view that initiates a drag event when touched.
     */
    public static class ItemViewHolder extends RecyclerView.ViewHolder implements
            ItemTouchHelperViewHolder {

        public final ImageView handleView;
        public LinearLayout view;

        public ItemViewHolder(View itemView) {
            super(itemView);
            handleView = (ImageView) itemView.findViewById(R.id.handle);
            view = (LinearLayout) itemView.findViewById(R.id.test);
        }

        @Override
        public void onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY);
        }

        @Override
        public void onItemClear() {
            itemView.setBackgroundColor(0);
        }
    }
}
