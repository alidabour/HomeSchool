package com.almanara.homeschool.parent.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.almanara.homeschool.data.firebase.ChildModel;
import com.bumptech.glide.Glide;
import com.almanara.ali.homeschool.R;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.List;

/**
 * Created by Ali on 4/21/2017.
 */

public class ChildsAdapter extends RecyclerView.Adapter<ChildsAdapter.ChildViewHolder> {


    public ChildsAdapter(List<ChildModel> childList, Context context ,
                         OnClickHandler onClickHandler) {
        this.childList = childList;
        this.onClickHandler = onClickHandler;
        this.context = context ;
    }


    Context context ;
    List<ChildModel> childList;
    OnClickHandler onClickHandler;
    public interface OnClickHandler {
        void onClick(ChildModel test);
    }
    @Override
    public ChildViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.children_item_card,parent,false);
        return new ChildViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ChildViewHolder holder, int position) {
        ChildModel childModel = childList.get(position);
        holder.childName.setText(childModel.getName());
        //holder.childPhoto.setImageResource(R.drawable.photoid);
        Log.v("TestImage","E :" + childList.size());
        if(childModel.getPhoto()!=null)
        Glide.with(context).load(childModel.getPhoto().trim()).listener(
                new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model,
                                               Target<GlideDrawable> target,
                                               boolean isFirstResource) {
                        Log.v("TestImage","E :" + e.getMessage());
//                        Log.v("TestImage","E :" + e.printStackTrace());
                        e.printStackTrace();

                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model,
                                                   Target<GlideDrawable> target,
                                                   boolean isFromMemoryCache,
                                                   boolean isFirstResource) {
                        Log.v("TestImage","Ready");
                        return false;
                    }
                }).into(holder.childPhoto);

    }

    @Override
    public int getItemCount() {
        return childList.size();
    }

    public class ChildViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView childName;
        private ImageView childPhoto;
        public ChildViewHolder(View itemView) {
            super(itemView);
            childName= (TextView) itemView.findViewById(R.id.children_textView);
            childPhoto= (ImageView) itemView.findViewById(R.id.children_imageView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int p = getAdapterPosition();
            onClickHandler.onClick(childList.get(p));
        }
    }
}
