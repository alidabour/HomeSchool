package com.example.ali.homeschool;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Ali on 1/23/2017.
 */

public class ImageCollapsingToolBarAdapter extends PagerAdapter {
    Context mContext;
    private int[] sliderImagesId = new int[]{
            R.drawable.earlymath,R.drawable.ic_launcher,R.drawable.earlymath
    };
    public ImageCollapsingToolBarAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return sliderImagesId.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==((ImageView)object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView mImageView  = new ImageView(mContext);
        mImageView.setScaleType(ImageView.ScaleType.FIT_XY);
        mImageView.setImageResource(sliderImagesId[position]);
        ((ViewPager) container).addView(mImageView,0);
        return mImageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((ImageView) object);
    }
}
// extends RecyclerView.Adapter<ImageCollapsingToolBarAdapter.ImageViewHolder> {
//    List<Integer> imageIds ;
//
//    public ImageCollapsingToolBarAdapter(List<Integer> imageIds) {
//        this.imageIds = imageIds;
//    }
//
//    @Override
//    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View itemView = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.collapsing_image_item,parent,false);
//        return new ImageCollapsingToolBarAdapter.ImageViewHolder(itemView);
//    }
//
//    @Override
//    public void onBindViewHolder(ImageViewHolder holder, int position) {
//        int id = imageIds.get(position);
//        holder.image.setImageResource(id);
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return imageIds.size();
//    }
//
//    public static class ImageViewHolder extends RecyclerView.ViewHolder {
//        protected ImageView image;
//        public ImageViewHolder(View itemView) {
//            super(itemView);
//            image = (ImageView) itemView.findViewById(R.id.collapsing_toolbar_image_item);
//        }
//    }
//}
