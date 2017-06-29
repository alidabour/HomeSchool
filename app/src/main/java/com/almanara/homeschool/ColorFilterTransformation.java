package com.almanara.homeschool;

import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapResource;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;

/**
 * Created by Ali on 6/29/2017.
 */
public class ColorFilterTransformation extends BitmapTransformation {

    private BitmapPool mBitmapPool;

    private int mColor;

    public ColorFilterTransformation(Context context) {
        super(context);
    }

//    public ColorFilterTransformation(BitmapPool pool, int color) {
//        mBitmapPool = pool;
//        mColor = color;
//    }

//    @Override
//    public Resource<Bitmap> transform(Resource<Bitmap> resource, int outWidth, int outHeight) {
//        Bitmap source = resource.get();
//
//        int width = source.getWidth();
//        int height = source.getHeight();
//
//        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
//
//        Canvas canvas = new Canvas(bitmap);
//        Paint paint = new Paint();
//        paint.setAntiAlias(true);
//        paint.setColorFilter(new PorterDuffColorFilter(mColor, PorterDuff.Mode.SRC_ATOP));
//        canvas.drawBitmap(source, 0, 0, paint);
//        source.recycle();
//
//        return BitmapResource.obtain(bitmap, mBitmapPool);
//    }

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        Bitmap source = toTransform;

        int width = source.getWidth();
        int height = source.getHeight();

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColorFilter(new PorterDuffColorFilter(Color.parseColor("#00ff00"), PorterDuff.Mode.SRC_ATOP));
        canvas.drawBitmap(source, 0, 0, paint);
        source.recycle();

        return source;
    }

    @Override
    public String getId() {
        return "ColorFilterTransformation(color=" + mColor + ")";
    }
}